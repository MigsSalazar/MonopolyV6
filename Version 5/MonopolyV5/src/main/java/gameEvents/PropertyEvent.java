/**
 * 
 */
package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import main.java.action.BankPropertyActions;
import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

/**
 * @author Miguel
 *
 */
public class PropertyEvent extends AbstractEvent {
	
	int status;
	Player play;
	Property prop;
	
	public PropertyEvent(EventPanel p, Player pl, Property pr){
		super(p);
		play = pl;
		prop = pr;
		status = ownership();
		text = play.getName()+" has landed on "+prop.getName()+" which belongs to ";
		switch(status){
		case -1:	text += prop.getOwner().getName()+".\nYou own them $"+prop.getRent()+" in rent.";
					break;
		case 0:		text += "no one.\nProperty is valued at $"+prop.getPrice()+".\nWhat would you like to do?";
					break;
		case 1:		text += "themself.\nA landlord must always check in on their asset";
					break;
		}
		defineComponents();
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(status == 0){
			if(e.getSource().equals(buttons[0])){
				BankPropertyActions.sellUnownedProperty(play, prop);
			}else{
				AbstractEvent ae = new AuctionEvent(parent, prop);
				parent.paintEvent(ae);
				try {
					ae.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}else if(status == -1){
			BankPropertyActions.rentOwnedProperty(play, prop);
		}
		
		
		this.notify();
	}

	/* (non-Javadoc)
	 * @see main.java.action.MultiChoiceEvent#defineButtons()
	 */
	@Override
	public void defineComponents() {
		if(status == 0){
			buttons = new JButton[2];
			buttons[0] = new JButton("Purchase");
			((JButton)buttons[0]).addActionListener(this);
			buttons[1] = new JButton("Auction Off");
			((JButton)buttons[1]).addActionListener(this);
		}else{
			buttons = new JButton[1];
			if(status == -1){
				buttons[0] = new JButton("Pay Rent");
				((JButton)buttons[0]).addActionListener(this);
			}else{
				buttons[0] = new JButton("Move Along");
				((JButton)buttons[0]).addActionListener(this);
			}
		}
	}
	
	private int ownership(){
		if(prop.getOwner() == null){
			return 0;
		}else if(prop.getOwner().equals(play)){
			return 1;
		}else{
			return -1;
		}
	}


}

/**
 * 
 */
package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

/**
 * @author Miguel
 *
 */
public class PropertyEvent extends AbstractEvent {
	
	private int status;
	private Player play;
	private Property prop;
	private Runner gameVars;
	private int rentOut = 0;
	
	public PropertyEvent(EventPanel p, Runner gv, Player pl, Property pr){
		super(p);
		//System.out.println("in the property event constructor");
		play = pl;
		prop = pr;
		gameVars = gv;
		status = ownership();
		text = "<html>"+play.getName()+" has landed on "+prop.getName()+" which belongs to ";
		//System.out.println("starting switch statement");
		switch(status){
		case -1:	rentOut = prop.getRent();
					text += prop.getOwner()+".<br>You own them $"+rentOut+" in rent.";
					break;
		case 0:		text += "no one.<br>Property is valued at $"+prop.getPrice()+".<br>What would you like to do?";
					break;
		case 1:		text += "themself.<br>A landlord must always check in on their asset";
					break;
		}
		text+= "</html>";
		defineComponents();
		//System.out.println("finished defining components");
		//parent.paintEvent(this);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(status == 0){
			if(e.getSource().equals(buttons[0])){
				//BankPropertyActions.sellUnownedProperty(play, prop);
				AbstractEvent ae = new PlayervPropertyEvent(parent, "You bought "+prop.getName()+"!", play, prop);
				parent.paintEvent(ae);
				sync(ae);
				desync();
			}else{
				AbstractEvent ae = new AuctionEvent(parent, prop);
				parent.paintEvent(ae);
				sync(ae);
				desync();
			}
		}else if(status == -1){
			//BankPropertyActions.rentOwnedProperty(play, prop);
			
			Player p2 = gameVars.getPlayers().get(prop.getOwner());
			String outText = "You payed "+prop.getOwner()+" $"+rentOut+" for landing on "+prop.getName()+"!";
			
			AbstractEvent ae = new PlayervPlayerEvent(parent, outText, play, p2, (-1)*rentOut);
			//AbstractEvent a = new MessageEvent(parent, "You payed "+play.getName()+" for landing on "+prop.getName()+"!");
			parent.paintEvent(ae);
			sync(ae);
			desync();
			//parent.jumpStartClean();
		}else if(status == 1){
			desync();
			parent.jumpStartClean();
		}
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
		if(prop.getOwner() == null || prop.getOwner().equals("")){
			return 0;
		}else if(prop.getOwner().equals(play.getName())){
			return 1;
		}else{
			return -1;
		}
	}

	
}

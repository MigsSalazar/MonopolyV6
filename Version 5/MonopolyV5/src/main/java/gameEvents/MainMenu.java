/**
 * 
 */
package main.java.gameEvents;

import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

/**
 * @author Miguel Salazar
 *
 */
public class MainMenu extends AbstractEvent {
	
	
	private Player currentPlayer;
	private Runner bigMe;
	private Random rando = new Random();
	
	/**
	 * @param p
	 */
	public MainMenu(EventPanel p) {
		super(p);
		bigMe = p.getGlobalVars();
		currentPlayer = bigMe.currentPlayer();
		text = currentPlayer.getName()+"'s turn.\nWhat would you like to do?";
		defineComponents();
		parent.paintEvent(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton me = (JButton)e.getSource();
			
			if(me.equals(buttons[0])){
				Player p = bigMe.currentPlayer();
				if(p.isInJail()){
					//TODO make jail event to see if player will get out of jail
				}else{
					//TODO have the dice displayed somewhere on the board so the players can see the dice rolls
					
					int dice1 = rando.nextInt(6)+1;
					int dice2 = rando.nextInt(6)+1;
					int roll = dice1 + dice2;
					
					crossGo(p, roll);
					
					
					p.movePlayer(roll);
					bigMe.getFrame().getGameBoard().movePlayer(p.getUserID(), roll);
					int result = findAction(p.getPosition());
					
					actionDone(p, result);
					
					if(dice1 != dice2){
						updateTurn();
					}
					
				}
			}else if(me.equals(buttons[1])){
				
			}else if(me.equals(buttons[2])){
				
			}else if(me.equals(buttons[3])){
				
			}
		}
		parent.paintEvent(this);

	}

	private void actionDone(Player p, int result) {
		AbstractEvent event;
		switch(result){
		case 1:event = new MessageEvent(parent, "You are passing through jail.\nSay hi to the jailbirds!");
				sync(event);
			break;
		case 2:event = new MessageEvent(parent,"");
				sync(event);
			break;
		case 3:p.setPosition(40);
				bigMe.getFrame().getGameBoard().jailPlayer(p.getUserID());
				p.setInJail(true);
				event = new MessageEvent(parent, p.getName()+", you must go to jail!\nDo not pass Go, do not collect $200!");
				sync(event);
			break;
		case 4:	event = new IncomeTaxEvent(parent, p);
				sync(event);
			break;
		case 5:event = new PlayervBankEvent(parent, p.getName()+", you must pay the $75 luxury tax!", p, -75);
				sync(event);
			break;
		case 6:	//TODO make a community chest event
			break;
		case 7:	//TODO make a chance event
			break;
		case 8:Property passMe = findPropPosition(p.getPosition());
				event = new PropertyEvent(parent, p, passMe);
				sync(event);
			break;
		}
	}

	private void crossGo(Player p, int roll) {
		if( (p.getPosition() + roll) >= 40 || (p.getPosition() + roll)==0){
			String text = p.getName()+" has landed or passed go. Collect $200.";
			PlayervBankEvent pbe = new PlayervBankEvent(parent, text, p, 200);
			sync(pbe);
		}
	}

	/* (non-Javadoc)
	 * @see main.java.action.Events#defineComponents()
	 */
	@Override
	public void defineComponents() {
		buttons = new JComponent[4];
		/*
		 * 4 menu options
		 * Role-Upgrade-Trade-Mortgage
		 */
		buttons[0] = new JButton("Role");
		((JButton)buttons[0]).addActionListener(this);
		
		buttons[1] = new JButton("Upgrade");
		((JButton)buttons[1]).addActionListener(this);
		
		buttons[2] = new JButton("Trade");
		((JButton)buttons[2]).addActionListener(this);
		
		buttons[3] = new JButton("Mortgage");
		((JButton)buttons[3]).addActionListener(this);
		
	}
	
	private void updateTurn(){
		bigMe.cyclePlayer();
		currentPlayer = bigMe.currentPlayer();
		text = currentPlayer.getName()+"'s turn.\nWhat would you like to do?";
		parent.paintEvent(this);
	}

	
	private int findAction(int position){
		
		if(position == 0){
			return 0;
		}else if(position == 10){
			return 1;
		}else if(position == 20){
			return 2;
		}else if(position == 30){
			return 3;
		}else if(position == 4){
			return 4;
		}else if(position == 38){
			return 5;
		}else if(position == 2 || position == 17 || position == 33){
			return 6;
		}else if(position == 7 || position == 22 || position == 36){
			return 7;
		}else{
			return 8;
		}
	}
	
	private Property findPropPosition(int pos){
		String[] names = bigMe.getPropName();
		Property prop;
		for(String s : names){
			prop = bigMe.getProperties().get(s);
			if(pos == prop.getPosition()){
				return prop;
			}
		}
		return null;
	}
	
	
}

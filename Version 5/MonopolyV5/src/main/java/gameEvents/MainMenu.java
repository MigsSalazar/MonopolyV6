/**
 * 
 */
package main.java.gameEvents;

import java.awt.event.ActionEvent;

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
	private int turn;
	private Runner bigMe;
	/**
	 * @param p
	 */
	public MainMenu(EventPanel p) {
		super(p);
		bigMe = p.getGlobalVars();
		turn = bigMe.getPlayerTurn();
		currentPlayer = bigMe.currentPlayer();
		text = currentPlayer.getName()+"'s turn.\nWhat would you like to do?";
		defineComponents();
	}

	/**
	 * @param p
	 * @param t
	 */
	public MainMenu(EventPanel p, String t) {
		super(p, t);
		bigMe = p.getGlobalVars();
		turn = bigMe.getPlayerTurn();
		currentPlayer = bigMe.currentPlayer();
		defineComponents();
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
					int roll = rollDice();
					if( (p.getPosition() + roll) >= 40 || (p.getPosition() + roll)==0){
						String text = p.getName()+" has landed or passed go. Collect $200.";
						PlayervBankEvent pbe = new PlayervBankEvent(parent, text, p, 200);
						sync(pbe);
					}
					p.movePlayer(roll);
					bigMe.getFrame().getGameBoard().movePlayer(p.getUserID(), roll);
					String result = findAction(p.getPosition());
					
					AbstractEvent event;
					
					switch(result){
					case "pass":event = new MessageEvent(parent, "You are passing through jail.\nSay hi to the jailbirds!");
								sync(event);
						break;
					case "free":event = new MessageEvent(parent,"");
								sync(event);
						break;
					case "jail":p.setPosition(40);
								bigMe.getFrame().getGameBoard().jailPlayer(p.getUserID());
								p.setInJail(true);
								event = new MessageEvent(parent, p.getName()+", you must go to jail!\nDo not pass Go, do not collect $200!");
								sync(event);
						break;
					case "itax":	//TODO make an income tax event to run here
						break;
					case "ltax":event = new PlayervBankEvent(parent, p.getName()+", you must pay the $75 luxury tax!", p, -75);
						break;
					case "comm":	//TODO make a community chest event
						break;
					case "chest":	//TODO make a chance event
						break;
					case "prop":Property passMe = findPropPosition(p.getPosition());
								event = new PropertyEvent(parent, p, passMe);
								sync(event);
						break;
					}
				}
			}else if(me.equals(buttons[1])){
				
			}else if(me.equals(buttons[2])){
				
			}else if(me.equals(buttons[3])){
				
			}
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
		turn = bigMe.getPlayerTurn();
		currentPlayer = bigMe.currentPlayer();
	}

	
	private String findAction(int position){
		
		if(position == 0){
			return "go";
		}else if(position == 10){
			return "pass";
		}else if(position == 20){
			return "free";
		}else if(position == 30){
			return "jail";
		}else if(position == 4){
			return "itax";
		}else if(position == 38){
			return "ltax";
		}else if(position == 2 || position == 17 || position == 33){
			return "comm";
		}else if(position == 7 || position == 22 || position == 36){
			return "chance";
		}else{
			return "prop";
		}
	}
	
	private void sync(AbstractEvent ae){
		while(ae.running()){
			try {
				ae.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	private int rollDice(){
		return bigMe.getDice().roll();
	}
	
}

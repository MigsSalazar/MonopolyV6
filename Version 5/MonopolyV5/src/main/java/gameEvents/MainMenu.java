/**
 * 
 */
package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.action.TradeManager;
import main.java.gui.BoardPanel;
import main.java.gui.EventPanel;
import main.java.gui.MortManagerFrame;
import main.java.gui.MortgageManager;

/**
 * @author Miguel Salazar
 *
 */
public class MainMenu extends DiceNeededEvent{
	
	
	/**
	 * @param p
	 */
	public MainMenu(EventPanel p) {
		super(p);
		//System.out.println("in the main menu contructor");
		gameVars = p.getGlobalVars();
		//System.out.println("grabbing global vars");
		gameDice = gameVars.getGameDice();
		//System.out.println("got the dice");
		currentPlayer = gameVars.currentPlayer();
		//System.out.println("got the first player");
		text = "<html>"+currentPlayer.getName()+"'s turn.<br>What would you like to do?</html>";
		System.out.println(text);
		//System.out.println("wrote the text: "+text);
		defineComponents();
		//System.out.println("defined components");
		//parent.paintEvent(this);
	}
	
	/**
	 * THIS IS FOR TEMPLITAZATION AND TESTING ONLY
	 * @param p
	 * @param b
	 * @deprecated
	 */
	public MainMenu(EventPanel p, BoardPanel b){
		//TODO finish all other template IO files before you finish this
		super(p);
		gameVars = p.getGlobalVars();
		gameDice = gameVars.getGameDice();
		
	}
	
	public void forceWait(AbstractEvent ae){
		parent.paintEvent(ae);
		sync(ae);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Current player = " + currentPlayer.getName());
		currentPlayer = gameVars.currentPlayer();
		//updateText();
		//text = 
		System.out.println("Current player has changed to: "+currentPlayer.getName());
		//System.out.println("e has been found in action performed");
		if(e.getSource() instanceof JButton){
			JButton me = (JButton)e.getSource();
			
			if(me.equals(buttons[0])){
				//System.out.println("Roll button pressed");
				if(currentPlayer.isInJail()){
					//System.out.println("This dude is locked up");
					AbstractEvent jailTime = new InJailEvent(parent, gameVars, currentPlayer);
					parent.paintEvent(jailTime);
					sync(jailTime);
					//updateTurn();
					currentPlayer = gameVars.currentPlayer();
					
				}else{
					
					int roll = gameDice.diceRoll();
					int dice1 = gameDice.getLastDice1();
					int dice2 = gameDice.getLastDice2();
					
					crossGo(currentPlayer, roll);
					
					AbstractEvent event = moveAndDo(roll);
					//System.out.println("now painting the event to the frame");
					parent.paintEvent(event);
					//System.out.println("syncing the event");
					if(dice1 != dice2){
						updateTurn();
					}
					sync(event);
					//System.out.println("and im out bois");
					
					
					
				}
			}else if(me.equals(buttons[1])){
				
			}else if(me.equals(buttons[2])){
				TradeManager tm = new TradeManager(gameVars, currentPlayer, gameVars.getPlayers());
				tm.runManager();
			}else if(me.equals(buttons[3])){
				MortgageManager mm = new MortgageManager(gameVars, currentPlayer);
				mm.beginManager();
			}
		}
		//parent.paintEvent(this);
		currentPlayer = gameVars.currentPlayer();
		//updateText();
	}
	
	/* (non-Javadoc)
	 * @see main.java.action.AbstractEvent#defineComponents()
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
	
	
}

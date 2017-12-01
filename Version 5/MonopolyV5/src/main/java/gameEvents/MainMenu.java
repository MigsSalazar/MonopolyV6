/**
 * 
 */
package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.gui.EventPanel;

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
		gameVars = p.getGlobalVars();
		board = gameVars.getFrame().getGameBoard();
		gameDice = gameVars.getGameDice();
		currentPlayer = gameVars.currentPlayer();
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
				if(currentPlayer.isInJail()){
					//TODO make jail event to see if player will get out of jail
				}else{
					//TODO have the dice displayed somewhere on the board so the players can see the dice rolls
					
					int roll = gameDice.diceRoll();
					int dice1 = gameDice.getLastDice1();
					int dice2 = gameDice.getLastDice2();
					
					crossGo(currentPlayer, roll);
					
					moveAndDo(roll);
					
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

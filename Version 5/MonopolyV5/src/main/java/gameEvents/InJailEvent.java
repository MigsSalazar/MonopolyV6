package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JButton;

import main.java.action.Roll;
import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Player;

public class InJailEvent extends DiceNeededEvent {

	private boolean lastDay;
	
	public InJailEvent(EventPanel p, Runner gv, Player pl) {
		super(p);
		gameVars = gv;
		currentPlayer = pl;
		if(pl.getJailCount() == 3){
			lastDay = true;
			text = "This is your last day in jail. Would you like to"
					+ "\nrole for freedom or pay $50 bail?";
		}else{
			lastDay = false;
			text = "You are in jail. Day: "+pl.getJailCount()+". Would you like to"
					+ "\nrole for freedom or pay $50 bail?";
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Roll gameDice = gameVars.getGameDice();
		gameDice.diceRoll();
		int dice1 = gameDice.getLastDice1();
		int dice2 = gameDice.getLastDice2();
		String t = "Dice: d1="+dice1+"    d2="+dice2;
		AbstractEvent event;
		if(e.getSource().equals(buttons[0])){
			if(dice1==dice2){
				t += "\nYou have rolled doubles and are now free to go!"
					+ "\nYou will be moved the distance given by the dice roll"
					+ "\nbut you will not roll again.";
				releasePrisoner();
				int roll = dice1 + dice2;
				
				moveAndDo(roll);
				updateTurn();
				event = new MessageEvent(parent, t);
				sync(event);
				desync();
			}else{
				if(lastDay){
					releasePrisoner();
					t +="\nYou failed your role but you must leave."
						+ "\nNow taking $50 bail from your account."
						+ "\nYou will be moved equal to your dice roll";
					event = new PlayervBankEvent(parent, t, currentPlayer, (-50));
					sync(event);
					moveAndDo(gameDice.getLastRoll());
					updateTurn();
					desync();
				}else{
					currentPlayer.spendANightInJail();
					t = "You failed your role but you may"
					+ "\nstay another "+(3-currentPlayer.getJailCount() +"night(s)");
					event = new MessageEvent(parent, t);
					sync(event);
					moveAndDo(gameDice.getLastRoll());
					updateTurn();
					desync();
				}
			}
			
		}else if(e.getSource().equals(buttons[1])){
			t = "You've paid the $50 bail!"
			+ "\nFreedom is yours!"
			+ "\nWe've rolled for you"
			+ "\n"+t+""
			+ "\nYou will be moved "+gameDice.getLastRoll()+" spaces";
			event = new PlayervBankEvent(parent, t, currentPlayer, (-50));
			sync(event);
			releasePrisoner();
			moveAndDo(gameDice.getLastRoll());
			updateTurn();
			desync();
		}
	}

	private void releasePrisoner() {
		currentPlayer.toggelInJail();
		currentPlayer.resetJailCount();
	}

	

	@Override
	public void defineComponents() {
		buttons = new JComponent[2];
		buttons[0] = new JButton("Role");
		((JButton)buttons[0]).addActionListener(this);
		
		buttons[1] = new JButton("Bail");
		((JButton)buttons[1]).addActionListener(this);
	}

}

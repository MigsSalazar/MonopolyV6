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
		gameDice = gameVars.getGameDice();
		if(pl.getJailCount() < 3){
			lastDay = false;
			text = "<html>You are in jail. Day: "+pl.getJailCount()+". Would you like to"
					+ "<br>role for freedom or pay $50 bail?</html>";
		}else{
			lastDay = true;
			text = "<html>This is your last day in jail. Would you like to"
					+ "<br>role for freedom or pay $50 bail?</html>";
		}
		defineComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Roll gameDice = gameVars.getGameDice();
		gameDice.diceRoll();
		int dice1 = gameDice.getLastDice1();
		int dice2 = gameDice.getLastDice2();
		String t;
		AbstractEvent event;
		if(e.getSource().equals(buttons[0])){
			if(dice1==dice2){
				t = "<html>You have rolled doubles and are now free to go!"
					+ "<br>Roll again to move.</html>";
				releasePrisoner();
				
				//int roll = dice1 + dice2;
				event = new MessageEvent(parent, t);
				parent.paintEvent(event);
				sync(event);
				desync();
				
				//event = moveAndDo(roll);
				//updateTurn();
				//event = new MessageEvent(parent, t);
				//parent.paintEvent(event);
				//sync(event);
				
			}else{
				if(lastDay){
					releasePrisoner();
					t = "<html>You failed your role but you must leave."
						+ "<br>Now taking $50 bail from your account.</html>";
					event = new PlayervBankEvent(parent, t, currentPlayer, (-50));
					releasePrisoner();
					parent.paintEvent(event);
					sync(event);
					desync();
					//parent.paintEvent(event);
					//holdIt(event);
					
					
					//event = moveAndDo(gameDice.getLastRoll());
					//parent.paintEvent(event);
					
					//updateTurn();
					
				}else{
					System.out.println("roll failed at InJailEvent");
					currentPlayer.spendANightInJail();
					System.out.println("current player = " + currentPlayer.getName());
					t = "<html>You failed your role but you may"
					+ "<br>stay another "+(4-currentPlayer.getJailCount() +" night(s)</html>");
					event = new MessageEvent(parent, t);
					updateTurn();
					parent.paintEvent(event);
					
					sync(event);
					//updateTurn();
					System.out.println("Next player = "+currentPlayer.getName());
					desync();
					//parent.jumpStartClean();
					//moveAndDo(gameDice.getLastRoll());
					//updateTurn();
					
				}
			}
			
		}else if(e.getSource().equals(buttons[1])){
			t = "<html>You've paid the $50 bail!"
			+ "<br>Freedom is yours!"
			+ "<br>Please roll to move.</html>";
			event = new PlayervBankEvent(parent, t, currentPlayer, (-50));
			releasePrisoner();
			parent.paintEvent(event);
			//holdIt(event);
			
			
			//event = moveAndDo(gameDice.getLastRoll());
			//parent.paintEvent(event);
			sync(event);
			//updateTurn();
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
	
	/*
	private void holdIt(AbstractEvent ae){
		try {
			synchronized(ae){
				System.out.println("Current sync trace: "+ae.toString());
				//boolean flag = true;
				parent.paintEvent(ae);
				ae.wait();
				System.out.println("Im still waiting");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/

}

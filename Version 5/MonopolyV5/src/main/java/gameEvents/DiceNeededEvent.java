package main.java.gameEvents;

import main.java.action.Roll;
import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

public abstract class DiceNeededEvent extends AbstractEvent {

	protected Player currentPlayer;
	protected Runner gameVars;
	protected Roll gameDice;

	public DiceNeededEvent(EventPanel p) {
		super(p);
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
		case 3:currentPlayer.setPosition(40);
				gameVars.jailPlayer(currentPlayer.getName());
				currentPlayer.setInJail(true);
				currentPlayer.spendANightInJail();
				event = new MessageEvent(parent, currentPlayer.getName()+", you must go to jail!\nDo not pass Go, do not collect $200!");
				sync(event);
			break;
		case 4:	event = new IncomeTaxEvent(parent, p);
				sync(event);
			break;
		case 5:event = new PlayervBankEvent(parent, currentPlayer.getName()+", you must pay the $75 luxury tax!", p, -75);
				sync(event);
			break;
		case 6:	//TODO make a community chest event
			break;
		case 7:	//TODO make a chance event
			break;
		case 8:Property passMe = gameDice.findPropPosition(currentPlayer.getPosition());
				event = new PropertyEvent(parent, p, passMe);
				sync(event);
			break;
		}
	}
	
	protected void crossGo(Player p, int roll) {
		if( (currentPlayer.getPosition() + roll) >= 40 || (currentPlayer.getPosition() + roll)==0){
			String text = currentPlayer.getName()+" has landed or passed go. Collect $200.";
			PlayervBankEvent pbe = new PlayervBankEvent(parent, text, p, 200);
			sync(pbe);
		}
	}
	
	protected void updateTurn(){
		gameVars.cyclePlayer();
		currentPlayer = gameVars.currentPlayer();
		text = currentPlayer.getName()+"'s turn.\nWhat would you like to do?";
		parent.paintEvent(this);
	}
	
	protected void moveAndDo(int roll) {
		currentPlayer.movePlayer(roll);
		gameVars.movePlayer(currentPlayer.getName(), roll);
		int result = gameDice.findAction(currentPlayer.getPosition());
		
		actionDone(currentPlayer, result);
	}
	
}

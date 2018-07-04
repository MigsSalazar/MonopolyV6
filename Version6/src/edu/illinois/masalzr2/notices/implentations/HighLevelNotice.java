package edu.illinois.masalzr2.notices.implentations;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;
import edu.illinois.masalzr2.models.Dice;

public abstract class HighLevelNotice extends AbstractNotice {
	protected Player currentPlayer;
	protected GameVariables gameVars;
	protected Dice gameDice;
	private String currency;

	public HighLevelNotice(ListListener ppl, GameVariables gv) {
		super(ppl);
		gameVars = gv;
		currentPlayer = gameVars.getCurrentPlayer();
		gameDice = (Dice)gameVars.getVariable("dice");
		currency = (String)gameVars.getVariable("currency");
	}
	
	
	protected AbstractNotice actionDone(Player p, int result) {
		AbstractNotice event;
		switch(result){
		case 0: event = new GoNotice(listener, currentPlayer);
				break;
		case 1:event = new MessageNotice( "<html>You are passing through jail.<br>Say hi to the jailbirds!</html>", listener);
			break;
		case 2:event = new MessageNotice("Free Parking! Nothing happens", listener);
			break;
		case 3:	gameVars.jailPlayer(p);
				//p.setPosition(10);
				event = new MessageNotice("<html>"+p.getName()+", you must go to jail!"
											+ "<br>Do not pass Go, do not collect "+currency+"200!</html>",
											listener);
			break;
		case 4:	event = new IncomeTaxNotice(listener, currency, p);
			break;
		case 5: event = new PlayerBankNotice("<html>"+p.getName()+", you must pay the "
											+currency+"75 luxury tax!</html>", 
											listener, 
											p, 
											-75);
			break;
		case 6:	//event = new CardNotice(listener, p, false);
				event = new GameCardNotice(listener, gameVars, p, false);
			break;
		case 7: //event = new CardNotice(listener, p, true);
				event = new GameCardNotice(listener, gameVars, p, true);
			break;
		case 8: //System.out.println("p position = "+ p.getPosition());
				Property passMe = gameVars.getPropertyAt(p.getPosition());
				event = new PropertyNotice(listener, gameVars, p, passMe);
			break;
		default: event = new MessageNotice("<html>Something went wrong. Defaulted</html>", listener);
			break;
		}
		return event;
		/*
		listener.paintNotice(event);
		System.out.println("Syncing event: "+event);
		sync(event);
		System.out.println("Sync is over");
		//listener.paintNotice(this);
		 * 
		 */
	}
	
	protected void crossGo(Player p, int roll) {
		if( (p.getPosition() + roll) >= 40 || (p.getPosition() + roll)==0){
			GoNotice pbe = new GoNotice(listener, currentPlayer);
			LOG.newEntry(this.getClass().getName() + ": crossGo: pushing self");
			listener.pushMe(new ListEvent(pbe));
		}
	}
	
	protected void updateTurn(){
		gameVars.nextTurn();
		currentPlayer = gameVars.getCurrentPlayer();
		text = currentPlayer.getName()+"'s turn.\nWhat would you like to do?";
	}
	
	protected AbstractNotice moveAndDo(int roll) {
		gameVars.fancyPlayerMove(currentPlayer, roll);
		currentPlayer.addPosition(roll);
		int result = findAction(currentPlayer.getPosition());
		//System.out.println("current Player Name: "+currentPlayer.getName());
		return actionDone(currentPlayer, result);
	}
	
	protected AbstractNotice moveAndDo(Player player, int roll) {
		System.out.println("player move and do roll: "+roll);
		
		gameVars.fancyPlayerMove(player, roll);
		player.addPosition(roll);
		int result = findAction(player.getPosition());
		System.out.println("current Player Name: "+currentPlayer.getName() + " result: "+result);
		return actionDone(player, result);
	}
	
	public static int findAction(int position){
		
		if(position <= 0){
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
}

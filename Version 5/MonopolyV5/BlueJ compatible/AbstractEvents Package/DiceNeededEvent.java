 
public abstract class DiceNeededEvent extends AbstractEvent {

	protected Player currentPlayer;
	protected Runner gameVars;
	protected Roll gameDice;

	public DiceNeededEvent(EventPanel p) {
		super(p);
	}
	
	
	private AbstractEvent actionDone(Player p, int result) {
		AbstractEvent event;
		switch(result){
		case 0: event = new GoEvent(parent, currentPlayer, this);
				break;
		case 1:event = new MessageEvent(parent, "<html>You are passing through jail.<br>Say hi to the jailbirds!</html>");
			break;
		case 2:event = new MessageEvent(parent,"Free Parking! Nothing happens");
			break;
		case 3:	gameVars.jailPlayer(p);
				//p.setPosition(10);
				event = new MessageEvent(parent, "<html>"+p.getName()+", you must go to jail!<br>Do not pass Go, do not collect "+parent.getCurrencySymbol()+"200!</html>");
			break;
		case 4:	event = new IncomeTaxEvent(parent, p);
			break;
		case 5: event = new PlayervBankEvent(parent, "<html>"+p.getName()+", you must pay the "+parent.getCurrencySymbol()+"75 luxury tax!</html>", p, -75);
			break;
		case 6:	event = new CardEvent(parent, p, false);
			break;
		case 7: event = new CardEvent(parent, p, true);
			break;
		case 8: //System.out.println("p position = "+ p.getPosition());
				Property passMe = gameDice.findPropPosition(p.getPosition());
				event = new PropertyEvent(parent, gameVars, p, passMe);
			break;
		default: event = new MessageEvent(parent, "<html>Something went wrong. Defaulted</html>");
			break;
		}
		return event;
		/*
		parent.paintEvent(event);
		System.out.println("Syncing event: "+event);
		sync(event);
		System.out.println("Sync is over");
		//parent.paintEvent(this);
		 * 
		 */
	}
	
	protected void crossGo(Player p, int roll) {
		if( (p.getPosition() + roll) >= 40 || (p.getPosition() + roll)==0){
			GoEvent pbe = new GoEvent(parent, currentPlayer, this);
			sync(pbe);
		}
	}
	
	protected void updateTurn(){
		//System.out.println("UpdateTurn: called with current player = " + currentPlayer.getName());
		gameVars.cyclePlayer();
		currentPlayer = gameVars.currentPlayer();
		//System.out.println("UpdateTurn: New current player has been set to "+ currentPlayer.getName());
		text = currentPlayer.getName()+"'s turn.\nWhat would you like to do?";
		//System.out.println(text);
		//parent.paintEvent(this);
	}
	
	protected AbstractEvent moveAndDo(int roll) {
		currentPlayer.movePlayer(roll);
		gameVars.movePlayer(currentPlayer, roll);
		int result = findAction(currentPlayer.getPosition());
		//System.out.println("current Player Name: "+currentPlayer.getName());
		return actionDone(currentPlayer, result);
	}
	
	protected AbstractEvent moveAndDo(Player player, int roll) {
		//System.out.println("player move and do roll: "+roll);
		player.movePlayer(roll);
		gameVars.movePlayer(player, roll);
		int result = findAction(player.getPosition());
		//System.out.println("current Player Name: "+currentPlayer.getName());
		return actionDone(player, result);
	}
	
	public static int findAction(int position){
		
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
	
}

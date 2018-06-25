package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.GameCard;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.models.Street;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;

public class GameCardNotice extends HighLevelNotice {

	private GameCard card;
	private Player player;
	
	private ArrayList<Player> players;
	private HashMap<String, Property> properties;
	
	@SuppressWarnings("unchecked")
	public GameCardNotice(ListListener ppl, GameVariables gv, Player pl, boolean chance) {
		super(ppl, gv);
		
		players = new ArrayList<Player>( ((HashMap<String,Player>)gameVars.getVariable("players")).values() );
		properties = (HashMap<String,Property>)gameVars.getVariable("properties");
		
		card = cardPicker(chance);
		text = "<html>You landed on "+(chance?"Chance":"Community Chest!")+". Your card reads:"
				+card.getText().substring(6,card.getText().length());
		player = pl;
		defineActions();
	}

	public String getText(){
		return text;
	}
	public void setText(String t){
		text = t;
	}
	
	public GameCard cardPicker(boolean chance){
		GameCard card = null;
		if(chance){
			card = gameVars.getRandomChance();
		}else{
			card = gameVars.getRandomCommChest();
		}
		
		return card;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(actions[0])){
			runCard(card);
			listener.popMe(new ListEvent(this));
		}
	}

	@Override
	protected void defineActions() {
		actions = new JComponent[1];
		actions[0] = new JButton("Ok");
		((JButton)actions[0]).addActionListener(this);
	}
	
	private void runCard(GameCard gc){
		moneyChange(gc);
		movePlayer(gc);
		giveJailCard(gc);
		sendToJail(gc);
		findNearestOnBoard(gc);
		findThisOnBoard(gc);
		renovateProperties(gc);
	}
	
	private void moneyChange( GameCard gc){
		AbstractNotice an = null;
		if(gc.isGlobalFunds()){
			an = new PlayerPlayerNotice("<html>Player transaction complete<br>Over seen by the bank.<html>",
											listener, player, players, gc.getMoneyEarned());
		}else{
			an = new PlayerBankNotice("<html>Bank transaction complete.</html>",
										listener, player, gc.getMoneyEarned());
		}
		//NoticePanel p, String message, Player p1, ArrayList<Player> plays, int cost
		noticePushPop(an);
		
	}
	
	private void movePlayer(GameCard gc){
		if(gc.getBaseMovement() != 0){
			//gameVars.movePlayer(player, gc.getBaseMovement());
			//player.movePlayer(gc.getBaseMovement());
			AbstractNotice an = moveAndDo(player, gc.getBaseMovement());
			noticePushPop(an);
		}
	}
	
	private void giveJailCard(GameCard gc){
		if(gc.isGetOutOfJail()){
			player.addOneJailCard();
			MessageNotice an = new MessageNotice("Total Jail Cards: "+player.getJailCard(), listener);
			noticePushPop(an);
		}
	}
	
	private void sendToJail(GameCard gc){
		if(gc.isGoToJail()){
			gameVars.jailPlayer(player);
			MessageNotice an = new MessageNotice("You have been sent to Jail!", listener);
			noticePushPop(an);
		}
	}
	
	private void findNearestOnBoard(GameCard gc){
		AbstractNotice an = null;
		switch(gc.getFindNearest()){
		case "railroad":an = findRailroad(player);
			break;
		case "utility": an = findUtility(player);
			break;
		case "": return;
		default: return;
		}
		if(an == null){
			System.out.println("FindNearestOnBoard returned null");
			return;
		}
		noticePushPop(an);
	}
	
	private AbstractNotice findUtility(Player player){
		int moveBy = 0;
		
		if(player.getPosition() < 12 || player.getPosition() > 28){
			if(player.getPosition() < 12){
				moveBy = 12 - player.getPosition();
			}else{
				moveBy = 52 - player.getPosition();
			}
		}else{
			moveBy = 28 - player.getPosition();
		}
		
		AbstractNotice event = moveAndDo(player, moveBy);
		return event;
	}

	private AbstractNotice findRailroad(Player player) {
		int moveBy = 0;
		if(player.getPosition() > 35 || player.getPosition() < 5){
			if(player.getPosition() < 5){
				moveBy = 5 - player.getPosition();
			}else{
				moveBy = 45 - player.getPosition();
				
			}
		}else if(player.getPosition() > 5 && player.getPosition() < 15){
			moveBy = 15 - player.getPosition();
		}else if(player.getPosition() > 15 && player.getPosition() < 25){
			moveBy = 25 - player.getPosition();
		}else if(player.getPosition() > 25 && player.getPosition() < 35){
			moveBy = 35 - player.getPosition();
		}
		AbstractNotice event = moveAndDo(player, moveBy);
		return event;
	}
	
	private void findThisOnBoard(GameCard gc){
		AbstractNotice an = null;
		int moveBy = 0;
		switch(gc.getFindThis()){
		case "": return;
		case "go":	moveBy = 40 - player.getPosition();
					an = moveAndDo(player, moveBy);
					break;
		default:if(properties.containsKey(gc.getFindThis())){
					Property prop = properties.get(gc.getFindThis());
					if(player.getPosition() > prop.getPosition()){
						moveBy = (40+prop.getPosition()) - player.getPosition();
					}else{
						moveBy = prop.getPosition() - player.getPosition();
					}
					an = moveAndDo(player,moveBy);
				}
		}
		if(an == null){
			return;
		}
		noticePushPop(an);
	}
	
	private void renovateProperties(GameCard gc){
		if(gc.isPropRenovation()){
			HashMap<String, Property> props = player.getProps();
			int total = 0;
			int houses = 0;
			int housenum = 0;
			int hotels = 0;
			int hotelnum = 0;
			for(String s : props.keySet()){
				if(props.get(s) instanceof Street){
					if(((Street)props.get(s)).getGrade() == 5){
						hotels += gc.getHotelCost();
						total += hotels;
						hotelnum++;
					}else if(((Street)props.get(s)).getGrade() > 0){
						houses += gc.getHouseCost() * ((Street)props.get(s)).getGrade();
						total += houses;
						housenum++;
					}
				}
			}
			String currency = (String)gameVars.getVariable("currency");
			String textOut = "<html>Renovation Costs:"
							+ "<br>Hotels: "+hotelnum+"    Cost: "+currency+hotels
							+ "<br>Houses: "+housenum+"    Cost: "+currency+houses
							+ "<br>Complete Total:          "+currency+total+"</html>";
			PlayerBankNotice an = new PlayerBankNotice( textOut, listener, player, (total * -1) );
			//player.subCash(total);
			noticePushPop(an);
		}
	}
	
}

package main.java.gameEvents;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Colored;
import main.java.models.Dice;
import main.java.models.GameCard;
import main.java.models.Player;
import main.java.models.Property;

public class CardEvent extends DiceNeededEvent{

	GameCard card;
	
	public CardEvent(EventPanel p, Player pl, boolean chance) {
		super(p);
		gameVars = p.getGlobalVars();
		gameDice = gameVars.getGameDice();
		card = cardPicker(chance);
		text = card.getText();
		currentPlayer = pl;
		defineComponents();
	}

	@Override
	public void defineComponents() {
		buttons = new JComponent[1];
		buttons[0] = new JButton("Ok");
		((JButton)buttons[0]).addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttons[0])){
			runCard(card);
			desync();
			parent.jumpStartClean();
		}

	}
	
	
	public String getText(){
		return text;
	}
	public void setText(String t){
		text = t;
	}
	
	public GameCard cardPicker(boolean chance){
		ArrayList<GameCard> deck = null;
		if(chance){
			deck = gameVars.getChance();
		}else{
			deck = gameVars.getCommChest();
		}
		
		Dice rando = new Dice(1, deck.size()-1);
		
		return deck.get(rando.roll());
		
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
		if(gc.isGlobalFunds()){
			Map<String, Player> plays = gameVars.getPlayers();
			for(String s : gameVars.getPlayerNames()){
				if(!plays.get(s).equals(currentPlayer)){
					plays.get(s).subCash(gc.getMoneyEarned());
				}
			}
		}
		
		currentPlayer.addCash(gc.getMoneyEarned());
		
	}
	
	private void movePlayer(GameCard gc){
		if(gc.getBaseMovement() != 0){
			gameVars.movePlayer(currentPlayer, gc.getBaseMovement());
			currentPlayer.movePlayer(gc.getBaseMovement());
			AbstractEvent caseOf = moveAndDo(currentPlayer, gc.getBaseMovement());
			sync(caseOf);
		}
	}
	
	private void giveJailCard(GameCard gc){
		if(gc.isGetOutOfJail()){
			currentPlayer.addJailCard();
		}
	}
	
	private void sendToJail(GameCard gc){
		if(gc.isGoToJail()){
			currentPlayer.setInJail(true);
			Runner.jailPlayer(currentPlayer);
		}
	}
	
	private void findNearestOnBoard(GameCard gc){
		AbstractEvent event = null;
		switch(gc.getFindNearest()){
		case "railroad":event = findRailroad(currentPlayer);
			break;
		case "utility": event = findUtility(currentPlayer);
			break;
		case "": return;
		default: return;
		}
		if(event == null){
			System.out.println("FindNearestOnBoard returned null");
			return;
		}
		sync(event);
	}
	
	private AbstractEvent findUtility(Player player){
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
		
		AbstractEvent event = moveAndDo(player, moveBy);
		return event;
	}

	private AbstractEvent findRailroad(Player player) {
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
		AbstractEvent event = moveAndDo(player, moveBy);
		return event;
	}
	
	private void findThisOnBoard(GameCard gc){
		AbstractEvent event = null;
		int moveBy = 0;
		switch(gc.getFindThis()){
		case "": return;
		case "go":	moveBy = 40 - currentPlayer.getPosition();
					event = moveAndDo(currentPlayer, moveBy);
					break;
		default:if(gameVars.getProperties().containsKey(gc.getFindThis())){
					Property prop = gameVars.getProperties().get(gc.getFindThis());
					if(currentPlayer.getPosition() > prop.getPosition()){
						moveBy = (40+prop.getPosition()) - currentPlayer.getPosition();
					}else{
						moveBy = prop.getPosition() - currentPlayer.getPosition();
					}
					event = moveAndDo(currentPlayer,moveBy);
				}
		}
		if(event == null){
			return;
		}
		sync(event);
	}
	
	private void renovateProperties(GameCard gc){
		if(gc.isPropRenovation()){
			Map<String, Property> props = currentPlayer.getProps();
			int total = 0;
			for(String s : props.keySet()){
				if(props.get(s) instanceof Colored){
					if(((Colored)props.get(s)).getGrade() == 5){
						total += gc.getHotelCost();
					}else if(((Colored)props.get(s)).getGrade() > 0){
						total += gc.getHouseCost();
					}
				}
			}
			currentPlayer.subCash(total);
		}
	}

	
	

}

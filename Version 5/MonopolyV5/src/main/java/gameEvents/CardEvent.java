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
	Player play;
	
	public CardEvent(EventPanel p, Player pl, boolean chance) {
		super(p);
		card = cardPicker(chance);
		text = card.getText();
		play = pl;
		defineComponents();
	}

	@Override
	public void defineComponents() {
		// TODO Auto-generated method stub
		buttons = new JComponent[1];
		buttons[0] = new JButton("Ok");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttons[0])){
			runCard(parent.getGlobalVars(), card, play);
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
			deck = parent.getGlobalVars().getChance();
		}else{
			deck = parent.getGlobalVars().getCommChest();
		}
		
		Dice rando = new Dice(1, deck.size());
		
		return deck.get(rando.roll());
		
	}
	
	private void runCard(Runner gv, GameCard gc, Player player){
		moneyChange(gv, gc, player);
		movePlayer(gv, gc, player);
		giveJailCard(gc, player);
		sendToJail(gv, gc, player);
		findNearestOnBoard(gv, gc, player);
		findThisOnBoard(gv, gc, player);
		renovateProperties(gc, player);
	}
	
	private void moneyChange(Runner gv, GameCard gc, Player player){
		if(gc.isGlobalFunds()){
			Map<String, Player> plays = gv.getPlayers();
			for(String s : gv.getPlayerNames()){
				if(!plays.get(s).equals(player)){
					plays.get(s).subCash(gc.getMoneyEarned());
				}
			}
		}
		
		player.addCash(gc.getMoneyEarned());
		
	}
	
	private void movePlayer(Runner gv, GameCard gc, Player player){
		if(gc.getBaseMovement() != 0){
			gv.movePlayer(player, gc.getBaseMovement());
			player.movePlayer(gc.getBaseMovement());
			AbstractEvent caseOf = moveAndDo(player, gc.getBaseMovement());
			sync(caseOf);
		}
	}
	
	private void giveJailCard(GameCard gc, Player player){
		if(gc.isGetOutOfJail()){
			player.addJailCard();
		}
	}
	
	private void sendToJail(Runner gv, GameCard gc, Player player){
		if(gc.isGoToJail()){
			player.setInJail(true);
			Runner.jailPlayer(player);
		}
	}
	
	private void findNearestOnBoard(Runner gv, GameCard gc, Player player){
		switch(gc.getFindNearest()){
		case "railroad":if(player.getPosition() > 35 || player.getPosition() < 5){
							
						}else if(player.getPosition() > 5 && player.getPosition() < 15){
							
						}else if(player.getPosition() > 15 && player.getPosition() < 25){
							
						}else if(player.getPosition() > 25 && player.getPosition() < 35){
							
						}
			break;
		case "utility":
			break;
		case "chance":
			break;
		case "commchest":
			break;
		case "":
			break;
		}
	}
	
	private void findThisOnBoard(Runner gv, GameCard gc, Player player){
		switch(gc.getFindThis()){
		case "": return;
		case "go":
			break;
		case "freeparking":
			break;
		case "chance":
			break;
		case "commchest":
			break;
		default:
			break;
		}
	}
	
	private void renovateProperties(GameCard gc, Player player){
		if(gc.isPropRenovation()){
			Map<String, Property> props = player.getProps();
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
			player.subCash(total);
		}
	}

	
	

}

package main.java.action;

import main.java.models.Dice;
import main.java.models.Player;

public class Roll {
	private Dice gameDice = new Dice(2,6);
	
	public int diceRoll(){
		return gameDice.roll();
	}
	
	public void rollPlayer(Player p){
		
	}
	
}

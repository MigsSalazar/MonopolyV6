package main.java.action;

import main.java.models.Dice;
import main.java.models.Player;

public class Roll {
	private Dice gameDice = new Dice(2,6);
	private Runner globalVars;
	
	public Roll(Runner gv){
		globalVars = gv;
	}
	
	public int diceRoll(){
		return gameDice.roll();
	}
	
	public void rollPlayer(Player p){
		int dist = diceRoll();
		
		
		
	}
	
}

package main.java.action;

import java.util.Set;

import main.java.models.Dice;
import main.java.models.Property;

public class Roll {
	private Dice gameDice = new Dice(1,6);
	private int roll1;
	private int roll2;
	private int rollSum;
	private Runner globalVars;
	
	public Roll(Runner gv){
		globalVars = gv;
		
	}
	
	public int diceRoll(){
		roll1 = gameDice.roll();
		roll2 = gameDice.roll();
		rollSum = roll1 + roll2;
		return rollSum;
	}
	
	public int getLastRoll(){
		return rollSum;
	}
	
	public int getLastDice1(){
		return roll1;
	}
	
	public int getLastDice2(){
		return roll2;
	}

	

	public Property findPropPosition(int pos){
		Set<String> names = globalVars.getPropName();
		
		Property prop;
		for(String s : names){
			prop = globalVars.getProperties().get(s);
			if(pos == prop.getPosition()){
				return prop;
			}
		}
		return null;
	}
	
}

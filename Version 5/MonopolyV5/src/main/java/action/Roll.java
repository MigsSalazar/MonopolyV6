package main.java.action;

import java.util.Map;
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
		globalVars.changeDice(roll1, roll2);
		return rollSum;
	}
	
	public int diceRollHalf(){
		roll1 = gameDice.roll();
		globalVars.changeDice(roll1);
		return roll1;
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
		//System.out.println("Position at int: "+pos);
		Property prop;
		Map<String,Property> propList = globalVars.getProperties();
		for(String s : names){
			prop = propList.get(s);
			//System.out.println(prop.getName() + " lies at position " + prop.getPosition());
			if(pos == prop.getPosition()){
				return prop;
			}
		}
		return null;
	}
	
}

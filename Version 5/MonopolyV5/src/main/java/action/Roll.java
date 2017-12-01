package main.java.action;

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

	public int findAction(int position){
		
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

	public Property findPropPosition(int pos){
		String[] names = globalVars.getPropName();
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

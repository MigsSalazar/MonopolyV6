package edu.illinois.masalzr2.models;

public class Utility extends Property {
	private Counter utilityOwned;
	private Dice gameDice;
	
	@Override
	public int getRent(){
		return ( 4 + 6*(utilityOwned.getCount()-1) ) * gameDice.getLastRoll();
	}
	
	public void setDice(Dice d){
		gameDice = d;
	}
	
	
}

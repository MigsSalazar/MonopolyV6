 

import java.util.Random;

import com.google.gson.annotations.Expose;

public class Dice {
	@Expose private Random rando = new Random();
	@Expose private int diceNum;
	@Expose private int sides;
	
	public Dice(){
		diceNum = 1;
		sides = 6;
	}
	
	public Dice(int d){
		diceNum = d;
		sides = 6;
	}
	
	public Dice(int d, int s){
		diceNum = d;
		sides = s;
	}
	
	public int getDiceNum(){
		return diceNum;
	}
	
	public void setDiceNum(int d){
		diceNum = d;
	}
	
	public int getNumOfSides(){
		return sides;
	}
	
	public void setNumOfSides(int s){
		sides = s;
	}
	
	public int roll(){
		int sum = 0;
		for(int i=0; i<diceNum; i++){
			sum += (rando.nextInt(sides)+1) ;
		}
		return sum;
	}
	
	public int roll(int rolls){
		int sum = 0;
		for(int i=0; i<rolls; i++){
			sum += roll();
		}
		return sum;
	}
	
}

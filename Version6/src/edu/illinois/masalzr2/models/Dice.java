package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sides;
	private int[] dice;
	private int lastRoll;
	
	public Dice(){
		sides = 6;
		dice = new int[1];
		lastRoll = 0;
	}
	
	public Dice(int s){
		sides = s;
		dice = new int[1];
		lastRoll = 0;
	}
	
	public Dice(int s, int d){
		sides = s;
		dice = new int[d];
		lastRoll = 0;
	}
	
	public int roll(){
		Random rand = new Random();
		lastRoll = 0;
		for(int i=0; i<dice.length; i++){
			dice[i] = rand.nextInt(sides) + 1;
			lastRoll += dice[i];
		}
		return lastRoll;
	}
	
	public int getLastRoll(){
		return lastRoll;
	}
	
	public int[] getLastDice(){
		return dice;
	}
	
	public int diceNum(){
		return dice.length;
	}
	
	public int getSideNum(){
		return sides;
	}
	
	public class Coin extends Dice{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Coin(){
			super(2,1);
		}
	}
	
	public class Cube extends Dice{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Cube(){
			super(6,1);
		}
	}
	
	public class DoubleCube extends Dice{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DoubleCube(){
			super(6,2);
		}
	}
	
	public class TwentySided extends Dice{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TwentySided(){
			super(20,1);
		}
	}
	
	/**
	 * Unique sub-class of dice that generates percent values between 0%-99%
	 * If 100% is desired, it is best to consider 0% as 100% instead but leave
	 * any single digit percents as such forming the range 1%-100%
	 * @author Unknown
	 *
	 */
	public class Percentile extends Dice{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Percentile(){
			super(10, 2);
		}
		
		public int roll(){
			Random rand = new Random();
			
			lastRoll = 0;
			
			dice[0] = (rand.nextInt(sides))*10;
			
			dice[1] = rand.nextInt(sides);
			
			lastRoll = dice[0] + dice[1];
			
			return lastRoll;
		}
		
	}
	
}

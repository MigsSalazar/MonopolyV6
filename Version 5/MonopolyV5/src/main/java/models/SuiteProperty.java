package main.java.models;

public class SuiteProperty extends Property {

	
	private int suite;
	private int[] rent;
	private int grade;
	
	/**
	 * 
	 * @param n
	 * @param p
	 * @param pr
	 * @param o
	 * @param m
	 * @param mb
	 * @param s
	 * @param r
	 * @param g
	 */
	public SuiteProperty(String n, int p, int pr, int o, int m, boolean mb, int s, int[] r, int g) {
		super(n, p, pr, o, m, mb);
		// TODO Auto-generated constructor stub
		suite = s;
		rent = r;
		grade = g;
		
	}
	
	/**
	 * 
	 * @return	containing suite as an int value
	 */
	public int getSuite(){
		return suite;
	}
	
	
	/**
	 * 
	 * @return	rent owed determined by property grade
	 */
	public int getRent(){
		return rent[grade];
	}
	
	public int getGrade(){
		return grade;
	}
	
	public void setGrade(int g){
		grade = g;
	}
	
	public int getWorth(){
		return isMortgaged() ?
				getMortgageValue() : //if Property is mortgaged
					getPrice() + (int)( grade*rent[7]*0.5 ); //if property is not mortgaged
	}

}

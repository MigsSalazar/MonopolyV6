package edu.illinois.masalzr2.models;

public class Street extends Property {
	
	private int grade;
	private int[] rent;
	
	public Street(){
		grade 		= 0;
		rent 		= new int[5];
	}
	
	public void setGrade(int g ){
		grade = g;
	}
	
	public int getGrade(){
		return grade;
	}
	
	public int getRent(int num){
		return rent[num];
	}
	
	@Override
	public int getRent(){
		return rent[grade];
	}
	
	@Override
	public int getWorth(){
		return 0;
	}
	
	@Override
	public int getLiquidationWorth(){
		return 0;
	}
	
}

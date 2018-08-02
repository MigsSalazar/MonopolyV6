package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import com.google.gson.annotations.Expose;

public class Street extends Property implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose private int grade;
	@Expose private int upgradeCost;
	@Expose private int[] rent;
	
	public Street(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen, int g, int uc, int[] r){
		super(n, pos, pr, o, m, listen);
		grade = g;
		upgradeCost = uc;
		rent = r;
	}
	
	public Street(){
		grade 		= 0;
		rent 		= new int[5];
	}
	
	public int getUpgradeCost(){
		return upgradeCost;
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
		return super.getPrice() + (grade*upgradeCost);
	}
	
	@Override
	public int getLiquidationWorth(){
		return super.getLiquidationWorth() + (grade*upgradeCost);
	}
	
}

package edu.illinois.masalzr2.models;


import java.io.Serializable;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class GameCard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose private String text;
	
	@Expose private int moneyEarned;
	@Expose private boolean globalFunds;
	
	@Expose private int baseMovement;
	
	@Expose private boolean getOutOfJail;
	
	@Expose private boolean goToJail;
	
	@Expose private String findNearest;
	@Expose private String findThis;
	
	@Expose private boolean propRenovation;
	@Expose private int houseCost;
	@Expose private int hotelCost;
	
	public GameCard(String t,
					int me, boolean gf,
					int bm,
					boolean oj,
					boolean tj,
					String fn, String ft,
					boolean pr, int house, int hotel){
		text = t;
		moneyEarned = me;
		globalFunds = gf;
		baseMovement = bm;
		getOutOfJail = oj;
		goToJail = tj;
		findNearest = fn;
		findThis = ft;
		propRenovation = pr;
		houseCost = house;
		hotelCost = hotel;
	}

}

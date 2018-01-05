package main.java.models;


import com.google.gson.annotations.Expose;


public class GameCard {
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getMoneyEarned() {
		return moneyEarned;
	}

	public void setMoneyEarned(int moneyEarned) {
		this.moneyEarned = moneyEarned;
	}

	public boolean isGlobalFunds() {
		return globalFunds;
	}

	public void setGlobalFunds(boolean globalFunds) {
		this.globalFunds = globalFunds;
	}

	public int getBaseMovement() {
		return baseMovement;
	}

	public void setBaseMovement(int baseMovement) {
		this.baseMovement = baseMovement;
	}

	public boolean isGetOutOfJail() {
		return getOutOfJail;
	}

	public void setGetOutOfJail(boolean getOutOfJail) {
		this.getOutOfJail = getOutOfJail;
	}

	public boolean isGoToJail() {
		return goToJail;
	}

	public void setGoToJail(boolean goToJail) {
		this.goToJail = goToJail;
	}

	public String getFindNearest() {
		return findNearest;
	}

	public void setFindNearest(String findNearest) {
		this.findNearest = findNearest;
	}

	public String getFindThis() {
		return findThis;
	}

	public void setFindThis(String findThis) {
		this.findThis = findThis;
	}

	public boolean isPropRenovation() {
		return propRenovation;
	}

	public void setPropRenovation(boolean propRenovation) {
		this.propRenovation = propRenovation;
	}

	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}

	public int getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}

}

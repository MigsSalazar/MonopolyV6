package edu.illinois.masalzr2.models;

import java.util.Comparator;

import com.google.gson.annotations.Expose;

public class Property{

	@Expose protected String name;
	@Expose protected int position;
	@Expose protected int price;
	@Expose protected String owner = "";
	@Expose protected boolean mBool;
	
	/**
	 * Returns the name of the Property
	 * @return 		String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * The position of the Property determined by the distance away from GO going clockwise on the board
	 * @return 		int value between 0-39
	 */
	public int getPosition(){
		return position;
	}
	
	/**
	 * The price for a player to purchase this Property from the bank
	 * @return 		int value of cash price
	 */
	public int getPrice(){
		return price;
	}
	
	/**
	 * Value of the Property as it relates to taxable income.
	 * Differs from Liquidation Worth in the value goes to your net worth and towards the Property's value
	 * when selling or trading but does not necessarily mean the property can be liquidated further
	 * 
	 * @return		int - property value following the equation:
	 * 				worth = price - mortgage value * (mortgage boolean as 1 or 0)
	 */
	public int getWorth(){
		return isMortgaged() ? mortgageValue() : getPrice() ;
	}
	
	/**
	 * Value of this Property as it relates to liquidation value.
	 * Differs from the Property's Worth in that this value shows the total liquidatable value of the
	 * Property 
	 * @return 		int - value of the Property's liquidizable assets following the equation:
	 * 				liquidation worth = mortgage value * (mortgage boolean as a 1 or 0)
	 */
	public int getLiquidationWorth(){
		return isMortgaged() ? 0 : mortgageValue();
	}
	
	public void setOwner(String o){
		owner = o;
	}
	
	public String getOwner(){
		return owner;
	}
	
	public boolean isMortgaged(){
		return mBool;
	}
	
	public int mortgageValue(){
		return (price/2);
	}
	
	public int getRent(){
		return 0;
	}
	
	public int compareByPosition(Property other){
		SortByPosition sbp = new SortByPosition();
		return sbp.compare(this, other);
	}
	
	public int compareByName(Property other){
		SortByName sbn = new SortByName();
		return sbn.compare(this, other);
	}
	
	public static Comparator<Property> getPositionComparator(){
		return new SortByPosition();
	}
	
	public static Comparator<Property> getNameComparator(){
		return new SortByName();
	}
	
	private static class SortByPosition implements Comparator<Property>{
		public int compare(Property o0, Property o1) {
			return o0.getPosition() - o1.getPosition();
		}
	}
	
	private static class SortByName implements Comparator<Property>{
		public int compare(Property o0, Property o1) {
			return o0.getName().compareTo(o1.getName());
		}
	}
	
}

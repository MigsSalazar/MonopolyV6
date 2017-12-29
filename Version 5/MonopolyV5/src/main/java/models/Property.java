package main.java.models;

import com.google.gson.annotations.Expose;

public abstract class Property {
	
	@Expose protected String name;
	@Expose protected int position;
	@Expose protected int price;
	@Expose protected String owner = "";
	@Expose protected boolean mBool;
	
	/**
	 * Classic constructor for Properties
	 * @param n		name of the property as a string
	 * @param o		name of the player who owns this property
	 * @param p		position of the property on the board as an integer
	 * @param s		suite the property belongs to as an integer
	 * @param pr	price of the property as an integer
	 * @param o		Player object of the owner
	 * @param m		mortgage value saved as an integer
	 * @param mb	boolean stating if property is mortgaged: mortgaged = true, not mortgaged = false
	 */
	public Property(String n, String o, int p, int pr, boolean mb) {
		name = n;
		position = p;
		price = pr;
		owner = o;
		mBool = mb;
	}
	
	/**
	 * 
	 * @return	A copy of the name string
	 */
	public String getName(){
		return name==null ? "" : name;
	}
	
	/**
	 * 
	 * @return	position of the property on the board
	 */
	public int getPosition(){
		return position;
	}
	
	/**
	 * 
	 * @return	property value for the property if unowned
	 */
	public int getPrice(){
		return price;
	}
	
	/**
	 * 
	 * @return	Player object of the current owner. If unowned, owner is equal to null
	 */
	public String getOwner(){
		if(owner == null){
			return null;
		}
		return owner;
	}
	
	/**
	 * 
	 * @param o	integer ID of the owner to be
	 */
	public void setOwner(String o){
		owner = o;
	}
	
	/**
	 * 
	 * @return	Property mortgaged value. Price to unmortage property is derived from this value as well
	 */
	public int getMortgageValue(){
		return (int)(price/2);
	}
	
	/**
	 * 
	 * @return	boolean confirmation or property's mortgaged status
	 */
	public boolean isMortgaged(){
		return mBool;
	}
	
	/**
	 * 
	 * @param b	boolean value setting property's mortgage status
	 */
	public void setMortgage(boolean b){
		mBool = b;
	}
	
	/**
	 * Sets current Mortgage status to it's opposite
	 * @return	current mortgage status after toggled
	 */
	public boolean toggleMortgage(){
		setMortgage(!mBool);
		return mBool;
	}
	
	/**
	 * 
	 * @return		rent due
	 */
	public abstract int getRent();

	/**
	 * 
	 * @return	property value
	 */
	public int getWorth(){
		return isMortgaged() ? getMortgageValue() : getPrice() ;
	}
	
	public int getRedeemableWorth(){
		return isMortgaged() ? 0 : getMortgageValue();
	}
}

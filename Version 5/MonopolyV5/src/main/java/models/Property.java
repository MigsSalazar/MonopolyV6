package main.java.models;

public abstract class Property {
	
	private String name;
	private int position;
	private int price;
	private int owner;
	private int mortgage;
	private boolean mBool;
	
	/**
	 * 
	 * @param n		name of the property as a string
	 * @param p		position of the property on the board as an integer
	 * @param s		suite the property belongs to as an integer
	 * @param pr	price of the property as an integer
	 * @param o		ID of the owner as an integer
	 * @param m		mortgage value saved as an integer
	 * @param mb	boolean stating if property is mortgaged: mortgaged = true, not mortgaged = false
	 */
	public Property(String n, int p, int pr, int o, int m, boolean mb) {
		name = n;
		position = p;
		price = pr;
		o = owner;
		m = mortgage;
		mBool = mb;
	}
	
	/**
	 * 
	 * @return	A copy of the name string
	 */
	public String getName(){
		return name==null ? null : name.substring(0, name.length());
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
	 * @return	ID integer of the current owner. If unowned, owner is set to -1
	 */
	public int getOwner(){
		return owner;
	}
	
	/**
	 * 
	 * @param o	integer ID of the owner to be
	 */
	public void setOwner(int o){
		owner = o;
	}
	
	/**
	 * 
	 * @return	Property mortgaged value. Price to unmortage property is derived from this value as well
	 */
	public int getMortgageValue(){
		return mortgage;
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
	public abstract int getWorth();
}

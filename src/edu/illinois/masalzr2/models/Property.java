package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.gson.annotations.Expose;


public class Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose protected String name;
	@Expose protected int position;
	@Expose protected int price;
	@Expose protected String owner = "";
	@Expose protected boolean mBool;
	@Expose protected int color;
	private ArrayList<ChangeListener> listeners;
	
	public transient static final Comparator<Property> POSITION_ORDER = new SortByPosition();
	public transient static final Comparator<Property> NAME_ORDER = new SortByName();
	
	public Property(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen){
		name = n;
		position = pos;
		price = pr;
		owner = o;
		mBool = m;
		listeners = listen==null? new ArrayList<ChangeListener>() : listen;
	}

	public Property(){
		name = "";
		position = -1;
		price = 0;
		owner = "";
		mBool = false;
		color = 1;
		listeners = new ArrayList<ChangeListener>();
	}
	
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
	
	public void setMBool(boolean mb){
		mBool = mb;
		fireChange();
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
		//System.out.println(name+" was bought buy "+owner);
		fireChange();
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
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int c) {
		color = c;
		fireChange();
	}
	
	public void addListener(ChangeListener ce){
		if(listeners == null) {
			listeners = new ArrayList<ChangeListener>();
		}
		if(!listeners.contains(ce)){
			listeners.add(ce);
		}
	}
	
	public void removeListener(ChangeListener ce) {
		listeners.remove(ce);
	}
	
	protected void fireChange(){
		ChangeEvent ce = new ChangeEvent(this);
		//System.out.println("Change has been fired in property");
		for(ChangeListener cl : listeners){
			cl.stateChanged(ce);
		}
		
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

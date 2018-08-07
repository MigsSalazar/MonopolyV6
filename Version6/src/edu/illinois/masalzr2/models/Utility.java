package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import com.google.gson.annotations.Expose;

public class Utility extends Property implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose private Counter utilityOwned;
	private Dice gameDice;
	
	public Utility(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen){
		super(n, pos, pr, o, m ,listen);
	}
	
	public Utility(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen, Counter u){
		super(n, pos, pr, o, m ,listen);
		utilityOwned = u;
	}
	
	@Override
	public void setOwner(String p) {

		if(owner.equals("") || owner == null) {
			utilityOwned.add(1);
		}
		super.setOwner(p);
		fireChange();
	}
	
	@Override
	public int getRent(){
		return ( 4 + 6*(utilityOwned.getCount()-1) ) * gameDice.getLastRoll();
	}
	
	public void setDice(Dice d){
		gameDice = d;
	}
	
	public Counter getCounter() {
		return utilityOwned;
	}
	
	public void setCounter(Counter c){
		utilityOwned = c;
	}
	
}

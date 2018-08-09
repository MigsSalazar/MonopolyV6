package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import com.google.gson.annotations.Expose;

public class Railroad extends Property implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose private Counter railsOwned;
	
	public Railroad(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen){
		super(n, pos, pr, o, m, listen);
	}
	
	public Railroad(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen, Counter r){
		super(n, pos, pr, o, m, listen);
		railsOwned = r;
	}
	
	@Override
	public void setOwner(String p) {

		if(owner.equals("") || owner == null) {
			railsOwned.add(1);
		}
		super.setOwner(p);
		fireChange();
	}
	
	public Counter getRailsOwned(){
		return railsOwned;
	}
	
	public void setRailedOwned(Counter c){
		railsOwned = c;
	}
	
	@Override
	public int getRent(){
		switch(railsOwned.getCount()) {
		case 1: return 25;
		case 2: return 50;
		case 3: return 100;
		case 4: return 200;
		default: return 0;
		}
	}
	
}

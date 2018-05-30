package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

public class Railroad extends Property implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Counter railsOwned;
	
	public Railroad(String n, int pos, int pr, String o, boolean m, ArrayList<ChangeListener> listen){
		super(n, pos, pr, o, m, listen);
	}
	
	public Counter getRailsOwned(){
		return railsOwned;
	}
	
	public void setRailedOwned(Counter c){
		railsOwned = c;
	}
	
	@Override
	public int getRent(){
		return 25 * (int)Math.pow(2, railsOwned.getCount() - 1 );
	}
	
}

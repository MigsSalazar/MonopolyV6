package edu.illinois.masalzr2.models;

public class Railroad extends Property {
	private Counter railsOwned;
	
	public Counter getRailsOwned(){
		return railsOwned;
	}
	
	@Override
	public int getRent(){
		return 25 * (int)Math.pow(2, railsOwned.getCount() - 1 );
	}
	
}

package main.java.models;

import com.google.gson.annotations.Expose;

public class Utility extends Property {
	
	@Expose private Dice uroll = new Dice(2,6);
	@Expose private GlobalCounter gcount;

	public Utility(String n, int p, int pr, boolean mb, GlobalCounter gb) {
		super(n, p, pr, mb);
		// TODO Auto-generated constructor stub
		gcount = gb;
	}
	
	
	
	@Override
	public int getRent() {
		// TODO Auto-generated method stub
		int rent = uroll.roll();
		if(gcount.getCount() == 1){
			rent = rent*4;
		}else if(gcount.getCount() == 2){
			rent = rent*10;
		}
		return rent;
	}



	public GlobalCounter getGcount() {
		return gcount;
	}



	public void setGcount(GlobalCounter gcount) {
		this.gcount = gcount;
	}

}

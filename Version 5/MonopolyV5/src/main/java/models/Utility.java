package main.java.models;

import com.google.gson.annotations.Expose;

import main.java.action.Roll;

public class Utility extends Property {
	
	private transient Roll uroll;
	@Expose private GlobalCounter gcount;

	public Utility(String n, int p, int pr, boolean mb, Roll ur, GlobalCounter gb) {
		super(n, p, pr, mb);
		// TODO Auto-generated constructor stub
		gcount = gb;
	}
	
	@Override
	public void setOwner(String o){
		if(owner == null || !owner.equals("")){
			gcount.incCount();
		}
		owner = o;
	}
	
	@Override
	public int getRent() {
		int rent = uroll.diceRollHalf();
		if(gcount.getCount() == 1){
			rent = rent*4;
		}else if(gcount.getCount() == 2){
			rent = rent*10;
		}
		return rent;
	}


	public void setUroll(Roll ur){
		uroll = ur;
	}

	public GlobalCounter getGcount() {
		return gcount;
	}



	public void setGcount(GlobalCounter gcount) {
		this.gcount = gcount;
	}

}

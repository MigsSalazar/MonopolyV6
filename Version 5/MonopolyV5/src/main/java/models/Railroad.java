package main.java.models;

import com.google.gson.annotations.Expose;

public class Railroad extends Property {
	
	@Expose private GlobalCounter gcount;
	
	public Railroad(String n, String o, int p, int pr, boolean mb, GlobalCounter gc) {
		super(n, o, p, pr, mb);
		gcount = gc;
	}
	
	@Override
	public void setOwner(String o){
		if(owner == null || owner.equals("")){
			System.out.println("owner was either null or empty");
			owner = o;
			System.out.println("gcount before:"+gcount.getCount());
			gcount.incCount();
			System.out.println("gcount after:"+gcount.getCount());
		}else{
			System.out.println("owner was found");
			owner = o;
		}
	}

	@Override
	public int getRent() {
		// TODO Auto-generated method stub
		System.out.println("gcount in railroad = "+gcount.getCount());
		switch(gcount.getCount()){
			case 1: return 25;
			case 2: return 50;
			case 3:	return 100;
			case 4: return 200;
			default: return 0;
		}
	}

	public GlobalCounter getGcount() {
		return gcount;
	}

	public void setGcount(GlobalCounter gcount) {
		this.gcount = gcount;
	}


}

package main.java.models;

public class Utility extends Property {
	
	private Dice uroll = new Dice(2,6);
	private GlobalCounter gcount;

	public Utility(String n, int p, int pr, int m, boolean mb, GlobalCounter gb) {
		super(n, p, pr, m, mb);
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

}

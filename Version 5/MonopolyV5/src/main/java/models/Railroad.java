package main.java.models;

public class Railroad extends Property {
	
	private GlobalCounter gcount;
	
	public Railroad(String n, int p, int pr, int o, int m, boolean mb, GlobalCounter gc) {
		super(n, p, pr, o, m, mb);
		// TODO Auto-generated constructor stub
		gcount = gc;
	}

	@Override
	public int getRent() {
		// TODO Auto-generated method stub
		switch(gcount.getCount()){
			case 1: return 25;
			case 2: return 50;
			case 3:	return 100;
			case 4: return 200;
			default: return 0;
		}
	}


}

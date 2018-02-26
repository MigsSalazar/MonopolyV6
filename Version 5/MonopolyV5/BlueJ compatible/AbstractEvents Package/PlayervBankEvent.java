 

import java.util.ArrayList;


public class PlayervBankEvent extends MessageEvent {
	
	public PlayervBankEvent(EventPanel p, String t,  Player play, int cost) {
		super(p,t);
		
		play.addCash(cost);
		//defineComponents();
	}
	
	public PlayervBankEvent(EventPanel p, String t,  ArrayList<Player> plays, int cost) {
		super(p,t);
		for(Player pl : plays){
			pl.addCash(cost);
		}
		//defineComponents();
	}
	

}

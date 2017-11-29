package main.java.gameEvents;

import java.util.ArrayList;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class PlayervPlayerEvent extends MessageEvent {

	public PlayervPlayerEvent(EventPanel p, String message, Player p1, Player p2, int cost) {
		super(p, message);
		p1.addCash(cost);
		p2.subCash(cost);
	}

	public PlayervPlayerEvent(EventPanel p, String message, Player p1, ArrayList<Player> plays, int cost) {
		super(p,message);
		for(Player pl : plays){
			if( !p1.equals(pl) ){
				p1.addCash(cost);
				pl.subCash(cost);
			}
		}
		
	}
	
	
}

package main.java.gameEvents;

import java.util.ArrayList;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class PlayervBankEvent extends MessageEvent {
	
	public PlayervBankEvent(EventPanel p, String t,  Player play, int cost) {
		super(p,t);
		play.addCash(cost);
	}
	
	public PlayervBankEvent(EventPanel p, String t,  ArrayList<Player> plays, int cost) {
		super(p,t);
		for(Player pl : plays){
			pl.addCash(cost);
		}
		
	}

}

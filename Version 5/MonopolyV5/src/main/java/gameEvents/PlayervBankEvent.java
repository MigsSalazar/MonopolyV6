package main.java.gameEvents;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class PlayervBankEvent extends MessageEvent {
	
	public PlayervBankEvent(EventPanel p, String t,  Player play, int cost) {
		super(p,t);
		play.addCash(cost);
	}

}

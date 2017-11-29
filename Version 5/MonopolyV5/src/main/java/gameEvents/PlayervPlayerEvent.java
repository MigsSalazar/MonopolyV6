package main.java.gameEvents;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class PlayervPlayerEvent extends MessageEvent {

	public PlayervPlayerEvent(EventPanel p, String message, Player p1, Player p2, int cost) {
		super(p, message);
		p1.addCash(cost);
		p2.subCash(cost);
	}

}

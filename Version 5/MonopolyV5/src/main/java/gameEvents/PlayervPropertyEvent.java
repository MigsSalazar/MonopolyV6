package main.java.gameEvents;

import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

public class PlayervPropertyEvent extends MessageEvent {
	
	public PlayervPropertyEvent(EventPanel p, String t, Player play, Property prop) {
		super(p, t);
		play.addProperty(prop);
		play.subCash(prop.getPrice());
	}
	
	public PlayervPropertyEvent(EventPanel p, String t, Player play, Property prop, int cost) {
		super(p, t);
		play.addProperty(prop);
		play.subCash(cost);
	}

}

package main.java.gameEvents;

import java.util.Map;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class PlayervPlayerEvent extends MessageEvent {

	public PlayervPlayerEvent(EventPanel p, String message, Player p1, Player p2, int cost) {
		super(p, message);
		p1.addCash(cost);
		p2.subCash(cost);
		//defineComponents();
	}

	public PlayervPlayerEvent(EventPanel p, String message, Player p1, Map<String, Player> plays, int cost) {
		super(p,message);
		for(String pname : plays.keySet()){
			if( !plays.get(pname).equals(p1) ){
				plays.get(pname).subCash(cost);
				p1.addCash(cost);
			}
		}
		//defineComponents();
		
	}
	
	
	
}

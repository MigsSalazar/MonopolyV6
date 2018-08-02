package edu.illinois.masalzr2.notices.implentations;

import java.util.List;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;

public class PlayerPlayerNotice extends MessageNotice{
	public PlayerPlayerNotice(String t, ListListener ppl, Player p1, Player p2, int c){
		super(t, ppl);
		p1.addCash(c);
		p2.subCash(c);
	}
	
	public PlayerPlayerNotice(String t, ListListener ppl, Player p1, List<Player> players, int c){
		super(t, ppl);
		for(Player p : players){
			p1.addCash(c);
			p.subCash(c);
		}
	}
}

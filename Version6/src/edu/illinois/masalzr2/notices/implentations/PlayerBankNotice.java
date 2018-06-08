package edu.illinois.masalzr2.notices.implentations;

import java.util.ArrayList;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;

public class PlayerBankNotice extends MessageNotice{
	public PlayerBankNotice(String t, ListListener ppl, Player p, int c){
		super(t, ppl);
		p.addCash(c);
	}
	public PlayerBankNotice(String t, ListListener ppl, ArrayList<Player> p, int c){
		super(t, ppl);
		for(Player plays : p){
			plays.addCash(c);
		}
	}
}
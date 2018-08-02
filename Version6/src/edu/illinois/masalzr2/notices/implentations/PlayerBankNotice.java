package edu.illinois.masalzr2.notices.implentations;

import java.util.ArrayList;

import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;

public class PlayerBankNotice extends MessageNotice{
	public PlayerBankNotice(String t, ListListener ppl, Player p, int c){
		super(t, ppl);
		LogMate.LOG.newEntry("PlayerBankNotice: Beginning: Cash value awarded="+c);
		LogMate.LOG.newEntry("PlayerBankNotice: Beginning: Player before: cash="+p.getCash()+" wealth="+p.getWealth()+" liquidWealth="+p.getLiquidationWorth());
		p.addCash(c);
		LogMate.LOG.newEntry("PlayerBankNotice: Beginning: Player after: cash="+p.getCash()+" wealth="+p.getWealth()+" liquidWealth="+p.getLiquidationWorth());
	}
	public PlayerBankNotice(String t, ListListener ppl, ArrayList<Player> p, int c){
		super(t, ppl);
		LogMate.LOG.newEntry("PlayerBankNotice: Beginning: Cash value awarded="+c);
		for(Player plays : p){
			LogMate.LOG.newEntry("PlayerBankNotice: Beginning: Player before: cash="+plays.getCash()+" wealth="+plays.getWealth()+" liquidWealth="+plays.getLiquidationWorth());
			plays.addCash(c);
			LogMate.LOG.newEntry("PlayerBankNotice: Beginning: Player after: cash="+plays.getCash()+" wealth="+plays.getWealth()+" liquidWealth="+plays.getLiquidationWorth());
		}
	}
}
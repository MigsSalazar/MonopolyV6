package edu.illinois.masalzr2.notices.implentations;

import java.util.List;

import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;

public class PlayerPlayerNotice extends MessageNotice{
	public PlayerPlayerNotice(String t, ListListener ppl, Player p1, Player p2, int c){
		super(t, ppl);
		LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: add to "+p1.getName()+" sub from "+p2.getName() + " the value of "+c);
		LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p1.getName()+" before: cash="+p1.getCash()+" wealth="+p1.getWealth()+" liquidWealth="+p1.getLiquidationWorth());
		p1.addCash(c);
		LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p1.getName()+" after: cash="+p1.getCash()+" wealth="+p1.getWealth()+" liquidWealth="+p1.getLiquidationWorth());
		LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p2.getName()+" before: cash="+p2.getCash()+" wealth="+p2.getWealth()+" liquidWealth="+p2.getLiquidationWorth());
		p2.subCash(c);
		LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p2.getName()+" after: cash="+p2.getCash()+" wealth="+p2.getWealth()+" liquidWealth="+p2.getLiquidationWorth());
		
	}
	
	public PlayerPlayerNotice(String t, ListListener ppl, Player p1, List<Player> players, int c){
		super(t, ppl);
		LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning multi: add to "+p1.getName()+" from each player value of "+c);
		for(Player p : players){
			LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p1.getName()+" before: cash="+p1.getCash()+" wealth="+p1.getWealth()+" liquidWealth="+p1.getLiquidationWorth());
			p1.addCash(c);
			LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p1.getName()+" after: cash="+p1.getCash()+" wealth="+p1.getWealth()+" liquidWealth="+p1.getLiquidationWorth());
			LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p.getName()+" before: cash="+p.getCash()+" wealth="+p.getWealth()+" liquidWealth="+p.getLiquidationWorth());
			p.subCash(c);
			LogMate.LOG.newEntry("PlayerPlayerNotice: Beginning single: Player "+p.getName()+" after: cash="+p.getCash()+" wealth="+p.getWealth()+" liquidWealth="+p.getLiquidationWorth());
			
		}
	}
}

package edu.illinois.masalzr2.notices.implentations;

import java.util.ArrayList;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;
import lombok.extern.flogger.Flogger;

@Flogger
public class PlayerBankNotice extends MessageNotice{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PlayerBankNotice(String t, ListListener ppl, Player p, int c){
		super(t, ppl);
		log.atFinest().log("PlayerBankNotice: Beginning: Cash value awarded="+c);
		log.atFinest().log("PlayerBankNotice: Beginning: Player before: cash="+p.getCash()+" wealth="+p.getWealth()+" liquidWealth="+p.getLiquidationWorth());
		p.addCash(c);
		log.atFinest().log("PlayerBankNotice: Beginning: Player after: cash="+p.getCash()+" wealth="+p.getWealth()+" liquidWealth="+p.getLiquidationWorth());
	}
	public PlayerBankNotice(String t, ListListener ppl, ArrayList<Player> p, int c){
		super(t, ppl);
		log.atFinest().log("PlayerBankNotice: Beginning: Cash value awarded="+c);
		for(Player plays : p){
			log.atFinest().log("PlayerBankNotice: Beginning: Player before: cash="+plays.getCash()+" wealth="+plays.getWealth()+" liquidWealth="+plays.getLiquidationWorth());
			plays.addCash(c);
			log.atFinest().log("PlayerBankNotice: Beginning: Player after: cash="+plays.getCash()+" wealth="+plays.getWealth()+" liquidWealth="+plays.getLiquidationWorth());
		}
	}
}
package edu.illinois.masalzr2.notices.implentations;

import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;

public class GoNotice extends PlayerBankNotice {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoNotice(ListListener ppl, Player p) {
		super("<html>You passed or landed on Go!"
				+ "<br>Collect $200!</html>" , ppl, p, 200);
		LogMate.LOG.newEntry("GoNotice: Beginning: Player "+p.getName()+" has landed/passed on Go");
	}

}

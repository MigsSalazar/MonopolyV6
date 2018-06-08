package edu.illinois.masalzr2.notices.implentations;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.ListListener;

public class GoNotice extends PlayerBankNotice {

	public GoNotice(ListListener ppl, Player p) {
		super("You passed/landed on Go!"
				+ "<br>Collect $200!" , ppl, p, 200);
		// TODO Auto-generated constructor stub
	}

}

package edu.illinois.masalzr2.notices;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.Dice;

public abstract class DiceNeeded extends AbstractNotice {

	protected Dice gameDice;

	public DiceNeeded(GameVariables gv, Dice d) {
		super(gv);
		// TODO Auto-generated constructor stub
		gameDice = d;
	}

	

}

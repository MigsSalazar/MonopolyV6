package edu.illinois.masalzr2.notices.implentations;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.notices.ListListener;

public class PlayerPropertyNotice extends MessageNotice {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlayerPropertyNotice(String t, ListListener ppl, Player play, Property prop) {
		super(t, ppl);
		play.addProp(prop);
		play.subCash(prop.getPrice());
		prop.setOwner(play.getName());
	}
	
	public PlayerPropertyNotice(String t, ListListener ppl, Player play, Property prop, int cost) {
		super(t, ppl);
		play.addProp(prop);
		play.subCash(cost);
		prop.setOwner(play.getName());
	}

}

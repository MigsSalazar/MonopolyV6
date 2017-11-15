package main.java.action;

import java.awt.event.ActionEvent;

import main.java.gui.EventPanel;
import main.java.models.Property;

public class AuctionEvent extends Events {

	private Property prop;
	
	public AuctionEvent(EventPanel p, Property pr){
		super(p);
		prop = pr;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void defineButtons() {
		// TODO Auto-generated method stub

	}

}

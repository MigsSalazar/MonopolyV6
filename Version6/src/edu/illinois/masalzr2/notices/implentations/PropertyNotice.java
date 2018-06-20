package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;
import edu.illinois.masalzr2.notices.implentations.AuctionNotice;

public class PropertyNotice extends AbstractNotice {

	private Player player;
	private Property prop;
	private GameVariables gameVars;
	
	public PropertyNotice(ListListener ppl, GameVariables gv, Player pl, Property pr) {
		super(ppl);
		player = pl;
		prop = pr;
		gameVars = gv;
		
		if(prop.getOwner() == null || prop.getOwner().equals("")) {
			text = prop.getName() + "is unowned. Asking price: " + gv.getCurrencySymbol() + prop.getPrice() +"\nWhat would you like to do?";
		}else if( !prop.getOwner().equals(pl.getName()) ){
			text = pl.getName() + " has landed on " +prop.getOwner() + "'s property of " + prop.getName() + "\nRent: " + gv.getCurrencySymbol() + prop.getRent();
		}else {
			text = "You own this property.\nNothing to be done";
		}
		
		defineActions();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(prop.getOwner() == null || prop.getOwner().equals("")){
			if(e.getSource().equals(actions[0])){
				//BankPropertyActions.sellUnownedProperty(play, prop);
				String texter = "You bought "+prop.getName()+"!";
				AbstractNotice an = new PlayerPropertyNotice(texter, listener, player, prop);
				listener.pushMe(new ListEvent(an));
				listener.popMe(new ListEvent(this));
			}else{
				AbstractNotice an = new AuctionNotice(listener, gameVars, prop);
				listener.pushMe(new ListEvent(an));
				listener.popMe(new ListEvent(this));
			}
		}else if(!prop.getOwner().equals(player.getName())){
			//BankPropertyActions.rentOwnedProperty(play, prop);
			
			Player p2 = gameVars.getPlayers().get(prop.getOwner());
			String outText = "You payed "+prop.getOwner()+" "+gameVars.getCurrencySymbol()+prop.getRent()+" for landing on "+prop.getName()+"!";
			
			AbstractNotice an = new PlayerPlayerNotice(outText, listener, player, p2, (-1)*prop.getRent());
			//AbstractNotice a = new MessageEvent(parent, "You payed "+play.getName()+" for landing on "+prop.getName()+"!");
			listener.pushMe(new ListEvent(an));
			listener.popMe(new ListEvent(this));
			//parent.jumpStartClean();
		}else{
			listener.popMe(new ListEvent(this));
		}
	}

	@Override
	protected void defineActions() {
		if(prop.getOwner() == null || prop.getOwner().equals("")){
			actions = new JButton[2];
			actions[0] = new JButton("Purchase");
			((JButton)actions[0]).addActionListener(this);
			actions[0].setEnabled(player.getCash()-prop.getPrice() > 0);
			actions[1] = new JButton("Auction Off");
			((JButton)actions[1]).addActionListener(this);
		}else{
			actions = new JButton[1];
			if(!prop.getOwner().equals(player.getName())){
				actions[0] = new JButton("Pay Rent");
				((JButton)actions[0]).addActionListener(this);
			}else{
				actions[0] = new JButton("Move Along");
				((JButton)actions[0]).addActionListener(this);
			}
		}
		
	}

}

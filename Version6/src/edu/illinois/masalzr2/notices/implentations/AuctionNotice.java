package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;

public class AuctionNotice extends AbstractNotice implements KeyListener  {

	private GameVariables gameVars;
	
	private Property prop;
	private int turn = 0;
	private int bid = 0;
	private int highestBidder = -1;
	private String[] playerNames;
	private HashMap<String, Player> players;
	
	public AuctionNotice(ListListener ppl, GameVariables gv, Property pr){
		super(ppl);
		subConstructor(gv, pr);
		
		defineActions();
	}
	
	public AuctionNotice(ListListener ppl, GameVariables gv, Property pr, int t, int b, int hb) {
		super(ppl);
		subConstructor(gv, pr);
		turn = t;
		bid = b;
		highestBidder = hb;
	}

	private void subConstructor(GameVariables gv, Property pr) {
		gameVars = gv;
		prop = pr;
		players = gameVars.getPlayers();
		playerNames = new String[players.size()];
		players.keySet().toArray(playerNames);
		text = "<html>Current bid on "+prop.getName()+" is "+gameVars.getCurrencySymbol()+bid + " by " + playerNames[0]
				+"<br>"+playerNames[turn]+", will you raise or pass? Entering 0 means you pass."
				+"<br>Your offer:</html>";
	}

	private void buttonPush(ActionEvent e) {
		//bidInput((JTextField)actions[0]);
		JTextField bidField = (JTextField)actions[0];
		if(e.getSource().equals(actions[1])){
			
			if( bidField.getText().equals("")
				|| Integer.parseInt(  bidField.getText()  ) == 0 
				|| Integer.parseInt( bidField.getText() ) == bid){
				((JButton)actions[2]).doClick();
			}else{
				bid = Integer.parseInt(bidField.getText());
				highestBidder = turn;
				turn = (turn+1)%playerNames.length;
				bidField.setText(""+(bid+1));
				setText();
				
				listener.pullMe(new ListEvent(this));
				
			}
		}else if(e.getSource().equals(actions[2])){
			turn = (turn+1)%playerNames.length;
			bidField.setText(""+(bid+1));
			setText();
			
			listener.pullMe(new ListEvent(this));
			
		}
		
	}
	
	private void setText(){
		if(actions != null){
			bidInput((JTextField)actions[0]);
		}
		
		String person;
		if(highestBidder > -1){
			person = " by " + (String) playerNames[highestBidder];
		}else{
			person = "";
		}
		text = "<html>Current bid on "+prop.getName()+" is "+gameVars.getCurrencySymbol()+bid + person
				+"<br>"+playerNames[turn]+", will you raise or pass? Entering 0 means you pass."
				+"<br>Your offer:</html>";
	}
	
	private void bidInput(JTextField useme) {
		try{
			int offer = Integer.parseInt(useme.getText());
			if(offer >=  players.get(playerNames[turn]).getCash() || (offer<=bid && bid !=0)){
				((JButton)actions[1]).setEnabled(false);
			}else if(offer > bid || offer==0){
				((JButton)actions[1]).setEnabled(true);
			}
		}catch(NumberFormatException nfe){
			if(!((JTextField)actions[0]).getText().equals("")){
				((JTextField)actions[0]).setText(""+(bid+1));
			}
			//((JTextField)actions[0]).setText(""+(bid+1));
		}
	}
	
	private String whoWon(){
		return "<html>"+playerNames[highestBidder]+" has won the auction and bought<br>"+prop.getName()+" for "+gameVars.getCurrencySymbol()+bid+"</html>";
	}
	
	private boolean fullCircle(){
		if(bid == 0 || highestBidder == -1){
			return false;
		}
		return highestBidder==turn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(actions[0])){
			JTextField useme = (JTextField)actions[0];
			//System.out.println("JtextField in auction has changed");
			bidInput(useme);
		}else if(e.getSource().equals(actions[1]) || e.getSource().equals(actions[2])){
			buttonPush(e);
			if(fullCircle()){
				//BankPropertyActions.sellUnownedProperty(players.get(playerNames[highestBidder].toString()), prop, bid);
				MessageNotice an = new PlayerPropertyNotice(whoWon(), listener, players.get(playerNames[highestBidder]), prop, bid);
				listener.pushMe(new ListEvent(an));
				listener.popMe(new ListEvent(this));
			}
		}
		
	}
	
	@Override
	protected void defineActions() {
		actions = new JComponent[3];
		actions[0] = new JTextField();
		((JTextField)actions[0]).setText("1");
		((JTextField)actions[0]).addKeyListener(this);
		((JTextField)actions[0]).setColumns(5);
		//((JTextField)actions[0]).setDragEnabled(false);
		actions[1] = new JButton("Raise");
		((JButton)actions[1]).addActionListener(this);
		actions[2] = new JButton("Pass");
		((JButton)actions[2]).addActionListener(this);
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		bidInput((JTextField)e.getSource());
	}


	@Override
	public void keyReleased(KeyEvent e) {
		bidInput((JTextField)e.getSource());
	}


	@Override
	public void keyTyped(KeyEvent e) {
		bidInput((JTextField)e.getSource());
	}

}

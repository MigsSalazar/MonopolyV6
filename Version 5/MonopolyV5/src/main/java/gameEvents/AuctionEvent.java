package main.java.gameEvents;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import main.java.action.Runner;
import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

public class AuctionEvent extends AbstractEvent {

	private Runner runme = parent.getGlobalVars();
	
	private Property prop;
	private int turn = 0;
	private int bid = 0;
	private int highestBidder = -1;
	private Object[] playerNames = runme.getPlayerNames().toArray();
	private Map<String, Player> players = runme.getPlayers();
	
	public AuctionEvent(EventPanel p, Property pr){
		super(p);
		prop = pr;
		setText();
		defineComponents();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttons[0])){
			JTextField useme = (JTextField)buttons[0];
			bidInput(useme);
		}else if(e.getSource().equals(buttons[1]) || e.getSource().equals(buttons[2])){
			buttonPush(e);
			if(fullCircle()){
				//BankPropertyActions.sellUnownedProperty(players.get(playerNames[highestBidder].toString()), prop, bid);
				MessageEvent me = new PlayervPropertyEvent(parent, whoWon(), players.get(playerNames[highestBidder]), prop, bid);
				parent.paintEvent(me);
				sync(me);
				desync();
			}
		}
	}


	private void buttonPush(ActionEvent e) {
		if(e.getSource().equals(buttons[1])){
			if( Integer.parseInt(  ((JTextField)buttons[0]).getText()  ) == 0 ){
				((JButton)buttons[2]).doClick();
			}else{
				bid = Integer.parseInt(((JTextField)buttons[0]).getText());
				highestBidder = turn;
				turn = (turn+1)%playerNames.length;
				setText();
				parent.softRepaint();
				
			}
		}else if(e.getSource().equals(buttons[2])){
			turn = (turn+1)%playerNames.length;
			setText();
			parent.softRepaint();
			
		}
		
	}


	private void bidInput(JTextField useme) {
		try{
			int offer = Integer.parseInt(useme.getText());
			if(offer >=  players.get(playerNames[turn]).getCash() || (offer<bid && bid !=0)){
				((JButton)buttons[1]).setEnabled(false);
			}else if(offer > bid || offer==0){
				((JButton)buttons[1]).setEnabled(true);
			}
		}catch(NumberFormatException nfe){
			((JTextField)buttons[0]).setText(""+(bid+1));
		}
	}

	private void setText(){
		text = "<html>Current bid on "+prop.getName()+" is "+bid
				+"<br>"+playerNames[turn]+", will you raise or pass? Entering 0 means you pass."
				+"<br>Your offer:</html>";
	}

	private boolean fullCircle(){
		return highestBidder==turn;
	}
	
	private String whoWon(){
		return "<html>"+playerNames[highestBidder]+" has won the auction and bought<br>"+prop.getName()+" for $"+bid+"</html>";
	}
	
	@Override
	public void defineComponents() {
		buttons = new JComponent[3];
		buttons[0] = new JTextField();
		((JTextField)buttons[0]).setText("1");
		((JTextField)buttons[0]).addActionListener(this);
		buttons[1] = new JButton("Raise");
		((JButton)buttons[1]).addActionListener(this);
		buttons[2] = new JButton("Pass");
		((JButton)buttons[2]).addActionListener(this);
	}



}

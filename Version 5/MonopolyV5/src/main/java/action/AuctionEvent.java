package main.java.action;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import main.java.gui.EventPanel;
import main.java.models.Player;
import main.java.models.Property;

public class AuctionEvent extends Events {

	private Runner runme = parent.getGlobalVars();
	
	private Property prop;
	private int turn = 0;
	private int bid = 0;
	private int highestBidder = -1;
	private String[] playerNames = runme.playerNames();
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
		}else if(e.getSource().equals(buttons[1]) || e.getSource().equals(buttons[2])){
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
			if(fullCircle()){
				BankPropertyActions.sellUnownedProperty(players.get(playerNames[highestBidder]), prop, bid);
				MessageEvent me = new MessageEvent(parent, whoWon());
				try {
					me.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				notify();
			}
		}
	}

	private void setText(){
		text = "Current bid on "+prop.getName()+" is "+bid
				+"\n"+playerNames[turn]+", will you raise or pass? Entering 0 means you pass."
				+"\nYour offer:";
	}

	private boolean fullCircle(){
		return highestBidder==turn;
	}
	
	private String whoWon(){
		return "";
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

package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListListener;

public class JailNotice extends HighLevelNotice {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int turnsJailed;

	public JailNotice(ListListener ppl, GameVariables gv) {
		super(ppl, gv);
		turnsJailed = gameVars.nightInJail(currentPlayer);
		
		if(turnsJailed < 3) {
			text = "<html>Day "+turnsJailed+" in jail for "+currentPlayer.getName()+". Bail is posted at $50. Want to pay bail"
				   + "<br>or plead your innocence (roll the dice) for freedom?</html>";
		}else {
			text = "<html>Day 3. Today is your trial, "+currentPlayer.getName()+". You may either plead guilty"
					+ "(pay $50) or go to trial (roll the dice).</html>";
		}
		defineActions();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		AbstractNotice an = null;
		String t = "";
		if(source.equals(actions[0])) {
			if(turnsJailed < 3) {
				t += "<html>You have paid the $50 bail. You are free to go."
						+ "<br>Cause no more trouble.</html>";
				
			}else {
				t += "<html>You have plead guilty and been fined $50. You are free to go."
						+ "<br>Cause no more trouble.</html>";
			}
			freeThem();
			an = new PlayerBankNotice(t, listener, currentPlayer, -50);
		}else if(source.equals(actions[1]) ){
			gameVars.roll();
			int[] rolled = gameDice.getLastDice();
			t = "<html>You have rolled "+rolled[0]+" and "+rolled[1]+".<br>";
			if(rolled[0] == rolled[1]) {
				t += (turnsJailed < 3 ? "Your plea was successful! You're free to go!" : "You won your trial! You're free to go!");
				t += "</html>";
				freeThem();
				an = new MessageNotice(t, listener);
			}else {
				if(turnsJailed < 3) {
					t += "Your plea was unsuccessful. You'll spend another night in jail.</html>";
					an = new MessageNotice(t, listener);
				}else {
					t += "You lost your trial and must pay the $50 fine!</html>";
					freeThem();
					an = new PlayerBankNotice(t, listener, currentPlayer, -50);
				}
				
			}
			
		}
		
		if(an != null) {
			updateTurn();
			this.noticePushPop(an);
		}
		
	}

	private void freeThem() {
		gameVars.releaseJailedPlayer(currentPlayer);
		gameVars.resetJail(currentPlayer);
	}

	@Override
	protected void defineActions() {
		actions = new JButton[2];
		JButton bail = new JButton("$50 Bail");
		bail.addActionListener(this);
		JButton dice = new JButton("Roll Dice");
		dice.addActionListener(this);
		actions[0] = bail;
		actions[1] = dice;
	}

}

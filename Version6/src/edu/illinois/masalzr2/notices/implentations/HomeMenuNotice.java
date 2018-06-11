package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;

public class HomeMenuNotice extends HighLevelNotice {

	public HomeMenuNotice(ListListener ppl, GameVariables gv) {
		super(ppl, gv);
		text = gv.getCurrentPlayer().getName()+"'s turn. ";
		defineActions();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Current player = " + currentPlayer.getName());
		currentPlayer = gameVars.getCurrentPlayer();
		//System.out.println("Current player has changed to: "+currentPlayer.getName());
		//System.out.println("e has been found in action performed");
		if(e.getSource() instanceof JButton){
			JButton me = (JButton)e.getSource();
			
			if(me.equals(actions[0])){
				//System.out.println("Roll button pressed");
				if(gameVars.isInJail(currentPlayer)){
					//System.out.println("This dude is locked up");
					//AbstractNotice jailTime = new InJailNotice(parent, gameVars, currentPlayer);
					//parent.paintNotice(jailTime);
					//sync(jailTime);
					//updateTurn();
					currentPlayer = gameVars.getCurrentPlayer();
					
				}else{
					
					int roll = gameDice.roll();
					int dice1 = gameDice.getLastDice()[0];
					int dice2 = gameDice.getLastDice()[1];
					
					crossGo(currentPlayer, roll);
					
					AbstractNotice event = moveAndDo(roll);
					//System.out.println("now painting the event to the frame");
					listener.pushMe(new ListEvent(event));
					//System.out.println("syncing the event");
					if(dice1 != dice2){
						updateTurn();
					}
					listener.popMe(new ListEvent(this));
					//System.out.println("and im out bois");
					
					
					
				}
			}else if(me.equals(actions[1])){
				//UpgradeManager um = new UpgradeManager(gameVars, currentPlayer);
				//um.beginManager();
			}else if(me.equals(actions[2])){
				//TradeManager tm = new TradeManager(gameVars, currentPlayer, gameVars.getPlayers());
				//tm.runManager();
			}else if(me.equals(actions[3])){
				//MortgageManager mm = new MortgageManager(gameVars, currentPlayer);
				//mm.beginManager();
				//gameVars.getFrame().getGameStats().updatePlayers();
			}
		}
		//parent.paintNotice(this);
		currentPlayer = gameVars.getCurrentPlayer();
		//updateText();
		//listener.popMe(new ListEvent(this));
	}

	@Override
	protected void defineActions() {
		actions = new JComponent[4];
		/*
		 * 4 menu options
		 * Role-Upgrade-Trade-Mortgage
		 */
		actions[0] = new JButton("Role");
		((JButton)actions[0]).addActionListener(this);
		
		actions[1] = new JButton("Upgrade");
		((JButton)actions[1]).addActionListener(this);
		
		actions[2] = new JButton("Trade");
		((JButton)actions[2]).addActionListener(this);
		
		actions[3] = new JButton("Mortgage");
		((JButton)actions[3]).addActionListener(this);
		
	}

}

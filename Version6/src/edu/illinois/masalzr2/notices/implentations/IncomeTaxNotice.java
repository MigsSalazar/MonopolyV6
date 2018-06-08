package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;

public class IncomeTaxNotice extends AbstractNotice {
	private Player play;
	private int tax;
	private GameVariables gameVars;

	public IncomeTaxNotice(ListListener ppl, GameVariables gv, Player p1) {
		
		super(ppl);
		play = p1;
		gameVars = gv;
		tax = calcTax();
		text = "<html>"+play.getName()+", you must pay an income tax equivalent to"
				+ "<br>10% of your total wealth or "+gameVars.getCurrencySymbol()+"200"
				+ "<br>10% of your wealth is "+tax+"</html>";
		//System.out.println("step 3");
		defineActions();
		//System.out.println("constructor over");
		//parent.paintEvent(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PlayerBankNotice pbe = null;
		if(e.getSource().equals(actions[0])){
			pbe = new PlayerBankNotice("<html>You have decided to pay 10%"
											+ "<br>This has been taken from your account</html>", listener,play,(-1)*tax);
		}else if(e.getSource().equals(actions[1])){
			pbe = new PlayerBankNotice("<html>You have decided to pay "+gameVars.getCurrencySymbol()+"200"
											+ "<br>This has been taken from yuor account</html>", listener, play, (-200));
		}
		listener.pushMe(new ListEvent(pbe));
		listener.popMe(new ListEvent(this));
	}

	@Override
	public void defineActions() {
		actions = new JComponent[2];
		
		actions[0] = new JButton(gameVars.getCurrencySymbol()+tax);
		((JButton)actions[0]).addActionListener(this);
		
		actions[1] = new JButton(gameVars.getCurrencySymbol()+"200");
		((JButton)actions[1]).addActionListener(this);
		
	}
	
	private int calcTax(){
		return (int)(play.getWealth() * 0.1);
	}
}

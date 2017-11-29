package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class IncomeTaxEvent extends AbstractEvent {
	
	private Player play;
	private int tax;

	public IncomeTaxEvent(EventPanel p, Player p1) {
		super(p);
		play = p1;
		tax = calcTax();
		text = play.getName()+", you must pay an income tax equivalent to\n10% of your total wealth or $200\n10% of your wealth is "+tax;
		defineComponents();
		parent.paintEvent(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PlayervBankEvent pbe = null;
		if(e.getSource().equals(buttons[0])){
			pbe = new PlayervBankEvent(parent, "You have decided to pay 10%\nThis has been taken from yuor account",play,tax);
			sync(pbe);
			desync();
		}else if(e.getSource().equals(buttons[2])){
			pbe = new PlayervBankEvent(parent, "You have decided to pay 10%\nThis has been taken from yuor account",play,tax);
			sync(pbe);
			desync();
		}
		
	}

	@Override
	public void defineComponents() {
		buttons = new JComponent[2];
		
		buttons[0] = new JButton("$"+tax);
		((JButton)buttons[0]).addActionListener(this);
		
		buttons[1] = new JButton("$200");
		((JButton)buttons[1]).addActionListener(this);
		
	}
	
	private int calcTax(){
		return (int)(play.getWealth() * 0.1);
	}

}

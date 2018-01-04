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
		//System.out.println("In income tax. wheres the problem?");
		play = p1;
		//System.out.println("step 1");
		tax = calcTax();
		//System.out.println("step 2");
		text = "<html>"+play.getName()+", you must pay an income tax equivalent to"
				+ "<br>10% of your total wealth or "+parent.getCurrencySymbol()+"200"
				+ "<br>10% of your wealth is "+tax+"</html>";
		//System.out.println("step 3");
		defineComponents();
		//System.out.println("constructor over");
		//parent.paintEvent(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PlayervBankEvent pbe = null;
		if(e.getSource().equals(buttons[0])){
			pbe = new PlayervBankEvent(parent, "<html>You have decided to pay 10%"
											+ "<br>This has been taken from yuor account</html>",play,(-1)*tax);
		}else if(e.getSource().equals(buttons[1])){
			pbe = new PlayervBankEvent(parent, "<html>You have decided to pay "+parent.getCurrencySymbol()+"200"
											+ "<br>This has been taken from yuor account</html>",play,(-200));
		}
		parent.paintEvent(pbe);
		sync(pbe);
		desync();
		
	}

	@Override
	public void defineComponents() {
		buttons = new JComponent[2];
		
		buttons[0] = new JButton(parent.getCurrencySymbol()+tax);
		((JButton)buttons[0]).addActionListener(this);
		
		buttons[1] = new JButton(parent.getCurrencySymbol()+"200");
		((JButton)buttons[1]).addActionListener(this);
		
	}
	
	private int calcTax(){
		return (int)(play.getWealth() * 0.1);
	}

}

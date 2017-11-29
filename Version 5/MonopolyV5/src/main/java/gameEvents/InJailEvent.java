package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JButton;

import main.java.gui.EventPanel;
import main.java.models.Player;

public class InJailEvent extends AbstractEvent {

	public InJailEvent(EventPanel p, Player play) {
		super(p);
		text = "You are in jail. Would you like to"
			+ "\nrole for freedom or pay $50 bail?";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttons[0])){
			
		}else if(e.getSource().equals(buttons[1])){
			
		}
	}

	@Override
	public void defineComponents() {
		buttons = new JComponent[2];
		buttons[0] = new JButton("Role");
		((JButton)buttons[0]).addActionListener(this);
		
		buttons[1] = new JButton("Bail");
		((JButton)buttons[1]).addActionListener(this);
	}

}

package main.java.gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JButton;

import main.java.gui.EventPanel;

public class GoEvent extends AbstractEvent {

	AbstractEvent root;
	
	public GoEvent(EventPanel p, AbstractEvent root) {
		super(p);
		text = "<html>You have passed Go!<br>Collect $200!</html>";
		this.root = root;
		defineComponents();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(buttons[0])){
			desync();
			parent.paintEvent(root);
		}
	}

	@Override
	public void defineComponents() {
		// TODO Auto-generated method stub
		buttons = new JComponent[1];
		buttons[0] = new JButton("Ok");
		((JButton)buttons[0]).addActionListener(this);
	}

}

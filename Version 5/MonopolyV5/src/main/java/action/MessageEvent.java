package main.java.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import main.java.gui.EventPanel;

public class MessageEvent extends Events {

	public MessageEvent(EventPanel p, String message) {
		super(p);
		text = message;
		defineComponents();
		parent.paintEvent(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttons[0])){
			notify();
		}
	}

	@Override
	public void defineComponents() {
		buttons = new JComponent[1];
		buttons[0] = new JButton("OK");
		((JButton)buttons[0]).addActionListener(this);
	}

}

package main.java.listeners;

import main.java.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Main.settingsLaunch();
	}

}

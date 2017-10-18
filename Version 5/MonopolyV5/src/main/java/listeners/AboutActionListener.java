package main.java.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class AboutActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String temp = "made it to about action performed";
		printer(temp);
	}

	public void printer(String stoof){
		JOptionPane.showMessageDialog(null, stoof);
	}
}

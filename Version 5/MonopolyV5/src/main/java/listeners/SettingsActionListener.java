package main.java.listeners;

import main.java.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class SettingsActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame parent = getFrameParent(e);
		Main.settingsLaunch(parent);
	}
	
	private JFrame getFrameParent(Object e){
		System.out.println("getFrameParentCalled: "+e.toString());
		if( (e instanceof Component)){
			if( e instanceof JFrame){
				return (JFrame)e;
			}else{
				Object elder = ((Component) e).getParent();
				return getFrameParent(elder);
			}
		}
		return null;
	}

}

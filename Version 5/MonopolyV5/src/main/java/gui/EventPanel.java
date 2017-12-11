package main.java.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.gameEvents.AbstractEvent;
import main.java.gameEvents.*;
import main.java.action.Runner;

public class EventPanel extends JPanel {
	
	private Runner gameVars;
	private JLabel text;
	private JPanel componentPanel;
	private AbstractEvent currentEvent;
	private MainMenu rootMenu;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8886595624760821726L;
	
	public EventPanel(Runner gv){
		gameVars = gv;
		rootMenu = new MainMenu(this);
		currentEvent = rootMenu;
		paintEvent(rootMenu);
	}
	
	public EventPanel(AbstractEvent ae){
		rootMenu = new MainMenu(this);
		currentEvent = ae;
		rootMenu.forceWait(ae);
	}
	
	public AbstractEvent getEvent(){
		return currentEvent;
	}
	
	
	/*
	 * Repaints this EventPanel expecting to use an entirely new Events object
	 */
	public void paintEvent(AbstractEvent e){
		if(currentEvent.equals(e)){
			return;
		}
		currentEvent = e;
		text.setText(currentEvent.getText());
		
		buildCompPanel();
		
	}
	
	
	public void buildCompPanel(){
		componentPanel = new JPanel();
		for(JComponent c : currentEvent.getComponents()){
			componentPanel.add(c);
		}
		this.add(componentPanel, BorderLayout.CENTER);
	}
	
	/*
	 * Repaints this EventPanel expecting to use the same, but slightly altered, Events object
	 * Only supposed to update the text, not any of the components
	 */
	public void softRepaint(){
		text.setText(currentEvent.getText());
		this.repaint();
	}
	
	public Runner getGlobalVars(){
		return gameVars;
	}
	
}

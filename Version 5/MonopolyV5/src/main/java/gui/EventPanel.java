package main.java.gui;

import javax.swing.JPanel;

import main.java.gameEvents.AbstractEvent;
import main.java.action.Runner;

public class EventPanel extends JPanel {
	
	
	
	private AbstractEvent currentEvent;
	
	
	public AbstractEvent getEvent(){
		return currentEvent;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8886595624760821726L;
	
	public EventPanel(){
	}
	
	/*
	 * Repaints this EventPanel expecting to use an entirely new Events object
	 */
	public void paintEvent(AbstractEvent e){
		currentEvent = e;
	}
	/*
	 * Repaints this EventPanel expecting to use the same, but slightly altered, Events object
	 * Only supposed to update the text, not any of the components
	 */
	public void softRepaint(){
		
	}
	
	public Runner getGlobalVars(){
		return ((GameFrame)this.getParent()).getGlobalVars();
	}
	
}

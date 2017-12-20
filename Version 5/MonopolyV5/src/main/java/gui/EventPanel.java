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
	private JPanel componentPanel = new JPanel();
	private AbstractEvent currentEvent;
	private MainMenu rootMenu;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8886595624760821726L;
	
	public EventPanel(Runner gv){
		//this.setSize(600,200);
		//this.setVisible(true);
		gameVars = gv;
		text = new JLabel();
		//rootMenu = new MainMenu(this);
		//currentEvent = rootMenu;
		//paintEvent(rootMenu);
		//System.out.println("root menu was painted. now where am i?");
		this.setVisible(true);
		this.repaint();
	}
	
	public EventPanel(Runner gv, AbstractEvent ae){
		gameVars = gv;
		//rootMenu = new MainMenu(this);
		//currentEvent = ae;
		//paintEvent(rootMenu);
		//rootMenu.forceWait(ae);
		this.setVisible(true);
	}
	
	public void jumpStartClean(){
		rootMenu = new MainMenu(this);
		//currentEvent = rootMenu;
		paintEvent(rootMenu);
	}
	
	public AbstractEvent getEvent(){
		return currentEvent;
	}
	
	
	/*
	 * Repaints this EventPanel expecting to use an entirely new Events object
	 */
	public void paintEvent(AbstractEvent e){
		//System.out.println("in paintEvent");
		if(currentEvent != null && currentEvent.equals(e)){
			return;
		}
		
		currentEvent = e;
		//System.out.println("current Event = " + currentEvent.toString());
		//System.out.println("current event's text: " + currentEvent.getText());
		text.setText(currentEvent.getText());
		this.add(text, BorderLayout.NORTH);
		buildCompPanel();
		this.repaint();
		gameVars.getFrame().repaint();
	}
	
	
	public void buildCompPanel(){
		componentPanel.removeAll();
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

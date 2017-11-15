package main.java.action;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.gui.EventPanel;

public abstract class Events implements ActionListener{
	protected String text;
	protected JButton[] buttons;
	protected EventPanel parent;
	
	public Events(EventPanel p){
		parent = p;
		text = "Default Event";
	}
	
	public Events(EventPanel p, String t){
		parent = p;
		text = t;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String t){
		text = t;
	}
	
	public JButton[] getButtons(){
		return buttons;
	}
	
	/*
	 * 
	 */
	public abstract void defineButtons();
	
}

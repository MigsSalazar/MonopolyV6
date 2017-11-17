package main.java.action;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.gui.EventPanel;

public abstract class Events implements ActionListener{
	protected String text;
	protected EventPanel parent;
	protected JComponent[] buttons;
	
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
	
	public abstract void defineComponents();
	
}

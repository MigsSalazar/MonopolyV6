package main.java.gameEvents;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

import main.java.gui.EventPanel;

public abstract class AbstractEvent implements ActionListener{
	protected String text;
	protected EventPanel parent;
	protected JComponent[] buttons;
	protected boolean run = true;
	
	public AbstractEvent(EventPanel p){
		parent = p;
		text = "Default Event";
	}
	
	public AbstractEvent(EventPanel p, String t){
		parent = p;
		text = t;
		parent.paintEvent(this);
	}
	
	public JComponent[] getComponents(){
		return buttons;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String t){
		text = t;
	}
	
	public abstract void defineComponents();
	
	public boolean running(){
		return run;
	}
	
	
}

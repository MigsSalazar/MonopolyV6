package main.java.function;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Event extends JDialog{
	private JFrame parent;
	private String text;
	
	public Event(JFrame p, String t){
		parent = p;
		text = t;
		
	}
}

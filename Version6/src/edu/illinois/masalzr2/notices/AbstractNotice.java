package edu.illinois.masalzr2.notices;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

public abstract class AbstractNotice implements ActionListener {
	protected String text;
	protected JComponent[] actions;
	protected ListListener listener;
	
	public AbstractNotice(ListListener ppl){
		text = "This is the default notice";
		listener = ppl;
		defineActions();
	}
	
	public AbstractNotice(String t, ListListener ppl){
		text = t;
		listener = ppl;
		defineActions();
	}
	
	protected abstract void defineActions();
	
	public String getText(){
		return text;
	}
	
	public void setText(String t){
		text = t;
	}
	
	public JComponent[] getActions(){
		return actions;
	}
	
	public void setListListener(ListListener ppl){
		listener = ppl;
	}
	
	
	
}

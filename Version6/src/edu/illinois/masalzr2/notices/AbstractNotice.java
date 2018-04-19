package edu.illinois.masalzr2.notices;

import javax.swing.JComponent;

import edu.illinois.masalzr2.masters.GameVariables;

public abstract class AbstractNotice {
	protected String text;
	protected JComponent[] actions;
	protected GameVariables gameVars;
	
	public AbstractNotice(GameVariables gv){
		gameVars = gv;
		text = "This is the default notice";
	}
	
	public AbstractNotice(GameVariables gv, String t){
		gameVars = gv;
		text = t;
	}
	
	protected abstract void defineActions();
	
	public String getText(){
		return text;
	}
	
	public void setText(String t){
		text = t;
	}
	
	
	
}

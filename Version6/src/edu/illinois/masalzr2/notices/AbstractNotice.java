package edu.illinois.masalzr2.notices;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.masters.LogMate.Logger;

public abstract class AbstractNotice implements ActionListener {
	protected String text;
	protected JComponent[] actions;
	protected ListListener listener;
	protected static Logger LOG = LogMate.LOG;
	
	public AbstractNotice(ListListener ppl){
		text = "This is the default notice";
		listener = ppl;
		//defineActions();
	}
	
	public AbstractNotice(String t, ListListener ppl){
		text = t;
		listener = ppl;
		//defineActions();
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
	
	protected void noticePushPop(AbstractNotice an) {
		LOG.newEntry(this.getClass().getName() + ": "+text);
		LOG.newEntry(this.getClass().getName() + ": noticePushPop: Pushing new " + an.getClass().getName() + " onto listener's queue and poping self");
		listener.pushMe(new ListEvent(an));
		listener.popMe(new ListEvent(this));
	}
	
}

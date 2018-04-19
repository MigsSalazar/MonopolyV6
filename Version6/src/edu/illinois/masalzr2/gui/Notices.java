package edu.illinois.masalzr2.gui;

import java.util.LinkedList;

import edu.illinois.masalzr2.notices.*;

public class Notices {

	private LinkedList<AbstractNotice> notices;
	
	public Notices(){
		notices = new LinkedList<AbstractNotice>();
	}
	
	public void pushNotice(AbstractNotice an){
		notices.add(an);
	}
	
	public AbstractNotice popNotice(){
		return notices.pop();
	}
	
	public void flushNotices(){
		notices = new LinkedList<AbstractNotice>();
	}
	
}

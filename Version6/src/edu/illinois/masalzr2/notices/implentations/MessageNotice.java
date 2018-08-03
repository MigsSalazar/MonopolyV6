package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;

public class MessageNotice extends AbstractNotice{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MessageNotice(String t, ListListener ppl){
		super(t, ppl);
		defineActions();
	} 
	
	@Override
	protected void defineActions() {
		actions = new JComponent[1];
		actions[0] = new JButton("OK");
		((JButton)actions[0]).addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		LOG.newEntry(this.getClass().getName() + ": actionPerformed: popping self from list");
		listener.popMe(new ListEvent(this));
	}
	
}

package edu.illinois.masalzr2.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.illinois.masalzr2.masters.Environment;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;
import edu.illinois.masalzr2.notices.implentations.HomeMenuNotice;
import edu.illinois.masalzr2.notices.implentations.JailNotice;
import lombok.Getter;
import lombok.Setter;

public class Notices implements ListListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<AbstractNotice> noticeList;
	private JPanel noticePanel;
	private AbstractNotice currNotice;
	private Environment gameVars;
	private JPanel components;
	private JLabel notification;
	@Getter @Setter private boolean gameOver = false;
	
	public Notices(Environment gv){
		gameVars = gv;
		noticeList = new LinkedList<AbstractNotice>();
		noticePanel = new JPanel();
		noticePanel.setBorder(defineBorder());
		noticePanel.setVisible(true);
		currNotice = new HomeMenuNotice(this, gameVars);
		noticeList.add(currNotice);
		noticePanel = defineNoticePanel();
		paintNotice(currNotice);
		
	}
	
	public JPanel getNoticePanel(){
		return noticePanel;
	}
	
	private Border defineBorder(){
		Border bottomSpace = BorderFactory.createEmptyBorder(0, 10, 10, 10);
		Border up = BorderFactory.createRaisedBevelBorder();
		Border down = BorderFactory.createLoweredBevelBorder();
		Border bevel = BorderFactory.createCompoundBorder(up, down);
		Border temp = BorderFactory.createCompoundBorder(bottomSpace, bevel);
		return temp;
	}
	
	private void softPaintNotice(){
		notification.setText(currNotice.getText());
		noticePanel.repaint();
	}
	
	private void paintNotice(AbstractNotice an){
		
		components.removeAll();
		for(JComponent c : an.getActions()){
			components.add(c);
		}
		notification.setText(an.getText());
		noticePanel.repaint();
	}
	
	private JPanel defineNoticePanel(){
		JPanel retval = new JPanel();
		retval.setLayout(new BorderLayout());
		components = new JPanel();
		notification = new JLabel();
		notification.setHorizontalAlignment(JLabel.CENTER);
		notification.setVerticalAlignment(JLabel.CENTER);
		retval.add(components, BorderLayout.SOUTH);
		retval.add(notification, BorderLayout.NORTH);
		retval.setPreferredSize(new Dimension(700,70));
		return retval;
	}
	
	public void flushNotices(){
		noticeList.removeAll(noticeList);
	}
	
	@Override
	public void pushMe(ListEvent le) {
		Object src = le.getSource();
		if(src instanceof AbstractNotice){
			((AbstractNotice)src).setListListener(this);
			noticeList.add((AbstractNotice)src);
		}
		
	}

	@Override
	public void popMe(ListEvent le) {
		Object src = le.getSource();
		if(src instanceof AbstractNotice){
			if(src.equals(noticeList.getFirst())){
				noticeList.pop();
			}
			//noticeList.pop();
		}
		if(noticeList.size() == 0){
			if(!gameVars.isInJail(gameVars.getCurrentPlayer()) && !gameOver ) {
				currNotice = new HomeMenuNotice(this, gameVars);
			}else if( gameVars.isInJail(gameVars.getCurrentPlayer()) && !gameOver ){
				currNotice = new JailNotice(this, gameVars);
			}else {
				currNotice = gameVars.getWinner();
			}
			
			noticeList.add(currNotice);
		}else{
			currNotice = noticeList.getFirst();
		}
		paintNotice(currNotice);
		
	}
	
	@Override
	public void pullMe(ListEvent le){
		Object src = le.getSource();
		if(src instanceof AbstractNotice){
			AbstractNotice an = (AbstractNotice)src;
			if(an.equals(noticeList.getFirst())){
				softPaintNotice();
			}
		}
	}
	
}

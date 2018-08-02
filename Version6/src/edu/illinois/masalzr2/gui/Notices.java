package edu.illinois.masalzr2.gui;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.ListListener;
import edu.illinois.masalzr2.notices.implentations.HomeMenuNotice;

public class Notices implements ListListener {

	private LinkedList<AbstractNotice> noticeList;
	private JPanel noticePanel;
	private AbstractNotice currNotice;
	private GameVariables gameVars;
	private JPanel components;
	private JLabel notification;
	
	public Notices(GameVariables gv){
		gameVars = gv;
		noticeList = new LinkedList<AbstractNotice>();
		noticePanel = new JPanel();
		noticePanel.setBorder(defineBorder());
		noticePanel.setVisible(true);
		//noticePanel.setPreferredSize(new Dimension(1280, 150));
		//TODO MAKE A MAIN MENU ABSTRACTNOTICE TO POPULATE THE NOTICELIST
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
		gameVars.repaintFrame();
	}
	
	private void paintNotice(AbstractNotice an){
		
		components.removeAll();
		for(JComponent c : an.getActions()){
			components.add(c);
		}
		notification.setText(an.getText());
		noticePanel.repaint();
		gameVars.repaintFrame();
	}
	
	private JPanel defineNoticePanel(){
		JPanel retval = new JPanel();
		retval.setLayout(new BorderLayout());
		components = new JPanel();
		notification = new JLabel();
		notification.setHorizontalAlignment(JLabel.CENTER);
		retval.add(components, BorderLayout.CENTER);
		retval.add(notification, BorderLayout.NORTH);
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
			currNotice = new HomeMenuNotice(this, gameVars);
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

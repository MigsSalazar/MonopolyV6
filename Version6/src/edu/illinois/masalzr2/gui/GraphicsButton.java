package edu.illinois.masalzr2.gui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class GraphicsButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<ImageIcon> icons;
	
	public void addIcon(ImageIcon i){
		if(icons == null){
			icons = new ArrayList<ImageIcon>();
		}
		icons.add(i);
		paintComponent(this.getGraphics());
	}
	
	public void replaceIcon(ImageIcon i){
		icons = new ArrayList<ImageIcon>();
		if(icons == null){
			icons = new ArrayList<ImageIcon>();
		}
		icons.add(i);
		paintComponent(this.getGraphics());
	}
	
	public void wipeIcons(){
		icons = new ArrayList<ImageIcon>();
		paintComponent(this.getGraphics());
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(icons != null){
			for(ImageIcon i : icons){
				g.drawImage(i.getImage(), 0, 0, null);
			}
		}
	}

}

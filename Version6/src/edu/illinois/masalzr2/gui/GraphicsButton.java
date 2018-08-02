package edu.illinois.masalzr2.gui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GraphicsButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<ImageIcon> icons;
	
	public GraphicsButton(){
		super(new ImageIcon());
	}
	
	public void addIcon(ImageIcon i){
		if(icons == null){
			//System.out.println("ArrayList is null");
			icons = new ArrayList<ImageIcon>();
			//System.out.println("ArrayList is null now?: "+(icons == null));
		}
		icons.add(i);
		//System.out.println(this.getGraphics() == null);
		this.repaint();
	}
	
	public void replaceIcon(ImageIcon i){
		icons = new ArrayList<ImageIcon>();
		icons.add(i);
		this.repaint();
	}
	
	public void wipeIcons(){
		if(icons == null){
			icons = new ArrayList<ImageIcon>();
		}
		if(icons.isEmpty()){
			return;
		}
		icons = new ArrayList<ImageIcon>();
		this.repaint();
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

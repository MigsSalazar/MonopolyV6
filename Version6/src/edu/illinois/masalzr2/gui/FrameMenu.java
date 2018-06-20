package edu.illinois.masalzr2.gui;

import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.GameVariables;

public class FrameMenu extends JMenuBar implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameVariables gameVars;
	
	private JMenu[] menus;
	private JMenuItem[] options;
	
	private Integer failure;
	
	public FrameMenu(GameVariables gv) {
		gameVars = gv;
		buildMenuBar();
	}
	
	public void buildMenuBar() {
		menus = new JMenu[2];
		menus[0] = new JMenu("File");
		menus[1] = new JMenu("About");
		
		options = new JMenuItem[7];
		
		options[0] = new JMenuItem("New");
		options[1] = new JMenuItem("Open");
		options[2] = new JMenuItem("Save");
		options[3] = new JMenuItem("Save As...");
		
		options[4] = new JMenuItem("Help");
		options[5] = new JMenuItem("Details");
		options[6] = new JMenuItem("GitHub");
		
		for(JMenuItem i : options) {
			i.addActionListener(this);
		}
		
		menus[0].add(options[0]);
		menus[0].add(options[1]);
		menus[0].add(options[2]);
		menus[0].add(options[3]);
		
		menus[1].add(options[4]);
		menus[1].add(options[5]);
		menus[1].add(options[6]);
		
		this.add(menus[0]);
		this.add(menus[1]);
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if( arg0.getSource() instanceof JMenuItem ) {
			JMenuItem source = (JMenuItem) arg0.getSource();
			
			if( source.equals(options[0]) ) {
				 
			} else if( source.equals(options[1]) ){
				
			} else if( source.equals(options[2]) ) {
				System.out.println(gameVars.getSaveFile().getPath());
				
				if( gameVars.getSaveFile().getPath().contains( "/resources/newgame.mns" ) ) {
					String newName = JOptionPane.showInputDialog(gameVars.getFrame(), "Name your game!");
					gameVars.setSaveFile(new File(System.getProperty("user.dir")+"/saves/"+newName+".mns" ));
				}
				GameIo.writeOut(gameVars);
				
			} else if( source.equals(options[3]) ) {
				String newName= JOptionPane.showInputDialog(gameVars.getFrame(), "Name your Game!");
				gameVars.setSaveFile(new File(System.getProperty("user.dir")+"/saves/"+newName+".mns" ));
				GameIo.writeOut(gameVars);
			} else if( source.equals(options[4]) ) {
				
			} else if( source.equals(options[5]) ) {
				
			} else if( source.equals(options[6]) ) {
				failure += 10;
				System.out.println(""+failure);
			}
			
		}
		
	}
	
}

package main.java.gui;

import main.java.listeners.*;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * @author Miguel Salazar
 *
 */
public class GameFrame extends JFrame{
	
	private HomePanel home;
	private JMenuBar menuBar;
	private JMenu[] menus;
	private JMenuItem[] menuItems;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8200279150286115532L;
	
	public GameFrame(){
		Container c = this.getContentPane();
		home = new HomePanel();
		c.add(home);
		defineMenus();
	}
	
	/*
	 * JMenuBar Contains menus:
	 * File - Actions - Settings - About
	 * 
	 * File Contains:
	 * New - Save - Load - Exit
	 * 
	 * Actions Contains:
	 * Roll - Trade - Mortgage - Monopolize
	 * 
	 * Settings Should be its own window or better yet, a JDialog:
	 * Full Screen(Radio Button) - Resolution(Menu) - Texture Pack
	 * 
	 * Resolution Contains:
	 * 3-7 resolution settings
	 * the largest resolution that fits
	 * entirely on the screen is the
	 * recommended and default resolution.
	 * This should have an * next to it
	 * 
	 * 
	 */
	
	private void defineMenus(){
		menus = new JMenu[5];
		menuItems = new JMenuItem[8];
		
		for(JMenuItem jmi : menuItems){
			jmi = new JMenuItem();
		}
		
		menuItems[0].addActionListener(new NewActionListener());
		menuItems[0].setText("Next");
		
		menuItems[1].addActionListener(new SaveActionListener());
		menuItems[1].setText("Save");
		
		menuItems[2].addActionListener(new LoadActionListener());
		menuItems[2].setText("Load");
		
		menuItems[3].addActionListener(new ExitActionListener());
		menuItems[3].setText("Exit");
		
		menuItems[4].addActionListener(new RollActionListener());
		menuItems[4].setText("Roll");
		
		menuItems[5].addActionListener(new TradeActionListener());
		menuItems[5].setText("Trade");
		
		menuItems[6].addActionListener(new MortgageActionListener());
		menuItems[6].setText("Mortgage");
		
		menuItems[7].addActionListener(new MonopolizeActionListener());
		menuItems[7].setText("Monopolize");
		
		menuItems[8].addActionListener(new SettingsActionListener());
		menuItems[8].setText("Settings");
		ImageIcon gear = new ImageIcon("/resources/game-assets/smallGear.png");
		menuItems[8].setIcon(gear);
		
	}

	
	
}

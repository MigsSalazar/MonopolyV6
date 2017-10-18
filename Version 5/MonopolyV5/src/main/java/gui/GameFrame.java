package main.java.gui;

import main.java.listeners.*;

import java.awt.BorderLayout;
import java.awt.Container;

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
	
	private BorderLayout border;
	private JMenuBar menuBar;
	private JMenu[] menus;
	private JMenuItem[] menuItems;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8200279150286115532L;
	public GameFrame(){
		Container c = this.getContentPane();
		//home = new HomePanel();
		//c.add(home);
		border = new BorderLayout();
		this.setLayout(border);
		defineMenus();
		this.setJMenuBar(menuBar);
		this.setSize(300, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	/**
	 * Creates, defines, and links all menus, items and bars to
	 * their respective ActionListener.
	 */
	private void defineMenus(){
		menuBar = new JMenuBar();
		menus = new JMenu[2];
		
		menus[0] = new JMenu();
		menus[0].setText("File");
		
		menus[1] = new JMenu();
		menus[1].setText("Actions");
		
		
		menuItems = new JMenuItem[10];
		
		for(int i=0; i<menuItems.length; i++){
			menuItems[i] = new JMenuItem();
		}
		
		menuItems[0].addActionListener(new NewActionListener());
		menuItems[0].setText("New");
		menus[0].add(menuItems[0]);
		
		menuItems[1].addActionListener(new SaveActionListener());
		menuItems[1].setText("Save");
		menus[0].add(menuItems[1]);
		
		menuItems[2].addActionListener(new LoadActionListener());
		menuItems[2].setText("Load");
		menus[0].add(menuItems[2]);
		
		menuItems[3].addActionListener(new ExitActionListener());
		menuItems[3].setText("Exit");
		menus[0].add(menuItems[3]);
		
		menuItems[4].addActionListener(new RollActionListener());
		menuItems[4].setText("Roll");
		menus[1].add(menuItems[4]);
		
		menuItems[5].addActionListener(new TradeActionListener());
		menuItems[5].setText("Trade");
		menus[1].add(menuItems[5]);
		
		menuItems[6].addActionListener(new MortgageActionListener());
		menuItems[6].setText("Mortgage");
		menus[1].add(menuItems[6]);
		
		
		menuItems[7].addActionListener(new MonopolizeActionListener());
		menuItems[7].setText("Monopolize");
		menus[1].add(menuItems[7]);
		
		menuItems[8].addActionListener(new SettingsActionListener());
		menuItems[8].setText("Settings");
		ImageIcon gear = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/smallGear.png");
		menuItems[8].setIcon(gear);
		
		menuItems[9].addActionListener(new AboutActionListener());
		menuItems[9].setText("About");
		ImageIcon mark = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/aboutSmall.png");
		menuItems[9].setIcon(mark);
		
		
		for(int i=0; i<menus.length; i++){
			menuBar.add(menus[i]);
		}
		menuBar.add(menuItems[8]);
		menuBar.add(menuItems[9]);
		
	}

	private void requestBoardPanel(){
		
	}
	
	private void requestEventPanel(){
		
	}
	
	private void requestStatsPanel(){
		
	}
	
	
}

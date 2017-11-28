package main.java.gui;

import main.java.action.Runner;
import main.java.io.GameReader;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gameEvents.*;


/**
 * @author Miguel Salazar
 *
 */
public class GameFrame extends JFrame{
	
	private BorderLayout border;
	private JMenuBar menuBar;
	private JMenu[] menus;
	private JMenuItem[] menuItems;
	private BoardPanel gameBoard;
	private StatsPanel gameStats;
	private EventPanel gameEvents;
	private GameReader readin;
	private Runner globalVars;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8200279150286115532L;
	public GameFrame(boolean flag, Runner gv, GameReader ri){
		//home = new HomePanel();
		//c.add(home);
		globalVars = gv;
		border = new BorderLayout();
		defineMenus();
		readin = ri;
	}
	
	public void setup(){
		this.setLayout(border);
		this.setJMenuBar(menuBar);
		this.setSize(640,690);
		Image icon = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/frameicon.png").getImage();
		this.setIconImage(icon);
		
		try{
			//System.out.println("Started try");
			gameBoard = requestBoardPanel();
			//System.out.println("created board");
			gameStats = requestStatsPanel();
			//System.out.println("created stats");
			gameEvents = requestEventPanel();
			//System.out.println("created events");
		}catch(IOException ioe){
			JOptionPane.showConfirmDialog(null, "Your attempt to generate this game has failed\nAborting all functions");
			ioe.printStackTrace();
			System.exit(1);
		}
		
		Container c = this.getContentPane();
		c.setLayout(border);
		c.add(gameBoard, BorderLayout.CENTER);
		c.add(gameStats, BorderLayout.EAST);
		c.add(gameEvents, BorderLayout.SOUTH);
		
		this.setResizable(false);
		this.setTitle("Migs Monopoly!");
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
		        closeMe();
		    }
		});
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
		menus = new JMenu[1];
		
		menus[0] = new JMenu();
		menus[0].setText("File");
		
		
		menuItems = new JMenuItem[10];
		
		for(int i=0; i<menuItems.length; i++){
			menuItems[i] = new JMenuItem();
		}
		//make a new game from scratch
		menuItems[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Runner newGame = new Runner();
				newGame.startNewGame();
				closeMe();
			}
		});
		menuItems[0].setText("New");
		menus[0].add(menuItems[0]);
		
		//open a prevously saved game
		menuItems[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		menuItems[1].setText("Save");
		menus[0].add(menuItems[1]);
		
		//make a game from a save file
		menuItems[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Runner oldGame = new Runner();
				oldGame.startSavedGame();
				closeMe();
			}
		});
		menuItems[2].setText("Load");
		menus[0].add(menuItems[2]);
		
		//Exits current game
		menuItems[3].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		menuItems[3].setText("Exit");
		menus[0].add(menuItems[3]);
		
		
		
		
		menuItems[4].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Runner.aboutThis();
			}
		});
		menuItems[4].setText("About");
		ImageIcon mark = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/aboutSmall.png");
		menuItems[4].setIcon(mark);
		
		for(int i=0; i<menus.length; i++){
			menuBar.add(menus[i]);
		}
		menuBar.add(menuItems[4]);
		
	}

	private BoardPanel requestBoardPanel() throws IOException{
		//System.out.println("requested board");
		BoardPanel retval = readin.getBoard();
		int[] selection = {4,2,7,5,1,3,0,6};
		String dir = System.getProperty("user.dir");
		retval.pickPlayerPieces(selection, globalVars.getSettings().textureMe());
		retval.firstPaintBoard(globalVars.getSettings().textureMe());
		//System.out.println("board built");
		return retval;
	}
	
	private EventPanel requestEventPanel() throws IOException{
		//System.out.println("requested events");
		return readin.getEvents();
	}
	
	private StatsPanel requestStatsPanel(){
		//System.out.println("requested stats");
		StatsPanel stats = new StatsPanel();
		//TODO implement StatsPanel
		//TODO properly initialize it here
		return stats;
	}
	
	public Runner getGlobalVars(){
		return globalVars;
	}

	public BoardPanel getGameBoard() {
		return gameBoard;
	}

	public StatsPanel getGameStats() {
		return gameStats;
	}

	public EventPanel getGameEvents() {
		return gameEvents;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void closeMe(){
		this.dispose();
	}
	
}

 

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/**
 * @author Miguel Salazar
 *
 */
public class GameFrame extends JFrame implements ActionListener{
	
	private JFrame me;
	private BorderLayout border;
	private JMenuBar menuBar;
	private JMenu[] menus;
	private JMenuItem[] menuItems;
	private BoardPanel gameBoard;
	private StatsPanel gameStats;
	private EventPanel gameEvents;
	private Runner globalVars;
	private Container content;
	private Image titleIcon;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8200279150286115532L;
	public GameFrame(boolean flag, Runner gv){
		me = this;
		//home = new HomePanel();
		//c.add(home);
		globalVars = gv;
		border = new BorderLayout();
		border.setVgap(10);
		border.setHgap(10);
		this.setLayout(border);
		content = this.getContentPane();
		defineMenus();
	}
	
	public void setup(){
		this.setLayout(border);
		this.setJMenuBar(menuBar);
		titleIcon = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/frameicon.png").getImage();
		this.setIconImage(titleIcon);
		this.pack();
		this.setResizable(true);
		this.setTitle("Migs Monopoly!");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			
		    public void windowClosing(WindowEvent e){
		    	//e.getWindow().
		        if(closeMe()){
		        	System.exit(0);
		        }
		        //this.
		    }
		});
	}
	
	
	/**
	 * Creates, defines, and links all menus, items and bars to
	 * their respective ActionListener.
	 */
	private void defineMenus(){
		menuBar = new JMenuBar();
		menus = new JMenu[2];
		
		menus[0] = new JMenu();
		menus[0].setText("File");
		menus[1] = new JMenu("Help");
		
		menuItems = new JMenuItem[10];
		
		for(int i=0; i<menuItems.length; i++){
			menuItems[i] = new JMenuItem();
		}
		
		
		//make a new game from scratch
		menuItems[0].addActionListener(this);
		menuItems[0].setText("New");
		menus[0].add(menuItems[0]);
		
		
		//saves the current game
		menuItems[1].addActionListener(this);
		menuItems[1].setText("Save");
		menus[0].add(menuItems[1]);
		
		
		//open a game from a save file
		menuItems[2].addActionListener(this);
		menuItems[2].setText("Load");
		menus[0].add(menuItems[2]);
		
		
		//Exits current game
		menuItems[3].addActionListener(this);
		menuItems[3].setText("Exit");
		menus[0].add(menuItems[3]);
		
		
		//Opens About pop-up
		menuItems[4].setText("About");
		menuItems[4].addActionListener(this);
		ImageIcon mark = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/aboutSmall.png");
		menuItems[4].setIcon(mark);
		
		menus[1].add(menuItems[4]);
		
		for(int i=0; i<menus.length; i++){
			menuBar.add(menus[i]);
		}
		
		//menuBar.add(menuItems[4]);
		//menuBar.setPreferredSize(new Dimension(,100));
	}
	
	public void giveBoardPanel(BoardPanel bp){
		gameBoard = bp;
		//gameBoard.setSize(600,600);
		gameBoard.setPreferredSize(new Dimension(600,600));
		gameBoard.setMaximumSize(new Dimension(600,600));
		content.add(gameBoard, BorderLayout.CENTER);
	}
	
	public void giveEventPanel(EventPanel ep){
		gameEvents = ep;
		gameEvents.jumpStartClean();
		gameEvents.setPreferredSize(new Dimension(200,75));
		//gameEvents.setSize(600,300);
		//gameEvents.setMinimumSize(new Dimension(600,200));
		content.add(gameEvents, BorderLayout.SOUTH);
		//System.out.println("Added the event panel");
		this.repaint();
	}
	
	
	
	public void giveStatsPanel(StatsPanel sp){
		gameStats = sp;
		content.add(gameStats, BorderLayout.EAST);
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
	
	public boolean closeMe(){
		
		int choice = JOptionPane.showConfirmDialog(gameBoard,  "Would you like to save before you exit?");
		if(choice == JOptionPane.YES_OPTION){
			globalVars.saveThisGame();
			dispose();
			return true;
		}else if(choice == JOptionPane.NO_OPTION){
			dispose();
			return true;
		}else if(choice == JOptionPane.CANCEL_OPTION){
			this.setVisible(true);
			return false;
		}
		return false;
		
		//this.dispose();
	}
	
	public Image getTitleIcon(){
		return titleIcon;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object menuSource = e.getSource();
		
		if(menuSource.equals(menuItems[0]) ){
			Runner newGame = new Runner();
			Settings sets = new Settings(me);
			sets.setup();
			if(newGame.startNewGame(sets)){
				closeMe();
			}
		}else if(menuSource.equals(menuItems[1])){
			globalVars.saveThisGame();
		}else if(menuSource.equals(menuItems[2])){
			Runner oldGame = new Runner();
			oldGame.startSavedGame();
			closeMe();
		}else if(menuSource.equals(menuItems[3])){
			closeMe();
		}else if(menuSource.equals(menuItems[4])){
			Runner.aboutThis();
		}
	}
	
}

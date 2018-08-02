/**
 * 
 */
package edu.illinois.masalzr2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.masters.LogMate.Logger;
import edu.illinois.masalzr2.models.GameToken;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.templates.TemplateGameVars;


/**
 * @author Miguel Salazar
 *
 */
public class PreGameFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7859011893692247775L;
	
	private static Logger LOG = LogMate.LOG;
	
	private static String sep = File.separator;
	private JPanel innerPanel = new JPanel();
	private ImageIcon picture;
	private JButton newGame;
	private JButton loadGame;
	private JButton settings;
	private JButton about;

	public PreGameFrame(){
		//c.setLayout(box);
		
		LOG.newEntry("PreGameFrame: beginning");
		
		picture = new ImageIcon(System.getProperty("user.dir")+sep+"resources"+sep+"topintroimage.png" );
		newGame = new JButton("New Game");
		loadGame = new JButton("Load Game");
		settings = new JButton("Settings");
		about = new JButton("About");
		
		LOG.newEntry("PreGameFrame: beginning: adding listeners and components");
		addListeners();
		JLabel pictureLabel = new JLabel(picture);
		innerPanel.add("picture", pictureLabel);
		innerPanel.add("new game button", newGame);
		innerPanel.add("load game button", loadGame);
		innerPanel.add("settings button", settings);
		innerPanel.add("about button", about);
		LOG.newEntry("PreGameFrame: beginning: setup complete");
	}
	public void start(){
		
		LOG.newEntry("PreGameFrame: Start: beginning start method");
		
		Container c = this.getContentPane();
		c.add(innerPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420,480);
		this.setTitle("Migs Monopoly!");
		//System.out.println(System.getProperty("user.dir")+"/resources/frameicon.png");
		LOG.newEntry("PreGameFrame: Start: adding icon");
		Image icon = new ImageIcon(System.getProperty("user.dir")+sep+"resources"+sep+"frameicon.png").getImage();
		this.setIconImage(icon);
		LOG.newEntry("PreGameFrame: Start: Making Visible");
		this.setVisible(true);
	}
	
	
	private void addListeners(){
		
		LOG.newEntry("Adding pregameframe listeners");
		
		newGame.addActionListener(this);
		
		loadGame.addActionListener(this);
		
		settings.addActionListener(this);
		
		about.addActionListener(this);
	}
	
	private void closeMe(){
		LOG.newEntry("PreGameFrame: closing");
		this.dispose();
	}
	
	private void badFile() {
		JOptionPane.showMessageDialog(this, "The save file you entered is invalid.\nIt is either out of date, corrupted,\nor is not a save file at all.", "Bad file", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LOG.newEntry("PreGameFrame: ActionPerformed: ActionEven receieved");
		if(e.getSource().equals(newGame)){
			LOG.newEntry("PreGameFrame: ActionPerformed: NewGame was pressed");
			GameVariables newerGame = GameIo.newGame();
			
			if(newerGame !=null) {
				
				LOG.newEntry("PreGameFrame: ActionPerformed: NewGame was not null");
				LOG.newEntry("PreGameFrame: ActionPerformed: Finding GameTokens");
				Map<String, GameToken> to = newerGame.getPlayerTokens();
				List<GameToken> tokens = new ArrayList<GameToken>(to.values());
				tokens.sort(GameToken.getTeamComparator());
				
				LOG.newEntry("PreGameFrame: ActionPerformed: developing NewGameStartUp");
				NewGameStartUp ngsup = new NewGameStartUp(this, tokens );
				LOG.newEntry("PreGameFrame: ActionPerformed: Starting NewGameStartUp Dialog");
				ngsup.beginDialog();
				LOG.newEntry("PreGameFrame: ActionPerformed: Dialog has ended, starting game");
				Map<Integer, Player> pl = newerGame.getPlayerID();
				Map<String, Player> pls = newerGame.getPlayers();
				List<String> names = ngsup.getNames();
				newerGame.setPlayerNumber(names.size());
				to.clear();
				LOG.newEntry("PreGameFrame: ActionPerformed: Assets gotten");
				for(int i=0; i<names.size(); i++) {
					LOG.newEntry("PreGameFrame: ActionPerformed: at name "+i + " is "+names.get(0));
					to.put(names.get(i), tokens.get(i));
					pl.get(i).setName(names.get(i));
					pls.remove(""+i);
					pls.put(names.get(i), pl.get(i));
				}
				LOG.newEntry("PreGameFrame: ActionPerformed: Loading assets. sending");
				newerGame.refreshPlayerMaps();
				newerGame.getTurn().setMax(names.size());
				newerGame.buildFrame();
				closeMe();
				
			}else {
				LOG.newEntry("PreGameFrame: ActionPerformed: Bad file found.");
				badFile();
			}
		}else if(e.getSource().equals(loadGame)){
			LOG.newEntry("PreGameFrame: ActionPerformed: Loading was pressed");
			String dir = GameIo.findGame(this);
			if(dir==null) {
				LOG.newEntry("PreGameFrame: ActionPerformed: Directory was found invalid");
				return;
			}
			LOG.newEntry("PreGameFrame: ActionPerformed: Producing saved game");
			GameVariables loadedGame = GameIo.produceSavedGame(dir);
			
			if(loadedGame !=null) {
				LOG.newEntry("PreGameFrame: ActionPerformed: Game was successfully produced");
				loadedGame.buildFrame();
				closeMe();
			}else {
				LOG.newEntry("PreGameFrame: ActionPerformed: Bad file found");
				badFile();
			}
		}else if(e.getSource().equals(settings)){
			LOG.newEntry("PreGameFrame: ActionPerformed: Settings was pressed");
			LOG.newEntry("PreGameFrame: ActionPerformed: Building settings dialog");
			BorderLayout manager = new BorderLayout(2,1);
			manager.setVgap(10);
			manager.setHgap(10);
			JPanel container = new JPanel();
			JDialog setThemUp = new JDialog(this, "Settings", true);
			setThemUp.add(container);
			setThemUp.setPreferredSize(new Dimension(300, 150));
			container.setLayout(manager);
			JLabel refresh = new JLabel("Refresh/Clean the game's system files");
			refresh.setPreferredSize(new Dimension(150,30));
			refresh.setVerticalAlignment(JLabel.CENTER);
			refresh.setHorizontalAlignment(JButton.CENTER);
			refresh.setVerticalTextPosition(JLabel.CENTER);
			refresh.setHorizontalTextPosition(JButton.CENTER);
			JButton clean = new JButton("Clean");
			clean.setSize(new Dimension(20,20));
			clean.setHorizontalAlignment(JButton.CENTER);
			clean.setHorizontalTextPosition(JButton.CENTER);
			clean.setVerticalAlignment(JButton.CENTER);
			clean.setVerticalTextPosition(JButton.CENTER);
			clean.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					clean.setForeground(Color.BLACK);
					TemplateGameVars.produceTemplate();
					clean.setForeground(Color.RED);
					refresh.setText("<html>Refresh/Clean the<br/>game's system files<br/>Files refreshed</html>");
				}
			});
			LOG.newEntry("PreGameFrame: ActionPerformed: Adding components");
			container.add(refresh, BorderLayout.CENTER);
			container.add(clean, BorderLayout.SOUTH);
			setThemUp.pack();
			LOG.newEntry("PreGameFrame: ActionPerformed: Setting dialog visible");
			setThemUp.setVisible(true);
		}
		
	}
}

/**
 * 
 */
package main.java.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.gui.BoardPanel;
import main.java.gui.EventPanel;
import main.java.gui.GameFrame;
import main.java.gui.StatsPanel;
import main.java.io.*;
import main.java.models.*;

/**
 * @author Unknown
 *
 */
public class Runner {
	private Map<String, Player> players;
	private Map<String, Property> properties;
	private Map<String, Suite> coloredProps;
	
	private Set<String> playerNames;
	private Set<String> propNames;
	private Set<String> suiteNames;
	
	private int playerTurn;
	private Roll gameDice;
	private GameFrame game;
	private GameReader gread;
	Settings sets;
	
	public void saveThisGame(){
		if(gread.isNewGame()){
			GameWriter.writeOutNewGame(game, game.getGameBoard(), game.getGameEvents().getEvent() , players, properties);
		}
		
		
	}
	
	
	
	public void startNewGame(){
		playerTurn = 0;
		gameDice = new Roll(this);
		
		gread = new GameReader();
		
		game = new GameFrame(true, this, gread);
		sets = new Settings((JFrame)game);
		try {
			players = gread.getPlayers();
			playerNames = players.keySet();
			properties = gread.getProperties();
			propNames = properties.keySet();
			coloredProps = gread.getSuites(properties);
			suiteNames = coloredProps.keySet();
			EventPanel ep = new EventPanel(this);
			game.giveBoardPanel(requestBoardPanel());
			game.giveEventPanel(ep);
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.setup();
		
	}
	
	public void startSavedGame(){
		playerTurn = 0;
		gameDice = new Roll(this);
		
		gread = new GameReader();
		
		game = new GameFrame(true, this, gread);
		sets = new Settings((JFrame)game);
		try {
			players = gread.getPlayers();
			playerNames = players.keySet();
			properties = gread.getProperties();
			propNames = properties.keySet();
			coloredProps = gread.getSuites(properties);
			suiteNames = coloredProps.keySet();
			
			game.giveBoardPanel(requestBoardPanel());
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.setup();
	}
	
	public String[] getPlayerNames(){
		return (String[])playerNames.toArray();
	}
	
	public String[] getPropName(){
		return (String[])propNames.toArray();
	}
	
	public String[] getSuiteName(){
		return (String[])suiteNames.toArray();
	}
	
	public Map<String, Player> getPlayers(){
		return players;
	}

	public Map<String, Property> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
		propNames = this.properties.keySet();
	}

	public Map<String, Suite> getColoredProps() {
		return coloredProps;
	}

	public void setColoredProps(Map<String, Suite> coloredProps) {
		this.coloredProps = coloredProps;
		suiteNames = this.coloredProps.keySet();
	}

	public int getPlayerTurn() {
		return playerTurn;
	}
	
	public Player currentPlayer(){
		for(String p : playerNames){
			if(playerTurn == players.get(p).getUserID()){
				return players.get(p);
			}
		}
		return null;
	}
	
	public void cyclePlayer(){
		Player current = currentPlayer();
		current.setTurn(false);
		playerTurn = (playerTurn + 1)%players.size();
		current = currentPlayer();
		current.setTurn(true);
	}

	public GameFrame getFrame(){
		return game;
	}
	
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
		playerNames = this.players.keySet();
	}

	public static void aboutThis(){
		Icon pic = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/aboutSmall.png");
		JOptionPane.showMessageDialog(null, "ABOUT"
										+ "\n=========================================="
										+ "\nDeveloper: 				 Miguel Salazar"
										+ "\nAsset Designer:			 Miguel Salazar"
										+ "\nProject Manager:			 Miguel Salazar"
										+ "\n[insert job position here]: Miguel Salazar"
										+ "\nIf you can't tell, I worked on this alone."
										+ "\nAs far as I know, there is very little to"
										+ "\ninclude here besides that I used Google's"
										+ "\nJSON parser GSON to read and write any"
										+ "\nsettings or configuration files. (I can't"
										+ "\nfind the version). I also used JUnit 4.10"
										+ "\nto test my code. I'd like to copyright my"
										+ "\nproject however, I'm not sure how to do"
										+ "\nthat or how to do that for my code. But I"
										+ "\ndoubt anyone could get too much out of my"
										+ "\ncode. I hope you enjoy my game!",
										"About",
										JOptionPane.INFORMATION_MESSAGE,
										pic);
	}

	public Settings getSettings() {
		return sets;
	}

	public Roll getGameDice() {
		return gameDice;
	}
	
	public void makeTemplateEssentials(){
		/*
		private Set<String> playerNames;
		private Set<String> propNames;
		private Set<String> suiteNames;
		
		private int playerTurn;
		private Roll gameDice;
		private GameFrame game;
		private GameReader gread;
		Settings sets;
		*/
		players = new HashMap<String, Player>();
		
		for(int i=0; i<8; i++){
			Player p = new Player(i+1, "Meep"+i );
			players.put("Meep"+i, p);
		}
		playerNames = players.keySet();
		
		
		
		properties = new HashMap<String, Property>();
		coloredProps = new HashMap<String, Suite>();
		
		
	}
	
	public void jailPlayer(Player p){
		jailPlayer(p.getName());
	}
	
	public void jailPlayer(String name){
		
	}
	
	public void movePlayer(Player p, int roll){
		movePlayer(p.getName(),roll);
	}
	
	public void movePlayer(String name, int roll){
		
	}
	
	private BoardPanel requestBoardPanel() throws IOException{
		//System.out.println("requested board");
		BoardPanel retval = gread.getBoard();
		int[] selection = {4,2,7,5,1,3,0,6};
		retval.pickPlayerPieces(selection, sets.textureMe());
		retval.firstPaintBoard(sets.textureMe());
		//System.out.println("board built");
		return retval;
	}
	
	private EventPanel requestEventPanel() throws IOException{
		//System.out.println("requested events");
		return gread.getEvents(this);
	}
	
	private StatsPanel requestStatsPanel(){
		//System.out.println("requested stats");
		StatsPanel stats = new StatsPanel();
		//TODO implement StatsPanel
		//TODO properly initialize it here
		return stats;
	}
	
}

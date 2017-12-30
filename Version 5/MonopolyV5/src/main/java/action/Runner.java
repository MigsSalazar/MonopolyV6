/**
 * 
 */
package main.java.action;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private ArrayList<GameCard> commChest;
	private ArrayList<GameCard> chance;
	
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
		
		game = new GameFrame(true, this);
		sets = new Settings((JFrame)game);
		try {
			players = gread.getPlayers();
			playerNames = players.keySet();
			
			properties = gread.getProperties(gameDice);
			propNames = properties.keySet();
			
			giveProperties();
			
			coloredProps = gread.getSuites(properties);
			suiteNames = coloredProps.keySet();
			
			commChest = gread.getCommChest();
			chance = gread.getChance();
			
			EventPanel ep = new EventPanel(this);
			game.giveBoardPanel(requestBoardPanel());
			game.giveEventPanel(ep);
			game.giveStatsPanel(new StatsPanel(this));
			paintHousing();
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.setup();
		//System.out.println("Runner is done");
		
	}
	
	private void giveProperties(){
		for(Property p : properties.values()){
			if(players.containsKey(p.getOwner())){
				players.get(p.getOwner()).addProperty(p);
			}
		}
	}
	
	public void startSavedGame(){
		playerTurn = 0;
		gameDice = new Roll(this);
		
		gread = new GameReader();
		
		game = new GameFrame(true, this);
		sets = new Settings((JFrame)game);
		try {
			players = gread.getPlayers();
			playerNames = players.keySet();
			properties = gread.getProperties(gameDice);
			propNames = properties.keySet();
			coloredProps = gread.getSuites(properties);
			suiteNames = coloredProps.keySet();
			
			game.giveBoardPanel(requestBoardPanel());
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.setup();
	}
	
	public Set<String> getPlayerNames(){
		return playerNames;
	}
	
	public Set<String> getPropName(){
		return propNames;
	}
	
	public Set<String> getSuiteName(){
		return suiteNames;
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
		if(game != null){
			if(game.getGameStats() != null){
				game.getGameStats().updatePlayers();
			}
		}
		
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
		game.getGameStats().updatePlayers();
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
	
	
	public boolean jailPlayer(Player jailBird){
		if(jailBird == null){
			return false;
		}
		game.getGameBoard().jailPlayer(jailBird.getUserID());
		jailBird.setInJail(true);
		jailBird.resetJailCount();
		jailBird.setPosition(10);
		jailBird.spendANightInJail();
		game.getGameStats().updatePlayers();
		return true;
	}
	
	public void movePlayer(Player p, int roll){
		game.getGameBoard().movePlayer(p.getUserID(), roll);
		game.getGameStats().updatePlayers();
		//movePlayer(p.getName(),roll);
	}
	
	public void paintHousing(){
		BoardPanel board = game.getGameBoard();
		int[][] basePaint = board.getBasePaint();
		for(String key : properties.keySet()){
			Property prop = properties.get(key);
			if(prop instanceof Colored){
				Colored color = (Colored)prop;
				
				int[] coords = findBoardCoord(color.getPosition());
				
				int num = basePaint[coords[0]][coords[1]];
				board.changeIcon(num, coords[0], coords[1]);
				board.changeIcon(num, coords[0], coords[1]+1);
				board.changeIcon(num, coords[0]+1, coords[1]);
				board.changeIcon(num, coords[0]+1, coords[1]+1);
				
				switch(color.getGrade()){
				case 5:	board.changeIcon(num+3, coords[0], coords[1]);
						board.changeIcon(num+2, coords[0], coords[1]+1);
						board.changeIcon(num+4, coords[0]+1, coords[1]);
						board.changeIcon(num+4, coords[0]+1, coords[1]+1);
						break;
				case 4:	board.changeIcon(num+1, coords[0]+1, coords[1]+1);
				case 3:	board.changeIcon(num+1, coords[0], coords[1]+1);
				case 2:	board.changeIcon(num+1, coords[0]+1, coords[1]);
				case 1:	board.changeIcon(num+1, coords[0], coords[1]);
						break;
				}
				
			}
		}
		
	}
	
	private int[] findBoardCoord(int position){
		int[] coords = new int[2];
		
		switch(position){
		case 1: coords[0] = 24;
				coords[1] = 22;
				break;
		case 3:	coords[0] = 24;
				coords[1] = 18;
				break;
		case 6:	coords[0] = 24;
				coords[1] = 12;
				break;
		case 8:	coords[0] = 24;
				coords[1] = 8;
				break;
		case 9:	coords[0] = 24;
				coords[1] = 6;
				break;
		case 11:coords[1] = 6;
				coords[0] = 22;
				break;
		case 13:coords[1] = 6;
				coords[0] = 18;
				break;
		case 14:coords[1] = 6;
				coords[0] = 16;
				break;
		case 16:coords[1] = 6;
				coords[0] = 12;
				break;
		case 18:coords[1] = 6;
				coords[0] = 8;
				break;
		case 19:coords[1] = 6;
				coords[0] = 6;
				break;
		case 21:coords[0] = 4;
				coords[1] = 6;
				break;
		case 23:coords[0] = 4;
				coords[1] = 10;
				break;
		case 24:coords[0] = 4;
				coords[1] = 12;
				break;
		case 26:coords[0] = 4;
				coords[1] = 16;
				break;
		case 27:coords[0] = 4;
				coords[1] = 18;
				break;
		case 29:coords[0] = 4;
				coords[1] = 22;
				break;
		case 31:coords[1] = 24;
				coords[0] = 6;
				break;
		case 32:coords[1] = 24;
				coords[0] = 8;
				break;
		case 34:coords[1] = 24;
				coords[0] = 12;
				break;
		case 37:coords[1] = 24;
				coords[0] = 18;
				break;
		case 39:coords[1] = 24;
				coords[0] = 22;
				break;
		}
		
		return coords;
	}
	
	public void changeDice(int d1, int d2){
		game.getGameBoard().paintDice(d1, d2);
		game.getGameStats().updatePlayers();
	}
	
	public void changeDice(int d1){
		game.getGameBoard().paintDice(d1);
		game.getGameStats().updatePlayers();
	}
	
	private BoardPanel requestBoardPanel() throws IOException{
		//System.out.println("requested board");
		BoardPanel retval = gread.getBoard();
		int[] selection = {4,2,7,5,1,3,0,6};
		retval.pickPlayerPieces(selection, sets.textureMe());
		ArrayList<Player> pl = new ArrayList<Player>(players.values());
		retval.firstPaintBoard(sets.textureMe(), pl);
		//System.out.println("board built");
		return retval;
	}
	

	public ArrayList<GameCard> getCommChest() {
		return commChest;
	}

	public void setCommChest(ArrayList<GameCard> commChest) {
		this.commChest = commChest;
	}

	public ArrayList<GameCard> getChance() {
		return chance;
	}

	public void setChance(ArrayList<GameCard> chance) {
		this.chance = chance;
	}
	
}

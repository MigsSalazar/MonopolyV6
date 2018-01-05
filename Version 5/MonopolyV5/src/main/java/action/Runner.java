/**
 * 
 */
package main.java.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.gui.BoardPanel;
import main.java.gui.EventPanel;
import main.java.gui.GameFrame;
import main.java.gui.NewGameStartUp;
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
	private Settings sets;
	
	public void saveThisGame(){
		if(gread.isNewGame()){
			String gameName = GameWriter.writeOutNewGame(game, game.getGameBoard(), coloredProps, players, properties, commChest, chance, sets);
			//System.out.println("Runner received gameName: "+gameName);
			gread.setLoaded(new File(gameName) );
			gread.setNewGame(false);
		}else{
			if(!GameWriter.writeOutOldGame(this, gread)){
				JOptionPane.showMessageDialog(getFrame(), "Game failed to save!");
			}
		}
		
	}
	
	
	
	public boolean startNewGame(){
		
		game = new GameFrame(true, this);
		sets = new Settings(game);
		sets.setup();
		playerTurn = 0;
		gameDice = new Roll(this);
		
		gread = new GameReader(sets);
		
		
		
		try {
			
			game.giveBoardPanel(requestBoardPanel());
			if(!playerSelection(sets.textureMe())){
				return false;
			}
			
			
			playerNames = players.keySet();
			
			properties = gread.getProperties(gameDice);
			propNames = properties.keySet();
			
			giveProperties();
			
			coloredProps = gread.getSuites(properties);
			suiteNames = coloredProps.keySet();
			
			commChest = gread.getCommChest();
			chance = gread.getChance();
			
			EventPanel ep = new EventPanel(this);
			
			game.giveEventPanel(ep);
			game.giveStatsPanel(new StatsPanel(this));
			paintHousing();
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.setup();
		//System.out.println("Runner is done");
		return true;
		
	}



	private boolean playerSelection(String textureDir) throws IOException {
		String[] playerPiecePaths = game.getGameBoard().getPlayerIconPaths();
		
		ImageIcon[] playerPieceIcons = new ImageIcon[playerPiecePaths.length];
		//String dir = System.getProperty("user.dir");
		for(int i=0; i<playerPiecePaths.length; i++){
			//System.out.println("playerPiecePath at "+i+": "+playerPiecePaths[i]);
			playerPieceIcons[i] = new ImageIcon(textureDir + playerPiecePaths[i]);
		}
		
		ArrayList<Pair<String,Integer>> fillMe = new ArrayList<Pair<String,Integer>>();
		
		while(fillMe.size() == 0){
			NewGameStartUp ngsu = new NewGameStartUp(null, playerPieceIcons, fillMe);
			ngsu.beginDialog();
		}
		if(fillMe.size() == 1){
			return false;
		}
		//System.exit(1);
		
		Map<String, Player> tempPlayers = gread.getPlayers();
		players = new HashMap<String,Player>();
		int[] selection = new int[fillMe.size()];
		for(String s : tempPlayers.keySet()){
			if(tempPlayers.get(s).getUserID() < fillMe.size()){
				Player play = tempPlayers.get(s);
				
				Pair<String,Integer> currPair = fillMe.get(play.getUserID());
				String name = currPair.first;
				play.setName(name);
				
				players.put(play.getName(), play);
				selection[play.getUserID()] = currPair.second;
			}
		}
		
		/*
		int[] selection = {4,2,7,5,1,3,0,6};
		retval.pickPlayerPieces(selection, sets.textureMe());
		ArrayList<Player> pl = new ArrayList<Player>(players.values());
		retval.firstPaintBoard(sets.textureMe(), pl);
		*/
		BoardPanel board = game.getGameBoard();
		board.setPlayerCount(fillMe.size());
		board.pickPlayerPieces(selection, textureDir);
		ArrayList<Player> pl = new ArrayList<Player>(players.values());
		board.firstPaintBoard(textureDir, pl);
		return true;
	}
	
	private void giveProperties(){
		for(Property p : properties.values()){
			if(players.containsKey(p.getOwner())){
				players.get(p.getOwner()).addProperty(p);
			}
		}
	}
	
	public boolean startSavedGame(){
		
		playerTurn = 0;
		gameDice = new Roll(this);
		
		game = new GameFrame(true, this);
		//sets = new Settings((JFrame)game);
		
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"/saved-games/locations-folder/");
		int retval = fc.showOpenDialog(game);
		File fin;
		if(retval == JFileChooser.APPROVE_OPTION){
			fin = fc.getSelectedFile();
		}else{
			return false;
		}
		
		gread = new GameReader(fin);
		
		try {
			if(!requestSettings()){
				return false;
			}
			game.giveBoardPanel(requestBoardPanel());
			
			players = gread.getPlayers();
			playerNames = players.keySet();
			properties = gread.getProperties(gameDice);
			propNames = properties.keySet();
			
			giveProperties();
			
			coloredProps = gread.getSuites(properties);
			suiteNames = coloredProps.keySet();
			
			BoardPanel board = game.getGameBoard();
			ArrayList<Player> pl = new ArrayList<Player>(players.values());
			board.firstPaintBoard(pl);
			
			commChest = gread.getCommChest();
			chance = gread.getChance();
			
			EventPanel ep = new EventPanel(this);
			
			game.giveEventPanel(ep);
			game.giveStatsPanel(new StatsPanel(this));
			paintHousing();
			
			findTurn();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.setup();
		return true;
	}
	
	private void findTurn(){
		for(String s : players.keySet()){
			if(players.get(s).isTurn()){
				playerTurn = players.get(s).getUserID();
			}
		}
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
				
				Pair<Integer,Integer> coords = findBoardCoord(color.getPosition());
				
				int num = basePaint[coords.first][coords.second];
				board.changeIcon(num, coords.first, coords.second);
				board.changeIcon(num, coords.first, coords.second+1);
				board.changeIcon(num, coords.first+1, coords.second);
				board.changeIcon(num, coords.first+1, coords.second+1);
				
				switch(color.getGrade()){
				case 5:	board.changeIcon(num+3, coords.first, coords.second);
						board.changeIcon(num+2, coords.first, coords.second+1);
						board.changeIcon(num+4, coords.first+1, coords.second);
						board.changeIcon(num+4, coords.first+1, coords.second+1);
						break;
				case 4:	board.changeIcon(num+1, coords.first+1, coords.second+1);
				case 3:	board.changeIcon(num+1, coords.first, coords.second+1);
				case 2:	board.changeIcon(num+1, coords.first+1, coords.second);
				case 1:	board.changeIcon(num+1, coords.first, coords.second);
						break;
				}
				
			}
		}
		
	}
	
	private Pair<Integer,Integer> findBoardCoord(int position){
		Pair<Integer,Integer> coords;
		
		switch(position){
		case 1: coords = new Pair<Integer,Integer>(24,22);
				break;
		case 3:	coords = new Pair<Integer,Integer>(24,18);
				break;
		case 6:	coords = new Pair<Integer,Integer>(24,12);
				break;
		case 8:	coords = new Pair<Integer,Integer>(24,8);
				break;
		case 9:	coords = new Pair<Integer,Integer>(24,6);
				break;
		case 11:coords = new Pair<Integer,Integer>(6,22);
				break;
		case 13:coords = new Pair<Integer,Integer>(18,6);
				break;
		case 14:coords = new Pair<Integer,Integer>(16,6);
				break;
		case 16:coords = new Pair<Integer,Integer>(12,6);
				break;
		case 18:coords = new Pair<Integer,Integer>(8,6);
				break;
		case 19:coords = new Pair<Integer,Integer>(6,6);
				break;
		case 21:coords = new Pair<Integer,Integer>(4,6);
				break;
		case 23:coords = new Pair<Integer,Integer>(4,10);
				break;
		case 24:coords = new Pair<Integer,Integer>(4,12);
				break;
		case 26:coords = new Pair<Integer,Integer>(4,16);
				break;
		case 27:coords = new Pair<Integer,Integer>(4,18);
				break;
		case 29:coords = new Pair<Integer,Integer>(4,22);
				break;
		case 31:coords = new Pair<Integer,Integer>(6,24);
				break;
		case 32:coords = new Pair<Integer,Integer>(8,24);
				break;
		case 34:coords = new Pair<Integer,Integer>(12,24);
				break;
		case 37:coords = new Pair<Integer,Integer>(18,24);
				break;
		case 39:coords = new Pair<Integer,Integer>(22,24);
				break;
		default:coords = new Pair<Integer,Integer>(10,10);
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
		return retval;
	}
	
	private boolean requestSettings(){
		sets = null;
		sets = gread.getSettings();
		return sets!=null;
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



	public String getCurrencySymbol() {
		return sets.getSigil();
	}
	
}

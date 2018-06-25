package edu.illinois.masalzr2.masters;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.google.gson.annotations.Expose;

import edu.illinois.masalzr2.gui.*;
import edu.illinois.masalzr2.masters.LogMate.Logger;
import edu.illinois.masalzr2.models.*;
import edu.illinois.masalzr2.templates.TemplateGameVars;

public class GameVariables implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static transient Logger LOG = LogMate.LOG;
	private transient String sep = File.separator;
	
	private transient JFrame frame;
	private transient FrameMenu menuBar;
	private transient Board board;
	private transient Scoreboard scores;
	private transient Notices notices;
	
	private File saveFile;
	
	@Expose private HashMap<String, Player> players;
	@Expose private HashMap<String, Property> properties;
	@Expose private HashMap<String, Suite> suites;
	
	private HashMap<Integer, Player> playerID;
	private HashMap<Integer, Property> propertyPos;
	
	@Expose private PositionIndex propertyPositions;
	
	private Counter turn;
	private ArrayList<Player> turnTable;
	private HashMap<String, Boolean> jailTable;
	private HashMap<String, Integer> jailTimes;
	
	private Counter railCount;
	private Counter utilCount;
	
	@Expose private ArrayList<GameCard> chance;
	@Expose private ArrayList<GameCard> commchest;
	
	private Dice gameDice;
	
	@Expose private int[][] paintByNumbers;
	@Expose private String[] icons;
	private transient ImageIcon[] paintedIcons;
	@Expose private Stamp[][] stampCollection;
	@Expose private HashMap<String, GameToken> playerTokens;
	@Expose private int[][] stickerBook;
	@Expose private String[] stickers;
	private transient ImageIcon[] coloredStickers;
	
	@Expose private String currency;
	@Expose private String texture;
	private Counter houseCount;
	private Counter hotelCount;
	
	private HashMap<String, Object> variables;
	
	public GameVariables() {
		LOG.newEntry("GameVariables called");
		populateVariables();
	}
	
	public void buildFrame() {
		sep = File.separator;
		frame = new JFrame();
		frame.setTitle("Monopoly!");
		frame.setIconImage( (new ImageIcon( System.getProperty("user.dir")+sep+"resources"+sep+"frameicon.png" )).getImage() );
		frame.setLayout(new BorderLayout());
		menuBar = new FrameMenu(this);
		frame.setJMenuBar(menuBar);
		buildBoard();
		notices = new Notices(this);
		scores = new Scoreboard( playerID, currency );
		frame.add(board.getBoard(), BorderLayout.CENTER);
		frame.add(notices.getNoticePanel(), BorderLayout.SOUTH);
		frame.add(scores.getScoreboard(), BorderLayout.EAST);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1280,700));
		//best height for the frame is 650
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		//frame.repaint();
		
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {
				LOG.newEntry("GameVariables: Game frame is closing");
				LOG.finish();
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}
		});
		
		//System.out.println(""+frame.getWidth()+" "+frame.getHeight());
	}
	
	
	private void buildBoard() {
		LOG.newEntry("GameVariables: buildBoard: Building game board");
		board = new Board();
		
		LOG.newEntry("GameVariables: buildBoard: Passing icons and numbers");
		board.setIconNumbers(paintByNumbers);
		paintedIcons = new ImageIcon[icons.length];
		for(int i=0; i<icons.length; i++) {
			//System.out.println(System.getProperty("user.dir")+icons[i]);
			paintedIcons[i] = new ImageIcon(System.getProperty("user.dir") + icons[i]);
			//System.out.println(paintedIcons[i] != null);
		}
		board.setIcons(paintedIcons);
		
		LOG.newEntry("GameVariables: buildBoard: Passing Stickers");
		board.setStickerBook(stickerBook);
		
		coloredStickers = new ImageIcon[stickers.length];
		for(int i=0; i<stickers.length; i++) {
			//System.out.println(System.getProperty("user.dir")+stickers[i]);
			coloredStickers[i] = new ImageIcon(System.getProperty("user.dir") + stickers[i]);
			//System.out.println(coloredStickers[i]);
		}
		
		board.setStickers(coloredStickers);
		
		LOG.newEntry("GameVariables: buildBoard: Passing stamps, dice, and dice assets");
		board.setStamps(stampCollection);
		
		board.setDiceIcons(paintedIcons[1], paintedIcons[2]);
		
		board.activateDice();
		
		board.setDiceLocations(7, 11, 7, 16);
		
		LOG.newEntry("GameVariables: buildBoard: painting display and placing tokens");
		board.paintDisplay();
		placeTokens();
	}
	
	/*
	 */
	
	private void populateVariables() {
		LOG.newEntry("GameVariables: populating variables");
		variables = new HashMap<String, Object>();
		
		variables.put("currency", currency);
		variables.put("housecount", houseCount);
		variables.put("hotelcount", hotelCount);
		variables.put("frame", frame);
		variables.put("proppos", propertyPositions);
		variables.put("texture", texture);
		variables.put("chance", chance);
		variables.put("comchest", commchest);
		variables.put("dice", gameDice);
		variables.put("railcount", railCount);
		variables.put("utilcount", utilCount);
		variables.put("paintbynumbers", paintByNumbers);
		variables.put("playertokens", playerTokens);
		variables.put("icons", icons);
		variables.put("stampcollection", stampCollection);
		variables.put("stickerbook", stickerBook);
		variables.put("stickers", stickers);
		variables.put("properties", properties);
		variables.put("players", players);
		variables.put("suites", suites);
		variables.put("savefile", saveFile);
		
	}
	
	public Object getVariable(String var) {
		LOG.newEntry("GameVariables: Variable requested: " + var);
		return variables.get(var);
	}
	
	public void setVariable(String key, Object value) {
		LOG.newEntry("GameVariables: request to set " + key + " to value " + value.toString());
		if( variables.containsKey(key) ) {
			variables.put(key, value);
		}
	}
	
	public int roll(){
		LOG.newEntry("GameVariables: roll: rolling dice");
		gameDice.roll();
		board.paintDice(gameDice.getLastDice()[0], gameDice.getLastDice()[1]);
		return gameDice.getLastRoll();
	}
	
	public int[] getPropCoords(Property p){
		return getPropCoords(p.getPosition());
	}
	
	public int[] getPropCoords(int p){
		return propertyPositions.getCoordsAtStep(p);
	}

	public boolean jailPlayer(Player p){
		LOG.newEntry("GameVariables: jailPlayer: player " + p.getName() + " has been jailed");
		if(jailTable.get(p.getName()) == null ){
			return false;
		}
		
		jailTable.put(p.getName(), true);
		
		GameToken jailMe = playerTokens.get(p.getName());
		
		int[] newCoords = jailMe.useSpecialtyCase(0);
		
		LOG.newEntry("GameVariables: jailPlayer: moving piece to jail cell");
		board.movePiece(jailMe.getPiece(), newCoords[0], newCoords[1]);
		
		return true;
	}
	
	public Player getCurrentPlayer(){
		return playerID.get(turn.getCount());
	}
	
	public GameCard getRandomCommChest(){
		Random rando = new Random();
		return commchest.get(rando.nextInt(commchest.size()));
		//return commchest.get(0);
	}
	
	public GameCard getRandomChance(){
		Random rando = new Random();
		//return chance.get(0);
		return chance.get(rando.nextInt(chance.size()));
	}
	
	public Player getPlayerByID(int id){
		return playerID.get(id);
	}
	
	public Property getPropertyAt(int p){
		return propertyPos.get(p);
	}
	
	public void nextTurn(){
		do{
			turn.add(1);
		}while( turnTable.get(turn.getCount()).isBankrupt() );
		
	}
	
	public boolean isInJail(Player p){
		return isInJail(p.getName());
	}
	
	public boolean isInJail(String p){
		return jailTable.get(p);
	}
	
	public void placeTokens() {
		for(GameToken gt : playerTokens.values()) {
			
			gt.refreshIcon();
			//System.out.println(gt.getPiece().getDescription());
			board.addPiece(gt.getPiece(), gt.getX(), gt.getY());
			
		}
	}
	
	public void fancyPlayerMove(Player p, int move) {
		Timer time = new Timer(200, null);
		time.addActionListener(new ActionListener() {
			
			int count = move;
			GameToken current = playerTokens.get(p.getName());
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Time clicked");
				visualMove(current, 1);
				current.movePiece(1);
				count--;
				if( count <= 0 ) {
					time.stop();
				}
			}
		});
		time.start();
		
		//movePlayer(p, move);
		
	}
	
	private void visualMove(GameToken current, int move) {
		ImageIcon piece = current.getPiece();
		
		int[] coords = current.getPath().getCoordsAtStep(move + current.getPath().getStep());
		
		board.movePiece(piece, coords[1], coords[0]);
	}
	
	public void movePlayer(Player p, int move){
		movePlayer(p.getName(), move);
	}
	
	public void movePlayer(String p, int move){
		GameToken current = playerTokens.get(p);
		
		current.movePiece(move);
		
		visualMove(current, move);
		
	}

	public void setJailTimes(HashMap<String,Integer> jtimes){
		jailTimes = jtimes;
	}
	
	public int getPlayerJailTime(Player p){
		return getPlayerJailTime(p.getName());
	}
	
	public int getPlayerJailTime(String p){
		return jailTimes.get(p);
	}
	
	
	
	public void parseSuites(String[] colorList) {
		
		LOG.newEntry("GameVariables: parseSuites: populating suites map");
		
		if(propertyPos.size() == 22 && colorList.length == 22) {
			suites = new HashMap<String,Suite>();
			
			suites.put(colorList[0], new Suite((Street)propertyPos.get(1), 	(Street)propertyPos.get(3), 	(Street)null, 				colorList[0]));
			suites.put(colorList[1], new Suite((Street)propertyPos.get(6), 	(Street)propertyPos.get(8), 	(Street)propertyPos.get(3), colorList[1]));
			suites.put(colorList[2], new Suite((Street)propertyPos.get(11), (Street)propertyPos.get(13),	(Street)propertyPos.get(3), colorList[2]));
			suites.put(colorList[3], new Suite((Street)propertyPos.get(16), (Street)propertyPos.get(18),	(Street)propertyPos.get(3), colorList[3]));
			suites.put(colorList[4], new Suite((Street)propertyPos.get(21), (Street)propertyPos.get(23), 	(Street)propertyPos.get(3), colorList[4]));
			suites.put(colorList[5], new Suite((Street)propertyPos.get(26), (Street)propertyPos.get(27), 	(Street)propertyPos.get(3), colorList[5]));
			suites.put(colorList[6], new Suite((Street)propertyPos.get(31), (Street)propertyPos.get(32), 	(Street)propertyPos.get(3), colorList[6]));
			suites.put(colorList[7], new Suite((Street)propertyPos.get(37), (Street)propertyPos.get(37), 	(Street)null, 				colorList[7]));
		}
	}
	
	public void buildCleanGame() {
		saveFile = new File(System.getProperty("user.dir")+sep+"resources"+sep+"newgame.mns");
		
		playerID = new HashMap<Integer, Player>();
		turnTable = new ArrayList<Player>();
		jailTable = new HashMap<String, Boolean>();
		jailTimes = new HashMap<String, Integer>();
		
		players = TemplateGameVars.definePlayers();
		populatePlayerMaps();
		
		turn = new Counter(0,8,0);
		
		jailTable = new HashMap<String,Boolean>();
		jailTimes = new HashMap<String, Integer>();
		for(String s : players.keySet()){
			jailTable.put(s, false);
			jailTimes.put(s, 0);
			
		}
		currency = "$";
		texture = "default";
		houseCount = new Counter(0, 32, 0);
		hotelCount = new Counter(0, 12, 0);
		
		
		railCount = new Counter(0,4,0);
		utilCount = new Counter(0,2,0);
		
		gameDice = new Dice(6,2);
		
		playerTokens = new HashMap<String, GameToken>();
		
		// Rails(4) + utility(2) + 22(streets) = 28
		
		railCount = new Counter(0,4,0);
		utilCount = new Counter(0,2,0);
		
		Property[] props = TemplateGameVars.defineProps(railCount, utilCount, gameDice);
		
		properties = new HashMap<String, Property>();
		propertyPos = new HashMap<Integer, Property>();
		
		for(Property p : props){
			properties.put(new String(p.getName()), p);
			propertyPos.put(p.getPosition(), p);
		}
		
		suites = TemplateGameVars.defineSuites(properties);
		propertyPositions = TemplateGameVars.definePropPositions();
		playerTokens = TemplateGameVars.definePlayerTokens(playerID);
		icons = TemplateGameVars.defineIcons();
		paintByNumbers = TemplateGameVars.definePaintByNumbers();
		commchest = TemplateGameVars.defineCommChest();
		chance = TemplateGameVars.defineChance();
		stampCollection = TemplateGameVars.defineStamps();
		stickerBook = TemplateGameVars.stickerBook();
		stickers = TemplateGameVars.getStickers();
		coloredStickers = new ImageIcon[stickers.length];
		for(int i=0; i<stickers.length; i++) {
			coloredStickers[i] = new ImageIcon(stickers[i]);
		}
		populateVariables();
	}
	
	private void populatePlayerMaps() {
		for(Player noob : players.values()) {
			playerID.put(noob.getId(), noob);
			turnTable.add(noob);
			jailTable.put(noob.getName(), false);
			jailTimes.put(noob.getName(), 0);
		}
			
	}
	
	public void repaintFrame(){
		frame.repaint();
	}
	
}

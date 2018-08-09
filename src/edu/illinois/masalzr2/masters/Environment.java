
package edu.illinois.masalzr2.masters;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.gson.annotations.Expose;

import edu.illinois.masalzr2.gui.Board;
import edu.illinois.masalzr2.gui.FrameMenu;
import edu.illinois.masalzr2.gui.MortgageManager;
import edu.illinois.masalzr2.gui.Notices;
import edu.illinois.masalzr2.gui.Scoreboard;
import edu.illinois.masalzr2.gui.Stamp;
import edu.illinois.masalzr2.gui.UpgradeManager;
import edu.illinois.masalzr2.masters.LogMate.Logger;
import edu.illinois.masalzr2.models.Counter;
import edu.illinois.masalzr2.models.Dice;
import edu.illinois.masalzr2.models.GameCard;
import edu.illinois.masalzr2.models.GameToken;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.PositionIndex;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.models.Street;
import edu.illinois.masalzr2.models.Suite;
import edu.illinois.masalzr2.notices.ListEvent;
import edu.illinois.masalzr2.notices.implentations.GameOverNotice;
import edu.illinois.masalzr2.notices.implentations.MessageNotice;
import edu.illinois.masalzr2.templates.TemplateEnvironment;
import lombok.Data;

/**
 * For all intents and purposes, this is the game instance.
 * This is both a massive model class for the game as well as a controller
 * class used as the go between for all objects. This is the central point
 * of contact for all objects and functions and stores all data for the game.
 * This central hub style of paradigm is to allow new/saved games to be loaded
 * independently and without interference from other prior game instances.
 * 
 * @author Miguel Salazar
 *
 */
@Data
public class Environment implements Serializable, ChangeListener {

	private static final long serialVersionUID = 1L;

	private static transient Logger LOG = LogMate.LOG;
	private transient String sep = File.separator;
	
	private transient JFrame frame;
	private transient FrameMenu menuBar;
	private transient Board board;
	private transient Scoreboard scores;
	private Notices notices;
	
	@Expose private File saveFile;
	
	@Expose private Map<String, Player> players;
	@Expose private Map<String, Property> properties;
	@Expose private Map<String, Suite> suites;
	
	private List<Player> playerID;
	private Map<Integer, Property> propertyPos;
	
	@Expose private PositionIndex propertyPositions;
	
	@Expose private int turn;
	@Expose private boolean limitingTurns;
	@Expose private int turnsLimit;
	private List<Player> turnTable;
	private Map<String, Boolean> jailTable;
	private Map<String, Integer> jailTimes;
	
	@Expose private Counter railCount;
	@Expose private Counter utilCount;
	
	@Expose private List<GameCard> chance;
	@Expose private List<GameCard> commchest;
	
	@Expose private Dice gameDice;
	
	@Expose private int[][] paintByNumbers;
	@Expose private String[] icons;
	private transient ImageIcon[] paintedIcons;
	@Expose private Stamp[][] stampCollection;
	@Expose private Map<String, GameToken> playerTokens;
	@Expose private int[][] stickerBook;
	@Expose private String[] stickers;
	private transient ImageIcon[] coloredStickers;
	
	@Expose private String currency;
	@Expose private String texture;
	@Expose private Counter houseCount;
	@Expose private Counter hotelCount;
	
	private transient Timer time;
	private Player goingBankrupt = null;
	@Expose boolean fancyMoveEnabled;
	
	/**
	 * A courtesy constructor to log the creation of the Environment
	 */
	public Environment() {
		LOG.newEntry("GameVariables called");
	}
	
	/**
	 * Restructures and populates a JFrame and all required assets for the game.
	 * buildFrame also makes the JFrame visible thus begining the game
	 */
	public void buildFrame() {
		sep = File.separator;
		frame = new JFrame();
		frame.setTitle("Monopoly!");
		frame.setIconImage( (new ImageIcon( System.getProperty("user.dir")+sep+"resources"+sep+"frameicon.png" )).getImage() );
		BorderLayout bl = new BorderLayout();
		bl.setHgap(8);
		bl.setVgap(8);
		frame.setLayout(bl);
		menuBar = new FrameMenu(this);
		frame.setJMenuBar(menuBar);
		buildBoard();
		if(notices == null) {
			notices = new Notices(this);
		}
		ImageIcon[] playerIcons = new ImageIcon[playerTokens.size()];
		for(int i=0; i<playerID.size(); i++) {
			playerIcons[i] = playerTokens.get(playerID.get(i).getName()).getPiece();
		}
		scores = new Scoreboard(playerIcons, playerID, currency );
		frame.add(board.getBoard(), BorderLayout.CENTER);
		frame.add(notices.getNoticePanel(), BorderLayout.SOUTH);
		frame.add(scores.getScoreboard(), BorderLayout.EAST);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
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
	}
	
	/**
	 * Builds the largest JPanel in the frame, the game board by repopulating
	 * and refreshing the ImageIcons for the stickers and game tokens as well as
	 * applying the stamps to each board tile
	 * 
	 */
	private void buildBoard() {
		//LOG.append("building board;");
		LOG.newEntry("GameVariables: buildBoard: Building game board");
		board = new Board();
		
		LOG.newEntry("GameVariables: buildBoard: Passing icons and numbers");
		board.setIconNumbers(paintByNumbers);
		paintedIcons = new ImageIcon[icons.length];
		//System.out.println("printing icons");
		for(int i=0; i<icons.length; i++) {
			//System.out.println(System.getProperty("user.dir") + sep + "textures" + sep + texture + sep + icons[i]);
			paintedIcons[i] = new ImageIcon(System.getProperty("user.dir") + sep + "textures" + sep + icons[i]);
			//System.out.println(paintedIcons[i] != null);
		}
		board.setIcons(paintedIcons);
		
		LOG.newEntry("GameVariables: buildBoard: Passing Stickers");
		board.setStickerBook(stickerBook);
		
		coloredStickers = new ImageIcon[stickers.length];
		//System.out.println("printing stickers");
		for(int i=0; i<stickers.length; i++) {
			//System.out.println(System.getProperty("user.dir") + "textures" + sep + stickers[i]);
			coloredStickers[i] = new ImageIcon(System.getProperty("user.dir") + sep + "textures" + sep + stickers[i]);
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
	
	/**
	 * Determines the grade of the property, where to place the house/hotel sticker(s),
	 * and how many to apply.
	 */
	public void paintHousing() {
		for(Suite s : suites.values()) {
			for(Street st : s.sortedByPosition()) {
				
				int[] coords = propertyPositions.getCoordsAtStep(st.getPosition());
				LogMate.LOG.newEntry("GameVariables: Paint Housing: Street "+st.getName()+" at position "+st.getPosition()+" with coord-x="+coords[0]+" and coord-y="+coords[1]);
				board.removePiece(new ImageIcon("house"+st.getName()));
				board.removePiece(new ImageIcon("hotel"+st.getName()));
				switch(st.getGrade()) {
				case 5: board.addPiece(coloredStickers[1], "hotel"+st.getName()+"left", coords[1], coords[0]);
						board.addPiece(coloredStickers[2], "hotel"+st.getName()+"right", coords[1], coords[0]+1);
						board.addPiece(coloredStickers[3], "hotel"+st.getName()+"bot1", coords[1]+1, coords[0]);
						board.addPiece(coloredStickers[3], "hotel"+st.getName()+"bot2", coords[1]+1, coords[0]+1);
						break;
				case 4: board.addPiece(coloredStickers[0], "house"+st.getName()+"4", coords[1]+1, coords[0]+1);
				case 3: board.addPiece(coloredStickers[0], "house"+st.getName()+"3", coords[1]+1, coords[0]);
				case 2: board.addPiece(coloredStickers[0], "house"+st.getName()+"2", coords[1], coords[0]+1);
				case 1: board.addPiece(coloredStickers[0], "house"+st.getName()+"1", coords[1], coords[0]);
					break;
				}
			}
		}
	}
	
	/**
	 * Defines how many players are joining this game. The players maps are cropped until at the passed size
	 * @param num - max size of the players between 2 inclusive and 8 inclusive
	 */
	public void setParticipantSize(int num) {
		if( num < 2 || num > 8) {
			return;
		}
		//System.out.println("num is "+num);
		while(players.size() > num) {
			//System.out.println("players size = "+players.size());
			Player p = playerID.get(players.size()-1);
			players.remove(p.getName());
			playerID.remove(playerID.size()-1);
		}
	}
	
	/**
	 * Rolls the game dice saved within the Environment and paints them to the board
	 * @return int - the sum of the two rolled dice
	 */
	public int roll(){
		
		LOG.newEntry("GameVariables: roll: rolling dice");
		gameDice.roll();
		board.paintDice(gameDice.getLastDice()[0], gameDice.getLastDice()[1]);
		return gameDice.getLastRoll();
	}
	
	/**
	 * Finds the row-column coordinates of the top left corner of a game tile.
	 * This is different from a board tile as each board tile holds one image
	 * where as each game tile represents a property or action on the board. 
	 * 
	 * NOTE: each game tile is accounted for so property positions are taken with all
	 * game tiles taken into account. For example, GO is on game tile/position 0, 
	 * Community Chests are at game tiles/positions 2, 17, and 33, and the properties
	 * between GO and Jail are at 1, 3, 5, 6, 8, and 9
	 * @param p - property represented by the requested coordinates
	 * @return - int[] - of size 2 consisting of the row and column values in that order
	 */
	public int[] getPropCoords(Property p){
		return getPropCoords(p.getPosition());
	}
	
	/**
	 * Helper function to @see {@link #getPropCoords(Property)}
	 * @param p - int value of the property position relative to the board
	 * @return int[] - of size 2 consisting of the row and column values in that order
	 */
	public int[] getPropCoords(int p){
		return propertyPositions.getCoordsAtStep(p);
	}

	/**
	 * Jailing a player causes the fancy move to finish, moving the player token to a specialty case
	 * setting the player's position at position 10, and setting the boolean for the player's jail
	 * NOTE: the player does not keep track of their jail status nor their time spent in jail. That
	 * is recorded within the Environment
	 * @param p - the player to be jailed
	 * @return boolean - true if succeeds and the player is found and jailed. False if at all otherwise
	 */
	public boolean jailPlayer(Player p){
		LOG.newEntry("GameVariables: jailPlayer: player " + p.getName() + " has been jailed");
		if(jailTable.get(p.getName()) == null ){
			return false;
		}
		
		jailTable.put(p.getName(), true);
		resetJail(p);
		
		GameToken jailMe = playerTokens.get(p.getName());
		if(fancyMoveEnabled) {
			while(time.isRunning()){
				ActionListener al = time.getActionListeners()[0];
				if(al instanceof TimerProcess) {
					TimerProcess trueAl = (TimerProcess)al;
					if( trueAl.getCount() <= 0) {
						time.stop();
					}else {
						LogMate.LOG.newEntry("Jail Player: Timer is running: count="+trueAl.getCount()+" mod="+trueAl.getMod()+" move="+trueAl.getMove());
						trueAl.actionPerformed(null);
					}
				}
			}
		}
		
		int[] newCoords = jailMe.useSpecialtyCase(0);
		jailMe.getPath().setStep(10);
		jailMe.movePiece(0);
		
		LOG.newEntry("GameVariables: jailPlayer: moving piece to jail cell");
		board.movePiece(jailMe.getPiece(), newCoords[0], newCoords[1]);
		
		return true;
	}
	
	/**
	 * Finds the player whose turn it is in the game
	 * @return Player - object representing the current player
	 */
	public Player getCurrentPlayer(){
		return playerID.get(turn%players.size());
	}
	
	/**
	 * Selects a random Community Chest card from the list
	 * @return GameCard - representative object for the Community Chest card
	 */
	public GameCard getRandomCommChest(){
		Random rando = new Random();
		return commchest.get(rando.nextInt(commchest.size()));
		//return commchest.get(0);
	}
	
	/**
	 * Selects a random Chance card from the list
	 * @return GameCard - representative object for the Chance card
	 */
	public GameCard getRandomChance(){
		Random rando = new Random();
		return chance.get(rando.nextInt(chance.size()));
		//return chance.get(0);
	}
	
	/**
	 * Requests a player object with the passed id
	 * @param id - id of the requested player
	 * @return Player - the requested object
	 */
	public Player getPlayerByID(int id){
		return playerID.get(id);
	}
	
	/**
	 * Requests a property object at the position requested relative to the board
	 * @param p - The property's position on the board
	 * @return - Property at the passed position
	 */
	public Property getPropertyAt(int p){
		return propertyPos.get(p);
	}
	
	/**
	 * Sets the max number of turns each player has before the game ends on its own.
	 * This is ignore if the is not limiting the number of turns per player.
	 * NOTE: the turn limit is relative but the @see #turn value is absolute.
	 * To show the difference, if turns limit is set to 5 between 8 players,
	 * each player gets 5 turns, but the #turn value will reach 40, incrementing
	 * by 1 as every player's turn finishes or when they are found bankrupt and skipped
	 * @param t int - number of turns alloted to each player
	 */
	public void setTurnsLimit(int t) {
		turnsLimit = t;
	}
	
	/**
	 * Increments the turn until a non-bankrupt player is found
	 */
	public void nextTurn(){
		do{
			turn++;
		}while( turnTable.get(turn%players.size()).isBankrupt() );
		if(limitingTurns && turn >= (turnsLimit*players.size()) ) {
			turnLimitReached();
		}
	}
	
	/**
	 * @see {@link #isInJail(String)}
	 * @param p - the player whose jail status is in question
	 * @return boolean - the current jail status of the requested player
	 */
	public boolean isInJail(Player p){
		return isInJail(p.getName());
	}
	
	/**
	 * @see {@link #isInJail(Player)}
	 * @param p - the name of the player whose jail status is in question
	 * @return boolean - the current jail status of the requested player
	 */
	public boolean isInJail(String p){
		return jailTable.get(p);
	}
	
	/**
	 * Places each player icon on the board at the requested coordinates
	 * according to the player's respective GameToken
	 */
	public void placeTokens() {
		for(GameToken gt : playerTokens.values()) {
			gt.refreshIcon();
			board.addPiece(gt.getPiece(), gt.getX(), gt.getY());
		}
	}

	/**
	 * Incrementally moves the player one game tile at a time according to a Timer instance.
	 * Each fancy move takes 1 second to complete and each step takes 1sec/game tiles to travel.
	 * If fancy move is turned off, this method is not bypassed. Rather, it calls for a basic move
	 * @param p - Player to move
	 * @param move - how far along on the board to move
	 */
	public void fancyPlayerMove(Player p, int move) {
		if( !fancyMoveEnabled ) {
			visualMove(playerTokens.get(p.getName()), move);
			playerTokens.get(p.getName()).movePiece(move);
			return;
		}
		if(time == null) {
			time = new Timer(100, null);
		}
		notices.pushMe(new ListEvent(new MessageNotice("You rolled a "+move, notices)));
		while(time.isRunning()){
			ActionListener al = time.getActionListeners()[0];
			if(al instanceof TimerProcess) {
				TimerProcess trueAl = (TimerProcess)al;
				if( trueAl.getCount() <= 0) {
					time.stop();
				}else {
					LogMate.LOG.newEntry("Fancy Player Move: Timer is running: count="+trueAl.getCount()+" mod="+trueAl.getMod()+" move="+trueAl.getMove());
					trueAl.actionPerformed(null);
				}
			}
		}
		time = new Timer(1000/move, null);
		time.addActionListener(new TimerProcess(move, p));
		time.start();
		
		//movePlayer(p, move);
		
	}
	
	/**
	 * Visually moves the player token on the board without the changing 
	 * the Player object's position value or the GameToken position
	 * @param current - GameToken containing the movement data and piece belonging to the player
	 * @param move - int value defining the distance to travel on the board
	 */
	private void visualMove(GameToken current, int move) {
		ImageIcon piece = current.getPiece();
		
		int[] coords = current.getPath().getCoordsAtStep( (move + current.getPath().getStep())%current.getPath().stepCount() );
		
		board.movePiece(piece, coords[1], coords[0]);
	}
	
	/**
	 * Moves the player's associated piece without changing the player's position value.
	 * Does change the GameToken's position
	 * @see {@link #movePlayer(String, int)}}
	 * @param p - Player the associated player object of the board piece 
	 * @param move - int the distance the piece needs to travel
	 */
	public void movePlayer(Player p, int move){
		movePlayer(p.getName(), move);
	}
	
	/**
	 * Moves the player's associated piece without changing the player's position value.
	 * Does change the GameToken's position
	 * @see {@link #movePlayer(Player, int)}}
	 * @param p - String the name of the associated player object of the board piece 
	 * @param move - int the distance the piece needs to travel
	 */
	public void movePlayer(String p, int move){
		LogMate.LOG.flush();
		GameToken current = playerTokens.get(p);
		
		current.movePiece(move);
		
		visualMove(current, move);
		
	}
	
	/**
	 * Metaphorically spends the night in jail. 
	 * Increments the jailTimes table at the player's entry and returns the new value.
	 * @see {@link #nightInJail(String)}
	 * @param p Player - the player who spent the night in jail
	 * @return int - the number of nights spent in the jail
	 */
	public int nightInJail(Player p) {
		return nightInJail(p.getName());
	}
	
	/**
	 * Metaphorically spends the night in jail. 
	 * Increments the jailTimes table at the player's entry and returns the new value.
	 * @see {@link #nightInJail(Player)}
	 * @param p String - name of the player who spent the night in jail
	 * @return int - the number of nights spent in the jail
	 */
	public int nightInJail(String p) {
		jailTimes.put(p, jailTimes.get(p)+1);
		return jailTimes.get(p);
	}
	
	/**
	 * Refreshes the entry of the player within the jailTimes map.
	 * @see {@link #resetJail(String)}
	 * @param p Player - the player whose jailTime entry to reset
	 */
	public void resetJail(Player p) {
		resetJail(p.getName());
	}
	
	/**
	 * Refreshes the entry of the player within the jailTimes map.
	 * @see {@link #resetJail(Player)}
	 * @param p Player - the player whose jailTime entry to reset
	 */
	public void resetJail(String p) {
		jailTimes.put(p, 0);
	}
	
	/**
	 * Metaphorically releases the player passed player from jail
	 * @see {@link #releaseJailedPlayer(String)}
	 * @param p Player - the player who will be released from jail
	 */
	public void releaseJailedPlayer(Player p) {
		releaseJailedPlayer(p.getName());
	}
	
	/**
	 * On top of setting the player's jail status to false,
	 * the player piece is also painted as outside of the jail
	 * and calls {@link #releaseJailedPlayer(String)} to resent the player's jail time
	 * @param p
	 */
	public void releaseJailedPlayer(String p) {
		jailTable.put(p, false);
		
		resetJail(p);
		
		LOG.newEntry("GameVariables: Release Jailed Player: player " + p+ " has been released from jail");
		
		GameToken jailMe = playerTokens.get(p);
		
		jailMe.getPath().setStep(10);
		jailMe.movePiece(0);
		
		LOG.newEntry("GameVariables: Release Jailed Player: moving piece to visiting jail");
		board.movePiece(jailMe.getPiece(), jailMe.getX(), jailMe.getY());
	}
	
	/**
	 * Used when generating an mns, json, or a fresh clean game. Calls
	 * the static methods of @see edu.illinois.masalzr2.TemplateEnvironment
	 * to generate the more complex needed variables
	 */
	public void buildCleanGame() {
		saveFile = new File(System.getProperty("user.dir")+sep+"resources"+sep+"packages"+sep+"default.mns");
		players = TemplateEnvironment.definePlayers();
		refreshPlayerCollections();
		
		fancyMoveEnabled = true;
		
		turn = 0;
		limitingTurns = false;
		turnsLimit = 10;
		
		currency = "$";
		texture = "default";
		houseCount = new Counter(0, 33, 0);
		hotelCount = new Counter(0, 13, 0);
		
		gameDice = new Dice(6,2);
		
		// Rails(4) + utility(2) + 22(streets) = 28
		
		railCount = new Counter(0,5,0);
		utilCount = new Counter(0,3,0);
		
		properties = TemplateEnvironment.defineProps(railCount, utilCount, gameDice);
		
		refreshPropertyCollections();
		
		suites = TemplateEnvironment.defineSuites(properties);
		propertyPositions = TemplateEnvironment.definePropPositions();
		playerTokens = TemplateEnvironment.definePlayerTokens(playerID);
		icons = TemplateEnvironment.defineIcons();
		
		paintByNumbers = TemplateEnvironment.definePaintByNumbers();
		commchest = TemplateEnvironment.defineCommChest();
		chance = TemplateEnvironment.defineChance();
		stampCollection = TemplateEnvironment.defineStamps();
		stickerBook = TemplateEnvironment.stickerBook();
		stickers = TemplateEnvironment.getStickers();
		
		refreshAllImages();
	}
	
	/**
	 * Calls all ImageIcons used to design the board to refresh their
	 * images from their paths
	 */
	public void refreshAllImages() {
		paintedIcons = new ImageIcon[icons.length];
		coloredStickers = new ImageIcon[stickers.length];
		for(int i=0; i< icons.length; i++) {
			paintedIcons[i] = new ImageIcon(System.getProperty("user.dir") + sep + "textures" + sep + texture + sep + icons[i]);
			LogMate.LOG.newEntry(System.getProperty("user.dir") + sep + "textures" + sep + texture + sep + icons[i]);
		}
		for(int i=0; i<stickers.length; i++) {
			coloredStickers[i] = new ImageIcon(System.getProperty("user.dir") + sep + "textures" + sep + texture + sep + stickers[i]);
			LogMate.LOG.newEntry(System.getProperty("user.dir") + sep + "textures" + sep + texture + sep + stickers[i]);
		}
	}
	
	/**
	 * Takes the {@link #properties} master Property map
	 * and populates the {@link #propertyPos} map
	 */
	public void refreshPropertyCollections() {
		propertyPos = new HashMap<Integer, Property>();
		
		for(Property p : properties.values()){
			propertyPos.put(p.getPosition(), p);
			
		}
	}
	
	/**
	 * Takes the {@link #players} master Player map and
	 * populates the {@link #playerID}, {@link #turnTable}, {@link #jailTable}, and {@link #jailTimes} 
	 * maps as well calling {@link #playerID} and {@link #turnTable} to be sorted by ID
	 */
	public void refreshPlayerCollections() {
		playerID = new ArrayList<Player>();
		turnTable = new ArrayList<Player>();
		jailTable = new HashMap<String, Boolean>();
		jailTimes = new HashMap<String, Integer>();
		
		for(Player noob : players.values()) {
			playerID.add(noob.getId(), noob);
			jailTable.put(noob.getName(), false);
			jailTimes.put(noob.getName(), 0);
			turnTable.add(noob);
			noob.addChangeListener(this);
		}
		
		playerID.sort(Player.ID_ORDER);
		turnTable.sort(Player.ID_ORDER);
		
	}
	
	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
	 * Listens whenever a player has changed and checks to see if they have
	 * gone bankrupt. If their cash is below 0 but they liquidizable wealth
	 * can compensate, the player is forced to liquidize assets until all
	 * debts are paid. If not, the player is bankrupt and looses the game
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(e.getSource() instanceof Player) {
			Player p = (Player)e.getSource();
			if(p.equals(goingBankrupt)) {
				return;
			}
			int cash = p.getCash();
			if(cash <= 0 ) {
				goingBankrupt = p;
				if( p.getLiquidationWorth() + cash > 0 ) {
					playerIsSalvageable(p);
				}else {
					bankruptPlayer(p);
				}
			}
			
		}
		goingBankrupt = null;
	}
	
	/**
	 * Sets the player's bankruptcy status to true and sells all of their properties in an
	 * auction according to the Hasbro rules until all assets are sold
	 * @param p Player - player to bankrupt
	 * @return int - the number of players remaining in the game. 
	 */
	private int bankruptPlayer(Player p) {
		p.setBankrupt(true);
		int count = 0;
		int id = -1;
		
		for(int i=0; i<playerID.size(); i++) 
			if(!playerID.get(i).isBankrupt()) {
				count++;
				id = i;
			}
		if( count > 1 ) {
			JOptionPane.showMessageDialog(frame, p.getName()+" has gone bankrupt! They have neither enough cash on hand or"
												+ "\nliquidizable wealth to pay their debts. Anything left in the chain of events"
												+ "\nwill be completed but any and all properties owned by "+p.getName()
												+ "\nwill be put to auction and all must be sold.");
			p.setCash(0);
			Map<String, Property> props = p.getProps();
			for(Property pr : props.values()) {
				if(pr instanceof Street) {
					((Street) pr).setGrade(0);
				}
				notices.pushMe(new ListEvent(pr));
				p.removeProp(pr);
			}
		}else {
			notices.setGameOver(true);
			return id;
		}
		return -1;
	}
	
	private int turnLimitReached() {
		int wealth = 0;
		int id = -1;
		for(Player p : players.values()) {
			if(wealth < p.getWealth()) {
				wealth = p.getWealth();
				id = p.getId();
			}
		}
		notices.setGameOver(true);
		return id;
	}
	
	public GameOverNotice getWinner() {
		if(turn >= turnsLimit*players.size()) {
			return new GameOverNotice(notices, playerID.get(turnLimitReached()), true);
		}else {
			return new GameOverNotice(notices, playerID.get(turnLimitReached()), false);
		}
	}

	private void playerIsSalvageable(Player p) {
		while(p.getCash() <= 0) {
			String[] options = {"Downgrade", "Mortgage"};
			int picked = JOptionPane.showOptionDialog(frame, 
					p.getName()+" does not have enough cash on hand."
							+ "\nSell your assets until you can pay off your debt"
							+ "\nof "+currency+p.getCash(), 
					"Liquidate", 
					JOptionPane.DEFAULT_OPTION, 
					JOptionPane.WARNING_MESSAGE, 
					null, 
					options, 
					null);
			if(picked != JOptionPane.CANCEL_OPTION) {
				switch(options[picked]) {
				case "Downgrade": UpgradeManager um = new UpgradeManager(this, p);
					um.beginManager();
					break;
				case "Mortgage": MortgageManager mm = new MortgageManager(this, p);
					mm.beginManager();
					break;
				}
			}
			if(p.getCash() <= 0) {
				JOptionPane.showMessageDialog(null, "You have not sold enough assets!\nRemaining balance: "+currency+p.getCash());
			}
		}
	}

	private final class TimerProcess implements ActionListener {
		private final int move;
		int mod;
		int count;
		GameToken current;

		private TimerProcess(int move, Player p) {
			this.move = move;
			mod = (move > 0) ? 1 : -1;
			count = move * mod;
			current = playerTokens.get(p.getName());
		}
		
		public int getMove() {
			return move;
		}
		
		public int getMod() {
			return mod;
		}
		
		public int getCount() {
			return count;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Time clicked");
			LogMate.LOG.newEntry("FancyPlayerMove: Count="+count+" mod="+mod+" move="+move);
			visualMove(current, mod);
			current.movePiece(mod);
			count--;
			if( count <= 0 ) {
				time.stop();;
			}
		}
	}
	
}

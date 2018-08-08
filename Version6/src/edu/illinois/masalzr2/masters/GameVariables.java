
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
import edu.illinois.masalzr2.templates.TemplateGameVars;
import lombok.Data;

@Data
public class GameVariables implements Serializable, ChangeListener{

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
	
	private Map<Integer, Player> playerID;
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
	@Expose boolean fancyMoveEnabled;
	
	public GameVariables() {
		LOG.newEntry("GameVariables called");
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()+this.hashCode();
	}
	
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
		System.out.println("Break Point!");
	}
	
	
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
	 * 
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
	/**/
	public void setPlayerNumber(int num) {
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
	
	public boolean isTextureInDir(){
		File f = new File(texture);
		if(!f.exists()){
			f = new File(System.getProperty("user.dir")+sep+"textures"+sep+texture);
		}
		return f.exists();
	}
	
	public String getTextureDir(){
		File f = new File(texture);
		if(!f.exists()){
			f = new File(System.getProperty("user.dir")+sep+"textures"+sep+texture);
		}
		return f.exists() ? f.getAbsolutePath() : "";
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
	
	public Player getCurrentPlayer(){
		return playerID.get(turn%players.size());
	}
	
	public GameCard getRandomCommChest(){
		Random rando = new Random();
		return commchest.get(rando.nextInt(commchest.size()));
		//return commchest.get(0);
	}
	
	public GameCard getRandomChance(){
		Random rando = new Random();
		return chance.get(rando.nextInt(chance.size()));
		//return chance.get(0);
	}
	
	public Player getPlayerByID(int id){
		return playerID.get(id);
	}
	
	public Property getPropertyAt(int p){
		return propertyPos.get(p);
	}
	
	public void setTurnsLimit(int t) {
		turnsLimit = t;
	}
	
	public void nextTurn(){
		do{
			turn++;
		}while( turnTable.get(turn%players.size()).isBankrupt() );
		if(limitingTurns && turn >= (turnsLimit*players.size()) ) {
			turnLimitReached();
		}
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
	
	private void visualMove(GameToken current, int move) {
		ImageIcon piece = current.getPiece();
		
		int[] coords = current.getPath().getCoordsAtStep( (move + current.getPath().getStep())%current.getPath().stepCount() );
		
		board.movePiece(piece, coords[1], coords[0]);
	}
	
	public void movePlayer(Player p, int move){
		movePlayer(p.getName(), move);
	}
	
	public void movePlayer(String p, int move){
		LogMate.LOG.flush();
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
	
	public int nightInJail(Player p) {
		return nightInJail(p.getName());
	}
	
	public int nightInJail(String p) {
		jailTimes.put(p, jailTimes.get(p)+1);
		return jailTimes.get(p);
	}
	
	public void resetJail(Player p) {
		resetJail(p.getName());
	}
	
	public void resetJail(String p) {
		jailTimes.put(p, 0);
	}
	
	public void releaseJailedPlayer(Player p) {
		releaseJailedPlayer(p.getName());
	}
	
	public void releaseJailedPlayer(String p) {
		jailTable.put(p, false);
		
		LOG.newEntry("GameVariables: Release Jailed Player: player " + p+ " has been released from jail");
		
		GameToken jailMe = playerTokens.get(p);
		
		jailMe.getPath().setStep(10);
		jailMe.movePiece(0);
		
		LOG.newEntry("GameVariables: Release Jailed Player: moving piece to visiting jail");
		board.movePiece(jailMe.getPiece(), jailMe.getX(), jailMe.getY());
	}
	
	public void buildCleanGame() {
		saveFile = new File(System.getProperty("user.dir")+sep+"resources"+sep+"packages"+sep+"default.mns");
		players = TemplateGameVars.definePlayers();
		refreshPlayerCollections();
		
		fancyMoveEnabled = true;
		
		turn = 0;
		limitingTurns = false;
		turnsLimit = 10;
		
		currency = "$";
		texture = "default";
		houseCount = new Counter(0, 32, 0);
		hotelCount = new Counter(0, 12, 0);
		
		gameDice = new Dice(6,2);
		
		// Rails(4) + utility(2) + 22(streets) = 28
		
		railCount = new Counter(0,4,0);
		utilCount = new Counter(0,2,0);
		
		properties = TemplateGameVars.defineProps(railCount, utilCount, gameDice);
		
		refreshPropertyCollections();
		
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
		
		refreshAllImages();
	}
	
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
	
	public void refreshPropertyCollections() {
		propertyPos = new HashMap<Integer, Property>();
		
		for(Property p : properties.values()){
			propertyPos.put(p.getPosition(), p);
		}
	}
	
	public void refreshPlayerCollections() {
		playerID = new HashMap<Integer, Player>();
		turnTable = new ArrayList<Player>();
		jailTable = new HashMap<String, Boolean>();
		jailTimes = new HashMap<String, Integer>();
		
		for(Player noob : players.values()) {
			playerID.put(noob.getId(), noob);
			jailTable.put(noob.getName(), false);
			jailTimes.put(noob.getName(), 0);
			noob.addChangeListener(this);
		}
		
		for(int i=0; i<playerID.size(); i++) {
			turnTable.add(playerID.get(i));
		}
			
	}
	
	public void repaintFrame(){
		frame.repaint();
	}
	
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
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(e.getSource() instanceof Player) {
			Player p = (Player)e.getSource();
			int cash = p.getCash();
			if(cash <= 0 ) {
				if( p.getLiquidationWorth() + cash > 0 ) {
					playerIsSalvageable(p);
				}else {
					bankruptPlayer(p);
				}
			}
			
		}
		
	}
}

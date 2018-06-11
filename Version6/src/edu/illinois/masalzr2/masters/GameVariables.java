package edu.illinois.masalzr2.masters;

import java.awt.BorderLayout;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import edu.illinois.masalzr2.gui.*;
import edu.illinois.masalzr2.models.*;
import edu.illinois.masalzr2.templates.TemplateGameVars;

public class GameVariables implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient JFrame frame;
	private transient Board board;
	//private JPanel stats;
	private transient Notices notices;
	
	private File saveFile;
	
	private HashMap<String, Player> players;
	private HashMap<String, Property> properties;
	private HashMap<String, Suite> suites;
	
	private HashMap<Integer, Player> playerID;
	private HashMap<Integer, Property> propertyPos;
	
	private PositionIndex propertyPositions;
	
	private Counter turn;
	private ArrayList<Player> turnTable;
	private HashMap<String, Boolean> jailTable;
	private HashMap<String, Integer> jailTimes;
	
	private Counter railCount;
	private Counter utilCount;
	
	private ArrayList<GameCard> chance;
	private ArrayList<GameCard> commchest;
	
	private Dice gameDice;
	
	private int[][] paintByNumbers;
	private String[] icons;
	private transient ImageIcon[] paintedIcons;
	private Stamp[][] stampCollection;
	private HashMap<String, GameToken> playerTokens;
	private int[][] stickerBook;
	private String[] stickers;
	private transient ImageIcon[] coloredStickers;
	
	private String currency;
	private String texture;
	private Counter houseCount;
	private Counter hotelCount;
	
	public void buildFrame() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		buildBoard();
		notices = new Notices(this);
		//board.getBoard().setPreferredSize(new D);
		frame.add(board.getBoard(), BorderLayout.CENTER);
		frame.add(notices.getNoticePanel(), BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		//frame.repaint();
		
		frame.setVisible(true);
		System.out.println(""+frame.getWidth()+" "+frame.getHeight());
	}
	

	private void buildBoard() {
		board = new Board();
		
		
		board.setIconNumbers(paintByNumbers);
		paintedIcons = new ImageIcon[icons.length];
		for(int i=0; i<icons.length; i++) {
			paintedIcons[i] = new ImageIcon(System.getProperty("user.dir") + "/" + icons[i]);
		}
		board.setIcons(paintedIcons);
		
		
		board.setStickerBook(stickerBook);
		
		coloredStickers = new ImageIcon[stickers.length];
		for(int i=0; i<stickers.length; i++) {
			coloredStickers[i] = new ImageIcon(System.getProperty("user.dir") + "/" + stickers[i]);
		}
		
		board.setStickers(coloredStickers);
		
		board.setStamps(stampCollection);
		
		board.setDiceIcons(paintedIcons[1], paintedIcons[2]);
		board.paintDisplay();
	}
	
	public String getCurrencySymbol(){
		return currency;
	}
	
	public void setCurrentcySymbol(String c) {
		currency = c;
	}
	
	public Counter getHouseCount(){
		return houseCount;
	}
	
	public Counter getHotelCount(){
		return hotelCount;
	}
	
	public boolean isTextureInDir(){
		File f = new File(texture);
		if(!f.exists()){
			f = new File(System.getProperty("user.dir")+"/"+texture);
		}
		return f.exists();
	}
	
	public String getTextureName(){
		return texture.substring(texture.lastIndexOf("/"), texture.length());
	}
	
	public String getTextureDir(){
		File f = new File(texture);
		if(!f.exists()){
			f = new File(System.getProperty("user.dir")+"/"+texture);
		}
		return f.exists() ? f.getAbsolutePath() : "";
	}
	
	public int roll(){
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
		
		if(jailTable.get(p.getName()) == null ){
			return false;
		}
		
		jailTable.put(p.getName(), true);
		
		GameToken jailMe = playerTokens.get(p.getName());
		
		int[] newCoords = jailMe.useSpecialtyCase(0);
		
		board.movePiece(jailMe.getPiece(), newCoords[0], newCoords[1]);
		
		//TODO notify the stats panel of the player's incarceration
		return true;
	}
	
	public Player getCurrentPlayer(){
		return playerID.get(turn.getCount());
	}
	
	public GameCard getRandomCommChest(){
		Random rando = new Random();
		return commchest.get(rando.nextInt(commchest.size()));
	}
	
	public GameCard getRandomChance(){
		Random rando = new Random();
		return chance.get(rando.nextInt(chance.size()));
	}
	
	public Dice getDice(){
		return gameDice;
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
	
	public void movePlayer(Player p, int move){
		movePlayer(p.getName(), move);
	}
	
	public void movePlayer(String p, int move){
		GameToken current = playerTokens.get(p);
		
		ImageIcon piece = current.getPiece();
		
		current.movePiece(move);
		
		board.movePiece(piece, current.getX(), current.getY());
		
	}

	public Counter getRailCount() {
		return railCount;
	}

	public void setRailCount(Counter railCount) {
		this.railCount = railCount;
	}

	public Counter getUtilCount() {
		return utilCount;
	}

	public void setUtilCount(Counter utilCount) {
		this.utilCount = utilCount;
	}

	public int[][] getPaintByNumbers() {
		return paintByNumbers;
	}

	public void setPaintByNumbers(int[][] paintByNumbers) {
		this.paintByNumbers = paintByNumbers;
	}

	public String[] getIcons() {
		return icons;
	}

	public void setIcons(String[] icons) {
		this.icons = icons;
	}

	public Stamp[][] getStampCollection() {
		return stampCollection;
	}

	public void setStampCollection(Stamp[][] stampCollection) {
		this.stampCollection = stampCollection;
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
	
	/*
	 * private File saveFile;
	
	private HashMap<String, Player> players;
	private HashMap<String, Property> properties;
	private HashMap<String, Suite> suites;
	 */
	
	public HashMap<String,Property> getProperties(){
		return properties;
	}
	
	public void setProperties(HashMap<String, Property> pr){
		properties = pr;
	}
	
	public HashMap<String,Player> getPlayers(){
		return players;
	}
	
	public void setPlayers(HashMap<String, Player> pl){
		players = pl;
	}
	
	public HashMap<String,Suite> getSuites(){
		return suites;
	}
	
	public void setSuites(HashMap<String, Suite> s){
		suites = s;
	}
	
	public void parseSuites(String[] colorList) {
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
		setSaveFile(new File(System.getProperty("user.dir")+"/resources/newgame.mns"));
		
		players = new HashMap<String, Player>();
		Scanner kb = new Scanner(System.in);
		
		//System.out.println("How many players?");
		//int pnum = kb.nextInt();
		
		//players = new HashMap<String, Player>();
		playerID = new HashMap<Integer, Player>();
		turnTable = new ArrayList<Player>();
		jailTable = new HashMap<String, Boolean>();
		jailTimes = new HashMap<String, Integer>();
		String name = "";
		for(int i=0; i<8; i++){
			//System.out.println("Enter name for Player "+(i+1)+":");
			//String name = kb.nextLine();
			name = i+"";
			Player noob =  new Player(name, i, 1500, 0, 0, false, new HashMap<String,Property>(), null);
			players.put(noob.getName(), noob);
			playerID.put(noob.getId(), noob);
			turnTable.add(noob);
			jailTable.put(noob.getName(), false);
			jailTimes.put(noob.getName(), 0);
			
			
		}
		kb.close();
		
		turn = new Counter(0,8,0);
		
		jailTable = new HashMap<String,Boolean>();
		jailTimes = new HashMap<String, Integer>();
		for(String s : players.keySet()){
			jailTable.put(s, false);
			jailTimes.put(s, 0);
			
		}
		currency = "$";
		texture = "/textures/default/";
		houseCount = new Counter(0, 32, 0);
		hotelCount = new Counter(0, 12, 0);
		
		
		railCount = new Counter(0,4,0);
		utilCount = new Counter(0,2,0);
		
		gameDice = new Dice(6,2);
		
		playerTokens = new HashMap<String, GameToken>();
		
		// Rails(4) + utility(2) + 22(streets) = 28
		Property[] props = TemplateGameVars.defineProps();
		
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
		
	}

	public File getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
	}
	
	public void repaintFrame(){
		frame.repaint();
	}
	
}

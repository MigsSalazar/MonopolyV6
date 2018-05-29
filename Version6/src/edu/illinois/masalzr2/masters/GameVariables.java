package edu.illinois.masalzr2.masters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.illinois.masalzr2.gui.*;
import edu.illinois.masalzr2.models.*;

public class GameVariables {
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
	private ImageIcon[] icons;
	private Stamp[][] stampCollection;
	private HashMap<String, GameToken> playerTokens;
	
	private String currency;
	private String texture;
	private Counter houseCount;
	private Counter hotelCount;
	
	
	
	public String getCurrencySymbol(){
		return currency;
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
			f = new File(System.getProperty("user.dir")+"\\"+texture);
		}
		return f.exists();
	}
	
	public String getTextureName(){
		return texture.substring(texture.lastIndexOf("\\"), texture.length());
	}
	
	public String getTextureDir(){
		File f = new File(texture);
		if(!f.exists()){
			f = new File(System.getProperty("user.dir")+"\\"+texture);
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
		
		jailTable.put(p.getName(), new Boolean(true));
		
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

	public ImageIcon[] getIcons() {
		return icons;
	}

	public void setIcons(ImageIcon[] icons) {
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
	
	
}

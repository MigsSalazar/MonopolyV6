package edu.illinois.masalzr2.masters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.illinois.masalzr2.gui.*;
import edu.illinois.masalzr2.models.*;

public class GameVariables {
	private Board board;
	//private JPanel stats;
	private Notices notices;
	
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
	
	private ArrayList<GameCard> chance;
	private ArrayList<GameCard> commchest;
	
	private Dice gameDice;
	
	private int[][] paintByNumbers;
	private ImageIcon[] icons;
	private HashMap<String, GameToken> playerTokens;
	
	private String currency;
	private String textureDir;
	private Counter houseCount;
	private Counter hotelCount;
	
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
		
		int[] oldCoords = {jailMe.getX(), jailMe.getY()};
		int[] newCoords = jailMe.useSpecialtyCase(0);
		
		board.movePiece(jailMe.getPiece(), oldCoords, newCoords);
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
		
		int oldX = current.getX();
		int oldY = current.getY();
		ImageIcon piece = current.getPiece();
		
		current.movePiece(move);
		
		board.movePiece(piece, oldX, oldY, current.getX(), current.getY());
		
	}
	
	
}

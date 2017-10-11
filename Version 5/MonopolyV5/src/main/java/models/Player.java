package main.java.models;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Player {
	private String name;
	private int userid;
	private int position;
	private int cash;
	private int wealth;
	private int jailCard;
	private int jailCount;
	private boolean inJail;
	private boolean active;
	private boolean turn;
	private Map<String, Property> props;
	
	/**
	 * Classic constructor. To be used if creating a fresh new Player
	 * @param uname	single digit integer 
	 * @param n		Name as chosen by the player
	 */
	public Player(int uid, String n)
	{
		userid = uid;
		name = n;
		position = 0;
		cash = 1500;
		jailCard = 0;
		inJail = false;
		active = true;
		turn = false;
		jailCount = 0;
		props = new HashMap<String, Property>();
		calcWealth();
	}
	
	/**
	 * Recreate constructor. To be used to recreate a Player object from save file
	 * @param uname		unique user name to identify the player
	 * @param uid		user ID number
	 * @param p			int position defining where the player is on the board
	 * @param c			int cash of players held LIQUID wealth
	 * @param jc		int defining how many Get Out of Jail Free Cards owned by the player
	 * @param jcount	int defining how long the players has stayed in jail since entering
	 * @param ij		boolean confirming or denying if the player is in jail
	 * @param a			boolean defining if the player has gone bankrupt or not
	 * @param t			boolean stating if it is the players turn or not
	 * @param pr		HashMap<String, Property> containing all the player's owne properties
	 */
	public Player(String n,
				  int uid, int p, int c, int jc, int jcount,
				  boolean ij, boolean a, boolean t,
				  Map<String, Property> pr)
	{	
		if(pr instanceof HashMap< ?, ? >){
			userid = uid;
			name = n;
			position = p;
			cash = c;
			jailCard = jc;
			inJail = ij;
			active = a;
			turn = t;
			jailCount = jcount;
			addProperties( (HashMap<String, Property>)pr);
			calcWealth();
		}
	}
	
	public int getUserID(){ return userid; }
	
	public String getName(){ return name; }
	
	public int getPosition(){ return position; }
	
	public void setPosition(int p){ position = p; }
	
	public int getCash(){ return cash; }
	
	public void addCash(int ac){
		cash += ac;
		calcWealth();
	}
	
	public void subCash(int sc){
		cash -= sc;
		calcWealth();
	}
	
	public void setCash(int c){
		cash = c;
		calcWealth();
	}
	
	public int getWealth(){
		calcWealth();
		return wealth;
	}
	
	private void calcWealth(){
		//TODO implement calcWealth
		Set<String> keys = props.keySet();
		wealth = cash;
		for(String k : keys){
			wealth += props.get(k).getWorth();
		}
		
	}
	
	public int getRedeemableWealth(){
		Set<String> keys = props.keySet();
		
		int redeemable = cash;
		
		for(String k : keys){
			redeemable += props.get(k).getRedeemableWorth();
		}
		
		return redeemable;
		
	}
	
	public int getJailCards(){ return jailCard; }
	
	public void setJailCards(int jc){ jailCard = jc; }
	
	public void addJailCard(){ jailCard++; }
	public void addJailCard(int ajc){ jailCard+= ajc; }
	
	public void subJailCard(){ jailCard--; }
	public void subJailCard(int sjc){ jailCard -= sjc; }
	
	public boolean isInJail(){ return inJail; }
	
	public void setInJail(boolean ij){ inJail = ij; }
	
	public boolean toggelInJail(){
		inJail = !inJail;
		return inJail;
	}
	
	public boolean isActive(){ return active; }
	
	public void setActive(boolean a){ active = a; }
	
	public boolean toggelActive(){
		active = !active;
		return active;
	}
	
	public boolean isTurn(){ return turn; }
	
	public void setTurn(boolean t){ turn = t; }
	
	public boolean toggleTurn(){
		turn = !turn;
		return turn;
	}
	
	public int getJailCount(){ return jailCount; }
	
	public int spendANightInJail(){
		jailCount++;
		return jailCount;
	}
	
	public void resetJailCount(){ jailCount = 0; }
	
	public boolean playerOwns(Property p){
		return props.containsValue(p);
	}
	
	public boolean addProperty(Property p){
		props.put(p.getName(), p);
		p.setOwner(this);
		return playerOwns(p);
	}
	
	public void addProperties(HashMap<String, Property> pr){
		Set<String> keys = pr.keySet();
		for(String k : keys){
			addProperty(pr.get(k));
		}
	}
	
	public boolean removeProperty(Property p){
		props.remove(p.getName());
		p.setOwner(null);
		return !( playerOwns(p) );
	}
	
	public boolean removeProperty(String key){
		Property goodBye = props.remove(key);
		if(goodBye!=null){
			goodBye.setOwner(null);
			return true;
		}else{
			return false;
		}
	}
	
}

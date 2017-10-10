package main.java.models;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Player {
	private String user;
	private String name;
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
	 * @param n
	 * @param p
	 * @param c
	 * @param jc
	 * @param jcount
	 * @param ij
	 * @param a
	 * @param t
	 */
	public Player(String n,
				  int p, int c, int jc, int jcount,
				  boolean ij, boolean a, boolean t)
	{
		Random r = new Random();
		user = n+r.nextInt(1000);
		name = n;
		position = p;
		cash = c;
		jailCard = c;
		inJail = ij;
		active = a;
		turn = t;
		jailCount = jcount;
		props = new HashMap<String, Property>();
		calcWealth();
	}
	
	public Player(String uname, String n,
				  int p, int c, int jc, int jcount,
				  boolean ij, boolean a, boolean t,
				  HashMap<String, Property> pr)
	{
		user = uname;
		name = n;
		position = p;
		cash = c;
		jailCard = jc;
		inJail = ij;
		active = a;
		turn = t;
		jailCount = jcount;
		props = pr;
		calcWealth();
	}
	
	public String getUserName(){ return user; }
	
	public String getName(){ return name; }
	
	public int getPosition(){ return position; }
	
	public void setPosition(int p){ position = p; }
	
	public int getCash(){ return cash; }
	
	public void addCash(int ac){ cash += ac; }
	
	public void subCash(int sc){ cash -= sc; }
	
	public void setCash(int c){ cash = c;}
	
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
		return playerOwns(p);
	}
	
	public boolean removeProperty(Property p){
		props.remove(p.getName());
		return !( playerOwns(p) );
	}
	
}

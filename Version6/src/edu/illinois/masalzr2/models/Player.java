package edu.illinois.masalzr2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int id;
	private int cash;
	private int position;
	private int jailCard;
	private boolean bankrupt;
	private HashMap<String, Property> props;
	private ArrayList<ChangeListener> listeners;
	
	public Player(String n, int i, int c, int p, int j, boolean b, HashMap<String, Property> pr, ArrayList<ChangeListener> listen){
		name = n;
		id = i;
		cash = c;
		position = p;
		jailCard = j;
		bankrupt = b;
		props = pr;
		listeners = listen;
	}
	
	public Player(int c){
		name = "";
		id = -1;
		position = 0;
		cash = c;
		jailCard = 0;
		props = new HashMap<String, Property>();
		listeners = new ArrayList<ChangeListener>();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public void setId(int i){
		id = i;
	}
	
	public int getId(){
		return id;
	}
	
	public int getCash(){
		return cash;
	}
	
	public void addCash(int a){
		cash += a;
		fireChange();
	}
	
	public void subCash(int s){
		cash -= s;
		fireChange();
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setPosition(int p){
		position = p;
		fireChange();
	}
	
	public int getJailCard(){
		return jailCard;
	}
	
	public void addJailCard(int a){
		jailCard += a;
		fireChange();
	}
	
	public void addOneJailCard(){
		addJailCard(1);
	}
	
	public void subJailCard(int s){
		addJailCard(-1 * s);
	}
	
	public void subOneJailCard(){
		addJailCard(-1);
	}
	
	public boolean isBankrupt(){
		return bankrupt;
	}
	
	public boolean ownsProp(Property p){
		return props.containsValue(p);
	}
	
	public HashMap<String,Property> getProps(){
		return props;
	}
	
	public void addProp(Property p){
		propsExist();
		props.put(p.getName(), p);
		fireChange();
	}
	
	public void addProperties(Collection<Property> inProps){
		propsExist();
		for(Property pr : inProps){
			props.put(pr.getName(), pr);
		}
		fireChange();
	}
	
	
	public void removeProp(Property p){
		removeProp(p.getName());
	}
	
	public void removeProp(String p){
		propsExist();
		props.remove(p);
		fireChange();
	}
	
	public int getWealth(){
		propsExist();
		
		int ret = cash;
		
		for(Property p : props.values()){
			ret += p.getWorth();
		}
		
		return ret;
	}
	
	public int getLiquidationWorth(){
		int ret = cash;
		
		propsExist();
		
		for(Property p : props.values()){
			ret += p.getLiquidationWorth();
		}
		
		return ret;
		
	}
	
	private void propsExist(){
		if(props == null){
			props = new HashMap<String, Property>();
		}
	}
	
	public void addChangeListener(ChangeListener cl){
		if(!listeners.contains(cl)){
			listeners.add(cl);
		}
	}
	
	private void fireChange(){
		ChangeEvent ce = new ChangeEvent(this);
		for(ChangeListener cl : listeners){
			cl.stateChanged(new ChangeEvent(ce));
		}
	}
	
}

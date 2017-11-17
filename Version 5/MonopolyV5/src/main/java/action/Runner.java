/**
 * 
 */
package main.java.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import main.java.gui.GameFrame;
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
	private GameFrame game;
	private Dice dice;
	private GameWriter gwrite;
	private GameReader gread;
	
	public void startNewGame(){
		GameFrame game = new GameFrame(true, this);
		game.setup();
	}
	
	public void startSavedGame(){
		GameFrame game = new GameFrame(true,this);
		game.setup();
	}
	
	public String[] playerNames(){
		return (String[])players.keySet().toArray();
	}
	
	public Map<String, Player> getPlayers(){
		return players;
	}
	
}

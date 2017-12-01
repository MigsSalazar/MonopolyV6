/**
 * 
 */
package main.java.action;

import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	
	private Set<String> playerNames;
	private Set<String> propNames;
	private Set<String> suiteNames;
	
	private int playerTurn;
	private Roll gameDice;
	private GameFrame game;
	private GameReader gread;
	Settings sets;
	
	public void saveThisGame(){
		if(gread.isNewGame()){
			GameWriter.writeOutNewGame(game, game.getGameBoard(), game.getGameEvents().getEvent() , players, properties);
		}
		
		
	}
	
	public void startNewGame(){
		gread = new GameReader();
		game = new GameFrame(true, this, gread);
		sets = new Settings((JFrame)game);
		game.setup();
	}
	
	public void startSavedGame(){
		gread = new GameReader();
		game = new GameFrame(true,this, gread);
		sets = new Settings((JFrame)game);
		game.setup();
	}
	
	public String[] getPlayerNames(){
		return (String[])playerNames.toArray();
	}
	
	public String[] getPropName(){
		return (String[])propNames.toArray();
	}
	
	public String[] getSuiteName(){
		return (String[])suiteNames.toArray();
	}
	
	public Map<String, Player> getPlayers(){
		return players;
	}

	public Map<String, Property> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
		propNames = this.properties.keySet();
	}

	public Map<String, Suite> getColoredProps() {
		return coloredProps;
	}

	public void setColoredProps(Map<String, Suite> coloredProps) {
		this.coloredProps = coloredProps;
		suiteNames = this.coloredProps.keySet();
	}

	public int getPlayerTurn() {
		return playerTurn;
	}
	
	public Player currentPlayer(){
		for(String p : playerNames){
			if(playerTurn == players.get(p).getUserID()){
				return players.get(p);
			}
		}
		return null;
	}
	
	public void cyclePlayer(){
		Player current = currentPlayer();
		current.setTurn(false);
		playerTurn = (playerTurn + 1)%players.size();
		current = currentPlayer();
		current.setTurn(true);
	}

	public GameFrame getFrame(){
		return game;
	}
	
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
		playerNames = this.players.keySet();
	}

	public static void aboutThis(){
		Icon pic = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/aboutSmall.png");
		JOptionPane.showMessageDialog(null, "ABOUT"
										+ "\n=========================================="
										+ "\nDeveloper: 				 Miguel Salazar"
										+ "\nAsset Designer:			 Miguel Salazar"
										+ "\nProject Manager:			 Miguel Salazar"
										+ "\n[insert job position here]: Miguel Salazar"
										+ "\nIf you can't tell, I worked on this alone."
										+ "\nAs far as I know, there is very little to"
										+ "\ninclude here besides that I used Google's"
										+ "\nJSON parser GSON to read and write any"
										+ "\nsettings or configuration files. (I can't"
										+ "\nfind the version). I also used JUnit 4.10"
										+ "\nto test my code. I'd like to copyright my"
										+ "\nproject however, I'm not sure how to do"
										+ "\nthat or how to do that for my code. But I"
										+ "\ndoubt anyone could get too much out of my"
										+ "\ncode. I hope you enjoy my game!",
										"About",
										JOptionPane.INFORMATION_MESSAGE,
										pic);
	}

	public Settings getSettings() {
		return sets;
	}

	public Roll getGameDice() {
		return gameDice;
	}
	
}

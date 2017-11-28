package main.java;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.java.action.Runner;
import main.java.action.Settings;
import main.java.gui.PreGameFrame;

public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Still in the works. Currently little is available");
		//Properties sysprops = System.getProperties();
		
		PreGameFrame pgf = new PreGameFrame();
		pgf.start();
	}
	
	public static void startNewGame(){
		Runner newGame = new Runner();
		newGame.startNewGame();
		
	}
	
	public static void startSaveGame(String gameName){
		JOptionPane.showMessageDialog(null, "So this is supposed to handle starting a loaded game"
										+ "\nbut we're not there just yet. Baby steps my friend."
										+ "\nBaby steps");
		Runner newGame = new Runner();
		newGame.startSavedGame();
	}
	
	public static void settingsLaunch(JFrame parent){
		Settings launchGear = new Settings(parent);
		launchGear.setup();
		launchGear.setVisible(true);
	}
	
}

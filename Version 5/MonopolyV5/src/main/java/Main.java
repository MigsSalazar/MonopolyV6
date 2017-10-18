package main.java;

import javax.swing.JOptionPane;
import main.java.gui.GameFrame;
import main.java.gui.PreGameFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Still in the works. Currently little is available");
		PreGameFrame pgf = new PreGameFrame();
	}
	
	public void startNewGame(){
		GameFrame game = new GameFrame();
	}
	
	public void startSaveGame(String gameName){
		JOptionPane.showMessageDialog(null, "So this is supposed to handle starting a loaded game"
										+ "\nbut we're not there just yet. Baby steps my friend."
										+ "\nBaby steps");
		GameFrame game = new GameFrame();
	}
	
	public void settingsLaunch(){
		Settings launchGear = new Settings();
	}
	
	public void aboutThis(){
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
										+ "\ncode. I hope you enjoy my game!");
	}
	
}

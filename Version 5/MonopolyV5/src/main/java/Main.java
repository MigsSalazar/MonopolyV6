package main.java;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.java.gui.GameFrame;
import main.java.gui.PreGameFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Still in the works. Currently little is available");
		//Properties sysprops = System.getProperties();
		
		PreGameFrame pgf = new PreGameFrame();
	}
	
	public static void startNewGame(){
		GameFrame game = new GameFrame();
	}
	
	public static void startSaveGame(String gameName){
		JOptionPane.showMessageDialog(null, "So this is supposed to handle starting a loaded game"
										+ "\nbut we're not there just yet. Baby steps my friend."
										+ "\nBaby steps");
		GameFrame game = new GameFrame();
	}
	
	public static void settingsLaunch(){
		Settings launchGear = new Settings();
		launchGear.setup();
		launchGear.setVisible(true);
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
	
}

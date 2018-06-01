package edu.illinois.masalzr2.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.templates.TemplateGameVars;

public class GameIn {

	public static void main(String[] args) {
		System.out.println("Testing if default game is corrupted or correct");
		GameVariables myGame = produceSavedGame(System.getProperty("user.dir")+"/resources/newgame.mns");
		if(myGame == null) {
			System.out.println("Failure");
		}else {
			myGame.buildFrame();
			System.out.println("Success");
		}
	}
	
	public static GameVariables newGame() {
		File f = new File(System.getProperty("user.dir") + "/resources/newgame.mns");
		System.out.println(System.getProperty("user.dir") + "/resources/newgame.mns");
		if(!f.exists()) {
			TemplateGameVars.produceTemplate();
		}
		GameVariables retval = produceSavedGame(f);
		if(retval == null) {
			TemplateGameVars.produceTemplate();
			retval = produceSavedGame(f);
		}
		
		return retval;
	}
	
	public static GameVariables produceSavedGame(String dir) {
		
		File f = new File(dir);
		
		if(!f.exists()) {
			return null;
		}
		
		return produceSavedGame(f);
	}
	
	public static GameVariables produceSavedGame(File dir) {
		FileInputStream fin;
		GameVariables theGame = null;
		try {
			fin = new FileInputStream(dir);
			ObjectInputStream objRead = new ObjectInputStream(fin);
			
			Object obj = objRead.readObject();
			
			if(obj instanceof GameVariables) {
				theGame = (GameVariables)obj;
			}
			
			objRead.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theGame;
		
	}
	
}

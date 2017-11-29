package main.java.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import main.java.action.Runner;
import main.java.gui.*;
import main.java.models.*;

public class GameReader {
	
	private ArrayList<String> locations = new ArrayList<String>();
	private boolean newGame;
	
	public GameReader(){
		newGame = true;
		String loaded = System.getProperty("user.dir")+"/saved-games/default-game/";
		//System.out.println(loaded);
		locations.add(loaded+"board_config.json");
		locations.add(loaded+"players.mns");
		locations.add(loaded+"properties.mns");
		
		//locations.add(loaded+"event.mns");
	}
	
	public GameReader(File loaded){
		newGame = false;
		try{
			Scanner in = new Scanner(loaded);
			while(in.hasNextLine()){
				locations.add(in.nextLine());
			}
			in.close();
		}catch(Exception e){
			
		}
		
		
	}
	
	public BoardPanel getBoard() throws IOException{
		//System.out.println("gson board begins");
		Gson gson = new Gson();
		Reader readme = new FileReader(new File(locations.get(0)));
		BoardPanel retval = gson.fromJson(readme, BoardPanel.class);
		//System.out.println("board exists and has been make. returning now");
		//System.out.println("Board: " + retval.toString() );
		readme.close();
		return retval;
	}
	
	public EventPanel getEvents(Runner gv) throws IOException{
		//System.out.println("gson events begins");
		if(newGame){
			//System.out.println("event doesnt exist, starting from scratch");
			return (new EventPanel());
		}else{
			Gson gson = new Gson();
			Reader readme = new FileReader(new File(locations.get(3)));
			EventPanel retval = gson.fromJson(readme, EventPanel.class);
			readme.close();
			//System.out.println("event does exist, returning now");
			//System.out.println("Event: " + retval.toString() );
			return retval;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Player> getPlayers() throws IOException{
		//System.out.println("gson player begins");
		Gson gson = new Gson();
		Reader readme = new FileReader(new File(locations.get(1)));
		HashMap<String, Player> retval = gson.fromJson(readme, HashMap.class);
		readme.close();
		//System.out.println("player exists, returning now");
		return retval;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Property> getProperties() throws IOException{
		//System.out.println("gson property begins");
		Gson gson = new Gson();
		Reader readme = new FileReader(new File(locations.get(2)));
		HashMap<String, Property> retval = gson.fromJson(readme, HashMap.class);
		readme.close();
		//System.out.println("property exists, returning now");
		return retval;
	}
	
	public static String findGame(JFrame parent){
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Monopoly Saves","mns");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(parent);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       return chooser.getSelectedFile().getName();
	    }else{
	    	return null;
	    }
	}
	
	public boolean isNewGame(){
		return newGame;
	}
	
}

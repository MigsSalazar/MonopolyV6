package main.java.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.java.models.*;
import main.java.gui.*;
import main.java.action.Runner;
import main.java.action.Settings;

public class GameWriter {
	
	public static boolean writeOutOldGame(Runner game, GameReader gread){
		File source = gread.getLoaded();
		String dir = System.getProperty("user.dir")+"/saved-games/";
		//File source = new File(dir+gameName);
		
		ArrayList<String> locations = new ArrayList<String>();
		
		Scanner fileIn;
		try {
			//System.out.println("Game Writer dir: "+dir);
			//System.out.println("Game Writer source: "+source);
			fileIn = new Scanner(source);
			while(fileIn.hasNextLine()){
				locations.add(dir+fileIn.nextLine());
				//System.out.println(locations.get(locations.size()-1));
			}
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		if(locations.size() == 0){
			return false;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		try{
			Writer writeOut;
			
			writeOut = new FileWriter(locations.get(0));
			gson.toJson(game.getFrame().getGameBoard(), writeOut );
			writeOut.close();
			
			writeOut = new FileWriter(locations.get(1));
			gson.toJson(game.getPlayers(), writeOut);
			writeOut.close();
			
			PropertyBean prbean = new PropertyBean(game.getProperties());
			writeOut = new FileWriter(locations.get(2));
			gson.toJson(prbean, writeOut);
			writeOut.close();
			
			suiteWriter(locations.get(3), game.getColoredProps());
			
			writeOut = new FileWriter(locations.get(4));
			gson.toJson(game.getCommChest(), writeOut);
			writeOut.close();
			
			writeOut =  new FileWriter(locations.get(5));
			gson.toJson(game.getChance(), writeOut);
			writeOut.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}
		//game.setN
		return true;
	}
	
	public static String writeOutNewGame(GameFrame frame,
										BoardPanel board,
										Map<String, Suite> suites,
										Map<String, Player> players,
										Map<String, Property> props,
										ArrayList<GameCard> comm,
										ArrayList<GameCard> chance,
										Settings sets){
		
		//String dir = System.getProperty("user.dir")+"/saved-games/locations-folder/";
		/*
		JFileChooser fc = new JFileChooser(dir);
		int choice = fc.showSaveDialog(frame);
		
		File writeOut;
		if(choice == JFileChooser.APPROVE_OPTION){
			writeOut = fc.getSelectedFile();
		}else{
			return false;
		}
		*/
		
		String path = JOptionPane.showInputDialog(frame, "Enter the name of your game");
		BufferedWriter out;
		try{
			if(path != null && !path.equals("")){
				//String homeFolder = System.getProperty("user.dir")+"/saved-games/";
				File newFolder = new File(System.getProperty("user.dir")+"/saved-games/"+path);
				//newFolder.mkdirs();
				newFolder.mkdir();
				//System.out.println("player defined path: "+path);
				//System.out.println("full path name: "+newFolder);
				
				ArrayList<String> outLines = new ArrayList<String>();
				
				outLines.add( objectWriter(path+"/board_config.json", board) );
				outLines.add( objectWriter(path+"/players.json", players) );
				
				PropertyBean spit = new PropertyBean(props);
				
				outLines.add( objectWriter(path+"/properties.json", spit) );
				outLines.add( suiteWriter(path+"/suiteNames.txt", suites) );
				outLines.add( objectWriter(path+"/community-chest.json", comm) );
				outLines.add( objectWriter(path+"/chance.json", chance) );
				outLines.add( objectWriter(path+"/settings.json", sets) );
				
				File location = new File(System.getProperty("user.dir")+"/saved-games/locations-folder/"+path+".mns");
				
				out = new BufferedWriter( new FileWriter(location) );
				
				for(String s : outLines){
					out.write(s);
					out.newLine();
				}
				
				out.close();
				return ""+location;
			}else{
				JOptionPane.showMessageDialog(frame, "Invalid name. Game has NOT been saved.");
				return "";
			}
		}catch(IOException ioe){
			System.out.println(ioe.toString());
			ioe.printStackTrace();
			return "";
		}
		
		
	}
	
	public static String suiteWriter(String path, Map<String,Suite> suites) throws IOException{
		
		String dir;
		
		if( path.contains(System.getProperty("user.dir") ) ) {
			dir = path;
		}else{
			dir = System.getProperty("user.dir")+"/saved-games/"+path;
		}
		
		
		BufferedWriter writeOut = new BufferedWriter( new FileWriter(dir) );
		
		//String lameOutput = "";
		
		for(String s : suites.keySet()){
			writeOut.write(s);
			for(Property p : suites.get(s).getProperties()){
				writeOut.write( ","+p.getName() );
			}
			writeOut.newLine();
		}
		//writeOut.write(lameOutput);
		writeOut.close();
		String retval = "/"+path;
		return retval;
	}
	
	public static String objectWriter(String path, Object out) throws IOException{
		Writer writeOut;
		//String mypath = generatePath(path);
		String dir = System.getProperty("user.dir")+"/saved-games/"+path;
		writeOut = new FileWriter(dir);
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		gson.toJson(out, writeOut);
		writeOut.close();
		String retval = "/"+path;
		return retval;
	}
	
}

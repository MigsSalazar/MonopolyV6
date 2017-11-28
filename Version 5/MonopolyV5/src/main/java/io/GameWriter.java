package main.java.io;

import java.io.*;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import gameEvents.AbstractEvent;
import main.java.models.*;
import main.java.gui.*;
import main.java.action.Runner;

public class GameWriter {
	
	public static boolean writeOutOldGame(Runner game){
		String source = GameReader.findGame(game.getFrame());
		String[] locations = source.split("\n");
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		try{
			gson.toJson(game.getFrame().getGameBoard(), new FileWriter(locations[0]));
			gson.toJson(game.getPlayers(), new FileWriter(locations[1]));
			gson.toJson(game.getProperties(), new FileWriter(locations[2]));
			gson.toJson(game.getFrame().getGameEvents(), new FileWriter(locations[3]));
		}catch(IOException ioe){
			return false;
		}
		return true;
	}
	
	public static boolean writeOutNewGame(GameFrame frame,
										BoardPanel board,
										AbstractEvent event,
										Map<String, Player> players,
										Map<String, Property> props){
		
		String path = JOptionPane.showInputDialog(frame, "Enter the name of your saved game:");
		Writer out;
		try{
			if(path != null && !path.equals("")){
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String homeFolder = System.getProperty("user.dir")+"/saved-games/";
				String boardPath = objectWriter(homeFolder+"/game-reqs/", board);
				String playerPath = objectWriter(homeFolder+"/game-reqs/", players);
				String propPath = objectWriter(homeFolder+"/game-reqs/", props);
				String eventPath = objectWriter(homeFolder+"/game-reqs/", event);
				
				out = new FileWriter(homeFolder+path+".mns");
				
				out.write(boardPath+"\n"
						+playerPath+"\n"
						+propPath+"\n"
						+eventPath);
				
				out.close();
				return true;
			}else{
				JOptionPane.showMessageDialog(frame, "Invalid name. Game has NOT been saved.");
				return false;
			}
		}catch(IOException ioe){
			System.out.println(ioe.toString());
			ioe.printStackTrace();
			return false;
		}
		
		
	}
	
	public static String objectWriter(String path, Object out) throws IOException{
		Writer writeOut;
		String mypath = generatePath(path);
		writeOut = new FileWriter(mypath);
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		gson.toJson(out, writeOut);
		writeOut.close();
		return mypath;
	}
	
	private static String generatePath(String path) {
		String title = getRandomHexString();
		File test = new File(path+title+".mns");
		while(test.exists() && test.isFile()){
			title = getRandomHexString();
			test = new File(path+title+".mns");
		}
		return title;
	}
	
	private static String getRandomHexString(){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < 10){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, 10);
    }
	
}

package main.java.io;

import java.io.*;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.java.models.*;
import main.java.gui.*;
import main.java.action.Events;

public class GameWriter {
	
	public static boolean writeOutGame(JFrame frame,
										BoardPanel board,
										Events event,
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

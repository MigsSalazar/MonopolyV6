package main.java.io;

import java.lang.reflect.Type;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
		locations.add(loaded+"board_config.json");	//0
		locations.add(loaded+"players.json");		//1
		locations.add(loaded+"properties.json");	//2
		locations.add(loaded+"suiteNames.txt");		//3
		
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
			return (new EventPanel(gv));
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

	
	public HashMap<String, Player> getPlayers() throws IOException{
		//System.out.println("gson player begins");
		Gson gson = new Gson();
		Reader readme = new FileReader(new File(locations.get(1)));
		//Type type = new TypeToken<Map<String, Player>>(){}.getType();
		Type type = new TypeToken<HashMap<String, Player>>(){}.getType();
		HashMap<String, Player> retval = gson.fromJson(readme, type);
		readme.close();
		for(String s : retval.keySet()){
			System.out.println("Current player: "+s);
		}
		//System.out.println("player exists, returning now");
		return retval;
	}
	
	public HashMap<String, Property> getProperties(){
		PropertyBean getter = PropertyBean.jsonToProperties(new File(locations.get(2)));
		return getter.getFullMap();
	}
	
	public HashMap<String,Suite> getSuites(Map<String,Property> props) throws IOException{
		Scanner filein = new Scanner(new File(locations.get(3)));
		HashMap<String,Suite> retval = new HashMap<String,Suite>();
		String linein = "";
		String[] linearr;
		
		/*
		for(String key : props.keySet()){
			System.out.println(key);
		}*/
		linein = filein.nextLine();
		while(!linein.equals("") ){
			
			/*if(linein == null){
				break;
			}*/
			//System.out.println(linein);
			linearr = linein.split(",");
			//System.out.println("linearr length="+linearr.length);
			if(linearr.length==3){
				//System.out.println(linearr[0]+" and "+linearr[1]+" and "+linearr[2]);
				//System.out.println(props.containsKey(linearr[1]) + " exists and " + props.containsKey(linearr[2]));
				SmallSuite small = new SmallSuite(linearr[0], (Colored)(props.get(linearr[1])), (Colored)(props.get(linearr[2])));
				small.setPropertySuite();
				retval.put(linearr[0], small);
				//System.out.println("small suite made");
			}else if(linearr.length==4){
				BigSuite big = new BigSuite(linearr[0], (Colored)props.get(linearr[1]), (Colored)props.get(linearr[2]), (Colored)props.get(linearr[3]));
				big.setPropertySuite();
				retval.put(linearr[0], big);
			}
			if(filein.hasNextLine()){
				linein = filein.nextLine();
			}else{
				linein = "";
			}
			
		}
		filein.close();
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

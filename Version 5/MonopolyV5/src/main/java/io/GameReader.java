package main.java.io;

import java.lang.reflect.Type;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
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

import main.java.action.Roll;
import main.java.action.Runner;
import main.java.action.Settings;
import main.java.gui.*;
import main.java.models.*;

public class GameReader {
	
	private ArrayList<String> locations = new ArrayList<String>();
	private boolean newGame;
	private File loaded;
	
	public GameReader(Settings sets){
		newGame = true;
		String loaded = sets.textureMe();
		//System.out.println(loaded);
		locations.add(loaded+"board_config.json");		//0
		locations.add(loaded+"players.json");			//1
		locations.add(loaded+"properties.json");		//2
		locations.add(loaded+"suiteNames.txt");			//3
		locations.add(loaded+"community-chest.json");	//4
		locations.add(loaded+"chance.json");			//5
		
		//locations.add(loaded+"event.mns");
	}
	
	public GameReader(File l){
		loaded = l;
		//System.out.println(""+loaded);
		newGame = false;
		String dir = System.getProperty("user.dir")+"\\saved-games";
		try{
			Scanner in = new Scanner(loaded);
			while(in.hasNextLine()){
				locations.add(dir+in.nextLine());
				//System.out.println(locations.get(locations.size()-1));
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public BoardPanel getBoard() throws IOException{
		//System.out.println("gson board begins");
		Gson gson = new Gson();
		File boardFile = new File(locations.get(0));
		if(!boardFile.exists()){
			boardFile = new File("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/board_config.json");
		}
		Reader readme = new FileReader(boardFile);
		BoardPanel retval = gson.fromJson(readme, BoardPanel.class);
		retval.setPreferredSize(new Dimension(retval.getBoardPixWidth(),retval.getBoardPixHeight()));
		//System.out.println("board exists and has been make. returning now");
		//System.out.println("Board: " + retval.toString() );
		readme.close();
		return retval;
	}
	
	
	public HashMap<String, Player> getPlayers() throws IOException{
		//System.out.println("gson player begins");
		Gson gson = new Gson();
		File playersFile = new File(locations.get(1));
		if(!playersFile.exists()){
			playersFile = new File("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/players.json");
		}
		Reader readme = new FileReader(playersFile);
		//Type type = new TypeToken<Map<String, Player>>(){}.getType();
		Type type = new TypeToken<HashMap<String, Player>>(){}.getType();
		HashMap<String, Player> retval = gson.fromJson(readme, type);
		readme.close();
		/*
		for(String s : retval.keySet()){
			System.out.println("Current player: "+s);
		}*/
		//System.out.println("player exists, returning now");
		return retval;
	}
	
	public HashMap<String, Property> getProperties(Roll uroll){
		File propFile = new File(locations.get(2));
		if(!propFile.exists()){
			propFile = new File("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/properties.json");
		}
		PropertyBean getter = PropertyBean.jsonToProperties(propFile);
		return getter.getFullMap(uroll);
	}
	
	public HashMap<String,Suite> getSuites(Map<String,Property> props) throws IOException{
		File suiteFile = new File(locations.get(3));
		if(!suiteFile.exists()){
			suiteFile = new File("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/suiteNames.txt");
		}
		
		Scanner filein = new Scanner(suiteFile);
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
	
	public ArrayList<GameCard> getChance(){
		Gson gson = new Gson();
		Reader readme;
		ArrayList<GameCard> retval = null;
		try {
			File chanceFile = new File(locations.get(5));
			if(!chanceFile.exists()){
				chanceFile = new File("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/chance.json");
			}
			readme = new FileReader(chanceFile);
			Type type = new TypeToken<ArrayList<GameCard>>(){}.getType();
			retval = gson.fromJson(readme, type);
			readme.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return retval;
	}
	
	
	public ArrayList<GameCard> getCommChest(){
		Gson gson = new Gson();
		Reader readme;
		ArrayList<GameCard> retval = null;
		try {
			File commChestFile = new File(locations.get(4));
			if(!commChestFile.exists()){
				commChestFile = new File("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/community-chest.json");
			}
			readme = new FileReader(commChestFile);
			Type type = new TypeToken<ArrayList<GameCard>>(){}.getType();
			retval = gson.fromJson(readme, type);
			readme.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
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

	public void setNewGame(boolean ng){
		newGame = ng;
	}
	
	public File getLoaded() {
		return loaded;
	}

	public void setLoaded(File loaded) {
		this.loaded = loaded;
	}
	
}

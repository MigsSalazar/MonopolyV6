package edu.illinois.masalzr2.io;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.illinois.masalzr2.masters.Environment;
import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.masters.MonopolyExceptionHandler;
import edu.illinois.masalzr2.models.Counter;
import edu.illinois.masalzr2.models.Dice;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.models.Railroad;
import edu.illinois.masalzr2.models.Street;
import edu.illinois.masalzr2.models.Utility;
import edu.illinois.masalzr2.templates.TemplateEnvironment;
import edu.illinois.masalzr2.templates.TemplateJson;

public class GameIo {

	private static String sep = File.separator;
	
	/**
	 * @deprecated
	 * @param args No arguments are accepted or utilized. Pass in whatever you want, the main doesn't use them
	 */
	public static void main(String[] args) {
		//System.out.println("Testing if default game is corrupted or correct");
		LogMate.LOG.newEntry("GameIO: Main: beginning");
		LogMate.LOG.newEntry("GameIO: Main: developing options");
		Object[] options = new JButton[2];
		options[0] = new JButton("MNS");
		((JButton)options[0]).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TemplateEnvironment.produceTemplate();
				//System.exit(0);
			}
		});
		options[1] = new JButton("JSon");
		((JButton)options[1]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printCleanJson();
				//System.exit(0);
			}
		});
		LogMate.LOG.newEntry("GameIO: Main: Showing options");
		//System.out.println("MNS or JSon?");
		JOptionPane.showOptionDialog(null, 
				"Would you like to generate an MNS or a JSon",
				"Generate Files", 
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				/*ImageIcon*/null, 
				options, 
				options[0]);
		varsFromJson(null).buildFrame();
		//System.exit(0);
	}
	
	public static Environment newGame(String fileDir) {
		LogMate.LOG.newEntry("GameIO: NewGame: beginning");
		File f = new File(fileDir);
		LogMate.LOG.newEntry("GameIO: NewGame: File made with directory "+fileDir);
		//System.out.println(System.getProperty("user.dir") + "/resources/newgame.mns");
		if(!f.exists()) {
			LogMate.LOG.newEntry("GameIO: NewGame: File does not exists. Generating template");
			TemplateEnvironment.produceTemplate();
		}
		LogMate.LOG.newEntry("GameIO: NewGame: Producing saved game");
		Environment retval = produceSavedGame(f);
		if(retval == null) {
			LogMate.LOG.newEntry("GameIO: NewGame: Produced Game was found null. Creating Template");
			//System.out.println("GameIo.newGame(): retval found null");
			TemplateEnvironment.produceTemplate();
			retval = produceSavedGame(f);
		}
		LogMate.LOG.newEntry("GameIO: NewGame: Returning produced game");
		return retval;
	}
	
	public static Environment newGame() {
		return newGame(System.getProperty("user.dir") + sep +"resources"+sep+"packages"+sep+"default.mns");
	}
	
	public static Environment produceSavedGame(String dir) {
		LogMate.LOG.newEntry("GameIO: Produce Saved Game String: Opening file at directory "+dir);
		File f = new File(dir);
		//System.out.println(dir);
		if(!f.exists()) {
			//System.out.println("file doesn't exist");
			LogMate.LOG.newEntry("GameIO: Produce Saved Game String: File does not exists. Returning null");
			return null;
		}
		LogMate.LOG.newEntry("GameIO: Produce Saved Game String: File found. Requesting game at file");
		return produceSavedGame(f);
	}
	
	public static Environment produceSavedGame(File dir) {
		LogMate.LOG.newEntry("GameIO: Produce Saved Game: File with directory "+dir.getPath());
		FileInputStream fin;
		Environment theGame = null;
		try {
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Beginning inputstream");
			fin = new FileInputStream(dir);
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Creating ObjectInputStream");
			ObjectInputStream objRead = new ObjectInputStream(fin);
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Reading object");
			Object obj = objRead.readObject();
			
			if(obj instanceof Environment) {
				LogMate.LOG.newEntry("GameIO: Produce Saved Game: GameVariables object receieved");
				theGame = (Environment)obj;
			}
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Closing InputStreams");
			objRead.close();
			//fin.close();
		} catch (FileNotFoundException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		} catch (IOException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());;
		} catch (ClassNotFoundException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		}
		LogMate.LOG.newEntry("GameIO: Produce Saved Game: Returning the game");
		return theGame;
		
	}
	
	public static String findFile(Container parent, FileNameExtensionFilter filter) {
		return findFile(parent, filter, System.getProperty("user.dir"));
	}
	
	public static String findFile(Container parent, FileNameExtensionFilter filter, String baseDir){
		//System.out.println(System.getProperty("user.dir") + sep + "saves");
		LogMate.LOG.newEntry("GameIO: Find Game: Searching for games in saves directory");
		JFileChooser chooser = new JFileChooser(baseDir);
		LogMate.LOG.newEntry("GameIO: Find Game: Setting file filter");
	    //FileNameExtensionFilter filter = new FileNameExtensionFilter("Monopoly Saves","mns");
	    chooser.setFileFilter(filter);
	    LogMate.LOG.newEntry("GameIO: Find Game: Looking for games");
	    int returnVal = chooser.showOpenDialog(parent);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	LogMate.LOG.newEntry("GameIO: Find Game: Game was selected");
	       return chooser.getSelectedFile().getPath();
	    }else{
	    	LogMate.LOG.newEntry("GameIO: Find Game: Selection was canceled");
	    	return null;
	    }
	}

	public static boolean printCleanJson() {
		LogMate.LOG.newEntry("GameIO: Print Clean Json: Creating clean GameVariables object");
		Environment gv = new Environment();
		gv.buildCleanGame();
		
		try {
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Preparing write out at directory: "+System.getProperty("user.dir") +sep+"textures"+sep+"default"+sep+"defaultgame.json");
			Writer writeOut = new FileWriter( new File(System.getProperty("user.dir") +sep+"textures"+sep+"default"+sep+"defaultgame.json" ) );
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Creating Json parser with Gson");
			Gson gson = new GsonBuilder()
							.setPrettyPrinting()
							.excludeFieldsWithoutExposeAnnotation()
							.create();
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Writing out");
			
			TemplateJson j = new TemplateJson(gv);
			
			gson.toJson(j, writeOut);
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Closing");
			writeOut.close();
			
		} catch (Exception e) {
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Exception occured. Writting out log");
			LogMate.LOG.flush();
			//LogMate.LOG.finish();
			//MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
			return false;
		}
		LogMate.LOG.flush();
		return true;
	}
	
	public static Environment varsFromJson(Container parent) {
		
		String gotten = findFile(parent, new FileNameExtensionFilter("Json", "json") );
		if(gotten == null)
			return null;
		Gson gson = new Gson();
		TemplateJson tempVars = null;
		Map<String, Property> props = new HashMap<String, Property>();
		Counter utilCount = new Counter(0,2,0);
		Counter railCount = new Counter(0,4,0);
		try {
			FileReader fin = new FileReader(gotten);
			tempVars = gson.fromJson(fin, TemplateJson.class);
			
			for(Street s : tempVars.getStreets().values()) {
				props.put(s.getName(), s);
			}
			
			for(Railroad r : tempVars.getRails().values()) {
				r.setRailedOwned(railCount);
				if(r.getOwner() != null && !r.getOwner().equals("")) {
					railCount.add(1);
				}
				props.put(r.getName(), r);
				
			}
			
			for(Utility u : tempVars.getUtils().values()) {
				u.setCounter(utilCount);
				u.setDice(tempVars.getDice());
				if(u.getOwner()!=null && !u.getOwner().equals("")) {
					utilCount.add(1);
				}
				props.put(u.getName(), u);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("I failed");
			return null;
		}
		
		/*
		
		@Expose private Map<String, Player> players;
		@Expose private Map<String, Property> properties;
		@Expose private Map<String, Suite> suites;
		@Expose private PositionIndex propertyPositions;
		@Expose private ArrayList<GameCard> chance;
		@Expose private ArrayList<GameCard> commchest;
		@Expose private int[][] paintByNumbers;
		@Expose private String[] icons;
		@Expose private Stamp[][] stampCollection;
		@Expose private Map<String, GameToken> playerTokens;
		@Expose private int[][] stickerBook;
		@Expose private String[] stickers;
		@Expose private String currency;
		@Expose private String texture;
		 */
		Environment vars = new Environment();
		vars.setPlayers(			tempVars.getPlayers());
		vars.setProperties(			props);
		vars.setSuites(				tempVars.getSuites());
		vars.setPropertyPositions(	tempVars.getPropertyPositions());
		vars.setChance(				tempVars.getChance());
		vars.setCommchest(			tempVars.getCommchest());
		vars.setPaintByNumbers(		tempVars.getPaintByNumbers());
		vars.setIcons(				tempVars.getIcons());
		vars.setStampCollection(	tempVars.getStampCollection());
		vars.setPlayerTokens(		tempVars.getPlayerTokens());
		vars.setStickerBook(		tempVars.getStickerBook());
		vars.setStickers(			tempVars.getStickers());
		vars.setCurrency(			tempVars.getCurrency());
		vars.setTexture(			tempVars.getTexture());
		vars.setSaveFile(new File(	tempVars.getSaveFile()));
		vars.setHotelCount(			tempVars.getHotelCount());
		vars.setHouseCount(			tempVars.getHouseCount());
		vars.setTurn(				tempVars.getTurn());
		vars.setGameDice(			tempVars.getDice());
		vars.setRailCount(			railCount);
		vars.setUtilCount(			utilCount);
		vars.setLimitingTurns(		tempVars.isLimitingTurns());
		vars.setTurnsLimit(			tempVars.getTurnsLimit());
		vars.setFancyMoveEnabled(	tempVars.isFancyMoveEnabled());
		vars.refreshAllImages();
		vars.refreshPlayerCollections();
		vars.refreshPropertyCollections();
		vars.setGameDice(new Dice(6,2));
		//vars.setTurn(new Counter(0,8,0));
		writeOut(vars);
		return vars;
	}
	
	public static void writeOut(Environment me) {
		LogMate.LOG.newEntry("GameIO: Write Out: Beginning write out");
		FileOutputStream fout;
		try {
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Creating FileOutputStream");
			File f;
			if(me.getSaveFile().getParentFile().exists()) {
				f = me.getSaveFile();
			}else {
				f = new File(System.getProperty("user.dir")+me.getSaveFile().getPath());
				//System.out.println(f.getPath());
				if( !f.getParentFile().exists() ) {
					JFileChooser choose = new JFileChooser();
					choose.setDialogTitle("Select where to save");
					int answer = -1;
					while(answer != JFileChooser.APPROVE_OPTION){
						answer = choose.showSaveDialog(null);
					}
					f = choose.getSelectedFile();
				}
			}
			
			fout = new FileOutputStream(f);
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Creating ObjectOutputStream");
			ObjectOutputStream objWrite = new ObjectOutputStream(fout);
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Writing object");
			objWrite.writeObject(me);
			
			objWrite.close();
			
		} catch (FileNotFoundException e) {
			LogMate.LOG.newEntry("GameIO: Write Out: FileNotFouncException occured. Writting out log");
			LogMate.LOG.flush();
			LogMate.LOG.finish();
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		} catch (IOException e) {
			LogMate.LOG.newEntry("GameIO: Write Out: Exception occured. Writting out log");
			LogMate.LOG.flush();
			LogMate.LOG.finish();
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		}
		
	}
	
}

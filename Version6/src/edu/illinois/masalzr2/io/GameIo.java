package edu.illinois.masalzr2.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.masters.MonopolyExceptionHandler;
import edu.illinois.masalzr2.templates.TemplateGameVars;
import edu.illinois.masalzr2.masters.LogMate;

public class GameIo {

	private static String sep = File.separator;
	
	public static void main(String[] args) {
		//System.out.println("Testing if default game is corrupted or correct");
		LogMate.LOG.newEntry("GameIO: Main: beginning");
		LogMate.LOG.newEntry("GameIO: Main: developing options");
		Object[] options = new JButton[2];
		options[0] = new JButton("MNS");
		((JButton)options[0]).addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TemplateGameVars.produceTemplate();
				System.exit(0);
			}
		});
		options[1] = new JButton("JSon");
		((JButton)options[1]).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printCleanJson();
				System.exit(0);
			}
		});
		LogMate.LOG.newEntry("GameIO: Main: Showing options");
		//System.out.println("MNS or JSon?");
		JOptionPane.showOptionDialog(null, 
				"Would you like to generate an MNS or a JSon",
				"Generate Files", 
				JOptionPane.OK_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				/*ImageIcon*/null, 
				options, 
				options[0]);
		
		
	}
	
	public static GameVariables newGame() {
		LogMate.LOG.newEntry("GameIO: NewGame: beginning");
		File f = new File(System.getProperty("user.dir") + sep +"resources"+sep+"newgame.mns");
		LogMate.LOG.newEntry("GameIO: NewGame: File made with directory "+System.getProperty("user.dir") + sep +"resources"+sep+"newgame.mns");
		//System.out.println(System.getProperty("user.dir") + "/resources/newgame.mns");
		if(!f.exists()) {
			LogMate.LOG.newEntry("GameIO: NewGame: File does not exists. Generating template");
			TemplateGameVars.produceTemplate();
		}
		LogMate.LOG.newEntry("GameIO: NewGame: Producing saved game");
		GameVariables retval = produceSavedGame(f);
		if(retval == null) {
			LogMate.LOG.newEntry("GameIO: NewGame: Produced Game was found null. Creating Template");
			//System.out.println("GameIo.newGame(): retval found null");
			TemplateGameVars.produceTemplate();
			retval = produceSavedGame(f);
		}
		LogMate.LOG.newEntry("GameIO: NewGame: Returning produced game");
		return retval;
	}
	
	public static GameVariables produceSavedGame(String dir) {
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
	
	public static GameVariables produceSavedGame(File dir) {
		LogMate.LOG.newEntry("GameIO: Produce Saved Game: File with directory "+dir.getPath());
		FileInputStream fin;
		GameVariables theGame = null;
		try {
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Beginning inputstream");
			fin = new FileInputStream(dir);
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Creating ObjectInputStream");
			ObjectInputStream objRead = new ObjectInputStream(fin);
			LogMate.LOG.newEntry("GameIO: Produce Saved Game: Reading object");
			Object obj = objRead.readObject();
			
			if(obj instanceof GameVariables) {
				LogMate.LOG.newEntry("GameIO: Produce Saved Game: GameVariables object receieved");
				theGame = (GameVariables)obj;
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
	
	public static String findGame(JFrame parent){
		//System.out.println(System.getProperty("user.dir") + sep + "saves");
		LogMate.LOG.newEntry("GameIO: Find Game: Searching for games in saves directory");
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + sep + "saves" );
		LogMate.LOG.newEntry("GameIO: Find Game: Setting file filter");
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Monopoly Saves","mns");
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

	public static void printCleanJson() {
		LogMate.LOG.newEntry("GameIO: Print Clean Json: Creating clean GameVariables object");
		GameVariables gv = new GameVariables();
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
			gson.toJson(gv, writeOut);
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Closing");
			writeOut.close();
			
		} catch (Exception e) {
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Exception occured. Writting out log");
			LogMate.LOG.flush();
			LogMate.LOG.finish();
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		}
		
		
	}
	
	public static void writeOut(GameVariables me) {
		LogMate.LOG.newEntry("GameIO: Write Out: Beginning write out");
		FileOutputStream fout;
		try {
			LogMate.LOG.newEntry("GameIO: Print Clean Json: Creating FileOutputStream");
			fout = new FileOutputStream((File)me.getSaveFile());
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

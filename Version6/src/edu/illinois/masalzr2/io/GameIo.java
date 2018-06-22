package edu.illinois.masalzr2.io;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

public class GameIo {

	private static String sep = File.separator;
	
	public static void main(String[] args) {
		//System.out.println("Testing if default game is corrupted or correct");
		
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
		File f = new File(System.getProperty("user.dir") + sep +"resources"+sep+"newgame.mns");
		//System.out.println(System.getProperty("user.dir") + "/resources/newgame.mns");
		if(!f.exists()) {
			TemplateGameVars.produceTemplate();
		}
		GameVariables retval = produceSavedGame(f);
		if(retval == null) {
			//System.out.println("GameIo.newGame(): retval found null");
			TemplateGameVars.produceTemplate();
			retval = produceSavedGame(f);
		}
		
		return retval;
	}
	
	public static GameVariables produceSavedGame(String dir) {
		
		File f = new File(dir);
		//System.out.println(dir);
		if(!f.exists()) {
			//System.out.println("file doesn't exist");
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
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		} catch (IOException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());;
		} catch (ClassNotFoundException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		}
		
		return theGame;
		
	}
	
	public static String findGame(JFrame parent){
		//System.out.println(System.getProperty("user.dir") + sep + "saves");
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + sep + "saves" );
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Monopoly Saves","mns");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(parent);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       return chooser.getSelectedFile().getPath();
	    }else{
	    	return null;
	    }
	}

	public static void printCleanJson() {
		
		GameVariables gv = new GameVariables();
		gv.buildCleanGame();
		
		try {
			
			Writer writeOut = new FileWriter( new File(System.getProperty("user.dir") +sep+"textures"+sep+"default"+sep+"defaultgame.json" ) );
			
			Gson gson = new GsonBuilder()
							.setPrettyPrinting()
							.excludeFieldsWithoutExposeAnnotation()
							.create();
			
			gson.toJson(gv, writeOut);
			
			writeOut.close();
			
		} catch (Exception e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		}
		
		
	}
	
	public static void writeOut(GameVariables me) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream((File)me.getVariable("savefile"));
			ObjectOutputStream objWrite = new ObjectOutputStream(fout);
			
			objWrite.writeObject(me);
			
			objWrite.close();
			
		} catch (FileNotFoundException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		} catch (IOException e) {
			MonopolyExceptionHandler.uncaughtException(e, Thread.currentThread());
		}
		
	}
	
}

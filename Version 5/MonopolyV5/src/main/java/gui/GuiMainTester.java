package main.java.gui;

import java.awt.Container;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import main.java.action.Runner;
import main.java.action.UnzipUtility;
import main.java.models.Dice;
import main.java.models.Player;
import main.java.templates.TemplateBoardPanel;
import main.java.templates.TemplatePiece;

@SuppressWarnings("unused")
public class GuiMainTester {
	
	//this class is meant only to test gui functions and resources
	//NEVER run the full application from here
	//any code written should be temporary and deleted
	//or commented out after done
	
	
	/*
	 * Main method used to test Piece class
	 * @PieceTesterMain
	 *
	public static void main(String[] args) {
		
		//System.out.println("starting shit");
		
		
		Player temp = new Player(1, "bob");
		
		TemplatePiece tempPiece = null;
		try{
			tempPiece = new TemplatePiece("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/template board.jpg", temp.getUserID());
			
		}catch(Exception e){
			System.out.println("tempPiece not made, don't believe their lies "+e.getMessage());
		}
		//System.out.println("piece has been made");
		
		if ( !tempPiece.makeJson() ){
			tempPiece = new TemplatePiece();
		}
		JFrame hi = new JFrame();
		JButton first = new JButton();
		first.setText(""+tempPiece.getTeam());
		first.setIcon(tempPiece.getIcon());
		first.setSize(100, 50);
		hi.add(first);
		hi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hi.setSize(100, 100);
		hi.setVisible(true);
		
		
		
		try(Reader reader = new FileReader( System.getProperty("user.dir")+"/resources/image-sets/default-image-set/board_config.json" )){
            Gson gson = new Gson();
            Piece p = gson.fromJson(reader, Piece.class);
            p.updateIcon();
            //p.getIcon().setImage(new ImageIcon(p.iconPath()).getImage());
            //System.out.println("icon = "+p.getIcon().toString());
            JButton iconLabel = new JButton(p.getIcon());
            //iconLabel.setText(""+p.getTeam());
            iconLabel.setSize(100, 50);
            hi.add(iconLabel);
            //System.out.println(p.getOwner().getName());
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}
	
	/**/
	
	
	/*
	 * Main method used to test BoardPanel serialization
	 * @BoardPanelTesterMain
	 * 
	 */
	public static void main(String[] args){
		JFrame tempFrame = new JFrame();
		TemplateBoardPanel bp = new TemplateBoardPanel();
		Container c = tempFrame.getContentPane();
		c.add(bp);
		
		tempFrame.setSize(630,650);	//(width,height)
		tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tempFrame.setVisible(true);
		tempFrame.setResizable(false);
		bp.traversePaths();
	}
	/**/
	
	/**
	 * Man method to test if the Zip utility works
	 * @param args
	 *
	public static void main(String[] args){
		UnzipUtility unzip = new UnzipUtility();
		
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Compressed Folder","zip");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(filter);
		
		jfc.setDialogTitle("Select your zip file!");
		
		jfc.showOpenDialog(null);
		
		File zipedFolder = jfc.getSelectedFile();
		
		JFileChooser jffc = new JFileChooser();
		jffc.setAcceptAllFileFilterUsed(false);
		jffc.setCurrentDirectory(new java.io.File("."));
		jffc.setDialogTitle("Select the destination folder");
		jffc.showSaveDialog(null);
		jffc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jffc.setFileFilter(null);
		
		
		File desktop = jffc.getCurrentDirectory(); //file path redacted
		
		if(!zipedFolder.exists()){
			System.out.println("failure. Zip file doesn't exist");
			System.exit(1);
		}
		
		if(!desktop.exists()){
			System.out.println("failure. Desktop file doesn't exist");
			System.exit(1);
		}
		
		System.out.println(zipedFolder.getAbsolutePath());
		System.out.println(desktop.getAbsolutePath());
		try {
			unzip.unzip(zipedFolder, desktop);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/**/
	
	/*
	 * 
	 *
	public static void main(String[] args){
		
		Runner runme = new Runner();
		
		GameFrame gframe;
		
		JFrame tempFrame = new JFrame();
		Container c = tempFrame.getContentPane();
		
		
		
		EventPanel epanel = new EventPanel(runme);
		
		c.add(epanel);
		
		
		tempFrame.setSize(630,650);	//(width,height)
		tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tempFrame.setVisible(true);
		tempFrame.setResizable(false);
		
		
	}
	/**/
	
	/*
	 * 
	 *
	public static void main(String[] args){
		JFrame frame = new JFrame();
		BoardPanel bp = null;
		try(Reader reader = new FileReader( System.getProperty("user.dir")+"/saved-games/default-game/board_config.json" )){
            Gson gson = new Gson();
            System.out.println(System.getProperty("user.dir"));
            bp = gson.fromJson(reader, BoardPanel.class);
            bp.firstPaintBoard();
            Container c = frame.getContentPane();
            c.add(bp);
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		frame.setSize(630,650);	//(width,height)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		try{
			travel(bp);
		}catch(Exception e){
			
		}
				
	}
	/**/
	
	
	public static void travel(BoardPanel bp){
		String choice = "roll";
		Scanner kb = new Scanner(System.in);
		int turn = 0;
		int num;
		while(choice.equals("roll")){
			System.out.println("roll?");
			choice = kb.nextLine();
			num = roll();
			bp.movePlayer(turn, num);
			turn = (turn+1)%bp.getPlayerCount();
		}
		kb.close();
	}
	public static int roll(){
		Dice d = new Dice(2,6);
		return d.roll();
	}

}

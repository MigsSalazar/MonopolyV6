package main.java.gui;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.*;

import com.google.gson.Gson;

import main.java.models.Player;

public class GuiMainTester {
	/*
	public static void main(String[] args) {
		//this main is meant only to test gui functions and resources
		//NEVER run the full application from here
		//any code written should be temporary and deleted
		//or commented out after done
		//System.out.println("starting shit");
		
		/*
		Player temp = new Player(1, "bob");
		
		Piece tempPiece = null;
		try{
			tempPiece = new Piece("C:/Users/Unknown/git/Monopoly/Version 5/MonopolyV5/resources/image-sets/default-image-set/template board.jpg", temp);
			
		}catch(Exception e){
			System.out.println("tempPiece not made, don't believe their lies "+e.getMessage());
		}
		//System.out.println("piece has been made");
		
		if ( !tempPiece.makeJson() ){
			tempPiece = new Piece();
		}
		JFrame hi = new JFrame();
		JButton first = new JButton();
		first.setText(tempPiece.getOwner().getName());
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
            //iconLabel.setText(p.getOwner().getName());
            iconLabel.setSize(100, 50);
            hi.add(iconLabel);
            //System.out.println(p.getOwner().getName());
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}
	*/
	
	public static void main(String[] args){
		Stamp tempStamp = new Stamp();
		tempStamp.giveBorder();
	}

}

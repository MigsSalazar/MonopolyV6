package main.java.gui;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.java.models.Path;
import main.java.models.PathStep;

import com.google.gson.*;

/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102084765976301993L;
	
	private ArrayList<Piece> gamePieces;
	private ArrayList<Path> piecePaths;
	private HashMap<String, ImageIcon> paintColors;
	private String[][] paintByString;
	private JLabel[][] tiles;
	private Border[] borders;
	private GridLayout gid;
	private File path;
	private int width;
	private int height;
	
	/*
	 * two cases for texture packs:
	 * clean slate - default inamges
	 * texture pack - takes a gson file
	 * 
	 * what stays the same for textures?
	 * 
	 * 
	 * two cases for positions:
	 * clean slate - 
	 * 
	 */
	
	public BoardPanel(){
		path = new File( System.getProperty("user.dir")+"/resources/image-sets/default-image-set/" );
		developBorders();
	}
	
	
	public BoardPanel(Gson gfile){
		
	}
	
	private void developBorders(){
		
	}
	
	private void populateTiles(){
		
	}
	
	private void gsonParser(Gson gfile){
		
	}
	
}





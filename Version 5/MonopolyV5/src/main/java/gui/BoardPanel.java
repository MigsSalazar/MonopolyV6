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
	
	
	private int width = 30;
	private int height = 30;
	private ImageIcon[] imageIndex;
	private String[] iconPaths;
	private int[][] basePaint = new int[30][30];
	private int[][] currPaint = new int[30][30];
	private Stamp[][] stampCollection = new Stamp[30][30];
	private ArrayList<Path> paths = new ArrayList<Path>();
	private ArrayList<Piece> gamePieces;
	
	private ImageIcon[][] displayedBoard;
	
	
	/**
	 * Generates a new json file from scratch of the default board
	 * and saves it to the file location. 
	 * @param file
	 */
	public BoardPanel(){
		
	}
	
	public static void generateTemplateJson(){
		String dir = System.getProperty("user.dir");
		generateTemplateJson(dir);
	}
	
	public static void generateTemplateJson(String file){
		
	}
	
}





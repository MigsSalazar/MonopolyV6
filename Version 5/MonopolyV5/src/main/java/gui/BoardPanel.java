package main.java.gui;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.java.models.Path;
import main.java.models.PathStep;
import main.java.models.Player;

/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102084765976301993L;
	
	
	private ArrayList<Path> playerPaths;
	private HashMap<String, ImageIcon> images;
	private ArrayList<ImageIcon> paintByNumber;
	private JLabel[][] tiles;
	private static JLabel[][] defaultTiles;
	private GridLayout gid;
	private File path;
	
	public BoardPanel(){
		path = new File( System.getProperty("user.dir")+"/resources/image-sets/default-image-set/" );
		
	}
	
	public BoardPanel(File p){
		
	}
	
	
	
}





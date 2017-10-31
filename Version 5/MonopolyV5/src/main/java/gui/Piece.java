package main.java.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import main.java.models.Player;

/**
 * @author Unknown
 *
 */
public class Piece {
	
	private Player owner;
	private File path;
	private transient ImageIcon icon;
	
	public Piece(){
		//System.out.println("constructor 0");
		path = new File(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png");
		icon = new ImageIcon(path.getPath());
		owner = null;
	}
	
	public Piece(Player o){
		//System.out.println("constructor 1");
		owner = o;
		path = new File(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png");
		icon = new ImageIcon( path.getPath() );
	}
	
	public Piece(String dir, Player o){
		//System.out.println("constructor 3");
		path = new File(dir);
		icon= new ImageIcon(path.getPath());
		if(icon==null){
			icon = new ImageIcon( System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png" );
		}
		owner = o;
	}
	
	public Player getOwner(){
		return owner;
	}
	
	public void setOwner(Player o){
		owner = o;
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
	
	public void updateIcon(){
		if(path!=null){
			icon = new ImageIcon(path.getPath());
		}
	}
	
	public boolean needsOwner(){
		return owner == null;
	}
	
	public boolean makeJson(String dir){
		System.out.println("making json with dir argument");
		try{
			dir += "board_config.json";
			finalizeJson(dir);
			return true;
		}catch(IOException ioe){
			return false;
		}
	}
	
	public boolean makeJson(){
		//System.out.println("making json without argument");
		String dir = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/board_config.json";
		try{
			finalizeJson(dir);
			return true;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
			return false;
		}
		
	}
	
	private void finalizeJson(String dir) throws IOException{
		//System.out.println("made it to finalizeJson");
		Writer iowriter = new FileWriter(dir);
		//System.out.println("created writer");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println("made gson");
		gson.toJson(this, iowriter);
		//System.out.println("wrote this");
		iowriter.close();
		//System.out.println("closed writer");
	}
	
}

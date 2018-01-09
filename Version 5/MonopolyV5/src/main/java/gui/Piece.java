/**
 * 
 */
package main.java.gui;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.java.models.GamePath;
import main.java.models.Pair;

import com.google.gson.annotations.Expose;

/**
 * @author Miguel Salazar
 *
 */
public class Piece {
	@Expose private int team;
	@Expose private GamePath travelPath;
	@Expose private String fileLocation;
	@Expose private ArrayList<Pair<Integer,Integer>> specialCases;
	private transient String dir = System.getProperty("user.dir");
	private transient ImageIcon icon;
	
	public Piece(int t, GamePath tp, String fl, ImageIcon i){
		team = t;
		travelPath = tp;
		fileLocation = fl;
		icon = i;
		specialCases = new ArrayList<Pair<Integer,Integer>>();
	}
	
	public Piece(int t, GamePath tp, String fl, ImageIcon i, Pair<Integer,Integer> sc){
		team = t;
		travelPath = tp;
		fileLocation = fl;
		icon = i;
		specialCases = new ArrayList<Pair<Integer,Integer>>();
		specialCases.add(sc);
	}
	
	public Piece(int t, GamePath tp, String fl, ImageIcon i, ArrayList<Pair<Integer,Integer>> sc){
		team = t;
		travelPath = tp;
		fileLocation = fl;
		icon = i;
		specialCases = sc;
	}
	
	public Pair<Integer,Integer> specialCase(int c){
		if(c < specialCases.size() && c > -1){
			return specialCases.get(c);
		}
		return null;
	}
	
	public void updateIcon(String d){
		if(getFileLocation()!=null){
			//System.out.println("Plain get FileLocation: "+getFileLocation());
			dir = d;
			//System.out.println("File Location + dir: " + dir+getFileLocation());
			
			if(new File(dir+getFileLocation()).exists()  ){
				setIcon(new ImageIcon(dir+getFileLocation()));
			}else if(new File(getFileLocation()).exists()){
				setIcon(new ImageIcon(getFileLocation()));
			}else if(new File(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/"+getFileLocation()).exists()){
				setIcon(new ImageIcon(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/"+getFileLocation()));
			}else{
				setIcon(new ImageIcon(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png"));
			}
			//setIcon(new ImageIcon(dir+getFileLocation()));
		}else{
			setIcon(new ImageIcon(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png"));
		}
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String path) {
		this.fileLocation = path;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public GamePath getTravelPath() {
		return travelPath;
	}

	public void setTravelPath(GamePath pathID) {
		this.travelPath = pathID;
	}
	
}

/**
 * 
 */
package main.java.gui;

import java.io.File;

import javax.swing.ImageIcon;

import main.java.models.GamePath;

import com.google.gson.annotations.Expose;

/**
 * @author Miguel Salazar
 *
 */
public class Piece {
	@Expose private int team;
	@Expose private GamePath travelPath;
	@Expose private String fileLocation;
	private transient String dir = System.getProperty("user.dir");
	private transient ImageIcon icon;
	
	public Piece(int t, GamePath tp, String fl, ImageIcon i){
		team = t;
		travelPath = tp;
		fileLocation = fl;
		icon = i;
	}
	
	public void updateIcon(){
		if(getFileLocation()!=null){
			//System.out.println("Plain get FileLocation: "+getFileLocation());
			//getFileLocation().
			if( getFileLocation().indexOf("C:") != 0 &&
					getFileLocation().indexOf("root") != 0){
				dir = System.getProperty("user.dir");
			}else{
				dir = "";
			}
			//System.out.println("File Location + dir: " + dir+getFileLocation());
			
			if(new File(dir+getFileLocation()).exists()  ){
				setIcon(new ImageIcon(dir+getFileLocation()));
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

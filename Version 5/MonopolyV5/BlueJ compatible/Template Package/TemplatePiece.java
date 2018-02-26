 

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author Unknown
 *
 */
public class TemplatePiece {
	
	@Expose private int team;
	@Expose private GamePath travelPath;
	@Expose private String fileLocation;
	private ImageIcon icon;
	
	public TemplatePiece(){
		//System.out.println("constructor 0");
		fileLocation = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png";
		icon = new ImageIcon(fileLocation);
		team = -1;
	}
	
	public TemplatePiece(int t){
		//System.out.println("constructor 1");
		team = t;
		fileLocation = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png";
		icon = new ImageIcon( fileLocation );
	}
	
	public TemplatePiece(String dir, int t){
		//System.out.println("constructor 3");
		fileLocation = dir;
		icon= new ImageIcon(fileLocation);
		if(icon==null){
			icon = new ImageIcon( System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png" );
		}
		team = t;
	}
	
	public TemplatePiece(int t, GamePath tp, String fl){
		team = t;
		travelPath = tp;
		fileLocation = fl;
		icon = new ImageIcon(fileLocation);
	}
	
	public int getTeam(){
		return team;
	}
	
	public void setOwner(int t){
		team = t;
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
	
	public void updateIcon(){
		if(fileLocation!=null){
			icon = new ImageIcon(fileLocation);
		}
	}
	
	public boolean needsOwner(){
		return team == -1;
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

	public GamePath getTravelPath() {
		return travelPath;
	}

	public void setTravelPath(GamePath pathID) {
		this.travelPath = pathID;
	}
	
}

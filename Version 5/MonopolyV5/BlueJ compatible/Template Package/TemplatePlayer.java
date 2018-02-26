 

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class TemplatePlayer {

	@Expose private Map<String,Player> players;
	private transient String dir = System.getProperty("user.dir");
	
	public static void main(String[] args){
		TemplatePlayer tp = new TemplatePlayer();
		tp.makePlayerGson();
	}
	
	public void makePlayerGson(){
		players = new HashMap<String,Player>();
		Player p;
		for(int i=0; i<8; i++){
			p = new Player(i, ""+i);
			players.put(p.getName(), p);
		}
		players.get("0").setTurn(true);
		writeTemplate();
		
	}
	
	
	private boolean writeTemplate(){
		try{
			Writer iowrite = new FileWriter(dir+"/saved-games/default-game/players.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(players, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			return false;
		}
	}

}

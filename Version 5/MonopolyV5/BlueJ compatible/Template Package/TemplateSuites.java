 

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;


public class TemplateSuites {
	
	@Expose Map<String,Suite> suites;
	private String dir = System.getProperty("user.dir");
	
	public TemplateSuites(Map<String,Suite> sui){
		suites = sui;
		writeTemplate();
	}
	
	private boolean writeTemplate(){
		try{
			Writer iowrite = new FileWriter(dir+"/saved-games/default-game/suites.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(this, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			return false;
		}
	}
	
	
}

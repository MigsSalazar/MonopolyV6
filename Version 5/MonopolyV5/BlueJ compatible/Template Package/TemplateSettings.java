 

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class TemplateSettings {

	private static Settings sets = new Settings(null);
	
	public static void main(String[] args) {
		// TODO Auto-generated constructor stub
		writeTemplate();
	}

	
	private static boolean writeTemplate(){
		try{
			String dir = System.getProperty("user.dir");
			Writer iowrite = new FileWriter(dir+"/resources/image-sets/default-image-set/settings.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(sets, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}
	}
}

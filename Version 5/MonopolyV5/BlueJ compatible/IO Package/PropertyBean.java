 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;


public class PropertyBean {
	@Expose private Map<String,Colored> colors;
	@Expose private Map<String,Railroad> rails;
	@Expose private Map<String,Utility> utility;
	
	
	public PropertyBean(){
		colors = new HashMap<String,Colored>();
		rails = new HashMap<String,Railroad>();
		utility = new HashMap<String,Utility>();
	}
	
	
	public PropertyBean(Map<String, Property> parse){
		colors = new HashMap<String,Colored>();
		rails = new HashMap<String,Railroad>();
		utility = new HashMap<String,Utility>();
		
		setPropertyList(parse);
		
	}
	
	public void setPropertyList(Map<String,Property> parse){
		Set<String> keySet = parse.keySet();
		for(String key : keySet){
			if(parse.get(key) instanceof Colored){
				colors.put(key, (Colored)parse.get(key));
				
			}else if(parse.get(key) instanceof Railroad){
				rails.put(key, (Railroad)parse.get(key));
			}else if(parse.get(key) instanceof Utility){
				utility.put(key, (Utility)parse.get(key));
			}
		}
	}
	
	public static PropertyBean jsonToProperties(File filein){
		Gson gson = new Gson();
		Reader readme;
		PropertyBean retval = null;
		try {
			readme = new FileReader(filein);
			retval = gson.fromJson(readme, PropertyBean.class);
			readme.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		//System.out.println("property exists, returning now");
		return retval;
	}
	
	public HashMap<String,Property> getFullMap(Roll uroll){
		HashMap<String,Property> ret = new HashMap<String,Property>();
		GlobalCounter rcount = null;
		GlobalCounter ucount = null;
		for(String r : rails.keySet()){
			if(rcount == null){
				rcount = rails.get(r).getGcount();
			}else{
				rails.get(r).setGcount(rcount);
			}
		}
		
		for(String u : utility.keySet()){
			utility.get(u).setUroll(uroll);
			if(ucount == null){
				ucount = utility.get(u).getGcount();
			}else{
				utility.get(u).setGcount(ucount);
			}
		}
		
		ret.putAll(colors);
		ret.putAll(rails);
		ret.putAll(utility);
		
		return ret;
	}
	
	
}

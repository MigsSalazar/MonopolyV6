package java.skinedit.io;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.*;

public class Recent {

	private class JsonRecents{
		private String 		name;
		private Calendar 	timeStamp;
		private File 		location;
		private int			assetNum;
		
		public JsonRecents(String n, Calendar t, File l, int a){
			name = n;
			timeStamp = t;
			location = l;
			assetNum = a;
		}
		
		
		
		
	}
	
	
	
	
}

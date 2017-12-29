package main.java.templates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import main.java.action.Roll;
import main.java.io.PropertyBean;
import main.java.models.*;

public class TemplateProperty {

	private transient String dir = System.getProperty("user.dir");
	@Expose private Map<String, Property> properties = new HashMap<String,Property>();
	private String suiteNameDir = System.getProperty("user.dir") + "\\saved-games\\default-game\\suiteNames.txt";
	private Map<String,Suite> suites = new HashMap<String, Suite>();
	
	public static void main(String[] args) {
		TemplateProperty tp = new TemplateProperty();
		tp.makeTemplateProperties();
		tp.writeTemplate();
	}
	
	public void makeTemplateProperties(){
		//String name, int position, int price, boolean mortbool, int[] rent, int grade
		
		GlobalCounter railCount = new GlobalCounter(0,4);
		GlobalCounter utilCount = new GlobalCounter(0,2);

		
		Colored medave = new Colored("Mediterranean Ave.","",1,60,false, new int[]{2,10,30,90,160,250,30,30}, 0);
		//Baltic Ave.,3,60,0,30,false,color,purple,0
		Colored balave = new Colored("Baltic Ave.","",3,60,false, new int[]{4,20,60,180,320,450,30,30}, 0);
		
		
		//Reading Railroad,5,200,0,100,false,rail
		Railroad rerail = new Railroad("Reading Railroad","",5,200,false, railCount);
		
		
		//Oriental Ave.,6,100,0,50,false,color,lightBlue,0
		Colored oriave = new Colored("Oriental Ave.","",6,100,false, new int[]{6,30,90,270,400,550,50,50}, 0);
		//Vermont Ave.,8,100,0,50,false,color,lightBlue,0
		Colored verave = new Colored("Vermont Ave.","",8,100,false, new int[]{6,30,90,270,400,550,50,50}, 0);
		//Connecticut Ave.,9,120,0,50,false,color,lightBlue,0
		Colored conave = new Colored("Connecticut Ave.","",9,120,false, new int[]{8,40,100,300,450,600,50,50}, 0);
		
		
		//St. Charles Place,11,140,0,70,false,color,pink,0
		Colored stchpl = new Colored("St. Charles Place","",11,140,false, new int[]{10,50,150,450,625,750,70,100}, 0);
		//Electric Company,12,150,0,75,false,util
		Roll uroll = new Roll(null);
		Utility elecom = new Utility("Electric Company","",12,150,false, uroll, utilCount);
		//States Ave.,13,140,0,70,false,color,pink,0
		Colored staave = new Colored("States Ave.","",13,140,false, new int[]{10,50,150,450,625,750,70,100}, 0);
		//Virginia Ave.,14,160,0,80,false,color,pink,0
		Colored virave = new Colored("Virginia Ave.","",14,60,false, new int[]{12,60,180,500,700,900,80,100}, 0);
		
		//Pennsylvania Railroad,15,200,0,100,false,rail
		Railroad perail = new Railroad("Pennsylvania Railroad","",15,200,false, railCount);
		
		
		//St. James Place,16,180,0,90,false,color,orange,0
		Colored stjmpl = new Colored("St. James Place","",16,180,false, new int[]{14,70,200,550,750,950,90,100}, 0);
		//Tennessee Ave.,18,180,0,90,false,color,orange,0
		Colored tenave = new Colored("Tennessee Ave.","",18,180,false, new int[]{14,70,200,550,750,950,90,100}, 0);
		//New York Ave.,19,200,0,100,false,color,orange,0
		Colored newave = new Colored("New York Ave.","",19,200,false, new int[]{16,80,220,600,800,1000,100,100}, 0);
		
		
		//Kentucky Ave.,21,220,0,110,false,color,red,0
		Colored kenave = new Colored("Kentucky Ave.","",21,220,false, new int[]{18,90,250,700,875,1050,110,150}, 0);
		//Indiana Ave.,23,220,0,110,false,color,red,0
		Colored indave = new Colored("Indiana Ave.","",23,220,false, new int[]{18,90,250,700,875,1050,110,150}, 0);
		//Illinois Ave.,24,240,0,120,false,color,red,0
		Colored illave = new Colored("Illinois Ave.","",24,240,false, new int[]{20,100,300,750,925,1100,120,150}, 0);
		
		
		//B&O Railroad,25,200,0,100,false,rail
		Railroad borail = new Railroad("B&O Railroad","",25,60,false, railCount);
		
		
		//Atlanic Ave.,26,260,0,130,false,color,yellow,0
		Colored atlave = new Colored("Atlantic Ave.","",26,260,false, new int[]{22,110,330,800,975,1150,130,150}, 0);
		//Ventnor Ave.,27,260,0,130,false,color,yellow,0
		Colored venave = new Colored("Ventnor Ave.","",27,260,false, new int[]{22,110,330,800,975,1150,130,150}, 0);
		//WaterWorks,28,150,0,75,false,util
		Utility watwor = new Utility("Water Works","",28,150,false, uroll, utilCount);
		//Marvin Gardens,29,280,0,140,false,color,yellow,0
		Colored margar = new Colored("Marvin Gardens","",29,280,false, new int[]{24,120,360,850,1025,1200,140,150}, 0);
		
		
		//Pacific Ave.,31,300,0,150,false,color,green,0
		Colored pacave = new Colored("Pacific Ave.","",31,300,false, new int[]{26,130,390,900,1100,1275,150,200}, 0);
		//North Carolina Ave.,32,300,0,150,false,color,green,0
		Colored nocaav = new Colored("North Carolina Ave.","",32,300,false, new int[]{26,130,390,900,1100,1275,150,200}, 0);
		//Pennsylvania Ave.,34,320,0,160,false,color,green,0
		Colored penave = new Colored("Pennsylvania Ave.","",34,320,false, new int[]{28,150,450,1000,1200,1400,160,200}, 0);
		
		
		//Short Line,35,200,0,100,false,rail
		Railroad sholin = new Railroad("Short Line","",35,200,false, railCount);
		
		
		//Park Place,37,350,0,175,false,color,blue,0
		Colored parpla = new Colored("Park Place","",37,350,false, new int[]{35,175,500,1100,1300,1500,175,200}, 0);
		//Board Walk,39,400,0,200,false,color,blue,0
		Colored boawal = new Colored("Board Walk","",39,400,false, new int[]{50,200,600,1400,1700,2000,200,200}, 0);
		
		String[] suiteNames = getSuiteNames();
		
		SmallSuite purple = new SmallSuite(suiteNames[0],medave, balave);
		BigSuite lightBlue = new BigSuite(suiteNames[1],oriave,verave,conave);
		BigSuite pink = new BigSuite(suiteNames[2],stchpl,staave,virave);
		BigSuite orange = new BigSuite(suiteNames[3],stjmpl,tenave,newave);
		BigSuite red = new BigSuite(suiteNames[4],kenave,indave,illave);
		BigSuite yellow = new BigSuite(suiteNames[5],atlave,venave,margar);
		BigSuite green = new BigSuite(suiteNames[6],pacave,nocaav,penave);
		SmallSuite blue = new SmallSuite(suiteNames[7],parpla, boawal);
		
		
		suites.put(suiteNames[0], purple);
		suites.put(suiteNames[1], lightBlue);
		suites.put(suiteNames[2], pink);
		suites.put(suiteNames[3], orange);
		suites.put(suiteNames[4], red);
		suites.put(suiteNames[5], yellow);
		suites.put(suiteNames[6], green);
		suites.put(suiteNames[7], blue);
		
		Suite tempSuite;
		for(String s : suiteNames){
			tempSuite = suites.get(s);
			for(Property p : tempSuite.getProperties()){
				properties.put(p.getName(), p);
			}
		}
		
		properties.put(elecom.getName(), elecom);
		properties.put(watwor.getName(), watwor);
		properties.put(rerail.getName(), rerail);
		properties.put(perail.getName(), perail);
		properties.put(borail.getName(), borail);
		properties.put(sholin.getName(), sholin);
		
		
	}
	
	private String[] getSuiteNames(){
		String[] names = new String[8];
		File nameDoc = new File(suiteNameDir);
		Scanner filein;
		String[] temp;
		int index = 0;
		try {
			filein = new Scanner(nameDoc);
			while(filein.hasNextLine()){
				String get = filein.nextLine();
				temp = get.split(",");
				names[index] = temp[0];
				index++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	private boolean writeTemplate(){
		try{
			Writer iowrite = new FileWriter(dir+"/saved-games/default-game/properties.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			PropertyBean prbean = new PropertyBean(properties);
			gson.toJson(prbean, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			return false;
		}
	}

}

package main.java.templates;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import main.java.models.GameCard;

public class TemplateCommunityChest {
	@Expose private ArrayList<GameCard> chance;
	
	public static void main(String[] args){
		TemplateCommunityChest me = new TemplateCommunityChest();
		me.chance = new ArrayList<GameCard>();
		
		me.chance.add(new GameCard("<html>Advance to Go!"
								+ "<br>(Collect $200)</html>",
									200, false,0,false,false,"","go",false, 0, 0));
		me.chance.add(new GameCard("<html>Bank error in your favor"
								+ "<br>Collect $200</html>",
				200, false,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>Doctor's fees."
								+ "<br>Pay $50</html>",
				-50, false,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>From sale of stock"
								+ "<br>you get $50</html>",
				50, false,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>Get out of jail free!"
								+ "<br>This card may be kept until needed,"
								+ "<br>or traded/sold.</html>",
				0, false,0,true,false,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>Go to directly to Jail"
				+				 "<br>Do not pass Go - Do not collect $200.</html>",
				0, false,0,false,true,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>Grand Opera Night!"
								+ "<br>Collect $50 from every player"
								+ "<br>foropening night seats.</html>",
				50,true,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>Holiday Fund matures!"
								+ "<br>Receive $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>Income tax refund!"
								+ "<br>Collect $20</html>",
				20, false,0,false,false,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>It is your birthday"
								+ "<br>Collect $10!</html>",
				10, false,0,false,false,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>Life insurance matures!"
								+ "<br>Collect $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>Pay hospital fees of $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>Pay school fees of $150</html>",
				150, false,0,false,false,"","",false, 0, 0));
		
		me.chance.add(new GameCard("<html>Recieve $25 consultancy fee</html>",
				25, false,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>You are assessed for stree repairs-"
								+ "<br>$40 per house"
								+ "<br>$115 per hotel</html>",
				0, false,0,false,false,"","",true, 40,115));
		
		me.chance.add(new GameCard("<html>You have won second prize"
								+ "<br>in a beauty contest!"
								+ "<br>Collect $10</html>",
				10, false,0,false,false,"","",false, 0, 0));
		me.chance.add(new GameCard("<html>You inherit $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		
		me.writeTemplate();
	}
	
	
	private boolean writeTemplate(){
		try{
			String dir = System.getProperty("user.dir");
			Writer iowrite = new FileWriter(dir+"/saved-games/default-game/community-chest.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(chance, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}
	}
}

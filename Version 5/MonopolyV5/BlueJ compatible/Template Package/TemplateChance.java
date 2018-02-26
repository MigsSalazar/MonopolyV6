 

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TemplateChance {

	@Expose private ArrayList<GameCard> commChest;
	
	/*
	 * String text,
		int moneyEarned, boolean globalFunds,
		int basemovement,
		boolean getOutOfJail,
		boolean goToJail,
		String findNearest, String findThis,
		boolean propRenovation, int houseCost, int hotelCost
	 */
	
	public static void main(String[] args){
		TemplateChance me = new TemplateChance();
		me.commChest = new ArrayList<GameCard>();
		me.commChest.add(new GameCard("<html>Advance to Go!<br>(Collect $200)</html>",
				200, false,0,false,false,"",
				"go",
				false, 0, 0));
		me.commChest.add(new GameCard("<html>Advance to Illinois Ave.!<br>(If you ass Go, collect $200)</html>",
				0, false,0,false,false,"",
				"Illinois Ave.",
				false, 0, 0));
		me.commChest.add(new GameCard("<html>Advance token to the nearest Utility"
									+ "<br>If unowned, you may buy it from the bank."
									+ "<br>If owned, throw the dice and pay the owner"
									+ "<br>ten times the amount thrown</html>",
				0, false,0,false,false,
				"utility",
				"",false, 0, 0));
		me.commChest.add(new GameCard("<html>Bank pays you dividends of $50</html>",
				50,
				false,0,false,false,"","",false, 0, 0));
		me.commChest.add(new GameCard("<html>Get out of jail free!"
									+ "<br>(This card may be kept until needed,"
									+ "<br>or traded/sold)</html>",
				0, false,0,true,false,"","",false, 0, 0));
		me.commChest.add(new GameCard("<html>Go back 3 spaces</html>",
				0, false,-3,false,false,"","",false, 0, 0));
		
		me.commChest.add(new GameCard("<html>Go directly to jail."
									+ "<br>Do not pass go,"
									+ "<br>do not collect $200</html>",
				0, false,0,false,true,"","",false, 0, 0));
		
		me.commChest.add(new GameCard("<html>Make general repairs on all your property"
									+ "<br>For each house pay $25"
									+ "<br>For each hotel pay $100</html>",
				0, false,0,false,false,"","",true, 25, 100));
		
		me.commChest.add(new GameCard("<html>Pay poor tax of $15</html>",
				-15, false,0,false,false,"","",false, 0, 0));
		
		me.commChest.add(new GameCard("<html>Take a trip to Reading Railraod</html>",
				0, false,0,false,false,"","Reading Railroad",false, 0, 0));
		
		me.commChest.add(new GameCard("<html>Advance token to Boardwalk</html>",
				0, false,0,false,false,"","Board Walk",false, 0, 0));
		
		me.commChest.add(new GameCard("<html>You have been elected Chairman"
									+ "<br>of the Board, Pay each player $50</html>",
				-50, true,0,false,false,"","",false, 0, 0));
		
		me.commChest.add(new GameCard("<html>You building and loan matures"
									+ "<br>Collect $150</html>",
				150, false,0,false,false,"","",false, 0, 0));
		me.writeTemplate();
	}
	
	private boolean writeTemplate(){
		try{
			String dir = System.getProperty("user.dir");
			Writer iowrite = new FileWriter(dir+"/saved-games/default-game/chance.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(commChest, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}
	}
	
}

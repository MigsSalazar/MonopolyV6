package main.java.action;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

import main.java.gui.TConfirm;
import main.java.gui.TOffer;
import main.java.gui.TPrompt;
import main.java.models.Asset;
import main.java.models.Player;
import main.java.models.Property;

/**
 * Control class. Goes through the process of
 * player's trading assets.
 * @author Miguel Salazar
 *
 */
public class TradeManager{

	private Runner gameVars;
	
	private Player player1;
	private Player player2;
	private Map<String,Player> players;
	private Asset tradeAsset;
	private ArrayList<Asset> offeredAsset;
	
	
	private TPrompt prompt;
	private TOffer offer;
	private TConfirm confirm;
	
	private int step = 0;
	private boolean agreed = false;
	
	/**
	 * Trade Manager's only constructor. Requires a full list
	 * of active players and the Player who wishes to trade with another
	 * @param p1	First Player who wishes to make an offer for another player's asset
	 * @param map	Full list of players active or inactive in the game
	 */
	public TradeManager(Runner gv, Player p1, Map<String, Player> map){
		gameVars = gv;
		//player1 wishes to make an offer
		player1 = p1;
		//player2 owns the desired asset
		players = map;
		
		//runManager();
		
		
	}
	
	/**
	 * Controls the process of the Trade Manager. Runs through each step until the request is canceled or completed
	 */
	public void runManager(){
		while(step<3){
			switch(step){
			case 0: step = 3;
					prompt = new TPrompt(gameVars, player1, players, this);
					prompt.setBounds(100, 100, 450, 300);
					prompt.setVisible(true);
					prompt.setResizable(false);
					//System.out.print("DEBUG: Player1: "+player1.getName()+" Player2: "+player2.getName()+" Desired asset: "+tradeAsset.toString() );
					break;
			case 1: step = 3;
					offer = new TOffer(gameVars.getFrame(), player1, this);
					offer.setBounds(100,100,450,500);
					offer.setVisible(true);
					offer.setResizable(false);
					//step = 3;
					break;
			case 2: step = 3;
					confirm = new TConfirm(gameVars, player1, player2, tradeAsset, offeredAsset, this);
					confirm.setBounds(100, 100, 450, 300);
					confirm.setVisible(true);
					confirm.setResizable(false);
					if(agreed){
						doTheTrade();
					}
					break;
			}
		}
	}
	
	/**
	 * Performs the trade.
	 */
	public void doTheTrade(){
		JOptionPane.showMessageDialog(gameVars.getFrame(), "Yup this all worked a-ok");
		
		if(tradeAsset.getType().equals("prop") ){
			Property tradeProp = null;
			Map<String,Property> props = player2.getProps();
			for(String p : props.keySet()){
				if(tradeAsset.getAsset().equals(p)){
					tradeProp = props.get(p);
				}
			}
			player1.addProperty(tradeProp);
			player2.removeProperty(tradeProp);
		}else if(tradeAsset.getType().equals("gojf")){
			player1.addJailCard();
			player2.subJailCard();
		}
		
		Property offeredProp = null;
		for(Asset a : offeredAsset){
			if(a.getType().equals("cash")){
				player1.subCash(a.getWorth());
				player2.addCash(a.getWorth());
			}else if(a.getType().equals("gojf")){
				player1.subJailCard();
				player2.subJailCard();
			}else if(a.getType().equals("prop")){
				Map<String,Property> props = player1.getProps();
				for(String p : props.keySet()){
					if(  p.equals(a.getAsset())  ){
						offeredProp = props.get(p);
					}
				}
				player1.removeProperty(offeredProp);
				player2.addProperty(offeredProp);
			}
		}
		
		gameVars.getFrame().getGameStats();
		
	}
	
	/**
	 * Sets the current step of the manager.
	 * Step 0- The Prompt asking which player (player 2) the first player wishes to trade with
	 * Step 1- The Offer where player 1 makes an offer for player 2's asset
	 * Step 2- The Confirmation step where the trade is finialized
	 * @param s	int value denoting which step you'd like to begin
	 */
	public void setStep(int s){
		step = s;
	}
	
	/**
	 * Sets the current step of the manager.
	 * Step 0- The Prompt asking which player (player 2) the first player wishes to trade with
	 * Step 1- The Offer where player 1 makes an offer for player 2's asset
	 * Step 2- The Confirmation step where the trade is finialized
	 * @return	an int value denoting the cirrent step
	 */
	public int getStep(){
		return step;
	}
	
	/**
	 * Defines the player making the offer for another player's assets
	 * @param p	Player object
	 */
	public void setPlayer1(Player p){
		player1 = p;
	}
	
	/**
	 * Defines the player with the desired asset
	 * @param p
	 */
	public void setPlayer2(Player p){
		player2 = p;
	}
	
	/**
	 * Defines the desired asset held by player 2
	 * @param a	Asset object
	 */
	public void setTradeAsset(Asset a){
		tradeAsset = a;
	}
	
	/**
	 * Returns the desired asset object that is up for bargain
	 * @return
	 */
	public Asset getTradeAsset(){
		return tradeAsset;
	}
	/**
	 * Defines the desired trade asset
	 * @param a
	 */
	public void setOfferedAsset(ArrayList<Asset> a){
		offeredAsset = a;
	}
	
	/**
	 * Returns the ArrayList of Assets that player 1 has offered in trade of player 2's asset
	 * @return
	 */
	public ArrayList<Asset> getOfferedAsset(){
		return offeredAsset;
	}
	
	/**
	 * Returns the stored value of whether or not the two players have agreed to the trade
	 * @return	boolean stating if the trade was accepted or not
	 */
	public boolean isAgreed(){
		return agreed;
	}
	
	/**
	 * Sets the boolean value agreed which defines whether or not the two players have agreed on the trade
	 * @param a	boolean value by which the 
	 */
	public void setAgreed(boolean a){
		agreed = a;
	}
	
}

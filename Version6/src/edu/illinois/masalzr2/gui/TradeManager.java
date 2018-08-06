package edu.illinois.masalzr2.gui;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.gui.TConfirm;
import edu.illinois.masalzr2.gui.TOffer;
import edu.illinois.masalzr2.gui.TPrompt;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;

/**
 * Control class. Goes through the process of
 * player's trading assets.
 * @author Miguel Salazar
 *
 */
public class TradeManager{

	private GameVariables gameVars;
	
	private Player player1;
	private Player player2;
	private Map<String,Player> players;
	private Object tradeAsset;
	private List<Object> offeredAsset;
	
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
	public TradeManager(GameVariables gv, Player p1, Map<String, Player> map){
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
					offer = new TOffer(gameVars.getFrame(), gameVars, player1, this);
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
		
		if(tradeAsset instanceof Property ){
			player1.addProp((Property) tradeAsset);
			player2.removeProp((Property) tradeAsset);
		}else if(tradeAsset instanceof String){
			player1.addOneJailCard();
			player2.subOneJailCard();
		}
		
		for(Object a : offeredAsset){
			if(a instanceof Integer){
				player1.subCash(((Integer) a).intValue());
				player2.addCash(((Integer) a).intValue());
			}else if(a instanceof String){
				player1.subOneJailCard();
				player2.addOneJailCard();
			}else if(a instanceof Property){
				player1.removeProp((Property) a);
				player2.addProp((Property) a);
			}
		}
		
		gameVars.getFrame().revalidate();
		
		JOptionPane.showMessageDialog(gameVars.getFrame(), "The bank handled oversaw this transaction.");
		
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
	public void setTradeAsset(Object a){
		tradeAsset = a;
	}
	
	/**
	 * Returns the desired asset object that is up for bargain
	 * @return
	 */
	public Object getTradeAsset(){
		return tradeAsset;
	}
	/**
	 * Defines the desired trade asset
	 * @param a
	 */
	public void setOfferedAsset(List<Object> a){
		offeredAsset = a;
	}
	
	/**
	 * Returns the ArrayList of Assets that player 1 has offered in trade of player 2's asset
	 * @return
	 */
	public List<Object> getOfferedAsset(){
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

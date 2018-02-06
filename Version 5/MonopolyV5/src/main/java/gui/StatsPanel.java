package main.java.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.action.Runner;
import main.java.models.Player;

public class StatsPanel extends JPanel {

	private Runner gameVars;
	private ArrayList<Player> players;
	private PlayerPanel[] panels;
	private GridLayout layout;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4482036009246875176L;

	public StatsPanel(Runner gv){
		gameVars = gv;
		
		
		
		Map<String,Player> temp = gameVars.getPlayers();
		
		players = sortByID(temp);
		
		panels = new PlayerPanel[players.size()];
		
		for(int i=0; i<players.size(); i++){
			panels[i] = new PlayerPanel(players.get(i), gameVars.getCurrencySymbol());
		}
		
		int width = (int)Math.ceil(players.size()/2);
		
		layout = new GridLayout(2,width);
		
		setLayout(layout);
		
		for(int i=0; i<panels.length; i++){
			add(panels[i]);
		}
		
		Border space = BorderFactory.createEmptyBorder(0, 0, 0, 7);
		this.setBorder(space);
		this.setVisible(true);
	}

	public void updatePlayers(){
		for(PlayerPanel p : panels){
			p.updatePanel();
		}
	}
	
	private ArrayList<Player> sortByID(Map<String,Player> temp){
		
		ArrayList<Player> yup = new ArrayList<Player>(temp.values());
		ArrayList<Player> retval = new ArrayList<Player>();
		for(int i=0; i<yup.size(); i++){
			int j=0;
			while(yup.get(j).getUserID() != i){
				j++;
			}
			retval.add(yup.get(j));
		}
		
		return retval;
	}
	
	
}

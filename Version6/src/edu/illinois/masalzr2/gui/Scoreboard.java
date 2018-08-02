package edu.illinois.masalzr2.gui;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;

import edu.illinois.masalzr2.models.Player;

public class Scoreboard {
	
	private JPanel scores;
	
	private StatsTile[] tiles;
	
	public Scoreboard(Map<Integer, Player> players, String currency) {
		
		rebuildScoreboard(players, currency);
		
	}
	
	public JPanel getScoreboard() {
		return scores;
	}
	
	public void rebuildScoreboard( Map<Integer, Player> players, String currency ) {
		scores = new JPanel( new GridLayout(2,4) );
		
		tiles = new StatsTile[players.size()];
		
		for(int i=0; i<tiles.length; i++) {
			tiles[i] = new StatsTile( players.get(i), currency );
			players.get(i).addChangeListener(tiles[i]);
			scores.add( tiles[i] );
		}
	}
	

}

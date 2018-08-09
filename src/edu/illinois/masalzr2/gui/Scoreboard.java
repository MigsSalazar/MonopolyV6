package edu.illinois.masalzr2.gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import edu.illinois.masalzr2.models.Player;

public class Scoreboard {
	
	private JPanel scores;
	
	private StatsTile[] tiles;
	
	public Scoreboard(ImageIcon[] playerIcons, List<Player> players, String currency) {
		
		rebuildScoreboard(playerIcons, players, currency);
		
	}
	
	public JPanel getScoreboard() {
		return scores;
	}
	
	public void rebuildScoreboard( ImageIcon[] playerIcons, List<Player> players, String currency ) {
		scores = new JPanel( new GridLayout(2,4) );
		
		tiles = new StatsTile[players.size()];
		
		for(int i=0; i<tiles.length; i++) {
			tiles[i] = new StatsTile( playerIcons[i], players.get(i), currency );
			List<ChangeListener> listeners = players.get(i).getListeners();
			int j = 0;
			while(j<listeners.size()) {
				if(listeners.get(j) instanceof StatsTile) {
					players.get(i).removeListener(listeners.get(j));
				}else {
					j++;
				}
			}
			players.get(i).addChangeListener(tiles[i]);
			scores.add( tiles[i] );
		}
	}
	

}

package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.java.models.Player;
import main.java.models.Property;

/**
 * Extends JPanels. Purpose is to display the information pertaining to a passed Player object
 * @author Miguel Salazar
 *
 */
public class PlayerPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7315064165704321061L;

	private Player myPlayer;
	
	private JPanel labels = new JPanel(new GridLayout(8,1));
	private JLabel name;
	private JLabel wealth;
	private JLabel bank;
	private JLabel posi;
	private JLabel jail;
	private JLabel card;
	private JLabel actv;
	private JLabel prop;
	
	private JScrollPane pListScroller;
	private JTextArea pTextArea;
	
	/**
	 * Default constructor. Purpose of this panel is to display all the necessary
	 * information pertaining to the passed player object needed for game play
	 * @param p	Player object for which to display the current standings
	 */
	public PlayerPanel(Player p){
		myPlayer=p;
		this.setLayout(new BorderLayout());
		
		formLabels();
		this.add(labels, BorderLayout.NORTH);
		
		formJList();
		this.add(pListScroller, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(175,300));
	}
	
	/**
	 * Generates the labels for each player listing the current assets and statuses of the player
	 */
	public void formLabels(){
		
		name = new JLabel(myPlayer.getName());
		wealth = new JLabel("Cumulative wealth: $"+myPlayer.getWealth());
		bank = new JLabel("Liquid cash: $"+myPlayer.getCash());
		posi = new JLabel("Current Position: "+myPlayer.getPosition());
		jail = new JLabel("Jail Status: "+myPlayer.isInJail());
		card = new JLabel("Get out of Jail Cards: "+myPlayer.getJailCards());
		actv = new JLabel("Is active: "+myPlayer.isActive());
		prop = new JLabel("Properties owned: ");
		
		labels.add(name);
		labels.add(wealth);
		labels.add(bank);
		labels.add(posi);
		labels.add(jail);
		labels.add(card);
		labels.add(actv);
		labels.add(prop);
		
	}
	
	/**
	 * Generates a list of the properties owned by a player to be displayed on a JTextArea
	 * within a JScrollPane
	 */
	public void formJList(){
		pTextArea = new JTextArea();
		pTextArea.setEditable(false);
		
		String fullList = getPropertyList();
		
		pTextArea.setText(fullList);
		
		pListScroller = new JScrollPane(pTextArea);
		
	}

	/**
	 * Updates all components of the JPanel to provide updated information
	 * on the passed player
	 */
	public void updatePanel(){
		
		name.setText(myPlayer.getName());
		wealth.setText("Cumulative Wealth: $"+myPlayer.getWealth());
		bank.setText("Liquid Cash: $"+myPlayer.getCash());
		posi.setText("Current Position "+myPlayer.getPosition());
		jail.setText("Jail Status: "+myPlayer.isInJail());
		card.setText("Get our of Jail Cards: "+myPlayer.getJailCards());
		actv.setText("Is active: "+myPlayer.isActive());
		
		String fullList = getPropertyList();
		
		pTextArea.setText(fullList);
		
	}

	private String getPropertyList() {
		String fullList = "";
		Map<String,Property> props = myPlayer.getProps();
		for(String key : props.keySet()){
			fullList += key;
			if(props.get(key).isMortgaged()){
				fullList += "-Mortgaged";
			}
			fullList += "\n";
		}
		return fullList;
	}
	
}

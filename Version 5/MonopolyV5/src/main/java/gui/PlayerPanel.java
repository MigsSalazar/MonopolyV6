package main.java.gui;

import java.awt.BorderLayout;
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
	
	private JPanel labels = new JPanel(new GridLayout(7,1));
	private JLabel name;
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
		
	}
	
	/**
	 * Generates the labels for each player listing the current assets and statuses of the player
	 */
	public void formLabels(){
		
		name = new JLabel(myPlayer.getName()+" has a cumulative $"+myPlayer.getWealth());
		bank = new JLabel("$"+myPlayer.getCash()+" of which is liquid cash.");
		posi = new JLabel("Currently in position "+myPlayer.getPosition());
		jail = new JLabel("In Jail?: "+myPlayer.isInJail());
		card = new JLabel("Number of Get our of Jail Cards: "+myPlayer.getJailCards());
		actv = new JLabel("Is active: "+myPlayer.isActive());
		prop = new JLabel("Properties owned: ");
		
		labels.add(name);
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
		
		String fullList = "";
		Map<String,Property> props = myPlayer.getProps();
		for(String key : props.keySet()){
			fullList += key;
		}
		
		pTextArea.setText(fullList);
		
		pListScroller = new JScrollPane(pTextArea);
		
	}

	/**
	 * Updates all components of the JPanel to provide updated information
	 * on the passed player
	 */
	public void updatePanel(){
		
		name.setText(myPlayer.getName()+" has a cumulative $"+myPlayer.getWealth());
		bank.setText("$"+myPlayer.getCash()+" of which is liquid cash.");
		posi.setText("Currently in position "+myPlayer.getPosition());
		jail.setText("In Jail?: "+myPlayer.isInJail());
		card.setText("Number of Get our of Jail Cards: "+myPlayer.getJailCards());
		actv.setText("Is active: "+myPlayer.isActive());
		
		String fullList = "";
		for(String key : myPlayer.getProps().keySet()){
			fullList += key+"\n";
		}
		
		pTextArea.setText(fullList);
		
	}
	
}

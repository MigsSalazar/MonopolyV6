package edu.illinois.masalzr2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.Property;

public class StatsTile extends JPanel implements ChangeListener{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private Player myPlayer;
	
	private String currencySymbol;
	
	private JPanel labels = new JPanel(new GridLayout(7,1));
	//private JLabel name;
	private Border title;
	private JLabel wealth;
	private JLabel bank;
	private JLabel posi;
	private JLabel card;
	private JLabel actv;
	private JLabel prop;
	
	private int propSize = 0;
	
	private JScrollPane pListScroller;
	private JPanel pTextArea;
	
	/**
	 * Default constructor. Purpose of this panel is to display all the necessary
	 * information pertaining to the passed player object needed for game play
	 * @param p	Player object for which to display the current standings
	 */
	public StatsTile(Player p, String cs){
		
		Border up = BorderFactory.createRaisedBevelBorder();
		Border down = BorderFactory.createLoweredBevelBorder();
		Border temp = BorderFactory.createCompoundBorder(down, up);
		
		title = BorderFactory.createTitledBorder(temp, p.getName());
		
		this.setBorder(title);
		currencySymbol = cs;
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
		
		//name = new JLabel(myPlayer.getName());
		posi = new JLabel("Current Position: "+myPlayer.getPosition());
		wealth = new JLabel("Assets: "+currencySymbol+myPlayer.getWealth());
		colorWealth();
		bank = new JLabel("Cash: "+currencySymbol+myPlayer.getCash());
		colorBank();
		actv = new JLabel("Bankrupt: "+ myPlayer.isBankrupt());
		colorActive();
		card = new JLabel("Get out of Jail Cards: "+myPlayer.getJailCard());
		
		prop = new JLabel("Properties owned: ");
		
		
		//labels.add(name);
		labels.add(posi);
		labels.add(wealth);
		labels.add(bank);
		labels.add(actv);
		labels.add(card);
		labels.add(prop);
		
	}

	private void colorActive() {
		if( !myPlayer.isBankrupt() ){
			actv.setBackground(Color.GREEN);
		}else{
			actv.setBackground(Color.RED);
		}
		
		actv.setOpaque(true);
	}

	
	
	/**
	 * Generates a list of the properties owned by a player to be displayed on a JTextArea
	 * within a JScrollPane
	 */
	public void formJList(){
		pTextArea = new JPanel(new GridLayout(10, 1));
		pTextArea.setOpaque(false);
		
		JLabel[] fullList = getPropertyList();
		
		pTextArea.removeAll();
		
		for(JLabel jl : fullList) {
			pTextArea.add(jl);
		}
		
		pListScroller = new JScrollPane(pTextArea);
		pListScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	private void colorBank() {
		Color c;
		
		if(myPlayer.getCash() < 700){
			c = Color.RED;
		}else if(myPlayer.getCash() > 1300){
			c = Color.GREEN;
		}else{
			c = Color.YELLOW;
		}
		bank.setBackground(c);
		bank.setOpaque(true);
	}
	
	public void colorWealth(){
		Color c;
		if(myPlayer.getWealth() < 1000){
			c = Color.RED;
		}else if(myPlayer.getWealth() > 2000){
			c = Color.GREEN;
		}else{
			c = Color.YELLOW;
		}
		wealth.setBackground(c);
		wealth.setOpaque(true);
	}

	private JLabel[] getPropertyList() {
		
		List<Property> props = new ArrayList<Property>( myPlayer.getProps().values() );
		JLabel[] labels = new JLabel[props.size()];
		
		props.sort(Property.getPositionComparator());
		
		for(int i=0; i<props.size(); i++) {
			Property p = props.get(i);
			
			labels[i] = new JLabel(p.getName() + (p.isMortgaged() ? " - Mortgaged" : ""));
			labels[i].setOpaque(true);
			if(p.getColor() != 0) {
				labels[i].setBackground( new Color(p.getColor()) );
			}
			
		}
		
		return labels;
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		wealth.setText("Assets: "+currencySymbol+myPlayer.getWealth());
		bank.setText("Cash: "+currencySymbol+myPlayer.getCash());
		posi.setText("Current Position "+myPlayer.getPosition());
		card.setText("Get our of Jail Cards: "+myPlayer.getJailCard());
		actv.setText("Bankrupt: "+myPlayer.isBankrupt());
		
		if( myPlayer.getProps().size() != propSize ) {
			JLabel[] fullList = getPropertyList();
			
			pTextArea.removeAll();
			
			if(fullList.length > ((GridLayout)pTextArea.getLayout()).getRows() ) {
				((GridLayout)pTextArea.getLayout()).setRows(fullList.length + 1);
			}
			
			for(JLabel jl : fullList) {
				pTextArea.add(jl);
			}
			
		}
		
		
		
	}

	
	
}

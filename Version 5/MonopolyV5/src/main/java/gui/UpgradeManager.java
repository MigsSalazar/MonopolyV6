package main.java.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.action.Runner;
import main.java.models.BigSuite;
import main.java.models.Colored;
import main.java.models.SmallSuite;
import main.java.models.Player;
import main.java.models.Property;
import main.java.models.Suite;

public class UpgradeManager extends JDialog implements ActionListener{

	private Runner gameVars;
	private Player player;
	private Map<String,Property> props;
	private Map<String,Suite> suites;
	private boolean hasMonopoly;
	private Suite currSuite;

	private JPanel selection;
	private JButton question;
	private JComboBox<String> colors;
	private JButton confirm;
	
	
	private JPanel alteration;
	private JPanel[] propDisplay;
	private JLabel[] propNames;
	private JPanel[] textButtons;
	private JLabel[] propGrade;
	private JButton[] add;
	private JButton[] sub;
	private JLabel improper = new JLabel("No data to show");
	
	
	public UpgradeManager(Runner gv, Player pl) {
		super(gv.getFrame(), "Upgrade Manager", true);
		gameVars = gv;
		player = pl;
		props = gameVars.getProperties();
		suites = gameVars.getColoredProps();
		
		developTop();
		developBottom();
		
	}

	public void beginManager() {
		try{
			BorderLayout bl = new BorderLayout();
			bl.setHgap(5);
			bl.setVgap(5);
			this.setLayout(bl);
			this.add(selection, BorderLayout.NORTH);
			this.add(alteration, BorderLayout.CENTER);
			
			this.pack();
			this.setIconImage(gameVars.getFrame().getTitleIcon());
			this.setResizable(false);
			this.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(gameVars.getFrame(), "Cannot start Upgrade Manager");
			this.dispose();
		}
	}
	
	private void developBottom(){
		if(hasMonopoly){
			currSuite = suites.get(colors.getSelectedItem());
			List<Colored> suiteProps = currSuite.getProperties();
			
			alteration = new JPanel(new GridLayout(3,1));
			propDisplay = new JPanel[3];
			propNames = new JLabel[3];
			
			propGrade = new JLabel[3];
			add = new JButton[3];
			sub = new JButton[3];
			textButtons = new JPanel[3];
			
			for(int i=0; i<3; i++){
				propDisplay[i] = new JPanel(new BorderLayout());
				
				propNames[i] = new JLabel();
				
				propGrade[i] = new JLabel();
				
				add[i] = new JButton("+");
				add[i].addActionListener(this);
				
				sub[i] = new JButton("-");
				sub[i].addActionListener(this);
				
				textButtons[i] = new JPanel(new GridLayout(1,3));
			}
			
			fillNames(suiteProps);
			fillGrades(suiteProps);
			completeAddSub(suiteProps);
			populateTextButtons();
			populatePropDisplay();
			
		}else{
			alteration = new JPanel();
			alteration.add(improper);
			propDisplay = null;
			propNames = null;
			textButtons = null;
			propGrade = null;
			add = null;
			sub = null;
			
		}
	}
	
	private void developTop(){
		selection = new JPanel(new BorderLayout());
		
		confirm = new JButton("Ok");
		confirm.addActionListener(this);
		
		ArrayList<String> heldSuites = new ArrayList<String>();
		
		for(Suite s : suites.values()){
			if(s.playerHasMonopoly(player) && !s.hasMortgage()){
				heldSuites.add(s.getColor());
			}
		}
		if(heldSuites.size() > 0){
			colors = new JComboBox<String>(heldSuites.toArray(new String[heldSuites.size()]));
			hasMonopoly = true;
		}else{
			colors = new JComboBox<String>();
			colors.addItem("You own no monopolies");
			hasMonopoly = false;
		}
		//colors = new JComboBox<String>(heldSuites.toArray(new String[heldSuites.size()]));
		colors.addActionListener(this);
		
		question = new JButton("?");
		question.addActionListener(this);
		
		selection.add(confirm,BorderLayout.WEST);
		selection.add(colors, BorderLayout.CENTER);
		selection.add(question, BorderLayout.EAST);
		
	}

	private void fillNames(List<Colored> props){
		for(int i=0; i<props.size(); i++){
			propNames[i].setText(props.get(i).getName());
		}
	}
	
	private void fillGrades(List<Colored> props){
		for(int i=0; i<props.size(); i++){
			propGrade[i].setText(""+props.get(i).getGrade());
		}
	}
	
	private void completeAddSub(List<Colored> props){
		//Reseting all buttons to a default state
		for(int i=0; i<add.length; i++){
			add[i].setEnabled(false);
			add[i].setToolTipText("No Property");
			sub[i].setEnabled(false);
			sub[i].setToolTipText("No Property");
		}
		
		//Populating buttons with a tooltip and situational enabled status
		for(int i=0; i<props.size(); i++){
			Colored current = props.get(i);
			
			int tooltipRent = current.getRentAt(7);
			
			add[i].setToolTipText(""+tooltipRent);
			
			if(current.getGrade() - currSuite.smallestGrade() < 1){
				add[i].setEnabled(true);
			}else{
				add[i].setEnabled(false);
			}
			
			sub[i].setToolTipText(""+((int)tooltipRent/2));
			
			if(currSuite.largestGrade()-current.getGrade() < 1){
				sub[i].setEnabled(true);
			}else{
				sub[i].setEnabled(false);
			}
			
		}
	}
	
	private void populateTextButtons(){
		JPanel current;
		for(int i=0; i<3; i++){
			current = textButtons[i];
			
			current.add(add[i]);
			current.add(propGrade[i]);
			current.add(sub[i]);
			current.setPreferredSize(new Dimension(225,100));
		}
	}
	
	private void populatePropDisplay(){
		for(int i=0; i<3; i++){
			propDisplay[i].add(propNames[i], BorderLayout.CENTER);
			propDisplay[i].add(textButtons[i], BorderLayout.EAST);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.models.Pair;

@SuppressWarnings("serial")
public class NewGameStartUp extends JDialog implements ActionListener, ChangeListener, KeyListener{
	
	private class IndexJButton extends JButton{
		
		private int index;
		
		public IndexJButton(ImageIcon icon, int i){
			super(icon);
			
			index = i;
		}
		
		public int getIndex(){
			return index;
		}
		
		public void setIndex(int i){
			index = i;
		}
	}
	
	private ImageIcon[] pieceSelection;
	
	private JPanel playerNums;
	private JLabel numPrompt;
	private JSpinner numSpin;
	
	
	private JPanel playerSpecs;
	private JLabel valid;
	private JPanel[] nameSpinnerPairs;
	private JLabel[] namePrompts;
	private JTextField[] names;
	private ArrayList<IndexJButton> playerIcons;
	
	private JPanel confirm;
	private JButton ok;
	private JButton cancel;
	
	private ArrayList<Pair<String,Integer>> fillMe;
	
	public NewGameStartUp(JFrame parent, ImageIcon[] selection, ArrayList<Pair<String,Integer>> fm){
		super(parent, "New Game", true);
		
		pieceSelection = selection;
		
		fillMe = fm;
		
		BorderLayout bl = new BorderLayout();
		bl.setVgap(5);
		
		setLayout(bl);
		
		developPlayerNums();
		developPlayerSpecs();
		developConfirm();
		
		//ArrayList<Map<String,Integer>> fillme;
		
	}
	
	public void beginDialog(){
		
		this.add(playerNums, BorderLayout.NORTH);
		this.add(playerSpecs, BorderLayout.CENTER);
		this.add(confirm, BorderLayout.SOUTH);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void developPlayerNums(){
		BorderLayout bl = new BorderLayout();
		bl.setVgap(5);
		playerNums = new JPanel(bl);
		
		numPrompt = new JLabel("Number of players");
		SpinnerModel model = new SpinnerNumberModel(2,2,8,1);
		numSpin = new JSpinner(model);
		numSpin.addChangeListener(this);
		
		numSpin.setPreferredSize(new Dimension(75,50));
		
		playerNums.add(numPrompt, BorderLayout.CENTER);
		playerNums.add(numSpin, BorderLayout.EAST);
		
		playerNums.setPreferredSize(new Dimension(200,30));
	}
	
	private void developPlayerSpecs(){
		playerSpecs = new JPanel(new GridLayout(9,1));
		Border title = BorderFactory.createTitledBorder("Define your players");
		playerSpecs.setBorder(title);
		
		valid = new JLabel("Invalid names!");
		valid.setVisible(false);
		
		nameSpinnerPairs = new JPanel[8];
		
		namePrompts = new JLabel[8];
		
		names = new JTextField[8];
		
		playerIcons = new ArrayList<IndexJButton>();
		
		for(int i=0; i<8; i++){
			nameSpinnerPairs[i] = new JPanel(new BorderLayout());
			
			namePrompts[i] = new JLabel("Player "+(i+1)+" name: ");
			names[i] = new JTextField();
			names[i].setColumns(10);
			names[i].addKeyListener(this);;
			//SpinnerListModel model = new SpinnerListModel(pieceSelection);
			
			playerIcons.add(new IndexJButton(pieceSelection[i],i));
			playerIcons.get(i).setPreferredSize(new Dimension(30,30));
			playerIcons.get(i).addActionListener(this);
			
			nameSpinnerPairs[i].add(namePrompts[i], BorderLayout.WEST);
			nameSpinnerPairs[i].add(names[i], BorderLayout.CENTER);
			nameSpinnerPairs[i].add(playerIcons.get(i), BorderLayout.EAST);
			
			nameSpinnerPairs[i].setPreferredSize(new Dimension(375,30));
			
			if(i > 1){
				names[i].setEnabled(false);
				playerIcons.get(i).setEnabled(false);
			}
			
			playerSpecs.add(nameSpinnerPairs[i]);
		}
		playerSpecs.add(valid);
		//playerSpecs.setPreferredSize(new Dimension(400,600));
		
	}
	
	private void developConfirm(){
		
		confirm = new JPanel();
		
		ok = new JButton("Ok");
		ok.setPreferredSize(new Dimension(75,25));
		ok.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(75,25));
		
		cancel.addActionListener(this);
		
		confirm.add(ok);
		confirm.add(cancel);
		
		confirm.setPreferredSize(new Dimension(200,50));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cancel)){
			Pair<String,Integer> blep = new Pair<String, Integer>("",0);
			fillMe.add(blep);
			this.dispose();
		}else if(playerIcons.contains(e.getSource())){
			
			IndexJButton selected = (IndexJButton)e.getSource();
			int index = (selected.getIndex()+1)%pieceSelection.length;
			selected.setIcon(pieceSelection[index]);
			selected.setIndex(index);
			
		}else if(e.getSource().equals(ok)){
			int num = (Integer)numSpin.getValue();
			for(int i=0; i<num; i++){
				
				String named = names[i].getText();
				if(named.equals("")){
					named = "Unnamed "+i;
				}
				int choice = playerIcons.get(i).getIndex();
				Pair<String,Integer> toAdd = new Pair<String,Integer>(named,choice);
				fillMe.add(toAdd);
			}
			this.dispose();
		}
	}
	
	private void enableAllNames(){
		ok.setEnabled(true);
		valid.setVisible(false);
	}
	
	private void disableInvalidNames(HashMap<String,JTextField> inputs, int enabled){
		valid.setVisible(true);
		for(int i=0; i<enabled; i++){
			if(!names[i].equals(inputs.get(names[i].getText()))){
				//namePrompts[i].setBackground(Color.RED);
				ok.setEnabled(false);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource().equals(numSpin)){
			//System.out.println("JSpinner registered a change");
			int num = (Integer)numSpin.getValue();
			for(int i=0; i<8; i++){
				if(num > 0){
					names[i].setEnabled(true);
					playerIcons.get(i).setEnabled(true);
					num--;
				}else{
					names[i].setEnabled(false);
					playerIcons.get(i).setEnabled(false);
					
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("textarea change");
		//System.out.println(e.getSource().toString());
		if(e.getSource() instanceof JTextField){
			
			HashMap<String, JTextField> inputs = new HashMap<String, JTextField>();
			//System.out.println("old size: "+inputs.size());
			int numEnabled = 0;
			for(JTextField n : names){
				if(n.isEnabled()){
					inputs.put(n.getText(), n);
					numEnabled++;
				}
			}
			//System.out.println("enabled: "+numEnabled+" size: "+inputs.size());
			if(inputs.size() != numEnabled){
				disableInvalidNames(inputs, numEnabled);
			}else if(!ok.isEnabled()){
				enableAllNames();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
}

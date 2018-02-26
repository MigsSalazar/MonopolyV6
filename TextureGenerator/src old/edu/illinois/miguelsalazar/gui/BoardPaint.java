package edu.illinois.miguelsalazar.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.gui.Stamp;
import main.java.models.Pair;



@SuppressWarnings("serial")
public class BoardPaint extends JPanel implements ActionListener, ChangeListener, ComponentListener, KeyListener {
	
	private int cycle = 0;
	
	private ArrayList<String> iconPaths;
	private ArrayList<File> assetList;
	
	private JTabbedPane parts;
	
	private JPanel board, images, stamps, paths;
	private JButton[][] arrButton;
	private boolean[][] forbidden;
	private int[][] basePaint;
	
	private JSpinner inputWidth, inputHeight;
	private DefaultListModel<String> model;
	private JList<String> list;
	
	private JRadioButton top, right, bottom, left, bold, italics, underline;
	private JButton lastButton, example, clear, surround, clean;
	private ArrayList<JRadioButton> directions, decorations;
	private JTextField givenRow, givenCol, engraving;
	private Pair<Integer,Integer> lastCoord;
	private Stamp lastStamp;
	
	
	private Stamp[][] stampCollection;
	
	
	public BoardPaint(ArrayList<String> ip, ArrayList<File> al){
		iconPaths = ip;
		assetList = al;
		basePaint = emptyBasePaint();
		stampCollection = emptyStampCollection();
		defineParts();
		this.add(board, BorderLayout.CENTER);
		this.add(parts, BorderLayout.EAST);
		this.setVisible(true);
		
	}
	
	private void defineParts(){
		parts = new JTabbedPane();
		
		defineImages();
		parts.addTab("Tiles", images);
		defineStamps();
		parts.addTab("Stamps", stamps);
		defineBoard();
	}
	
	private void defineImages(){
		images = new JPanel(new BorderLayout());
		
		JPanel boardDim = new JPanel(new GridLayout(2,2));
		
		JLabel width = new JLabel("Width");
		JLabel height = new JLabel("Height");
		
		SpinnerModel widthNum = new SpinnerNumberModel();
		widthNum.setValue(600);
		inputWidth = new JSpinner(widthNum);
		inputWidth.addChangeListener(this);
		
		SpinnerModel heightNum = new SpinnerNumberModel();
		heightNum.setValue(600);
		inputHeight = new JSpinner(heightNum);
		inputHeight.addChangeListener(this);
		
		boardDim.add(width);
		boardDim.add(inputWidth);
		boardDim.add(height);
		boardDim.add(inputHeight);
		
		ArrayList<String> names = new ArrayList<String>();
		
		for(File f : assetList){
			names.add(f.getName());
		}
		String[] nameArray = names.toArray(new String[names.size()]);
		
		model = new DefaultListModel<String>();
		model.copyInto(nameArray);
		
		list = new JList<String>(model);
		
		JScrollPane pane = new JScrollPane(list);
		pane.setPreferredSize(new Dimension(375,500));
		
		images.add(boardDim, BorderLayout.NORTH);
		images.add(pane, BorderLayout.CENTER);
		images.addComponentListener(this);
		
	}
	
	private void defineBoard(){
		board = new JPanel(new GridLayout(30,30));
		board.setMaximumSize(new Dimension(800,800));
		Dimension boardSize = new Dimension((Integer)inputWidth.getValue(), (Integer)inputHeight.getValue());
		board.setPreferredSize(boardSize);
		arrButton = new JButton[30][30];
		
		for(int r=0; r<arrButton.length; r++){
			for(int c=0; c<arrButton[r].length; c++){
				arrButton[r][c] = new JButton();
				arrButton[r][c].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
				arrButton[r][c].setBackground(Color.WHITE);
				arrButton[r][c].addActionListener(this);
				arrButton[r][c].setOpaque(true);
				arrButton[r][c].setLayout(null);
				arrButton[r][c].setIconTextGap((-1)*30);

				
				if(!iconPaths.isEmpty() && basePaint[r][c] < iconPaths.size()){
					arrButton[r][c].setIcon(new ImageIcon(iconPaths.get(basePaint[r][c]) ));
				}
				stampCollection[r][c].engraveButton(arrButton[r][c]);
				board.add(arrButton[r][c]);
			}
		}
		
	}
	
	private void defineStamps(){
		stamps = new JPanel(new BorderLayout());
		
		JPanel selected = directionalRadioButtons();
		
		stamps.add(selected, BorderLayout.NORTH);
		lastButton = null;
		lastCoord = null;
		
		JPanel options = new JPanel();
		
		clear = new JButton("Clear All");
		clear.addActionListener(this);
		clear.setVerticalAlignment(JButton.CENTER);
		
		surround = new JButton("Surround");
		surround.addActionListener(this);
		surround.setVerticalAlignment(JButton.CENTER);
		
		options.add(clear);
		options.add(surround);
		
		//stamps.add(options, BorderLayout.CENTER);
		
		JPanel extras = decorativeRadioButtons();
		

		JPanel fullBot = new JPanel(new BorderLayout());
		fullBot.add(options, BorderLayout.CENTER);
		fullBot.add(extras, BorderLayout.SOUTH);
		
		stamps.add(fullBot, BorderLayout.CENTER);
		
		JPanel empty = new JPanel();
		empty.setPreferredSize(new Dimension(375,160));
		
		stamps.add(empty,BorderLayout.SOUTH);
		
		stamps.addComponentListener(this);
	}

	private JPanel decorativeRadioButtons() {
		GridLayout gl = new GridLayout(5,2);
		gl.setVgap(10);
		JPanel extras = new JPanel(gl);
		
		decorations = new ArrayList<JRadioButton>();
		
		JLabel myRow = new JLabel("Current Row");
		myRow.setHorizontalAlignment(JLabel.CENTER);
		givenRow = new JTextField();
		prepTextField(givenRow);
		
		JLabel myCol = new JLabel("Current Column");
		myCol.setHorizontalAlignment(JLabel.CENTER);
		givenCol = new JTextField(" ");
		prepTextField(givenCol);
		
		JLabel myEngraving = new JLabel("Engraving");
		myEngraving.setHorizontalAlignment(JLabel.CENTER);
		engraving = new JTextField();
		engraving.setColumns(1);
		engraving.setEnabled(true);
		engraving.addKeyListener(this);
		engraving.setCaretPosition(0);
		
		bold = new JRadioButton("Bold");
		decorations.add(bold);
		
		italics = new JRadioButton("Italics");
		decorations.add(italics);
		
		underline = new JRadioButton("Underline");
		decorations.add(underline);
		
		for(JRadioButton b : decorations){
			b.addActionListener(this);
			b.setHorizontalAlignment(JRadioButton.CENTER);
		}
		
		clean = new JButton("Clean");
		clean.addActionListener(this);
		
		extras.add(myRow);
		extras.add(givenRow);
		extras.add(myCol);
		extras.add(givenCol);
		extras.add(myEngraving);
		extras.add(engraving);
		extras.add(bold);
		extras.add(italics);
		extras.add(underline);
		extras.add(clean);
		
		return extras;
	}
	
	private void prepTextField(JTextField label){
		label.setText(" ");
		label.setColumns(2);
		label.setEditable(false);
	}

	private JPanel directionalRadioButtons() {
		JPanel selected = new JPanel(new BorderLayout());
		directions = new ArrayList<JRadioButton>();
		//selected.setPreferredSize(new Dimension(300,300));
		
		top = new JRadioButton("Top");
		top.setVerticalAlignment(JRadioButton.BOTTOM);
		top.setHorizontalAlignment(JRadioButton.CENTER);
		top.setPreferredSize(new Dimension(300,25));
		directions.add(top);
		
		right = new JRadioButton("Right");
		right.setHorizontalAlignment(JRadioButton.LEFT);
		right.setPreferredSize(new Dimension(150,100));
		directions.add(right);
		
		bottom = new JRadioButton("Bottom");
		bottom.setVerticalAlignment(JRadioButton.TOP);
		bottom.setHorizontalAlignment(JRadioButton.CENTER);
		bottom.setPreferredSize(new Dimension(300,25));
		directions.add(bottom);
		
		left = new JRadioButton("Left");
		left.setHorizontalAlignment(JRadioButton.RIGHT);
		left.setPreferredSize(new Dimension(150,100));
		directions.add(left);
		
		for(JRadioButton b : directions) b.addActionListener(this);
		
		example = new JButton();
		example.setIconTextGap(-28);
		example.setEnabled(false);
		example.setBackground(Color.WHITE);
		example.setOpaque(true);
		example.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		example.setPreferredSize(new Dimension(100,100));
		
		selected.add(top, BorderLayout.NORTH);
		selected.add(right, BorderLayout.EAST);
		selected.add(bottom, BorderLayout.SOUTH);
		selected.add(left, BorderLayout.WEST);
		selected.add(example, BorderLayout.CENTER);
		return selected;
	}
	
	
	/**
	 * Always minimize! not for any programming reason but because it just lags my computer
	 * @return
	 */
	private int[][] emptyBasePaint(){
		int[][] num =	{{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //0
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //1
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //2
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //3
						{0,0,0,0,0,0,		23,23,0,0,23,23,23,23,0,0,28,28,28,28,0,0,28,28,	0,0,0,0,0,0}, //4
						{0,0,0,0,0,0,		23,23,0,0,23,23,23,23,0,0,28,28,28,28,0,0,28,28,	0,0,0,0,0,0}, //5
						
						{0,0,0,0,18,18,		0,0,0,0,0,1,1,1,0,0,1,1,1,0,0,0,0,0,					33,33,0,0,0,0}, //6
						{0,0,0,0,18,18,		0,0,0,0,0,2,1,2,0,0,2,1,2,0,0,0,0,0,					33,33,0,0,0,0}, //7
						{0,0,0,0,18,18,		0,0,0,0,0,1,1,1,0,0,1,1,1,0,0,0,0,0,					33,33,0,0,0,0}, //8
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //9
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //10
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //11
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //12
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //13
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //14
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //15
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //16
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //17
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //18
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //19
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //20
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //21
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //22
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //23
							
						{0,0,0,0,0,0,		8,8,8,8,0,0,8,8,0,0,0,0,3,3,0,0,3,3,				0,0,0,0,0,0}, //24
						{0,0,0,0,0,0,		8,8,8,8,0,0,8,8,0,0,0,0,3,3,0,0,3,3,				0,0,0,0,0,0}, //25
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //26
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //27
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //28
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}};//29
		
		forbidden = defineForbidden();
		return num;
	}
	
	/**
	 * Always minimize this too! same reason as emptyBasePaint
	 * @return
	 */
	private boolean[][] defineForbidden(){
		boolean[][] forb =	{{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //0
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //1
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //2
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //3
							{false,false,false,false,false,false,		true,true,false,false,true,true,true,true,false,false,true,true,true,true,false,false,true,true,				false,false,false,false,false,false}, //4
							{false,false,false,false,false,false,		true,true,false,false,true,true,true,true,false,false,true,true,true,true,false,false,true,true,				false,false,false,false,false,false}, //5
							
							{false,false,false,false,true,true,			false,false,false,false,false,true,true,true,false,false,true,true,true,false,false,false,false,false,			true,true,false,false,false,false}, //6
							{false,false,false,false,true,true,			false,false,false,false,false,true,true,true,false,false,true,true,true,false,false,false,false,false,			true,true,false,false,false,false}, //7
							{false,false,false,false,true,true,			false,false,false,false,false,true,true,true,false,false,true,true,true,false,false,false,false,false,			true,true,false,false,false,false}, //8
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //9
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //10
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //11
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //12
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //13
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //14
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //15
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //16
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //17
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //18
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //19
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //20
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //21
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //22
							{false,false,false,false,true,true,			false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	true,true,false,false,false,false}, //23
								
							{false,false,false,false,false,false,		true,true,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,				false,false,false,false,false,false}, //24
							{false,false,false,false,false,false,		true,true,true,true,false,false,true,true,false,false,false,false,true,true,false,false,true,true,				false,false,false,false,false,false}, //25
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //26
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //27
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}, //28
							{false,false,false,false,false,false,		false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,	false,false,false,false,false,false}};//29
		
		return forb;
	}
	
	private Stamp[][] emptyStampCollection(){
		Stamp[][] collection = new Stamp[30][30];
		
		for(int r=0; r<collection.length; r++){
			for(int c=0; c<collection[r].length; c++){
				collection[r][c] = new Stamp();
			}
		}
		
		return collection;
	}
	
	public void updateAssets(){
		//picks.getModel().
		model.clear();
		for(File f : assetList){
			model.addElement(f.getName());
		}
		//picks.
	}
	
	public void redecorate(){
		for(int r=0; r<arrButton.length; r++){
			for(int c=0; c<arrButton[r].length; c++){
				if(!iconPaths.isEmpty() && basePaint[r][c] < iconPaths.size()){
					arrButton[r][c].setIcon(new ImageIcon(iconPaths.get(basePaint[r][c]) ));
				}
			}
		}
	}
	
	public void setAssetList(ArrayList<File> al){
		assetList = al;
		updateAssets();
	}
	
	public void setIconPaths(ArrayList<String> ip){
		iconPaths = ip;
		//updateAssets();
	}
	
	public boolean isForbiddenButton(int row, int col){
		return forbidden[row][col];
	}

	private void paintButton(ActionEvent e) {
		lastButton = (JButton)e.getSource();
		
		lastCoord = findButtonLocation(lastButton);
		lastStamp = stampCollection[lastCoord.first][lastCoord.second];
		if(lastCoord == null){
			return;
		}
		if(!isForbiddenButton(lastCoord.first,lastCoord.second)){
			int index = list.getSelectedIndex();
			if(index != -1){
				String pathme = assetList.get(index).getPath();
				lastButton.setIcon(new ImageIcon(pathme));
				if(!iconPaths.contains(pathme)){
					iconPaths.add(pathme);
				}
				
				basePaint[lastCoord.first][lastCoord.second] = iconPaths.indexOf(pathme);
			}
			
		}
	}
	
	public void redrawButton(ActionEvent e){
		//System.out.println("redraw border called");
		if(e.getSource().equals(clear)){
			top.setSelected(false);
			bottom.setSelected(false);
			right.setSelected(false);
			left.setSelected(false);
			applyStamp();
		}else if(e.getSource().equals(surround)){
			top.setSelected(true);
			bottom.setSelected(true);
			right.setSelected(true);
			left.setSelected(true);
			applyStamp();
		}else if(e.getSource().equals(clean)){
			bold.setSelected(false);
			italics.setSelected(false);
			underline.setSelected(false);
			engraving.setText(" ");
			applyStamp();
		}else if(e.getSource() instanceof JButton){
			//System.out.println("source is a JButton");
			if(lastButton != null){
				lastButton.setEnabled(true);
			}
			lastButton = (JButton)e.getSource();
			lastButton.setEnabled(false);
			collectTile();
			engraving.requestFocusInWindow();
		}else if(e.getSource() instanceof JRadioButton){
			applyStamp();
		}
	}

	private void collectTile() {
		lastCoord = findButtonLocation(lastButton);
		lastStamp = stampCollection[lastCoord.first][lastCoord.second];
		
		givenRow.setText(""+lastCoord.first);
		givenCol.setText(""+lastCoord.second);
		engraving.setText(""+lastStamp.getEngraving());
		
		italics.setSelected(lastStamp.isItalics());
		bold.setSelected(lastStamp.isBold());
		underline.setSelected(lastStamp.isUnderline());
		
		int border = lastStamp.getBorder();
		top.setSelected(false);
		right.setSelected(false);
		bottom.setSelected(false);
		left.setSelected(false);
		
		if(border < 211 && border > 0){
			if(border%2 == 0) top.setSelected(true); 
			if(border%3 == 0) right.setSelected(true);
			if(border%5 == 0) bottom.setSelected(true);
			if(border%7 == 0) left.setSelected(true);
		}
		
		example.setBorder(lastStamp.makeBorder());
		example.setIcon(new ImageIcon(iconPaths.get(basePaint[lastCoord.first][lastCoord.second] ) ));
		example.setDisabledIcon(new ImageIcon(iconPaths.get(basePaint[lastCoord.first][lastCoord.second] ) ) );
		
		int fancyFont = (bold.isSelected()? Font.BOLD : 0) + (italics.isSelected()? Font.ITALIC : 0);
		Font font = new Font(lastButton.getFont().getName(),fancyFont, lastButton.getFont().getSize());
		example.setFont(font);
		example.setText((underline.isSelected()?"<html><u>":"")+lastStamp.getEngraving()+(underline.isSelected()?"</u></html>":""));
		
	}

	private void applyStamp() {
		//System.out.println("cardinal direction called");
		if(lastButton == null){ return; }
		
		if(lastCoord == null){ return; }
		
		if(lastStamp == null){ return; }
		
		int b = 1;
		if(top.isSelected()) b = b*2;
		
		if(right.isSelected()) b = b*3;
		
		if(bottom.isSelected()) b = b*5;
		
		if(left.isSelected()) b = b*7;
		
		//System.out.println("Got what I needed. Doing stuff. b="+b);
		
		lastStamp.setBold(bold.isSelected());
		lastStamp.setItalics(italics.isSelected());
		lastStamp.setUnderline(underline.isSelected());
		if(engraving.getText().length() > 0){
			lastStamp.setEngraving(engraving.getText().charAt(0));
		}else{
			lastStamp.setEngraving(' ');
		}
		
		lastStamp.setBorder(b);
		
		int fancyFont = (bold.isSelected()? Font.BOLD : 0) + (italics.isSelected()? Font.ITALIC : 0);
		
		//lastStamp.engraveButton(lastButton);
		//Font newButtonFont=new Font(button.getFont().getName(),Font.ITALIC+Font.BOLD,button.getFont().getSize());
		Font font = new Font(lastButton.getFont().getName(),fancyFont, lastButton.getFont().getSize());
		
		lastButton.setFont(font);
		example.setFont(font);
		
		lastButton.setText((underline.isSelected()?"<html><u>":"")+lastStamp.getEngraving()+(underline.isSelected()?"</u></html>":""));
		
		lastButton.setBorder(lastStamp.makeBorder());
		
		example.setText((underline.isSelected()?"<html><u>":"")+lastStamp.getEngraving()+(underline.isSelected()?"</u></html>":""));
		
		example.setBorder(lastStamp.makeBorder());
	}

	private Pair<Integer, Integer> findButtonLocation(JButton button) {
		Pair<Integer,Integer> coord = null;
		for(int r=0; r<arrButton.length; r++){
			for(int c=0; c<arrButton[r].length; c++){
				if(arrButton[r][c].equals(button)){
					coord = new Pair<Integer,Integer>(r,c);
					break;
				}
			}
			if(coord != null){
				break;
			}
		}
		return coord;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(cycle){
		case 0: if(lastButton != null){lastButton.setEnabled(true);}
			paintButton(e);
			break;
		case 1: if(lastButton != null){lastButton.setEnabled(false);}
			redrawButton(e);
			break;
		case 2:
			break;
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		//System.out.println("Change registered");
		if(e.getSource().equals(inputWidth) || e.getSource().equals(inputHeight)){
			int width = (Integer)inputWidth.getValue();
			int height = (Integer)inputHeight.getValue();
			if(width > 640){
				width = 640;
			}
			if(height > 640){
				height = 640;
			}
			Dimension boardSize = new Dimension(width,height);
			board.setPreferredSize(boardSize);
			
			//board.repaint();
			//this.repaint();
			this.getParent().repaint();
		}
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		if(e.getSource().equals(images)){
			cycle = 0;
		}else if(e.getSource().equals(stamps)){
			cycle = 1;
		}else if(e.getSource().equals(paths)){
			cycle = 2;
		}
		//System.out.println("Cycle is now: "+cycle);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(engraving.getText().length() > 1){
			engraving.setText(engraving.getText().substring(0, 1));
		}
		if(lastStamp != null && lastButton != null){
			lastStamp.setEngraving(e.getKeyChar());
		}
		engraving.setCaretPosition(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(engraving.getText().length() > 1){
			engraving.setText(engraving.getText().substring(0, 1));
		}
		engraving.setCaretPosition(0);
		if(lastStamp != null && lastButton != null){
			lastStamp.setEngraving(e.getKeyChar());
			applyStamp();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(engraving.getText().length() > 1){
			engraving.setText(engraving.getText().substring(0, 1));
		}
		if(lastStamp != null && lastButton != null){
			lastStamp.setEngraving(e.getKeyChar());
		}
		engraving.setCaretPosition(0);
	}
	
}

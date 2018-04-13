package edu.illinois.masalzr2.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Board {
	private JPanel board;
	private GridLayout grid;
	private Dimension dim;
	
	private int gridWidth;
	private int gridHeight;
	private int pixWidth;
	private int pixHeight;
	
	private int[][] iconNumbers;
	private ImageIcon[] icons;
	private JButton[][] display;
	
	private Stamp[][] stampCollection;
	
	private boolean showDice;
	
	private JButton[][] dice1 = new JButton[3][3];
	private JButton[][] dice2 = new JButton[3][3];
	
	private int d1=1;
	private int d2=1;
	
	private ImageIcon dotDie;
	private ImageIcon blankDie;
	
	public Board(){
		
		gridWidth = 30;
		gridHeight = 30;
		
		pixWidth = gridWidth*20;
		pixHeight = gridHeight*20;
		dim = new Dimension(pixWidth, pixHeight);
		
		grid = new GridLayout(gridWidth, gridHeight);
		
		board = new JPanel(grid);
		board.setPreferredSize(dim);
		
		iconNumbers = new int[gridWidth][gridHeight];
		
		for(int[] i : iconNumbers){
			for(int j=0; j<i.length; j++){
				i[j] = 0;
			}
		}
		
		icons = new ImageIcon[1];
		icons[0] = new ImageIcon();
		
		display = new JButton[gridWidth][gridHeight];
		stampCollection = new Stamp[gridWidth][gridHeight];
		
		for(int x=0; x<gridWidth; x++){
			for(int y=0; y<gridHeight; y++){
				display[x][y] = new JButton();
				display[x][y].setBorderPainted(false);
				display[x][y].setPreferredSize(new Dimension(20,20));
				stampCollection[x][y] = new Stamp();
			}
		}
		
		paintDisplay();
		
		for(JButton[] jba : display){
			for(JButton jb : jba){
				board.add(jb);
			}
		}
		
		
	}
	
	public JPanel getBoard(){
		return board;
	}

	public void paintDisplay() {
		for(int b=0; b<display.length; b++){
			for(int i=0; i<display[b].length; i++){
				display[b][i].setIcon(icons[iconNumbers[b][i]]);;
				stampCollection[b][i].engraveButton(display[b][i]);
			}
		}
		
		if(showDice){
			paintDice(d1,d2);
		}
		
	}
	
	public void setIconNumbers(int[][] nums){
		iconNumbers = nums;
	}
	
	public void setIcons(ImageIcon[] i){
		icons = i;
		
		this.setDiceIcons(icons[1], icons[2]);
		
	}
	
	
	public void addPiece(ImageIcon icon, int x, int y){
		display[x][y].setIcon(icon);
	}
	
	public void movePiece(ImageIcon icon, int oldx, int oldy, int newx, int newy){
		display[oldx][oldy].setIcon(icons[  iconNumbers[oldx][oldy]  ] );
		display[newx][newy].setIcon(icon);
	}
	
	public void removePiece(int x, int y){
		display[x][y].setIcon(  icons[  iconNumbers[x][y]  ]  );
	}
	
	public void activateDice(){
		
		if(!showDice){
			for(int j=0, y=Math.floorDiv(gridHeight,2)-3; j<3; j++, y++){
				for(int i=0, x=Math.floorDiv(gridWidth, 2); i<3; i++, x++){
					dice1[i][j] = display[x][y];
					dice2[i][j] = display[x][y+3];
				}
			}
			showDice = true;
		}
		
		
		paintDice(d1,d2);
		
	}
	
	public void deactivateDice(){
		if(showDice){
			for(int i=0; i<3; i++){
				for(int j=0; j<3; j++){
					dice1[i][j] = null;
					dice2[i][j] = null;
				}
			}
			showDice = false;
		}
		paintDisplay();
	}
	
	public void setDiceLocations(int x1, int y1, int x2, int y2){
		
		for(int i=0; i<3; i++, x1++){
			for(int j=0; j<3; j++, y1++){
				dice1[i][j] = display[x1][y1];
			}
			y1 -= 3;
		}
		
		for(int i=0; i<3; i++, x2++){
			for(int j=0; j<3; j++, y2++){
				dice2[i][j] = display[x2][y2];
			}
			y2 -= 3;
		}
		
		paintDisplay();
		
		
		
	}
	
	public void setDiceIcons(ImageIcon dDie, ImageIcon bDie){
		dotDie = dDie;
		blankDie = bDie;
	}
	
	public void paintDice(int d1, int d2){
		
		if(!showDice){
			return;
		}
		
		if(dotDie == null){
			dotDie = new ImageIcon();
		}
		
		if(blankDie == null){
			blankDie = new ImageIcon();
		}
		
		this.d1 = d1;
		this.d2 = d2;
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				dice1[i][j].setIcon(blankDie);
				dice2[i][j].setIcon(blankDie);
			}
		}
		
		switch(d1){
		case 5:	dice1[0][0].setIcon(dotDie);
				dice1[2][2].setIcon(dotDie);
		case 3: dice1[2][0].setIcon(dotDie);
				dice1[0][2].setIcon(dotDie);
		case 1: dice1[1][1].setIcon(dotDie);
			break;
		case 6: dice1[0][1].setIcon(dotDie);
				dice1[2][1].setIcon(dotDie);
		case 4: dice1[0][0].setIcon(dotDie);
				dice1[2][2].setIcon(dotDie);
		case 2: dice1[0][2].setIcon(dotDie);
				dice1[2][0].setIcon(dotDie);
			break;
		}
		
		switch(d2){
		case 5:	dice2[0][0].setIcon(dotDie);
				dice2[2][2].setIcon(dotDie);
		case 3: dice2[2][0].setIcon(dotDie);
				dice2[0][2].setIcon(dotDie);
		case 1: dice2[1][1].setIcon(dotDie);
			break;
		case 6: dice2[0][1].setIcon(dotDie);
				dice2[2][1].setIcon(dotDie);
		case 4: dice2[0][0].setIcon(dotDie);
				dice2[2][2].setIcon(dotDie);
		case 2: dice2[0][2].setIcon(dotDie);
				dice2[2][0].setIcon(dotDie);
			break;
		}
		
		//dice1[0][0].setIcon(dotDie);
	}
	
}

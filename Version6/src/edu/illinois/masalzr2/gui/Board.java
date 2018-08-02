package edu.illinois.masalzr2.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.illinois.masalzr2.masters.LogMate;


public class Board {
	private JPanel board;
	private GridLayout grid;
	private Dimension dim;
	
	private HashMap<String, Dimension> pieceCoords;
	
	private int gridWidth;
	private int gridHeight;
	private int pixWidth;
	private int pixHeight;
	
	private int[][] iconNumbers;
	private ImageIcon[] icons;
	private GraphicsButton[][] display;
	
	private int[][] stickerBook;
	private ImageIcon[] stickers;
	
	private Stamp[][] stampCollection;
	
	private boolean showDice;
	
	private GraphicsButton[][] dice1 = new GraphicsButton[3][3];
	private GraphicsButton[][] dice2 = new GraphicsButton[3][3];
	
	private int d1=1;
	private int d2=1;
	
	private ImageIcon dotDie;
	private ImageIcon blankDie;
	
	public Board(){
		LogMate.LOG.newEntry("Board: Beginning: Creating new Baord");
		pieceCoords = new HashMap<String, Dimension>();

		gridWidth = 30;
		gridHeight = 30;
		
		pixWidth = gridWidth*20;
		pixHeight = gridHeight*20;
		dim = new Dimension(pixWidth, pixHeight);
		
		grid = new GridLayout(gridWidth, gridHeight);
		LogMate.LOG.newEntry("Board: Beginning: gridWidth="+gridWidth+" gridHeight="+gridHeight+" pixWidth="+pixWidth+" pixHeight="+pixHeight);
		board = new JPanel(grid);
		board.setMaximumSize(dim);
		LogMate.LOG.newEntry("Board: Beginning: Setting iconNumbers and stickerBook dimensions");
		iconNumbers = new int[gridWidth][gridHeight];
		
		stickerBook = new int[gridWidth][gridHeight];
		
		LogMate.LOG.newEntry("Board: Beginning: Populating iconNumbers");
		for(int[] i : iconNumbers){
			for(int j=0; j<i.length; j++){
				i[j] = 0;
			}
		}
		
		icons = new ImageIcon[1];
		icons[0] = new ImageIcon();
		
		stickers = new ImageIcon[1];
		stickers[0] = new ImageIcon();
		
		display = new GraphicsButton[gridWidth][gridHeight];
		stampCollection = new Stamp[gridWidth][gridHeight];
		LogMate.LOG.newEntry("Board: Beginning: Loading Graphics Buttons with empty placeholder icons");
		for(int x=0; x<gridWidth; x++){
			for(int y=0; y<gridHeight; y++){
				display[x][y] = new GraphicsButton();
				
				//display[x][y].setBorderPainted(false);
				display[x][y].setPreferredSize(new Dimension(20,20));
				stampCollection[x][y] = new Stamp();
			}
		}
		
		//paintDisplay();
		LogMate.LOG.newEntry("Board: Beginning: Adding JButtons to display");
		for(JButton[] jba : display){
			for(JButton jb : jba){
				board.add(jb);
			}
		}
		
		board.setPreferredSize(new Dimension(600,600));
	}
	
	public JPanel getBoard(){
		LogMate.LOG.newEntry("Board: Get Board ");
		return board;
	}

	public void paintDisplay() {
		LogMate.LOG.newEntry("Board: Paint Display: Beginning.\nTO PREVENT EXESSIVE LOG ENTRIES, FOR LOOPS ARE SKIPPED");
		for(int b=0; b<display.length; b++){
			for(int i=0; i<display[b].length; i++){
				
				display[b][i].setIcon(icons[ iconNumbers[b][i] ]);
				
				if(stickerBook[b][i] > -1 ){
					//System.out.println("found a sticker! b="+b+"    i="+i);
					display[b][i].addIcon(stickers[ stickerBook[b][i] ]);
				}else{
					//System.out.println(":( no sticker b="+b+"    i="+i);
					display[b][i].wipeIcons();
				}
				
				if(stampCollection != null){
					if(stampCollection[b] != null){
						if(stampCollection[b][i] != null){
							display[b][i].setIconTextGap(-30);
							display[b][i].setOpaque(true);
							display[b][i].setLayout(null);
							
							stampCollection[b][i].engraveButton(display[b][i]);
							
							stampCollection[b][i].giveBorder(display[b][i]);
							
						}
					}
				}
				
			}
		}
		
		if(showDice){
			paintDice(d1,d2);
		}
		
	}
	
	public void setStamps(Stamp[][] collection){
		LogMate.LOG.newEntry("Board: Set Stamps");
		stampCollection = collection;
	}
	
	public void setIconNumbers(int[][] nums){
		LogMate.LOG.newEntry("Board: Set Icon Numbers");
		iconNumbers = nums;
	}
	
	public void setIcons(ImageIcon[] i){
		LogMate.LOG.newEntry("Board: Set Image Icons");
		icons = i;
		
		this.setDiceIcons(icons[1], icons[2]);
		
	}
	
	public void setStickerBook(int[][] sb){
		LogMate.LOG.newEntry("Board: Set Sticker Book");
		stickerBook = sb;
	}
	
	public void setStickers(ImageIcon[] s){
		LogMate.LOG.newEntry("Board: Set Sticker Icons");
		stickers = s;
	}
	
	
	public void addPiece(ImageIcon icon, int x, int y){
		LogMate.LOG.newEntry("Board: Add Piece: Adding icon to display");
		display[x][y].addIcon(icon);
		LogMate.LOG.newEntry("Board: Add Piece: Adding icon to registry");
		pieceCoords.put(icon.toString(), new Dimension(x,y));
	}
	
	public void addPiece(ImageIcon icon, String key, int x, int y){
		LogMate.LOG.newEntry("Board: Add Piece: adding icon to display");
		display[x][y].addIcon(icon);
		LogMate.LOG.newEntry("Board: Add Piece: Adding icon to registry with key: "+key);
		pieceCoords.put(key, new Dimension(x,y));
	}
	
	public void movePiece(ImageIcon icon, int x, int y){
		LogMate.LOG.newEntry("Board: Move Piece Sans Key: Requesting movement based on image name");
		movePiece(icon, icon.toString(), x, y);
	}
	
	public void movePiece(ImageIcon icon, String key, int x, int y){
		LogMate.LOG.newEntry("Board: Move Piece: Moving piece of key: "+key+" to coords x="+x+" y="+y);
		if(pieceCoords.containsKey(key)){
			LogMate.LOG.newEntry("Board: Move Piece: Key was found, retrieving");
			Dimension dim = pieceCoords.get(key);
			//LogMate.LOG.newEntry("Board: Move Piece: Wiping old icons");
			display[dim.width][dim.height].wipeIcons();
			//LogMate.LOG.newEntry("Board: Move Piece: Setting coordinates");
			dim.setSize(x, y);
			//LogMate.LOG.newEntry("Board: Move Piece: Adding Icon");
			display[dim.width][dim.height].addIcon(icon);
		}
	}
	
	public void removePiece(ImageIcon icon){
		LogMate.LOG.newEntry("Board: Remove Piece Sans Key: Removing based on Image Icon");
		removePiece(icon.toString());
	}
	
	public void removePiece(String key){
		LogMate.LOG.newEntry("Board: Remove Piece: Removing piece of key: "+key);
		if(pieceCoords.containsKey(key)){
			LogMate.LOG.newEntry("Board: Remove Piece: Key was found. TrueDelete");
			trueDelete(key);
		}else {
			LogMate.LOG.newEntry("Board: Remove Piece: No key was found. Removing all pieces with subnames of key");
			Set<String> keys = pieceCoords.keySet();
			String[] strKeys = new String[keys.size()];
			keys.toArray(strKeys);
			//LogMate.LOG.newEntry("Board: Remove Piece: True Deleting pieces");
			for(String s : strKeys) {
				if(s.contains(key)) {
					trueDelete(s);
				}
			}
		}
	}

	private void trueDelete(String key) {
		LogMate.LOG.newEntry("Board: True Delete: Finding coordinates");
		Dimension dim = pieceCoords.get(key);
		LogMate.LOG.newEntry("Board: True Delete: removing piece and wiping icons");
		pieceCoords.remove(key);
		display[dim.width][dim.height].wipeIcons();
	}
	
	
	
	public void activateDice(){
		LogMate.LOG.newEntry("Board: Activate Dice: Beginning");
		if(!showDice){
			LogMate.LOG.newEntry("Board: Activate Dice: Dice was not active. Activating and setting dice");
			for(int j=0, y=Math.floorDiv(gridHeight,2)-3; j<3; j++, y++){
				for(int i=0, x=Math.floorDiv(gridWidth, 2); i<3; i++, x++){
					dice1[i][j] = display[x][y];
					dice2[i][j] = display[x][y+3];
				}
			}
			showDice = true;
		}
		
		LogMate.LOG.newEntry("Board: Activate Dice: Painting dice");
		paintDice(d1,d2);
		
	}
	
	public void deactivateDice(){
		LogMate.LOG.newEntry("Board: Deactivate Dice: Deactivating if active");
		if(showDice){
			LogMate.LOG.newEntry("Board: Deactivate Dice: Dice found active");
			for(int i=0; i<3; i++){
				for(int j=0; j<3; j++){
					dice1[i][j] = null;
					dice2[i][j] = null;
				}
			}
			showDice = false;
		}
		LogMate.LOG.newEntry("Board: Deactivate Dice: Painting display sans dice");
		paintDisplay();
	}
	
	public void setDiceLocations(int x1, int y1, int x2, int y2){
		LogMate.LOG.newEntry("Board: Set Dice Locations: Selecting 3x3 display for 2 dice for each upper-left corner coordinate given");
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
		LogMate.LOG.newEntry("Board: Set Dice Locations: Painting display");
		paintDisplay();
		
	}
	
	public void setDiceIcons(ImageIcon dDie, ImageIcon bDie){
		LogMate.LOG.newEntry("Board: Set Dice Icons: Setting Icons for Dice");
		dotDie = dDie;
		blankDie = bDie;
	}
	
	public void paintDice(int dOne, int dTwo){
		LogMate.LOG.newEntry("Board: Paint Dice: Updating dice");
		d1 = dOne;
		d2 = dTwo;
		if(!showDice){
			LogMate.LOG.newEntry("Board: Paint Dice: Dice are inactive. Ending");
			return;
		}
		
		if(dotDie == null){
			LogMate.LOG.newEntry("Board: Paint Dice: DotDie is null. Setting placeholder");
			dotDie = new ImageIcon();
		}
		
		if(blankDie == null){
			LogMate.LOG.newEntry("Board: Paint Dice: BlankDie is null. Setting placeholder");
			blankDie = new ImageIcon();
		}
		LogMate.LOG.newEntry("Board: Paint Dice: Setting all empty");
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				dice1[i][j].setIcon(blankDie);
				dice2[i][j].setIcon(blankDie);
			}
		}
		LogMate.LOG.newEntry("Board: Paint Dice: Painting first die");
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
		LogMate.LOG.newEntry("Board: Paint Dice: Painting second die");
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

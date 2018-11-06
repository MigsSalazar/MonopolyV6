package edu.illinois.masalzr2.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.gson.annotations.Expose;

import lombok.extern.flogger.Flogger;

@Flogger
public class Board {
	private JPanel board;
	private GridLayout grid;
	@Expose
	private Dimension dim;
	
	@Expose
	private HashMap<String, Dimension> pieceCoords;
	
	@Expose
	private int pixSquare;
	@Expose
	private int gridWidth;
	@Expose
	private int gridHeight;
	@Expose
	private int pixWidth;
	@Expose
	private int pixHeight;
	@Expose
	private int[][] iconNumbers;
	
	private ImageIcon[] icons;
	
	private GraphicsButton[][] display;
	private StickerBook stickerBook;
	@Expose
	private Stamp[][] stampCollection;
	@Expose
	private boolean showDice;
	
	private GraphicsButton[][] dice1 = new GraphicsButton[3][3];
	
	private GraphicsButton[][] dice2 = new GraphicsButton[3][3];
	@Expose
	private int d1=1;
	@Expose
	private int d2=1;
	
	private ImageIcon dotDie;
	
	private ImageIcon blankDie;
	
	public Board(int w, int h, int p){
		buildBoard(w,h,p);
	}
	
	public Board(int w, int h){
		buildBoard(w,h, 20);
	}
	
	public Board(){
		buildBoard(30,30, 20);
	}

	private void buildBoard(int w, int h, int pix) {
		log.atInfo().log("Board: Beginning: Creating new Baord");
		pieceCoords = new HashMap<String, Dimension>();

		gridWidth = w;
		gridHeight = h;
		
		pixSquare = pix;
		
		pixWidth = gridWidth*pixSquare;
		pixHeight = gridHeight*pixSquare;
		dim = new Dimension(pixWidth, pixHeight);
		
		grid = new GridLayout(gridWidth, gridHeight);
		log.atConfig().log("Board: Beginning: gridWidth="+gridWidth+" gridHeight="+gridHeight+" pixWidth="+pixWidth+" pixHeight="+pixHeight);
		board = new JPanel(grid);
		board.setMaximumSize(dim);
		log.atInfo().log("Board: Beginning: Setting iconNumbers and stickerBook dimensions");
		iconNumbers = new int[gridWidth][gridHeight];
		
		log.atInfo().log("Board: Beginning: Populating iconNumbers");
		for(int[] i : iconNumbers){
			for(int j=0; j<i.length; j++){
				i[j] = 0;
			}
		}
		
		icons = new ImageIcon[1];
		icons[0] = new ImageIcon();
		
		display = new GraphicsButton[gridWidth][gridHeight];
		stampCollection = new Stamp[gridWidth][gridHeight];
		log.atInfo().log("Board: Beginning: Loading Graphics Buttons with empty placeholder icons");
		for(int x=0; x<gridWidth; x++){
			for(int y=0; y<gridHeight; y++){
				display[x][y] = new GraphicsButton();
				
				//display[x][y].setBorderPainted(false);
				display[x][y].setPreferredSize(new Dimension(pixSquare,pixSquare));
				stampCollection[x][y] = new Stamp();
				log.atFinest().log("Stamp at x:"+x+" y:"+y, stampCollection[x][y]);
			}
		}
		
		//paintDisplay();
		log.atInfo().log("Board: Beginning: Adding JButtons to display");
		for(JButton[] jba : display){
			for(JButton jb : jba){
				board.add(jb);
			}
		}
		
		board.setPreferredSize(new Dimension(pixSquare*gridWidth,pixSquare*gridHeight));
	}
	
	public JPanel getBoard(){
		log.atConfig().log("Board: Get Board ");
		return board;
	}

	public void paintDisplay() {
		log.atInfo().log("Board: Paint Display: Beginning");
		
		for(int b=0; b<display.length; b++){
			for(int i=0; i<display[b].length; i++){
				
				display[b][i].setIcon(icons[ iconNumbers[b][i] ]);
				log.atFinest().log("page depth at b:"+b+" i:"+i+" is "+stickerBook.pageDepthAt(b, i));
				if(stickerBook.pageDepthAt(b, i) > 0 ){
					log.atFinest().log("found a sticker! b="+b+"    i="+i);
					for( ImageIcon icon : stickerBook.stackStickersAt(b, i) ){
						display[b][i].addIcon(icon);
					}
					
				}else{
					log.atFinest().log(":( no sticker b="+b+"    i="+i);
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
		log.atConfig().log("Board: Set Stamps");
		stampCollection = collection;
	}
	
	public void setIconNumbers(int[][] nums){
		log.atConfig().log("Board: Set Icon Numbers");
		iconNumbers = nums;
	}
	
	public void setIcons(ImageIcon[] i){
		log.atConfig().log("Board: Set Image Icons");
		icons = i;
		
		this.setDiceIcons(icons[1], icons[2]);
		
	}
	
	public void setStickerBook(StickerBook sb){
		log.atConfig().log("Board: Set Sticker Book");
		stickerBook = sb;
	}
	
	public void addPiece(ImageIcon icon, int x, int y){
		log.atConfig().log("Board: Add Piece: Adding icon to display");
		display[x][y].addIcon(icon);
		log.atConfig().log("Board: Add Piece: Adding icon to registry");
		pieceCoords.put(icon.toString(), new Dimension(x,y));
	}
	
	public void addPiece(ImageIcon icon, String key, int x, int y){
		log.atConfig().log("Board: Add Piece: adding icon to display");
		display[x][y].addIcon(icon);
		log.atConfig().log("Board: Add Piece: Adding icon to registry with key: "+key);
		pieceCoords.put(key, new Dimension(x,y));
	}
	
	public void movePiece(ImageIcon icon, int x, int y){
		log.atFinest().log("Board: Move Piece Sans Key: Requesting movement based on image name");
		movePiece(icon, icon.toString(), x, y);
	}
	
	public void movePiece(ImageIcon icon, String key, int x, int y){
		log.atFinest().log("Board: Move Piece: Moving piece of key: "+key+" to coords x="+x+" y="+y);
		if(pieceCoords.containsKey(key)){
			log.atFinest().log("Board: Move Piece: Key was found, retrieving");
			Dimension dim = pieceCoords.get(key);
			log.atFinest().log("Board: Move Piece: Wiping old icons");
			display[dim.width][dim.height].removeIcon(icon);
			log.atFinest().log("Board: Move Piece: Setting coordinates");
			dim.setSize(x, y);
			log.atFinest().log("Board: Move Piece: Adding Icon");
			display[dim.width][dim.height].addIcon(icon);
		}
	}
	
	public void removePiece(ImageIcon key){
		log.atInfo().log("Board: Remove Piece: Removing piece of key: "+key);
		if(pieceCoords.containsKey(key.getDescription())){
			log.atInfo().log("Board: Remove Piece: Key was found. TrueDelete");
			trueDelete(key);
		}else {
			log.atInfo().log("Board: Remove Piece: No key was found. Removing all pieces with subnames of key");
			Set<String> keys = pieceCoords.keySet();
			String[] strKeys = new String[keys.size()];
			keys.toArray(strKeys);
			log.atInfo().log("Board: Remove Piece: True Deleting pieces");
			for(String s : pieceCoords.keySet()) {
				if(s.contains(key.getDescription())) {
					trueDelete(new ImageIcon(s));
				}
			}
		}
	}

	private void trueDelete(ImageIcon key) {
		log.atInfo().log("Board: True Delete: Finding coordinates");
		Dimension dim = pieceCoords.get(key.getDescription());
		log.atInfo().log("Board: True Delete: removing piece and wiping icons");
		pieceCoords.remove(key.getDescription());
		display[dim.width][dim.height].removeIcon(key);
	}
	
	
	
	public void activateDice(){
		log.atInfo().log("Board: Activate Dice: Beginning");
		if(!showDice){
			log.atConfig().log("Board: Activate Dice: Dice was not active. Activating and setting dice");
			for(int j=0, y=Math.floorDiv(gridHeight,2)-3; j<3; j++, y++){
				for(int i=0, x=Math.floorDiv(gridWidth, 2); i<3; i++, x++){
					log.atFinest().log("Activate Dice: inner for-loop: dice tile at ("+i+","+j+")");
					dice1[i][j] = display[x][y];
					dice2[i][j] = display[x][y+3];
				}
			}
			showDice = true;
		}
		
		log.atInfo().log("Board: Activate Dice: Painting dice");
		paintDice(d1,d2);
		
	}
	
	public void deactivateDice(){
		log.atInfo().log("Board: Deactivate Dice: Deactivating if active");
		if(showDice){
			log.atConfig().log("Board: Deactivate Dice: Dice found active");
			for(int i=0; i<3; i++){
				for(int j=0; j<3; j++){
					dice1[i][j] = null;
					dice2[i][j] = null;
				}
			}
			showDice = false;
		}
		log.atInfo().log("Board: Deactivate Dice: Painting display sans dice");
		paintDisplay();
	}
	
	public void setDiceLocations(int x1, int y1, int x2, int y2){
		log.atInfo().log("Board: Set Dice Locations: Selecting 3x3 display for 2 dice for each upper-left corner coordinate given");
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
		log.atInfo().log("Board: Set Dice Locations: Painting display");
		paintDisplay();
		
	}
	
	public void setDiceIcons(ImageIcon dDie, ImageIcon bDie){
		log.atConfig().log("Board: Set Dice Icons: Setting Icons for Dice");
		dotDie = dDie;
		blankDie = bDie;
	}
	
	public void paintDice(int dOne, int dTwo){
		log.atConfig().log("Board: Paint Dice: Updating dice: d1="+dOne+" d2="+dTwo);
		d1 = dOne;
		d2 = dTwo;
		if(!showDice){
			log.atInfo().log("Board: Paint Dice: Dice are inactive. Ending");
			return;
		}
		
		if(dotDie == null){
			log.atInfo().log("Board: Paint Dice: DotDie is null. Setting placeholder");
			dotDie = new ImageIcon();
		}
		
		if(blankDie == null){
			log.atInfo().log("Board: Paint Dice: BlankDie is null. Setting placeholder");
			blankDie = new ImageIcon();
		}
		log.atInfo().log("Board: Paint Dice: Setting all empty");
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				dice1[i][j].setIcon(blankDie);
				dice2[i][j].setIcon(blankDie);
			}
		}
		log.atInfo().log("Board: Paint Dice: Painting first die");
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
		log.atInfo().log("Board: Paint Dice: Painting second die");
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

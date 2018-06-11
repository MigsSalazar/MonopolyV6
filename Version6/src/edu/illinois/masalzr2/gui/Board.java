package edu.illinois.masalzr2.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


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
		
		pieceCoords = new HashMap<String, Dimension>();
		
		gridWidth = 30;
		gridHeight = 30;
		
		pixWidth = gridWidth*20;
		pixHeight = gridHeight*20;
		dim = new Dimension(pixWidth, pixHeight);
		
		grid = new GridLayout(gridWidth, gridHeight);
		
		board = new JPanel(grid);
		board.setPreferredSize(dim);
		
		iconNumbers = new int[gridWidth][gridHeight];
		
		stickerBook = new int[gridWidth][gridHeight];
		
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
		
		for(int x=0; x<gridWidth; x++){
			for(int y=0; y<gridHeight; y++){
				display[x][y] = new GraphicsButton();
				
				//display[x][y].setBorderPainted(false);
				display[x][y].setPreferredSize(new Dimension(20,20));
				stampCollection[x][y] = new Stamp();
			}
		}
		
		//paintDisplay();
		
		for(JButton[] jba : display){
			for(JButton jb : jba){
				board.add(jb);
			}
		}
		
		board.setPreferredSize(new Dimension(600,600));
	}
	
	public JPanel getBoard(){
		return board;
	}

	public void paintDisplay() {
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
		stampCollection = collection;
	}
	
	public void setIconNumbers(int[][] nums){
		iconNumbers = nums;
	}
	
	public void setIcons(ImageIcon[] i){
		icons = i;
		
		this.setDiceIcons(icons[1], icons[2]);
		
	}
	
	public void setStickerBook(int[][] sb){
		stickerBook = sb;
	}
	
	public void setStickers(ImageIcon[] s){
		stickers = s;
	}
	
	
	public void addPiece(ImageIcon icon, int x, int y){
		display[x][y].addIcon(icon);
		pieceCoords.put(icon.toString()+pieceCoords.size(), new Dimension(x,y));
	}
	
	public void addPiece(ImageIcon icon, String key, int x, int y){
		display[x][y].addIcon(icon);
		pieceCoords.put(key, new Dimension(x,y));
	}
	
	public void movePiece(ImageIcon icon, int x, int y){
		movePiece(icon, icon.toString(), x, y);
	}
	
	public void movePiece(ImageIcon icon, String key, int x, int y){
		if(pieceCoords.containsKey(key)){
			Dimension dim = pieceCoords.get(key);
			
			display[dim.width][dim.height].wipeIcons();
			
			dim.setSize(x, y);
			
			display[dim.width][dim.height].addIcon(icon);
		}
	}
	
	public void removePiece(ImageIcon icon){
		removePiece(icon.toString());
	}
	
	public void removePiece(String key){
		if(pieceCoords.containsKey(key)){
			trueDelete(key);
		}else {
			Set<String> keys = pieceCoords.keySet();
			String[] strKeys = new String[keys.size()];
			keys.toArray(strKeys);
			
			for(String s : strKeys) {
				if(s.contains(key)) {
					trueDelete(s);
				}
			}
		}
	}

	private void trueDelete(String key) {
		Dimension dim = pieceCoords.get(key);
		pieceCoords.remove(key);
		display[dim.width][dim.height].wipeIcons();
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
	
	public void paintDice(int dOne, int dTwo){
		d1 = dOne;
		d2 = dTwo;
		if(!showDice){
			return;
		}
		
		if(dotDie == null){
			dotDie = new ImageIcon();
		}
		
		if(blankDie == null){
			blankDie = new ImageIcon();
		}
		
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

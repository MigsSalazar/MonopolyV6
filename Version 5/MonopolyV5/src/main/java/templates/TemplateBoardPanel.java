package main.java.templates;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.java.models.GamePath;
import main.java.models.Pair;
import main.java.gui.Stamp;
import main.java.gui.Piece;


/**
 * @author Miguel Salazar
 *
 */
public class TemplateBoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102084765976301993L;
	
	@Expose private int boardWidth = 30;
	@Expose private int boardHeight = 30;
	@Expose private int boardPixWidth = 600;
	@Expose private int boardPixHeight = 600;
	@Expose private int playerCount = 8;
	@Expose private String dir = System.getProperty("user.dir");
	@Expose private ArrayList<Piece> gamePieces;
	@Expose private String[] playerIconPaths;
	@Expose private String[] iconPaths;
	@Expose private int[][] basePaint;
	@Expose private Stamp[][] stampCollection = new Stamp[30][30];
	private transient ArrayList<Pair<Integer,Integer>> jailSpots;
	private transient GridLayout grid = new GridLayout(boardWidth,boardHeight);
	private transient ImageIcon[][] displayedBoard;
	private transient ImageIcon[] playerIcons;
	private transient ImageIcon[] imageIndex;
	private transient ArrayList<GamePath> paths = new ArrayList<GamePath>();
	
	
	
	/**
	 * Generates a new json file from scratch of the default board
	 * and saves it to the System user directory and then opens
	 * the default board for display
	 */
	public TemplateBoardPanel(){
		this.setLayout(grid);
		templateImageIndex();
		fillImageIndex();
		templateBasePaint();
		fillDisplayedBoard();
		fillStampCollection();
		fillThisBoard();
		generatePlayerIconPaths();
		developIcons();
		generatePaths();
		generatePieces();
		
		if(writeTemplate()){
			JOptionPane.showMessageDialog(null,"Success!");
		}else{
			JOptionPane.showMessageDialog(null,"Failure");
		}
		
		
		
	}
	
	private void formJailSpots(){
		
		/*
		case 6:	coords = new Pair<Integer,Integer>(25,4);
				break;
		case 7:coords = new Pair<Integer,Integer>(25,3);
				break;
		 */
		jailSpots = new ArrayList<Pair<Integer,Integer>>();
		jailSpots.add( new Pair<Integer,Integer>(25,2) );	//0
		jailSpots.add( new Pair<Integer,Integer>(26,2) );	//1
		jailSpots.add( new Pair<Integer,Integer>(27,2) );	//2
		jailSpots.add( new Pair<Integer,Integer>(27,3) );	//3
		jailSpots.add( new Pair<Integer,Integer>(27,4) );	//4
		jailSpots.add( new Pair<Integer,Integer>(26,4) );	//5
		jailSpots.add( new Pair<Integer,Integer>(25,4) ); 	//6
		jailSpots.add( new Pair<Integer,Integer>(25,3) );	//7
	}
	
	private void generatePieces(){
		Piece toSave;
		formJailSpots();
		gamePieces = new ArrayList<Piece>();
		for(int i=0; i<playerIcons.length; i++){
			toSave = new Piece(i, paths.get(i), playerIconPaths[i], playerIcons[i], jailSpots.get(i));
			gamePieces.add(toSave);
			
		}
	}
	
	private void developIcons(){
		playerIcons = new ImageIcon[8];
		for(int i=0; i<playerIconPaths.length; i++){
			playerIcons[i] = new ImageIcon(dir+"\\resources\\image-sets\\default-image-set\\"+playerIconPaths[i]);
		}
	}
	
	private void generatePlayerIconPaths(){
		String[] playerip = {"pupper.png",
							 "thimble.png",
							 "boot.png",
							 "boat.png",
							 "wheelbarrow.png",
							 "hat.png",
							 "iron.png",
							 "car.png"};
		playerIconPaths = playerip;
		
	}
	
	public void traversePaths(){
	
		/*
		 * NECESSARY CODE TO CHANGE IMAGES: BUT NOT THE FIRST TIME: ONLY TO CHANGE ANY TIME AFTER
		 * ALL OF IT
		displayedBoard[10][10] = imageIndex[43];
		Component com = this.getComponent(10*displayedBoard.length+10);

		if( com instanceof JLabel ){
			((JLabel) com).setIcon(displayedBoard[10][10]);
			com.repaint();
		}
		 */
	
		
		Scanner kb = new Scanner(System.in);
		
		String choice = "";
		int pathToTake = -1;
		
		while(!choice.equals("exit")){
			pathToTake = -1;
			System.out.println("Which path? 0-7 (type clear to clear board and exit to kill the process)");
			choice = kb.nextLine();
			try{
				pathToTake = Integer.parseInt(choice);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			if(pathToTake > -1 && pathToTake < 8){
				
				printPath(gamePieces.get(pathToTake));
				
			}else if(choice.equals("clear")){
				
				for(int r=0; r<displayedBoard.length; r++){
					for(int c=0; c<displayedBoard[r].length; c++){
						displayedBoard[r][c] = imageIndex[basePaint[r][c]];
						
						Component com = this.getComponent(r*displayedBoard.length+c);

						if( com instanceof JLabel ){
							((JLabel) com).setIcon(displayedBoard[r][c]);
							com.repaint();
						}
						
					}
				}
				
			}else if(choice.equals("jail")){
				for(Piece p : gamePieces){
					Pair<Integer,Integer> coord = p.specialCase(0);
					displayedBoard[coord.first][coord.second] = p.getIcon();
					
					Component com = this.getComponent(coord.first*displayedBoard.length+coord.second);

					if( com instanceof JLabel ){
						((JLabel) com).setIcon(displayedBoard[coord.first][coord.second]);
						com.repaint();
					}
				}
			}
			
		}
		
		kb.close();
		
		
	}
	
	public void printPath(Piece p){
		for(int i=0; i<40; i++){
			int r = p.getTravelPath().getCurrentRow();
			int c = p.getTravelPath().getCurrentCol();
			displayedBoard[r][c] = p.getIcon();
			
			Component com = this.getComponent(r*displayedBoard.length+c);

			if( com instanceof JLabel ){
				((JLabel) com).setIcon(displayedBoard[r][c]);
				com.repaint();
			}
			p.getTravelPath().forward();
		}
	}
	
	public void printPath(GamePath p){
		for(int i=0; i<40; i++){
			int r = p.getStepAt(i).first;
			int c = p.getStepAt(i).second;
			displayedBoard[r][c] = imageIndex[22];
			
			Component com = this.getComponent(r*displayedBoard.length+c);

			if( com instanceof JLabel ){
				((JLabel) com).setIcon(displayedBoard[r][c]);
				com.repaint();
			}
		}
	}
	
	private void generatePaths(){
		generatePath1();
		generatePath2();
		generatePath3();
		generatePath4();
		generatePath5();
		generatePath6();
		generatePath7();
		generatePath8();
	}
	
	private void generatePath1(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(25,25));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(26,c));
		}
		p.addStep(new Pair<Integer,Integer>(25,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,0));
		}
		p.addStep(new Pair<Integer,Integer>(2,0));
		for(int c=6; c<23; c=c+2){
			p.addStep(new Pair<Integer,Integer>(0,c));
		}
		p.addStep(new Pair<Integer,Integer>(1,25));
		for(int r=6; r<23; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,26));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath2(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(25,26));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(26,c));
		}
		p.addStep(new Pair<Integer,Integer>(26,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,1));
		}
		p.addStep(new Pair<Integer,Integer>(2,1));
		for(int c=7; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(0,c));
		}
		p.addStep(new Pair<Integer,Integer>(1,26));
		for(int r=6; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,27));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath3(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(25,27));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(27,c));
		}
		p.addStep(new Pair<Integer,Integer>(27,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,2));
		}
		p.addStep(new Pair<Integer,Integer>(2,4));
		for(int c=6; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(1,c));
		}
		p.addStep(new Pair<Integer,Integer>(1,27));
		for(int r=6; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,28));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath4(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(25,28));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(27,c));
		}
		p.addStep(new Pair<Integer,Integer>(28,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,3));
		}
		p.addStep(new Pair<Integer,Integer>(2,5));
		for(int c=7; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(1,c));
		}
		p.addStep(new Pair<Integer,Integer>(1,28));
		for(int r=6; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,29));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath5(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(26,25));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(28,c));
		}
		p.addStep(new Pair<Integer,Integer>(29,1));
		for(int r=23; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,0));
		}
		p.addStep(new Pair<Integer,Integer>(3,0));
		for(int c=6; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(2,c));
		}
		p.addStep(new Pair<Integer,Integer>(2,25));
		for(int r=7; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,26));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath6(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(26,26));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(28,c));
		}
		p.addStep(new Pair<Integer,Integer>(29,2));
		for(int r=23; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,1));
		}
		p.addStep(new Pair<Integer,Integer>(3,1));
		for(int c=7; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(2,c));
		}
		p.addStep(new Pair<Integer,Integer>(2,26));
		for(int r=7; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,27));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath7(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(26,27));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(29,c));
		}
		p.addStep(new Pair<Integer,Integer>(29,3));
		for(int r=23; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,2));
		}
		p.addStep(new Pair<Integer,Integer>(3,4));
		for(int c=6; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(3,c));
		}
		p.addStep(new Pair<Integer,Integer>(2,27));
		for(int r=7; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,28));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath8(){
		GamePath p = new GamePath();
		p.addStep(new Pair<Integer,Integer>(26,28));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new Pair<Integer,Integer>(29,c));
		}
		p.addStep(new Pair<Integer,Integer>(29,4));
		for(int r=23; r>5; r=r-2){
			p.addStep(new Pair<Integer,Integer>(r,3));
		}
		p.addStep(new Pair<Integer,Integer>(3,5));
		for(int c=7; c<24; c=c+2){
			p.addStep(new Pair<Integer,Integer>(3,c));
		}
		p.addStep(new Pair<Integer,Integer>(2,28));
		for(int r=7; r<24; r=r+2){
			p.addStep(new Pair<Integer,Integer>(r,29));
		}
		
		paths.add(p);
		
	}
	
	private void fillStampCollection(){
		for(int r=0; r<30; r++){
			for(int c=0; c<30; c++){
				stampCollection[r][c] = new Stamp();
			}
		}
		
		
		
		applyDownPropStampScheme(0,6);
		applyVertNoFillStampScheme(0,8);
		applyDownPropStampScheme(0,10);
		applyDownPropStampScheme(0,12);
		applyVertNoFillStampScheme(0,14);
		applyDownPropStampScheme(0,16);
		applyDownPropStampScheme(0,18);
		applyVertNoFillStampScheme(0,20);
		applyDownPropStampScheme(0,22);
		
		applyRightPropStampScheme(6,24);
		applyRightPropStampScheme(8,24);
		applyHorzNoFillStampScheme(10,24);
		applyRightPropStampScheme(12,24);
		applyHorzNoFillStampScheme(14,24);
		applyHorzNoFillStampScheme(16,24);
		applyRightPropStampScheme(18,24);
		applyHorzNoFillStampScheme(20,24);
		applyRightPropStampScheme(22,24);
		
		applyLeftPropStampScheme(6,0);
		applyLeftPropStampScheme(8,0);
		applyHorzNoFillStampScheme(10,0);
		applyLeftPropStampScheme(12,0);
		applyHorzNoFillStampScheme(14,0);
		applyLeftPropStampScheme(16,0);
		applyLeftPropStampScheme(18,0);
		applyHorzNoFillStampScheme(20,0);
		applyLeftPropStampScheme(22,0);
		
		applyUpPropStampScheme(24,6);
		applyUpPropStampScheme(24,8);
		applyVertNoFillStampScheme(24,10);
		applyUpPropStampScheme(24,12);
		applyVertNoFillStampScheme(24,14);
		applyVertNoFillStampScheme(24,16);
		applyUpPropStampScheme(24,18);
		applyVertNoFillStampScheme(24,20);
		applyUpPropStampScheme(24,22);
		
		stampCorners();
		applyFullBorderStamp(6,23,6,23);
		applyFullBorderStamp(6,8,11,13);
		applyFullBorderStamp(6,8,16,18);
		defineEngravings();
	}
	
	private void defineEngravings(){
		stampCollection[1][1].setEngraving('F');
		stampCollection[1][2].setEngraving('R');
		stampCollection[1][3].setEngraving('E');
		stampCollection[1][4].setEngraving('E');
		stampCollection[14][4].setEngraving('P');
		stampCollection[14][5].setEngraving('N');
		stampCollection[15][4].setEngraving('R');
		stampCollection[15][5].setEngraving('R');
		stampCollection[4][14].setEngraving('B');
		stampCollection[4][15].setEngraving('O');
		stampCollection[5][14].setEngraving('R');
		stampCollection[5][15].setEngraving('R');
		stampCollection[24][14].setEngraving('R');
		stampCollection[24][15].setEngraving('E');
		stampCollection[25][14].setEngraving('R');
		stampCollection[25][15].setEngraving('R');
		stampCollection[14][24].setEngraving('S');
		stampCollection[14][25].setEngraving('L');
		stampCollection[27][26].setEngraving('G');
		stampCollection[27][27].setEngraving('O');
		stampCollection[3][25].setEngraving('G');
		stampCollection[3][26].setEngraving('O');
		stampCollection[3][27].setEngraving('T');
		stampCollection[3][28].setEngraving('O');
		stampCollection[4][25].setEngraving('J');
		stampCollection[4][26].setEngraving('A');
		stampCollection[4][27].setEngraving('I');
		stampCollection[4][28].setEngraving('L');
		stampCollection[24][16].setEngraving('I');
		stampCollection[24][17].setEngraving('C');
		stampCollection[25][16].setEngraving('T');
		stampCollection[25][17].setEngraving('X');
		
		stampCollection[20][24].setEngraving('L');
		stampCollection[20][25].setEngraving('X');
		stampCollection[21][24].setEngraving('T');
		stampCollection[21][25].setEngraving('X');
		
	}
	
	private void stampCorners(){
		applyFullBorderStamp(0,5,0,5);
		applyFullBorderStamp(0,5,24,29);
		applyFullBorderStamp(24,29,0,5);
		applyFullBorderStamp(24,29,24,29);
	}
	
	private void applyVertNoFillStampScheme(int r, int c){
		applyFullBorderStamp(r,r+5,c,c+1);
	}
	
	private void applyHorzNoFillStampScheme(int r, int c){
		applyFullBorderStamp(r,r+1,c,c+5);
	}
	
	private void applyUpPropStampScheme(int r, int c){
		
		applyFullBorderStamp(r,r+1,c,c+1);
		applyFullBorderStamp(r+2,r+5,c,c+1);
		
	}
	
	private void applyRightPropStampScheme(int r, int c){
		
		applyFullBorderStamp(r,r+1,c,c+1);
		applyFullBorderStamp(r,r+1,c+2,c+5);
		
		
	}
	
	private void applyDownPropStampScheme(int r, int c){
		
		applyFullBorderStamp(r+4,r+5,c,c+1);
		applyFullBorderStamp(r,r+3,c,c+1);
		
	}
	
	private void applyLeftPropStampScheme(int r, int c){
		//System.out.println("left stamp scheme called");
		//System.out.println("r="+r+" c="+c);
		applyFullBorderStamp(r,r+1,c+4,c+5);
		applyFullBorderStamp(r,r+1,c,c+3);
	}
	
	private void applyFullBorderStamp(int r1, int r2, int c1, int c2){
		int factor = 1;
		//System.out.println("FullBorder requested");
		//System.out.println("r1="+r1+" r2="+r2+" c1="+c1+" c2="+c2);
		for(int row = r1; row < r2+1; row++){
			for(int col = c1; col < c2+1; col++){
				factor = 1;
				if(row == r1){
					factor = factor*2;
				}
				if(row == r2){
					factor = factor*5;
				}
				if(col == c1){
					factor = factor*7;
				}
				if(col == c2){
					factor = factor*3;
				}
				//System.out.println("factor at r=" + row + " and c=" + col + " is f=" + factor);
				stampCollection[row][col].setBorder(factor);
			}
		}
	}
	
	private boolean writeTemplate(){
		try{
			Writer iowrite = new FileWriter(dir+"/saved-games/default-game/board_config.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			gson.toJson(this, iowrite);
			iowrite.close();
			return true;
		}catch(IOException ioe){
			return false;
		}
	}
	
	private void fillThisBoard(){
		for(int r=0; r<displayedBoard.length; r++){
			for(int c=0; c<displayedBoard[r].length; c++){
				
				//creates a new label with the correct image
				JLabel newLabel = new JLabel(displayedBoard[r][c]);
				
				//define label such that engravings show
				newLabel.setIconTextGap(-30);
				newLabel.setOpaque(true);
				newLabel.setLayout(null);
				
				//stamping border
				stampCollection[r][c].giveBorder(newLabel);
				
				//add icon to boardpanel
				this.add(newLabel, r*displayedBoard.length+c);
				
				//stamping engraving
				stampCollection[r][c].engraveLabel(newLabel);
				
				//make label visible
				newLabel.setVisible(true);
				
			}
		}
	}
	
	private void fillDisplayedBoard(){
		displayedBoard = new ImageIcon[30][30];
		for(int r=0; r<basePaint.length; r++){
			for(int c=0; c<basePaint[r].length; c++){
				displayedBoard[r][c] = imageIndex[basePaint[r][c]];
			}
		}
	}
	
	private void fillImageIndex(){
		imageIndex = new ImageIcon[iconPaths.length];
		for(int i=0; i<iconPaths.length; i++){
			//System.out.println(dir+"\\resources\\image-sets\\default-image-set\\"+iconPaths[i]);
			imageIndex[i] = new ImageIcon(dir+"\\resources\\image-sets\\default-image-set\\"+iconPaths[i]);
		}
	}
	
	private void templateBasePaint(){
		int[][] temp = {{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //0
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //1
						{0,0,23,52,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //2
						{0,0,23,53,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //3
						{0,0,23,0,0,0,		23,23,48,48,23,23,23,23,0,0,28,28,28,28,44,44,28,28,	0,0,0,0,0,0}, //4
						{0,0,23,0,0,0,		23,23,48,48,23,23,23,23,0,0,28,28,28,28,44,44,28,28,	0,0,0,0,0,0}, //5
						
						{0,0,0,0,18,18,		0,0,0,0,0,1,1,1,0,0,1,1,1,0,0,0,0,0,					33,33,0,0,0,0}, //6
						{0,0,0,0,18,18,		0,0,0,0,0,2,1,2,0,0,2,1,2,0,0,0,0,0,					33,33,0,0,0,0}, //7
						{0,0,0,0,18,18,		0,0,0,0,0,1,1,1,0,0,1,1,1,0,0,0,0,0,					33,33,0,0,0,0}, //8
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //9
						{0,0,0,0,46,46,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					46,46,0,0,0,0}, //10
						{0,0,0,0,47,47,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					47,47,0,0,0,0}, //11
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //12
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //13
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //14
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //15
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					48,48,0,0,0,0}, //16
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					48,48,0,0,0,0}, //17
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //18
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //19
						{0,0,0,0,43,43,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //20
						{0,0,0,0,43,43,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //21
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //22
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //23
							
						{0,45,45,45,45,45,	8,8,8,8,48,48,8,8,0,0,0,0,3,3,46,46,3,3,				0,0,0,0,0,0}, //24
						{0,45,0,0,0,45,		8,8,8,8,48,48,8,8,0,0,0,0,3,3,47,47,3,3,				0,0,0,0,0,0}, //25
						{0,45,0,45,0,45,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //26
						{0,45,0,0,0,45,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,49,0,0,0,0}, //27
						{0,45,45,45,45,45,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					50,23,23,23,23,23}, //28
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,51,0,0,0,0}};//29
		basePaint = temp;
	}
	
	private void templateImageIndex(){
		String[] icons = {	"baseboard.png",			//0
							"dotdie.png",				//1
							"blankdie.png",				//2
							
							"purple.png",				//3
							"purplehouse.png",			//4
							"purplehotelleft.png",		//5
							"purplehotelright.png",		//6
							"purplehotelbottom.png",	//7
							"lightblue.png",			//8
							"lightbluehouse.png",		//9
							"lightbluehotelleft.png",	//10
							"lightbluehotelright.png",	//11
							"lightbluehotelbottom.png", //12
							"pink.png",					//13
							"pinkhouse.png",			//14
							"pinkhotelleft.png",		//15
							"pinkhotelright.png",		//16
							"pinkhotelbottom.png",		//17
							"orange.png",				//18
							"orangehouse.png",			//19
							"orangehotelleft.png",		//20
							"orangehotelright.png",		//21
							"orangehotelbottom.png",	//22
							"red.png",					//23
							"redhouse.png",				//24
							"redhotelleft.png",			//25
							"redhotelright.png",		//26
							"redhotelbottom.png",		//27
							"yellow.png",				//28
							"yellowhouse.png",			//29
							"yellowhotelleft.png",		//30
							"yellowhotelright.png",		//31
							"yellowhotelbottom.png",	//32
							"green.png",				//33
							"greenhouse.png",			//34
							"greenhotelleft.png",		//35
							"greenhotelright.png",		//36
							"greenhotelbottom.png",		//37
							"blue.png",					//38
							"bluehouse.png",			//39
							"bluehotelleft.png",		//40
							"bluehotelright.png",		//41
							"bluehotelbottom.png",		//42
							
							"eleccomp.png",				//43
							"waterworks.png",			//44
							
							"jail.png",					//45
							"chesttop.png",				//46
							"chestbottom.png",			//47
							"chance.png",				//48
							
							"gotop.png",				//49
							"gomid.png",				//50
							"gobot.png",				//51
							
							"parktop.png",				//52
							"parkbot.png"};				//53
		iconPaths = icons;
	}
	
	
}





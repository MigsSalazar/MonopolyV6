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

import main.java.models.Path;
import main.java.gui.Stamp;
import main.java.models.CoordPair;


/**
 * @author Miguel Salazar
 *
 */
public class TemplateBoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102084765976301993L;
	
	private transient ImageIcon[] imageIndex;
	@Expose private String[] iconPaths;
	@Expose private int[][] basePaint;
	@Expose private Stamp[][] stampCollection = new Stamp[30][30];
	@Expose private ArrayList<Path> paths = new ArrayList<Path>();
	@Expose private int width = 30;
	@Expose private int height = 30;
	private GridLayout grid = new GridLayout(width,height);
	
	/*
	private ArrayList<Piece> gamePieces;
	*/
	
	private transient ImageIcon[][] displayedBoard;
	
	
	/**
	 * Generates a new json file from scratch of the default board
	 * and saves it to the System user directory and then opens
	 * the default board for display
	 */
	public TemplateBoardPanel(){
		String dir = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/";
		this.setLayout(grid);
		templateImageIndex(dir);
		fillImageIndex();
		templateBasePaint();
		fillDisplayedBoard();
		fillStampCollection();
		fillThisBoard();
		
		generatePaths();
		
		if(writeTemplate()){
			JOptionPane.showMessageDialog(null,"Success!");
		}else{
			JOptionPane.showMessageDialog(null,"Failure");
		}
		
		
		
	}
	
	public void traversePaths(){
	
		/*
		 * NECESSARY CODE TO CHANGE IMAGES
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
				
				printPath(paths.get(pathToTake));
				
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
				
			}
			
		}
		
		kb.close();
		
		
	}
	
	public void printPath(Path p){
		for(int i=0; i<40; i++){
			int r = p.getCurrentStep(i).getRow();
			int c = p.getCurrentStep(i).getCol();
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
		Path p = new Path();
		p.addStep(new CoordPair(25,25));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new CoordPair(26,c));
		}
		p.addStep(new CoordPair(25,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new CoordPair(r,0));
		}
		p.addStep(new CoordPair(2,0));
		for(int c=6; c<23; c=c+2){
			p.addStep(new CoordPair(0,c));
		}
		p.addStep(new CoordPair(1,25));
		for(int r=6; r<23; r=r+2){
			p.addStep(new CoordPair(r,26));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath2(){
		Path p = new Path();
		p.addStep(new CoordPair(25,26));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new CoordPair(26,c));
		}
		p.addStep(new CoordPair(26,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new CoordPair(r,1));
		}
		p.addStep(new CoordPair(2,1));
		for(int c=7; c<24; c=c+2){
			p.addStep(new CoordPair(0,c));
		}
		p.addStep(new CoordPair(1,26));
		for(int r=6; r<24; r=r+2){
			p.addStep(new CoordPair(r,27));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath3(){
		Path p = new Path();
		p.addStep(new CoordPair(25,27));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new CoordPair(27,c));
		}
		p.addStep(new CoordPair(27,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new CoordPair(r,2));
		}
		p.addStep(new CoordPair(2,4));
		for(int c=6; c<24; c=c+2){
			p.addStep(new CoordPair(1,c));
		}
		p.addStep(new CoordPair(1,27));
		for(int r=6; r<24; r=r+2){
			p.addStep(new CoordPair(r,28));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath4(){
		Path p = new Path();
		p.addStep(new CoordPair(25,28));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new CoordPair(27,c));
		}
		p.addStep(new CoordPair(28,0));
		for(int r=22; r>5; r=r-2){
			p.addStep(new CoordPair(r,3));
		}
		p.addStep(new CoordPair(2,5));
		for(int c=7; c<24; c=c+2){
			p.addStep(new CoordPair(1,c));
		}
		p.addStep(new CoordPair(1,28));
		for(int r=6; r<24; r=r+2){
			p.addStep(new CoordPair(r,29));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath5(){
		Path p = new Path();
		p.addStep(new CoordPair(26,25));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new CoordPair(28,c));
		}
		p.addStep(new CoordPair(29,1));
		for(int r=23; r>5; r=r-2){
			p.addStep(new CoordPair(r,0));
		}
		p.addStep(new CoordPair(3,0));
		for(int c=6; c<24; c=c+2){
			p.addStep(new CoordPair(2,c));
		}
		p.addStep(new CoordPair(2,25));
		for(int r=7; r<24; r=r+2){
			p.addStep(new CoordPair(r,26));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath6(){
		Path p = new Path();
		p.addStep(new CoordPair(26,26));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new CoordPair(28,c));
		}
		p.addStep(new CoordPair(29,2));
		for(int r=23; r>5; r=r-2){
			p.addStep(new CoordPair(r,1));
		}
		p.addStep(new CoordPair(3,1));
		for(int c=7; c<24; c=c+2){
			p.addStep(new CoordPair(2,c));
		}
		p.addStep(new CoordPair(2,26));
		for(int r=7; r<24; r=r+2){
			p.addStep(new CoordPair(r,27));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath7(){
		Path p = new Path();
		p.addStep(new CoordPair(26,27));
		for(int c=22; c > 5; c=c-2){
			p.addStep(new CoordPair(29,c));
		}
		p.addStep(new CoordPair(29,3));
		for(int r=23; r>5; r=r-2){
			p.addStep(new CoordPair(r,2));
		}
		p.addStep(new CoordPair(3,4));
		for(int c=6; c<24; c=c+2){
			p.addStep(new CoordPair(3,c));
		}
		p.addStep(new CoordPair(2,27));
		for(int r=7; r<24; r=r+2){
			p.addStep(new CoordPair(r,28));
		}
		
		paths.add(p);
		
	}
	
	private void generatePath8(){
		Path p = new Path();
		p.addStep(new CoordPair(26,28));
		for(int c=23; c > 5; c=c-2){
			p.addStep(new CoordPair(29,c));
		}
		p.addStep(new CoordPair(29,4));
		for(int r=23; r>5; r=r-2){
			p.addStep(new CoordPair(r,3));
		}
		p.addStep(new CoordPair(3,5));
		for(int c=7; c<24; c=c+2){
			p.addStep(new CoordPair(3,c));
		}
		p.addStep(new CoordPair(2,28));
		for(int r=7; r<24; r=r+2){
			p.addStep(new CoordPair(r,29));
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
			Writer iowrite = new FileWriter(System.getProperty("user.dir")+"/template.json");
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
				//ImageIcon icon = displayedBoard[r][c];
				JLabel newLabel = new JLabel(displayedBoard[r][c]);
				//newLabel.setIcon(displayedBoard[r][c]);
				newLabel.setIconTextGap(-30);
				newLabel.setOpaque(true);
				newLabel.setLayout(null);
				
				
				stampCollection[r][c].giveBorder(newLabel);
				this.add(newLabel, r*displayedBoard.length+c);
				
				//newLabel.setText(""+stampCollection[r][c].getEngraving());
				stampCollection[r][c].engraveLabel(newLabel);
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
			imageIndex[i] = new ImageIcon(iconPaths[i]);
		}
	}
	
	private void templateBasePaint(){
		int[][] temp = {{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //0
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //1
						{0,0,5,5,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //2
						{0,0,5,5,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //3
						{0,0,5,0,0,0,		5,5,44,44,5,5,5,5,0,0,6,6,6,6,46,46,6,6,		0,0,0,0,0,0}, //4
						{0,0,5,0,0,0,		5,5,44,44,5,5,5,5,0,0,6,6,6,6,46,46,6,6,		0,0,0,0,0,0}, //5
						
						{0,0,0,0,4,4,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			7,7,0,0,0,0}, //6
						{0,0,0,0,4,4,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			7,7,0,0,0,0}, //7
						{0,0,0,0,4,4,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			7,7,0,0,0,0}, //8
						{0,0,0,0,4,4,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			7,7,0,0,0,0}, //9
						{0,0,0,0,42,42,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			42,42,0,0,0,0}, //10
						{0,0,0,0,43,43,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			43,43,0,0,0,0}, //11
						{0,0,0,0,4,4,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			7,7,0,0,0,0}, //12
						{0,0,0,0,4,4,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			7,7,0,0,0,0}, //13
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //14
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //15
						{0,0,0,0,3,3,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			44,44,0,0,0,0}, //16
						{0,0,0,0,3,3,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			44,44,0,0,0,0}, //17
						{0,0,0,0,3,3,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			8,8,0,0,0,0}, //18
						{0,0,0,0,3,3,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			8,8,0,0,0,0}, //19
						{0,0,0,0,45,45,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //20
						{0,0,0,0,45,45,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //21
						{0,0,0,0,3,3,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			8,8,0,0,0,0}, //22
						{0,0,0,0,3,3,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			8,8,0,0,0,0}, //23
							
						{0,41,41,41,41,41,	2,2,2,2,44,44,2,2,0,0,0,0,1,1,42,42,1,1,		0,0,0,0,0,0}, //24
						{0,41,0,0,0,41,		2,2,2,2,44,44,2,2,0,0,0,0,1,1,43,43,1,1,		0,0,0,0,0,0}, //25
						{0,41,0,41,0,41,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,0,0,0,0,0}, //26
						{0,41,0,0,0,41,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,5,0,0,0,0}, //27
						{0,41,41,41,41,41,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			5,5,5,5,5,5}, //28
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,			0,5,0,0,0,0}};//29
		basePaint = temp;
	}
	
	private void templateImageIndex(String dir){
		String[] icons = {	dir+"baseboard.png",				//0
							dir+"purple.png",				//1
							dir+"lightblue.png",			//2
							dir+"pink.png",					//3
							dir+"orange.png",				//4
							dir+"red.png",					//5
							dir+"yellow.png",				//6
							dir+"green.png",				//7
							dir+"blue.png",					//8
							dir+"purplehouse.png",			//9
							dir+"lightbluehouse.png",		//10
							dir+"pinkhouse.png",			//11
							dir+"orangehouse.png",			//12
							dir+"redhouse.png",				//13
							dir+"yellowhouse.png",			//14
							dir+"greenhouse.png",			//15
							dir+"bluehouse.png",			//16
							dir+"purplehotelleft.png",		//17
							dir+"purplehotelright.png",		//18
							dir+"purplehotelbottom.png",	//19
							dir+"lightbluehotelleft.png",	//20
							dir+"lightbluehotelright.png",	//21
							dir+"lightbluehotelbottom.png", //22
							dir+"pinkhotelleft.png",		//23
							dir+"pinkhotelright.png",		//24
							dir+"pinkhotelbottom.png",		//25
							dir+"orangehotelleft.png",		//26
							dir+"orangehotelright.png",		//27
							dir+"orangehotelbottom.png",	//28
							dir+"redhotelleft.png",			//29
							dir+"redhotelright.png",		//30
							dir+"redhotelbottom.png",		//31
							dir+"yellowhotelleft.png",		//32
							dir+"yellowhotelright.png",		//33
							dir+"yellowhotelbottom.png",	//34
							dir+"greenhotelleft.png",		//35
							dir+"greenhotelright.png",		//36
							dir+"greenhotelbottom.png",		//37
							dir+"bluehotelleft.png",		//38
							dir+"bluehotelright.png",		//39
							dir+"bluehotelbottom.png",		//40
							dir+"jail.png",					//41
							dir+"chesttop.png",				//42
							dir+"chestbottom.png",			//43
							dir+"chance.png",				//44
							dir+"eleccomp.png",				//45
							dir+"waterworks.png"};			//46
		iconPaths = icons;
	}
	
	
}





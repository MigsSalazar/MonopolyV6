package main.java.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.java.models.Path;
import main.java.models.CoordPair;


/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102084765976301993L;
	
	private transient ImageIcon[] imageIndex;
	@Expose private String[] iconPaths;
	@Expose private int[][] basePaint;
	@Expose private int[][] currPaint;
	@Expose private Stamp[][] stampCollection = new Stamp[30][30];
	/*
	private ArrayList<Path> paths = new ArrayList<Path>();
	private ArrayList<Piece> gamePieces;
	*/
	
	private transient ImageIcon[][] displayedBoard;
	
	
	/**
	 * Generates a new json file from scratch of the default board
	 * and saves it to the System user directory and then opens
	 * the default board for display
	 */
	public BoardPanel(){
		String dir = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/";
		this.setLayout(new GridLayout(30,30));
		templateImageIndex(dir);
		fillImageIndex();
		templateBasePaint();
		fillCurrPaint();
		fillDisplayedBoard();
		fillStampCollection();
		fillThisBoard();
		if(writeTemplate()){
			JOptionPane.showMessageDialog(null,"Success!");
		}else{
			JOptionPane.showMessageDialog(null,"Failure");
		}
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
				ImageIcon icon = displayedBoard[r][c];
				JLabel newLabel = new JLabel(){
					public void paintComponent(Graphics g){
						g.drawImage(icon.getImage(), 0, 0, null);
						super.paintComponent(g);
					}
				};
				
				newLabel.setOpaque(false);
				stampCollection[r][c].giveBorder(newLabel);
				this.add(newLabel);
				
				//newLabel.setText(""+stampCollection[r][c].getEngraving());
				stampCollection[r][c].engraveLabel(newLabel);
			}
		}
	}
	
	private void fillDisplayedBoard(){
		displayedBoard = new ImageIcon[30][30];
		for(int r=0; r<currPaint.length; r++){
			for(int c=0; c<currPaint[r].length; c++){
				displayedBoard[r][c] = imageIndex[currPaint[r][c]];
			}
		}
	}
	
	private void fillCurrPaint(){
		currPaint = new int[30][30];
		for(int r=0; r<currPaint.length; r++){
			for(int c=0; c<currPaint[r].length; c++){
				currPaint[r][c] = basePaint[r][c];
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





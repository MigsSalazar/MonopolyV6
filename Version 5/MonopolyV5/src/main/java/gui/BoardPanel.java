package main.java.gui;

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
	private Stamp[][] stampCollection = new Stamp[30][30];
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
		
		applyRightPropStampScheme(6,0);
		applyRightPropStampScheme(8,0);
		applyHorzNoFillStampScheme(10,0);
		applyRightPropStampScheme(12,0);
		applyHorzNoFillStampScheme(14,0);
		applyRightPropStampScheme(16,0);
		applyRightPropStampScheme(18,0);
		applyHorzNoFillStampScheme(20,0);
		applyRightPropStampScheme(22,0);
		
		applyLeftPropStampScheme(6,24);
		applyLeftPropStampScheme(8,24);
		applyHorzNoFillStampScheme(10,24);
		applyLeftPropStampScheme(12,24);
		applyHorzNoFillStampScheme(14,24);
		applyHorzNoFillStampScheme(16,24);
		applyLeftPropStampScheme(18,24);
		applyHorzNoFillStampScheme(20,24);
		applyLeftPropStampScheme(22,24);
		
		applyDownPropStampScheme(24,6);
		applyVertNoFillStampScheme(24,8);
		applyDownPropStampScheme(24,10);
		applyDownPropStampScheme(24,12);
		applyVertNoFillStampScheme(24,14);
		applyDownPropStampScheme(24,16);
		applyDownPropStampScheme(24,18);
		applyVertNoFillStampScheme(24,20);
		applyDownPropStampScheme(24,22);
		
	}
	
	private void applyVertNoFillStampScheme(int r, int c){
		stampCollection[ r ][ c ].setBorder(14);
		stampCollection[ r ][c+1].setBorder(6);
		stampCollection[r+5][ c ].setBorder(35);
		stampCollection[r+5][c+1].setBorder(15);
		for(int row = r+1; row<r+5; row++){
			stampCollection[row][ c ].setBorder(7);
			stampCollection[row][c+1].setBorder(3);
		}
	}
	
	private void applyHorzNoFillStampScheme(int r, int c){
		stampCollection[ r ][ c ].setBorder(14);
		stampCollection[r+1][ c ].setBorder(35);
		stampCollection[ r ][c+5].setBorder(6);
		stampCollection[r+1][c+5].setBorder(15);
		for(int col = c+1; col<c+5; col++){
			stampCollection[ r ][col].setBorder(7);
			stampCollection[r+1][col].setBorder(3);
		}
	}
	
	private void applyUpPropStampSheme(int r, int c){
		stampCollection[ r ][ c ].setBorder(14);
		stampCollection[ r ][c+1].setBorder(6);
		stampCollection[r+1][ c ].setBorder(35);
		stampCollection[r+1][c+1].setBorder(15);
		
		stampCollection[r+2][ c ].setBorder(14);
		stampCollection[r+2][c+1].setBorder(6);
		stampCollection[r+5][ c ].setBorder(35);
		stampCollection[r+5][c+1].setBorder(15);
		
		stampCollection[r+3][ c ].setBorder(7);
		stampCollection[r+3][c+1].setBorder(3);
		stampCollection[r+4][ c ].setBorder(7);
		stampCollection[r+4][c+1].setBorder(3);
	}
	
	private void applyRightPropStampScheme(int r, int c){
		stampCollection[ r ][c+4].setBorder(14);
		stampCollection[r+1][c+4].setBorder(35);
		stampCollection[ r ][c+5].setBorder(6);
		stampCollection[r+1][c+5].setBorder(15);
		
		stampCollection[ r ][ c ].setBorder(14);
		stampCollection[r+1][ c ].setBorder(35);
		stampCollection[ r ][c+3].setBorder(6);
		stampCollection[r+1][c+3].setBorder(15);
		
		stampCollection[ r ][c+1].setBorder(14);
		stampCollection[r+1][c+1].setBorder(35);
		stampCollection[ r ][c+2].setBorder(6);
		stampCollection[r+1][c+2].setBorder(15);
	}
	
	private void applyDownPropStampScheme(int r, int c){
		stampCollection[r+4][ c ].setBorder(14);
		stampCollection[r+4][c+1].setBorder(6);
		stampCollection[r+5][ c ].setBorder(35);
		stampCollection[r+5][c+1].setBorder(15);
		
		stampCollection[ r ][ c ].setBorder(14);
		stampCollection[ r ][c+1].setBorder(6);
		stampCollection[r+3][ c ].setBorder(35);
		stampCollection[r+3][c+1].setBorder(15);
		
		stampCollection[r+1][ c ].setBorder(7);
		stampCollection[r+1][c+1].setBorder(3);
		stampCollection[r+2][ c ].setBorder(7);
		stampCollection[r+2][c+1].setBorder(3);
	}
	
	private void applyLeftPropStampScheme(int r, int c){
		stampCollection[ r ][ c ].setBorder(14);
		stampCollection[r+1][ c ].setBorder(35);
		stampCollection[ r ][c+1].setBorder(6);
		stampCollection[r+1][c+1].setBorder(15);
		
		stampCollection[ r ][c+2].setBorder(14);
		stampCollection[r+1][c+2].setBorder(35);
		stampCollection[ r ][c+5].setBorder(6);
		stampCollection[r+1][c+5].setBorder(15);
		
		stampCollection[ r ][c+3].setBorder(14);
		stampCollection[r+1][c+3].setBorder(35);
		stampCollection[ r ][c+4].setBorder(6);
		stampCollection[r+1][c+4].setBorder(15);
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
				JLabel newLabel = new JLabel();
				newLabel.setIcon(displayedBoard[r][c]);
				stampCollection[r][c].applyFullStamp(newLabel);
				this.add(newLabel);
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





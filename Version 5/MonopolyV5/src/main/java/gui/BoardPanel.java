/**
 * 
 */
package main.java.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.annotations.Expose;

import main.java.models.GamePath;

/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {
	
	@Expose private int boardWidth = 30;					//width of the board by icons, not by pixels
	@Expose private int boardHeight = 30;					//height of the board by icons, not by pixels
	@Expose private int playerCount;						//number of players in the game (used for player displaying)
	@Expose private ArrayList<Piece> gamePieces;			//pieces that move around the board
	@Expose private String[] playerIconPaths;				//stores the path as a string of the icons that gamepieces uses to display themselves
	@Expose private String[] iconPaths;						//stores all the paths for all the icons available for the board to use
	@Expose private int[][] basePaint;						//stores the specified default board design
	@Expose private Stamp[][] stampCollection = new Stamp[30][30];//the stamps that save the text, text format, and borders of each tile
	private transient GridLayout grid;						//gridlayout, nothing much else
	private transient ImageIcon[][] displayedBoard;			//the currently displayed grid of icons
	private transient ImageIcon[] imageIndex;				//stores all the icons used by the board
	
	public void pickPlayerPieces(int[] selection){
		if(selection.length == getPlayerCount()){
			for(int i=0; i<selection.length; i++){
				gamePieces.get(i).setFileLocation(playerIconPaths[selection[i]]);
			}
		}else if(selection.length > getPlayerCount()){
			for(int i=0; i<getPlayerCount(); i++){
				gamePieces.get(i).setFileLocation(playerIconPaths[i]);
			}
		}else{
			for(int i=0; i<selection.length; i++){
				gamePieces.get(i).setFileLocation(playerIconPaths[i]);
			}
		}
	}
	
	public void firstPaintBoard() throws NullPointerException{
		makeGrid();
		this.setLayout(grid);
		
		imageIndex = new ImageIcon[iconPaths.length];
		for(int i=0; i<imageIndex.length; i++){
			imageIndex[i] = new ImageIcon(iconPaths[i]);
		}
		
		displayedBoard = new ImageIcon[boardWidth][boardHeight];
		for(int r=0; r<boardWidth; r++){
			for(int c=0; c<boardHeight; c++){
				displayedBoard[r][c] = imageIndex[basePaint[r][c]];
				addLabel(r, c);
			}
		}
		
		int[] selection = {0,1,2,3,4,5,6,7};
		
		pickPlayerPieces(selection);
		
		for(Piece gp : gamePieces){
			gp.updateIcon();
		}
		
		
		for(int i=0; i<getPlayerCount(); i++){
			updateIcon(gamePieces.get(i), 0);
		}
			
			
	}
	
	public boolean movePlayer(int pid, int addedStep){
		Piece p = gamePieces.get(pid);
		addedStep += p.getTravelPath().getCurrentStep();
		return updateIcon(p, addedStep);
	}

	public boolean updateIcon(int pid, int newPosition){
		Piece p = gamePieces.get(pid);
		return updateIcon(p, newPosition);
	}
	
	public boolean updateIcon(Piece p, int newposition){
		try{
			GamePath gp = p.getTravelPath();
			int currR = gp.getCurrentRow();
			int currC = gp.getCurrentCol();
			displayedBoard[currR][currC] = imageIndex[ basePaint[currR][currC] ];
			Component com = this.getComponent(currR*displayedBoard.length+currC);
	
			if( com instanceof JLabel ){
				((JLabel) com).setIcon(displayedBoard[currR][currC]);
				com.repaint();
			}
			
			gp.setCurrentStep(newposition);
			currR = gp.getCurrentRow();
			currC = gp.getCurrentCol();
			
			displayedBoard[currR][currC] = p.getIcon();
			com = this.getComponent(currR*displayedBoard.length+currC);
	
			if( com instanceof JLabel ){
				((JLabel) com).setIcon(displayedBoard[currR][currC]);
				com.repaint();
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private void addLabel(int r, int c) {
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
	
	private void makeGrid(){
		grid = new GridLayout(boardWidth, boardHeight);
	}

	/**
	 * @return the playerCount
	 */
	public int getPlayerCount() {
		return playerCount;
	}

}

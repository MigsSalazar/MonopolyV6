/**
 * 
 */
 

import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.annotations.Expose;


/**
 * @author Miguel Salazar
 *
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	
	@Expose private int boardWidth;					//width of the board by icons, not by pixels
	@Expose private int boardHeight;					//height of the board by icons, not by pixels
	@Expose private int boardPixWidth;
	@Expose private int boardPixHeight;
	@Expose private int playerCount;						//number of players in the game (used for player displaying)
	@Expose private ArrayList<Piece> gamePieces;			//pieces that move around the board
	@Expose private String[] playerIconPaths;				//stores the path as a string of the icons that gamepieces uses to display themselves
	@Expose private String[] iconPaths;						//stores all the paths for all the icons available for the board to use
	@Expose private int[][] basePaint;						//stores the specified default board design
	@Expose private Stamp[][] stampCollection = new Stamp[30][30];//the stamps that save the text, text format, and borders of each tile
	private transient GridLayout grid;						//gridlayout, nothing much else
	private transient ImageIcon[][] displayedBoard;			//the currently displayed grid of icons
	private transient ImageIcon[] imageIndex;				//stores all the icons used by the board
	
	
	
	public void pickPlayerPieces(int[] selection, String dir){
		if(selection.length == getPlayerCount()){
			//The number of tokens selected is equal to the number of players
			for(int i=0; i<selection.length; i++){
				gamePieces.get(i).setFileLocation(dir+playerIconPaths[selection[i]]);
			}
		}else if(selection.length > getPlayerCount()){
			//A selection error has occurred where there are MORE selected tokens than active players
			for(int i=0; i<getPlayerCount(); i++){
				gamePieces.get(i).setFileLocation(dir+playerIconPaths[selection[i]]);
			}
		}else{
			//a selection error has occurred where there are LESS selected tokens than active players
			for(int i=0; i<selection.length; i++){
				gamePieces.get(i).setFileLocation(dir+playerIconPaths[i]);
			}
		}
	}
	
	
	
	public void jailPlayer(int player){
		Piece playerPiece = gamePieces.get(player);
		Pair<Integer,Integer> coords = playerPiece.specialCase(0);
		updateIcon(playerPiece, 10,coords.first,coords.second);
		
	}
	
	public void firstPaintBoard(ArrayList<Player> players) throws NullPointerException{
		firstPaintBoard("", players);
	}
	
	public void firstPaintBoard(String dir, ArrayList<Player> players) throws NullPointerException{
		makeGrid();
		this.setLayout(grid);
		
		imageIndex = new ImageIcon[iconPaths.length];
		for(int i=0; i<imageIndex.length; i++){
			if(new File(dir+iconPaths[i]).exists()){
				imageIndex[i] = new ImageIcon(dir+iconPaths[i]);
			}else if(new File(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/"+iconPaths[i]  ).exists()){
				imageIndex[i] = new ImageIcon(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/"+iconPaths[i]);
			}else{
				imageIndex[i] = new ImageIcon(System.getProperty("user.dir")+"/resources/image-sets/default-image-set/404ERROR.png");
			}
			
		}
		
		displayedBoard = new ImageIcon[boardWidth][boardHeight];
		for(int r=0; r<boardWidth; r++){
			for(int c=0; c<boardHeight; c++){
				displayedBoard[r][c] = imageIndex[basePaint[r][c]];
				addLabel(r, c);
			}
		}
		paintDice(0,0);
		for(Piece gp : gamePieces){
			gp.updateIcon(dir);
		}
		
		
		for(int i=0; i<getPlayerCount(); i++){
			Piece plPiece = gamePieces.get(i);
			if(players.get(i).isInJail()){
				int id = players.get(i).getUserID();
				Pair<Integer,Integer> coords = gamePieces.get(id).specialCase(0);
				updateIcon(plPiece, plPiece.getTravelPath().getCurrentStep(), coords.first, coords.second);
			}else{
				updateIcon(plPiece, plPiece.getTravelPath().getCurrentStep());
			}
		}
			
			
	}
	
	public void paintDice(int d1, int d2){
		/*
		 * Dice one occupies areas
		 * 	12	14
		 * 7 0 0 0
		 * 	 - 0 -
		 * 9 0 0 0
		 * 
		 * Dice one occupies areas
		 * 	17	19
		 * 7 0 0 0
		 * 	 - 0 -
		 * 9 0 0 0
		 *applyFullBorderStamp(6,8,11,13);
		 *applyFullBorderStamp(6,8,16,18);
		 *
		 */
		setDoubles(false);
		cleanDice();
		if(d1 <1 || d1 > 6 || d2 < 1 || d2 > 6){
			return;
		}else{
			dice1(d1);
			
			dice2(d2);
			if(d1==d2){
				setDoubles(true);
			}
		}
	}

	private void dice2(int d2) {
		switch(d2){
		case 5: changeIcon(imageIndex[1],6,18);
				changeIcon(imageIndex[1],8,16);
				
		case 3: changeIcon(imageIndex[1],6,16);
				changeIcon(imageIndex[1],8,18);
				
		case 1: changeIcon(imageIndex[1],7,17);
				break;
				
		case 6: changeIcon(imageIndex[1],6,17);
				changeIcon(imageIndex[1],8,17);
				
		case 4: changeIcon(imageIndex[1],6,18);
				changeIcon(imageIndex[1],8,16);
				
		case 2: changeIcon(imageIndex[1],6,16);
				changeIcon(imageIndex[1],8,18);
				break;
		
		}
	}

	private void dice1(int d1) {
		switch(d1){
		case 5: changeIcon(imageIndex[1],6,13);
				changeIcon(imageIndex[1],8,11);
				
		case 3: changeIcon(imageIndex[1],6,11);
				changeIcon(imageIndex[1],8,13);
				
		case 1: changeIcon(imageIndex[1],7,12);
				break;
		
		case 6: changeIcon(imageIndex[1],6,12);
				changeIcon(imageIndex[1],8,12);
				
		case 4: changeIcon(imageIndex[1],6,13);
				changeIcon(imageIndex[1],8,11);
				
		case 2: changeIcon(imageIndex[1],6,11);
				changeIcon(imageIndex[1],8,13);
				break;
		}
	}
	
	public void paintDice(int d1){
		cleanDice();
		dice1(d1);
	}
	
	private void setDoubles(boolean flag){
		String setme;
		if(flag){
			setme = "DOUBLES";
			//System.out.println("set doubles: doubles found");
		}else{
			setme = "       ";
			//System.out.println("no doubles:"+setme+":");
		}
		for(int i=0; i<setme.length(); i++){
			Component com;
			ImageIcon icon = displayedBoard[10][i+11];
			com = this.getComponent(10*displayedBoard.length+(i+11));

			if( com instanceof JLabel ){
				JLabel comp = (JLabel)com;
				comp.setIconTextGap(-30);
				comp.setOpaque(true);
				comp.setLayout(null);
				stampCollection[10][i+11].setEngraving(setme.charAt(i));
				stampCollection[10][i+11].engraveLabel(comp);
				changeIcon(icon, 10, (i+11));
			}
		}
	}
	
	private void cleanDice(){
		for(int r=6; r<9; r++){
			for(int c=11; c<14; c++){
				changeIcon(imageIndex[2],r,c);
			}
		}
		for(int r=6; r<9; r++){
			for(int c=16; c<19; c++){
				changeIcon(imageIndex[2],r,c);
			}
		}
	}
	
	public boolean movePlayer(int pid, int addedStep){
		Piece p = gamePieces.get(pid);
		addedStep += p.getTravelPath().getCurrentStep();
		return updateIcon(p, addedStep);
	}

	/**
	 * Takes in a player ID and passes the corresponding Piece (not Player object)
	 * to the proper method
	 * @param pid
	 * @param newPosition
	 * @return
	 */
	public boolean updateIcon(int pid, int newPosition){
		Piece p = gamePieces.get(pid);
		return updateIcon(p, newPosition);
	}
	
	public void changeIcon(int icon, int currR, int currC){
		//System.out.println("icon: "+icon+" currR: "+currR+" currC: "+currC);
		ImageIcon pass = imageIndex[icon];
		changeIcon(pass, currR, currC);
	}
	
	private void changeIcon(ImageIcon icon, int currR, int currC) {
		Component com;
		displayedBoard[currR][currC] = icon;
		com = this.getComponent(currR*displayedBoard.length+currC);

		if( com instanceof JLabel ){
			((JLabel) com).setIcon(displayedBoard[currR][currC]);
			com.repaint();
		}
	}
	
	private void clearPiece(GamePath gp, int pnum) {
		int currR = gp.getCurrentRow();
		int currC = gp.getCurrentCol();
		changeIcon(imageIndex[ basePaint[currR][currC] ], currR, currC);
		Pair<Integer,Integer> coords = gamePieces.get(pnum).specialCase(0);
		changeIcon(imageIndex[ basePaint[coords.first][coords.second] ], coords.first, coords.second);;
	}
	
	public boolean updateIcon(Piece p, int newposition, int r, int c){
		try{
			GamePath gp = p.getTravelPath();
			clearPiece(gp, p.getTeam());
			
			gp.setCurrentStep(newposition);
			int currR = r;
			int currC = c;
			
			changeIcon(p.getIcon(), currR, currC);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean updateIcon(Piece p, int newposition){
		try{
			GamePath gp = p.getTravelPath();
			clearPiece(gp, p.getTeam());
			
			gp.setCurrentStep(newposition);
			int currR = gp.getCurrentRow();
			int currC = gp.getCurrentCol();
			
			changeIcon(p.getIcon(), currR, currC);
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

	public int[][] getBasePaint(){
		return basePaint;
	}
	
	/**
	 * @return the playerCount
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int pc){
		playerCount = pc;
	}

	public String[] getPlayerIconPaths() {
		return playerIconPaths;
	}



	public void setPlayerIconPaths(String[] playerIconPaths) {
		this.playerIconPaths = playerIconPaths;
	}



	public int getBoardPixWidth() {
		return boardPixWidth;
	}



	public void setBoardPixWidth(int boardPixWidth) {
		this.boardPixWidth = boardPixWidth;
	}



	public int getBoardPixHeight() {
		return boardPixHeight;
	}



	public void setBoardPixHeight(int boardPixHeight) {
		this.boardPixHeight = boardPixHeight;
	}
}

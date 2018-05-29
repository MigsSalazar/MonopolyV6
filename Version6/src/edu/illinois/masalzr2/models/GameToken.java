package edu.illinois.masalzr2.models;

import java.awt.MediaTracker;
import java.io.File;

import javax.swing.ImageIcon;

public class GameToken {
	
	private String pieceDir;
	
	private ImageIcon piece;
	private int[] coords;
	
	private int team;
	
	private PositionIndex path;
	
	public GameToken(int t, String dir, PositionIndex p){
		
		team = t;
		
		piece = new ImageIcon();
		
		pieceDir = dir;
		
		File f = new File(dir);
		
		if(f.exists()){
			piece = new ImageIcon(f.getAbsolutePath());
		}
		
		path = p;
		
		coords = path.getCoords();
		
	}
	
	public int getX(){
		return coords[0];
	}
	
	public int getY(){
		return coords[1];
	}
	
	public void setTeam(int t){
		team = t;
	}
	
	public int getTeam(){
		return team;
	}
	
	public ImageIcon getPiece(){
		return piece;
	}
	
	public void movePiece(int m){
		if(path.isLocked()){
			return;
		}
		coords = path.move(m);
	}
	
	public PositionIndex getPath(){
		return path;
	}
	
	public boolean giveIconPath(String dir){
		piece = new ImageIcon(dir);
		return piece.getImageLoadStatus() == MediaTracker.COMPLETE;
	}
	
	public void refreshIcon(){
		refreshIcon(System.getProperty("user.dir"));
	}
	
	public void refreshIcon(String parentDir){
		File f = new File(parentDir + pieceDir);
		
		if(f.exists()){
			piece = new ImageIcon(f.getAbsolutePath());
		}else{
			piece = new ImageIcon(pieceDir);
		}
		
	}
	
	public int[] useSpecialtyCase(int s){
		coords = path.getSpecialCase(s);
		return coords;
	}
	
	public void setLocked(boolean l){
		path.setLocked(l);
	}
	
}

package edu.illinois.masalzr2.models;

import java.io.File;

import javax.swing.ImageIcon;

public class GameToken {
	
	private String pieceDir;
	
	private ImageIcon piece;
	private int[] coords;
	
	private int team;
	
	private TokenPath path;
	
	public GameToken(int t, String dir, TokenPath p){
		
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
	
	public int getTeam(){
		return team;
	}
	
	public ImageIcon getPiece(){
		return piece;
	}
	
	public void refreshIcon(String parentDir){
		File f = new File(parentDir + pieceDir);
		
		if(f.exists()){
			piece = new ImageIcon(f.getAbsolutePath());
		}else{
			piece = new ImageIcon(pieceDir);
		}
		
	}
	
}

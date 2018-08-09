package edu.illinois.masalzr2.models;

import java.awt.MediaTracker;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

import javax.swing.ImageIcon;

import com.google.gson.annotations.Expose;


public class GameToken implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose private String pieceDir;
	
	private transient ImageIcon piece;
	@Expose private int[] coords;
	
	@Expose private int team;
	
	@Expose private PositionIndex path;
	
	public GameToken(int t, String dir, PositionIndex p){
		
		team = t;
		
		piece = new ImageIcon();
		
		giveIconPath(dir);
		
		path = p;
		
		coords = path.getCoords();
		
	}
	
	
	public int getX(){
		return coords[1];
	}
	
	public int getY(){
		return coords[0];
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
	
	public String getPieceDir() {
		return pieceDir;
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
		pieceDir = dir;
		return piece.getImageLoadStatus() == MediaTracker.COMPLETE;
	}
	
	public void refreshIcon(){
		refreshIcon(System.getProperty("user.dir"));
	}
	
	public void refreshIcon(String parentDir){
		File f = new File(parentDir + File.separator + "textures" + File.separator + pieceDir);
		//System.out.println(parentDir + File.separator + "textures" + File.separator + pieceDir);
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
	
	public static Comparator<GameToken> getTeamComparator(){
		return new SortByTeam();
	}
	
	public static Comparator<GameToken> getIconComparator(){
		return new SortByIcon();
	}
	
	private static class SortByTeam implements Comparator<GameToken>{
		public int compare(GameToken g0, GameToken g1) {
			return g0.getTeam() - g1.getTeam();
		}
	}
	
	private static class SortByIcon implements Comparator<GameToken>{
		public int compare(GameToken o0, GameToken o1) {
			return o0.pieceDir.compareTo(o1.pieceDir);
		}
	}
	
}

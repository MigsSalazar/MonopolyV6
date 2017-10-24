package main.java.models;

import javax.swing.ImageIcon;

/**
 * @author Unknown
 *
 */
public class Piece {

	private ImageIcon icon;
	private Player owner;
	
	public Piece(ImageIcon i){
		icon = i;
	}
	
	public Piece(ImageIcon i, Player o){
		icon = i;
		owner = o;
	}
	
}

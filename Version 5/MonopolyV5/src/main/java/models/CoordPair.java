/**
 * 
 */
package main.java.models;

/**
 * @author Unknown
 *
 */
public class CoordPair {

	private int row;
	private int col;
	
	public CoordPair(){
		row = 0;
		col = 0;
	}
	
	public CoordPair(int r, int c){
		row = r;
		col = c;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
}

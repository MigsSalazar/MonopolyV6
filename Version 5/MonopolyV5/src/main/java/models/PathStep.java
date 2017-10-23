/**
 * 
 */
package main.java.models;

/**
 * @author Unknown
 *
 */
public class PathStep {

	private int row;
	private int col;
	
	public PathStep(){
		row = 0;
		col = 0;
	}
	
	public PathStep(int r, int c){
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

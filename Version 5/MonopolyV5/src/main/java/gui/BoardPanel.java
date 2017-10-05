package main.java.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.util.Arrays;

import com.google.gson.*;



/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {
	
	private GridLayout grid;
	private int rowNum;
	private int colNum;
	private JLabel[][] gridFillers;
	private ImageIcon[] images;
	
	/**
	 * Creates empty panel with default GridLayout
	 */
	public BoardPanel(){
		grid = new GridLayout();
		rowNum = grid.getRows();
		colNum = grid.getColumns();
		this.setLayout( grid );
		
		
		gridFillers = new JLabel[rowNum][colNum];
		gridFillers[0][0] = new JLabel("Empty graphics set");
		
		images = new ImageIcon[1];
		images[0] = new ImageIcon();
		
	}
	
	/**
	 * Resizes the number of rows in the GridLayout 
	 * @param r	positive integer to set rows
	 */
	public boolean setRow(int r){
		grid.setRows(r);
		return grid.getRows()==r;
	}
	
	/**
	 * Resizes the number of columns in the GridLayout
	 * @param c	positive integer to set columns
	 * @return	confirms if grid has been resized
	 */
	public boolean setCols(int c){
		grid.setColumns(c);
		return grid.getColumns()==c;
	}
	
	/**
	 * 
	 * @param r	positive integer to set rows
	 * @param c	positive integer to set columns
	 * @return	confirms if grid has been resized
	 */
	public boolean resizeGrid(int r, int c){
		grid.setColumns(c);
		grid.setRows(r);
		
		return (grid.getColumns()==c) && (grid.getRows()==r);
	}
	
	/**
	 * 
	 * @return	A copy of the 2D array of the same MxN as the board's grid containing the JLabels used to populate the board's grid with a 1 to 1 correlation
	 */
	public JLabel[][] getGridFillers(){
		return gridFillers==null ? null : Arrays.copyOf(gridFillers, gridFillers.length);
	}
	
	/**
	 * Populates gridFillers with the first element of images
	 */
	private void populateGridFillers(){
		JLabel[][] temp = gridFillers;
		gridFillers = new JLabel[rowNum][colNum];
		for(int r=0; r<rowNum; r++){
			for(int c=0; c<colNum; c++){
				gridFillers[r][c].setIcon(images[0]);
			}
		}
	}
	
	private void populateGridFillers(Gson directory){
		
		
		
	}
	
}





package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;



/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {
	
	private GridLayout grid;
	private int rowNum;
	private int colNum;
	private JLabel[][] gridFillers;
	
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
	 * @return	A two dimensional array of JLabels that populate the GridLayout
	 */
	public JLabel[][] getGridFillers(){
		return gridFillers;
	}
	
	private void populateGridFillers(){
		JLabel[][] temp = gridFillers;
		gridFillers = new JLabel[rowNum][colNum];
		
	}
	
}

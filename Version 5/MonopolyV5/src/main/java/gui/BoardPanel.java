/**
 * 
 */
package main.java.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.google.gson.annotations.Expose;

import main.java.models.GamePath;

/**
 * @author Miguel Salazar
 *
 */
public class BoardPanel extends JPanel {
	private transient ImageIcon[] imageIndex;
	@Expose private String[] iconPaths;
	@Expose private int[][] basePaint;
	@Expose private Stamp[][] stampCollection = new Stamp[30][30];
	@Expose private ArrayList<GamePath> paths = new ArrayList<GamePath>();
	@Expose private int width = 30;
	@Expose private int height = 30;
	private GridLayout grid = new GridLayout(width,height);
	private ArrayList<Piece> gamePieces;
	private transient ImageIcon[][] displayedBoard;
}

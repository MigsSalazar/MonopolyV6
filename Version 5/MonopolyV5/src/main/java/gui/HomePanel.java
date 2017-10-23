package main.java.gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3956960976009000851L;
	private BorderLayout homeLayout;
	private BoardPanel board = new BoardPanel();
	private StatsPanel stats = new StatsPanel();
	private EventPanel events = new EventPanel();
	
	public HomePanel(){
		homeLayout = new BorderLayout();
		setLayout(homeLayout);
		this.add(board, BorderLayout.CENTER);
		this.add(stats, BorderLayout.EAST);
		this.add(events, BorderLayout.SOUTH);
	}
}

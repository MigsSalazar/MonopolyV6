package java.skinedit.guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Preload {
	
	private File srcdir = new File(System.getProperty("user.dir"));
	
	private JFrame mainFrame;
	private JPanel recents;
	private JPanel options;
	
	public Preload(){
		mainFrame = new JFrame("Migs Monopoly Skin Editor");
		
		mainFrame.setLayout(new BorderLayout());
		
		mainFrame.setPreferredSize(new Dimension(700,700));
		
		
		
		recents = generateRecents();
		options = generateOptions();
		
		mainFrame.add(recents, BorderLayout.WEST);
		mainFrame.add(options, BorderLayout.EAST);
	}
	
	public JPanel generateRecents(File source){
		JPanel retval = new JPanel();
		return retval;
	}
	
	private JPanel generateRecents(){
		return generateRecents(srcdir);
	}
	
	
	private JPanel generateOptions(){
		JPanel retval = new JPanel();
		return retval;
	}
	
	
}

package edu.illinois.masalzr2.gui;

import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.List;
import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;

public class NewGameSetup {
	
	private File compressed;
	private String sep = File.separator;
	
	private Map<String, List<?>> returns;
	private JDialog control;
	private JPanel panel;
	
	private JPanel topPanel;
	private JPanel textureDetails;
	private JPanel playerNumbers;
	
	public NewGameSetup(JFrame parent, File texture) {
		
		if(texture.exists()) {
			compressed = texture;
		}else {
			compressed = new File(System.getProperty("user.dir")+sep+"textures"+sep+"default");
		}
		
		returns = new HashMap<String, List<?>>();
		
		control = new JDialog(parent, "Start New Game!", true);
		panel = new JPanel();
		control.add(panel);
		
		
		
	}
	
	private void buildTopPanel() {
		
		topPanel = new JPanel(new BorderLayout());
		JLabel text = new JLabel("Texture");
		textureDetails = new JPanel(new BorderLayout());
		
		
		
	}
	
}

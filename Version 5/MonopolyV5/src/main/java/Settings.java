/**
 * 
 */
package main.java;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.java.listeners.FullScreenActionListener;
import main.java.listeners.ResolutionActionListener;
import main.java.listeners.TexturePackActionListener;

/**
 * @author Unknown
 *
 */
public class Settings extends JDialog {
	
	private JRadioButton fullScreen;
	private JComboBox<String> resolution;
	private JButton textureFinder;
	private JPanel innerPanel;
	
	public Settings(){
		 
		innerPanel = new JPanel();
		
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		
		createFullScreen();
		createResolution();
		createTextureFinder();
		/*
		 * remake this for a grid panel with labels.
		 * its really nasty just having the components
		 * 
		innerPanel.add(Box.createHorizontalGlue());
		innerPanel.add(fullScreen);
		innerPanel.add(Box.createHorizontalGlue());
		innerPanel.add(resolution);
		innerPanel.add(Box.createHorizontalGlue());
		innerPanel.add(textureFinder);
		*/
		this.add(innerPanel);
		
		this.setSize(200, 200);
		this.setResizable(false);
		this.setTitle("Settings");
		
		Image gear = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/smallGear.png").getImage(); 
		
		this.setIconImage(gear);
		this.setVisible(true);

	}
	
	private void createFullScreen(){
		fullScreen = new JRadioButton();
		fullScreen.setMaximumSize(new Dimension(100,50));
		fullScreen.setText("Full Screen");
		fullScreen.setSize(100, 25);
		fullScreen.addActionListener(new FullScreenActionListener());
	}
	
	private void createResolution(){
		String[] resChoices = new String[5];
		for(int i=0; i<resChoices.length; i++){
			resChoices[i] = "#### x ###"+i;
		}
		resolution = new JComboBox<String>(resChoices);
		resolution.setMaximumSize(new Dimension(100,25));
		resolution.addActionListener(new ResolutionActionListener());
		
	}
	
	private void createTextureFinder(){
		textureFinder = new JButton("Texture Pack");
		textureFinder.setMaximumSize(new Dimension(100,25));
		textureFinder.addActionListener(new TexturePackActionListener());
		
	}
}

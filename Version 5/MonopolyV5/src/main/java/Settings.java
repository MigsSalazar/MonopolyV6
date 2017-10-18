/**
 * 
 */
package main.java;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
	
	public Settings(){
		
		this.setLayout(new GridLayout(3,1));
		
		createFullScreen();
		createResolution();
		createTextureFinder();

		this.add(fullScreen);
		this.add(resolution);
		this.add(textureFinder);
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
		fullScreen.setText("Full Screen   ");
		
		fullScreen.addActionListener(new FullScreenActionListener());
	}
	
	private void createResolution(){
		String[] resChoices = new String[5];
		for(int i=0; i<resChoices.length; i++){
			resChoices[i] = "#### x ###"+i;
		}
		resolution = new JComboBox<String>(resChoices);
		resolution.setMaximumSize(new Dimension(100,50));
		resolution.addActionListener(new ResolutionActionListener());
		
	}
	
	private void createTextureFinder(){
		textureFinder = new JButton("Texture Pack");
		textureFinder.setMaximumSize(new Dimension(100,50));
		textureFinder.addActionListener(new TexturePackActionListener());
		
	}
}

/**
 * 
 */
package edu.illinois.masalzr2.gui;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.GameVariables;

import javax.swing.JButton;


/**
 * @author Miguel Salazar
 *
 */
public class PreGameFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7859011893692247775L;
	private JPanel innerPanel = new JPanel();
	private ImageIcon picture;
	private JButton newGame;
	private JButton loadGame;
	private JButton settings;
	private JButton about;

	public PreGameFrame(){
		//c.setLayout(box);
		picture = new ImageIcon(System.getProperty("user.dir")+"/resources/topintroimage.png" );
		newGame = new JButton("New Game");
		loadGame = new JButton("Load Game");
		settings = new JButton("Settings");
		about = new JButton("About");
		
		
		addListeners();
		JLabel pictureLabel = new JLabel(picture);
		innerPanel.add("picture", pictureLabel);
		innerPanel.add("new game button", newGame);
		innerPanel.add("load game button", loadGame);
		innerPanel.add("settings button", settings);
		innerPanel.add("about button", about);
	}
	public void start(){
		Container c = this.getContentPane();
		c.add(innerPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420,480);
		this.setTitle("Migs Monopoly!");
		System.out.println(System.getProperty("user.dir")+"/resources/frameicon.png");
		Image icon = new ImageIcon(System.getProperty("user.dir")+"/resources/frameicon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);
	}
	
	
	private void addListeners(){
		newGame.addActionListener(this);
		
		loadGame.addActionListener(this);
		
		settings.addActionListener(this);
		
		about.addActionListener(this);
	}
	
	private void closeMe(){
		this.dispose();
	}
	
	private void badFile() {
		JOptionPane.showMessageDialog(this, "The save file you entered is invalid.\nIt is either out of date, corrupted,\nor is not a save file at all.", "Bad file", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(newGame)){
			GameVariables newerGame = GameIo.newGame();
			if(newerGame !=null) {
				newerGame.buildFrame();
				closeMe();
			}else {
				badFile();
			}
		}else if(e.getSource().equals(loadGame)){
			String dir = GameIo.findGame(this);
			if(dir==null) {
				return;
			}
			GameVariables loadedGame = GameIo.produceSavedGame(dir);
			if(loadedGame !=null) {
				loadedGame.buildFrame();
				closeMe();
			}else {
				badFile();
			}
		}
		
	}
}

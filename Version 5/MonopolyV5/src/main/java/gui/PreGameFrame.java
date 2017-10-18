/**
 * 
 */
package main.java.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import main.java.*;

/**
 * @author Miguel Salazar
 *
 */
public class PreGameFrame extends JFrame {
	
	private Main mainCaller = new Main();
	private JPanel innerPanel = new JPanel();
	private BoxLayout box;
	ImageIcon picture;
	JButton newGame;
	JButton loadGame;
	JButton settings;
	JButton about;

	public PreGameFrame(){
		Container c = this.getContentPane();
		box = new BoxLayout(innerPanel, BoxLayout.Y_AXIS);
		//c.setLayout(box);
		picture = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/topintroimage.png" );
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

		c.add(innerPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420,500);
		this.setTitle("Migs Monopoly!");
		this.setVisible(true);
	}
	
	
	private void addListeners(){
		newGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainCaller.startNewGame();
				closeMe();
			}
		});
		
		loadGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainCaller.startSaveGame("");
				closeMe();
			}
		});
		
		settings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainCaller.settingsLaunch();
			}
		});
		
		about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mainCaller.aboutThis();
			}
		});
	}
	
	private void closeMe(){
		this.dispose();
	}
}

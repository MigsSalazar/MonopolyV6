/**
 * 
 */
package main.java.gui;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import main.java.Main;
import main.java.action.Runner;
import main.java.action.Settings;

/**
 * @author Miguel Salazar
 *
 */
public class PreGameFrame extends JFrame {
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
	private Settings sets;

	public PreGameFrame(){
		sets = new Settings(this);
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
	}
	public void start(){
		Container c = this.getContentPane();
		c.add(innerPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420,480);
		this.setTitle("Migs Monopoly!");
		Image icon = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/frameicon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);
	}
	
	
	private void addListeners(){
		newGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Runner newGame = new Runner();
				if(newGame.startNewGame()){
					closeMe();
				}
			}
		});
		
		loadGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Runner oldGame = new Runner();
				if(oldGame.startSavedGame()){
					closeMe();
				}
			}
		});
		
		settings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sets.setup();
			}
		});
		
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Runner.aboutThis();
			}
		});
	}
	
	private void closeMe(){
		this.dispose();
	}
}

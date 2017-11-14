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
import main.java.listeners.AboutActionListener;
import main.java.listeners.LoadActionListener;
import main.java.listeners.NewActionListener;

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
	ImageIcon picture;
	JButton newGame;
	JButton loadGame;
	JButton settings;
	JButton about;
	PreGameFrame me = this;

	public PreGameFrame(){
		
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(420,480);
		this.setTitle("Migs Monopoly!");
		Image icon = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/frameicon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);
	}
	
	
	private void addListeners(){
		newGame.addActionListener(new NewActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				super.actionPerformed(e);
				closeMe();
			}
		});
		
		loadGame.addActionListener(new LoadActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				super.actionPerformed(e);
				closeMe();
			}
		});
		
		settings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Main.settingsLaunch(me);
			}
		});
		
		about.addActionListener(new AboutActionListener());
	}
	
	private void closeMe(){
		this.dispose();
	}
}

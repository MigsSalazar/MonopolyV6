/**
 * 
 */
 

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		newGame.addActionListener(this);
		
		loadGame.addActionListener(this);
		
		settings.addActionListener(this);
		
		about.addActionListener(this);
	}
	
	private void closeMe(){
		this.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(newGame)){
			Runner newGame = new Runner();
			if(newGame.startNewGame(sets)){
				closeMe();
			}
		}else if(e.getSource().equals(loadGame)){
			Runner oldGame = new Runner();
			if(oldGame.startSavedGame()){
				closeMe();
			}
		}else if(e.getSource().equals(settings)){
			sets.setup();
		}else if(e.getSource().equals(about)){
			Runner.aboutThis();
		}
		
	}
}

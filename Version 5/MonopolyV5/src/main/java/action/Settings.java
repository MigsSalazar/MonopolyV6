/**
 * 
 */
package main.java.action;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * @author Miguel Salazar
 *
 */
public class Settings extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8037425504189246209L;
	private JComboBox<String> resolution;
	private JButton textureFinder;
	private JPanel innerPanel;
	private JPanel outerPanel;
	
	private String texturePath = System.getProperty("user.dir");
	
	public Settings(JFrame parent){
		super(parent, true);
	}
		 	
	public void setup(){
		innerPanel = new JPanel();
		
		innerPanel.setLayout(new GridLayout(1,2));
		outerPanel = new JPanel(new GridLayout(4,3) );
		
		createResolution();
		createTextureFinder();
		
		JLabel resolutionLabel = new JLabel("Resolution");
		
		//( (GridLayout)innerPanel.getLayout() ).setVgap(20);
		
		( (GridLayout)outerPanel.getLayout() ).setVgap(10);
		//( (GridLayout)outerPanel.getLayout() ).setHgap(20);
		
		innerPanel.add(resolutionLabel);
		innerPanel.add(resolution);
		
		JLabel topTitle = new JLabel("Settings");
		
		topTitle.setHorizontalAlignment(JLabel.CENTER);
		textureFinder.setHorizontalAlignment(JButton.CENTER);
		
		outerPanel.add(topTitle);
		outerPanel.add(innerPanel);
		outerPanel.add(textureFinder);
		
		this.add(outerPanel);
		this.setBounds(50, 50, 200, 200);
		this.setResizable(false);
		this.setTitle("Settings");
		
		Image gear = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/smallGear.png").getImage(); 
		
		this.setIconImage(gear);

	}
	
	
	private void createResolution(){
		String[] resChoices = new String[5];
		for(int i=0; i<resChoices.length; i++){
			resChoices[i] = "#### x ###"+i;
		}
		resolution = new JComboBox<String>(resChoices);
		resolution.setMaximumSize(new Dimension(75,25));
		resolution.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
	}
	
	private void createTextureFinder(){
		textureFinder = new JButton("Texture Pack");
		textureFinder.setMaximumSize(new Dimension(75,25));
		textureFinder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
	}
	
	public String textureMe(){
		//TODO make this a file chooser that allows you to pick your texture pack
		return texturePath;
	}
	
}

/**
 * 
 */
package main.java.gui;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author Miguel Salazar
 *
 */
public class PreGameFrame extends JFrame {
	
	private BoxLayout box;
	
	public PreGameFrame(){
		Container c = this.getContentPane();
		box = new BoxLayout(c, BoxLayout.Y_AXIS);
		ImageIcon picture = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/topintroimage.png" );
	}
	
}

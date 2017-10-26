/**
 * 
 */
package main.java.models;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * @author Miguel Salazar
 * Hopefully this is a serializable imageIcon
 */
public class Tile extends ImageIcon {
	private File path;
	
	public Tile(String p){
		super(p);
	}
	
	public void updateImage(){
		Tile newMe = this;
		newMe = new Tile(path.getPath());
	}
	
}

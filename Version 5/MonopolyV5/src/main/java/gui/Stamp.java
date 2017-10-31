/**
 * 
 */
package main.java.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * @author Unknown
 *
 */
public class Stamp {
	private int rpos;
	private int cpos;
	private char engraving;
	private byte italic = 0;
	private byte bold = 0;
	private byte underline = 0;
	private int fontsize;
	
	/**
	 * 1st	 2nd	3rd		4th
	 * top	 right	bottom	left
	 */
	private byte border;
	
	
	
	
	public void giveBorder(/*JLabel label*/){
		border = 		(byte)1111;
		byte top = 		(byte)0000;
		byte right = 	(byte)0100;
		byte bottom = 	(byte)0010;
		byte left = 	(byte)0001;

		System.out.println(border);
		if((border & top) == top){
			System.out.println("top");
		}
		if((border & right) == right){
			System.out.println("right");
		}
		if((border & bottom) == bottom){
			System.out.println("bottom");
		}
		if((border & left) == left){
			System.out.println("left");
		}
		
		
		/*label.setBorder(BorderFactory.createMatteBorder((border>>0&1),
														(border>>1&1),
														(border>>2&1),
														(border>>3&1),
														Color.BLACK));
														*/
	}
}

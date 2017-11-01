/**
 * 
 */
package main.java.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * @author Unknown
 *
 */
public class Stamp {
	private char engraving;
	private boolean italics;
	private boolean bold;
	private boolean underline;
	private int border;
	
	
	public Stamp(){
		engraving = ' ';
		italics = false;
		bold = false;
		underline = false;
		border = 1;
	}
	
	public Stamp(char e){
		engraving = e;
		
		italics = false;
		bold = false;
		underline = false;
		border = 1;
		
	}
	
	public Stamp(char e, boolean i, boolean b, boolean u, int bint){
		engraving = e;
		italics = i;
		bold = b;
		underline = u;
		border = bint;
		validateBorder();
	}
	
	public boolean validateBorder(){
		if( (border%2==0 || border%3==0 || border%5==0 || border%7==0) && border<=210 ){
			return true;
		}else{
			border = 1;
			return false;
		}
	}
	
	
	public void engraveLabel(JLabel label){
		String engraved = ""+engraving;
		
		italicize(engraved);
		bolden(engraved);
		underlined(engraved);
		
		engraved = "<html>"+engraved+"</html>";
		
		label.setText(engraved);
		
	}
	
	private String underlined(String e){
		if(underline){
			e = "<u>"+e+"</u>";
		}
		return e;
	}
	
	private String italicize(String e){
		if(italics){
			e = "<i>"+e+"</i>";
		}
		return e;
	}
	
	private String bolden(String e){
		if(bold){
			e = "<b>"+e+"</b>";
		}
		return e;
	}
	
	public void giveBorder(JLabel label){
		Border given = BorderFactory.createMatteBorder(hasTop(), hasRight(), hasBottom(), hasLeft(), Color.BLACK);
		label.setBorder(given);
	}
	
	private int hasTop(){
		 validateBorder();
		if(border%2 == 0){
			return 1;
		}
		return 0;
	}
	
	private int hasRight(){
		 validateBorder();
		if(border%3 == 0){
			return 1;
		}
		return 0;
	}
	
	private int hasBottom(){
		 validateBorder();
		if(border%5 == 0){
			return 1;
		}
		return 0;
	}
	
	private int hasLeft(){
		 validateBorder();
		if(border%7 == 0){
			return 1;
		}
		return 0;
	}

	public char getEngraving() {
		return engraving;
	}

	public void setEngraving(char engraving) {
		this.engraving = engraving;
	}

	public boolean isItalics() {
		return italics;
	}

	public void setItalics(boolean italics) {
		this.italics = italics;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
		validateBorder();
	}
	
	
}

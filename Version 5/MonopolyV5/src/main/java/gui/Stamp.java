/**
 * 
 */
package main.java.gui;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.google.gson.annotations.Expose;

/**
 * @author Unknown
 *
 */
public class Stamp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Expose private char engraving;
	@Expose private boolean italics;
	@Expose private boolean bold;
	@Expose private boolean underline;
	@Expose private int border;
	
	
	public Stamp(){
		engraving = ' ';
		italics = false;
		bold = true;
		underline = false;
		border = 1;
	}
	
	public Stamp(int b){
		border =1;
		if(validateBorder(b)){
			border = b;
		}
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
		if(!validateBorder(border)){
			border = 1;
		}
	}
	
	public static boolean validateBorder(int b){
		if(b == 1){
			return true;
		}else if( (b%2==0 || b%3==0 || b%5==0 || b%7==0) && b<=210 ){
			return true;
		}else{
			return false;
		}
	}
	
	public void engraveLabel(JLabel label){
		String engraved = engraveComponent();
		label.setText(""+engraved);
		if( engraving != ' '){
			//System.out.println("engraving = "+engraved);
			label.setText(engraved);
			
			//label.setHorizontalAlignment(JLabel.CENTER);
			
			//label.setVerticalAlignment(JLabel.CENTER);
		}
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		
	}
	
	public void engraveButton(JButton button){
		String engraved = engraveComponent();
		button.setText(""+engraved);
		if( engraving != ' '){
			//System.out.println("engraving = "+engraved);
			button.setText(engraved);
			
			//label.setHorizontalAlignment(JLabel.CENTER);
			
			//label.setVerticalAlignment(JLabel.CENTER);
		}
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);
	}

	private String engraveComponent() {
		String engraved = ""+engraving;
		
		italicize(engraved);
		bolden(engraved);
		underlined(engraved);
		engraved = "<html>"+engraved+"</html>";
		
		return engraved;
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
		Border given = makeBorder();
		label.setBorder(given);
	}
	
	public Border makeBorder(){
		return makeBorder(border);
	}
	
	public static Border makeBorder(int b){
		if(validateBorder(b)){
			return BorderFactory.createMatteBorder(hasTop(b), hasLeft(b), hasBottom(b), hasRight(b), Color.BLACK);
		}else{
			return BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
		}
	}
	
	
	
	private static int hasTop(int b){
		if(b%2 == 0){
			return 1;
		}
		return 0;
	}
	
	private static int hasRight(int b){
		if(b%3 == 0){
			return 1;
		}
		return 0;
	}
	
	private static int hasBottom(int b){
		if(b%5 == 0){
			return 1;
		}
		return 0;
	}
	
	private static int hasLeft(int b){
		if(b%7 == 0){
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

	public void setBorder(int b) {
		if(validateBorder(b)){
			border = b;
		}
	}
	
	@Override
	public String toString(){
		
		/*
		 * @Expose private char engraving;
	@Expose private boolean italics;
	@Expose private boolean bold;
	@Expose private boolean underline;
	@Expose private int border;
		 */
		return new StringBuffer("Engraving: ").append(engraving)
				.append("Italics: ").append(italics)
				.append("Bold: ").append(bold)
				.append("Underline: ").append(underline)
				.append("Border: ").append(border).toString();
	}
	
	
}

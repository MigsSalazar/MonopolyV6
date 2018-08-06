package edu.illinois.masalzr2.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import edu.illinois.masalzr2.gui.Stamp;
import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.models.Counter;
import edu.illinois.masalzr2.models.Dice;
import edu.illinois.masalzr2.models.GameCard;
import edu.illinois.masalzr2.models.GameToken;
import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.models.PositionIndex;
import edu.illinois.masalzr2.models.Property;
import edu.illinois.masalzr2.models.Railroad;
import edu.illinois.masalzr2.models.Street;
import edu.illinois.masalzr2.models.Suite;
import edu.illinois.masalzr2.models.Utility;

public class TemplateGameVars{
	
	public static String sep = java.io.File.separator;
	private static JProgressBar progress = new JProgressBar(0,120);
	private static JTextArea updates = new JTextArea();
	
	public static void main(String[] args) {
		produceTemplate();
		//System.out.println("Template generation complete");
	}
	
	public static void produceTemplate() {
		JDialog progPanel = new JDialog((JFrame)null, "Template Generation", false);
		progPanel.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		progPanel.setLayout(new BorderLayout());
		//progress = new JProgressBar(0,120);
		updates.setPreferredSize(new Dimension(300,300));
		progress.setValue(0);
		progPanel.add(progress, BorderLayout.NORTH);
		progPanel.add(updates, BorderLayout.CENTER);
		progPanel.pack();
		progPanel.setVisible(true);
		GameVariables gv = new GameVariables();
		
		gv.buildCleanGame();
		
		GameIo.writeOut(gv);
		updateProgress(120, "Template generation complete!\nPlease close me out.");
		//gv.buildFrame();
		
		//System.out.println("I'm done");
		//progPanel.dispose();
		LogMate.LOG.newEntry("Finished template!");
		LogMate.LOG.flush();
		LogMate.LOG.finish();
	}
	
	private static void updateProgress(int value, String output){
		if (progress != null){
			progress.setValue(value);
		}
		if(updates != null) {
			updates.append(output+"\n");
		}
	}
	
	public static HashMap<String, Player> definePlayers(){
		HashMap<String, Player> retval = new HashMap<String, Player>();
		
		Player noob;
		for(int i=0; i<8; i++) {
			noob = new Player(""+i, i, 1500, 0, 0, false, new HashMap<String,Property>(), null);
			retval.put(noob.getName(), noob);
		}
		
		return retval;
	}
	
	
	public static int[][] stickerBook(){
		int[][] temp = {{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //-1
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //1
						{-1,-1,-1,13,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //2
						{-1,-1,-1,14,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //3
						{-1,-1,-1,-1,-1,-1,	-1,-1,9,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,5,5,-1,-1,		-1,-1,-1,-1,-1,-1}, //4
						{-1,-1,-1,-1,-1,-1,	-1,-1,9,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,5,5,-1,-1,		-1,-1,-1,-1,-1,-1}, //5
						
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //6
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //7
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //8
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //9
						{-1,-1,-1,-1,7,7,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	7,7,-1,-1,-1,-1}, //1-1
						{-1,-1,-1,-1,8,8,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	8,8,-1,-1,-1,-1}, //11
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //12
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //13
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //14
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //15
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	9,9,-1,-1,-1,-1}, //16
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	9,9,-1,-1,-1,-1}, //17
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //18
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //19
						{-1,-1,-1,-1,4,4,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //2-1
						{-1,-1,-1,-1,4,4,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //21
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //22
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //23
							
						{-1,6,6,6,6,6,		-1,-1,-1,-1,9,9,-1,-1,-1,-1,-1,-1,-1,-1,7,7,-1,-1,		-1,-1,-1,-1,-1,-1}, //24
						{-1,6,-1,-1,-1,6,	-1,-1,-1,-1,9,9,-1,-1,-1,-1,-1,-1,-1,-1,8,8,-1,-1,		-1,-1,-1,-1,-1,-1}, //25
						{-1,6,-1,-1,-1,6,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //26
						{-1,6,-1,-1,-1,6,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,10,-1,-1,-1,-1}, //27
						{-1,6,6,6,6,6,		-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	11,-1,-1,-1,-1,-1}, //28
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,12,-1,-1,-1,-1}};//29
		
		updateProgress(100, "Sticker book has been generated");
		
		return temp;
	}

	public static String[] getStickers(){
		String[] stickers = {	"housing.png",				//0
								"hotelLeft.png",				//1
								"hotelRight.png",				//2
								"hotelBottom.png",				//3
								
								"eleccomp.png",				//4
								"waterworks.png",			//5
								
								"jail.png",					//6
								"chesttop.png",				//7
								"chestbottom.png",			//8
								"chance.png",				//9
								
								"gotop.png",				//10
								"gomid.png",				//11
								"gobot.png",				//12
								
								"parktop.png",				//13
								"parkbot.png"};				//14
		
		//String dir = System.getProperty("user.dir");
		for(int i=0; i<stickers.length; i++){
			stickers[i] = sep+"textures"+sep+"default"+sep+stickers[i];
			//System.out.println(retval[i].getDescription());
		}
		
		updateProgress(110, "Stickers have been generated");
		
		return stickers;
	}
	
	public static Stamp[][] defineStamps(){
		Stamp[][] sc = { {
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp('F',false,true,false,1),
		new Stamp('R',false,true,false,1),
		new Stamp('E',false,true,false,1),
		new Stamp('E',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp('G',false,true,false,1),
		new Stamp('O',false,true,false,1),
		new Stamp('T',false,true,false,1),
		new Stamp('O',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp('B',false,true,false,7),
		new Stamp('O',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp('J',false,true,false,1),
		new Stamp('A',false,true,false,1),
		new Stamp('I',false,true,false,1),
		new Stamp('L',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp('R',false,true,false,35),
		new Stamp('R',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp('P',false,true,false,2),
		new Stamp('N',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp('S',false,true,false,14),
		new Stamp('L',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp('R',false,true,false,5),
		new Stamp('R',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp('L',false,true,false,14),
		new Stamp('X',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp('T',false,true,false,35),
		new Stamp('X',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		},
		{
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp('R',false,true,false,14),
		new Stamp('E',false,true,false,6),
		new Stamp('I',false,true,false,14),
		new Stamp('C',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,2),
		new Stamp(' ',false,true,false,6)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp('R',false,true,false,7),
		new Stamp('R',false,true,false,3),
		new Stamp('T',false,true,false,7),
		new Stamp('X',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,14),
		new Stamp(' ',false,true,false,6),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp('G',false,true,false,1),
		new Stamp('O',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,3),
		new Stamp(' ',false,true,false,7),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,1),
		new Stamp(' ',false,true,false,3)
		},
		{
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,15),
		new Stamp(' ',false,true,false,35),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,5),
		new Stamp(' ',false,true,false,15)
		}
		};
		
		updateProgress(90, "Stickers have been generated");
		
		return sc;

	}

	public static ArrayList<GameCard> defineCommChest() {
		ArrayList<GameCard> commchest = new ArrayList<GameCard>();
		
		commchest.add(new GameCard("<html>Advance to Go!"
								+ "<br>(Collect $200)</html>",
									0, false,0,false,false,"","go",false, 0, 0));
		commchest.add(new GameCard("<html>Bank error in your favor"
								+ "<br>Collect $200</html>",
				200, false,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>Doctor's fees."
								+ "<br>Pay $50</html>",
				-50, false,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>From sale of stock"
								+ "<br>you get $50</html>",
				50, false,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>Get out of jail free!"
								+ "<br>This card may be kept until needed,"
								+ "<br>or traded/sold.</html>",
				0, false,0,true,false,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>Go to directly to Jail"
				+				 "<br>Do not pass Go - Do not collect $200.</html>",
				0, false,0,false,true,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>Grand Opera Night!"
								+ "<br>Collect $50 from every player"
								+ "<br>foropening night seats.</html>",
				50,true,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>Holiday Fund matures!"
								+ "<br>Receive $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>Income tax refund!"
								+ "<br>Collect $20</html>",
				20, false,0,false,false,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>It is your birthday"
								+ "<br>Collect $10!</html>",
				10, false,0,false,false,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>Life insurance matures!"
								+ "<br>Collect $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>Pay hospital fees of $100</html>",
				-100, false,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>Pay school fees of $150</html>",
				-150, false,0,false,false,"","",false, 0, 0));
		
		commchest.add(new GameCard("<html>Recieve $25 consultancy fee</html>",
				25, false,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>You are assessed for street repairs-"
								+ "<br>$40 per house"
								+ "<br>$115 per hotel</html>",
				0, false,0,false,false,"","",true, 40,115));
		
		commchest.add(new GameCard("<html>You have won second prize"
								+ "<br>in a beauty contest!"
								+ "<br>Collect $10</html>",
				10, false,0,false,false,"","",false, 0, 0));
		commchest.add(new GameCard("<html>You inherit $100</html>",
				100, false,0,false,false,"","",false, 0, 0));
		
		updateProgress(70, "Community Chest cards have been generated");
		
		return commchest;
	}
	
	public static ArrayList<GameCard> defineChance() {
		ArrayList<GameCard> chance = new ArrayList<GameCard>();
		chance.add(new GameCard("<html>Advance to Go!<br>(Collect $200)</html>",
				0, false,0,false,false,"",
				"go",
				false, 0, 0));
		chance.add(new GameCard("<html>Advance to Illinois Ave.!<br>(If you ass Go, collect $200)</html>",
				0, false,0,false,false,"",
				"Illinois Ave.",
				false, 0, 0));
		chance.add(new GameCard("<html>Advance token to the nearest Utility"
									+ "<br>If unowned, you may buy it from the bank."
									+ "<br>If owned, throw the dice and pay the owner"
									+ "<br>ten times the amount thrown</html>",
				0, false,0,false,false,
				"utility",
				"",false, 0, 0));
		chance.add(new GameCard("<html>Bank pays you dividends of $50</html>",
				50,
				false,0,false,false,"","",false, 0, 0));
		chance.add(new GameCard("<html>Get out of jail free!"
									+ "<br>(This card may be kept until needed,"
									+ "<br>or traded/sold)</html>",
				0, false,0,true,false,"","",false, 0, 0));
		chance.add(new GameCard("<html>Go back 3 spaces</html>",
				0, false,-3,false,false,"","",false, 0, 0));
		
		chance.add(new GameCard("<html>Go directly to jail."
									+ "<br>Do not pass go,"
									+ "<br>do not collect $200</html>",
				0, false,0,false,true,"","",false, 0, 0));
		
		chance.add(new GameCard("<html>Make general repairs on all your property"
									+ "<br>For each house pay $25"
									+ "<br>For each hotel pay $100</html>",
				0, false,0,false,false,"","",true, 25, 100));
		
		chance.add(new GameCard("<html>Pay poor tax of $15</html>",
				-15, false,0,false,false,"","",false, 0, 0));
		
		chance.add(new GameCard("<html>Take a trip to Reading Railraod</html>",
				0, false,0,false,false,"","Reading Railroad",false, 0, 0));
		
		chance.add(new GameCard("<html>Advance token to Boardwalk</html>",
				0, false,0,false,false,"","Board Walk",false, 0, 0));
		
		chance.add(new GameCard("<html>You have been elected Chairman"
									+ "<br>of the Board, Pay each player $50</html>",
				-50, true,0,false,false,"","",false, 0, 0));
		
		chance.add(new GameCard("<html>You building and loan matures"
									+ "<br>Collect $150</html>",
				150, false,0,false,false,"","",false, 0, 0));
		
		updateProgress(80, "Chance cards have been generated");
		
		return chance;
	}
	
	public static HashMap<String, Suite> defineSuites(Map<String, Property> properties) {
		HashMap<String, Suite>suites = new HashMap<String, Suite>();
		
		suites.put("purple", 	new Suite((Street)properties.get("Mediterranean Ave."), (Street)properties.get("Baltic Ave."), 			null, 											"purple", 	Color.MAGENTA.getRGB()));
		suites.put("lightBlue", new Suite((Street)properties.get("Oriental Ave."), 		(Street)properties.get("Vermont Ave."), 		(Street)properties.get("Connecticut Ave."), 	"lightBlue",Color.CYAN.getRGB()));
		suites.put("pink", 		new Suite((Street)properties.get("St. Charles Place"), 	(Street)properties.get("States Ave."), 			(Street)properties.get("Virginia Ave."), 		"pink", 	Color.PINK.getRGB()));
		suites.put("orange", 	new Suite((Street)properties.get("St. James Place"), 	(Street)properties.get("Tennessee Ave."), 		(Street)properties.get("New York Ave."), 		"orange", 	Color.ORANGE.getRGB()));
		suites.put("red", 		new Suite((Street)properties.get("Kentucky Ave."), 		(Street)properties.get("Indiana Ave."), 		(Street)properties.get("Illinois Ave."), 		"red", 		Color.RED.getRGB()));
		suites.put("yellow", 	new Suite((Street)properties.get("Atlantic Ave."), 		(Street)properties.get("Ventnor Ave."), 		(Street)properties.get("Marvin Gardens"), 		"yellow", 	Color.YELLOW.getRGB()));
		suites.put("green", 	new Suite((Street)properties.get("Pacific Ave."), 		(Street)properties.get("North Carolina Ave."), 	(Street)properties.get("Pennsylvania Ave."), 	"green", 	Color.GREEN.getRGB()));
		suites.put("blue", 		new Suite((Street)properties.get("Park Place"), 		(Street)properties.get("Board Walk"), 			null, 											"blue", 	Color.BLUE.getRGB()));
		updateProgress(20, "Suites have been generated");
		return suites;
	}
	
	public static int[][] definePaintByNumbers(){
		int[][] pbn = 
				{{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //0
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //1
				{0,0,7,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //2
				{0,0,7,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //3
				{0,0,7,0,0,0,		7,7,0,0,7,7,7,7,0,0,8,8,8,8,0,0,8,8,		0,0,0,0,0,0}, //4
				{0,0,7,0,0,0,		7,7,0,0,7,7,7,7,0,0,8,8,8,8,0,0,8,8,		0,0,0,0,0,0}, //5
				
				{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //6
				{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //7
				{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //8
				{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //9
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //10
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //11
				{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //12
				{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //13
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //14
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //15
				{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //16
				{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //17
				{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //18
				{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //19
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //20
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //21
				{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //22
				{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //23
					
				{0,0,0,0,0,0,		4,4,4,4,0,0,4,4,0,0,0,0,3,3,0,0,3,3,		0,0,0,0,0,0}, //24
				{0,0,11,12,13,0,	4,4,4,4,0,0,4,4,0,0,0,0,3,3,0,0,3,3,		0,0,0,0,0,0}, //25
				{0,0,14,15,16,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //26
				{0,0,17,18,19,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //27
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,7,7,7,7,7}, //28
				{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}};//29
		
		updateProgress(60, "Board Paint numbers have been generated");
		
		return pbn;
	}
	
	public static String[] defineIcons(){
		String[] icons = {	"baseboard.png",//0
				"dotdie.png",				//1
				"blankdie.png",				//2
				
				"purple.png",				//3
				"lightblue.png",			//4
				"pink.png",					//5
				"orange.png",				//6
				"red.png",					//7
				"yellow.png",				//8
				"green.png",				//9
				"blue.png",					//10
				"jail0.png",				//11
				"jail1.png",				//12
				"jail2.png",				//13
				"jail3.png",				//14
				"jail4.png",				//15
				"jail5.png",				//16
				"jail6.png",				//17
				"jail7.png",				//18
				"jail8.png"};				//19
		for(int i=0; i<icons.length; i++){
			icons[i] = sep+"textures"+sep+"default"+sep+icons[i];
		}
		
		updateProgress(50, "Icons have been defined");
		
		return icons;
	}
	
	public static HashMap<String, GameToken> definePlayerTokens(Map<Integer, Player> playerIds){
		HashMap<String, GameToken> playerTokens = new HashMap<String, GameToken>();
		GameToken p1 = new GameToken(0, "/textures/default/boat.png", new PositionIndex(
				new int[]{25,22,20,18,16,14,12,10,8,6,0,0,0,0,0,0,0,0,0,0,0,6,8,10,12,14,16,18,20,22,25,26,26,26,26,26,26,26,26,26,},
				new int[]{25,26,26,26,26,26,26,26,26,26,25,22,20,18,16,14,12,10,8,6,2,0,0,0,0,0,0,0,0,0,1,6,8,10,12,14,16,18,20,22,},
				new int[]{25},
				new int[]{2}));

		GameToken p2 = new GameToken(1, "/textures/default/boot.png", new PositionIndex(
			new int[]{26,23,21,19,17,15,13,11,9,7,0,1,1,1,1,1,1,1,1,1,1,7,9,11,13,15,17,19,21,23,26,27,27,27,27,27,27,27,27,27,},
			new int[]{25,26,26,26,26,26,26,26,26,26,26,22,20,18,16,14,12,10,8,6,2,0,0,0,0,0,0,0,0,0,1,6,8,10,12,14,16,18,20,22,},
			new int[]{26},
			new int[]{2}));

		GameToken p3 = new GameToken(2, "/textures/default/car.png", new PositionIndex(
			new int[]{27,22,20,18,16,14,12,10,8,6,0,2,2,2,2,2,2,2,2,2,4,6,8,10,12,14,16,18,20,22,27,28,28,28,28,28,28,28,28,28,},
			new int[]{25,27,27,27,27,27,27,27,27,27,27,22,20,18,16,14,12,10,8,6,2,1,1,1,1,1,1,1,1,1,1,6,8,10,12,14,16,18,20,22,},
			new int[]{27},
			new int[]{2}));
	
		GameToken p4 = new GameToken(3, "/textures/default/hat.png", new PositionIndex(
			new int[]{28,23,21,19,17,15,13,11,9,7,0,3,3,3,3,3,3,3,3,3,5,7,9,11,13,15,17,19,21,23,28,29,29,29,29,29,29,29,29,29,},
			new int[]{25,27,27,27,27,27,27,27,27,27,28,22,20,18,16,14,12,10,8,6,2,1,1,1,1,1,1,1,1,1,1,6,8,10,12,14,16,18,20,22,},
			new int[]{27},
			new int[]{3}));
	
		GameToken p5 = new GameToken(4, "/textures/default/iron.png", new PositionIndex(
			new int[]{25,22,20,18,16,14,12,10,8,6,1,0,0,0,0,0,0,0,0,0,0,6,8,10,12,14,16,18,20,22,25,26,26,26,26,26,26,26,26,26,},
			new int[]{26,28,28,28,28,28,28,28,28,28,29,23,21,19,17,15,13,11,9,7,3,2,2,2,2,2,2,2,2,2,2,7,9,11,13,15,17,19,21,23,},
			new int[]{27},
			new int[]{4}));
		
		GameToken p6 = new GameToken(5, "/textures/default/pupper.png", new PositionIndex(
			new int[]{26,23,21,19,17,15,13,11,9,7,2,1,1,1,1,1,1,1,1,1,1,7,9,11,13,15,17,19,21,23,26,27,27,27,27,27,27,27,27,27,},
			new int[]{26,28,28,28,28,28,28,28,28,28,29,23,21,19,17,15,13,11,9,7,3,2,2,2,2,2,2,2,2,2,2,7,9,11,13,15,17,19,21,23,},
			new int[]{26},
			new int[]{4}));
	
		GameToken p7 = new GameToken(6, "/textures/default/thimble.png", new PositionIndex(
			new int[]{27,22,20,18,16,14,12,10,8,6,3,2,2,2,2,2,2,2,2,2,4,6,8,10,12,14,16,18,20,22,27,28,28,28,28,28,28,28,28,28,},
			new int[]{26,29,29,29,29,29,29,29,29,29,29,23,21,19,17,15,13,11,9,7,3,3,3,3,3,3,3,3,3,3,2,7,9,11,13,15,17,19,21,23,},
			new int[]{25},
			new int[]{4}));
	
		GameToken p8 = new GameToken(7, "/textures/default/wheelbarrow.png", new PositionIndex(
			new int[]{28,23,21,19,17,15,13,11,9,7,4,3,3,3,3,3,3,3,3,3,5,7,9,11,13,15,17,19,21,23,28,29,29,29,29,29,29,29,29,29,},
			new int[]{26,29,29,29,29,29,29,29,29,29,29,23,21,19,17,15,13,11,9,7,3,3,3,3,3,3,3,3,3,3,2,7,9,11,13,15,17,19,21,23,},
			new int[]{25},
			new int[]{3}));
	
	
		playerTokens.put( playerIds.get(0).getName() , p1);
		playerTokens.put( playerIds.get(1).getName() , p2);
		playerTokens.put( playerIds.get(2).getName() , p3);
		playerTokens.put( playerIds.get(3).getName() , p4);
		playerTokens.put( playerIds.get(4).getName() , p5);
		playerTokens.put( playerIds.get(5).getName() , p6);
		playerTokens.put( playerIds.get(6).getName() , p7);
		playerTokens.put( playerIds.get(7).getName() , p8);
		
		updateProgress(40, "Game Tokens have been defined");
		
		return playerTokens;
	}
	
	public static PositionIndex definePropPositions(){
				//  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39
		int[] x = {-1,22,-1,18,-1,-1,12,-1, 8, 6,-1, 4,-1, 4, 4,-1, 4,-1, 4, 4,-1, 6,-1,10,12,-1,16,18,-1,22,-1,24,24,-1,24,-1,-1,24,-1,24};
		int[] y = {-1,24,-1,24,-1,-1,24,-1,24,24,-1,22,-1,18,16,-1,12,-1, 8, 6,-1, 4,-1, 4, 4,-1, 4, 4,-1, 4,-1, 6, 8,-1,12,-1,-1,18,-1,22};
		
		PositionIndex propertyPositions = new PositionIndex(x,y);
		updateProgress(30, "Property positions have been defined");
		return propertyPositions;
	}
	
	public static Property[] defineProps(Counter rails, Counter utils, Dice gameDice){
		Property[] props = new Property[28];
		props[0] = new Street("Mediterranean Ave.", 1, 60, "", false, null, 0, 30, new int[]{2, 10, 30, 90, 160, 250});
		props[1] = new Street("Baltic Ave.", 		3, 60, "", false, null, 0, 30, new int[]{4, 20, 60, 180, 320, 450});

		props[2] = new Railroad("Reading Railroad", 5, 200, "", false, null, rails);
		((Railroad)props[2]).setRailedOwned(rails);

		props[3] = new Street("Oriental Ave.", 		6, 100, "", false, null, 0, 50, new int[]{6, 30, 90, 270, 400, 550});
		props[4] = new Street("Vermont Ave.", 		8, 100, "", false, null, 0, 50, new int[]{6, 30, 90, 270, 400, 550});
		props[5] = new Street("Connecticut Ave.", 	9, 120, "", false, null, 0, 50, new int[]{8, 40, 100, 300, 450, 600});

		props[6] = new Street("St. Charles Place", 	11, 140, "", false, null, 0, 100, new int[]{10, 50, 150, 450, 625, 750});
		props[7] = new Utility("Electric Company", 	12, 150, "", false, null, utils);
		((Utility)props[7]).setDice(gameDice);
		((Utility)props[7]).setCounter(utils);
		props[8] = new Street("States Ave.", 		13, 140, "", false, null, 0, 100, new int[]{10, 50, 150, 450, 625, 750});
		props[9] = new Street("Virginia Ave.", 		14, 160, "", false, null, 0, 100, new int[]{12, 60, 180, 500, 700, 900});
		
		props[10] = new Railroad("Pennsylvania Railroad", 15, 200, "", false, null, rails);
		((Railroad)props[10]).setRailedOwned(rails);

		props[11] = new Street("St. James Place", 	16, 180, "", false, null, 0, 100, new int[]{14, 70, 200, 550, 750, 950});
		props[12] = new Street("Tennessee Ave.",	18, 180, "", false, null, 0, 100, new int[]{14, 70, 200, 550, 750, 950});
		props[13] = new Street("New York Ave.", 	19, 200, "", false, null, 0, 100, new int[]{16, 80, 220, 600, 800, 1000});

		props[14] = new Street("Kentucky Ave.", 	21, 220, "", false, null, 0, 150, new int[]{18, 90, 250, 700, 875, 1050});
		props[15] = new Street("Indiana Ave.", 		23, 220, "", false, null, 0, 150, new int[]{18, 90, 250, 700, 875, 1050});
		props[16] = new Street("Illinois Ave.", 	24, 240, "", false, null, 0, 150, new int[]{20, 100, 300, 750, 925, 1100});

		props[17] = new Railroad("B&O Railroad",	25, 200, "", false, null, rails);
		((Railroad)props[17]).setRailedOwned(rails);

		props[18] = new Street("Atlantic Ave.", 	26, 260, "", false, null, 0, 150, new int[]{22, 110, 330, 800, 975, 1150});
		props[19] = new Street("Ventnor Ave.", 		27, 260, "", false, null, 0, 150, new int[]{22, 110, 330, 800, 975, 1150});
		props[20] = new Utility("Water Works", 		28, 150, "", false, null, utils);
		((Utility)props[20]).setDice(gameDice);
		((Utility)props[20]).setCounter(utils);
		props[21] = new Street("Marvin Gardens", 	29, 280, "", false, null, 0, 150, new int[]{24, 120, 360, 850, 1025, 1200});

		props[22] = new Street("Pacific Ave.", 		31, 300, "", false, null, 0, 200, new int[]{26, 130, 390, 900, 1100, 1275});
		props[23]= new Street("North Carolina Ave.",32, 300, "", false, null, 0, 200, new int[]{26, 130, 390, 900, 1100, 1275});
		props[24] = new Street("Pennsylvania Ave.", 34, 320, "", false, null, 0, 200, new int[]{28, 150, 450, 1000, 1200, 1400});

		props[25] = new Railroad("Short Line", 		35, 200, "", false, null,rails);
		((Railroad)props[25]).setRailedOwned(rails);
		
		props[26] = new Street("Park Place", 		37, 350, "", false, null, 0, 200, new int[]{35, 175, 500, 1100, 1300, 1500});
		props[27] = new Street("Board Walk",		39, 400, "", false, null, 0, 200, new int[]{50, 200, 600, 1400, 1700, 2000});
		
		updateProgress(10, "Properties have been generated");
		
		return props;
	}
	

}

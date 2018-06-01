package edu.illinois.masalzr2.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.google.gson.Gson;

import edu.illinois.masalzr2.models.GameToken;
import edu.illinois.masalzr2.models.PositionIndex;


public class TesterFrame {
	public static void main(String[] args){
		Board board = new Board();
		JFrame frame = new JFrame();
		
		board.setIconNumbers(fatbastard());
		ImageIcon[] icons = getIcons();
		board.setIcons(icons);
		
		board.setStickerBook(stickerBook());
		board.setStickers(getStickers());
		
		board.setStamps(getCollection());
		
		board.setDiceIcons(icons[1], icons[2]);
		board.paintDisplay();
		
		
		frame.add(board.getBoard());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		//frame.repaint();
		
		frame.setVisible(true);
		HashMap<String, GameToken> playerTokens = defineGameTokens();
		boolean[] actives = {false,false,false,false,false,false,false,false};
		
		//board.paintDisplay();
		
		Scanner kb = new Scanner(System.in);
		String input = "";
		
		while(!input.equals("exit")){
			System.out.println("What would you like to do?");
			input = kb.nextLine();
			if(input.equals("diceon") ){
				board.activateDice();
			}else if(input.equals("diceoff")){
				board.deactivateDice();
			}else if(input.equals("move dice")){
				System.out.print("X1: ");
				int x1 = kb.nextInt();
				System.out.print("Y1: ");
				int y1 = kb.nextInt();
				System.out.print("X2: ");
				int x2 = kb.nextInt();
				System.out.print("Y2: ");
				int y2 = kb.nextInt();
				
				board.activateDice();
				board.setDiceLocations(x1, y1, x2, y2);
				
			}else if(input.equals("set dice")){
				System.out.print("D1: ");
				int d1 = kb.nextInt();
				System.out.print("D2: ");
				int d2 = kb.nextInt();
				
				board.paintDice(d1, d2);
			}else if( input.contains("player") ) {
				System.out.println("out 0");
				String num = input.replaceAll("player", "").trim();
				try {
					System.out.println("out 1");
					int player = Integer.parseInt(num) - 1;
					GameToken theChosenOne = playerTokens.get("p"+num);
					if(theChosenOne == null) {
						System.out.println("out 2");
						continue;
					}
					System.out.println("out 3");
					PositionIndex theChosenPath = theChosenOne.getPath();
					theChosenPath.setStep(0);
					System.out.println("out 4");
					if(!actives[player]) {
						System.out.println("out 5");
						for(int i=0; i<40; i++, theChosenOne.movePiece(1)) {
							board.addPiece(theChosenOne.getPiece(), theChosenOne.getX(), theChosenOne.getY());
							//board.paintDisplay();
							//TimeUnit.SECONDS.sleep(1);
							
						}
						
						actives[player] = true;
					}else {
						System.out.println("out 6");
						actives[player] = false;
						board.removePiece(theChosenOne.getPiece());
					}
					System.out.println("out 7");
				}catch(Exception e) {
					System.out.println("Exception has occured:\n");
					e.printStackTrace();
				}
			}
		}
		
		kb.close();
		frame.dispose();
	}
	

	private static HashMap<String, GameToken> defineGameTokens(){
		HashMap<String,GameToken> playerTokens = new HashMap<String,GameToken>();
		String homeDir = System.getProperty("user.dir")+"/textures/default/";
		
		GameToken p1 = new GameToken(0, homeDir+"boat.png", new PositionIndex(
				new int[]{25,22,20,18,16,14,12,10,8,6,0,0,0,0,0,0,0,0,0,0,0,6,8,10,12,14,16,18,20,22,25,26,26,26,26,26,26,26,26,26,},
				new int[]{25,26,26,26,26,26,26,26,26,26,25,22,20,18,16,14,12,10,8,6,2,0,0,0,0,0,0,0,0,0,1,6,8,10,12,14,16,18,20,22,},
				new int[]{2},
				new int[]{25}));

		GameToken p2 = new GameToken(1, homeDir+"boot.png", new PositionIndex(
			new int[]{26,23,21,19,17,15,13,11,9,7,0,1,1,1,1,1,1,1,1,1,1,7,9,11,13,15,17,19,21,23,26,27,27,27,27,27,27,27,27,27,},
			new int[]{25,26,26,26,26,26,26,26,26,26,26,22,20,18,16,14,12,10,8,6,2,0,0,0,0,0,0,0,0,0,1,6,8,10,12,14,16,18,20,22,},
			new int[]{2},
			new int[]{26}));

		GameToken p3 = new GameToken(2, homeDir+"car.png", new PositionIndex(
			new int[]{27,22,20,18,16,14,12,10,8,6,0,2,2,2,2,2,2,2,2,2,4,6,8,10,12,14,16,18,20,22,27,28,28,28,28,28,28,28,28,28,},
			new int[]{25,27,27,27,27,27,27,27,27,27,27,22,20,18,16,14,12,10,8,6,2,1,1,1,1,1,1,1,1,1,1,6,8,10,12,14,16,18,20,22,},
			new int[]{2},
			new int[]{27}));
	
		GameToken p4 = new GameToken(3, homeDir+"hat.png", new PositionIndex(
			new int[]{28,23,21,19,17,15,13,11,9,7,0,3,3,3,3,3,3,3,3,3,5,7,9,11,13,15,17,19,21,23,28,29,29,29,29,29,29,29,29,29,},
			new int[]{25,27,27,27,27,27,27,27,27,27,28,22,20,18,16,14,12,10,8,6,2,1,1,1,1,1,1,1,1,1,1,6,8,10,12,14,16,18,20,22,}
			,
			new int[]{3},
			new int[]{27}));
	
		GameToken p5 = new GameToken(4, homeDir+"iron.png", new PositionIndex(
			new int[]{25,22,20,18,16,14,12,10,8,6,1,0,0,0,0,0,0,0,0,0,0,6,8,10,12,14,16,18,20,22,25,26,26,26,26,26,26,26,26,26,},
			new int[]{26,28,28,28,28,28,28,28,28,28,29,23,21,19,17,15,13,11,9,7,3,2,2,2,2,2,2,2,2,2,2,7,9,11,13,15,17,19,21,23,},
			new int[]{4},
			new int[]{27}));
		
		GameToken p6 = new GameToken(5, homeDir+"pupper.png", new PositionIndex(
			new int[]{26,23,21,19,17,15,13,11,9,7,2,1,1,1,1,1,1,1,1,1,1,7,9,11,13,15,17,19,21,23,26,27,27,27,27,27,27,27,27,27,},
			new int[]{26,28,28,28,28,28,28,28,28,28,29,23,21,19,17,15,13,11,9,7,3,2,2,2,2,2,2,2,2,2,2,7,9,11,13,15,17,19,21,23,},
			new int[]{4},
			new int[]{26}));
	
		GameToken p7 = new GameToken(6, homeDir+"thimble.png", new PositionIndex(
			new int[]{27,22,20,18,16,14,12,10,8,6,3,2,2,2,2,2,2,2,2,2,4,6,8,10,12,14,16,18,20,22,27,28,28,28,28,28,28,28,28,28,},
			new int[]{26,29,29,29,29,29,29,29,29,29,29,23,21,19,17,15,13,11,9,7,3,3,3,3,3,3,3,3,3,3,2,7,9,11,13,15,17,19,21,23,},
			new int[]{4},
			new int[]{25}));
	
		GameToken p8 = new GameToken(7, homeDir+"wheelbarrow.png", new PositionIndex(
			new int[]{28,23,21,19,17,15,13,11,9,7,4,3,3,3,3,3,3,3,3,3,5,7,9,11,13,15,17,19,21,23,28,29,29,29,29,29,29,29,29,29,},
			new int[]{26,29,29,29,29,29,29,29,29,29,29,23,21,19,17,15,13,11,9,7,3,3,3,3,3,3,3,3,3,3,2,7,9,11,13,15,17,19,21,23,},
			new int[]{3},
			new int[]{25}));
	
	
		playerTokens.put("p1", p1);
		playerTokens.put("p2", p2);
		playerTokens.put("p3", p3);
		playerTokens.put("p4", p4);
		playerTokens.put("p5", p5);
		playerTokens.put("p6", p6);
		playerTokens.put("p7", p7);
		playerTokens.put("p8", p8);
		
		return playerTokens;
	}
	
	
	private static Stamp[][] getCollection(){
		
		Gson gson = new Gson();
		
		///Users/msalazar/git/Monopoly/Version6/textures/default
		
		File f = new File(System.getProperty("user.dir")+"/textures/default/stamps.json");
		
		if(!f.exists()) {
			System.out.println("I failed\n"+f.getAbsolutePath());
			System.exit(1);
		}
		
		Stamp[][] collection = null;
		Reader readme = null;
		
		try {
			readme = new FileReader(f);
			collection = gson.fromJson(readme, Stamp[][].class);
			readme.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return collection;
	}
	
	private static ImageIcon[] getStickers(){
		String[] stickers = {	"housing.png",				//0
								"hotelLeft",				//1
								"hotelRight",				//2
								"hotelBottom",				//3
								
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
		
		String dir = System.getProperty("user.dir");
		ImageIcon[] retval = new ImageIcon[stickers.length];
		for(int i=0; i<stickers.length; i++){
			retval[i] = new ImageIcon(dir+"/textures/default/"+stickers[i]);
			//System.out.println(retval[i].getDescription());
		}
		return retval;
	}
	
	private static ImageIcon[] getIcons(){
		
		String[] icons = {	"baseboard.png",			//0
							"dotdie.png",				//1
							"blankdie.png",				//2
							
							"purple.png",				//3
							"lightblue.png",			//4
							"pink.png",					//5
							"orange.png",				//6
							"red.png",					//7
							"yellow.png",				//8
							"green.png",				//9
							"blue.png"};				//10
		
		String dir = System.getProperty("user.dir");
		ImageIcon[] retval = new ImageIcon[icons.length];
		for(int i=0; i<icons.length; i++){
			retval[i] = new ImageIcon(dir+"/textures/default/"+icons[i]);
		}
		return retval;
	}
	
	private static int[][] stickerBook(){
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
						{-1,6,-1,6,-1,6,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1}, //26
						{-1,6,-1,-1,-1,6,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,10,-1,-1,-1,-1}, //27
						{-1,6,6,6,6,6,		-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	11,-1,-1,-1,-1,-1}, //28
						{-1,-1,-1,-1,-1,-1,	-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,	-1,12,-1,-1,-1,-1}};//29
		return temp;
	}
	
	private static int[][] fatbastard(){
		int[][] temp = {{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //0
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //1
						{0,0,7,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //2
						{0,0,7,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //3
						{0,0,7,0,0,0,	7,7,0,0,7,7,7,7,0,0,8,8,8,8,0,0,8,8,	0,0,0,0,0,0}, //4
						{0,0,7,0,0,0,	7,7,0,0,7,7,7,7,0,0,8,8,8,8,0,0,8,8,	0,0,0,0,0,0}, //5
						
						{0,0,0,0,6,6,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	9,9,0,0,0,0}, //6
						{0,0,0,0,6,6,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	9,9,0,0,0,0}, //7
						{0,0,0,0,6,6,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	9,9,0,0,0,0}, //8
						{0,0,0,0,6,6,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	9,9,0,0,0,0}, //9
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //10
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //11
						{0,0,0,0,6,6,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	9,9,0,0,0,0}, //12
						{0,0,0,0,6,6,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	9,9,0,0,0,0}, //13
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //14
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //15
						{0,0,0,0,5,5,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //16
						{0,0,0,0,5,5,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //17
						{0,0,0,0,5,5,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	10,10,0,0,0,0}, //18
						{0,0,0,0,5,5,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	10,10,0,0,0,0}, //19
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //20
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //21
						{0,0,0,0,5,5,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	10,10,0,0,0,0}, //22
						{0,0,0,0,5,5,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	10,10,0,0,0,0}, //23
							
						{0,0,0,0,0,0,	4,4,4,4,0,0,4,4,0,0,0,0,3,3,0,0,3,3,	0,0,0,0,0,0}, //24
						{0,0,0,0,0,0,	4,4,4,4,0,0,4,4,0,0,0,0,3,3,0,0,3,3,	0,0,0,0,0,0}, //25
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //26
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}, //27
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,7,7,7,7,7}, //28
						{0,0,0,0,0,0,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,	0,0,0,0,0,0}};//29
		return temp;
	}
}

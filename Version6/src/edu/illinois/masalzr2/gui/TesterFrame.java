package edu.illinois.masalzr2.gui;

import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class TesterFrame {
	public static void main(String[] args){
		Board board = new Board();
		JFrame frame = new JFrame();
		
		board.setIconNumbers(fatbastard());
		ImageIcon[] icons = getIcons();
		board.setIcons(icons);
		
		board.setDiceIcons(icons[1], icons[2]);
		
		board.paintDisplay();
		
		frame.add(board.getBoard());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
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
			}
		}
		
		kb.close();
		System.exit(0);
	}
	
	private static ImageIcon[] getIcons(){
		
		String[] icons = {	"baseboard.png",			//0
							"dotdie.png",
							"blankdie.png",
							
							"purple.png",				//3
							"purplehouse.png",			//4
							"purplehotelleft.png",		//5
							"purplehotelright.png",		//6
							"purplehotelbottom.png",	//7
							"lightblue.png",			//8
							"lightbluehouse.png",		//9
							"lightbluehotelleft.png",	//10
							"lightbluehotelright.png",	//11
							"lightbluehotelbottom.png", //12
							"pink.png",					//13
							"pinkhouse.png",			//14
							"pinkhotelleft.png",		//15
							"pinkhotelright.png",		//16
							"pinkhotelbottom.png",		//17
							"orange.png",				//18
							"orangehouse.png",			//19
							"orangehotelleft.png",		//20
							"orangehotelright.png",		//21
							"orangehotelbottom.png",	//22
							"red.png",					//23
							"redhouse.png",				//24
							"redhotelleft.png",			//25
							"redhotelright.png",		//26
							"redhotelbottom.png",		//27
							"yellow.png",				//28
							"yellowhouse.png",			//29
							"yellowhotelleft.png",		//30
							"yellowhotelright.png",		//31
							"yellowhotelbottom.png",	//32
							"green.png",				//33
							"greenhouse.png",			//34
							"greenhotelleft.png",		//35
							"greenhotelright.png",		//36
							"greenhotelbottom.png",		//37
							"blue.png",					//38
							"bluehouse.png",			//39
							"bluehotelleft.png",		//40
							"bluehotelright.png",		//41
							"bluehotelbottom.png",		//42
							
							"eleccomp.png",				//43
							"waterworks.png",			//44
							
							"jail.png",					//45
							"chesttop.png",				//46
							"chestbottom.png",			//47
							"chance.png",				//48
							
							"gotop.png",				//49
							"gomid.png",				//50
							"gobot.png",				//51
							
							"parktop.png",				//52
							"parkbot.png"};				//53
		
		String dir = System.getProperty("user.dir");
		ImageIcon[] retval = new ImageIcon[icons.length];
		for(int i=0; i<icons.length; i++){
			retval[i] = new ImageIcon(dir+"/textures/default/"+icons[i]);
		}
		return retval;
	}
	
	private static int[][] fatbastard(){
		int[][] temp = {{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //0
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //1
						{0,0,23,52,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //2
						{0,0,23,53,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //3
						{0,0,23,0,0,0,		23,23,48,48,23,23,23,23,0,0,28,28,28,28,44,44,28,28,	0,0,0,0,0,0}, //4
						{0,0,23,0,0,0,		23,23,48,48,23,23,23,23,0,0,28,28,28,28,44,44,28,28,	0,0,0,0,0,0}, //5
						
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //6
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //7
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //8
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //9
						{0,0,0,0,46,46,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					46,46,0,0,0,0}, //10
						{0,0,0,0,47,47,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					47,47,0,0,0,0}, //11
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //12
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					33,33,0,0,0,0}, //13
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //14
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //15
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					48,48,0,0,0,0}, //16
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					48,48,0,0,0,0}, //17
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //18
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //19
						{0,0,0,0,43,43,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //20
						{0,0,0,0,43,43,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //21
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //22
						{0,0,0,0,13,13,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					38,38,0,0,0,0}, //23
							
						{0,45,45,45,45,45,	8,8,8,8,48,48,8,8,0,0,0,0,3,3,46,46,3,3,				0,0,0,0,0,0}, //24
						{0,45,0,0,0,45,		8,8,8,8,48,48,8,8,0,0,0,0,3,3,47,47,3,3,				0,0,0,0,0,0}, //25
						{0,45,0,45,0,45,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,0,0,0,0,0}, //26
						{0,45,0,0,0,45,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,49,0,0,0,0}, //27
						{0,45,45,45,45,45,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					50,23,23,23,23,23}, //28
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,					0,51,0,0,0,0}};//29
		return temp;
	}
}

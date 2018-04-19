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

							"housing.png",				//11
							"hotelLeft",				//12
							"hotelRight",				//13
							"hotelBottom",				//14
							
							"eleccomp.png",				//15
							"waterworks.png",			//16
							
							"jail.png",					//17
							"chesttop.png",				//18
							"chestbottom.png",			//19
							"chance.png",				//20
							
							"gotop.png",				//21
							"gomid.png",				//22
							"gobot.png",				//23
							
							"parktop.png",				//24
							"parkbot.png"};				//25
		
		String dir = System.getProperty("user.dir");
		ImageIcon[] retval = new ImageIcon[icons.length];
		for(int i=0; i<icons.length; i++){
			retval[i] = new ImageIcon(dir+"/textures/default/"+icons[i]);
		}
		return retval;
	}
	
	private static int[][] fatbastard(){
		int[][] temp = {{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //0
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //1
						{0,0,7,24,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //2
						{0,0,7,25,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //3
						{0,0,7,0,0,0,		7,7,20,20,7,7,7,7,0,0,8,8,8,8,16,16,8,8,	0,0,0,0,0,0}, //4
						{0,0,7,0,0,0,		7,7,20,20,7,7,7,7,0,0,8,8,8,8,16,16,8,8,	0,0,0,0,0,0}, //5
						
						{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //6
						{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //7
						{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //8
						{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //9
						{0,0,0,0,18,18,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		18,18,0,0,0,0}, //10
						{0,0,0,0,19,19,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		19,19,0,0,0,0}, //11
						{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //12
						{0,0,0,0,6,6,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		9,9,0,0,0,0}, //13
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //14
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //15
						{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		20,20,0,0,0,0}, //16
						{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		20,20,0,0,0,0}, //17
						{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //18
						{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //19
						{0,0,0,0,15,15,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //20
						{0,0,0,0,15,15,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //21
						{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //22
						{0,0,0,0,5,5,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		10,10,0,0,0,0}, //23
							
						{0,17,17,17,17,17,	4,4,4,4,20,20,4,4,0,0,0,0,3,3,18,18,3,3,	0,0,0,0,0,0}, //24
						{0,17,0,0,0,17,		4,4,4,4,20,20,4,4,0,0,0,0,3,3,19,19,3,3,	0,0,0,0,0,0}, //25
						{0,17,0,17,0,17,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,0,0,0,0,0}, //26
						{0,17,0,0,0,17,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,21,0,0,0,0}, //27
						{0,17,17,17,17,17,	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		22,7,7,7,7,7}, //28
						{0,0,0,0,0,0,		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,		0,23,0,0,0,0}};//29
		return temp;
	}
}

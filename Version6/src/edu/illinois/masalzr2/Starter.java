package edu.illinois.masalzr2;

import java.io.File;

import edu.illinois.masalzr2.io.GameIn;
import edu.illinois.masalzr2.masters.GameVariables;

public class Starter {
	public static void main( String[] args ){
		GameVariables myGame = GameIn.newGame();
		if(args.length > 0) {
			int i=0;
			while( i<args.length && !args[i].endsWith(".mns") ) {
				i++;
			}
			if(i < args.length) {
				File f = new File(args[i]);
				if(f.exists()) {
					myGame = GameIn.produceSavedGame(f);
				}
			}
		}
		myGame.buildFrame();
	}
}

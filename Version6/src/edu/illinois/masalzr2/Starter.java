package edu.illinois.masalzr2;

import java.io.File;

import edu.illinois.masalzr2.gui.PreGameFrame;
import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.MonopolyExceptionHandler;



public class Starter {
	
	static{
		MonopolyExceptionHandler masterCatcher = new MonopolyExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(masterCatcher);
	}
	
	public static void main( String[] args ){
		if(args.length > 0) {
			int i=0;
			while( i<args.length && !args[i].endsWith(".mns") ) {
				i++;
			}
			if(i < args.length) {
				File f = new File(args[i]);
				if(f.exists()) {
					GameIo.produceSavedGame(f).buildFrame();
					return;
				}
			}
		}
		
		PreGameFrame pgf = new PreGameFrame();
		pgf.start();
		//myGame.buildFrame();
	}
}

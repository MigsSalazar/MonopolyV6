package edu.illinois.masalzr2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import edu.illinois.masalzr2.gui.NewGameStartUp;
import edu.illinois.masalzr2.gui.PreGameFrame;
import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.masters.LogMate;
import edu.illinois.masalzr2.masters.MonopolyExceptionHandler;
import edu.illinois.masalzr2.models.GameToken;
import edu.illinois.masalzr2.models.Player;



public class Starter {
	
	static{
		MonopolyExceptionHandler masterCatcher = new MonopolyExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(masterCatcher);
		
		LogMate.LOG.newEntry("Starter: static: beginning log");
		
	}
	
	public static void main( String[] args ){
		if(args.length > 0) {
			LogMate.LOG.newEntry("Starter: main: found multiple arguments");
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
		LogMate.LOG.newEntry("Starter: main: creating PreGameFrame");
		PreGameFrame pgf = new PreGameFrame();
		LogMate.LOG.newEntry("Starter: main: Starting PreGameFrame");
		pgf.start();
		//myGame.buildFrame();
	}
	
	public static boolean gameSetup(JFrame parent, GameVariables newerGame) {
		LogMate.LOG.newEntry("Starter: Game Setup: NewGame was not null");
		LogMate.LOG.newEntry("Starter: Game Setup: Finding GameTokens");
		Map<String, GameToken> to = newerGame.getPlayerTokens();
		List<GameToken> tokens = new ArrayList<GameToken>(to.values());
		tokens.sort(GameToken.getTeamComparator());
		
		LogMate.LOG.newEntry("Starter: Game Setup: developing NewGameStartUp");
		NewGameStartUp ngsup = new NewGameStartUp(parent, tokens );
		LogMate.LOG.newEntry("Starter: Game Setup: Starting NewGameStartUp Dialog");
		ngsup.beginDialog();
		List<String> names = ngsup.getNames();
		if(names.size() == 1) {
			return false;
		}
		LogMate.LOG.newEntry("Starter: Game Setup: Dialog has ended, starting game");
		Map<Integer, Player> pl = newerGame.getPlayerID();
		Map<String, Player> pls = newerGame.getPlayers();
		newerGame.setPlayerNumber(names.size());
		to.clear();
		LogMate.LOG.newEntry("Starter: Game Setup: Assets gotten");
		for(int i=0; i<names.size(); i++) {
			LogMate.LOG.newEntry("Starter: Game Setup: at name "+i + " is "+names.get(0));
			to.put(names.get(i), tokens.get(i));
			pl.get(i).setName(names.get(i));
			pls.remove(""+i);
			pls.put(names.get(i), pl.get(i));
		}
		LogMate.LOG.newEntry("Starter: Game Setup: Loading assets. sending");
		newerGame.refreshPlayerMaps();
		newerGame.getTurn().setMax(names.size());
		newerGame.buildFrame();
		return true;
	}
	
}

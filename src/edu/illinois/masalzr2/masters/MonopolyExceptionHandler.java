package edu.illinois.masalzr2.masters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class MonopolyExceptionHandler implements Thread.UncaughtExceptionHandler {
	
	private String sep = File.separator;
	
	public static void uncaughtException(Throwable e, Thread t){
		MonopolyExceptionHandler meh = new MonopolyExceptionHandler();
		meh.uncaughtException(t, e);
	}
	
	public void uncaughtException(Thread t, Throwable e) {
		LogMate.LOG.flush();
		LogMate.LOG.finish();
		File errorLog = new File(System.getProperty("user.dir") + sep + "logs" + sep + "errorlogs");
		if( !errorLog.exists() ) {
			errorLog.mkdirs();
		}
		
		SimpleDateFormat today = new SimpleDateFormat("hh.mm.ss MM-dd-yyyy ");
		
		File finLog = new File(errorLog.getPath()+sep+"log "+today.format(new Date())+".log");
		
		PrintWriter writeout;
		try {
			writeout = new PrintWriter(finLog);
			e.printStackTrace(writeout);
			writeout.close();
			JOptionPane.showMessageDialog(null, "Game has arrived at an exception\nApplication now closing\nLog can be found at\n"+finLog.getAbsolutePath());
			System.exit(1);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "You fucked up so badly that this exception is literally impossible to acheive"
											+ "\nI am both tremendously dissapointed and remarkably impressed at you."
											+ "\nCongradulations.");
		}
		
		
	}
}

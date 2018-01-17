package edu.illinois.miguelsalazar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import edu.illinois.miguelsalazar.gui.Editor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Im called");
		Editor edit = new Editor();
		System.out.println("Im made");
		edit.startEditor();
		System.out.println("Im started");
	}

}

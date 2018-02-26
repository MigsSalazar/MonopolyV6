package edu.illinois.miguelsalazar.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import com.google.gson.annotations.Expose;
import com.sun.glass.events.KeyEvent;

import edu.illinois.miguelsalazar.gui.guiModels.SelectorPanel;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, ComponentListener {
	
	private ArrayList<File> assetList;
	
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem save;
	private JMenuItem saveas;
	private JMenuItem open;
	private JMenuItem neww;
	private JMenuItem exit;
	
	private JMenu help;
	private JMenuItem about;
	private JMenuItem manual;
	
	private JTabbedPane tabs;
	
	private ImageSelection imageTab;
	private BoardPaint boardTab;
	private PlayerConstructor playerTab;
	private PropertyRetailer propertyTab;
	private CardMaker cardTab;
	
	@Expose private ArrayList<String> iconPaths;
	
	public Editor(){
		iconPaths = new ArrayList<String>();
		tabs = new JTabbedPane();
		makeMenu();
		this.setJMenuBar(menuBar);
		defineTabs();
		this.add(tabs);
		System.out.println("Editor complete");
	}
	
	public void startEditor(){
		
		this.setTitle("Monopoly Creator");
		//this.setSize(new Dimension(500,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.out.println("All files");
				for(File f : assetList){
					System.out.println(f.getAbsolutePath());
				}
				System.out.println("Files all printed");
				System.exit(0);
			}
		});*/
		this.pack();
		this.setVisible(true);
	}
	
	private void makeMenu(){
		menuBar = new JMenuBar();
		
		file = new JMenu("File");
		neww = new JMenuItem("New Ctr+N");
		neww.addActionListener(this);
		neww.setMnemonic(KeyEvent.VK_N);
		
		save = new JMenuItem("Save Ctrl+S");
		save.addActionListener(this);
		save.setMnemonic(KeyEvent.VK_S);
		
		saveas = new JMenuItem("Save as...");
		saveas.addActionListener(this);
		
		open = new JMenuItem("Open Ctrl+O");
		open.addActionListener(this);
		open.setMnemonic(KeyEvent.VK_O);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		
		file.add(neww);
		file.add(save);
		file.add(open);
		file.add(exit);
		
		menuBar.add(file);
		
		help = new JMenu("Help");
		
		about = new JMenuItem("About");
		about.addActionListener(this);
		
		manual = new JMenuItem("Guide");
		manual.addActionListener(this);
		
		help.add(about);
		help.add(manual);
		
		menuBar.add(help);
		
	}
	
	private void defineTabs(){
		
		assetList = new ArrayList<File>();
		
		imageTab = new ImageSelection(assetList);
		imageTab.addComponentListener(this);
		
		boardTab = new BoardPaint(iconPaths, assetList);
		boardTab.addComponentListener(this);
		
		playerTab = new PlayerConstructor();
		propertyTab = new PropertyRetailer();
		cardTab = new CardMaker();
		tabs = new JTabbedPane();
		
		tabs.setPreferredSize(new Dimension(1200,680));
		
		tabs.addTab("Images", imageTab);
		tabs.addTab("Board Config", boardTab);
		tabs.addTab("Players", playerTab);
		tabs.addTab("Property", propertyTab);
		tabs.addTab("Cards", cardTab);
		
		//this.add(tabs);
	}

	private void acquireIconPaths(){
		//System.out.println("acquire paths started");
		
		ArrayList<String> ip = new ArrayList<String>();
		
		ArrayList<SelectorPanel> panels = imageTab.getSelects();

		SelectorPanel current = panels.get(4);
		
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(8);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(7);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(6);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(3);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(0);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(1);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(2);
		passToIconPaths(current.getStringPaths(),ip);
		current = panels.get(5);
		passToIconPaths(current.getStringPaths(),ip);
		
		for(int i=0; i<ip.size(); i++){
			if(i<iconPaths.size()){
				iconPaths.set(i, ip.get(i));
			}else{
				iconPaths.add(ip.get(i));
			}
			
		}
		printIconPaths();
	}
	
	public void printIconPaths(){
		for(int i=0; i<iconPaths.size(); i++){
			System.out.println(iconPaths.get(i));
		}
	}
	
	private void passToIconPaths(String[] paths, ArrayList<String> ip){
		if(paths.length == 0){
			return;
		}
		//System.out.println("Paths length: "+paths.length);
		for(String s : paths){
			ip.add(s);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("something got pressed!");
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
		if(e.getSource().equals(imageTab)){
			acquireIconPaths();
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		//System.out.println("Component Moved- "+e.getSource());
	}

	@Override
	public void componentResized(ComponentEvent e) {
		//System.out.println("Component Resized- "+e.getSource());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		if(e.getSource().equals(boardTab)){
			boardTab.setIconPaths(iconPaths);
			boardTab.redecorate();
			boardTab.updateAssets();
		}
	}
	
	
}

package edu.illinois.miguelsalazar.gui.guiModels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class SelectorPanel extends JPanel implements ActionListener, WindowFocusListener, ListSelectionListener{

	private JPanel mainPanel;
	
	private ArrayList<JPanel> cells;
	private ArrayList<JLabel> labels;
	private ArrayList<JButton> images;
	private ArrayList<JTextField> paths;
	
	private String[] stringPaths;
	private ImageIcon[] icons;
	
	private ArrayList<File> assetList;
	
	private JFrame listings;
	private JList<String> picks;
	private JButton lastButton;
	
	
	public SelectorPanel(String title, ArrayList<File> al, int num){
		
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new GridLayout(5,1));
		
		assetList = al;
		
		Border border = BorderFactory.createTitledBorder(title);
		mainPanel.setBorder(border);
		defineAtomicValues(num);
		defineComponents(num);
		
		labels.get(0).setText("Color: ");
		labels.get(1).setText("House: ");
		labels.get(2).setText("Hotel Left: ");
		labels.get(3).setText("Hotel Right: ");
		labels.get(4).setText("Hotel Bot: ");
		
		labels.get(0).setPreferredSize(new Dimension(70,30));
		labels.get(1).setPreferredSize(new Dimension(70,30));
		labels.get(2).setPreferredSize(new Dimension(70,30));
		labels.get(3).setPreferredSize(new Dimension(70,30));
		labels.get(4).setPreferredSize(new Dimension(70,30));
		
		for(JPanel c : cells){
			mainPanel.add(c);
		}
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
		
	}
	
	public SelectorPanel(ArrayList<File> al){
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new GridLayout(5,1));
		
		assetList = al;
		
		Border border = BorderFactory.createTitledBorder("Base Images");
		mainPanel.setBorder(border);
		defineAtomicValues(3);
		defineComponents(3);
		
		labels.get(0).setText("Board:");
		labels.get(0).setPreferredSize(new Dimension(65,30));
		labels.get(1).setText("Dot Dice:");
		labels.get(1).setPreferredSize(new Dimension(65,30));
		labels.get(2).setText("Blank Dice:");
		labels.get(2).setPreferredSize(new Dimension(65,30));
		
		JPanel blank = new JPanel();
		blank.setPreferredSize(new Dimension(65,30));
		JPanel empty = new JPanel();
		empty.setPreferredSize(new Dimension(65,30));
		
		for(JPanel c : cells){
			mainPanel.add(c);
		}
		
		mainPanel.add(blank);
		mainPanel.add(empty);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	public void setAssetList(ArrayList<File> al){
		assetList = al;	
	}
	
	private void defineAtomicValues(int num){
		icons = new ImageIcon[num];
		stringPaths = new String[num];
		for(int i=0; i<num; i++){
			icons[i] = new ImageIcon();
			stringPaths[i] = "";
		}
		
		
		
	}
	
	private void defineComponents(int num){
		cells = new ArrayList<JPanel>();
		labels = new ArrayList<JLabel>();
		images = new ArrayList<JButton>();
		paths = new ArrayList<JTextField>();
		
		for(int i=0; i<num; i++){
			cells.add(new JPanel(new BorderLayout()));
			labels.add(new JLabel());
			//labels.get(i).setText("          ");;
			
			images.add(new JButton(icons[i]));
			images.get(i).setPreferredSize(new Dimension(30,30));
			images.get(i).addActionListener(this);
			
			paths.add(new JTextField("file @ "+i));
			paths.get(i).setColumns(10);
			//paths.get(i).setR
			//paths.get(i).setPreferredSize(new Dimension(15,paths.get(i).getHeight()));
			paths.get(i).setEditable(false);
			
			JPanel temp = cells.get(i);
			temp.add(labels.get(i), BorderLayout.WEST);
			temp.add(paths.get(i), BorderLayout.CENTER);
			temp.add(images.get(i), BorderLayout.EAST);
			
		}
		
	}
	
	private JFrame miniPopUp(JButton alongSide){
		lastButton = alongSide;
		listings = new JFrame();
		listings.setUndecorated(true);
		
		Point pointme = alongSide.getLocationOnScreen();
		Point nextPoint = new Point(pointme.x, pointme.y);
		nextPoint.translate(30, 0);
		
		listings.setLocation(nextPoint);
		
		listings.setSize(200, 100);
		
		listings.add(defineScrollPane());
		
		return listings;
	}
	
	private JScrollPane defineScrollPane(){
		
		DefaultListModel<String> m = new DefaultListModel<String>();
		m.addElement("<NONE>");
		for(File f : assetList){
			m.addElement(f.getName());
		}
		
		picks = new JList<String>(m);
		
		picks.setLayoutOrientation(JList.VERTICAL);
		picks.setVisibleRowCount(5);
		picks.addListSelectionListener(this);
		
		JScrollPane s = new JScrollPane(picks);
		s.setWheelScrollingEnabled(true);
		
		return s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(images.contains(e.getSource())){
			int index = images.indexOf(e.getSource());
			//System.out.println("index: "+index);
			JFrame listings = miniPopUp(images.get(index));
			
			//listings.addFocusListener(this);
			listings.addWindowFocusListener(this);
			//System.out.println("me: "+this.hashCode()+" listener: "+listings.getListeners(FocusListener.class) );
			listings.setVisible(true);
		}
	}


	@Override
	public void windowGainedFocus(WindowEvent e) {
		//System.out.println("window listener: gained focus");
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		//System.out.println("window listener: lost focus");
		if(e.getSource().equals(listings) ){
			//listings.setVisible(false);
			listings.dispose();
			listings = null;
			lastButton = null;
			picks = null;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int button = images.indexOf(lastButton);
		int name = picks.getSelectedIndex();
		if(name == 0){
			stringPaths[button] = "";
			paths.get(button).setText("file @ "+button);
			paths.get(button).setToolTipText("No File");
			icons[button] = null;
		}else{
			stringPaths[button] = assetList.get(name-1).getAbsolutePath();
			//paths.get(button).repaint();
			paths.get(button).setText(stringPaths[button]);
			paths.get(button).setToolTipText(stringPaths[button]);
			icons[button] = new ImageIcon(stringPaths[button]);
		}
		images.get(button).setIcon(icons[button]);
		
		//int name = assetList.indexOf()
	}

	public void validateSelection(File removal){
		
		for(int i=0; i<stringPaths.length; i++){
			if(stringPaths[i].equals(removal.getAbsolutePath())){
				images.get(i).setIcon(null);
				stringPaths[i] = "";
				paths.get(i).setText("file @ "+i);
				paths.get(i).setToolTipText("No File");
				icons[i] = null;
			}
		}
	}
	
	public String[] getStringPaths() {
		return stringPaths;
	}

	public void setStringPaths(String[] stringPaths) {
		this.stringPaths = stringPaths;
	}

}

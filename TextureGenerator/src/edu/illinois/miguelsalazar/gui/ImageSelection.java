package edu.illinois.miguelsalazar.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.illinois.miguelsalazar.gui.guiModels.SelectorPanel;

@SuppressWarnings("serial")
public class ImageSelection extends JPanel implements ActionListener{
	
	private JPanel selection;
	private ArrayList<SelectorPanel> selects;
	private JScrollPane scrollme;
	private JList<String> picks;
	private DefaultListModel<String> model;
	
	private JPanel utility;
	private JButton importation;
	private JButton remove;
	//private JList<String> picks;
	//private DefaultListModel<String> model;
	
	
	private ArrayList<File> assetList;
	
	public ImageSelection(ArrayList<File> al){
		
		this.setLayout(new BorderLayout());
		
		JPanel selectors = new JPanel(new GridLayout(3,3));
		selects = new ArrayList<SelectorPanel>();
		
		selects.add(new SelectorPanel("Unit 5 - Red", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 6 - Yellow", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 7 - Green", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 4 - Orange", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel(new ArrayList<File>()));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 8 - Blue", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 3 - Pink", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 2 - Light Blue", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		selects.add(new SelectorPanel("Unit 1 - Purple/Brown", new ArrayList<File>(), 5));
		selectors.add(selects.get(selects.size()-1));
		
		this.add(selectors, BorderLayout.CENTER);
		
		assetList = al;
		
		selection = new JPanel(new BorderLayout());
		scrollme = defineScrollPane();
		
		defineUtility();
		selection.add(scrollme, BorderLayout.CENTER);
		selection.add(utility,BorderLayout.SOUTH);
		
		this.add(selection, BorderLayout.SOUTH);
		
		//System.out.println("selection height: "+selection.getHeight() );
	}
	
	private void defineUtility(){
		utility = new JPanel();
		
		importation = new JButton("Import");
		importation.setPreferredSize(new Dimension(100,25));
		importation.addActionListener(this);
		
		remove = new JButton("Remove");
		remove.setPreferredSize(new Dimension(100,25));
		remove.addActionListener(this);
		
		utility.add(importation);
		utility.add(remove);
		
	}
	
	
	private JScrollPane defineScrollPane(){
		
		model = new DefaultListModel<String>();
		
		for(File f : assetList){
			model.addElement(f.getName());
		}
		picks = new JList<String>(model);
		
		picks.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		picks.setVisibleRowCount(5);
		picks.setPreferredSize(new Dimension(100,100));
		
		JScrollPane s = new JScrollPane(picks);
		s.setWheelScrollingEnabled(true);
		
		return s;
	}
	
	private void acquireFiles(){
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		FileFilter filter = new FileNameExtensionFilter("Image Files","jpg","png","mpeg");
		jfc.setFileFilter(filter);
		
		jfc.setMultiSelectionEnabled(true);
		
		int choice = jfc.showOpenDialog(this);
		if(choice != JFileChooser.APPROVE_OPTION){
			return;
		}
		int startIdx = assetList.size();
		File[] files = jfc.getSelectedFiles();
		
		for(File f : files){
			int index = assetList.indexOf(f);
			if(index == -1){
				assetList.add(f);
			}
		}
		
		updateAssets(startIdx);	
	}

	public void updateAssets(int startIdx){
		for(SelectorPanel sp : selects){
			sp.setAssetList(assetList);
		}
		//picks.getModel().
		for(int i=startIdx; i<assetList.size(); i++){
			model.addElement(assetList.get(i).getName());
		}
		//picks.
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(importation)){
			acquireFiles();
		}else if(e.getSource().equals(remove)){
			int removed = picks.getSelectedIndex();
			
			//picks.remove(removed);
			model.remove(removed);
			File name = assetList.remove(removed);
			for(SelectorPanel sp : selects){
				sp.validateSelection(name);
			}
		}
		
	}

	public ArrayList<SelectorPanel> getSelects() {
		return selects;
	}

	public void setSelects(ArrayList<SelectorPanel> selects) {
		this.selects = selects;
	}

	public JPanel getSelection() {
		return selection;
	}
	
}

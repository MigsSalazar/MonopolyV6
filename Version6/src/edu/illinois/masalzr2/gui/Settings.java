package edu.illinois.masalzr2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.illinois.masalzr2.io.GameIo;
import edu.illinois.masalzr2.masters.GameVariables;
import edu.illinois.masalzr2.templates.TemplateGameVars;
import lombok.Getter;
import lombok.Setter;

public class Settings implements ActionListener, ListSelectionListener {
	
	private String sep = File.separator;

	private JDialog dialog;
	
	private JPanel top;
	
	private JPanel gameSets;
	private JComboBox<String> currency;
	private JCheckBox turnLimitOn;
	private JSpinner turnLimit;
	private SpinnerNumberModel model;
	private JCheckBox fancyMove;
	
	private JPanel documentation;
	private JButton instBook;
	private JButton logs;
	private JButton gitHub;
	
	private JPanel textureSets;
	private JButton textImport;
	private JButton genJson;
	private JButton clean;
	
	private JPanel texturePanel;
	private JScrollPane textureSelect;
	private JList<String> textures;
	private DefaultListModel<String> textModel;
	
	private JButton ok;
	
	private JFrame parent;
	
	private Map<String, File> paths;
	@Getter @Setter private String fileDir = System.getProperty("user.dir") + sep +"resources"+sep+"packages"+sep+"default.mns";
	private boolean limitedTurns;
	private int turnsLimited;
	
	public Settings(JFrame p, boolean lt, int tl) {
		parent = p;
		limitedTurns = lt;
		turnsLimited = tl;
		defineDialog();
	}
	
	public void start() {
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	private void defineDialog() {
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
		
		defineGameSets();
		defineDocumentation();
		defineTextureSets();
		
		top.add(gameSets);
		top.add(documentation);
		top.add(textureSets);
		
		defineTextureSelect();
		
		ok = new JButton("OK");
		ok.addActionListener(this);
		
		dialog = new JDialog(parent,"Settings",true);
		dialog.setLayout(new BorderLayout());
		
		dialog.add(top, BorderLayout.NORTH);
		dialog.add(texturePanel, BorderLayout.CENTER);
		dialog.add(ok, BorderLayout.SOUTH);
		
	}
	
	private void defineTextureSelect(){
		
		TitledBorder title = BorderFactory.createTitledBorder("Texture Selection");
		
		texturePanel = new JPanel();
		texturePanel.setBorder(title);
		texturePanel.setPreferredSize(new Dimension(300,200));
		texturePanel.setLayout(new BorderLayout());
		
		textModel = new DefaultListModel<String>();
		paths = new HashMap<String, File>();
		
		updateTextures();
		
		textures = new JList<String>(textModel);
		textures.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		textures.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		textures.addListSelectionListener(this);
		
		textureSelect = new JScrollPane(textures);
		//textureSelect.add(textures);
		
		texturePanel.add(textureSelect, BorderLayout.CENTER);
	}

	private void updateTextures() {
		textModel.removeAllElements();
		paths.clear();
		
		File f = new File(System.getProperty("user.dir")+sep+"resources"+sep+"packages"+sep);
		if( !f.exists() ) {
			f.mkdirs();
		}
		System.out.println(f.getPath());
		File[] files = f.listFiles();
		for(File s : files) {
			textModel.addElement(s.getName());
			System.out.println("textModel has s:"+textModel.contains(s) );
			paths.put(s.getName(), s);
		}
	}
	
	private void defineGameSets() {
		TitledBorder gameTitle = BorderFactory.createTitledBorder("Game Settings");
		gameSets = new JPanel();
		gameSets.setLayout(new BoxLayout(gameSets, BoxLayout.LINE_AXIS));
		gameSets.setBorder(gameTitle);
		
		String[] options = {"$","€","£","₱","¥","₿","₨","¤"};
		currency = new JComboBox<String>(options);
		
		turnLimitOn = new JCheckBox("Turn Limit");
		turnLimitOn.setSelected(limitedTurns);
		turnLimitOn.addActionListener(this);
		
		model = new SpinnerNumberModel();
		//model.setMinimum(10);
		model.setValue(turnsLimited);
		turnLimit = new JSpinner(model);
		turnLimit.setEnabled(false);
		
		fancyMove = new JCheckBox("Fancy Move");
		fancyMove.setSelected(true);
		
		gameSets.add(currency);
		gameSets.add(turnLimitOn);
		gameSets.add(turnLimit);
		gameSets.add(fancyMove);
	}
	
	private void defineDocumentation() {
		TitledBorder docTitle = BorderFactory.createTitledBorder("Documentation");
		
		documentation = new JPanel();
		documentation.setBorder(docTitle);
		documentation.setLayout(new BoxLayout(documentation, BoxLayout.LINE_AXIS));
		
		instBook = new JButton("Guide Book");
		instBook.addActionListener(this);
		
		logs = new JButton("Open logs");
		logs.addActionListener(this);
		
		gitHub = new JButton("GitHub");
		gitHub.addActionListener(this);
		
		documentation.add(instBook);
		documentation.add(logs);
		documentation.add(gitHub);
	}
	
	private void defineTextureSets() {
		TitledBorder textTitle = BorderFactory.createTitledBorder("Import Textures");
		
		textureSets = new JPanel();
		textureSets.setBorder(textTitle);
		textureSets.setLayout(new BoxLayout(textureSets, BoxLayout.LINE_AXIS));
		
		textImport = new JButton("Import");
		textImport.addActionListener(this);
		
		genJson = new JButton("Generate Json");
		genJson.addActionListener(this);
		
		clean = new JButton("Clean");
		clean.addActionListener(this);
		
		textureSets.add(textImport);
		textureSets.add(genJson);
		textureSets.add(clean);	
	}
	
	public boolean isTurnsLimited() {
		return turnLimitOn.isSelected();
	}
	
	public int getTurnLimit() {
		return (Integer)model.getValue();
	}
	
	public String getCurrency() {
		return (String)currency.getSelectedItem();
	}
	
	public boolean isFancyMoveEnabled() {
		return fancyMove.isSelected();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		//System.out.println(source.toString());
		if( source.equals(turnLimitOn) ) {
			turnLimit.setEnabled(turnLimitOn.isSelected());
		}else if(source.equals(textImport)) {
			GameVariables gv = GameIo.varsFromJson(dialog);
			 if(gv == null) {
				 textImport.setForeground(Color.RED);
				 textImport.setOpaque(true);
			 }else {
				 textImport.setForeground(Color.GREEN);
				 textImport.setOpaque(true);
				 fileDir = gv.getSaveFile().getPath();
				 if( !(new File(fileDir)).exists() ) {
					 fileDir = System.getProperty("user.dir")+sep+fileDir;
				 }
			 }
		}else if(source.equals(clean)) {
			//clean.setForeground(Color.BLACK);
			TemplateGameVars.produceTemplate();
			clean.setForeground(Color.GREEN);
			clean.setOpaque(true);
			TemplateGameVars.closeProgressPanel();
		}else if(source.equals(genJson)) {
			if( GameIo.printCleanJson() ) {
				genJson.setForeground(Color.GREEN);
				genJson.setOpaque(true);
			}else {
				genJson.setForeground(Color.RED);
				genJson.setOpaque(true);
			}
		}else if(source.equals(logs)) {
			try {
				if(Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open( new File(System.getProperty("user.dir") + sep + "logs" ) );
				}
			}catch(IOException ioe) {
				JOptionPane.showMessageDialog(dialog, "Could no open Logs file. To access logs, go to:\n"+System.getProperty("user.dir")+sep+"logs");
			}
		}else if(source.equals(gitHub)) {
			try {
				if(Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(new URI("https://github.com/MigsSalazar/Monopoly"));
				}
			} catch (URISyntaxException | IOException e1) {
				JOptionPane.showMessageDialog(dialog, "Could no open github file. To access logs, go to:\nhttps://github.com/MigsSalazar/Monopoly");
			}
		}else if(source.equals(instBook)) {
			JOptionPane.showMessageDialog(dialog, "Instruction Manual not yeat available");
		}else if(source.equals(ok)) {
			dialog.setVisible(false);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object source= e.getSource();
		if(source.equals(textures)) {
			fileDir = paths.get(textures.getSelectedValue()).getPath();
		}
	}

}

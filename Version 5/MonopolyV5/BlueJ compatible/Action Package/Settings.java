/**
 * 
 */
 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.annotations.Expose;


/**
 * @author Miguel Salazar
 *
 */
public class Settings implements ActionListener,ChangeListener,WindowListener{
	
	
	private transient JDialog settingsDisplay;
	
	private transient JPanel currencyPicker;
	private transient JLabel currencyStatus;
	private transient JComboBox<String> currency;
	private transient String[] symbols;

	private transient JPanel texturePicker;
	private transient JLabel textureTitle;
	private transient JLabel textureChoice;
	private transient JButton textureSearch;
	private transient JButton textureImport;
	
	private transient JButton accept;
	
	@Expose private String texturePath = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/";
	
	@Expose private String sigil;
	
	
	
	public Settings(JFrame parent){
		settingsDisplay = new JDialog(parent, false);
		//superDialog = super;
		BorderLayout bl = new BorderLayout();
		bl.setHgap(5);
		bl.setVgap(5);
		settingsDisplay.setLayout(bl);
		defineCurrency();
		defineTexture();
		
		accept = new JButton("Ok");
		accept.addActionListener(this);
		
		settingsDisplay.add(currencyPicker, BorderLayout.NORTH);
		settingsDisplay.add(texturePicker, BorderLayout.CENTER);
		settingsDisplay.add(accept, BorderLayout.SOUTH);
		settingsDisplay.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		settingsDisplay.addWindowListener(this);
	}
	
	
	
	public void setup(){
		//settingsDisplay.setBounds(50, 50, 200, 200);
		settingsDisplay.pack();
		settingsDisplay.setResizable(false);
		settingsDisplay.setTitle("Settings");
		settingsDisplay.setModal(true);
		Image gear = new ImageIcon(System.getProperty("user.dir")+"/resources/game-assets/smallGear.png").getImage(); 
		
		settingsDisplay.setIconImage(gear);
		settingsDisplay.setVisible(true);
	}
	
	private void defineTexture(){
		texturePicker = new JPanel(new GridLayout(4,1));
		
		textureTitle = new JLabel("Selected Texture Pack");
		
		textureChoice = new JLabel("Default");
		
		textureSearch = new JButton("Select");
		textureSearch.addActionListener(this);
		
		textureImport = new JButton("Import");
		textureImport.addActionListener(this);
		
		texturePicker.add(textureTitle);
		texturePicker.add(textureChoice);
		texturePicker.add(textureSearch);
		texturePicker.add(textureImport);
		
		texturePicker.setPreferredSize(new Dimension(200,100) );
		
	}
	
	private void defineCurrency(){
		currencyPicker = new JPanel(new BorderLayout());
		currencyStatus = new JLabel("Select the currency symbol:");
		symbols = new String[30];
		symbols[0] = "$";
		symbols[1] = "€";
		symbols[2] = "£";
		symbols[3] = "ƒ";
		symbols[4] = "CHF";
		symbols[5] = "$b";
		symbols[6] = "KM";
		symbols[7] = "P";
		symbols[8] = "лв";
		symbols[9] = "R$";
		symbols[10] = "៛";
		symbols[11] = "¥";
		symbols[12] = "₡";
		symbols[13] = "₱";
		symbols[14] = "RD$";
		symbols[15] = "¢";
		symbols[16] = "﷼";
		symbols[17] = "₪";
		symbols[18] = "J$";
		symbols[19] = "₩";
		symbols[20] = "ден";
		symbols[21] = "₨";
		symbols[22] = "₮";
		symbols[23] = "₦";
		symbols[24] = "B/.";
		symbols[25] = "S/.";
		symbols[26] = "zł";
		symbols[27] = "lei";
		symbols[28] = "₫";
		symbols[29] = "฿";
		currency = new JComboBox<String>(symbols);
		sigil = (String)currency.getSelectedItem();
		currency.setPreferredSize(new Dimension(50,30));
		currency.addActionListener(this);
		currencyPicker.add(currencyStatus, BorderLayout.CENTER);
		currencyPicker.add(currency, BorderLayout.EAST);
	}
	
	public String getSigil(){
		//System.out.println("getSigil: "+sigil);
		return sigil;
	}
	
	public String textureMe(){
		return texturePath;
	}

	@Override
	public void stateChanged(ChangeEvent ce) {
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {/*I do nothing*/}

	@Override
	public void windowClosed(WindowEvent arg0) {/*I do nothing*/}

	@Override
	public void windowClosing(WindowEvent arg0) {
		settingsDisplay.setVisible(false);
		settingsDisplay.setModal(false);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {/*I do nothing*/}

	@Override
	public void windowDeiconified(WindowEvent arg0) {/*I do nothing*/}

	@Override
	public void windowIconified(WindowEvent arg0) {/*I do nothing*/}

	@Override
	public void windowOpened(WindowEvent arg0) {/*I do nothing*/}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(currency)){
			//System.out.println("statechange sigil: "+sigil);
			sigil = (String)currency.getSelectedItem();
		}else if(ae.getSource().equals(textureSearch)){
			discoverTextures();
		}else if(ae.getSource().equals(textureImport)){
			importZipTextures();
		}else if(ae.getSource().equals(accept)){
			settingsDisplay.dispose();
		}
	}



	private void importZipTextures() {
		File search;
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Compressed Folder","zip");
		jfc.setFileFilter(filter);
		int option = jfc.showOpenDialog(settingsDisplay);
		if(option == JFileChooser.APPROVE_OPTION){
			search = jfc.getSelectedFile();
		}else{
			return;
		}
		
		String path = search.getPath();
		System.out.println(search.getName());
		
		String trueName = search.getName();
		trueName = trueName.substring(0, trueName.lastIndexOf(".zip"));
		
		File home = new File(System.getProperty("user.dir")+"/resources/image-sets/"+trueName );
		home.mkdir();
		System.out.println(home.getName());
		
		UnzipUtility unzip = new UnzipUtility();
		try {
			unzip.unzip(path, ""+home);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void discoverTextures() {
		JDialog getme = new JDialog(settingsDisplay,"Pick your texture",true);
		JButton closeOut = new JButton("Ok");
		closeOut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getme.dispose();
			}
		});
		
		HashMap<String,File> names = new HashMap<String,File>();
		
		
		File setsFolder = new File(System.getProperty("user.dir")+"/resources/image-sets/");
		
		//names.put("default", new File(setsFolder+"default-image-set"));
		DefaultListModel<String> tListModel = new DefaultListModel<String>();
		
		names.put("Default", new File(setsFolder+"/default-image-set/"));
		tListModel.addElement("Default");
		for(File f : setsFolder.listFiles()){
			//System.out.println("settings file name search: "+f.getName());
			if(!f.getName().equals("default-image-set")){
				names.put(f.getName(),f);
				tListModel.addElement(f.getName());
			}
			
		}
		
		JList<String> tList = new JList<String>(tListModel);
		
		tList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tList.setSelectedIndex(0);
		
		JScrollPane tlistScroller = new JScrollPane(tList);
		
		JPanel fullPanel = new JPanel(new BorderLayout());
		fullPanel.add(tlistScroller,BorderLayout.CENTER);
		fullPanel.add(closeOut, BorderLayout.SOUTH);
		
		getme.add(fullPanel);
		
		getme.setResizable(false);
		getme.setPreferredSize(new Dimension(300,200));
		getme.pack();
		getme.setVisible(true);
		
		File getter = names.get(tList.getSelectedValue());
		
		texturePath = getter.getPath()+"/";
		//System.out.println("texturePath: "+texturePath);
		if(getter.getName().equals("default-image-set")){
			textureChoice.setText("Default");
		}else{
			textureChoice.setText(getter.getName());
		}
	}
	
	
	
}

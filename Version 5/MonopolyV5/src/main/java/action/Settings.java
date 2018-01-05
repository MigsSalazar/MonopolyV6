/**
 * 
 */
package main.java.action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	@Expose private String sigil;
	
	private String texturePath = System.getProperty("user.dir")+"/resources/image-sets/default-image-set/";
	
	public Settings(JFrame parent){
		settingsDisplay = new JDialog(parent, false);
		//superDialog = super;
		BorderLayout bl = new BorderLayout();
		bl.setHgap(5);
		bl.setVgap(5);
		settingsDisplay.setLayout(bl);
		defineCurrency();
		settingsDisplay.add(currencyPicker, BorderLayout.NORTH);
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
		}
	}
	
}

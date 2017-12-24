package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.action.Runner;
import main.java.models.Colored;
import main.java.models.Player;
import main.java.models.Property;

/**
 * Mortgage Manager. Controls and displays all parts of the mortgaging process
 * @author Miguel Salazar
 *
 */
public class MortManagerFrame extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9010214798423079244L;
	
	private Runner gameVars;
	
	private boolean mortOnly = false;
	
	private String[] options;
	private Player play;
	private Map<String,Property> props;
	private JPanel topPanel;
	private JPanel botPanel;
	private JPanel funcPanel;
	private JPanel labelPanel;
	private JButton ok;
	private JButton qu;
	private JComboBox<String> jcbProps;
	private JLabel status;
	private JLabel changeTo;
	private JLabel mort;
	private JPanel buttonPanel;
	private JButton mortgage;
	private JButton notmortgage;
	private String currentS;
	private Property currentP;
	private boolean currentB;
	private int currentI;
	
	/**
	 * Default constructor. Allows for properties to be
	 * mortgaged and unmortgaged
	 * @param frame	the parent JFrame
	 * @param p		Player object of the player who wishes to mortgage or unmortgage their properties
	 */
	public MortManagerFrame(Runner gv, Player p){
		super( gv.getFrame(), "Mortgage Manager", true);
		gameVars = gv;
		play = p;
		props = play.getProps();
		mortOnly=false;
		//System.out.println(props.toString());
		
		setOptions();
		if(options.length == 0){
			return;
		}
		
		//System.out.println(props.get(0).toString());
		currentI = 0;
		currentS = options[0];
		currentP = props.get(options[0]);
		currentB = currentP.isMortgaged();
		setupUI();
		addFunctionality();
		setBounds(100, 100, 350, 140);
		setResizable(false);
		setVisible(true);
		//setBounds(100, 100, 350, 140);
		//setResizable(false);
	}
	
	/**
	 * Constructor used when properties can only be mortgaged
	 * @param frame	the parent JFrame
	 * @param p		Player object of the player who wishes to mortgage or unmortgage their properties
	 * @param mo	boolean value denoting that the player can only mortgage their properties
	 */
	public MortManagerFrame(JFrame frame, Player p, boolean mo){
		super( frame, "Mortgage Manager", true);
		play = p;
		props = play.getProps();
		mortOnly=mo;
		//System.out.println(props.toString());
		
		setOptions();
		if(options.length == 0){
			return;
		}
		
		//System.out.println(props.get(0).toString());
		currentI = 0;
		currentS = options[0];
		currentP = props.get(options[0]);
		currentB = currentP.isMortgaged();
		setupUI();
		addFunctionality();
		setBounds(100, 100, 350, 140);
		setResizable(false);
	}
	
	/**
	 * Request child components to be created and added
	 */
	public void setupUI(){
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout(30,10));
		
		setTopPanel();
		c.add(topPanel, BorderLayout.NORTH);
		
		setBotPanel();
		c.add(botPanel, BorderLayout.CENTER);
		/*
		this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                System.out.println("DEBUG: componentResized- e="+e.toString());
            }
        });
        */
	}
	
	/**
	 * Determines which properties are valid to mortgage/unmortgage
	 */
	public void setOptions(){
		
		ArrayList<String> opt = new ArrayList<String>();
		for(String i : props.keySet()){
			if(mortOnly){
				if(!props.get(i).isMortgaged()){
					opt.add( props.get(i).getName() );
				}
			}else{
				if(props.get(i) instanceof Colored && ((Colored)props.get(i)).getGrade()==0){
					opt.add( props.get(i).getName() );
				}
			}
		}
		
		if(opt.size()==0){
			JOptionPane.showMessageDialog(gameVars.getFrame(), "You have no properties to mortgage.");
			this.dispose();
		}
		
		options = new String[opt.size()];
		for(int i=0; i<opt.size(); i++){
			options[i] = opt.get(i);
		}
	}
	
	/**
	 * Creates the top tool bar and labels and then adds them to the top Panel
	 */
	public void setTopPanel(){
		topPanel = new JPanel(new BorderLayout());
		
		funcPanel = new JPanel( new BorderLayout());
		
		ok = new JButton("OK");
		jcbProps = new JComboBox<String>(options);
		qu = new JButton("?");
		
		funcPanel.add(ok, BorderLayout.WEST);
		funcPanel.add(jcbProps, BorderLayout.CENTER);
		funcPanel.add(qu, BorderLayout.EAST);
		
		labelPanel = new JPanel( new BorderLayout(30,10) );
		
		status = new JLabel("Current Status");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		changeTo = new JLabel("Set to new Status");
		changeTo.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelPanel.add(status, BorderLayout.WEST);
		labelPanel.add(changeTo, BorderLayout.CENTER);
		
		topPanel.add(funcPanel, BorderLayout.NORTH);
		topPanel.add(labelPanel, BorderLayout.CENTER);
		
	}
	
	/**
	 * Creates the bottom label and JButtons then adds them to the parent bot Panel
	 */
	public void setBotPanel(){
		botPanel = new JPanel(new BorderLayout(10,10));
		
		mort = new JLabel(currentP.isMortgaged() ? "Mortgaged" : "Not Mortgaged");
		mort.setHorizontalAlignment(SwingConstants.CENTER);
		/*if(savedS.PROP1.getGrade()==propGrades[1]){
			propGrade1.setFont(this.getFont().deriveFont(Font.BOLD));
		}else{
			propGrade1.setFont(this.getFont().deriveFont(Font.PLAIN));
		}*/
		
		buttonPanel = new JPanel(new BorderLayout(0,10));
		
		mortgage = new JButton("Mortgaged");
		notmortgage = new JButton("Not Mortgaged");
		
		//System.out.println("DEBUG: currentB: "+currentB);
		if(!currentB){
			notmortgage.setEnabled(false);
		}else{
			mortgage.setEnabled(false);
		}
		
		buttonPanel.add(mortgage, BorderLayout.WEST);
		buttonPanel.add(notmortgage, BorderLayout.CENTER);
		updateButtons();
		
		botPanel.add(mort, BorderLayout.CENTER);
		botPanel.add(buttonPanel, BorderLayout.EAST);
		
	}
	
	/**
	 * Updates the tooltips of each button and enables/disables each button
	 */
	public void updateButtons(){
		mortgage.setToolTipText("Grants: $"+currentP.getMortgageValue());
		notmortgage.setToolTipText("Costs: $"+(currentP.getMortgageValue() + (int)(currentP.getMortgageValue()*0.1) ));
		if(currentB){
			mortgage.setEnabled(false);
			notmortgage.setEnabled(true);
		}else{
			mortgage.setEnabled(true);
			notmortgage.setEnabled(false);
		}
		
		if(mortOnly){
			notmortgage.setEnabled(false);
		}
		
	}
	
	/**
	 * Prompts the user to save their changes and then acts accordingly
	 */
	public void okButton(){
		int cost = currentP.getMortgageValue();
		int choice = 0;
		if(currentP.isMortgaged()!=currentB){
			if(currentP.isMortgaged()){
				//The Property was originally mortgaged
				cost += (int)cost*0.1;
				choice = JOptionPane.showConfirmDialog(gameVars.getFrame(), "You have decided to unmortgage"
																  + "\n"+currentP.getName()
																  + "\nDoing so will cost you"
																  + "\n$"+cost+"."
																  + "\nWish to continue?");
				if(choice==JOptionPane.YES_OPTION){
					currentP.setMortgage(currentB);
					play.subCash(cost);;
					JOptionPane.showMessageDialog(gameVars.getFrame(), currentP.getName()
															+ "\nhas been unmortgaged."
															+ "\nThe fund will be taken"
															+ "\nfrom your bank account.");
					gameVars.getFrame().getGameStats();
					
				}
			}else{
				//The Property was not mortgaged originally
				choice = JOptionPane.showConfirmDialog(gameVars.getFrame(), "You have decided to mortgage"
																 + "\n"+currentP.getName()
																 + "\nDoing so will refund you"
																 + "\n$"+cost+"."
																 + "\nWish to continue?");
				if(choice==JOptionPane.YES_OPTION){
					currentP.setMortgage(currentB);
					play.addCash(cost);
					JOptionPane.showMessageDialog(gameVars.getFrame(), currentP.getName()
															+ "\nhas been mortgaged."
															+ "\nThe funds will be added"
															+ "\nto your bank account.");
					gameVars.getFrame().getGameStats();
				}
			}
			
		}
	}
	
	/**
	 * Used within actionListeners. Propmpts a JOptionPane with the string send
	 * @param send	String declaring what to prompt the user
	 */
	public void post(String send){
		JOptionPane.showMessageDialog(gameVars.getFrame(), send);
	}
	
	/**
	 * Determines if the property has been changed
	 * @return	true=the property has been changed, false=the property has not been changed
	 */
	public boolean isChanged(){
		return !( currentB==currentP.isMortgaged() );
	}
	
	/**
	 * Adds actionListeners to the child components of the top and bot blocks
	 */
	public void addFunctionality(){
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				okButton();
			}

		});
		
		qu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//post("Work in progress");
				String send = "MORTGAGE MANAGER"
						  + "\nIn this frame, you are able to mortgage your"
						  + "\nproperties or lift the mortgage from them."
						  + "\nIn the drop down menu, you will find all of"
						  + "\nyour properties that are valid to be be"
						  + "\nmortgaged or have thier mortgage lifted."
						  + "\nTo the bottom left, you will see the current"
						  + "\nstatus of your property and to the right are"
						  + "\nbuttons to change the status. The disabled"
						  + "\nbutton shows which state, when saved, your"
						  + "\nproperty will be under. By mortgaging a property"
						  + "\nthe bank will grant you the mortgaged value"
						  + "\nwhich is half the original price of the proptery."
						  + "\nTo unmortgage a property, you must pay the"
						  + "\nmortgage value plus 10%. (Hover over the buttons"
						  + "\nto find out how much each is) You cannot switch"
						  + "\nproprtiesif you have made unsaved changes to the"
						  + "\ncurrent property. To save them, simply press the"
						  + "\nOK button where you will be prompted to save. Be"
						  + "\ncareful, if you save your changes, you cannot"
						  + "\nrevert them back meaning you will be charged"
						  + "\naccordingly. Saving your changes when no changes"
						  + "\nhave been made does nothing.";
				post(send);
				
			}
		});
		
		mortgage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentB = true;
				updateButtons();
				//mort.setText("Mortgaged");
			}
		});
		
		notmortgage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentB = false;
				updateButtons();
				//mort.setText("Not Mortgaged");
			}
		});
		
		jcbProps.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Getting change in jcb
				//iodb.cache("DEBUG: Option has changed!");
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				//Checking if change has occurred
		        if(  !currentS.equals( cb.getSelectedItem() )  ){
		        	//Determining if change is allowed
		        	if(isChanged()){
		        		//Change in suit is illegal
		        		post("You cannot edit another suit\nuntil you have saved the changes\nto this one or reset them.");
		        		cb.setSelectedIndex(currentI);
		        	}else{
		        		//Change in suit is legal
		        		//iodb.cache("DEBUG: Option change went through");
		        		//Changing necessary variables
		        		currentI = cb.getSelectedIndex();
		        		currentS = options[currentI];
		        		currentP = props.get(currentI);
		        		currentB = currentP.isMortgaged();
		        		updateButtons();
		        		//Updating tBlock and bBlock
		        	}
		        }
			}
		});
	}
	
}

/**
 * 
 */
package gameEvents;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import main.java.action.Runner;
import main.java.gui.EventPanel;

/**
 * @author Miguel Salazar
 *
 */
public class MainMenu extends AbstractEvent {
	
	private Runner bigMe;
	/**
	 * @param p
	 */
	public MainMenu(EventPanel p) {
		super(p);
		bigMe = p.getGlobalVars();
		text = "";
		defineComponents();
	}

	/**
	 * @param p
	 * @param t
	 */
	public MainMenu(EventPanel p, String t) {
		super(p, t);
		bigMe = p.getGlobalVars();
		defineComponents();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton me = (JButton)e.getSource();
			
			if(me.equals(buttons[0])){
				
			}else if(me.equals(buttons[1])){
				
			}else if(me.equals(buttons[2])){
				
			}else if(me.equals(buttons[3])){
				
			}
		}

	}

	/* (non-Javadoc)
	 * @see main.java.action.Events#defineComponents()
	 */
	@Override
	public void defineComponents() {
		buttons = new JComponent[4];
		/*
		 * 4 menu options
		 * Role-Upgrade-Trade-Mortgage
		 */
		buttons[0] = new JButton("Role");
		((JButton)buttons[0]).addActionListener(this);
		
		buttons[1] = new JButton("Upgrade");
		((JButton)buttons[1]).addActionListener(this);
		
		buttons[2] = new JButton("Trade");
		((JButton)buttons[2]).addActionListener(this);
		
		buttons[3] = new JButton("Mortgage");
		((JButton)buttons[3]).addActionListener(this);
		
	}

}

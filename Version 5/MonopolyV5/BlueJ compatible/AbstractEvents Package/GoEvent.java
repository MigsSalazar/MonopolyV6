 

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JButton;


public class GoEvent extends AbstractEvent {

	AbstractEvent root;
	
	public GoEvent(EventPanel p, Player pl, AbstractEvent root) {
		super(p);
		text = "<html>You have passed Go!<br>Collect "+parent.getCurrencySymbol()+"200!</html>";
		pl.addCash(200);
		this.root = root;
		defineComponents();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(buttons[0])){
			desync();
			parent.paintEvent(root);
		}
	}

	@Override
	public void defineComponents() {
		// TODO Auto-generated method stub
		buttons = new JComponent[1];
		buttons[0] = new JButton("Ok");
		((JButton)buttons[0]).addActionListener(this);
	}

}

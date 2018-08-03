package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListListener;

public class GameOverNotice extends AbstractNotice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameOverNotice(ListListener ppl) {
		super(ppl);
		text = "GAME OVER";
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	protected void defineActions() {
		actions = new JComponent[1];
		JLabel empty = new JLabel();
		actions[0] = empty;
	}

}

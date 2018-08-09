package edu.illinois.masalzr2.notices.implentations;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import edu.illinois.masalzr2.models.Player;
import edu.illinois.masalzr2.notices.AbstractNotice;
import edu.illinois.masalzr2.notices.ListListener;

public class GameOverNotice extends AbstractNotice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameOverNotice(ListListener ppl, Player winner, boolean turnsLimitReached) {
		super(ppl);
		text = "<html>Congradulations "+winner.getName()+"! You have won!";
		if( !turnsLimitReached ) {
			text += "<br>All other players have gone bankrupt!";
		}
		text += "<br>GAME OVER</html>";
		defineActions();
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

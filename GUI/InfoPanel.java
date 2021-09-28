package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Scacchiera;

public class InfoPanel extends JPanel {
	private JTextField text;
	public InfoPanel() {
		setBackground(Color.DARK_GRAY);
		setSize(300, 800);
		setPreferredSize(new Dimension(300, 800));
		text = new JTextField();
		setVisible(false);
	}
	public void aggiorna(Scacchiera nuova) {
		String turno = "Tocca al ";
		turno += nuova.isWhiteTurn() ? "bianco" : "nero";
		text.setText(turno);
		setVisible(false);
	}
}

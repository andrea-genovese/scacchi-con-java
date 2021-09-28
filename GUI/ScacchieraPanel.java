package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import main.Arrocco;
import main.Mossa;
import main.Scacchiera;
import pezzi.Donna;
import pezzi.Pedone;
import pezzi.Pezzo;
import pezzi.Re;

@SuppressWarnings("serial")
public class ScacchieraPanel extends JPanel {
	private CasaButton[][] btns = new CasaButton[8][8];
	private int width = 800, height = 800;
	private InterfacciaGrafica parent;
	int firstX = -1, firstY = -1;

	public ScacchieraPanel(Scacchiera scacchieraLogica, InterfacciaGrafica parent) {
		this.parent = parent;
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setLayout(new GridLayout(8, 8));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btns[i][j] = new CasaButton(i, j, this);
				add(btns[i][j]);
			}
		}
		// aggiorna(scacchieraLogica);
	}

	public void aggiorna(Scacchiera nuova) {
		for (int i = 0; i < btns.length; i++) {
			for (int j = 0; j < btns[0].length; j++) {
				Pezzo p = nuova.getArray()[i][j];
				if (p != null)
					btns[i][j].setIcon(p.getIcon(width / 8, height / 8));
				else
					btns[i][j].setIcon(null);
			}
		}
		setVisible(true);
	}

	public void provaMossa(int xDest, int yDest) {

		Scacchiera s = parent.scacchieraLogica;
		Pezzo pezzo = s.getArray()[firstY][firstX];
		if (pezzo == null)
			return;
		Mossa mossa = null;
		if (pezzo instanceof Pedone) {
			if (yDest == 0 || yDest == 7)
				mossa = new Mossa(s, firstX, firstY, xDest, yDest, new Donna(xDest, yDest, pezzo.isWhite, s));
			else if (firstX != xDest && s.getArray()[yDest][xDest] == null) {
				mossa = new Mossa(s, firstX, firstY, xDest, yDest, Scacchiera.convertSquare(xDest, yDest));
			} else
				mossa = new Mossa(s, firstX, firstY, xDest, yDest);
		} else if (pezzo instanceof Re) {
			if (Math.abs(xDest - firstX) > 1) {
				if (xDest == 6) {
					if(pezzo.isWhite && yDest == 7)
						mossa = new Arrocco(s, firstX, firstY, true, true);
					else if(!pezzo.isWhite && yDest == 0)
						mossa = new Arrocco(s, firstX, firstY, false, true);
					else return;
				} else if(xDest == 2){
					if(pezzo.isWhite && yDest == 7)
						mossa = new Arrocco(s, firstX, firstY, true, false);
					else if(!pezzo.isWhite && yDest == 0)
						mossa = new Arrocco(s, firstX, firstY, false, false);
					else return;
				}
				else return;
			} else
				mossa = new Mossa(s, firstX, firstY, xDest, yDest);
		} else {
			mossa = new Mossa(s, firstX, firstY, xDest, yDest);
		}
		if (!mossa.eseguibile()) {
			System.out.println(mossa + " non e' una mossa eseguibile");
		} else {

			parent.scacchieraLogica = mossa.eseguiMossa();
			System.out.println(mossa);
			System.out.println(parent.scacchieraLogica.getMosseScacchiera());
			parent.aggiorna();
		}
	}
}

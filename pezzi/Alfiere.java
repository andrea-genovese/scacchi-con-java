package pezzi;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Mossa;
import main.Scacchiera;

public class Alfiere extends Pezzo {
	
	public Alfiere(int x, int y, boolean isWhite, Scacchiera s) {
		super(x, y, isWhite, s);
		if(isWhite) {
			icon= new ImageIcon("./Icone/AlfiereBianco.png");
		} else {
			icon = new ImageIcon("./Icone/AlfiereNero.png");
		}
	}

	@Override
	public ArrayList<Mossa> getMosse() {
		return getMosseAlfiere();
	}

	@Override
	public char getChar() {
		return 'B';
	}

	@Override
	public double getValore() {
		return 3.15;
	}

}

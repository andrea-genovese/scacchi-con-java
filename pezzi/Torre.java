package pezzi;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Mossa;
import main.Scacchiera;

public class Torre extends Pezzo {

	public Torre(int x, int y, boolean isWhite, Scacchiera s) {
		super(x, y, isWhite, s);
		if(isWhite) {
			icon= new ImageIcon("./Icone/TorreBianca.png");
		} else {
			icon = new ImageIcon("./Icone/TorreNera.png");
		}
	}

	@Override
	public ArrayList<Mossa> getMosse() {
		return getMosseTorre();
	}
	

	@Override
	public char getChar() {
		return 'R';
	}

	@Override
	public double getValore() {
		return 5;
	}

}

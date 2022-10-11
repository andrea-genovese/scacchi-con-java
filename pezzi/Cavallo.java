package pezzi;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Mossa;
import main.Scacchiera;

public class Cavallo extends Pezzo {

	public Cavallo(int x, int y, boolean isWhite, Scacchiera s) {
		super(x, y, isWhite, s);
		if(isWhite) {
			icon= new ImageIcon("./Icone/CavalloBianco.png");
		} else {
			icon = new ImageIcon("./Icone/CavalloNero.png");
		}
	}

	@Override
	public ArrayList<Mossa> getMosse() {
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();
		addIfValid(mosse, x-1, y-2);
		addIfValid(mosse, x+1, y-2);
		
		addIfValid(mosse, x+2, y-1);
		addIfValid(mosse, x+2, y+1);
		
		addIfValid(mosse, x+1, y+2);
		addIfValid(mosse, x-1, y+2);
		
		addIfValid(mosse, x-2, y+1);
		addIfValid(mosse, x-2, y-1);
		return mosse;
	}

	@Override
	public char getChar() {
		return 'N';
	}

	@Override
	public double getValore() {
		return 3;
	}

}

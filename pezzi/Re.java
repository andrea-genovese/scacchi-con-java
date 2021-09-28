package pezzi;

import java.util.ArrayList;
import javax.swing.ImageIcon;

import main.Arrocco;
import main.Mossa;
import main.Scacchiera;

public class Re extends Pezzo {

	public Re(int x, int y, boolean isWhite, Scacchiera s) {
		super(x, y, isWhite, s);
		if(isWhite) {
			icon= new ImageIcon("src/Icone/ReBianco.png");
		} else {
			icon = new ImageIcon("src/Icone/ReNero.png");
		}
	}

	@Override
	public ArrayList<Mossa> getMosse() {
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();
		addIfValid(mosse, x-1, y-1); 
		addIfValid(mosse, x-1, y);
		addIfValid(mosse, x-1, y+1);
		
		addIfValid(mosse, x, y-1);
		addIfValid(mosse, x, y+1);
		
		addIfValid(mosse, x+1, y-1);
		addIfValid(mosse, x+1, y);
		addIfValid(mosse, x+1, y+1);
		if(isWhite) {
			if(s.arrocco.indexOf('K') != -1 && s.getArray()[7][5] == null && s.getArray()[7][6] == null) {
				mosse.add(new Arrocco(s, x, y, isWhite, true));
			}
			if(s.arrocco.indexOf('Q') != -1 && s.getArray()[7][1] == null 
					&& s.getArray()[7][2] == null && s.getArray()[7][3] == null) {
				mosse.add(new Arrocco(s, x, y, isWhite, false));
			}
		}
		else {
			if(s.arrocco.indexOf('k') != -1 && s.getArray()[0][5] == null && s.getArray()[0][6] == null) {
				mosse.add(new Arrocco(s, x, y, isWhite, true));
			}
			if(s.arrocco.indexOf('q') != -1 && s.getArray()[0][1] == null 
					&& s.getArray()[0][2] == null && s.getArray()[0][3] == null) {
				mosse.add(new Arrocco(s, x, y, isWhite, false));
			}
		}
		return mosse;
	}

	@Override
	public char getChar() {
		return 'K';
	}

	@Override
	public double getValore() {
		return Double.POSITIVE_INFINITY;
	}
	
}

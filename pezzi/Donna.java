package pezzi;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import main.Mossa;
import main.Scacchiera;

public class Donna extends Pezzo {

	public Donna(int x, int y, boolean isWhite, Scacchiera s) {
		super(x, y, isWhite, s);
		if(isWhite) {
			icon= new ImageIcon("./Icone/DonnaBianca.png");
		} else {
			icon = new ImageIcon("./Icone/DonnaNera.png");
		}
	}

	@Override
	public ArrayList<Mossa> getMosse() {
		ArrayList<Mossa> mosse = getMosseAlfiere();
		mosse.addAll(getMosseTorre());
		return mosse;
	}

	@Override
	public char getChar() {
		return 'Q';
	}

	@Override
	public double getValore() {
		return 9;
	}

}

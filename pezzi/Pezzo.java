package pezzi;
import java.awt.Image;
import java.util.ArrayList;
import main.Mossa;
import javax.swing.ImageIcon;
import main.Scacchiera;
/**Questa classe rappresenta un pezzo generico non istanziabile su una {@code Scacchiera}
 *
 */
public abstract class Pezzo {
	protected ImageIcon icon;
	public int x;
	public int y;
	public boolean isWhite;
	protected Scacchiera s;
	public Pezzo(int x, int y, boolean isWhite, Scacchiera s) {
		super();
		this.x = x;
		this.y = y;
		this.isWhite = isWhite;
		this.s = s;
	}
	/**Restituisce una lista di mosse possibili senza tenere conto dello scacco
	 * 
	 * @return una {@code ArrayList} di Mosse possibili
	 */
	public abstract ArrayList<Mossa> getMosse();
	public abstract char getChar();
	
	/**Aggiunge la mossa alla lista se la casa di destinazione esiste e non ha sopra un pezzo amico 
	 * @param list la lista delle mosse
	 * @param xDest la coordinata x di arrivo
	 * @param yDest la coordinata y di arrivo*/
	protected void addIfValid(ArrayList<Mossa> list, int xDest, int yDest) {
		addIfValid(list, xDest, yDest, null, null);
	}
	protected void addIfValid(ArrayList<Mossa> list, int xDest, int yDest, Pezzo promozione) {
		addIfValid(list, xDest, yDest, null, promozione);
	}
	protected void addIfValid(ArrayList<Mossa> list, int xDest, int yDest, String enPassant) {
		addIfValid(list, xDest, yDest, enPassant, null);
	}
	private void addIfValid(ArrayList<Mossa> list, int xDest, int yDest, String enPassant, Pezzo promozione) {
		if(Scacchiera.isValidSquare(xDest, yDest)) {
			Pezzo arr = this.s.getArray()[yDest][xDest];
			if(arr == null || arr.isWhite != this.isWhite)
				list.add(new Mossa(s, x, y, xDest, yDest, enPassant, promozione));
			}
	}
	public ImageIcon getIcon(int w, int h) {
		ImageIcon icona = new ImageIcon(icon.getImage().getScaledInstance(w, h,Image.SCALE_SMOOTH));
		return icona;
	}
	
	protected ArrayList<Mossa> getMosseTorre(){
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();
		int xStart = x+1;
		int yStart = y;
		while(Scacchiera.isValidSquare(xStart, y) && s.getArray()[y][xStart] == null) { //verso destra
			addIfValid(mosse, xStart, y);
			xStart++;
		}
		addIfValid(mosse, xStart, y);
		
		xStart = x-1;
		while(Scacchiera.isValidSquare(xStart, y) && s.getArray()[y][xStart] == null) { //verso sinistra
			addIfValid(mosse, xStart, y);
			xStart--;
		}
		addIfValid(mosse, xStart, y);
		
		yStart = y+1;
		while(Scacchiera.isValidSquare(x, yStart) && s.getArray()[yStart][x] == null) { //verso l'alto
			addIfValid(mosse, x, yStart);
			yStart++;
		}
		addIfValid(mosse, x, yStart);
		
		yStart = y-1;
		while(Scacchiera.isValidSquare(x, yStart) && s.getArray()[yStart][x] == null) { //verso il basso
			addIfValid(mosse, x, yStart);
			yStart--;
		}
		addIfValid(mosse, x, yStart);
		
		return mosse;
	}
	public abstract double getValore();
	protected ArrayList<Mossa> getMosseAlfiere(){
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();
		int xStart = x - 1;
		int yStart = y - 1;
		while(Scacchiera.isValidSquare(xStart, yStart) && s.getArray()[yStart][xStart] == null) { //verso alto-sinistra
			addIfValid(mosse, xStart, yStart);
			xStart--;
			yStart--;
		}
		addIfValid(mosse, xStart, yStart);
		
		xStart = x + 1;
		yStart = y - 1;
		while(Scacchiera.isValidSquare(xStart, yStart) && s.getArray()[yStart][xStart] == null) { //verso alto-destra
			addIfValid(mosse, xStart, yStart);
			xStart++;
			yStart--;
		}
		addIfValid(mosse, xStart, yStart);
		
		xStart = x - 1;
		yStart = y + 1;
		while(Scacchiera.isValidSquare(xStart, yStart) && s.getArray()[yStart][xStart] == null) { //verso basso-sinistra
			addIfValid(mosse, xStart, yStart);
			xStart--;
			yStart++;
		}
		addIfValid(mosse, xStart, yStart);
		
		xStart = x + 1;
		yStart = y + 1;
		while(Scacchiera.isValidSquare(xStart, yStart) && s.getArray()[yStart][xStart] == null) { //verso basso-destra
			addIfValid(mosse, xStart, yStart);
			xStart++;
			yStart++;
		}
		addIfValid(mosse, xStart, yStart);
		
		return mosse;
	}
	public boolean puoCatturare(int x, int y) {
		if(!Scacchiera.isValidSquare(x, y) || s.getArray()[y][x] == null) {
			System.out.println("Pezzo.puoCatturare errore parametri");
			System.out.println("x= "+x+", y= "+y);
			return false;
		}
		if(s.getArray()[y][x].isWhite == this.isWhite) {
			return false;
		}
		return this.getMosse().contains(new Mossa(s, this.x, this.y, x, y));
	}
	public Pezzo clone(Scacchiera nuova) {
		if(this instanceof Re) {
			return new Re(x, y, isWhite, nuova);
		} else if(this instanceof Donna) {
			return new Donna(x, y, isWhite, nuova);
		} else if(this instanceof Torre) {
			return new Torre(x, y, isWhite, nuova);
		} else if(this instanceof Alfiere) {
			return new Alfiere(x, y, isWhite, nuova);
		} else if(this instanceof Cavallo) {
			return new Cavallo(x, y, isWhite, nuova);
		} else if(this instanceof Pedone) {
			return new Pedone(x, y, isWhite, nuova);
		} else throw new IllegalCallerException("this non � istanza di nessun pezzo");
		
	}
 	@Override
	public String toString() {
		String str = this.getChar() + " [";
		str += Scacchiera.convertSquare(x, y) +", ";
		str += isWhite ? "bianco" : "nero";
		str +="]";
		return str;
	}
}

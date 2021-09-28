package main;
import pezzi.Pedone;
import pezzi.Pezzo;
import pezzi.Re;
import pezzi.Torre;

public class Mossa {
	public int xStart, yStart, xDest, yDest;
	private Pezzo promozione;
	private String enPassant;
	Scacchiera s;
	public Mossa(Scacchiera s, int xStart, int yStart, int xDest, int yDest) {
		this(s, xStart, yStart, xDest, yDest, null, null);		
	}
	public Mossa(Scacchiera s, int xStart, int yStart, int xDest, int yDest, Pezzo promozione) {
		this(s, xStart, yStart, xDest, yDest, null, promozione);
	}
	public Mossa(Scacchiera s, int xStart, int yStart, int xDest, int yDest, String enPassant, Pezzo promozione) {
		if(!Scacchiera.isValidSquare(xStart, yStart) || 
				!Scacchiera.isValidSquare(xDest, yDest)) {
				throw new IllegalArgumentException("Casa non esistente");
			}
			if (xStart == xDest && yStart == yDest) {
				System.out.println("Casa di partenza uguale ad arrivo");
			}
			if (s.getArray()[yStart][xStart] instanceof Pedone && (yStart == 7 || yStart == 0)) {
				System.out.println("non si pu� avere un pedone sulla prima o ultima traversa");
			}
			if(enPassant != null && Scacchiera.convertSquare(enPassant)[1] != 2 && Scacchiera.convertSquare(enPassant)[1] != 5) {
				System.out.println("Si pu� avere en passant solo su terza o sesta traversa");
			}
			this.xStart = xStart;
			this.yStart = yStart;
			this.xDest = xDest;
			this.yDest = yDest;
			this.s = s;
			this.enPassant = enPassant;
			this.promozione = promozione;
	}
	public Mossa(Scacchiera s, int xStart, int yStart, int xDest, int yDest, String enPassant) {
		this(s, xStart, yStart, xDest, yDest, enPassant, null);
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mossa other = (Mossa) obj;
		
		if (xDest != other.xDest)
			return false;
		if (xStart != other.xStart)
			return false;
		if (yDest != other.yDest)
			return false;
		if (yStart != other.yStart)
			return false;
		return true;
	}
	@Override
	public String toString() {		
		String str = "Mossa [";
		char c = s.getArray()[yStart][xStart].getChar();
		if(c == 0 && xStart != xDest) {
			c =(char) (xStart + 'a');
		}
		if(c != 0) str += c;
		if(enPassant != null || s.getArray()[yDest][xDest] != null) {
			str += "x";
		}
		str += Scacchiera.convertSquare(xDest, yDest);
		if(promozione != null) {
			str += "=";
			str += promozione.getChar();
		}
		return str + "]";
	}
	public Scacchiera eseguiMossa() {		
		Scacchiera nuova = new Scacchiera(s);
		Pezzo p = s.getArray()[yStart][xStart];
		if(p == null) {
			System.out.println(xStart+","+yStart+" è null");
		}
		nuova.getArray()[yDest][xDest] = p.clone(nuova);
		nuova.getArray()[yDest][xDest].x = xDest;
		nuova.getArray()[yDest][xDest].y = yDest;
		nuova.getArray()[yStart][xStart] = null;
		if(promozione != null) {
			nuova.getArray()[yDest][xDest] = promozione;
		}
		//eliminazione possibilità di arroccare
		if(p instanceof Re) {
			nuova.rimuoviArrocco(p.isWhite);
		}
		if(p instanceof Torre) {
			if(p.y == 0) {
				if(p.x == 0)
					nuova.rimuoviArrocco("q");
				else if(p.x == 7)
					nuova.rimuoviArrocco("k");
			} else if(p.y == 7) {
				if(p.x == 0)
					nuova.rimuoviArrocco("Q");
				else if(p.x == 7)
					nuova.rimuoviArrocco("K");
			}
		}
		//eliminazione pezzo preso en passsant
		if(enPassant != null) {
			int[] enP = Scacchiera.convertSquare(enPassant);
			enP[1] = (enP[1] == 2) ? 3 : 4; 
			nuova.getArray()[enP[1]][enP[0]] = null;
		} //impostazione di en passant possibile
		if(s.getArray()[yStart][xStart] instanceof Pedone && Math.abs(yStart-yDest) == 2) {
			int yEnPassant = p.isWhite ? yDest + 1 : yDest - 1;
			nuova.enPassant = Scacchiera.convertSquare(xDest, yEnPassant);
		}
		else {
			nuova.enPassant = "-";
		}
		nuova.setTurn(!s.isWhiteTurn());
		return nuova;
	}
	public boolean eseguibile() {
		return s.getMosseScacchiera().contains(this);
	}
	
}

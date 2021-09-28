package main;

public class Arrocco extends Mossa {
	private boolean isShort;
	private boolean isWhite;
	public Arrocco(Scacchiera s, int xStart, int yStart, boolean isWhite, boolean isShort) {
		super(s, xStart, yStart, 0, 0 );
		if(isWhite) {
			if(isShort) {
				this.xDest = 6;
				this.yDest = 7;
			}
			else {
				this.xDest = 2;
				this.yDest = 7;
			}
		}
		else {
			if(isShort) {
				this.xDest = 6;
				this.yDest = 0;
			}
			else {
				this.xDest = 2;
				this.yDest = 0;
			}
		}
		this.isShort = isShort;
		this.isWhite = isWhite;
	}
	@Override
	public Scacchiera eseguiMossa() {
		Scacchiera nuova = new Scacchiera(s);		
		nuova.getArray()[yDest][xDest] = nuova.getArray()[yStart][xStart];
		nuova.getArray()[yDest][xDest].x = xDest;
		nuova.getArray()[yDest][xDest].y = yDest;
		nuova.getArray()[yStart][xStart] = null;
		nuova.rimuoviArrocco(isWhite);
		//bianco
		if(isWhite) {
			
			//bianco corto
			if(isShort) {
				nuova.getArray()[7][5] = nuova.getArray()[7][7];
				nuova.getArray()[7][5].x = 5;
				nuova.getArray()[7][5].y = 7;
				nuova.getArray()[7][7] = null;
				
			}
			//bianco lungo
			else {
				nuova.getArray()[7][3] = nuova.getArray()[7][0];
				nuova.getArray()[7][3].x = 3;
				nuova.getArray()[7][3].y = 7;
				nuova.getArray()[7][0] = null;
			}
			
		}
		//nero
		else {
			//nero corto
			if(isShort) {
				nuova.getArray()[0][5] = nuova.getArray()[0][7];
				nuova.getArray()[0][5].x = 5;
				nuova.getArray()[0][5].y = 0;
				nuova.getArray()[0][7] = null;
			} //nero lungo
			else {
				nuova.getArray()[0][3] = nuova.getArray()[0][0];
				nuova.getArray()[0][3].x = 3;
				nuova.getArray()[0][3].y = 0;
				nuova.getArray()[0][0] = null;
			}
		}
		nuova.setTurn(!s.isWhiteTurn());
		return nuova;
	}
	
	@Override
	public String toString() {
		if(isShort) {
			return "Mossa [O-O]";
		}
		else {
			return "Mossa [O-O-O]";
		}
	}
}

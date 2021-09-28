package pezzi;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Mossa;
import main.Scacchiera;

public class Pedone extends Pezzo {

	public Pedone(int x, int y, boolean isWhite, Scacchiera s) {
		super(x, y, isWhite, s);
		if(isWhite) {
			icon= new ImageIcon("src/Icone/PedoneBianco.png");
		} else {
			icon = new ImageIcon("src/Icone/PedoneNero.png");
		}
	}

	@Override
	public ArrayList<Mossa> getMosse() {
		if(this.y == 0 || this.y == 7) {
			throw new IllegalCallerException("non può esserci un pedone sulla prima o ultima traversa"+this.toString());
		}
		if(this.isWhite) {
			return getMosseBianco();
		} else {
			return getMosseNero();
		}
	}
	private ArrayList<Mossa> getMosseBianco(){
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();		
		if(s.getArray()[y-1][x] == null) {
				if(y-1 == 0) {
					mosse.add(new Mossa(s, x, y, x, y-1, new Donna(x, y-1, isWhite, s)));
					mosse.add(new Mossa(s, x, y, x, y-1, new Torre(x, y-1, isWhite, s)));
					mosse.add(new Mossa(s, x, y, x, y-1, new Alfiere(x, y-1, isWhite, s)));
					mosse.add(new Mossa(s, x, y, x, y-1, new Cavallo(x, y-1, isWhite, s)));
				}else{addIfValid(mosse, x, y-1);}
		}
		if(y == 6 && s.getArray()[y-1][x] == null) { //prima mossa
			addIfValid(mosse, x, y-2);
		}
		//catture
		if(Scacchiera.isValidSquare(x-1, y-1) && s.getArray()[y-1][x-1] != null) { 
			if(y-1 == 0) {
				addIfValid(mosse, x-1, y-1, new Donna(x-1, y-1, isWhite, s));
				addIfValid(mosse, x-1, y-1, new Torre(x-1, y-1, isWhite, s));
				addIfValid(mosse, x-1, y-1, new Alfiere(x-1, y-1, isWhite, s));
				addIfValid(mosse, x-1, y-1, new Cavallo(x-1, y-1, isWhite, s));
			}else{addIfValid(mosse, x-1, y-1);}
			
		}
		if(Scacchiera.isValidSquare(x+1, y-1) && s.getArray()[y-1][x+1] != null) { 
			if(y-1 == 0) {
				addIfValid(mosse, x+1, y-1, new Donna(x+1, y-1, isWhite, s));
				addIfValid(mosse, x+1, y-1, new Torre(x+1, y-1, isWhite, s));
				addIfValid(mosse, x+1, y-1, new Alfiere(x+1, y-1, isWhite, s));
				addIfValid(mosse, x+1, y-1, new Cavallo(x+1, y-1, isWhite, s));
			}else{addIfValid(mosse, x+1, y-1);}
		}
		//enPassant
		if(!s.enPassant.equals("-")) {
			int[] enPassantInt = Scacchiera.convertSquare(s.enPassant);
			if(enPassantInt[1] == y-1){
				if(enPassantInt[0] == x-1) {
					addIfValid(mosse, x-1, y-1, s.enPassant);
				} else if(enPassantInt[0] == x+1) {
					addIfValid(mosse, x+1, y-1, s.enPassant);
				}
			}
		}
		
		
		return mosse;
	}
	private ArrayList<Mossa> getMosseNero(){
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();
		if(s.getArray()[y+1][x] == null) {
				if(y+1 == 7) {
					mosse.add(new Mossa(s, x, y, x, y+1, new Donna(x, y+1, isWhite, s)));
					mosse.add(new Mossa(s, x, y, x, y+1, new Torre(x, y+1, isWhite, s)));
					mosse.add(new Mossa(s, x, y, x, y+1, new Alfiere(x, y+1, isWhite, s)));
					mosse.add(new Mossa(s, x, y, x, y+1, new Cavallo(x, y+1, isWhite, s)));
				}else{addIfValid(mosse, x, y+1);}
		}
		if(y == 1 && s.getArray()[y+1][x] == null) { //prima mossa
			addIfValid(mosse, x, y+2);
		}
		//catture
		if(Scacchiera.isValidSquare(x-1, y+1) && s.getArray()[y+1][x-1] != null) { 
			if(y+1 == 7) {
				addIfValid(mosse, x-1, y+1, new Donna(x-1, y+1, isWhite, s));
				addIfValid(mosse, x-1, y+1, new Torre(x-1, y+1, isWhite, s));
				addIfValid(mosse, x-1, y+1, new Alfiere(x-1, y+1, isWhite, s));
				addIfValid(mosse, x-1, y+1, new Cavallo(x-1, y+1, isWhite, s));
			}else{addIfValid(mosse, x-1, y+1);}
			
		}
		if(Scacchiera.isValidSquare(x+1, y+1) && s.getArray()[y+1][x+1] != null) { 
			if(y+1 == 7) {
				addIfValid(mosse, x+1, y+1, new Donna(x+1, y+1, isWhite, s));
				addIfValid(mosse, x+1, y+1, new Torre(x+1, y+1, isWhite, s));
				addIfValid(mosse, x+1, y+1, new Alfiere(x+1, y+1, isWhite, s));
				addIfValid(mosse, x+1, y+1, new Cavallo(x+1, y+1, isWhite, s));
			}else{addIfValid(mosse, x+1, y+1);}
		}
		//enPassant
		if(s.enPassant != null && !s.enPassant.equals("-")) {
			int[] enPassantInt = Scacchiera.convertSquare(s.enPassant);
			if(enPassantInt[1] == y+1){
				if(enPassantInt[0] == x-1) {
					addIfValid(mosse, x-1, y+1, s.enPassant);
				} else if(enPassantInt[0] == x+1) {
					addIfValid(mosse, x+1, y+1, s.enPassant);
				}
			}
		}
		
		
		return mosse;
	}

	@Override
	public char getChar() {
		return 0;
	}

	@Override
	public double getValore() {
		return 1;
	}
}

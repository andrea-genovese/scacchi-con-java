package main;

import java.util.ArrayList;

import pezzi.*;

public class Scacchiera {
	private static final String POSIZIONE_INIZIALE = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private Pezzo[][] array = new Pezzo[8][8];
	private boolean turnoWhite;
	public String arrocco;
	public String enPassant = "-";
	private int mosse, semimosse;

	public Scacchiera(Scacchiera s) {
		for (int i = 0; i < s.array.length; i++) {
			for (int j = 0; j < s.array.length; j++) {
				Pezzo pez = s.array[i][j];
				if (pez != null)
					this.array[i][j] = pez.clone(this);
			}
			this.turnoWhite = s.turnoWhite;
			this.arrocco = s.arrocco;
			this.enPassant = s.enPassant;
			this.mosse = s.mosse;
			this.semimosse = s.semimosse;
		}
	}

	public Scacchiera(String Fen) {
		int riga = 0;
		int colonna = 0;
		int i = 0;
		for (; i < Fen.length() && Fen.charAt(i) != ' '; i++, colonna++) {
			char c = Fen.charAt(i);
			if (c > '0' && c < '9') {
				colonna += c - '0' - 1;
				continue;
			}
			if (c == '/') {
				riga++;
				colonna = -1;
			} else {
				array[riga][colonna] = Pezzo.createFromChar(colonna, riga, c, this);
			}

		}
		String status = Fen.substring(i);
		if (status.length() == 0) {
			turnoWhite = true;
			arrocco = "KQkq";
			enPassant = "-";
			semimosse = 0;
			mosse = 1;
		} else {
			turnoWhite = status.charAt(1) == 'w';
			status = status.substring(3, status.length());
			arrocco = status.substring(0, status.indexOf(' '));
			status = status.substring(status.indexOf(' ') + 1, status.length());
			enPassant = status.substring(0, status.indexOf(' '));
			status = status.substring(status.indexOf(' ') + 1, status.length());
			semimosse = Integer.parseInt(status.substring(0, status.indexOf(' ')));
			status = status.substring(status.indexOf(' ') + 1,
					status.lastIndexOf(' ') != status.indexOf(' ') ? status.lastIndexOf(' ') : status.length());
			mosse = Integer.parseInt(status);
		}
	}

	public Scacchiera() {
		this(POSIZIONE_INIZIALE);
	}

	public Pezzo[][] getArray() {
		return array;
	}

	public boolean isWhiteTurn() {
		return turnoWhite;
	}

	public void setTurn(boolean isWhiteTurn) {
		turnoWhite = isWhiteTurn;
	}

	public static boolean isValidSquare(int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return false;
		}
		return true;
	}

	public void rimuoviArrocco(String c) {
		arrocco = arrocco.replace(c, "");
		if (arrocco.length() == 0)
			arrocco = "-";
	}

	public void rimuoviArrocco(boolean isWhite) {
		rimuoviArrocco(isWhite ? "KQ" : "kq");
	}

	public static String convertSquare(int x, int y) {
		String str = "";
		char colonna = (char) (x + 'a');
		char traversa = (char) (8 - y + '0');
		str += colonna;
		return str + traversa;
	}

	/**
	 * converte la stringa che rappresenta una casa in un array di int
	 * 
	 * @param s una stringa che rappresenta una casa
	 * @return arr[0] = x e arr[1] = y
	 */
	public static int[] convertSquare(String s) {
		if (s.length() != 2) {
			return null;
		}
		int[] arr = new int[2];
		arr[0] = s.charAt(0) - 'a';
		arr[1] = 8 - (s.charAt(1) - '0');
		return arr;
	}

	public void checkEnd() throws EndException {
		if (getMosseScacchiera().size() == 0) {
			if (reSottoScacco(turnoWhite))
				throw new ScaccoMattoException(!turnoWhite);
			else
				throw new StalloException(!turnoWhite);
		}
		if (semimosse >= 50)
			throw new SemimosseException();
	}

	public ArrayList<Mossa> getMosseScacchiera() {
		ArrayList<Mossa> mosse = new ArrayList<Mossa>();
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[i][j] != null && array[i][j].isWhite == turnoWhite) {
					ArrayList<Mossa> mossePezzo = array[i][j].getMosse();
					for (Mossa mossa : mossePezzo) {
						Scacchiera mossaEseguita = mossa.eseguiMossa();

						if (!mossaEseguita.reSottoScacco(turnoWhite)) {
							mosse.add(mossa);
						}
					}
				}
			}
		}
		return mosse;
	}

	public boolean reSottoScacco(boolean isWhite) {
		int[] posRe = getPosizioneRe(isWhite);
		for (Pezzo[] riga : array) {
			for (Pezzo p : riga) {
				if (p != null && p.puoCatturare(posRe[0], posRe[1]))
					return true;
			}
		}
		return false;
	}

	/**
	 * restituisce la posizione del re di un determinato colore
	 * 
	 * @param isWhite il colore del re
	 * @return un array di int in cui il primo elemento � la {@code x} del re e il
	 *         secondo � la {@code y}
	 */
	public int[] getPosizioneRe(boolean isWhite) {
		int[] pos = new int[2];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[i][j] != null && array[i][j].getClass().equals(Re.class) && array[i][j].isWhite == isWhite) {
					pos[0] = j;
					pos[1] = i;
					return pos;
				}
			}
		}
		throw new IllegalArgumentException("Re non trovato, colore=" + isWhite);
	}

	@Override
	public String toString() {

		String str = "";
		for (int i = 0; i < this.array.length; i++) {
			str += getFenRiga(array[i]);
			if (i != array.length - 1)
				str += '/';
		}
		str += ' ';
		str += turnoWhite ? 'w' : 'b';
		str += " " + arrocco + " " + enPassant + " " + Integer.toString(semimosse) + " " + Integer.toString(mosse);

		return str;
	}

	private static String getFenRiga(Pezzo[] arr) {
		String str = "";
		int counter = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null)
				counter++;
			else {
				if (counter != 0)
					str += Integer.toString(counter);
				counter = 0;
				char c = arr[i].getChar();
				if (c == 0) {
					c = 'P';
				}
				if (!arr[i].isWhite)
					c = (char) (c + 32);
				str += c;
			}
		}
		if (counter != 0)
			str += Integer.toString(counter);
		if (str.equals(""))
			str = "8";
		return str;
	}

}

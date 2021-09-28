package main;

public class StalloException extends EndException {
	public boolean haDatoStallo;

	public StalloException(boolean haDatoStallo) {
		this.haDatoStallo = haDatoStallo;
	}
	
}

package main;

@SuppressWarnings("serial")
public class ScaccoMattoException extends EndException {
	public boolean winner;

	public ScaccoMattoException(boolean winner) {
		this.winner = winner;
	}
	
}

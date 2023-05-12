package GUI;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import main.EndException;
import main.Scacchiera;
import main.ScaccoMattoException;
import main.SemimosseException;
import main.StalloException;

@SuppressWarnings("serial")
public class InterfacciaGrafica extends JFrame {
	Scacchiera scacchieraLogica;
	private ScacchieraPanel pannelloScacchiera;
	private InfoPanel info;
	public InterfacciaGrafica(Scacchiera scacchieraLogica) {
		this.scacchieraLogica = scacchieraLogica;
		this.pannelloScacchiera = new ScacchieraPanel(scacchieraLogica, this);
		info = new InfoPanel();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(pannelloScacchiera);
		add(info);
		setTitle("Scacchi");
		setLayout(new FlowLayout());
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		aggiorna();
	}
	public InterfacciaGrafica() {
		this(new Scacchiera());
	}
	public InterfacciaGrafica(String FEN) {
		this(new Scacchiera(FEN));
	}
	public void aggiorna() {
		pannelloScacchiera.aggiorna(scacchieraLogica);		
		info.aggiorna(scacchieraLogica);
		try {
			scacchieraLogica.checkEnd();
		} catch (ScaccoMattoException e) {
			System.out.println("Il "+
					(e.winner ? "bianco" : "nero") +" vince per scacco matto");
		} catch (StalloException e) {
			System.out.println("Il "+(e.haDatoStallo ? "bianco":"nero")+
				" ha dato stallo. La partita è patta");
			
		} catch (SemimosseException e) {
			System.out.println("La partita è patta per la regola delle 50 semimosse");
		} catch (EndException e) {
			System.err.println("questa parte di codice dovrebbe essere irraggiungibile");
			e.printStackTrace();
		}
		setVisible(true);
	}
	
}

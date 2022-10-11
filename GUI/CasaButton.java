package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class CasaButton extends JButton {
	int i, j;
	ScacchieraPanel parent;
	public CasaButton(int i, int j, ScacchieraPanel parent) {
		this.parent = parent;
		this.i = i;
		this.j = j;
		if((i + j) % 2 == 0) setBackground(Color.LIGHT_GRAY);
		else setBackground(Color.DARK_GRAY);
		setBorderPainted(false);
		setFocusable(false);
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(parent.firstX == -1) {
					parent.firstX = j;
					parent.firstY = i;
				} else {
					parent.provaMossa(j, i);
					resetParent();
				}
				
			}			
		});
		setVisible(true);
	}
	private void resetParent() {
		parent.firstX = -1;
		parent.firstY = -1;
	}
	
	
}

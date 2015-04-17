package ihm;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;



/**
 * 
 * @author prou
 *
 */
public class JPanelEntree  extends JPanel {

	String entree;
	JTextField jTextField;

	public JPanelEntree(String texte, String entreeInitiale, int largeur) {
		JLabel jLabel;
		this.entree = entreeInitiale;
		setLayout(new GridLayout(1, 2, 4, 4));
		setPreferredSize(new Dimension(largeur-20, 20));
		jLabel = new JLabel(texte);
		jLabel.setVisible(true);
		add(jLabel);
		jTextField = new JTextField(entree);
		jTextField.setVisible(true);
		jTextField.addCaretListener(new ActionEntree());
		add(jTextField);
		setVisible(true);
	}
	
	public void setEntree(String entree) {
		this.entree = entree;
		jTextField.setText(entree);
	}
	
	public String getEntree() {
		return entree;
	}

	class ActionEntree implements CaretListener {
		public void caretUpdate(CaretEvent e) {
			entree = new String(jTextField.getText());
		}
	}
}

package ihm;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


/**
 * 
 * @author prou
 *
 */
public class JPanelPassword extends JPanel {

	private String password;
	private JPasswordField jPasswordField;

	public JPanelPassword(String texte, String passwordInitial, int largeur) {
		password = passwordInitial;
		JLabel jLabel;
		setLayout(new GridLayout(1, 2, 4, 4));
		setPreferredSize(new Dimension(largeur-20, 20));
		jLabel = new JLabel(texte);
		jLabel.setVisible(true);
		add(jLabel);
		jPasswordField = new JPasswordField(passwordInitial);
		jPasswordField.setVisible(true);
		jPasswordField.addCaretListener(new ActionPasswordGestionnaire());
		add(jPasswordField);
		setVisible(true);

	}

	public String getPassword() {
		return password;
	}

	class ActionPasswordGestionnaire implements CaretListener {
		public void caretUpdate(CaretEvent e) {
			password = new String(jPasswordField.getPassword());
		}
	}
}

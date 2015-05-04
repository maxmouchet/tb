package ihm;

import javax.swing.*;
import java.awt.*;


/**
 * @author prou
 */
public class JScrollPaneTexte extends JScrollPane {

    private JTextArea jTextArea;

    public JScrollPaneTexte(String titre, String texteInitial, boolean modifiable, int largeur) {
        jTextArea = new JTextArea(10, 40);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setText(texteInitial);
        jTextArea.setVisible(true);
        if (!modifiable)
            jTextArea.setEditable(false);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setBounds(4, 4, largeur - 20, 250);
        setPreferredSize(new Dimension(largeur + 50, 250));
        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(titre),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        getBorder()));
        add(jTextArea);
        setViewportView(jTextArea);
        setVisible(true);
    }


    public String getTexte() {
        return jTextArea.getText();
    }

    public void setTexte(String s) {
        jTextArea.setText(s);
    }

}

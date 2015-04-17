package avis;


import ihm.JPanelEntree;
import ihm.JPanelPassword;
import ihm.JScrollPaneTexte;

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * 
 * @author prou
 *
 */
public class IHM {





	/**
	 * @uml.property  name="metier"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="iHM:siteParis.Metier"
	 */
	private SocialNetwork metier = null;



	private int largeurFenetre = 600;

	private String pseudoMembre = "";
	private String passwordMembre = "";
	private String profilMembre = "";

	String titreLivre;
	String genreLivre;
	String auteurLivre;
	String nbPagesLivre;

	String titreFilm;
	String genreFilm;
	String realisateurFilm;
	String scenaristeFilm;
	String dureeFilm;

	String commentaireMembre;
	String noteMembre;

	private String [] films = new String[0];
	private String [] livres = new String[0];

	private JFrame fenetreInteraction;
	private JMenuBar barreMenu;
	private JMenu menuMembre;
	private JMenu menuVisiteur;
	
	private JFrame fenetreAffichageReseau;
	private JScrollPaneTexte jScrollPaneAffichageReseau;
	
	private JFrame fenetreAffichageItems;
	private JScrollPaneTexte jScrollPaneAffichageItems;


	public IHM() {

		JMenuItem jMenuItem;
		fenetreInteraction = new JFrame("IHM social network : ");
		fenetreInteraction.setSize(largeurFenetre + 100, 100);
		fenetreInteraction.setVisible(true);
		Font f = fenetreInteraction.getFont().deriveFont(Font.ITALIC + Font.BOLD);
		fenetreInteraction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		barreMenu = new JMenuBar();
		fenetreAffichageReseau = null;


		menuMembre = new JMenu("membre");
		jMenuItem = new JMenuItem("afficher le réseau social");
		jMenuItem.addActionListener(new AffichageReseau());
		menuMembre.add(jMenuItem);
		jMenuItem = new JMenuItem("consulter un item");
		jMenuItem.addActionListener(new ConsultItem());
		menuMembre.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un profil");
		jMenuItem.addActionListener(new AddMember());
		menuMembre.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un item livre");
		jMenuItem.addActionListener(new AddItemBook());
		menuMembre.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un item film");
		jMenuItem.addActionListener(new AddItemFilm());
		menuMembre.add(jMenuItem);
		jMenuItem = new JMenuItem("donner un avis sur un item livre");
		jMenuItem.addActionListener(new ReviewItem("livre"));
		menuMembre.add(jMenuItem);
		jMenuItem = new JMenuItem("donner un avis sur un item film");
		jMenuItem.addActionListener(new ReviewItem("film"));
		menuMembre.add(jMenuItem);


		menuVisiteur = new JMenu("visiteur");
		jMenuItem = new JMenuItem("consulter un item");
		jMenuItem.addActionListener(new ConsultItem());
		menuVisiteur.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un profil");
		jMenuItem.addActionListener(new AddMember());
		menuVisiteur.add(jMenuItem);

		barreMenu.add(menuMembre);
		barreMenu.add(menuVisiteur);

		fenetreInteraction.setJMenuBar(barreMenu);
		fenetreInteraction.setVisible(true);
	}







	/**
	 * Setter of the property <tt>metier</tt>
	 * @param metier  The metier to set.
	 * @uml.property  name="metier"
	 */
	public void setMetier(SocialNetwork metier) {
		this.metier = metier;
	}



	private class AffichageReseau  implements ActionListener {
	public void actionPerformed(ActionEvent e) {
			if (fenetreAffichageReseau == null) {
				fenetreAffichageReseau = new JFrame();
				fenetreAffichageReseau.setLocation(60,60);
				jScrollPaneAffichageReseau = new JScrollPaneTexte(" Social Network ",  "", false, largeurFenetre);
				jScrollPaneAffichageReseau.setTexte(metier.toString());
				fenetreAffichageReseau.getContentPane().removeAll();
				fenetreAffichageReseau.repaint();
				fenetreAffichageReseau.setSize(largeurFenetre + 100, 350);
				fenetreAffichageReseau.getContentPane().add(jScrollPaneAffichageReseau);
				jScrollPaneAffichageReseau.setVisible(true);
				fenetreAffichageReseau.setVisible(true);
				fenetreAffichageReseau.repaint();
			} else {
				jScrollPaneAffichageReseau.setTexte(metier.toString());
				fenetreAffichageReseau.setVisible(true);
				fenetreAffichageReseau.repaint();				
			}
		}

	}



	private class ConsultItem implements ActionListener {
		JPanelEntree jPanelNameItem;
		public void actionPerformed(ActionEvent e) {
			JPanel consulterItem = new JPanel();
			consulterItem.setLayout(new GridLayout(2,1, 4, 4));
			jPanelNameItem = new JPanelEntree(" nom de l'item ? ", "", largeurFenetre);
			consulterItem.add(jPanelNameItem);
			JButton valider = new JButton("valider la demande consultation "); 
			valider.setPreferredSize(new Dimension(largeurFenetre-20, 25));
			valider.addActionListener(new ActionConsultItem());
			consulterItem.add(valider);
			consulterItem.setVisible(false);
			fenetreInteraction.getContentPane().removeAll();
			fenetreInteraction.repaint();
			fenetreInteraction.setSize(largeurFenetre + 100, 120);
			fenetreInteraction.getContentPane().add(consulterItem);
			consulterItem.setVisible(true);			
			fenetreInteraction.repaint();
		}
		class ActionConsultItem implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					LinkedList <String> items = metier.consultItems(jPanelNameItem.getEntree());
					String s = "\n";
					for (String item : items) {
						s += item + "\n";
					}
					if (fenetreAffichageItems == null) {
						fenetreAffichageItems = new JFrame();
						fenetreAffichageItems.setLocation(30,30);
						jScrollPaneAffichageItems = new JScrollPaneTexte(" items ",  s, false, largeurFenetre);
						fenetreAffichageItems.getContentPane().removeAll();
						fenetreAffichageItems.repaint();
						fenetreAffichageItems.setSize(largeurFenetre + 100, 350);
						fenetreAffichageItems.getContentPane().add(jScrollPaneAffichageItems);
						jScrollPaneAffichageItems.setVisible(true);
						fenetreAffichageItems.setVisible(true);
						fenetreAffichageItems.repaint();
					} else {
						jScrollPaneAffichageItems.setTexte(s);
						fenetreAffichageItems.setVisible(true);
						fenetreAffichageItems.repaint();				
					}

				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(fenetreInteraction, "Exception dans consultItems :   "  + exception);
				}
			}
		}

	}



	private class AddMember implements ActionListener {
		JPanelEntree jPanelPseudo;
		JPanelEntree jPanelPassword;
		JScrollPaneTexte jScrollPaneProfil;
		public void actionPerformed(ActionEvent e) {
			JPanel inscrireMembre = new JPanel();
			jScrollPaneProfil = new JScrollPaneTexte(" profil du membre ? ",  profilMembre, true, largeurFenetre);
			inscrireMembre.add(jScrollPaneProfil);
			JPanel jPanelPseudoEtPassword = new JPanel();
			jPanelPseudoEtPassword.setLayout(new GridLayout(3,1, 4, 4));
			jPanelPseudo = new JPanelEntree(" pseudo ? ", pseudoMembre, largeurFenetre);
			jPanelPseudoEtPassword.add(jPanelPseudo);
			jPanelPassword = new JPanelEntree(" password ? ", passwordMembre, largeurFenetre);
			jPanelPseudoEtPassword.add(jPanelPassword);
			JButton valider = new JButton("valider son inscription "); 
			valider.setPreferredSize(new Dimension(largeurFenetre-20, 25));
			valider.addActionListener(new ActionAddMember());
			jPanelPseudoEtPassword.add(valider);
			jPanelPseudoEtPassword.setVisible(true);
			inscrireMembre.add(jPanelPseudoEtPassword);
			inscrireMembre.setVisible(false);
			fenetreInteraction.getContentPane().removeAll();
			fenetreInteraction.repaint();
			fenetreInteraction.setSize(largeurFenetre + 100, 410);
			fenetreInteraction.getContentPane().add(inscrireMembre);
			inscrireMembre.setVisible(true);			
			fenetreInteraction.repaint();
		}

		class ActionAddMember implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					pseudoMembre = jPanelPseudo.getEntree();
					passwordMembre = jPanelPassword.getEntree();
					profilMembre = jScrollPaneProfil.getTexte();
					metier.addMember(pseudoMembre, passwordMembre, profilMembre);
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(fenetreInteraction, "Exception dans addMember :   "  + exception);
				}
			}
		}
	}




	private class AddItemBook implements ActionListener {
		JPanelEntree jPanelPseudo;
		JPanelPassword jPanelPassword;
		JPanelEntree jPanelTitre;
		JPanelEntree jPanelGenre;
		JPanelEntree jPanelAuteur;
		JPanelEntree jPanelNombrePages;
		public void actionPerformed(ActionEvent e) {
			JPanel ajouterLivre = new JPanel();
			ajouterLivre.setLayout(new GridLayout(7,1, 4, 4));
			jPanelPseudo = new JPanelEntree(" pseudo membre ? ", pseudoMembre, largeurFenetre);
			ajouterLivre.add(jPanelPseudo);
			jPanelPassword = new JPanelPassword(" password membre ? ", passwordMembre, largeurFenetre);
			ajouterLivre.add(jPanelPassword);
			jPanelTitre = new JPanelEntree(" titre ? ", titreLivre, largeurFenetre);
			ajouterLivre.add(jPanelTitre);
			jPanelGenre = new JPanelEntree(" genre ? ", genreLivre, largeurFenetre);
			ajouterLivre.add(jPanelGenre);
			jPanelAuteur = new JPanelEntree(" auteur ? ", auteurLivre, largeurFenetre);
			ajouterLivre.add(jPanelAuteur);
			jPanelNombrePages = new JPanelEntree(" nb pages ? ", nbPagesLivre, largeurFenetre);
			ajouterLivre.add(jPanelNombrePages);
			JButton valider = new JButton("Valider ajouter item livre"); 
			valider.setPreferredSize(new Dimension(largeurFenetre-20, 25));
			valider.addActionListener(new ActionAddItemBook());
			ajouterLivre.add(valider);
			ajouterLivre.setVisible(false);
			fenetreInteraction.getContentPane().removeAll();
			fenetreInteraction.repaint();
			fenetreInteraction.setSize(largeurFenetre + 100, 320);
			fenetreInteraction.getContentPane().add(ajouterLivre);
			ajouterLivre.setVisible(true);			
			fenetreInteraction.setVisible(true);			
			fenetreInteraction.repaint();
		}

		class ActionAddItemBook implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					pseudoMembre = jPanelPseudo.getEntree(); 
					passwordMembre = jPanelPassword.getPassword(); 
					titreLivre = jPanelTitre.getEntree(); 
					genreLivre = jPanelGenre.getEntree(); 
					auteurLivre = jPanelAuteur.getEntree(); 
					nbPagesLivre = jPanelNombrePages.getEntree(); 
					metier.addItemBook(pseudoMembre, passwordMembre, titreLivre, genreLivre, auteurLivre, new Integer(nbPagesLivre));
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(fenetreInteraction, "Exception dans addItemBook :   "  + exception);
				}
			}
		}
	}


	private class AddItemFilm implements ActionListener {
		JPanelEntree jPanelPseudo;
		JPanelPassword jPanelPassword;
		JPanelEntree jPanelTitre;
		JPanelEntree jPanelGenre;
		JPanelEntree jPanelRealisateur;
		JPanelEntree jPanelScenariste;
		JPanelEntree jPanelNombrePages;
		public void actionPerformed(ActionEvent e) {
			JPanel ajouterFilm = new JPanel();
			ajouterFilm.setLayout(new GridLayout(7,1, 4, 4));
			jPanelPseudo = new JPanelEntree(" pseudo membre ? ", pseudoMembre, largeurFenetre);
			ajouterFilm.add(jPanelPseudo);
			jPanelPassword = new JPanelPassword(" password membre ? ", passwordMembre, largeurFenetre);
			ajouterFilm.add(jPanelPassword);
			jPanelTitre = new JPanelEntree(" titre ? ", titreFilm, largeurFenetre);
			ajouterFilm.add(jPanelTitre);
			jPanelGenre = new JPanelEntree(" genre ? ", genreFilm, largeurFenetre);
			ajouterFilm.add(jPanelGenre);
			jPanelRealisateur = new JPanelEntree(" réalisateur ? ", realisateurFilm, largeurFenetre);
			ajouterFilm.add(jPanelRealisateur);
			jPanelScenariste = new JPanelEntree(" scénariste ? ", scenaristeFilm, largeurFenetre);
			ajouterFilm.add(jPanelScenariste);
			jPanelNombrePages = new JPanelEntree(" durée ? ", dureeFilm, largeurFenetre);
			ajouterFilm.add(jPanelNombrePages);
			JButton valider = new JButton("Valider ajouter item film"); 
			valider.setPreferredSize(new Dimension(largeurFenetre-20, 25));
			valider.addActionListener(new ActionAddItemFilm());
			ajouterFilm.add(valider);
			ajouterFilm.setVisible(false);
			fenetreInteraction.getContentPane().removeAll();
			fenetreInteraction.repaint();
			fenetreInteraction.setSize(largeurFenetre + 100, 320);
			fenetreInteraction.getContentPane().add(ajouterFilm);
			ajouterFilm.setVisible(true);			
			fenetreInteraction.setVisible(true);			
			fenetreInteraction.repaint();
		}

		class ActionAddItemFilm implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					pseudoMembre = jPanelPseudo.getEntree(); 
					passwordMembre = jPanelPassword.getPassword(); 
					titreFilm = jPanelTitre.getEntree(); 
					genreFilm = jPanelGenre.getEntree(); 
					realisateurFilm = jPanelRealisateur.getEntree(); 
					scenaristeFilm = jPanelScenariste.getEntree(); 
					dureeFilm = jPanelNombrePages.getEntree(); 
					metier.addItemFilm(pseudoMembre, passwordMembre, titreFilm, genreFilm, realisateurFilm, scenaristeFilm, new Integer(dureeFilm));
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(fenetreInteraction, "Exception dans addItemFilm :   "  + exception);
				}
			}
		}
	}




	private class ReviewItem   implements ActionListener {
		JPanelEntree jPanelPseudo;
		JPanelPassword jPanelPassword;
		JPanelEntree jPanelTitre;
		JPanelEntree jPanelNote;
		JScrollPaneTexte jScrollPaneCommentaire;

		String type;

		public ReviewItem (String type) {
			this.type = type;
		}
		public void actionPerformed(ActionEvent e) {
			JPanel reviewer = new JPanel();
			jScrollPaneCommentaire = new JScrollPaneTexte(" commentaire ? ",  commentaireMembre, true, largeurFenetre);
			reviewer.add(jScrollPaneCommentaire);
			JPanel jPanelPseudoEtPasswordEtTitre = new JPanel();
			jPanelPseudoEtPasswordEtTitre.setLayout(new GridLayout(5,1, 4, 4));
			jPanelPseudo = new JPanelEntree(" pseudo membre ? ", pseudoMembre, largeurFenetre);
			jPanelPseudoEtPasswordEtTitre.add(jPanelPseudo);
			jPanelPassword = new JPanelPassword(" password membre ? ", passwordMembre, largeurFenetre);
			jPanelPseudoEtPasswordEtTitre.add(jPanelPassword);
			jPanelTitre = new JPanelEntree(" titre ? ", titreLivre, largeurFenetre);
			jPanelPseudoEtPasswordEtTitre.add(jPanelTitre);
			jPanelNote = new JPanelEntree(" note ? ", noteMembre, largeurFenetre);
			jPanelPseudoEtPasswordEtTitre.add(jPanelNote);
			JButton valider = new JButton("Valider reviewer item " + type); 
			valider.setPreferredSize(new Dimension(largeurFenetre-20, 25));
			valider.addActionListener(new ActionReviewItem());
			jPanelPseudoEtPasswordEtTitre.add(valider);
			reviewer.add(jPanelPseudoEtPasswordEtTitre);				
			reviewer.setVisible(false);
			fenetreInteraction.getContentPane().removeAll();
			fenetreInteraction.repaint();
			fenetreInteraction.setSize(largeurFenetre + 100, 470);
			fenetreInteraction.getContentPane().add(reviewer);
			reviewer.setVisible(true);			
			fenetreInteraction.setVisible(true);			
			fenetreInteraction.repaint();
		}

		class ActionReviewItem implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					pseudoMembre = jPanelPseudo.getEntree(); 
					passwordMembre = jPanelPassword.getPassword(); 
					titreLivre = jPanelTitre.getEntree(); 
					noteMembre = jPanelNote.getEntree();
					commentaireMembre = jScrollPaneCommentaire.getTexte();
					if (type.equals("livre"))
						metier.reviewItemBook(pseudoMembre, passwordMembre, titreLivre, new Float(noteMembre), commentaireMembre);
					if (type.equals("film"))
						metier.reviewItemFilm(pseudoMembre, passwordMembre, titreLivre, new Float(noteMembre), commentaireMembre);

				}
				catch (Exception exception) {
					if (type.equals("livre"))
						JOptionPane.showMessageDialog(fenetreInteraction, "Exception dans reviewItemBook :   "  + exception);
					if (type.equals("film"))
						JOptionPane.showMessageDialog(fenetreInteraction, "Exception dans reviewItemFilm :   "  + exception);
				}
			}
		}
	}








	public static void main (String [] args) {

		try {
			SocialNetwork sn = new SocialNetwork();

			IHM ihm = new IHM(); 
			ihm.setMetier(sn);

			// ajout de 3 membres avec entrées "correctes"
			sn.addMember("Paul", "paul", "lecteur impulsif");
			sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
			sn.addMember("Alice", "alice", "23 ans, sexy");


		}
		catch (Exception e) {
			System.out.println("Exception inattendue : " + e);
			e.printStackTrace();
		}

	}


}






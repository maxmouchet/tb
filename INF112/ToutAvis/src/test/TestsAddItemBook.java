package test;

import java.util.LinkedList;

import avis.SocialNetwork;

import exception.BadEntry;
import exception.ItemFilmAlreadyExists;
import exception.ItemBookAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotItem;
import exception.NotMember;

public class TestsAddItemBook {


	public static int addItemBookOKTest (SocialNetwork sn, String pseudo, String pwd, String profil, String idTest) {
		int nbLivres = sn.nbBooks();
		
		try {
			sn.addItemBook(pseudo, pwd, "Hackers & Painters", "Essais", "Paul Graham", 200);
			
			if (sn.nbBooks() != nbLivres + 1) {
				System.out.println("Test " + idTest + " : le nombre de livres n'a pas été correctement incrémenté");
				return 1;
			} else {
				return 0;
			}
			
		} catch (Exception e) {
			System.out.println ("Test " + idTest + " : exception non prévue. " + e); 
			e.printStackTrace();
			return 1;
		}
	}
	
	public static int addItemBookAlreadyExistTest (SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
		int nbLivres = sn.nbBooks();
		
		try {
			sn.addItemBook(pseudo, pwd, "From Zero To One", "Essais", "Peter Thiel", 200);
			sn.addItemBook(pseudo, pwd, "From Zero To One", "Essais", "Peter Thiel", 200);
			
			System.out.println ("Test " + idTest + " : " + messErreur);
			return 1;	
		} catch (ItemBookAlreadyExists e) {
			if (sn.nbBooks() != nbLivres + 1) {
				System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println ("Test " + idTest + " : exception non prévue. " + e); 
			e.printStackTrace();
			return 1;
		}
	}
	
	public static int addItemBookAlreadyExistDifferentCaseTest (SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
		int nbLivres = sn.nbBooks();
		
		try {
			sn.addItemBook(pseudo, pwd, "The Singularity is Near", "Essais", "Raymond Kurzweil", 600);
			sn.addItemBook(pseudo, pwd, "tHE Singularity is Near", "Essais", "rAYMOND Kurzweil", 600);
			
			System.out.println ("Test " + idTest + " : " + messErreur);
			return 1;	
		} catch (ItemBookAlreadyExists e) {
			if (sn.nbBooks() != nbLivres + 1) {
				System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println ("Test " + idTest + " : exception non prévue. " + e); 
			e.printStackTrace();
			return 1;
		}
	}
	
	public static int addItemBookAlreadyExistWithWhitespacesTest (SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
		int nbLivres = sn.nbBooks();
		
		try {
			sn.addItemBook(pseudo, pwd, "The Art of War", "Essais", "Sun Tzu", 100);
			sn.addItemBook(pseudo, pwd, "  The Art of War  ", "Essais", "  Sun T", 100);
			
			System.out.println ("Test " + idTest + " : " + messErreur);
			return 1;	
		} catch (ItemBookAlreadyExists e) {
			if (sn.nbBooks() != nbLivres + 1) {
				System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println ("Test " + idTest + " : exception non prévue. " + e); 
			e.printStackTrace();
			return 1;
		}
	}
	
	public static int addItemBookSameAuthorDifferentTitleTest (SocialNetwork sn, String pseudo, String pwd, String profil, String idTest) {
		int nbLivres = sn.nbBooks();
		
		try {
			sn.addItemBook(pseudo, pwd, "1Q84", "Roman", "Haruki Murakami", 1000);
			sn.addItemBook(pseudo, pwd, "Kafka on the shore", "Roman", "Haruki Murakami", 500);
			
			if (sn.nbBooks() != nbLivres + 2) {
				System.out.println("Test " + idTest + " : le nombre de livres n'a pas été correctement incrémenté");
				return 1;
			} else {
				return 0;
			}
			
		} catch (Exception e) {
			System.out.println ("Test " + idTest + " : exception non prévue. " + e); 
			e.printStackTrace();
			return 1;
		}
	}
	
	public static void main(String[] args) throws BadEntry, MemberAlreadyExists {
		int nbFilms = 0;

		int nbTests = 0;
		int nbErreurs = 0;
		
		System.out.println("# Ajout de livres au réseau social");
		SocialNetwork sn = new SocialNetwork();

		
		String pseudo = "ToTo";
		String pwd = "Pa$$w0rd";
		String profil = "tAtA";
		
		System.out.println("Ajout d'un membre pour le test: " + pseudo);
		sn.addMember(pseudo, pwd, profil);
		
		// tests de add
		nbFilms = sn.nbFilms();

		// <=> fiche numéro 3
		// tentative d'ajout de livres avec entrées "correctes"

		nbTests++;
		nbErreurs += addItemBookOKTest(sn, pseudo, pwd, profil, "1.1");
		nbTests++;
		nbErreurs += addItemBookAlreadyExistTest(sn, pseudo, pwd, profil, "1.2", "L'ajout d'un livre dont le titre existe déjà est autorisé");
		nbTests++;
		nbErreurs += addItemBookAlreadyExistDifferentCaseTest(sn, pseudo, pwd, profil, "1.3", "L'ajout d'un livre dont le titre existe déjà (casse différente) est autorisé");
		nbTests++;
		nbErreurs += addItemBookAlreadyExistWithWhitespacesTest(sn, pseudo, pwd, profil, "1.4", "L'ajout d'un livre dont le titre existe déjà (avec des espaces en début et fin) est autorisé");
		nbTests++;
		nbErreurs += addItemBookSameAuthorDifferentTitleTest(sn, pseudo, pwd, profil, "1.5");

		// <=> fiche numéro 4
		// TODO

		nbTests++;
		if (nbFilms != sn.nbFilms()) {
			System.out.println("Erreur  :  le nombre de films après utilisation de addItemBook a été modifié");
			nbErreurs++;
		}

		// ce n'est pas du test, mais cela peut "rassurer"...
		System.out.println(sn);

		// bilan du test de addMember
		System.out.println("TestsAddMember :   " + nbErreurs + " erreur(s) / " +  nbTests + " tests effectués");

	}
}

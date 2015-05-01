package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotMember;

import java.util.HashMap;

public class TestsAddItemBook {

    private static int addItemBookOKTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, String genre, String author, int pageCount) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, password, title, genre, author, pageCount);

            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : le nombre de livres n'a pas été correctement incrémenté");
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int addItemBookAlreadyExistsTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, String genre, String author, int pageCount, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, password, title, genre, author, pageCount);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (ItemBookAlreadyExists e) {
            if (sn.nbBooks() != nbLivres) {
                System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int addItemBookBadEntryTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, String genre, String author, int pageCount, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, password, title, genre, author, pageCount);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbBooks() != nbLivres) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int addItemBookNotMemberTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, String genre, String author, int pageCount, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, password, title, genre, author, pageCount);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotMember e) {
            if (sn.nbBooks() != nbLivres) {
                System.out.println("Test " + idTest + " : l'exception NotMember a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo, String password) throws MemberAlreadyExists, BadEntry, ItemBookAlreadyExists, NotMember {
        System.out.println("\n# Tests d'ajout de livres");

        int nbTests = 0;
        int nbErreurs = 0;

        int nbFilms = sn.nbFilms();

        // Ajout d'un livre pour les tests
        String title = "From Zero To One";
        String genre = "Essay";
        String author = "Peter Thiel";
        int pageCount = 200;

        System.out.println("* Ajout d'un livre pour les tests: " + title);
        sn.addItemBook(pseudo, password, title, genre, author, pageCount);

        // Fiche 3
        // Tentatives d'ajout de livres avec des entrées correctes

        nbTests++;
        nbErreurs += addItemBookOKTest("3.1", sn, pseudo, password, "1Q84", "Roman", "Haruki Murakami", 1000);
        nbTests++;
        nbErreurs += addItemBookOKTest("3.2", sn, pseudo, password, "Kafka on the shore", "Roman", "Haruki Murakami", 1000);

        nbTests++;
        nbErreurs += addItemBookAlreadyExistsTest("3.3", sn, pseudo, password, title, genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà est autorisé.");
        nbTests++;
        nbErreurs += addItemBookAlreadyExistsTest("3.4", sn, pseudo, password, title.toUpperCase(), genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà (casse différente) est autorisé.");
        nbTests++;
        nbErreurs += addItemBookAlreadyExistsTest("3.5", sn, pseudo, password, "  " + title + "   ", genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà (avec des espaces en début et fin) est autorisé.");

        // Fiche 4
        // Tentatives d'ajout de livres avec des entrées incorrectes

        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.1", sn, pseudo, password, null, genre, author, pageCount, "L'ajout d'un livre avec un titre null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.2", sn, pseudo, password, "  ", genre, author, pageCount, "L'ajout d'un livre avec un titre composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.3", sn, pseudo, password, title, null, author, pageCount, "L'ajout d'un livre avec un genre null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.4", sn, pseudo, password, title, genre, null, pageCount, "L'ajout d'un livre avec un auteur null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.5", sn, pseudo, password, title, genre, author, -100, "L'ajout d'un livre avec un nombre de pages négatif est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.6", sn, null, password, title, genre, author, pageCount, "L'ajout d'un livre avec un pseudo non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.7", sn, pseudo, null, title, genre, author, pageCount, "L'ajout d'un livre avec un password non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemBookBadEntryTest("4.8", sn, "   ", password, title, genre, author, pageCount, "L'ajout d'un livre avec un pseudo composé uniquement d'espaces est autorisé.");

        nbTests++;
        nbErreurs += addItemBookNotMemberTest("4.9", sn, "BillGate$$38", "Micro$$oft", title, genre, author, pageCount, "L'ajout d'un livre pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += addItemBookNotMemberTest("4.10", sn, pseudo, "Ju5nPa5lo2015", title, genre, author, pageCount, "L'ajout d'un livre avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur: le nombre de films après utilisation de addItemBook a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<String, Integer>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, NotMember, ItemBookAlreadyExists {
        SocialNetwork sn = new SocialNetwork();

        // Ajout d'un membre pour les tests
        String pseudo = "ToTo";
        String password = "Pa$$w0rd";
        String profil = "tAtA";

        sn.addMember(pseudo, password, profil);

        HashMap<String, Integer> testsResults = runTests(sn, pseudo, password);
        System.out.println("-> TestsAddItemBook: " + testsResults.get("errors") + " erreur(s) / " + testsResults.get("total") + " tests effectués");
    }
}

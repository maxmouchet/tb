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

    private static int addItemBookExceptionTest(String idTest, Class<?> expectedException, SocialNetwork sn, String pseudo, String password, String title, String genre, String author, int pageCount, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, password, title, genre, author, pageCount);

            // Cas erroné: une exception était attendue.
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {

                // Cas erroné: exception attendue mais le nombre de livres a changé.
                if (sn.nbBooks() != nbLivres) {
                    System.out.println("Test " + idTest + " : l'exception NotMember a bien été levé, mais le nombre de livres a été incrémenté");
                    return 1;
                }

                // Cas normal: exception attendue et nombre de livres inchangé.
                else {
                    return 0;
                }
            }
            // Cas erroné: l'exception n'était pas attendue.
            else {
                System.out.println("Test " + idTest + " : exception non prévue. " + e);
                e.printStackTrace();
                return 1;
            }
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
        nbErreurs += addItemBookExceptionTest("3.3", ItemBookAlreadyExists.class, sn, pseudo, password, title, genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("3.4", ItemBookAlreadyExists.class, sn, pseudo, password, title.toUpperCase(), genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà (casse différente) est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("3.5", ItemBookAlreadyExists.class, sn, pseudo, password, "  " + title + "   ", genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà (avec des espaces en début et fin) est autorisé.");

        // Fiche 4
        // Tentatives d'ajout de livres avec des entrées incorrectes

        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.1", BadEntry.class, sn, pseudo, password, null, genre, author, pageCount, "L'ajout d'un livre avec un titre null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.2", BadEntry.class, sn, pseudo, password, "  ", genre, author, pageCount, "L'ajout d'un livre avec un titre composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.3", BadEntry.class, sn, pseudo, password, title, null, author, pageCount, "L'ajout d'un livre avec un genre null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.4", BadEntry.class, sn, pseudo, password, title, genre, null, pageCount, "L'ajout d'un livre avec un auteur null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.5", BadEntry.class, sn, pseudo, password, title, genre, author, -100, "L'ajout d'un livre avec un nombre de pages négatif est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.6", BadEntry.class, sn, null, password, title, genre, author, pageCount, "L'ajout d'un livre avec un pseudo non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.7", BadEntry.class, sn, pseudo, null, title, genre, author, pageCount, "L'ajout d'un livre avec un password non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.8", BadEntry.class, sn, "   ", password, title, genre, author, pageCount, "L'ajout d'un livre avec un pseudo composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.9", BadEntry.class, sn, pseudo, "  123  ", title, genre, author, pageCount, "L'ajout d'un livre avec un password composé de moins de 4 caractères est autorisé.");

        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.10", NotMember.class, sn, "BillGate$$38", "Micro$$oft", title, genre, author, pageCount, "L'ajout d'un livre pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.11", NotMember.class, sn, pseudo, "Ju5nPa5lo2015", title, genre, author, pageCount, "L'ajout d'un livre avec un mauvais mot de passe est autorisé.");

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

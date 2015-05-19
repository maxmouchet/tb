package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.NotMember;

import java.util.HashMap;

public class TestsAddItemBook implements SocialNetworkTest {

    private int addItemBookOKTest(String idTest, SocialNetwork sn, String pseudo1, String password1, String title, String genre, String author, int pageCount) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo1, password1, title, genre, author, pageCount);

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

    private int addItemBookExceptionTest(String idTest, Class<?> expectedException, SocialNetwork sn, String pseudo1, String password1, String title, String genre, String author, int pageCount, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo1, password1, title, genre, author, pageCount);

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

    public HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo1, String password1, String pseudo2, String password2) throws Exception {
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
        sn.addItemBook(pseudo1, password1, title, genre, author, pageCount);

        // Fiche 3
        // Tentatives d'ajout de livres avec des entrées correctes

        nbTests++;
        nbErreurs += addItemBookOKTest("3.1", sn, pseudo1, password1, "1Q84", "Roman", "Haruki Murakami", 1000);
        nbTests++;
        nbErreurs += addItemBookOKTest("3.2", sn, pseudo1, password1, "Kafka on the shore", "Roman", "Haruki Murakami", 1000);

        nbTests++;
        nbErreurs += addItemBookExceptionTest("3.3", ItemBookAlreadyExists.class, sn, pseudo1, password1, title, genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("3.4", ItemBookAlreadyExists.class, sn, pseudo1, password1, title.toUpperCase(), genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà (casse différente) est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("3.5", ItemBookAlreadyExists.class, sn, pseudo1, password1, "  " + title + "   ", genre, author, pageCount, "L'ajout d'un livre dont le titre existe déjà (avec des espaces en début et fin) est autorisé.");

        // Fiche 4
        // Tentatives d'ajout de livres avec des entrées incorrectes

        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.1", BadEntry.class, sn, pseudo1, password1, null, genre, author, pageCount, "L'ajout d'un livre avec un titre null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.2", BadEntry.class, sn, pseudo1, password1, "  ", genre, author, pageCount, "L'ajout d'un livre avec un titre composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.3", BadEntry.class, sn, pseudo1, password1, title, null, author, pageCount, "L'ajout d'un livre avec un genre null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.4", BadEntry.class, sn, pseudo1, password1, title, genre, null, pageCount, "L'ajout d'un livre avec un auteur null est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.5", BadEntry.class, sn, pseudo1, password1, title, genre, author, -100, "L'ajout d'un livre avec un nombre de pages négatif est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.6", BadEntry.class, sn, pseudo1, password1, title, genre, author, 0, "L'ajout d'un livre avec un nombre de pages égal à 0 est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.7", BadEntry.class, sn, null, password1, title, genre, author, pageCount, "L'ajout d'un livre avec un pseudo non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.8", BadEntry.class, sn, pseudo1, null, title, genre, author, pageCount, "L'ajout d'un livre avec un password non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.9", BadEntry.class, sn, "   ", password1, title, genre, author, pageCount, "L'ajout d'un livre avec un pseudo composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.10", BadEntry.class, sn, pseudo1, "  123  ", title, genre, author, pageCount, "L'ajout d'un livre avec un password composé de moins de 4 caractères est autorisé.");

        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.11", NotMember.class, sn, "BillGate$$38", "Micro$$oft", title, genre, author, pageCount, "L'ajout d'un livre pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += addItemBookExceptionTest("4.12", NotMember.class, sn, pseudo1, "Ju5nPa5lo2015", title, genre, author, pageCount, "L'ajout d'un livre avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur: le nombre de films après utilisation de addItemBook a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }
}

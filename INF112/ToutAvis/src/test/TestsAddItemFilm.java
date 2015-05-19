package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemFilmAlreadyExists;
import exception.NotMember;

import java.util.HashMap;

public class TestsAddItemFilm implements SocialNetworkTest {

    private int addItemFilmOKTest(String idTest, SocialNetwork sn, String pseudo1, String password1, String title, String genre, String director, String writer, int length) {
        int nbFilms = sn.nbFilms();

        try {
            sn.addItemFilm(pseudo1, password1, title, genre, director, writer, length);

            if (sn.nbFilms() != nbFilms + 1) {
                System.out.println("Test " + idTest + " : le nombre de films n'a pas été correctement incrémenté");
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

    private int addItemFilmExceptionTest(String idTest, Class<?> expectedException, SocialNetwork sn, String pseudo1, String password1, String title, String genre, String director, String writer, int length, String messErreur) {
        int nbFilms = sn.nbFilms();

        try {
            sn.addItemFilm(pseudo1, password1, title, genre, director, writer, length);

            // Cas erroné: une exception était attendue.
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {

                // Cas erroné: exception attendue mais le nombre de films a changé.
                if (sn.nbFilms() != nbFilms) {
                    System.out.println("Test " + idTest + " : l'exception NotMember a bien été levé, mais le nombre de films a été incrémenté");
                    return 1;
                }

                // Cas normal: exception attendue et nombre de films inchangé.
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
        System.out.println("\n# Tests d'ajout de films");

        int nbTests = 0;
        int nbErreurs = 0;

        int nbLivres = sn.nbBooks();

        // Ajout d'un film pour les tests
        String title = "The Social Network";
        String genre = "Biographie";
        String director = "David Fincher";
        String writer = "Aaron Sorkin";
        int length = 121;

        System.out.println("* Ajout d'un film pour les tests: " + title);
        sn.addItemFilm(pseudo1, password1, title, genre, director, writer, length);

        // Fiche 7
        // Tentatives d'ajout de films avec des entrées correctes

        nbTests++;
        nbErreurs += addItemFilmOKTest("7.1", sn, pseudo1, password1, "Imitation Game", "Biographie", "Morten Tyldum", "Graham Moore", 114);
        nbTests++;
        nbErreurs += addItemFilmOKTest("7.2", sn, pseudo1, password1, "Citizenfour", "Biographie", "Laura Poitras", "Laura Poitras", 114);

        nbTests++;
        nbErreurs += addItemFilmExceptionTest("7.3", ItemFilmAlreadyExists.class, sn, pseudo1, password1, title, genre, director, writer, length, "L'ajout d'un film dont le titre existe déjà est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("7.4", ItemFilmAlreadyExists.class, sn, pseudo1, password1, title.toUpperCase(), genre, director, writer, length, "L'ajout d'un film dont le titre existe déjà (casse différente) est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("7.5", ItemFilmAlreadyExists.class, sn, pseudo1, password1, "  " + title + "   ", genre, director, writer, length, "L'ajout d'un film dont le titre existe déjà (avec des espaces en début et fin) est autorisé.");

        // Fiche 8
        // Tentatives d'ajout de films avec des entrées incorrectes

        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.1", BadEntry.class, sn, pseudo1, password1, null, genre, director, writer, length, "L'ajout d'un film avec un titre null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.2", BadEntry.class, sn, pseudo1, password1, "  ", genre, director, writer, length, "L'ajout d'un film avec un titre composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.3", BadEntry.class, sn, pseudo1, password1, title, null, director, writer, length, "L'ajout d'un film avec un genre null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.4", BadEntry.class, sn, pseudo1, password1, title, genre, null, writer, length, "L'ajout d'un film avec un réalisateur null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.5", BadEntry.class, sn, pseudo1, password1, title, genre, director, null, length, "L'ajout d'un film avec un scénariste null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.6", BadEntry.class, sn, pseudo1, password1, title, genre, director, writer, -100, "L'ajout d'un film avec une durée négative est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.7", BadEntry.class, sn, pseudo1, password1, title, genre, director, writer, 0, "L'ajout d'un film avec une durée égale à 0 est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.8", BadEntry.class, sn, null, password1, title, genre, director, writer, length, "L'ajout d'un film avec un pseudo non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.9", BadEntry.class, sn, pseudo1, null, title, genre, director, writer, length, "L'ajout d'un film avec un password non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.10", BadEntry.class, sn, "   ", password1, title, genre, director, writer, length, "L'ajout d'un film avec un pseudo composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.11", BadEntry.class, sn, pseudo1, "  123  ", title, genre, director, writer, length, "L'ajout d'un film avec un password composé de moins de 4 caractères est autorisé.");

        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.12", NotMember.class, sn, "BillGate$$38", "Micro$$oft", title, genre, director, writer, length, "L'ajout d'un film pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("8.13", NotMember.class, sn, pseudo1, "Ju5nPa5lo2015", title, genre, director, writer, length, "L'ajout d'un film avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbLivres != sn.nbBooks()) {
            System.out.println("Erreur: le nombre de livres après utilisation de addItemFilm a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }
}

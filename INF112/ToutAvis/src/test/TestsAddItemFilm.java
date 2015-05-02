package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemFilmAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotMember;

import java.util.HashMap;

public class TestsAddItemFilm {

    private static int addItemFilmOKTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, String genre, String director, String writer, int length) {
        int nbFilms = sn.nbFilms();

        try {
            sn.addItemFilm(pseudo, password, title, genre, director, writer, length);

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

    private static int addItemFilmExceptionTest(String idTest, Class<?> expectedException, SocialNetwork sn, String pseudo, String password, String title, String genre, String director, String writer, int length, String messErreur) {
        int nbFilms = sn.nbFilms();

        try {
            sn.addItemFilm(pseudo, password, title, genre, director, writer, length);

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

    private static HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo, String password) throws MemberAlreadyExists, BadEntry, ItemFilmAlreadyExists, NotMember {
        System.out.println("\n# Tests d'ajout de films");

        int nbTests = 0;
        int nbErreurs = 0;

        int nbBooks = sn.nbBooks();

        // Ajout d'un film pour les tests
        String title = "The Social Network";
        String genre = "Biographie";
        String director = "David Fincher";
        String writer = "Aaron Sorkin";
        int length = 121;

        System.out.println("* Ajout d'un film pour les tests: " + title);
        sn.addItemFilm(pseudo, password, title, genre, director, writer, length);

        // Fiche 3
        // Tentatives d'ajout de films avec des entrées correctes

        nbTests++;
        nbErreurs += addItemFilmOKTest("3.1", sn, pseudo, password, "Imitation Game", "Biographie", "Morten Tyldum", "Graham Moore", 114);
        nbTests++;
        nbErreurs += addItemFilmOKTest("3.2", sn, pseudo, password, "Citizenfour", "Biographie", "Laura Poitras", "Laura Poitras", 114);

        nbTests++;
        nbErreurs += addItemFilmExceptionTest("3.3", ItemFilmAlreadyExists.class, sn, pseudo, password, title, genre, director, writer, length, "L'ajout d'un film dont le titre existe déjà est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("3.4", ItemFilmAlreadyExists.class, sn, pseudo, password, title.toUpperCase(), genre, director, writer, length, "L'ajout d'un film dont le titre existe déjà (casse différente) est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("3.5", ItemFilmAlreadyExists.class, sn, pseudo, password, "  " + title + "   ", genre, director, writer, length, "L'ajout d'un film dont le titre existe déjà (avec des espaces en début et fin) est autorisé.");

        // Fiche 4
        // Tentatives d'ajout de films avec des entrées incorrectes

        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.1", BadEntry.class, sn, pseudo, password, null, genre, director, writer, length, "L'ajout d'un film avec un titre null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.2", BadEntry.class, sn, pseudo, password, "  ", genre, director, writer, length, "L'ajout d'un film avec un titre composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.3", BadEntry.class, sn, pseudo, password, title, null, director, writer, length, "L'ajout d'un film avec un genre null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.4", BadEntry.class, sn, pseudo, password, title, genre, null, writer, length, "L'ajout d'un film avec un réalisateur null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.5", BadEntry.class, sn, pseudo, password, title, genre, director, null, length, "L'ajout d'un film avec un scénariste null est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.6", BadEntry.class, sn, pseudo, password, title, genre, director, writer, -100, "L'ajout d'un film avec une durée négative est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.7", BadEntry.class, sn, null, password, title, genre, director, writer, length, "L'ajout d'un film avec un pseudo non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.8", BadEntry.class, sn, pseudo, null, title, genre, director, writer, length, "L'ajout d'un film avec un password non instancié est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.9", BadEntry.class, sn, "   ", password, title, genre, director, writer, length, "L'ajout d'un film avec un pseudo composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.10", BadEntry.class, sn, pseudo, "  123  ", title, genre, director, writer, length, "L'ajout d'un film avec un password composé de moins de 4 caractères est autorisé.");

        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.11", NotMember.class, sn, "BillGate$$38", "Micro$$oft", title, genre, director, writer, length, "L'ajout d'un film pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += addItemFilmExceptionTest("4.12", NotMember.class, sn, pseudo, "Ju5nPa5lo2015", title, genre, director, writer, length, "L'ajout d'un film avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbBooks != sn.nbBooks()) {
            System.out.println("Erreur: le nombre de livres après utilisation de addItemFilm a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<String, Integer>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, NotMember, ItemFilmAlreadyExists {
        SocialNetwork sn = new SocialNetwork();

        // Ajout d'un membre pour les tests
        String pseudo = "ToTo";
        String password = "Pa$$w0rd";
        String profil = "tAtA";

        sn.addMember(pseudo, password, profil);

        HashMap<String, Integer> testsResults = runTests(sn, pseudo, password);
        System.out.println("-> TestsAddItemFilm: " + testsResults.get("errors") + " erreur(s) / " + testsResults.get("total") + " tests effectués");
    }
}
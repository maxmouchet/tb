package test;

import avis.SocialNetwork;
import exception.*;

import java.util.HashMap;

public class TestsReviewItemFilm {

    private static int reviewItemFilmOKTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, Float rating, Float expectedRating, String comment) {
        try {
            float newRating = sn.reviewItemFilm(pseudo, password, title, rating, comment);

            if (newRating != expectedRating) {
                System.out.println("Test " + idTest + " : la note du film n'a pas été correctement mise à jour.");
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

    private static int reviewItemFilmNotItemTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, Float rating, String comment, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemFilm(pseudo, password, title, rating, comment);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotItem notItem) {
            return 0;
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int reviewItemFilmExceptionTest(String idTest, Class<?> expectedException, SocialNetwork sn, String realPseudo, String realPassword, String pseudo, String password, String title, Float rating, Float expectedRating, String comment, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemFilm(pseudo, password, title, rating, comment);

            // Cas erroné: une exception était attendue.
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {
                float newRating;

                try {
                    newRating = sn.reviewItemFilm(realPseudo, realPassword, title, expectedRating, "Amazing !");
                }

                // Cas normal: exception attendue, on ne peut pas recuperer la note d'un item avec un titre invalide.
                catch (BadEntry badEntry) {
                    return 0;
                }

                // Cas normal: exception attendue et note du film inchangé.
                if (newRating == expectedRating) {
                    return 0;
                }

                // Cas erroné: exception attendue mais la note du film a changé.
                else {
                    System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais la note du film a été modifié");
                    return 1;
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

    private static HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo1, String password1, String pseudo2, String password2) throws NotMember, BadEntry, ItemFilmAlreadyExists, NotItem {
        System.out.println("\n# Tests d'ajout d'avis sur des films");

        int nbTests = 0;
        int nbErreurs = 0;

        // Ajout d'un film pour les tests
        String title = "Mommy";
        String genre = "Drame";
        String director = "Xavier Dolan";
        String writer = "Xavier Dolan";
        int length = 134;

        System.out.println("* Ajout d'un film pour les tests: " + title);
        sn.addItemFilm(pseudo1, password1, title, genre, director, writer, length);

        int nbFilms = sn.nbFilms();
        int nbLivres = sn.nbBooks();

        // Valeurs par defaut pour les tests
        float expectedRating = 3.0f;
        String comment = "Amazing !";

        // Fiche 9
        // Tentatives d'ajout de reviews avec des entrées correctes

        // Ajout d'une review avec l'utilisateur 1
        nbTests++;
        nbErreurs += reviewItemFilmOKTest("9.1", sn, pseudo1, password1, title, 1.0f, 1.0f, comment);

        // Modification de la review de l'utilisateur 1
        nbTests++;
        nbErreurs += reviewItemFilmOKTest("9.2", sn, pseudo1, password1, title, 5.0f, 5.0f, comment);

        // Ajout d'une review avec l'utilisateur 2
        nbTests++;
        nbErreurs += reviewItemFilmOKTest("9.3", sn, pseudo2, password2, title, 3.0f, 4.0f, comment);

        // Fiche 10
        // Tentatives d'ajout de reviews avec des entrées incorrectes

        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.1", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, title, -1.0f, expectedRating, comment, "L'ajout d'une review avec une note negative est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.2", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, title, 6.0f, expectedRating, comment, "L'ajout d'une review avec une note supérieure au maximum est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.3", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, title, 1.0f, expectedRating, null, "L'ajout d'une review null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.4", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, null, 1.0f, expectedRating, comment, "L'ajout d'une review avec un titre null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.5", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, "   ", 1.0f, expectedRating, comment, "L'ajout d'une review avec un titre composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.6", BadEntry.class, sn, pseudo1, password1, null, password1, title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un pseudo null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.7", BadEntry.class, sn, pseudo1, password1, pseudo1, null, title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un password null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.8", BadEntry.class, sn, pseudo1, password1, "   ", password1, title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un pseudo composé uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.9", BadEntry.class, sn, pseudo1, password1, pseudo1, "  123  ", title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un password composé de moins de 4 caractères est autorisé.");

        nbTests++;
        nbErreurs += reviewItemFilmNotItemTest("10.10", sn, pseudo1, password1, "Don Quichotte", 1.0f, comment, "L'ajout d'une review pour un film inexistant est autorisé.");

        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.11", NotMember.class, sn, pseudo1, password1, "BillGate$$38", "Micro$$oft", title, 1.0f, expectedRating, comment, "L'ajout d'une review pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += reviewItemFilmExceptionTest("10.12", NotMember.class, sn, pseudo1, password1, pseudo1, "Ju5nPa5lo2015", title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur: le nombre de films après utilisation de reviewItemFilm a été modifié.");
            nbErreurs++;
        }

        nbTests++;
        if (nbLivres != sn.nbBooks()) {
            System.out.println("Erreur: le nombre de livres après utilisation de reviewItemFilm a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<String, Integer>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemFilmAlreadyExists, NotMember, NotItem {
        SocialNetwork sn = new SocialNetwork();

        // Ajout de membres pour les tests
        String pseudo1 = "ToTo";
        String password1 = "Pa$$w0rd";
        String profil1 = "tAtA";

        String pseudo2 = "JoJo";
        String password2 = "Pa$$w0rd";
        String profil2 = "lOlO";

        sn.addMember(pseudo1, password1, profil1);
        sn.addMember(pseudo2, password2, profil2);

        HashMap<String, Integer> testsResults = runTests(sn, pseudo1, password1, pseudo2, password2);
        System.out.println("-> TestsReviewItemFilm: " + testsResults.get("errors") + " erreur(s) / " + testsResults.get("total") + " tests effectués");
    }
}

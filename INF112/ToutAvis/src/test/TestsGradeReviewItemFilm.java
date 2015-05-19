package test;

import avis.SocialNetwork;
import exception.*;

import java.util.HashMap;

public class TestsGradeReviewItemFilm implements SocialNetworkTest {

    private static int gradeReviewItemFilmOKTest(String idTest, SocialNetwork sn, String pseudo, String password, String reviewPseudo, String reviewTitle, Float grade, Float expectedGrade) {
        try {
            float newGrade = sn.gradeReviewItemFilm(pseudo, password, reviewPseudo, reviewTitle, grade);

            if (newGrade != expectedGrade) {
                System.out.println("Test " + idTest + " : la note de l'avis n'a pas été correctement mise à jour.");
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

    private static int gradeReviewItemFilmNotReviewTest(String idTest, SocialNetwork sn, String pseudo, String password, String reviewPseudo, String reviewTitle, Float grade, String messErreur) {
        try {
            sn.gradeReviewItemFilm(pseudo, password, reviewPseudo, reviewTitle, grade);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotReview notReview) {
            return 0;
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int gradeReviewItemFilmExceptionTest(String idTest, Class<?> expectedException, SocialNetwork sn, String realPseudo, String realPassword, String pseudo, String password, String realReviewPseudo, String reviewPseudo, String reviewTitle, Float grade, Float expectedGrade, String messErreur) throws NotMember, NotReview {
        try {
            sn.gradeReviewItemFilm(pseudo, password, reviewPseudo, reviewTitle, grade);

            // Cas erroné: une exception était attendue.
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {
                float newGrade;

                try {
                    newGrade = sn.gradeReviewItemFilm(realPseudo, realPassword, realReviewPseudo, reviewTitle, expectedGrade);
                }

                // Cas normal: exception attendue, on ne peut pas recuperer la note d'une review avec un titre invalide.
                catch (BadEntry badEntry) {
                    return 0;
                }

                // Cas normal: exception attendue et note de la review inchangé.
                if (newGrade == expectedGrade) {
                    return 0;
                }

                // Cas erroné: exception attendue mais la note de la review a changé.
                else {
                    System.out.println("Test " + idTest + " : l'exception " + e.getClass().getSimpleName() + " a bien été levé, mais la note de la review a été modifié");
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

    public HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo1, String password1, String pseudo2, String password2) throws NotMember, BadEntry, ItemFilmAlreadyExists, NotItem, NotReview {
        System.out.println("\n# Tests de notation d'avis sur des films");

        int nbTests = 0;
        int nbErreurs = 0;

        // Ajout d'un film pour les tests
        String title = "Gone Girl";
        String genre = "Thriller";
        String director = "David Fincher";
        String writer = "Gillian Flynn";
        int length = 149;

        System.out.println("* Ajout d'un film pour les tests: " + title);
        sn.addItemFilm(pseudo1, password1, title, genre, director, writer, length);

        // Ajout d'une review pour les tests
        float grade = 5.0f;
        String comment = "Amazing !";

        System.out.println("* Ajout d'une review pour les tests: " + title + " : " + comment);
        sn.reviewItemFilm(pseudo2, password2, title, grade, comment);

        int nbFilms = sn.nbFilms();
        int nbLivres = sn.nbBooks();

        // Valeurs par defaut pour les tests
        float expectedGrade = 2.0f;

        // Fiche 13
        // Tentatives de notation de reviews avec des entrées correctes

        // Notation d'une review de l'utilisateur 2 avec l'utilisateur 1
        nbTests++;
        nbErreurs += gradeReviewItemFilmOKTest("13.1", sn, pseudo1, password1, pseudo2, title, 1.0f, 1.0f);

        // Modification de la note de la review de l'utilisateur 2 avec l'utilisateur 1
        nbTests++;
        nbErreurs += gradeReviewItemFilmOKTest("13.2", sn, pseudo1, password1, pseudo2, title, 2.0f, 2.0f);
        nbTests++;
        nbErreurs += gradeReviewItemFilmOKTest("13.3", sn, pseudo1, password1, pseudo2, title.toLowerCase(), 2.0f, 2.0f);
        nbTests++;
        nbErreurs += gradeReviewItemFilmOKTest("13.4", sn, pseudo1, password1, pseudo2, "  " + title + "  ", 2.0f, 2.0f);

        // Notation d'une review de l'utilisateur 2 avec l'utilisateur 2
        nbTests++;
        nbErreurs += gradeReviewItemFilmOKTest("13.5", sn, pseudo2, password2, pseudo2, "  " + title + "  ", 2.0f, 2.0f);

        // Fiche 14
        // Tentatives de notation de reviews avec des entrées incorrectes

        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.1", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, pseudo2, title, -1.0f, expectedGrade, "La notation d'une review avec une note négative est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.2", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, pseudo2, title, 4.0f, expectedGrade, "La notation d'une review avec une note supérieure au maximum est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.3", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, pseudo2, null, 1.0f, expectedGrade, "La notation d'une review avec un titre non instancié est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.4", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, pseudo2, "   ", 1.0f, expectedGrade, "La notation d'une review avec un titre compose uniquement d'espaces est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.5", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, null, title, 1.0f, expectedGrade, "La notation d'une review avec un pseudo non instancié pour le donneur d'avis est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.6", BadEntry.class, sn, pseudo1, password1, null, password1, pseudo2, pseudo2, title, 1.0f, expectedGrade, "La notation d'une review avec un pseudo non instancié pour le noteur est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.7", BadEntry.class, sn, pseudo1, password1, pseudo1, null, pseudo2, pseudo2, title, 1.0f, expectedGrade, "La notation d'une review avec un password non instancié pour le noteur est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.8", BadEntry.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, "   ", title, 1.0f, expectedGrade, "La notation d'une review avec un pseudo composé uniquement d'espaces pour le donneur d'avis est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.9", BadEntry.class, sn, pseudo1, password1, "   ", password1, pseudo2, pseudo2, title, 1.0f, expectedGrade, "La notation d'une review avec un pseudo composé uniquement d'espaces pour le noteur est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.10", BadEntry.class, sn, pseudo1, password1, pseudo1, " 123 ", pseudo2, pseudo2, title, 1.0f, expectedGrade, "La notation d'une review avec un password composé de moins de 4 caractères est autorisé.");

        nbTests++;
        nbErreurs += gradeReviewItemFilmNotReviewTest("14.11", sn, pseudo1, password1, pseudo2, "Alice chez les Barbapapas.", 1.0f, "La notation d'une review inexistante est autorisé");

        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.12", NotMember.class, sn, pseudo1, password1, pseudo1, password1, pseudo2, "St7veJ0bs", title, 1.0f, expectedGrade, "La notation d'une review pour un donneur d'avis inexistant est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.13", NotMember.class, sn, pseudo1, password1, "BillGate$$38", "Micro$$oft", pseudo2, pseudo2, title, 1.0f, expectedGrade, "La notation d'une review pour un noteur inexistant est autorisé.");
        nbTests++;
        nbErreurs += gradeReviewItemFilmExceptionTest("14.14", NotMember.class, sn, pseudo1, password1, pseudo1, "Ju5nPa5lo2015", pseudo2, pseudo2, title, 1.0f, expectedGrade, "La notation d'une review avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur: le nombre de films après utilisation de gradeReviewItemFilm a été modifié.");
            nbErreurs++;
        }

        nbTests++;
        if (nbLivres != sn.nbBooks()) {
            System.out.println("Erreur: le nombre de livres après utilisation de gradeReviewItemFilm a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }
}

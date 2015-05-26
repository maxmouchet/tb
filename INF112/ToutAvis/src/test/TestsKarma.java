package test;

import avis.SocialNetwork;

import java.util.HashMap;

public class TestsKarma implements SocialNetworkTest {

    @Override
    public HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo1, String password1, String pseudo2, String password2) throws Exception {
        System.out.println("\n# Tests du karma");

        int nbTests = 0;
        int nbErreurs = 0;

        // Ajout d'un livre pour les tests
        String title = "Madame Bavarde";
        String genre = "Children";
        String author = "Inconnu";
        int pageCount = 10;

        System.out.println("* Ajout d'un livre pour les tests: " + title);
        sn.addItemBook(pseudo1, password1, title, genre, author, pageCount);

        float newRating;

        // 17.1 Vérification du karma par défaut (neutre, sans effet sur la note)
        nbTests++;
        newRating = sn.reviewItemBook(pseudo1, password1, title, 5.0f, "Amazing !");
        if (newRating != 5.0) {
            System.out.println("17.1 Le karma par défaut n'est pas neutre sur la note d'un item.");
            nbErreurs++;
        }

        // 17.2 Vérification de la saturation de la note (note max. * karma max.)
        nbTests++;
        sn.gradeReviewItemBook(pseudo2, password2, pseudo1, title, 3.0f);
        newRating = sn.reviewItemBook(pseudo1, password1, title, 5.0f, "Amazing !");
        if (newRating != 5.0) {
            System.out.println("17.2 Le karma maximum modifie la note maximum.");
            nbErreurs++;
        }

        // 17.3 Vérification de la réduction de la note (karma négatif)
        nbTests++;
        sn.gradeReviewItemBook(pseudo2, password2, pseudo1, title, 1.0f);
        newRating = sn.reviewItemBook(pseudo1, password1, title, 5.0f, "Amazing !");
        if (newRating >= 5.0) {
            System.out.println("17.3 Un karma négatif ne diminue pas la note.");
            nbErreurs++;
        }

        // 17.4 Vérification de l'augmentation de la note (karma positif)
        nbTests++;
        sn.gradeReviewItemBook(pseudo2, password2, pseudo1, title, 3.0f);
        newRating = sn.reviewItemBook(pseudo1, password1, title, 1.0f, "Amazing !");
        if (newRating <= 1.0) {
            System.out.println("17.4 Un karma positif n'augmente pas la note.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }
}

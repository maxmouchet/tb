package test;

import avis.SocialNetwork;
import exception.*;

import java.util.HashMap;

public class TestsReviewItemBook {

    private static int reviewItemBookOKTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, Float rating, Float expectedRating, String comment) {
        try {
            float newRating = sn.reviewItemBook(pseudo, password, title, rating, comment);

            if (newRating != expectedRating) {
                System.out.println("Test " + idTest + " : la note du livre n'a pas été correctement mise à jour.");
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

    private static int reviewItemBookBadEntryTest(String idTest, SocialNetwork sn, String realPseudo, String realPassword, String pseudo, String password, String title, Float rating, Float expectedRating, String comment, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, password, title, rating, comment);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry badEntry) {
            float newRating = sn.reviewItemBook(realPseudo, realPassword, title, expectedRating, "Amazing !");

            if (newRating != expectedRating) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais la note du livre a été modifié");
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

    private static int reviewItemBookNotItemTest(String idTest, SocialNetwork sn, String pseudo, String password, String title, Float rating, String comment, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, password, title, rating, comment);
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

    private static int reviewItemBookNotMemberTest(String idTest, SocialNetwork sn, String realPseudo, String realPassword, String pseudo, String password, String title, Float rating, Float expectedRating, String comment, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, password, title, rating, comment);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotMember notMember) {
            float newRating = sn.reviewItemBook(realPseudo, realPassword, title, expectedRating, comment);

            if (newRating != expectedRating) {
                System.out.println("Test " + idTest + " : l'exception NotMember a bien été levé, mais la note du livre a été modifié");
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

    private static HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo1, String password1, String pseudo2, String password2) throws NotMember, BadEntry, ItemBookAlreadyExists, NotItem {
        System.out.println("\n# Tests d'ajout d'avis sur des livres");

        int nbTests = 0;
        int nbErreurs = 0;

        // Ajout d'un livre pour les tests
        String title = "What the Dormouse Said";
        String genre = "History";
        String author = "John Markoff";
        int pageCount = 200;

        System.out.println("* Ajout d'un livre pour les tests: " + title);
        sn.addItemBook(pseudo1, password1, title, genre, author, pageCount);

        int nbFilms = sn.nbFilms();
        int nbLivres = sn.nbBooks();

        // Valeurs par defaut pour les tests
        float expectedRating = 3.0f;
        String comment = "Amazing !";

        // Fiche 5
        // Tentatives d'ajout de reviews avec des entrées correctes

        // Ajout d'une review avec l'utilisateur 1
        nbTests++;
        nbErreurs += reviewItemBookOKTest("5.1", sn, pseudo1, password1, title, 1.0f, 1.0f, comment);

        // Modification de la review de l'utilisateur 1
        nbTests++;
        nbErreurs += reviewItemBookOKTest("5.2", sn, pseudo1, password1, title, 5.0f, 5.0f, comment);

        // Ajout d'une review avec l'utilisateur 2
        nbTests++;
        nbErreurs += reviewItemBookOKTest("5.3", sn, pseudo2, password2, title, 3.0f, 4.0f, comment);

        // Fiche 6
        // Tentatives d'ajout de reviews avec des entrées incorrectes

        nbTests++;
        nbErreurs += reviewItemBookBadEntryTest("6.1", sn, pseudo1, password1, pseudo1, password1, title, -1.0f, expectedRating, comment, "L'ajout d'une review avec une note negative est autorisé.");
        nbTests++;
        nbErreurs += reviewItemBookBadEntryTest("6.2", sn, pseudo1, password1, pseudo1, password1, title, 6.0f, expectedRating, comment, "L'ajout d'une review avec une note supérieure au maximum est autorisé.");
        nbTests++;
        nbErreurs += reviewItemBookBadEntryTest("6.3", sn, pseudo1, password1, pseudo1, password1, title, 1.0f, expectedRating, null, "L'ajout d'une review null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemBookBadEntryTest("6.4", sn, pseudo1, password1, null, password1, title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un pseudo null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemBookBadEntryTest("6.5", sn, pseudo1, password1, pseudo1, null, title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un password null est autorisé.");
        nbTests++;
        nbErreurs += reviewItemBookBadEntryTest("6.6", sn, pseudo1, password1, "   ", password1, title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un pseudo composé uniquement d'espaces est autorisé.");

        nbTests++;
        nbErreurs += reviewItemBookNotItemTest("6.7", sn, pseudo1, password1, "Alice chez les Barbapapas.", 1.0f, comment, "L'ajout d'une review pour un livre inexistant est autorisé.");

        nbTests++;
        nbErreurs += reviewItemBookNotMemberTest("6.8", sn, pseudo1, password1, "BillGate$$38", "Micro$$oft", title, 1.0f, expectedRating, comment, "L'ajout d'une review pour un membre inexistant est autorisé.");
        nbTests++;
        nbErreurs += reviewItemBookNotMemberTest("6.9", sn, pseudo1, password1, pseudo1, "Ju5nPa5lo2015", title, 1.0f, expectedRating, comment, "L'ajout d'une review avec un mauvais mot de passe est autorisé.");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur: le nombre de films après utilisation de reviewItemBook a été modifié.");
            nbErreurs++;
        }

        nbTests++;
        if (nbLivres != sn.nbBooks()) {
            System.out.println("Erreur: le nombre de livres après utilisation de reviewItemBook a été modifié.");
            nbErreurs++;
        }

        HashMap<String, Integer> testsResults = new HashMap<String, Integer>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);
        return testsResults;
    }

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemBookAlreadyExists, NotMember, NotItem {
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
        System.out.println("-> TestsReviewItemBook: " + testsResults.get("errors") + " erreur(s) / " + testsResults.get("total") + " tests effectués");
    }
}

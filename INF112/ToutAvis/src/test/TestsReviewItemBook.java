package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotMember;

public class TestsReviewItemBook {
    public static int reviewItemBookOKTest (SocialNetwork sn, String pseudo, String pwd, String profil, String book, String idTest) {
    }

    public static int reviewItemBookNegativeRatingTest (SocialNetwork sn, String pseudo, String pwd, String profil, String book, String idTest, String messErreur) {
    }

    public static int reviewItemBookInvalidRatingTest (SocialNetwork sn, String pseudo, String pwd, String profil, String book, String idTest, String messErreur) {
    }

    public static int reviewItemBookNullCommentTest (SocialNetwork sn, String pseudo, String pwd, String profil, String book, String idTest, String messErreur) {
    }

    public static int reviewItemBookNullBookTest (SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
    }

    public static int reviewItemBookNotMemberTest (SocialNetwork sn, String profil, String book, String idTest, String messErreur) {
    }

    public static int reviewItemBookWrongPasswordTest (SocialNetwork sn, String pseudo, String profil, String book, String idTest, String messErreur) {
    }

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemBookAlreadyExists, NotMember {
        int nbTests = 0;
        int nbErreurs = 0;

        System.out.println("# Ajout de reviews de livres au réseau social");
        SocialNetwork sn = new SocialNetwork();

        String pseudo = "ToTo";
        String pwd = "Pa$$w0rd";
        String profil = "tAtA";

        System.out.println("Ajout d'un membre pour le test: " + pseudo);
        sn.addMember(pseudo, pwd, profil);

        String titre = "What the Dormouse Said";
        System.out.println("Ajout d'un livre pour le test: " + titre);
        sn.addItemBook(pseudo, pwd, titre, "History", "John Markoff", 200);

        // tests de review
        int nbFilms = sn.nbFilms();
        int nbLivres = sn.nbBooks();

        // <=> fiche numéro 5
        // tentative d'ajout de reviews avec entrées "correctes"

        nbTests++;
        nbErreurs += reviewItemBookOKTest(sn, pseudo, pwd, profil, titre, "1.1");

        // <=> fiche numéro 6
        // tentative d'ajout de reviews avec entrées "incorrectes"

        nbTests++;
        nbErreurs += reviewItemBookNegativeRatingTest(sn, pseudo, pwd, profil, titre, "2.1", "L'ajout d'une review avec une note negative est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookInvalidRatingTest(sn, pseudo, pwd, profil, titre, "2.2", "L'ajout d'une review avec une note supérieure au maximum est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookNullCommentTest(sn, pseudo, pwd, profil, titre, "2.3", "L'ajout d'une review null est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookNullBookTest(sn, pseudo, pwd, profil, "2.4", "L'ajout d'une review avec un titre de livre non existant est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookNotMemberTest(sn, profil, titre, "2.6", "L'ajout d'une review pour un membre inexistant est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookWrongPasswordTest(sn, pseudo, profil, titre, "2.7", "L'ajout d'une review avec un mauvais mot de passe est autorisé");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur  :  le nombre de films après utilisation de reviewItemBook a été modifié");
            nbErreurs++;
        }

        nbTests++;
        if (nbLivres != sn.nbBooks()) {
            System.out.println("Erreur  :  le nombre de livres après utilisation de reviewItemBook a été modifié");
            nbErreurs++;
        }

        // ce n'est pas du test, mais cela peut "rassurer"...
        System.out.println(sn);

        // bilan du test de addMember
        System.out.println("TestsReviewItemBook :   " + nbErreurs + " erreur(s) / " +  nbTests + " tests effectués");

    }
}

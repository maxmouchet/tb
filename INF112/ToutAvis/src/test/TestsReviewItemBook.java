package test;

import avis.SocialNetwork;
import exception.*;

public class TestsReviewItemBook {

    public static int reviewItemBookOKTest(SocialNetwork sn, String pseudo, String pwd, String profil, String book, Float rating, String idTest) {
        try {
            float newRating = sn.reviewItemBook(pseudo, pwd, book, rating, "Amazing !");

            if (newRating != rating) {
                System.out.println("Test " + idTest + " : la note du livre n'a pas été correctement incrémenté");
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

    public static int reviewItemBookNegativeRatingTest(SocialNetwork sn, String pseudo, String pwd, String profil, String book, Float rating, String idTest, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, pwd, book, -1.0f, "Amazing !");
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry badEntry) {
            float newRating = sn.reviewItemBook(pseudo, pwd, book, rating, "Amazing !");

            if (newRating != rating) {
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

    public static int reviewItemBookInvalidRatingTest(SocialNetwork sn, String pseudo, String pwd, String profil, String book, Float rating, String idTest, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, pwd, book, 6.0f, "Amazing !");

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry badEntry) {
            float newRating = sn.reviewItemBook(pseudo, pwd, book, rating, "Amazing !");

            if (newRating != rating) {
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

    public static int reviewItemBookNullCommentTest(SocialNetwork sn, String pseudo, String pwd, String profil, String book, Float rating, String idTest, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, pwd, book, 1.0f, null);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry badEntry) {
            float newRating = sn.reviewItemBook(pseudo, pwd, book, rating, "Amazing !");

            if (newRating != rating) {
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

    public static int reviewItemBookUnknownBookTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        try {
            sn.reviewItemBook(pseudo, pwd, "Les MOOC", 1.0f, "Amazing !");

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry badEntry) {
            return 0;
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int reviewItemBookNotMemberTest(SocialNetwork sn, String pseudo, String pwd, String profil, String book, Float rating, String idTest, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook("BillGate$$38", "Micro$$oft", book, 1.0f, null);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotMember notMember) {
            float newRating = sn.reviewItemBook(pseudo, pwd, book, rating, "Amazing !");

            if (newRating != rating) {
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

    public static int reviewItemBookWrongPasswordTest(SocialNetwork sn, String pseudo, String pwd, String profil, String book, Float rating, String idTest, String messErreur) throws NotMember, BadEntry, NotItem {
        try {
            sn.reviewItemBook(pseudo, "Ju5nPa5lo2015", book, 1.0f, null);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotMember notMember) {
            float newRating = sn.reviewItemBook(pseudo, pwd, book, rating, "Amazing !");

            if (newRating != rating) {
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

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemBookAlreadyExists, NotMember, NotItem {
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
        Float rating = 5.0f;

        System.out.println("Ajout d'un livre pour le test: " + titre);
        sn.addItemBook(pseudo, pwd, titre, "History", "John Markoff", 200);

        // tests de review
        int nbFilms = sn.nbFilms();
        int nbLivres = sn.nbBooks();

        // <=> fiche numéro 5
        // tentative d'ajout de reviews avec entrées "correctes"

        nbTests++;
        nbErreurs += reviewItemBookOKTest(sn, pseudo, pwd, profil, titre, rating, "1.1");

        // <=> fiche numéro 6
        // tentative d'ajout de reviews avec entrées "incorrectes"

        nbTests++;
        nbErreurs += reviewItemBookNegativeRatingTest(sn, pseudo, pwd, profil, titre, rating, "2.1", "L'ajout d'une review avec une note negative est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookInvalidRatingTest(sn, pseudo, pwd, profil, titre, rating, "2.2", "L'ajout d'une review avec une note supérieure au maximum est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookNullCommentTest(sn, pseudo, pwd, profil, titre, rating, "2.3", "L'ajout d'une review null est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookUnknownBookTest(sn, pseudo, pwd, profil, "2.4", "L'ajout d'une review avec un titre de livre non existant est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookNotMemberTest(sn, pseudo, pwd, profil, titre, rating, "2.5", "L'ajout d'une review pour un membre inexistant est autorisé");
        nbTests++;
        nbErreurs += reviewItemBookWrongPasswordTest(sn, pseudo, pwd, profil, titre, rating, "2.6", "L'ajout d'une review avec un mauvais mot de passe est autorisé");

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

        // bilan du test de reviewItemBook
        System.out.println("TestsReviewItemBook :   " + nbErreurs + " erreur(s) / " + nbTests + " tests effectués");
    }
}

package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.MemberAlreadyExists;

import java.util.HashMap;

/**
 * @author B. Prou, E. Cousin
 * @version V1.0
 * @date mars 2015
 */

public class TestsAddMember {

    private static int addMemberBadEntryTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        // vérifie que l'ajout d'un membre (pseudo, pwd, profil) est refusée (levée de l'exception BadEntry et  pas de modification du sn)
        // si c'est bien le cas, ne fait rien
        // sinon, affiche le message d'erreur passé en paramètre
        int nbMembres = sn.nbMembers();
        try {
            sn.addMember(pseudo, pwd, profil);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbMembers() != nbMembres) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levée mais le nombre de membres a été modifié");
                return 1;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int addMemberOKTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest) {
        int nbMembres = sn.nbMembers();
        try {
            sn.addMember(pseudo, pwd, profil);
            if (sn.nbMembers() != nbMembres + 1) {
                System.out.println("Test " + idTest + " :  le nombre de membres n'a pas été correctement incrémenté");
                return 1;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    private static int addMemberAlreadyExistsTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbMembres = sn.nbMembers();
        try {
            sn.addMember(pseudo, pwd, profil);
            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (MemberAlreadyExists e) {
            if (sn.nbMembers() != nbMembres) {
                System.out.println("Test " + idTest + " : l'exception MemberAlreadyExists a bien été levée mais le nombre de membres a été modifié");
                return 1;
            } else
                return 0;
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static HashMap<String, Integer> runTests(SocialNetwork sn) {
        System.out.println("\n# Tests d'ajout de membres");

        int nbTests = 0;
        int nbErreurs = 0;

        // tests de addMember
        int nbFilms = sn.nbFilms();
        int nbLivres = sn.nbBooks();

        // <=> fiche numéro 1

        // tentative d'ajout de membres avec entrées "incorrectes"

        nbTests++;
        nbErreurs += addMemberBadEntryTest(sn, null, "qsdfgh", "", "1.1", "L'ajout d'un membre dont le pseudo n'est pas instancié est accepté");
        nbTests++;
        nbErreurs += addMemberBadEntryTest(sn, " ", "qsdfgh", "", "1.2", "L'ajout d'un membre dont le pseudo ne contient pas un caracteres, autre que des espaces, est accepté");
        nbTests++;
        nbErreurs += addMemberBadEntryTest(sn, "B", null, "", "1.3", "L'ajout d'un membre dont le password n'est pas instancié est accepté");
        nbTests++;
        nbErreurs += addMemberBadEntryTest(sn, "B", "   qwd ", "", "1.4", "L'ajout d'un membre dont le password ne contient pas au moins 4 caracteres, autre que des espaces de début ou de fin, est accepté");
        nbTests++;
        nbErreurs += addMemberBadEntryTest(sn, "BBBB", "bbbb", null, "1.5", "L'ajout d'un membre dont le profil n'est pas instancié est accepté");


        // <=> fiche numéro 2

        // ajout de 3 membres avec entrées "correctes"

        nbTests++;
        nbErreurs += addMemberOKTest(sn, "Paul", "paul", "lecteur impulsif", "2.1a");
        nbTests++;
        nbErreurs += addMemberOKTest(sn, "Antoine", "antoine", "grand amoureux de la littérature", "2.1b");
        nbTests++;
        nbErreurs += addMemberOKTest(sn, "Alice", "alice", "20 ans, sexy", "2.1c");

        // tentative d'ajout de membre "existant"

        nbTests++;
        nbErreurs += addMemberAlreadyExistsTest(sn, new String("Paul"), "abcdefghij", "", "2.2", "L'ajout d'un membre avec le pseudo du premier membre ajouté est accepté");
        nbTests++;
        nbErreurs += addMemberAlreadyExistsTest(sn, new String("Alice"), "abcdefghij", "", "2.3", "L'ajout d'un membre avec le pseudo du dernier membre ajouté est accepté");
        nbTests++;
        nbErreurs += addMemberAlreadyExistsTest(sn, new String("anToine"), "abcdefghij", "", "2.4", "L'ajout d'un membre avec un pseudo existant (avec casse différente) est accepté");
        nbTests++;
        nbErreurs += addMemberAlreadyExistsTest(sn, new String(" Antoine "), "abcdefghij", "", "2.5", "L'ajout d'un membre avec un pseudo existant (avec leading et trailing blanks) est accepté");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur: le nombre de films après utilisation de addMember a été modifié");
            nbErreurs++;
        }
        nbTests++;
        if (nbLivres != sn.nbBooks()) {
            System.out.println("Erreur: le nombre de livres après utilisation de addMember a été modifié");
            nbErreurs++;
        }

        // bilan du test de addMember
        HashMap<String, Integer> testsResults = new HashMap<String, Integer>();
        testsResults.put("errors", nbErreurs);
        testsResults.put("total", nbTests);

        System.out.println("-> TestsAddMember: " + testsResults.get("errors") + " erreur(s) / " + testsResults.get("total") + " tests effectués");
        return testsResults;
    }
}

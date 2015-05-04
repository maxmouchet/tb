package test;

import avis.SocialNetwork;
import exception.*;

import java.util.HashMap;
import java.util.LinkedList;

public class SocialNetworkTester {

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemBookAlreadyExists, NotMember, NotItem, ItemFilmAlreadyExists {
        // SocialNetwork pour les tests
        SocialNetwork sn = new SocialNetwork();

        // Ajout de membres pour les tests
        // Membre 1
        String pseudo1 = "ToTo";
        String password1 = "Pa$$w0rd";
        String profil1 = "tAtA";

        // Membre 2
        String pseudo2 = "JoJo";
        String password2 = "Pa$$w0rd";
        String profil2 = "lOlO";

        sn.addMember(pseudo1, password1, profil1);
        sn.addMember(pseudo2, password2, profil1);

        // Résultats des tests unitaires
        LinkedList<HashMap<String, Integer>> testsResults = new LinkedList<HashMap<String, Integer>>();

        TestsInitialisation.main(args);
        testsResults.add(TestsAddMember.runTests(sn));
        testsResults.add(TestsAddItemBook.runTests(sn, pseudo1, password1));
        testsResults.add(TestsReviewItemBook.runTests(sn, pseudo1, password1, pseudo2, password2));
        testsResults.add(TestsAddItemFilm.runTests(sn, pseudo1, password1));
        testsResults.add(TestsReviewItemFilm.runTests(sn, pseudo1, password1, pseudo2, password2));
        testsResults.add(TestsConsultItems.runTests(sn, pseudo1, password1));

        System.out.println("\n*** Synthèse des tests ***");

        Integer totalTests = 0, totalErrors = 0;
        for (HashMap<String, Integer> testResult : testsResults) {
            totalTests += testResult.get("total");
            totalErrors += testResult.get("errors");
        }

        System.out.println(totalTests + " tests, " + totalErrors + " erreurs.\n");

        // Tests de performance
        TestsPerformances.testPerformances(100, 100, 100);
        TestsPerformances.testPerformances(1000, 1000, 1000);
        TestsPerformances.testPerformances(10000, 10000, 10000);
    }

}

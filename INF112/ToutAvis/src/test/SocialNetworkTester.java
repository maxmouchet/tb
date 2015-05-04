package test;

import avis.SocialNetwork;

import java.util.HashMap;
import java.util.LinkedList;

public class SocialNetworkTester {

    public static void main(String[] args) throws Exception {
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

        // Tests unitaires
        LinkedList<HashMap<String, Integer>> testsResults = new LinkedList<HashMap<String, Integer>>();

        TestsInitialisation.main(args);
        testsResults.add(TestsAddMember.runTests(sn));
        testsResults.add(TestsAddItemBook.runTests(sn, pseudo1, password1));
        testsResults.add(TestsReviewItemBook.runTests(sn, pseudo1, password1, pseudo2, password2));
        testsResults.add(TestsAddItemFilm.runTests(sn, pseudo1, password1));
        testsResults.add(TestsReviewItemFilm.runTests(sn, pseudo1, password1, pseudo2, password2));
        testsResults.add(TestsConsultItems.runTests(sn, pseudo1, password1));

        System.out.println("\n*** Synthèse des tests unitaires ***");

        Integer totalTests = 0, totalErrors = 0;
        for (HashMap<String, Integer> testResult : testsResults) {
            totalTests += testResult.get("total");
            totalErrors += testResult.get("errors");
        }

        System.out.println(totalTests + " tests, " + totalErrors + " erreurs.\n");

        // Tests des performances
        System.out.println("*** Tests des performances ***");
        System.out.println("Note: une review par item est ajouté.");
        System.out.println("Members | Books | Films | addMember() | addItemBook() | addItemFilm() | reviewItemBook() | reviewItemFilm() | consultItems()");

        for (int i = 1; i <= 100000; i *= 10) {
            HashMap<String, Long> performanceResult = TestsPerformances.testPerformances(i, i, i);
            System.out.format("%8d|%7d|%7d|%10d ms|%12d ms|%12d ms|%15d ms|%15d ms|%12d ms\n", i, i, i, performanceResult.get("addMember"), performanceResult.get("addItemBook"), performanceResult.get("addItemFilm"), performanceResult.get("reviewItemBook"), performanceResult.get("reviewItemFilm"), performanceResult.get("consultItems"));

        }
    }

}

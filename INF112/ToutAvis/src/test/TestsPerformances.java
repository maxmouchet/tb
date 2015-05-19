package test;

import avis.SocialNetwork;

import java.lang.ref.WeakReference;
import java.util.HashMap;

class TestsPerformances {
    public static HashMap<String, Long> testPerformances(int members, int books, int films) throws Exception {
        if (members < 1 || books < 1 || films < 1) {
            throw new Exception("At least one member and/or book and/or film is required to test performances.");
        }

        HashMap<String, Long> results = new HashMap<>();
        SocialNetwork sn = new SocialNetwork();

        long startTime;
        long endTime;

        // Force garbage collection before running tests
        Object obj = new Object();
        WeakReference ref = new WeakReference<>(obj);
        obj = null;
        while (ref.get() != null) {
            System.gc();
        }

        startTime = System.nanoTime();
        for (int i = 0; i < members; i++) {
            sn.addMember("User" + i, "Password", "Profil");
        }
        endTime = System.nanoTime();
        results.put("addMember", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < books; i++) {
            sn.addItemBook("User0", "Password", "Book" + i, "Roman", "James Joyce", 200);
        }
        endTime = System.nanoTime();
        results.put("addItemBook", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < films; i++) {
            sn.addItemFilm("User0", "Password", "Film" + i, "Drame", "James Cameron", "JC", 120);
        }
        endTime = System.nanoTime();
        results.put("addItemFilm", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < films; i++) {
            sn.reviewItemBook("User0", "Password", "Book" + i, 5.0f, "Amazing !");
        }
        endTime = System.nanoTime();
        results.put("reviewItemBook", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < films; i++) {
            sn.reviewItemFilm("User0", "Password", "Film" + i, 5.0f, "Amazing !");
        }
        endTime = System.nanoTime();
        results.put("reviewItemFilm", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < films; i++) {
            sn.consultItems("Film" + i);
        }
        endTime = System.nanoTime();
        results.put("consultItems", (endTime - startTime) / (1000 * 1000));

        return results;
    }
}

package test;

import avis.SocialNetwork;

import java.lang.ref.WeakReference;
import java.util.HashMap;

class TestsPerformances {
    public static HashMap<String, Long> testPerformances(int members, int books, int films) throws Exception {
        if (members < 2 || books < 2 || films < 2) {
            throw new Exception("At least two members and/or books and/or films are required to test performances.");
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
        for (int i = 0; i < books; i++) {
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
        for (int i = 0; i < books; i++) {
            sn.gradeReviewItemBook("User1", "Password", "User0", "Book" + i, 3.0f);
        }
        endTime = System.nanoTime();
        results.put("gradeReviewItemBook", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < films; i++) {
            sn.gradeReviewItemFilm("User1", "Password", "User0", "Film" + i, 3.0f);
        }
        endTime = System.nanoTime();
        results.put("gradeReviewItemFilm", (endTime - startTime) / (1000 * 1000));

        startTime = System.nanoTime();
        for (int i = 0; i < books; i++) {
            sn.consultItems("Book" + i);
        }
        endTime = System.nanoTime();
        results.put("consultItems", (endTime - startTime) / (1000 * 1000));

        return results;
    }
}

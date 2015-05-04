package test;

import avis.SocialNetwork;

import java.util.HashMap;

public class TestsPerformances {
    public static HashMap<String, Long> testPerformances(int members, int books, int films) throws Exception {
        if (members < 1 || books < 1 || films < 1) {
            throw new Exception("At least one member and/or book and/or film is required to test performances.");
        }

        HashMap<String, Long> results = new HashMap<String, Long>();
        SocialNetwork sn = new SocialNetwork();

        long startTime;
        long endTime;

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

        return results;
    }
}

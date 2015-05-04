package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.MemberAlreadyExists;

public class TestsPerformances {
    public static void testPerformances(int members, int books, int films) throws MemberAlreadyExists, BadEntry {
        // Une review par member et item

        long startTime;
        long endTime;

        SocialNetwork sn = new SocialNetwork();

        startTime = System.nanoTime();
        for (int i = 0; i < members; i++) {
            sn.addMember("User" + i, "Password", "Profil");
        }
        endTime = System.nanoTime();

        System.out.println("Run time for SocialNetwork.addMember() with " + members + " members: " + (endTime - startTime) / (1000 * 1000) + " milliseconds.");
    }
}

package test;

import exception.*;

public class SocialNetworkTester {

    /**
     * @param args
     * @throws MemberAlreadyExists
     * @throws BadEntry
     * @throws NotItem
     * @throws NotMember
     * @throws ItemBookAlreadyExists
     */
    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemBookAlreadyExists, NotMember, NotItem, ItemFilmAlreadyExists {
        // TODO: Add summary of all tests results
        TestsInitialisation.main(args);
        TestsAddMember.main(args);
        TestsAddItemBook.main(args);
        TestsReviewItemBook.main(args);
        TestsAddItemFilm.main(args);
    }

}

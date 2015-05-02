package test;

import exception.*;

public class SocialNetworkTester {

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists, ItemBookAlreadyExists, NotMember, NotItem, ItemFilmAlreadyExists {
        TestsInitialisation.main(args);
        TestsAddMember.main(args);
        TestsAddItemBook.main(args);
        TestsReviewItemBook.main(args);
        TestsAddItemFilm.main(args);
        TestsReviewItemFilm.main(args);
    }

}

package avis.models;

import exception.BadEntry;

import java.util.LinkedList;


public class Member {

    /**
     * @uml.property name="reviews"
     * @uml.associationEnd multiplicity="(0 -1)" inverse="member:avis.models.Review"
     */
    private LinkedList<Review> reviews;

    private String pseudo;

    private String password;

    private String profile;

    public Member(String pseudo, String password, String profile) throws BadEntry {
        boolean isValid = pseudoIsValid(pseudo) && passwordIsValid(password) && profileIsValid(profile);

        if (!isValid) {
            throw new BadEntry("Pseudo and/or password does not meet the requirements.");
        }

        this.pseudo = pseudo;
        this.password = password;
        this.profile = profile;

        this.reviews = new LinkedList<Review>();
    }

    public static boolean pseudoIsValid(String pseudo) {
        return (pseudo != null) && (pseudo.trim().length() > 1);
    }

    public static boolean passwordIsValid(String password) {
        return (password != null) && (password.trim().length() >= 4);
    }

    private static boolean profileIsValid(String profile) {
        return profile != null;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public Review findReview(String title) {
        Review review = null;

        for (Review r : reviews) {
            if (r.getItem().getTitle().equals(title)) {
                review = r;
                break;
            }
        }

        return review;
    }

    public boolean checkCredentials(String pseudo, String password) {
        return (this.pseudo.equals(pseudo) && this.password.equals(password));
    }

    @Override
    public String toString() {
        String output = "";

        // Password is not shown for security reasons.
        output += "Pseudo: " + pseudo + "\n";
        output += "Profile: " + profile + "\n";
        output += reviews.size() + " reviews" + "\n";

        return output;
    }
}

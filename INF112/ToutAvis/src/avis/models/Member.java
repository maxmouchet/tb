package avis.models;

import avis.structures.MemberKey;
import avis.structures.ReviewGradeKey;
import avis.structures.ReviewKey;
import exception.BadEntry;

import java.util.HashMap;

/**
 * Représente un membre du SocialNetwork.
 */
public class Member {

    /**
     * Une clé caractérisant de manière unique le membre.
     */
    public final MemberKey mapKey;
    /**
     * La liste des reviews écrites par le membre.
     *
     * @uml.property name="reviews"
     * @uml.associationEnd multiplicity="(0 -1)" inverse="member:avis.models.Review"
     */
    private final HashMap<ReviewKey, Review> reviews;
    /**
     * @uml.property name="reviewGrade"
     * @uml.associationEnd multiplicity="(0 -1)" inverse="member:avis.models.ReviewGrade"
     */
    private final HashMap<ReviewGradeKey, ReviewGrade> reviewGrades;
    /**
     * Le pseudo du membre.
     */
    private final String pseudo;
    /**
     * Le mot de passe du membre.
     */
    private final String password;
    /**
     * La description du membre.
     */
    private final String profile;
    /**
     * Le karma du membre.
     * Compris entre 1.0 et 3.0 il pondère négativement
     * ou positivement les avis émis par le membre.
     */
    private float karma;

    /**
     * Initialise un membre.
     *
     * @param pseudo   le pseudo du membre.
     * @param password le mot de passe du membre.
     * @param profile  la description du membre.
     * @throws BadEntry <ul>
     *                  <li>si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .</li>
     *                  <li>si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks.</li>
     *                  <li>si le profil n'est pas instancié.</li>
     *                  </ul><br>
     */
    public Member(String pseudo, String password, String profile) throws BadEntry {
        boolean isValid = pseudoIsValid(pseudo) && passwordIsValid(password) && profileIsValid(profile);

        if (!isValid) {
            throw new BadEntry("Pseudo and/or password does not meet the requirements.");
        }

        this.pseudo = pseudo;
        this.password = password;
        this.profile = profile;

        this.reviews = new HashMap<>();
        this.reviewGrades = new HashMap<>();

        this.mapKey = new MemberKey(pseudo);
    }

    /**
     * Vérifie que le pseudo respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param pseudo le pseudo du membre.
     * @return true si le pseudo est valid. false sinon.
     */
    public static boolean pseudoIsValid(String pseudo) {
        return (pseudo != null) && (pseudo.trim().length() > 1);
    }

    /**
     * Vérifie que le mot de passe respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param password le mot de passe du membre.
     * @return true si le mot de passe est valide. false sinon.
     */
    public static boolean passwordIsValid(String password) {
        return (password != null) && (password.trim().length() >= 4);
    }

    /**
     * Vérifie que la description du membre respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param profile la description du membre.
     * @return true si la description est valide. false sinon.
     */
    private static boolean profileIsValid(String profile) {
        return profile != null;
    }

    /**
     * Obtient le pseudo du membre.
     *
     * @return le pseudo du membre.
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Ajoute une review au membre.
     *
     * @param review la review.
     */
    public void addReview(Review review) {
        this.reviews.put(review.mapKey, review);
        updateKarma(review.getAverageGrade());
    }

    /**
     * Ajoute une notation de review au membre.
     *
     * @param reviewGrade la notation de review.
     */
    public void addReviewGrade(ReviewGrade reviewGrade) {
        this.reviewGrades.put(reviewGrade.mapKey, reviewGrade);
    }

    /**
     * Recherche la review laissé par le membre pour un item donné.
     *
     * @param item l'item.
     * @return la review, si elle existe. null sinon.
     */
    public Review findReview(Item item) {
        return this.reviews.get(new ReviewKey(this.mapKey, item.mapKey));
    }

    /**
     * Recherche la note laissé par le membre pour une review donnée.
     *
     * @param review la review.
     * @return la note, si elle existe. null sinon.
     */
    public ReviewGrade findReviewGrade(Review review) {
        return this.reviewGrades.get(new ReviewGradeKey(this.mapKey, review.mapKey));
    }

    /**
     * Vérifie les identifiants du membre.
     *
     * @param pseudo   le pseudo.
     * @param password le mot de passe.
     * @return true si le pseudo et le mot de passe correspondent au membre. false sinon.
     */
    public boolean checkCredentials(String pseudo, String password) {
        return (this.pseudo.equals(pseudo) && this.password.equals(password));
    }

    /**
     * Met à jour le karma du membre suite à l'ajout d'une notation sur un avis.
     *
     * @param newAverage la nouvelle note moyenne associée à l'avis.
     */
    public void updateKarma(float newAverage) {
        karma = ((reviews.size() - 1) * karma + newAverage) / reviews.size();
    }

    /**
     * Met à jour le karma du membre suite à la mise à jour de la notation d'un avis.
     *
     * @param oldAverage l'ancienne note moyenne associée à l'avis.
     * @param newAverage la nouvelle note moyenne associée à l'avis.
     */
    public void updateKarma(float oldAverage, float newAverage) {
        karma = (reviews.size() * karma + (newAverage - oldAverage)) / reviews.size();
    }

    /**
     * Obtient le karma du membre.
     *
     * @return le karma du membre.
     */
    public float getKarma() {
        return karma;
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

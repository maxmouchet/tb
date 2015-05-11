package avis.models;

import avis.SocialNetwork;
import exception.BadEntry;

import java.util.HashMap;
import java.util.Collection;

/**
 * Représente un membre du SocialNetwork.
 */
public class Member {

    /**
	 * La liste des reviews écrites par le membre.
	 * @uml.property   name="reviews"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="member:avis.models.Review"
	 */
    private HashMap<String, Review> reviews;
    
	/**
	 * @uml.property   name="reviewGrade"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="member:avis.models.ReviewGrade"
	 */
	private HashMap<String, ReviewGrade> reviewGrades;

    /**
     * Le pseudo du membre.
     */
    private String pseudo;

    /**
     * Le mot de passe du membre.
     */
    private String password;

    /**
     * La description du membre.
     */
    private String profile;

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
        String hashKey = SocialNetwork.getHashKeyForClass(review.getItem().getClass(), review.getItem().getTitle());
        this.reviews.put(hashKey, review);
    }
    
    public void addReviewGrade(ReviewGrade reviewGrade) {
        String hashKey = SocialNetwork.getHashKeyForClass(reviewGrade.getReview().getItem().getClass(), reviewGrade.getReview().getItem().getTitle() + reviewGrade.getReview().getMember().getPseudo());
        this.reviewGrades.put(hashKey, reviewGrade);
    }

    /**
     * Recherche la review laissé par le membre pour un type et un titre donné.
     *
     * @param klass le type de l'item ({@code Book} ou {@code Film}).
     * @param title le titre de l'item.
     * @return la review, si elle existe. null sinon.
     */
    public Review findReview(Class<?> klass, String title) {
    	// TODO: Refactor avec Item en parametre.
        return this.reviews.get(SocialNetwork.getHashKeyForClass(klass, title));
    }
    
    public ReviewGrade findReviewGrade(Review review) {
        return this.reviewGrades.get(SocialNetwork.getHashKeyForClass(review.getItem().getClass(), review.getItem().getTitle() + review.getMember().getPseudo()));
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
    
    public float getKarma() {
    	float sum = 0.0f;
        float nbGrades = reviews.size();

        for (Review review : reviews.values()) {
            sum += review.getGrade();
        }
        return 1;
        //return sum / nbGrades;
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

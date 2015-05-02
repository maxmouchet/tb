package avis.models;

import exception.BadEntry;

import java.util.LinkedList;

/**
 * Représente un item du SocialNetwork.
 * Un item doit être d'un type défini et peut être noté par les membres du SocialNetwork.
 */
public abstract class Item {

    /**
     * Le titre de l'item.
     *
     * @uml.property name="title"
     */
    private String title;

    /**
     * Le genre de l'item.
     *
     * @uml.property name="genre"
     */
    private String genre;

    /**
     * La liste des reviews concernant l'item.
     *
     * @uml.property name="reviews"
     * @uml.associationEnd multiplicity="(0 -1)" inverse="item:avis.models.Review"
     */
    private LinkedList<Review> reviews;

    /**
     * Initialise un item. Réservé pour les classes enfants, Item étant abstraite.
     *
     * @param title le titre de l'item.
     * @param genre le genre de l'item.
     * @throws BadEntry <ul>
     *                  <li>si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.</li>
     *                  <li>si le genre n'est pas instancié.</li>
     *                  </ul>
     */
    Item(String title, String genre) throws BadEntry {
        boolean isValid = titleIsValid(title) && genreIsValid(genre);

        if (!isValid) {
            throw new BadEntry("Title and/or genre does not meet the requirements.");
        }

        this.title = title;
        this.genre = genre;
        this.reviews = new LinkedList<Review>();
    }

    /**
     * Vérifie que le titre respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param title le titre de l'item.
     * @return true si le titre est valide. false sinon.
     */
    public static boolean titleIsValid(String title) {
        return (title != null) && (title.trim().length() > 1);
    }

    /**
     * Vérifie que le genre respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param genre le genre de l'item.
     * @return true si le genre est valide. false sinon.
     */
    private static boolean genreIsValid(String genre) {
        return genre != null;
    }

    /**
     * Ajoute une review à l'item.
     *
     * @param review la review.
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Obtient le titre de l'item.
     *
     * @return le titre de l'item.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Calcule la note moyenne de l'item d'après les reviews le concernant.
     *
     * @return la note moyenne de l'item.
     */
    public float getRating() {
        float sum = 0.0f;
        float nbRating = reviews.size();

        for (Review review : this.reviews) {
            sum += review.getRating();
        }

        return sum / nbRating;
    }

    @Override
    public String toString() {
        String output = "";

        output += "Titre: " + title + "\n";
        output += "Genre: " + genre + "\n";
        output += "Reviews: " + reviews.size() + "\n";

        if (reviews.size() > 0) {
            output += "Note moyenne: " + getRating() + "\n";

        }

        return output;
    }
}

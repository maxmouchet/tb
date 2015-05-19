package avis.models;

import exception.BadEntry;

/**
 * Représente une opinion d'un utilisateur pour un item, dans le SocialNetwork.
 */
public class Review {

    /**
     * Le membre associé à la review.
     *
     * @uml.property name="member"
     * @uml.associationEnd multiplicity="(1 1)" inverse="reviews:avis.models.Member"
     */
    private final Member member;
    /**
     * L'item associé à la review.
     *
     * @uml.property name="item"
     * @uml.associationEnd multiplicity="(1 1)" inverse="reviews:avis.models.Item"
     */
    private final Item item;
    /**
     * La note pour l'item.
     *
     * @uml.property name="rating"
     */
    private float rating;
    /**
     * Le commentaire pour l'item.
     *
     * @uml.property name="comment"
     */
    private String comment;
    /**
     * La note moyenne de la review.
     * <p/>
     * Une valeur par défaut, sans effet sur le karma de l'utilisateur,
     * et la note de l'item est définie.
     */
    private float averageGrade = 2;

    /**
     *
     */
    private int gradesCount = 0;

    /**
     * Initialise une review.
     *
     * @param item    l'item associé à la review.
     * @param member  le membre associé à la review.
     * @param comment le commentaire pour l'item.
     * @param rating  la note pour l'item.
     * @throws BadEntry <ul>
     *                  <li>si la note n'est pas comprise entre 0.0 et 5.0.</li>
     *                  <li>si le commentaire n'est pas instancié.</li>
     *                  </ul>
     */
    public Review(Item item, Member member, String comment, float rating) throws BadEntry {
        this.item = item;
        this.member = member;
        update(comment, rating);
    }

    /**
     * Met à jour la note et le commentaire d'une review.
     *
     * @param comment le commentaire.
     * @param rating  la note.
     * @throws BadEntry <ul>
     *                  <li>si la note n'est pas comprise entre 0.0 et 5.0.</li>
     *                  <li>si le commentaire n'est pas instancié.</li>
     *                  </ul>
     */
    public void update(String comment, float rating) throws BadEntry {
        boolean isValid = ratingIsValid(rating) && commentIsValid(comment);

        if (!isValid) {
            throw new BadEntry("Comment and/or rating does not meet the requirements.");
        }

        this.comment = comment;
        this.rating = rating;
    }

    /**
     * Vérifie que la note respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param rating la note.
     * @return true si la note est valide. false sinon.
     */
    private boolean ratingIsValid(float rating) {
        return ((rating >= 0.0) && (rating <= 5.0));
    }

    /**
     * Vérifie que le commentaire respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param comment le commentaire.
     * @return true si le commentaire est valide. false sinon.
     */
    private boolean commentIsValid(String comment) {
        return comment != null;
    }

    /**
     * Obtient l'item associé à la review.
     *
     * @return l'item associé à la review.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Obtient le membre associé à la review.
     *
     * @return le membre associé à la review.
     */
    public Member getMember() {
        return member;
    }

    /**
     * Obtient la note associé à la review.
     *
     * @return la note associé à la review.
     */
    public float getRating() {
        float weightedRating = (member.getKarma() / 2) * rating;
        return weightedRating <= 5.0f ? weightedRating : 5.0f;
    }

    public void updateAverageGrade(float newGrade) {
        float newAverageGrade = (gradesCount * averageGrade + newGrade) / ++gradesCount;
        member.updateKarma(this.getAverageGrade(), newAverageGrade);
        this.averageGrade = newAverageGrade;
    }

    public void updateAverageGrade(float oldGrade, float newGrade) {
        float newAverageGrade = (gradesCount * averageGrade + (newGrade - oldGrade)) / gradesCount;
        member.updateKarma(this.getAverageGrade(), newAverageGrade);
        this.averageGrade = newAverageGrade;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    /**
     * Retourne une chaîne unique pour chaque review.
     * Peut-être utilisé comme clé dans une table de hachage.
     *
     * @return une chaîne unique.
     */
    public String mapKey() {
        return this.item.getClass().getName() + this.item.getTitle() + this.member.getPseudo();
    }

    @Override
    public String toString() {
        String output = "";

        output += "Review from " + member.getPseudo() + " on " + item.getTitle() + "\n";
        output += "Rating: " + rating + "\n";
        output += "Comment: " + comment + "\n";

        return output;
    }

}

package avis.models;

import avis.structures.ReviewGradeKey;
import exception.BadEntry;


/**
 * Représente la notation d'un avis par un membre du SocialNetwork.
 */
public class ReviewGrade {

    /**
     * La note donnée à l'avis.
     *
     * @uml.property name="grade"
     */
    private float grade;

    /**
     * Une clé caractérisant de manière unique la note.
     */
    public final ReviewGradeKey mapKey;

    /**
     * Initialise une note sur un avis.
     *
     * @param review la review.
     * @param member le membre noteur.
     * @param grade la note donnée à l'avis.
     * @throws BadEntry si la note n'est pas comprise entre 1.0 et 3.0.
     */
    public ReviewGrade(Review review, Member member, float grade) throws BadEntry {
        this.mapKey = new ReviewGradeKey(member.mapKey, review.mapKey);
        update(grade);
    }

    /**
     * Obtient la note donnée à l'avis.
     *
     * @return la note donnée à l'avis.
     */
    public float getGrade() {
        return this.grade;
    }

    /**
     * Met à jour la note.
     *
     * @param grade la nouvelle note.
     * @throws BadEntry si la note n'est pas comprise entre 1.0 et 3.0.
     */
    public void update(float grade) throws BadEntry {
        if (!gradeIsValid(grade)) {
            throw new BadEntry("Grade does not meet the requirements.");
        }

        this.grade = grade;
    }

    private boolean gradeIsValid(float grade) {
        return ((grade >= 1) && (grade <= 3));
    }

}

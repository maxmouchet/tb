package avis.models;

import exception.BadEntry;


public class ReviewGrade {

    /**
     * @uml.property name="grade"
     */
    private float grade;

    public final String mapKey;

    public ReviewGrade(Review review, float grade) throws BadEntry {
        this.mapKey = review.mapKey;
        update(grade);
    }

    public float getGrade() {
        return this.grade;
    }

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

package avis.models;

import avis.structures.ReviewGradeKey;
import exception.BadEntry;


public class ReviewGrade {

    /**
     * @uml.property name="grade"
     */
    private float grade;

    public final ReviewGradeKey mapKey;

    public ReviewGrade(Review review, Member member, float grade) throws BadEntry {
        this.mapKey = new ReviewGradeKey(member.mapKey, review.mapKey);
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

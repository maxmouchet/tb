package avis.models;

import exception.BadEntry;


public class ReviewGrade {

    /**
     * @uml.property name="member"
     * @uml.associationEnd multiplicity="(1 1)" inverse="reviewGrade:avis.models.Member"
     */
    private final Member member;
    /**
     * @uml.property name="review"
     * @uml.associationEnd multiplicity="(1 1)" inverse="reviewGrade:avis.models.Review"
     */
    private final Review review;
    /**
     * @uml.property name="grade"
     */
    private float grade;

    public ReviewGrade(Review review, Member member, float grade) throws BadEntry {
        this.review = review;
        this.member = member;
        update(grade);
    }

    public float getGrade() {
        return this.grade;
    }

    public Review getReview() {
        return this.review;
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

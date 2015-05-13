package avis.models;

import exception.BadEntry;



public class ReviewGrade {

	/**
	 * @uml.property  name="grade"
	 */
	private float grade;

	public float getGrade() {
		return this.grade;
	}

	/**
	 * @uml.property  name="member"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="reviewGrade:avis.models.Member"
	 */
	private Member member;

	public Member getMember() {
		return this.member;
	}

	/**
	 * @uml.property  name="review"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="reviewGrade:avis.models.Review"
	 */
	private Review review;

	public Review getReview() {
		return this.review;
	}

	public ReviewGrade(Review review, Member member, float grade) throws BadEntry {
		this.review = review;
		this.member = member;
		update(grade);
	}

	public void update(float grade) throws BadEntry {
        boolean isValid = gradeIsValid(grade);

        if (!isValid) {
            throw new BadEntry("Grade does not meet the requirements.");
        }

        this.grade = grade;
    }

	private boolean gradeIsValid(float grade) {
        return ((grade >= 1) && (grade <= 3));
    }

}

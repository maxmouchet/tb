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
		// TODO Auto-generated constructor stub
		// TODO VERIFICATIONS
		this.review = review;
		this.member = member;
		update(grade);
	}
	
	public void update(float grade) throws BadEntry {
		// TODO VERIFICATIONS
        //boolean isValid = ratingIsValid(rating) && commentIsValid(comment);

        //if (!isValid) {
        //    throw new BadEntry("Comment and/or rating does not meet the requirements.");
        //}

        this.grade = grade;
    }
	
}

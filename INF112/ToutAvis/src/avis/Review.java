package avis;

import exception.BadEntry;


public class Review {

	/**
	 * @uml.property  name="rating"
	 */
	private float rating;



	/**
	 * @uml.property  name="comment"
	 */
	private String comment;


	/**
	 * @uml.property  name="member"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="reviews:avis.Member"
	 */
	private Member member = null;

	

	/** 
	 * @uml.property name="item"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="reviews:avis.Item"
	 */
	private Item item = null;
	
	public Review(Item item, Member member) throws BadEntry {		
		this.item = item;
		this.member = member;
	}
	
	public void update(String comment, float rating) throws BadEntry {
		boolean isValid = ratingIsValid(rating) && commentIsValid(comment);
		
		if (!isValid) {
			throw new BadEntry("blablabla");
		}
		
		this.comment = comment;
		this.rating = rating;
	}
	
	private boolean ratingIsValid(float rating) {
		return ((rating >= 0.0) && (rating <= 5.0));
	}
	
	private boolean commentIsValid(String comment) {
		return comment != null;
	}

	public float getRating() {
		return rating;
	}
}

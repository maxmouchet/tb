package avis.models;

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
	 * @uml.property   name="member"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="reviews:avis.models.Member"
	 */
	private Member member = null;

	/**
	 * @uml.property   name="item"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="reviews:avis.models.Item"
	 */
	private Item item = null;
	
	public Review(Item item, Member member, String comment, float rating) throws BadEntry {		
		this.item = item;
		this.member = member;
		update(comment, rating);
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
	
	@Override
	public String toString() {
		String output = "";
		
		output += "Review from " + member.getPseudo() + " on " + item.getTitle() + "\n";
		output += "Rating: " + rating + "\n";
		output += "Comment: " + comment + "\n";
		
		return output;
	}
}

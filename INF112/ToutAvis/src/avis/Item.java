package avis;

import java.util.Collection;
import java.util.LinkedList;


public abstract class Item {

	/**
	 * @uml.property  name="title"
	 */
	private String title;

	public String getTitle() {
		return this.title;
	}
	
	public static boolean titleIsValid(String title) {
		return (title != null) && (title.trim().length() > 1);
	}

	/**
	 * @uml.property  name="genre"
	 */
	private String genre;

	

	public Item(String title, String genre) {
		this.title = title;
		this.genre = genre;
		this.reviews = new LinkedList<Review>();
	}

	/** 
	 * @uml.property name="reviews"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="item:avis.Review"
	 */
	private LinkedList<Review> reviews;
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}

	public float getRating() {
		float sum = 0.0f; 
		float nbRating = reviews.size();
		
		for(Review review : this.reviews ){
			sum += review.getRating();
		}
		
		return sum / nbRating;
	}
}

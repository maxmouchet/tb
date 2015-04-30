package avis.models;

import java.util.LinkedList;
import exception.BadEntry;

public abstract class Item {

	/**
	 * @uml.property  name="title"
	 */
	private String title;

	/**
	 * @uml.property  name="genre"
	 */
	private String genre;
	
	/**
	 * @uml.property   name="reviews"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="item:avis.models.Review"
	 */
	private LinkedList<Review> reviews;
	
	public Item(String title, String genre) throws BadEntry {
		boolean isValid = titleIsValid(title) && genreIsValid(genre);

		if (!isValid) {
			throw new BadEntry("Title and/or genre does not meet the requirements.");
		}
		
		this.title = title;
		this.genre = genre;
		this.reviews = new LinkedList<Review>();
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}
	
	public String getTitle() {
		return this.title;
	}

	public float getRating() {
		float sum = 0.0f; 
		float nbRating = reviews.size();
		
		for(Review review : this.reviews ){
			sum += review.getRating();
		}
		
		return sum / nbRating;
	}
	
	public static boolean titleIsValid(String title) {
		return (title != null) && (title.trim().length() > 1);
	}

	public static boolean genreIsValid(String genre) {
		return genre != null;
	}

	@Override
	public String toString() {
		String output = "";
		
		output += "Titre: " + title + "\n";
		output += "Genre: " + genre + "\n";
		output += "Reviews: " + reviews.size() + "\n";
		
		return output;
	}
}

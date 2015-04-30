package avis;

import java.util.Collection;


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

	

	/** 
	 * @uml.property name="reviews"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="item:avis.Review"
	 */
	private Collection reviews;


}

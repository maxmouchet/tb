package avis;

import java.util.Collection;


public abstract class Item {

	/**
	 * @uml.property  name="titre"
	 */
	private String titre;

	

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

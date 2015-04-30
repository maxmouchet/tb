package avis;


public class Review {

	/**
	 * @uml.property  name="note"
	 */
	private float note;



	/**
	 * @uml.property  name="commentaire"
	 */
	private String commentaire;


	/**
	 * @uml.property  name="member"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="reviews:avis.Member"
	 */
	private Member member = new avis.Member();

	

	/** 
	 * @uml.property name="item"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="reviews:avis.Item"
	 */
	private Item item = new avis.Book();
}

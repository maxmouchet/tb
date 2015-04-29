package avis;

import java.util.Collection;


public class Member {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @uml.property  name="reviews"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="member:avis.Review"
	 */
	private Collection reviews;

	/**
	 * Getter of the property <tt>reviews</tt>
	 * @return  Returns the reviews.
	 * @uml.property  name="reviews"
	 */
	public Collection getReviews() {
		return reviews;
	}

	/**
	 * Setter of the property <tt>reviews</tt>
	 * @param reviews  The reviews to set.
	 * @uml.property  name="reviews"
	 */
	public void setReviews(Collection reviews) {
		this.reviews = reviews;
	}

}

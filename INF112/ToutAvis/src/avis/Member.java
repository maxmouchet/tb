package avis;

import java.util.Collection;

import exception.BadEntry;


public class Member {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private String profile;

	public Member(String pseudo, String password, String profile) throws BadEntry {
		boolean isValid = pseudoIsValid(pseudo) && passwordIsValid(password);

		if (!isValid) {
			throw new BadEntry("Pseudo and/or Password does not meet the requirements.");
		}
		
		this.pseudo = pseudo;
		this.password = password;
		this.profile = profile;
	}
	
	public static boolean pseudoIsValid(String pseudo) {
		return (pseudo != null) && (pseudo.trim().length() > 1);
	}
	
	public static boolean passwordIsValid(String password) {
		return (password != null) && (password.trim().length() >= 4);
	}
	
	public boolean checkCredentials(String pseudo, String password) {
		return (this.pseudo.equals(pseudo) && this.password.equals(password));
	}
	
	public Review findReview(String title) {
		return null;
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}

	/**
	 * @uml.property  name="reviews"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="member:avis.Review"
	 */
	private Collection reviews;

	private String pseudo;

	private String password;
}

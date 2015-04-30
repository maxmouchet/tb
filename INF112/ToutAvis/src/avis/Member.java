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

	public Member(String pseudo, String password) throws BadEntry {
		boolean isValid = pseudoIsValid(pseudo) && passwordIsValid(password);

		if (isValid) {
			this.pseudo = pseudo;
			this.password = password;
		} else {
			throw new BadEntry("Pseudo and/or Password does not meet the requirements.");
		}
	}
	
	public static boolean pseudoIsValid(String pseudo) {
		return (pseudo != null) && (pseudo.trim().length() > 1);
	}
	
	public static boolean passwordIsValid(String password) {
		return (password != null) && (password.trim().length() >= 4);
	}

	/**
	 * @uml.property  name="reviews"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="member:avis.Review"
	 */
	private Collection reviews;

	private String pseudo;

	private String password;
}

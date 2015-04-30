package avis;


public class Book extends Item {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Book(String titre, String genre, String auteur, int nbPages){
		super(titre, genre);
		this.auteur = auteur;
		this.nbPages = nbPages;
	}

	/**
	 * @uml.property  name="auteur"
	 */
	private String auteur;


	/**
	 * @uml.property  name="nbPages"
	 */
	private int nbPages;

}

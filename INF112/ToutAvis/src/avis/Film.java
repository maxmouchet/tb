package avis;


public class Film extends Item {
	
	public Film(String title, String genre) {
		super(title, genre);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @uml.property  name="realisateur"
	 */
	private String realisateur;

	/**
	 * @uml.property  name="scenariste"
	 */
	private String scenariste;


	/**
	 * @uml.property  name="duree"
	 */
	private int duree;

}

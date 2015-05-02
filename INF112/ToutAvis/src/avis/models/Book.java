package avis.models;

import exception.BadEntry;

/**
 * Représente un item de type book dans le SocialNetwork.
 */
public class Book extends Item {

    /**
     * L'auteur du livre.
     *
     * @uml.property name="author"
     */
    private String author;

    /**
     * Le nombre de pages du livre.
     *
     * @uml.property name="pageCount"
     */
    private int pageCount;

    /**
     * Initialise un livre.
     *
     * @param title     le titre du livre.
     * @param genre     le genre du livre.
     * @param author    l'auteur du livre.
     * @param pageCount le nombre de pages du livre.
     * @throws BadEntry <ul>
     *                  <li>si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.</li>
     *                  <li>si le genre n'est pas instancié.</li>
     *                  <li>si l'auteur n'est pas instancié.</li>
     *                  <li>si le nombre de pages n'est pas positif.</li>
     *                  </ul>
     */
    public Book(String title, String genre, String author, int pageCount) throws BadEntry {
        super(title, genre);

        if (!(authorIsValid(author) && pageCountIsValid(pageCount))) {
            throw new BadEntry("Author and/or page count does not meet the requirements.");
        }

        this.author = author;
        this.pageCount = pageCount;
    }

    /**
     * Vérifie que l'auteur respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param auteur l'auteur du livre.
     * @return true si l'auteur est valide. false sinon.
     */
    private boolean authorIsValid(String auteur) {
        return auteur != null;
    }

    /**
     * Vérifie que le nombre de pages respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param pageCount le nombre de pages du livre.
     * @return true si le nombre de pages est valide. false sinon.
     */
    private boolean pageCountIsValid(int pageCount) {
        return pageCount >= 0;
    }

    @Override
    public String toString() {
        String output = super.toString();

        output += "Author: " + author + "\n";
        output += "Page count: " + pageCount + "\n";

        return output;
    }
}

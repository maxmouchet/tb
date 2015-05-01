package avis.models;

import exception.BadEntry;

public class Book extends Item {

    /**
     * @uml.property name="author"
     */
    private String author;

    /**
     * @uml.property name="pageCount"
     */
    private int pageCount;

    public Book(String title, String genre, String author, int pageCount) throws BadEntry {
        super(title, genre);

        if (!(authorIsValid(author) && pageCountIsValid(pageCount))) {
            throw new BadEntry("Author and/or page count does not meet the requirements.");
        }

        this.author = author;
        this.pageCount = pageCount;
    }

    private boolean authorIsValid(String auteur) {
        return auteur != null;
    }

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

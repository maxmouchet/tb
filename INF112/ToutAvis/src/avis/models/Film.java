package avis.models;

import exception.BadEntry;

/**
 * Représente un item de type film dans le SocialNetwork.
 */
public class Film extends Item {

    /**
     * Le réalisateur du film.
     *
     * @uml.property name="director"
     */
    private String director;

    /**
     * Le scénariste du film.
     *
     * @uml.property name="writer"
     */
    private String writer;

    /**
     * La durée du film, en minutes.
     *
     * @uml.property name="length"
     */
    private int length;

    /**
     * Initialise un film.
     *
     * @param title    le titre du film.
     * @param genre    le genre du film.
     * @param director le réalisateur du film.
     * @param writer   le scénariste du film.
     * @param length   la durée du film, en minutes.
     * @throws BadEntry <ul>
     *                  <li>si le titre n'est pas instancié ou a moins de 1 caractère autre que des espaces.</li>
     *                  <li>si le genre n'est pas instancié.</li>
     *                  <li>si le réalisateur n'est pas instancié.</li>
     *                  <li>si le scénariste n'est pas instancié.</li>
     *                  <li>si la durée n'est pas positive.</li>
     *                  </ul>
     */
    public Film(String title, String genre, String director, String writer, int length) throws BadEntry {
        super(title, genre);

        if (!(directorIsValid(director) && writerIsValid(writer) && lengthIsValid(length))) {
            throw new BadEntry("Author and/or page count does not meet the requirements.");
        }

        this.director = director;
        this.writer = writer;
        this.length = length;
    }

    /**
     * Vérifie que le réalisateur respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param director le réalisateur du film.
     * @return true si le réalisateur est valide. false sinon.
     */
    private boolean directorIsValid(String director) {
        return director != null;
    }

    /**
     * Vérifie que le scénariste respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param writer le scénariste du film.
     * @return true si le scénariste est valide. false sinon.
     */
    private boolean writerIsValid(String writer) {
        return writer != null;
    }

    /**
     * Vérifie que la durée respecte les conditions d'existence définies dans le cahier des charges.
     *
     * @param length la durée du film, en minutes.
     * @return true si la durée est valide. false sinon.
     */
    private boolean lengthIsValid(int length) {
        return length >= 0;
    }

    @Override
    public String toString() {
        String output = super.toString();

        output += "Director: " + director + "\n";
        output += "Writer: " + writer + "\n";
        output += "Length: " + length + "min \n";

        return output;
    }
}

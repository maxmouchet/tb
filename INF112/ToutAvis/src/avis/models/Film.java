package avis.models;

import exception.BadEntry;

public class Film extends Item {
	
	/**
	 * @uml.property  name="director"
	 */
	private String director;

	/**
	 * @uml.property  name="writer"
	 */
	private String writer;

	/**
	 * @uml.property  name="length"
	 */
	private int length;
	
	public Film(String title, String genre, String director, String writer, int length) throws BadEntry {
		super(title, genre);
		
		if (!(directorIsValid(director) && writerIsValid(writer) && lengthIsValid(length))) {
			throw new BadEntry("Author and/or page count does not meet the requirements.");
		}
		
		this.director = director;
		this.writer = writer;
		this.length = length;
	}
	
	private boolean directorIsValid(String director) {
		return director != null;
	}

	private boolean writerIsValid(String writer) {
		return writer != null;
	}
	
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

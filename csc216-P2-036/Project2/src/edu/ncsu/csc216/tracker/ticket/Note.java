package edu.ncsu.csc216.tracker.ticket;

/**
 * Constructs a Note object that has an author and text
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class Note {
	/** author of the note */
	private String noteAuthor;
	/** text of the note */
	private String noteText;

	/**
	 * Constructs a Note object
	 * 
	 * @param author
	 *            Author of the note
	 * @param text
	 *            Text of the note
	 */
	public Note(String author, String text) {
		setNoteAuthor(author);
		setNoteText(text);
	}

	/**
	 * Gets the text of a note
	 * @return notetext on a certain note
	 */
	public String getNoteText() {
		return noteText;
	}

	/**
	 * Sets the noteText. Throws exceptions if it is invalid
	 * 
	 * @param text
	 *            Text to be set as the noteText
	 * @throws IllegalArgumentException
	 *             if the text is null or empty
	 */
	private void setNoteText(String text) {
		if (text == null || text.equals("")) {
			throw new IllegalArgumentException();
		}
		noteText = text;
	}

	/**
	 * Gets the author of a note
	 * 
	 * @return noteAuthor of a note
	 */
	public String getNoteAuthor() {
		return noteAuthor;
	}

	/**
	 * Sets the noteAuthor. Throws exceptions if it is invalid
	 * 
	 * @param author
	 *            Author to be set as the noteAuthor
	 * @throws IllegalArgumentException
	 *             if the author is null or empty
	 */
	private void setNoteAuthor(String author) {
		if (author == null || author.equals("")) {
			throw new IllegalArgumentException();
		}
		noteAuthor = author;
	}
	
	/**
	 * Returns an array with the author and text information
	 * @return an array with the author at index 0 and note text at index 1
	 */
	public String[] getNoteArray(){
		String[] temp = new String[2];
		temp[0] = noteAuthor;
		temp[1] = noteText;
		return temp;
	}
}

package edu.ncsu.csc216.tracker.command;

/**
 * Commands that can be performed with a Ticket
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class Command {
	/** String to represent a duplicate flag */
	public static final String F_DUPLICATE = "Duplicate";
	/** String to represent an inappropriate flag */
	public static final String F_INAPPROPRIATE = "Inappropriate";
	/** String to represent a resolved flag */
	public static final String F_RESOLVED = "Resolved";
	/** Enum for command values */
	public static enum CommandValue { POSSESSION, ACCEPTED, CLOSED, PROGRESS, FEEDBACK }
	/** Enum for all flag types */
	public static enum Flag { DUPLICATE, INAPPROPRIATE, RESOLVED } 

	/** represents the owner of the Ticket */
	private String owner;
	/** represents the author of the note */
	private String noteAuthor;
	/** the note attached to a ticket */
	private String note;
	/**Flag that is used with the current ticket*/
	private Flag flag;
	/**Command value that is used with current ticket*/
	private CommandValue c;
	
	
	/**
	 * Constructs a new Command object that encapsulates user actions
	 * @param cv CommandValue to act with
	 * @param owner Owner of the ticket 
	 * @param f Flag that represents the ticket's current state
	 * @param note Text of a note on the ticket
	 * @param noteAuthor Author of the note
	 */
	public Command(CommandValue cv, String owner, Flag f, String noteAuthor, String note) {
		if (cv == null || noteAuthor == null || noteAuthor.equals("") || note == null || note.equals("")
			|| (cv == CommandValue.POSSESSION && owner == null) ||  (cv == CommandValue.POSSESSION && owner.equals(""))
			|| (cv == CommandValue.CLOSED && f == null)){
			throw new IllegalArgumentException();
		}
		this.c = cv;
		this.owner = owner;
		this.flag = f;
		this.note = note;
		this.noteAuthor = noteAuthor;
	}
	
	/**
	 * Gets the command value of this command
	 * @return command value requested
	 */
	public CommandValue getCommand() {
		return c;
	}

	/**
	 * Gets the owner of a ticket
	 * @return owner of the ticket
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Gets the flag on a ticket
	 * @return flag on a ticket
	 */
	public Flag getFlag(){
		return flag;
	}

	/**
	 * Gets the note text of the ticket
	 * @return note text to be added to the ticket
	 */
	public String getNoteText() {
		return note;
	}

	/**
	 * Gets the note author of the ticket
	 * @return note author of the ticket
	 */
	public String getNoteAuthor() {
		return noteAuthor;
	}

}

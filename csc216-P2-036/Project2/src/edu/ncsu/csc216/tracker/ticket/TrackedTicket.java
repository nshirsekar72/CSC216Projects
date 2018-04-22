package edu.ncsu.csc216.tracker.ticket;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.ticket.xml.NoteItem;
import edu.ncsu.csc216.ticket.xml.NoteList;
import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;

/**
 * Creates a Ticket that has the capability of being tracked
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class TrackedTicket {
	/** ID of the ticket in the list of tickets */
	private int ticketId;
	/** title of the ticket */
	private String title;
	/** who submitted the ticket */
	private String submitter;
	/** owner of the ticket */
	private String owner;
	/** Flag for current ticket */
	private Flag flag;
	/** name of a new state */
	public static final String NEW_NAME = "New";
	/** name of assigned state */
	public static final String ASSIGNED_NAME = "Assigned";
	/** name of a working state */
	public static final String WORKING_NAME = "Working";
	/** name of a new state */
	public static final String FEEDBACK_NAME = "Feedback";
	/** name of a new state */
	public static final String CLOSED_NAME = "Closed";
	/** represents a counter for the ID of the next ticket */
	private static int counter;
	/** list of notes */
	private ArrayList<Note> notes;
	/** reference of new state */
	private final TicketState newState = new NewState();
	/** reference of assigned state */
	private final TicketState assignedState = new AssignedState();
	/** reference of working state */
	private final TicketState workingState = new WorkingState();
	/** reference of feedback state */
	private final TicketState feedbackState = new FeedbackState();
	/** reference of closed state */
	private final TicketState closedState = new ClosedState();
	/** current state of the ticket */
	private TicketState state;

	/**
	 * Constructs a new ticket given strings
	 * 
	 * @param t
	 *            title of the ticket
	 * @param submitter
	 *            submitter of the ticket
	 * @param note
	 *            note that is being added
	 */
	public TrackedTicket(String t, String submitter, String note) {
		if (t == null || t.equals("")) {
			throw new IllegalArgumentException();
		}

		// counter = 0;
		ticketId = counter;
		incrementCounter();
		this.title = t;
		this.submitter = submitter;
		this.state = newState;
		flag = null;
		notes = new ArrayList<Note>();
		notes.add(new Note(submitter, note));
	}

	/**
	 * Constructs a new TrackedTicket when given a ticket object
	 * 
	 * @param t
	 *            XML ticket to be worked with
	 */
	public TrackedTicket(Ticket t) {
		this.title = t.getTitle();
		this.submitter = t.getSubmitter();
		this.owner = t.getOwner();
		setState(t.getState());
		setFlag(t.getFlag());
		notes = new ArrayList<Note>();

		List<NoteItem> list = t.getNoteList().getNotes();
		for (int i = 0; i < list.size(); i++) {
			NoteItem item = list.get(i);
			notes.add(new Note(item.getNoteAuthor(), item.getNoteText()));
		}
		this.ticketId = t.getId();
	}

	/**
	 * increments number for the next tracked tickets id
	 */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * gets the ticket id of the current ticket
	 * 
	 * @return ticketId for current ticket
	 */
	public int getTicketId() {
		return this.ticketId;
	}

	/**
	 * gets a string version of the state the ticket is currently in
	 * 
	 * @return the name of the state
	 */
	public String getStateName() {
		if (state.equals(newState)) {
			return NEW_NAME;
		} else if (state.equals(assignedState)) {
			return ASSIGNED_NAME;
		} else if (state.equals(workingState)) {
			return WORKING_NAME;
		} else if (state.equals(feedbackState)) {
			return FEEDBACK_NAME;
		} else {
			return CLOSED_NAME;
		}
	}

	/**
	 * sets state of current ticket
	 * 
	 * @param state
	 *            state that ticket will be set to
	 */
	private void setState(String state) {
		if (state.equals(newState.getStateName())) {
			this.state = newState;
		} else if (state.equals(assignedState.getStateName())) {
			this.state = assignedState;
		} else if (state.equals(workingState.getStateName())) {
			this.state = workingState;
		} else if (state.equals(feedbackState.getStateName())) {
			this.state = feedbackState;
		} else if (state.equals(closedState.getStateName())) {
			this.state = closedState;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the flag for the current ticket
	 * 
	 * @return Flag of the current ticket
	 */
	public Flag getFlag() {
		return this.flag;
	}

	/**
	 * Sets the flag of this ticket to the given flag
	 * 
	 * @param newFlag
	 *            Flag to set this one to
	 */
	private void setFlag(String newFlag) {
		if (newFlag == null) {
			return;
		} else if (newFlag.equals(Command.F_DUPLICATE)) {
			flag = Flag.DUPLICATE;
		} else if (newFlag.equals(Command.F_INAPPROPRIATE)) {
			flag = Flag.INAPPROPRIATE;
		} else if (newFlag.equals(Command.F_RESOLVED)) {
			flag = Flag.RESOLVED;
		}
	}

	/**
	 * gets owner of ticket
	 * 
	 * @return Owner owner of ticket
	 */
	public String getOwner() {
		return this.owner;
	}

	/**
	 * returns title of ticket
	 * 
	 * @return title title of ticket
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * gets the submitter of the
	 * 
	 * @return submitter the submitter of the ticket
	 */
	public String getSubmitter() {
		return this.submitter;
	}

	/**
	 * gets the notes for a ticket
	 * 
	 * @return notes the array of notes for the ticket
	 */
	public ArrayList<Note> getNotes() {
		return this.notes;
	}

	/**
	 * updates commands propogated from the UI
	 * 
	 * @param c
	 *            command that is issued
	 */
	public void update(Command c) {
		flag = c.getFlag();
		state.updateState(c);
		notes.add(new Note(c.getNoteAuthor(), c.getNoteText()));
	}

	/**
	 * Gets the ticket for the XML
	 * 
	 * @return this TrackedTicket in object form
	 */
	public Ticket getXMLTicket() {
		Ticket t = new Ticket();
		t.setId(this.ticketId);
		t.setFlag(this.getFlagString());
		NoteList nl = new NoteList();
		for (int i = 0; i < notes.size(); i++) {
			NoteItem n = new NoteItem();
			n.setNoteAuthor(notes.get(i).getNoteAuthor());
			n.setNoteText(notes.get(i).getNoteText());
			nl.getNotes().add(n);
		}
		t.setNoteList(nl);
		t.setOwner(this.owner);
		t.setState(this.getStateName());
		t.setSubmitter(this.submitter);
		t.setTitle(this.title);
		return t;
	}

	/**
	 * sets counter for creating ticket Ids
	 * 
	 * @param i
	 *            integer to set counter to
	 */
	public static void setCounter(int i) {
		if (i <= 0) {
			throw new IllegalArgumentException();
		}
		counter = i;

	}

	/**
	 * creates string array of notes arraylist
	 * 
	 * @return notesArray string array version of notes
	 */
	public String[][] getNotesArray() {
		String[][] notesArray = new String[notes.size()][2];
		for (int i = 0; i < notes.size(); i++) {
			notesArray[i] = notes.get(i).getNoteArray();
		}
		return notesArray;
	}

	/**
	 * Gets the flag of the ticket as a string
	 * 
	 * @return string that represents the flag on a ticket
	 */
	public String getFlagString() {
		if (flag == Flag.DUPLICATE) {
			return Command.F_DUPLICATE;
		} else if (flag == Flag.INAPPROPRIATE) {
			return Command.F_INAPPROPRIATE;
		} else if (flag == Flag.RESOLVED) {
			return Command.F_RESOLVED;
		} else {
			return null;
		}
	}

	/**
	 * Interface for states in the Ticket State Pattern. All concrete ticket
	 * states must implement the TicketState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface TicketState {

		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the
		 * {@link CommandValue} is not a valid action for the given state.
		 * 
		 * @param c
		 *            {@link Command} describing the action that will update the
		 *            {@link TrackedTicket}'s state.
		 * @throws UnsupportedOperationException
		 *             if the {@link CommandValue} is not a valid action for the
		 *             given state.
		 */
		void updateState(Command c);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * Represents the assigned state of a ticket in our FSM
	 * 
	 * @author Michelle Lemons
	 * @author Nikhil Shirsekar
	 */
	private class AssignedState implements TicketState {

		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the
		 * {@link CommandValue} is not a valid action for the given state.
		 * 
		 * @param c
		 *            {@link Command} describing the action that will update the
		 *            {@link TrackedTicket}'s state.
		 * @throws UnsupportedOperationException
		 *             if the {@link CommandValue} is not a valid action for the
		 *             given state.
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.ACCEPTED) {
				state = workingState;
			} else if (c.getCommand() == CommandValue.CLOSED) {
				state = closedState;
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			return ASSIGNED_NAME;
		}

	}

	/**
	 * Represents the working state of a ticket in our FSM
	 * 
	 * @author Michelle Lemons
	 * @author Nikhil Shirsekar
	 */
	private class WorkingState implements TicketState {

		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the
		 * {@link CommandValue} is not a valid action for the given state.
		 * 
		 * @param c
		 *            {@link Command} describing the action that will update the
		 *            {@link TrackedTicket}'s state.
		 * @throws UnsupportedOperationException
		 *             if the {@link CommandValue} is not a valid action for the
		 *             given state.
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.PROGRESS) {
				state = workingState;
			} else if (c.getCommand() == CommandValue.FEEDBACK) {
				state = feedbackState;
			} else if (c.getCommand() == CommandValue.CLOSED) {
				state = closedState;
			} else if (c.getCommand() == CommandValue.POSSESSION) {
				state = assignedState;
				owner = c.getOwner();
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			return WORKING_NAME;
		}

	}

	/**
	 * Represents the new state of a ticket in our FSM
	 * 
	 * @author Michelle Lemons
	 * @author Nikhil Shirsekar
	 */
	private class NewState implements TicketState {

		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the
		 * {@link CommandValue} is not a valid action for the given state.
		 * 
		 * @param c
		 *            {@link Command} describing the action that will update the
		 *            {@link TrackedTicket}'s state.
		 * @throws UnsupportedOperationException
		 *             if the {@link CommandValue} is not a valid action for the
		 *             given state.
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.POSSESSION) {
				owner = c.getOwner();
				state = assignedState;
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			return NEW_NAME;
		}

	}

	/**
	 * Represents the closed state of a ticket in our FSM
	 * 
	 * @author Michelle Lemons
	 * @author Nikhil Shirsekar
	 */
	private class ClosedState implements TicketState {

		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the
		 * {@link CommandValue} is not a valid action for the given state.
		 * 
		 * @param c
		 *            {@link Command} describing the action that will update the
		 *            {@link TrackedTicket}'s state.
		 * @throws UnsupportedOperationException
		 *             if the {@link CommandValue} is not a valid action for the
		 *             given state.
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.PROGRESS) {
				state = workingState;
			} else if (c.getCommand() == CommandValue.POSSESSION) {
				owner = c.getOwner();
				state = assignedState;

			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			return CLOSED_NAME;
		}

	}

	/**
	 * Represents the feedback state of a ticket in our FSM
	 * 
	 * @author Michelle Lemons
	 * @author Nikhil Shirsekar
	 */
	private class FeedbackState implements TicketState {

		/**
		 * Update the {@link TrackedTicket} based on the given {@link Command}.
		 * An {@link UnsupportedOperationException} is throw if the
		 * {@link CommandValue} is not a valid action for the given state.
		 * 
		 * @param c
		 *            {@link Command} describing the action that will update the
		 *            {@link TrackedTicket}'s state.
		 * @throws UnsupportedOperationException
		 *             if the {@link CommandValue} is not a valid action for the
		 *             given state.
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.FEEDBACK) {
				state = workingState;
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			return FEEDBACK_NAME;
		}

	}

}

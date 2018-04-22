/**
 * 
 */
package edu.ncsu.csc216.tracker.ticket;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.ticket.xml.NoteItem;
import edu.ncsu.csc216.ticket.xml.NoteList;
import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;

/**
 * Tests the TrackedTicket object
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class TrackedTicketTest {
	/**
	 * Sets up the tickets so that every time starts with counter fresh
	 */
	@Before
	public void setUp() {
		TrackedTicket.setCounter(1);

	}

	/**
	 * tests the normal tracked ticket constructor
	 */
	@Test
	public void testTrackedTicketStringStringString() {
		TrackedTicket t1 = new TrackedTicket("helparooni", "mkLemons", "THIS MIGHT FIX THE THING");
		assertEquals("mkLemons", t1.getSubmitter());
		assertNull(t1.getFlag());
		assertNull(t1.getFlagString());
		assertEquals("helparooni", t1.getTitle());
		assertEquals(1, t1.getNotes().size());
		assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());
		assertEquals(1, t1.getTicketId());
	}

	/**
	 * tests the constructor that takes a ticket parameter
	 */
	@Test
	public void testTrackedTicketTicket() {
		Ticket t4 = new Ticket();
		t4.setNoteList(new NoteList());
		t4.setFlag(null);
		t4.setId(0);
		t4.setTitle("Help with thing");
		t4.setOwner(null);
		t4.setState(TrackedTicket.NEW_NAME);
		t4.setSubmitter("mklemons");
		t4.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(t4);
		assertNull(t1.getOwner());
		assertEquals("mklemons", t1.getSubmitter());
		assertNull(t1.getFlag());
		assertNull(t1.getFlagString());
		assertEquals("Help with thing", t1.getTitle());
		assertEquals(0, t1.getNotes().size());
		assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());
		assertEquals(0, t1.getTicketId());
	}

	/**
	 * test the increment counter method
	 */
	@Test
	public void testIncrementCounter() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner(null);
		ti.setState(TrackedTicket.NEW_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		assertEquals(0, t1.getTicketId());

		TrackedTicket t2 = new TrackedTicket("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		assertEquals(1, t2.getTicketId());

		TrackedTicket t3 = new TrackedTicket("helparooni", "nnshirse", "THIS MIGHT ACTUALLY FIX THE THING");
		assertEquals(2, t3.getTicketId());
	}

	/**
	 * tests getting the states name
	 */
	@Test
	public void testGetStateName() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner(null);
		ti.setState(TrackedTicket.NEW_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);

		assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());

	}

	/**
	 * tests updating to a new state
	 */
	@Test
	public void testUpdateNewState() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner(null);
		ti.setState(TrackedTicket.NEW_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		// try moving ticket to new state
		try {
			t1.update(new Command(CommandValue.ACCEPTED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());
		}
		// try moving ticket to working state
		try {
			t1.update(new Command(CommandValue.PROGRESS, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());
		}
		// try moving ticket to feedback state
		try {
			t1.update(new Command(CommandValue.FEEDBACK, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());
		}
		// try moving ticket to closed state
		try {
			t1.update(new Command(CommandValue.CLOSED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TrackedTicket.NEW_NAME, t1.getStateName());
		}

		// update ticket to assigned state
		t1.update(new Command(CommandValue.POSSESSION, "nnshirse", t1.getFlag(), "helperu", "Help"));
		assertEquals("Help", t1.getNotesArray()[0][1]);
		assertEquals("helperu", t1.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.ASSIGNED_NAME, t1.getStateName());

	}

	/**
	 * tests updating to the assigned state
	 */
	@Test
	public void testUpdateAssignedState() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner("nnshirse");
		ti.setState(TrackedTicket.ASSIGNED_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		// try moving ticket to new state
		try {
			t1.update(new Command(CommandValue.PROGRESS, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.ASSIGNED_NAME, t1.getStateName());
		}
		// try moving ticket to new state
		try {
			t1.update(new Command(CommandValue.POSSESSION, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.ASSIGNED_NAME, t1.getStateName());
		}

		// try moving ticket to feedback state
		try {
			t1.update(new Command(CommandValue.FEEDBACK, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.ASSIGNED_NAME, t1.getStateName());
		}
		// update ticket to assigned state

		t1.update(new Command(CommandValue.ACCEPTED, t1.getOwner(), t1.getFlag(), "nnshirse", "I'm on it"));
		assertEquals("I'm on it", t1.getNotesArray()[0][1]);
		assertEquals("nnshirse", t1.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.WORKING_NAME, t1.getStateName());

		Ticket t2 = new Ticket();
		t2.setNoteList(new NoteList());
		t2.setFlag(null);
		t2.setId(0);
		t2.setTitle("Help with thing");
		t2.setOwner("nnshirse");
		t2.setState(TrackedTicket.ASSIGNED_NAME);
		t2.setSubmitter("mklemons");
		t2.setTitle("Help with thing");

		TrackedTicket t3 = new TrackedTicket(t2);
		t3.update(new Command(CommandValue.CLOSED, t3.getOwner(), Flag.INAPPROPRIATE, "helperu", "Help"));
		assertEquals("Help", t3.getNotesArray()[0][1]);
		assertEquals("helperu", t3.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.CLOSED_NAME, t3.getStateName());

	}

	/**
	 * tests updating to a working state
	 */
	@Test
	public void testUpdateWorkingState() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner("nnshirse");
		ti.setState(TrackedTicket.WORKING_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);

		// try moving ticket to new state
		try {
			t1.update(new Command(CommandValue.ACCEPTED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.WORKING_NAME, t1.getStateName());
		}

		// update the ticket to more progess
		t1.update(new Command(CommandValue.PROGRESS, t1.getOwner(), t1.getFlag(), "nnshirse", "I'm on it"));
		assertEquals("I'm on it", t1.getNotesArray()[0][1]);
		assertEquals("nnshirse", t1.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.WORKING_NAME, t1.getStateName());

		// update ticket to assigned state
		t1.update(new Command(CommandValue.POSSESSION, "mklemons", t1.getFlag(), "helperu", "Help"));
		assertEquals("I'm on it", t1.getNotesArray()[0][1]);
		assertEquals("nnshirse", t1.getNotesArray()[0][0]);
		assertEquals("Help", t1.getNotesArray()[1][1]);
		assertEquals("helperu", t1.getNotesArray()[1][0]);
		assertEquals("mklemons", t1.getOwner());
		assertEquals(TrackedTicket.ASSIGNED_NAME, t1.getStateName());

		// update the ticket to more progess
		t1.update(new Command(CommandValue.ACCEPTED, t1.getOwner(), t1.getFlag(), "nnshirse", "I'm on it"));
		assertEquals("I'm on it", t1.getNotesArray()[0][1]);
		assertEquals("nnshirse", t1.getNotesArray()[0][0]);
		assertEquals("Help", t1.getNotesArray()[1][1]);
		assertEquals("helperu", t1.getNotesArray()[1][0]);
		assertEquals("I'm on it", t1.getNotesArray()[2][1]);
		assertEquals("nnshirse", t1.getNotesArray()[2][0]);
		assertEquals(TrackedTicket.WORKING_NAME, t1.getStateName());

		// update the ticket to feedback state
		t1.update(new Command(CommandValue.FEEDBACK, t1.getOwner(), t1.getFlag(), "helperuy", "Helpy"));
		assertEquals("Helpy", t1.getNotesArray()[3][1]);
		assertEquals("helperuy", t1.getNotesArray()[3][0]);
		assertEquals("mklemons", t1.getOwner());
		assertEquals(TrackedTicket.FEEDBACK_NAME, t1.getStateName());

		// update the ticket to more progess
		t1.update(new Command(CommandValue.FEEDBACK, t1.getOwner(), t1.getFlag(), "nnshirse", "I'm on it"));
		assertEquals("I'm on it", t1.getNotesArray()[4][1]);
		assertEquals("nnshirse", t1.getNotesArray()[4][0]);
		assertEquals(TrackedTicket.WORKING_NAME, t1.getStateName());

		Ticket t3 = new Ticket();
		t3.setNoteList(new NoteList());
		t3.setFlag(null);
		t3.setId(0);
		t3.setTitle("Help with thing");
		t3.setOwner("nnshirse");
		t3.setState(TrackedTicket.WORKING_NAME);
		t3.setSubmitter("mklemons");
		t3.setTitle("Help with thing");
		TrackedTicket t2 = new TrackedTicket(t3);
		// update the ticket to closed
		t2.update(new Command(CommandValue.CLOSED, t1.getOwner(), Flag.RESOLVED, "nnshirse", "Finished"));
		assertEquals("Finished", t2.getNotesArray()[0][1]);
		assertEquals("nnshirse", t2.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.CLOSED_NAME, t2.getStateName());

	}

	/**
	 * tests updating to the feedback state
	 */
	@Test
	public void testUpdateFeedbackState() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner("nnshirse");
		ti.setState(TrackedTicket.FEEDBACK_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		// try moving ticket to new state
		try {
			t1.update(new Command(CommandValue.ACCEPTED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.FEEDBACK_NAME, t1.getStateName());
		}
		// try moving ticket to working state
		try {
			t1.update(new Command(CommandValue.POSSESSION, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.FEEDBACK_NAME, t1.getStateName());
		}
		// try moving ticket to feedback state
		try {
			t1.update(new Command(CommandValue.PROGRESS, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.FEEDBACK_NAME, t1.getStateName());
		}
		// try moving ticket to closed state
		try {
			t1.update(new Command(CommandValue.CLOSED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(TrackedTicket.FEEDBACK_NAME, t1.getStateName());
		}

		// update ticket to assigned state
		t1.update(new Command(CommandValue.FEEDBACK, "nnshirse", t1.getFlag(), "helperu", "Help"));
		assertEquals("Help", t1.getNotesArray()[0][1]);
		assertEquals("helperu", t1.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.WORKING_NAME, t1.getStateName());

	}

	/**
	 * tests updating to the closed state
	 */
	@Test
	public void testUpdateClosedState() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(Command.F_RESOLVED);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner("nnshirse");
		ti.setState(TrackedTicket.CLOSED_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		// try moving ticket to new state
		try {
			t1.update(new Command(CommandValue.ACCEPTED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.CLOSED_NAME, t1.getStateName());
		}
		// try moving ticket to feedback state
		try {
			t1.update(new Command(CommandValue.FEEDBACK, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.CLOSED_NAME, t1.getStateName());
		}
		// try moving ticket to closed state
		try {
			t1.update(new Command(CommandValue.CLOSED, t1.getOwner(), t1.getFlag(), "Help", "helperu"));
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(TrackedTicket.CLOSED_NAME, t1.getStateName());
		}

		t1.update(new Command(CommandValue.POSSESSION, t1.getOwner(), t1.getFlag(), "helperu", "Help"));
		assertEquals("Help", t1.getNotesArray()[0][1]);
		assertEquals("helperu", t1.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.ASSIGNED_NAME, t1.getStateName());

		Ticket t2 = new Ticket();
		t2.setNoteList(new NoteList());
		t2.setFlag(Command.F_RESOLVED);
		t2.setId(0);
		t2.setTitle("Help with thing");
		t2.setOwner("nnshirse");
		t2.setState(TrackedTicket.CLOSED_NAME);
		t2.setSubmitter("mklemons");
		t2.setTitle("Help with thing");

		TrackedTicket t3 = new TrackedTicket(t2);
		// update ticket to assigned state

		t3.update(new Command(CommandValue.PROGRESS, "nnshirse", t3.getFlag(), "helperu", "Help"));
		assertEquals("Help", t3.getNotesArray()[0][1]);
		assertEquals("helperu", t3.getNotesArray()[0][0]);
		assertEquals(TrackedTicket.WORKING_NAME, t3.getStateName());

	}

	/**
	 * tests getting an XML ticket
	 */
	@Test
	public void testGetXMLTicket() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(1);
		ti.setTitle("Help with thing");
		NoteItem ni = new NoteItem();
		ni.setNoteAuthor("mklemons");
		ni.setNoteText("hi");
		ti.getNoteList().getNotes().add(ni);
		ti.setOwner(null);
		ti.setState(TrackedTicket.NEW_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		Ticket tside = t1.getXMLTicket();
		assertNull(tside.getFlag());
		assertEquals(ti.getId(), tside.getId());
		assertEquals(ti.getNoteList().getNotes().get(0).getNoteAuthor(),
				tside.getNoteList().getNotes().get(0).getNoteAuthor());
		assertEquals(ti.getOwner(), tside.getOwner());
		assertEquals(ti.getState(), tside.getState());
		assertEquals(ti.getSubmitter(), tside.getSubmitter());
		assertEquals(ti.getTitle(), tside.getTitle());

	}
	
	/**
	 * tests setting the state
	 */
	@Test
	public void testSetState(){
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(1);
		ti.setTitle("Help with thing");
		NoteItem ni = new NoteItem();
		ni.setNoteAuthor("mklemons");
		ni.setNoteText("hi");
		ti.getNoteList().getNotes().add(ni);
		ti.setOwner(null);
		ti.setState("lol");
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = null;
		try {
			t1 = new TrackedTicket(ti);
			fail();
		} catch(IllegalArgumentException e){
			assertNull(t1);
		}
		
	}
	
	/**
	 * Tests setFlag
	 */
	@Test
	public void testSetFlag(){
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(Command.F_DUPLICATE);
		ti.setId(1);
		ti.setTitle("Help with thing");
		NoteItem ni = new NoteItem();
		ni.setNoteAuthor("mklemons");
		ni.setNoteText("hi");
		ti.getNoteList().getNotes().add(ni);
		ti.setOwner(null);
		ti.setState(TrackedTicket.NEW_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");

		TrackedTicket t1 = new TrackedTicket(ti);
		assertEquals(Command.F_DUPLICATE, t1.getFlagString());
		assertEquals(Flag.DUPLICATE, t1.getFlag());
		
		Ticket t2 = new Ticket();
		t2.setNoteList(new NoteList());
		t2.setFlag(Command.F_INAPPROPRIATE);
		t2.setId(1);
		t2.setTitle("Help with thing");
		NoteItem ni2 = new NoteItem();
		ni2.setNoteAuthor("mklemons");
		ni2.setNoteText("hi");
		t2.getNoteList().getNotes().add(ni2);
		t2.setOwner(null);
		t2.setState(TrackedTicket.NEW_NAME);
		t2.setSubmitter("mklemons");
		t2.setTitle("Help with thing");

		TrackedTicket t3 = new TrackedTicket(t2);
		assertEquals(Command.F_INAPPROPRIATE, t3.getFlagString());
		assertEquals(Flag.INAPPROPRIATE, t3.getFlag());
		
		Ticket t4 = new Ticket();
		t4.setNoteList(new NoteList());
		t4.setFlag(Command.F_RESOLVED);
		t4.setId(1);
		t4.setTitle("Help with thing");
		NoteItem ni3 = new NoteItem();
		ni3.setNoteAuthor("mklemons");
		ni3.setNoteText("hi");
		t4.getNoteList().getNotes().add(ni3);
		t4.setOwner(null);
		t4.setState(TrackedTicket.NEW_NAME);
		t4.setSubmitter("mklemons");
		t4.setTitle("Help with thing");

		TrackedTicket t5 = new TrackedTicket(t4);
		assertEquals(Command.F_RESOLVED, t5.getFlagString());
		assertEquals(Flag.RESOLVED, t5.getFlag());
		
	}
	
	/**
	 * Test set counter
	 */
	@Test
	public void testSetCounter(){
		try {
			TrackedTicket.setCounter(0);
		} catch(IllegalArgumentException e){
			assertEquals(null, e.getMessage());
		}
	}

}

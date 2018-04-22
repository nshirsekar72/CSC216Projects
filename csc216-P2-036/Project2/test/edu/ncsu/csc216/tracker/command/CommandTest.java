/**
 * 
 */
package edu.ncsu.csc216.tracker.command;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;

/**
 * Test Command class
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class CommandTest {

	/**
	 * tests the command constructor
	 */
	@Test
	public void testCommand() {

		// Test that if command parameter is null, that an illegal argument
		// exception is thrown
		Command a = null;
		try {
			a = new Command(null, "Michelle Lemons", Flag.DUPLICATE, "this ticket no good, has no command.",
					"Nikhil Shirsekar");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(a);
		}

		// Test if note author is null, that an illegal argument exception is
		// thrown
		Command b = null;
		try {
			b = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE,
					"this ticket no good, has no note author.", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(b);
		}

		// Test if note author is a blank string, that an illegal argument
		// exception is thrown
		Command c = null;
		try {
			c = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE,
					"this ticket no good, has no note author.", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		// Test if note is null, that an illegal argument exception is
		// thrown
		Command d = null;
		try {
			d = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, null, "Nikhil Shirsekar");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(d);
		}

		// Test if note is a blank string, that an illegal argument exception is
		// thrown
		Command f = null;
		try {
			f = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, "", "Nikhil Shirsekar");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(f);
		}

		// Test that if Command value is set to possession and owner is null,
		// that an IAE is thrown
		Command g = null;
		try {
			g = new Command(CommandValue.POSSESSION, null, Flag.DUPLICATE, "this ticket no good, has no owner.",
					"Nikhil Shirsekar");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(g);
		}

		// Test that if Command value is set to possession and owner is a blank
		// string,
		// that an IAE is thrown
		Command h = null;
		try {
			h = new Command(CommandValue.POSSESSION, "", Flag.DUPLICATE, "this ticket no good, has no owner.",
					"Nikhil Shirsekar");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(h);
		}

		// Test if the command value is closed, and flag is null, then an IAE is
		// thrown
		Command i = null;
		try {
			i = new Command(CommandValue.CLOSED, "Michelle Lemons", null,
					"this ticket no good, has no flag for closed.", "Nikhil Shirsekar");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(i);
		}
		
		assertEquals(CommandValue.ACCEPTED, CommandValue.valueOf("ACCEPTED"));
		assertEquals(CommandValue.POSSESSION, CommandValue.valueOf("POSSESSION"));
		assertEquals(CommandValue.PROGRESS, CommandValue.valueOf("PROGRESS"));
		assertEquals(CommandValue.FEEDBACK, CommandValue.valueOf("FEEDBACK"));
		assertEquals(CommandValue.CLOSED, CommandValue.valueOf("CLOSED"));
		assertEquals(Flag.DUPLICATE, Flag.valueOf("DUPLICATE"));
		assertEquals(Flag.INAPPROPRIATE, Flag.valueOf("INAPPROPRIATE"));
		assertEquals(Flag.RESOLVED, Flag.valueOf("RESOLVED"));

	}

	/**
	 * tests getCommand's functionality
	 */
	@Test
	public void testGetCommand() {
		Command theOneCommand = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, "This the one", "Nikhil Shirsekar");
		
		assertEquals(CommandValue.ACCEPTED, theOneCommand.getCommand());
		
	}

	/**
	 * tests getOwner functionality
	 */
	@Test
	public void testGetOwner() {
		Command theOneCommand = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, "This the one", "Nikhil Shirsekar");

		assertEquals("Michelle Lemons", theOneCommand.getOwner());
		
	}

	/**
	 * tests getCommand's functionality
	 */
	@Test
	public void testGetFlag() {
		Command theOneCommand = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, "This the one", "Nikhil Shirsekar");

		assertEquals(Flag.DUPLICATE, theOneCommand.getFlag());
	}

	/**
	 * tests getNoteText functionality
	 */
	@Test
	public void testGetNoteText() {
		Command theOneCommand = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, "Nikhil Shirsekar", "This the one");

		assertEquals("This the one", theOneCommand.getNoteText());
		
	}

	/**
	 * tests getAuthorNote functionality
	 */
	@Test
	public void testGetNoteAuthor() {
		Command theOneCommand = new Command(CommandValue.ACCEPTED, "Michelle Lemons", Flag.DUPLICATE, "Nikhil Shirsekar", "This the one");

		assertEquals("Nikhil Shirsekar", theOneCommand.getNoteAuthor());
		
	}

}

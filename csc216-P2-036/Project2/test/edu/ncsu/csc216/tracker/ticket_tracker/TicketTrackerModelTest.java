/**
 * 
 */
package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;
import edu.ncsu.csc216.tracker.ticket.Note;

/**
 * Tests the TicketTrackerModel
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class TicketTrackerModelTest {

	/** the registration manager */
	private TicketTrackerModel model;

	/**
	 * Sets up the model so they all use the same instance
	 */
	@Before
	public void setUp() {
		model = TicketTrackerModel.getInstance();
		model.createNewTicketList();
	}

	/**
	 * tests saving and loading tickets to a file
	 */
	@Test
	public void testSaveAndLoadTicketsToFile() {
		model.addTicketToList("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		model.getTicketById(1).getNotes().add(new Note("hello", "mklemons"));
		model.addTicketToList("Doin more things", "mkLemons", "THE TICKET CONSTRUCTOR WAS WACK IN THIS TEST CLASS");
		model.executeCommand(2, new Command(CommandValue.POSSESSION, "nnshirse", null, "nnshirse", "Updated owner"));
		model.getTicketById(2).getNotes().add(new Note("help", "mklemons"));
		model.saveTicketsToFile("test_files/actual-saved-ticket-file.txt");
		model.loadTicketsFromFile("test_files/actual-saved-ticket-file.txt");
		assertNull(model.getTicketById(1).getOwner());
		assertEquals("nnshirse", model.getTicketById(2).getOwner());
	}

	/**
	 * tests creating a new ticket list
	 */
	@Test
	public void testCreateNewTicketList() {
		model.addTicketToList("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		model.getTicketById(1).getNotes().add(new Note("hello", "mklemons"));
		model.addTicketToList("Doin more things", "mkLemons", "THE TICKET CONSTRUCTOR WAS WACK IN THIS TEST CLASS");
		model.getTicketById(2).getNotes().add(new Note("help", "mklemons"));

		assertEquals(2, model.getTicketListAsArray().length);
		model.createNewTicketList();
		assertEquals(0, model.getTicketListAsArray().length);
	}

	/**
	 * tests getting tickets as an array
	 */
	@Test
	public void testGetTicketListAsArray() {
		model.addTicketToList("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		model.getTicketById(1).getNotes().add(new Note("hello", "mklemons"));
		model.addTicketToList("MAKIN PROGRESS", "mkLemons", "STILL DOIN MORE THINGS");
		model.getTicketById(2).getNotes().add(new Note("help", "mklemons"));

		Object[][] o = model.getTicketListAsArray();
		assertEquals(1, o[0][0]);
		assertEquals("New", o[0][1]);
		assertEquals("Doin the things", o[0][2]);
		assertEquals(2, o[1][0]);
		assertEquals("New", o[1][1]);
		assertEquals("MAKIN PROGRESS", o[1][2]);

	}

	/**
	 * tests getting tickets by owner
	 */
	@Test
	public void testGetTicketListByOwnerAsArray() {
		model.addTicketToList("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		model.executeCommand(1, new Command(CommandValue.POSSESSION, "nnshirse", null, "nnshirse", "Updated owner"));
		model.getTicketById(1).getNotes().add(new Note("hello", "mklemons"));
		model.addTicketToList("MAKIN PROGRESS", "mkLemons", "STILL DOIN MORE THINGS");
		model.getTicketById(2).getNotes().add(new Note("help", "mklemons"));

		Object[][] o = model.getTicketListByOwnerAsArray("nnshirse");
		assertEquals(1, o[0][0]);
		assertEquals("Assigned", o[0][1]);
		assertEquals("Doin the things", o[0][2]);
	}

	/**
	 * test getting tickets by submitter
	 */
	@Test
	public void testGetTicketListBySubmitterAsArray() {
		model.addTicketToList("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		model.getTicketById(1).getNotes().add(new Note("hello", "mklemons"));
		model.addTicketToList("MAKIN PROGRESS", "mkLemons", "STILL DOIN MORE THINGS");
		model.getTicketById(2).getNotes().add(new Note("help", "mklemons"));

		Object[][] o = model.getTicketListBySubmitterAsArray("mkLemons");
		assertEquals(1, o[0][0]);
		assertEquals("New", o[0][1]);
		assertEquals("Doin the things", o[0][2]);
	}

	/**
	 * test deleting ticket by id
	 */
	@Test
	public void testDeleteTicketById() {
		model.addTicketToList("Doin the things", "mkLemons", "THIS MIGHT FIX THE THING");
		model.getTicketById(1).getNotes().add(new Note("hello", "mklemons"));
		model.addTicketToList("MAKIN PROGRESS", "mkLemons", "STILL DOIN MORE THINGS");
		model.getTicketById(2).getNotes().add(new Note("help", "mklemons"));

		model.deleteTicketById(1);
		assertEquals(1, model.getTicketListAsArray().length);
		assertEquals(2, model.getTicketListAsArray()[0][0]);
	}

	/**
	 * test executing a command
	 */
	@Test
	public void testExecuteCommand() {
		model.addTicketToList("Animal cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");
		
		Command c = new Command(CommandValue.POSSESSION, "Nikhil Shirsekar", Flag.DUPLICATE, "My note", "Nikhil Shirsekar");
		
		model.executeCommand(1, c);

		assertEquals("Duplicate", model.getTicketById(1).getFlagString());
	}

}

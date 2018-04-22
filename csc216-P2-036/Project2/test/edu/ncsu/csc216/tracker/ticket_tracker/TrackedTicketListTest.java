/**
 * 
 */
package edu.ncsu.csc216.tracker.ticket_tracker;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.ticket.xml.NoteList;
import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.ticket.xml.TicketList;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

/**
 * Test for the TrackedTicketList object
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class TrackedTicketListTest {

	/**
	 * Tests the Constructor for TrackedTicketList
	 */
	@Test
	public void testTrackedTicketList() {
		TrackedTicketList list = new TrackedTicketList();

		// Testing this constructor is kinda weird cause it gonna be used
		// everywhere else
		assertFalse(list == null);
	}

	/**
	 * Tests the adding functionality of TrackedTicketList
	 */
	@Test
	public void testAddTrackedTicket() {
		TrackedTicketList list = new TrackedTicketList();

		// Add a valid ticket
		list.addTrackedTicket("Animal cannot be constructed", "Michelle Lemons", "Nikhil Shirsekar");

		assertEquals(list.getTicketById(1).getSubmitter(), "Michelle Lemons");

	}

	/**
	 * test adding xml tickets
	 */
	@Test
	public void testAddXMLTickets() {
		Ticket ti = new Ticket();
		ti.setNoteList(new NoteList());
		ti.setFlag(null);
		ti.setId(0);
		ti.setTitle("Help with thing");
		ti.setOwner("nnshirse");
		ti.setState(TrackedTicket.WORKING_NAME);
		ti.setSubmitter("mklemons");
		ti.setTitle("Help with thing");
		TicketList tickets = new TicketList();
		tickets.getTickets().add(ti);
		
		TrackedTicketList list = new TrackedTicketList();
		
		list.addXMLTickets(tickets.getTickets());
		
		assertEquals(1, list.getTrackedTickets().size());
	}

	/**
	 * test get tracked tickets
	 */
	@Test
	public void testGetTrackedTickets() {
		TrackedTicketList list = new TrackedTicketList();

		// Add a valid ticket
		list.addTrackedTicket("Animal cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");

		ArrayList<TrackedTicket> copyList = list.getTrackedTickets();

		assertEquals(list.getTicketById(1), copyList.get(0));
	}

	/**
	 * test get tickets by submitter
	 */
	@Test
	public void testGetTicketsBySubmitter() {
		TrackedTicketList list = new TrackedTicketList();

		// Add some valid tickets
		list.addTrackedTicket("Animal cannot be constructed", "Michelle Lemons", "Nikhil Shirsekar");
		list.addTrackedTicket("Ecosystem cannot be constructed", "Michelle Lemons", "Nikhil Shirsekar");
		list.addTrackedTicket("GUI cannot be constructed", "Kevin Harrington", "Nikhil Shirsekar");

		ArrayList<TrackedTicket> ownedByMichelleLemons = list.getTicketsBySubmitter("Michelle Lemons");

		assertEquals(2, ownedByMichelleLemons.size());
	}

	/**
	 * test get tickets by owner
	 */
	@Test
	public void testGetTicketsByOwner() {
		TrackedTicketList list = new TrackedTicketList();

		// Add some valid tickets
		list.addTrackedTicket("Animal cannot be constructed", "Michelle Lemons", "Nikhil Shirsekar");
		list.executeCommand(1, new Command(CommandValue.POSSESSION, "nnshirse", null, "nnshirse", "Updated owner"));
		list.addTrackedTicket("Ecosystem cannot be constructed", "Michelle Lemons", "Nikhil Shirsekar");
		list.executeCommand(2, new Command(CommandValue.POSSESSION, "nnshirse", null, "nnshirse", "Updated owner"));
		list.addTrackedTicket("GUI cannot be constructed", "Kevin Harrington", "Domonic Caristo");

		ArrayList<TrackedTicket> ownedByNikhil = list.getTicketsByOwner("nnshirse");

		assertEquals(2, ownedByNikhil.size());
	}

	/**
	 * test get ticket by id
	 */
	@Test
	public void testGetTicketById() {
		TrackedTicketList list = new TrackedTicketList();

		// Add some valid tickets
		list.addTrackedTicket("Animal cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");
		list.addTrackedTicket("Ecosystem cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");
		list.addTrackedTicket("GUI cannot be constructed", "Domonic Caristo", "Kevin Harrington");

		TrackedTicket lastTicketSubmitted = list.getTicketById(3);

		assertEquals(3, lastTicketSubmitted.getTicketId());
	}

	/**
	 * test executing a command
	 */
	@Test
	public void testExecuteCommand() {
		TrackedTicketList list = new TrackedTicketList();

		list.addTrackedTicket("Animal cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");
		
		Command c = new Command(CommandValue.POSSESSION, "Nikhil Shirsekar", Flag.DUPLICATE, "My note", "Nikhil Shirsekar");
		
		list.executeCommand(1, c);

		assertEquals("Duplicate", list.getTicketById(1).getFlagString());
	}

	/**
	 * test delete ticket by id
	 */
	@Test
	public void testDeleteTicketById() {
		TrackedTicketList list = new TrackedTicketList();

		// Add some valid tickets
		list.addTrackedTicket("Animal cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");
		list.addTrackedTicket("Ecosystem cannot be constructed", "Nikhil Shirsekar", "Michelle Lemons");
		list.addTrackedTicket("GUI cannot be constructed", "Domonic Caristo", "Kevin Harrington");

		TrackedTicket lastTicketSubmitted = list.getTicketById(3);

		assertEquals(3, lastTicketSubmitted.getTicketId());
		
		list.deleteTicketById(2);
		assertNull(list.getTicketById(2));
		
		assertEquals("GUI cannot be constructed", list.getTicketById(3).getTitle());
		
	}

}

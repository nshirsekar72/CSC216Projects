package edu.ncsu.csc216.tracker.ticket_tracker;

import java.util.ArrayList;

import edu.ncsu.csc216.ticket.xml.TicketIOException;
import edu.ncsu.csc216.ticket.xml.TicketReader;
import edu.ncsu.csc216.ticket.xml.TicketWriter;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

/**
 * Creates the manager of all the trackedTickets
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class TicketTrackerModel {
	/** instance of the TicketTrackerModel */
	private static TicketTrackerModel instance;
	/** list of the tickets in the system */
	private TrackedTicketList trackedTicketList = new TrackedTicketList();
	
	/**
	 * Gets the instance of the t that is currently running
	 * @return instance currently running since there only has to be one
	 */
	public static TicketTrackerModel getInstance() {
		if (instance == null) {
			instance = new TicketTrackerModel();
			TrackedTicket.setCounter(1);
		}
		return instance;
	}
	
	/**
	 * Saves the current list of tickets to a file specified by the param
	 * @param fileName File to save the Tickets to
	 */
	public void saveTicketsToFile(String fileName){
		TicketWriter tw = new TicketWriter(fileName);
		TrackedTicket t1 = null;
		for (int i = 0; i < trackedTicketList.getTrackedTickets().size(); i++){
			t1 = trackedTicketList.getTrackedTickets().get(i);
			tw.addItem(t1.getXMLTicket());
		}
		try {
			tw.marshal();
		} catch (TicketIOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	/**
	 * Loads Tickets from a file given in XML format
	 * @param fileName File to load ticket objects from
	 */
	public void loadTicketsFromFile(String fileName) {
		TicketReader tr = null;
		try {
			tr = new TicketReader(fileName);
			trackedTicketList.addXMLTickets(tr.getTickets());
		} catch (TicketIOException e){
			throw new IllegalArgumentException(e.getMessage());
		}
		
		
	}
	
	/**
	 * Creates a new list of tickets
	 */
	public void createNewTicketList(){
		trackedTicketList = new TrackedTicketList();
	}
	
	/**
	 * Gets the ticketlist as an array instead of an object
	 * @return a 2D array of objects that represents the ticket list
	 */
	public Object[][] getTicketListAsArray(){
		ArrayList<TrackedTicket> hello = trackedTicketList.getTrackedTickets();
		Object[][] o = new Object[hello.size()][3];
		for (int i = 0; i < hello.size(); i++){
			TrackedTicket t = hello.get(i);
			o[i][0] = t.getTicketId();
			o[i][1] = t.getStateName();
			o[i][2] = t.getTitle();
		}
		return o;
	}
	
	/**
	 * Gets the ticketlist as an array instead of an object while sorted by owner
	 * @param owner the owner of tickets to filter with
	 * @return a 2D array of objects that represents the ticket list of one owner
	 */
	public Object[][] getTicketListByOwnerAsArray(String owner){
		ArrayList<TrackedTicket> hello = trackedTicketList.getTicketsByOwner(owner);
		Object[][] o = new Object[hello.size()][3];
		for (int i = 0; i < hello.size(); i++){
			TrackedTicket t = hello.get(i);
			o[i][0] = t.getTicketId();
			o[i][1] = t.getStateName();
			o[i][2] = t.getTitle();
		}
		return o;
	}
	
	/**
	 * Gets the ticketlist as an array instead of an object while sorted by submitter
	 * @param submitter the submitter of tickets to filter with
	 * @return a 2D array of objects that represents the ticket list of one owner
	 */
	public Object[][] getTicketListBySubmitterAsArray(String submitter){
		ArrayList<TrackedTicket> hello = trackedTicketList.getTicketsBySubmitter(submitter);
		Object[][] o = new Object[hello.size()][3];
		for (int i = 0; i < hello.size(); i++){
			TrackedTicket t = hello.get(i);
			o[i][0] = t.getTicketId();
			o[i][1] = t.getStateName();
			o[i][2] = t.getTitle();
		}
		return o;
	}
	
	/**
	 * Returns a ticket with a given id
	 * @param id ID of the ticket needed
	 * @return ticket at ID number specified
	 */
	public TrackedTicket getTicketById(int id){
		return trackedTicketList.getTicketById(id);
	}
	
	/**
	 * Will remove a given ticket from the list given an id
	 * @param id ID of the ticket to be deleted
	 */
	public void deleteTicketById(int id){
		trackedTicketList.deleteTicketById(id);
	}
	
	/**
	 * Adds a ticket to the list of tickets
	 * @param title Title of ticket
	 * @param submitter Submitter of ticket
	 * @param owner Owner of ticket
	 */
	public void addTicketToList(String title, String submitter, String owner){
		trackedTicketList.addTrackedTicket(title, submitter, owner);
	}

	/**
	 * Executes a given command on a specified ticket
	 * @param ticketId ID of the ticket to be modified
	 * @param c Command to be executed
	 */
	public void executeCommand(int ticketId, Command c) {
		trackedTicketList.getTicketById(ticketId).update(c);
		
	}

}

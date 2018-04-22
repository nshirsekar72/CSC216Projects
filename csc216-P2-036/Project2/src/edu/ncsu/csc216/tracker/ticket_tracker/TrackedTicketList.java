package edu.ncsu.csc216.tracker.ticket_tracker;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.ticket.xml.Ticket;
import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;

/**
 * Makes a list of the tracked tickets and performs actions with them
 * 
 * @author Michelle Lemons
 * @author Nikhil Shirsekar
 */
public class TrackedTicketList {

	/** Initial value for counter */
	private static final int INITIAL_COUNTER_VALUE = 1;

	/** List of tracked tickets */
	private ArrayList<TrackedTicket> trackedTicketList;

	/**
	 * Constructor for Tracked Ticket List
	 */
	public TrackedTicketList() { 
		trackedTicketList = new ArrayList<TrackedTicket>();
		TrackedTicket.setCounter(INITIAL_COUNTER_VALUE);
	}

	/**
	 * adds a new tracked ticket to the list
	 * 
	 * @param title
	 *            title of ticket
	 * @param submitter
	 *            submitter of ticket
	 * @param owner
	 *            owner of ticket
	 * @return number of ticket created
	 */
	public int addTrackedTicket(String title, String submitter, String owner) {

		TrackedTicket newTicket = new TrackedTicket(title, submitter, owner);

		trackedTicketList.add(newTicket);

		return newTicket.getTicketId();
	}

	/**
	 * adds tickets form XML file
	 * 
	 * @param tickets
	 *            list of tickets to be added
	 */
	public void addXMLTickets(List<Ticket> tickets) {
		TrackedTicket ticketToBeAdded = null;
		int maxTicketId = 0;

		for (int i = 0; i < tickets.size(); i++) {

			ticketToBeAdded = new TrackedTicket(tickets.get(i));

			if (ticketToBeAdded.getTicketId() > maxTicketId) {
				maxTicketId = ticketToBeAdded.getTicketId();
			}

			trackedTicketList.add(ticketToBeAdded);
		}

		TrackedTicket.setCounter(maxTicketId + 1);
	}

	/**
	 * gets all tracked tickets
	 * 
	 * @return a list of all tracked tickets
	 */
	public ArrayList<TrackedTicket> getTrackedTickets() {
		return this.trackedTicketList;
	}

	/**
	 * gets all tickets for a specific owner
	 * 
	 * @param owner
	 *            owner of the tickets
	 * @return list of all tickets from that owner
	 */
	public ArrayList<TrackedTicket> getTicketsByOwner(String owner) {
		if (owner == null){
			throw new IllegalArgumentException();
		}
		ArrayList<TrackedTicket> ticketsByOwner = new ArrayList<TrackedTicket>();

		for (int i = 0; i < this.trackedTicketList.size(); i++) {

			if (trackedTicketList.get(i).getOwner() != null && trackedTicketList.get(i).getOwner().equals(owner)) {
				ticketsByOwner.add(trackedTicketList.get(i));
			}
		}

		return ticketsByOwner;
	}

	/**
	 * gets tickets for a specified submitter
	 * 
	 * @param submitter
	 *            one who submitted the ticket
	 * @return list of tickets from submitter
	 */
	public ArrayList<TrackedTicket> getTicketsBySubmitter(String submitter) {
		if (submitter == null){
			throw new IllegalArgumentException();
		}

		ArrayList<TrackedTicket> ticketsBySubmitter = new ArrayList<TrackedTicket>();

		for (int i = 0; i < this.trackedTicketList.size(); i++) {

			if (trackedTicketList.get(i).getSubmitter().equals(submitter)) {
				ticketsBySubmitter.add(trackedTicketList.get(i));
			}
		}

		return ticketsBySubmitter;
	}

	/**
	 * Get ticket based off id number
	 * 
	 * @param id
	 *            identification number
	 * @return ticket with id number
	 */
	public TrackedTicket getTicketById(int id) {
		
		for (int i = 0; i < trackedTicketList.size(); i++){
			if (trackedTicketList.get(i) != null && trackedTicketList.get(i).getTicketId() == id){
				return trackedTicketList.get(i);
			}
		}
		return null;
	}

	/**
	 * executes a command on a specified ticket
	 * 
	 * @param id
	 *            id number for ticket
	 * @param c
	 *            command to be issued
	 */
	public void executeCommand(int id, Command c) {
		for (int i = 0; i < trackedTicketList.size(); i++){
			if (trackedTicketList.get(i) != null && trackedTicketList.get(i).getTicketId() == id){
				trackedTicketList.get(i).update(c);
			}
		}
	}

	/**
	 * deletes ticket by id
	 * 
	 * @param id
	 *            id of ticket
	 */
	public void deleteTicketById(int id) {

		for (int i = 0; i < trackedTicketList.size(); i++) {

			if (trackedTicketList.get(i).getTicketId() == id) {
				trackedTicketList.remove(i);
			}
		}
		

	}

}

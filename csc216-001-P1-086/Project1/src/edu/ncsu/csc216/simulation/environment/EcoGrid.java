package edu.ncsu.csc216.simulation.environment;

import edu.ncsu.csc216.simulation.actor.Animal;
import edu.ncsu.csc216.simulation.environment.utils.Location;


/**
 * Behaviors of a rectangular grid. Each grid cell in the grid can contain an Item.
 * @author Jo Perry
 */
public interface EcoGrid {
	
	/**
	 * Is the grid cell empty?
	 * @param location location of the cell on the grid
	 * @return true if there is no item in that cell.
	 */
	boolean isEmpty(Location location);
	
	/**
	 * What is the Animal at a particular grid cell?
	 * @param location location of the cell on the grid
	 * @return the Animal at the given cell or null if the cell is empty
	 */
	Animal getItemAt(Location location);
	
	/**
	 * Make a particular grid cell empty.
	 * @param place location of the cell on the grid
	 */
	void remove(Location place);
	
	/**
	 * Add an Animal to a particular grid cell.
	 * @param x Item to add
	 * @param location location of the cell on the grid
	 */
	void add(Animal x, Location location);
	
	/**
	 * Find the first cell in the grid that shares a border with the cell at
	 * the given position and that contains no Animal. Start looking in the
	 * given direction and continue clockwise around the cell.
	 * @param position initial position of animal
	 * @param startDirection Direction to start: 0 = west, 1 = north, 2 = east, 3 = south.
	 * @return the location for the first empty neighbor found, or null if none are found.
	 */
	public Location findFirstEmptyNeighbor(Location position, int startDirection); 
	
	/**
	 * What cell is due north of the given cell?
	 * @param x The given cell
	 * @return The cell due north
	 */
	Location dueNorth(Location x);
	
	/**
	 * What cell is due south of the given cell?
	 * @param x The given cell
	 * @return The cell due south
	 */
	Location dueSouth(Location x);	
	
	/**
	 * What cell is due west of the given cell?
	 * @param x The given cell
	 * @return The cell due west
	 */
	Location dueWest(Location x);
	
	/**
	 * What cell is due east of the given cell?
	 * @param x The given cell
	 * @return The cell due east
	 */
	Location dueEast(Location x);
	
	/**
	 * What is in the grid?
	 * @return a snapshot of the grid, showing
	 *   the contents of each grid cell.
	 */
	Animal[][] getMap();
	
	/**
	 * Enable all live animals in the grid.
	 */
	public void enableTheLiving();
	
	/**
	 * Remove all dead animals from the grid.
	 */
	public void buryTheDead();
	

}
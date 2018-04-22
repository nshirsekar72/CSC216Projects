/**
 * location class to deal with location in a more streamline way
 */
package edu.ncsu.csc216.simulation.environment.utils;

/**
 * allows for easy access to location in the grid
 * @author Nikhil Shirsekar
 *
 */
public class Location {
	
	/** row number of animals location */
	private int row;
	
	
	/** column number of animals location*/
	private int column;
	
	/**
	 * constructor for location
	 * @param row row number
	 * @param column column number
	 */
	public Location(int row, int column){
		this.row = row;
		this.column = column;
	}

	/**
	 * gets the row value for location
	 * @return the row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * gets the column value for location
	 * @return the column
	 */
	public int getCol() {
		return this.column;
	}
	
	

}

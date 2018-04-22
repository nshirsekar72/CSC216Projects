/**
 * 
 */
package edu.ncsu.csc216.simulation.environment;

import edu.ncsu.csc216.simulation.actor.Animal;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * Ecosystem's main engine
 * 
 * @author Nikhil
 * 
 */
public class Ecosystem implements EcoGrid {

	/** number of rows */
	private int maxRows;

	/** number of columns */
	private int maxCols;

	/** Map of environment for ecosystem */
	private Animal[][] map;

	/**
	 * Ecosystem constructor
	 * 
	 * @param maxRows
	 *            number of rows
	 * @param maxCols
	 *            number of columns
	 */
	public Ecosystem(int maxRows, int maxCols) {
		this.maxRows = maxRows;
		this.maxCols = maxCols;

		this.map = new Animal[this.maxRows][this.maxCols];
	}

	/**
	 * check if a location is empty
	 * 
	 * @param location
	 *            the location in question
	 * @return return true if grid is empty
	 */
	@Override
	public boolean isEmpty(Location location) {
		if (location == null) {
			return true;
		
		} else if (map[location.getRow()][location.getCol()] == null) {
			return true;
		}
		return false;
		
	}

	/**
	 * gets the animal in a certain location
	 * 
	 * @param location
	 *            location of animal wanted
	 * @return the animal at that location
	 */
	@Override
	public Animal getItemAt(Location location) {
		if (location == null) {
			return null;
		}
		return map[location.getRow()][location.getCol()];
	}

	/**
	 * remove an animal from the ecosystem
	 * 
	 * @param location
	 *            location of animal to remove
	 */
	@Override
	public void remove(Location location) {
		map[location.getRow()][location.getCol()] = null;

	}

//	/**
//	 * add an animal to a location
//	 * 
//	 * @param location
//	 *            where to add the animal
//	 * @param a
//	 *            what kind of animal to add
//	 */
//	public void add(Location location, Animal a) {
//
//		map[location.getRow()][location.getCol()] = a;
//
//	}

	/**
	 * find the first empty neighbor of animal
	 * @param location location of the animal
	 * @param x integer used for...
	 * @return location of empty cell if there is one
	 */
	@Override
	public Location findFirstEmptyNeighbor(Location location, int x) {
		
		//Starting from the west
		if (x == 0) {
			if (isEmpty(dueWest(location))) {
				return dueWest(location);
			} else if (isEmpty(dueNorth(location))) {
				return dueNorth(location);
			} else if (isEmpty(dueEast(location))) {
				return dueEast(location);
			} else if (isEmpty(dueSouth(location))) {
				return dueSouth(location);
			}
		}
		
		//starting from the north
		if (x == 1) {
			if (isEmpty(dueNorth(location))) {
				return dueNorth(location);
			} else if (isEmpty(dueEast(location))) {
				return dueEast(location);
			} else if (isEmpty(dueSouth(location))) {
				return dueSouth(location);
			} else if (isEmpty(dueWest(location))) {
				return dueWest(location);
			}
		}
		
		//starting from the east
		if (x == 2) {
			if (isEmpty(dueEast(location))) {
				return dueEast(location);
			} else if (isEmpty(dueSouth(location))) {
				return dueSouth(location);
			} else if (isEmpty(dueWest(location))) {
				return dueWest(location);
			} else if (isEmpty(dueNorth(location))) {
				return dueNorth(location);
			}
		}
		
		// Starting from the south
		if (x == 3) {
			if (isEmpty(dueSouth(location))) {
				return dueSouth(location);
			} else if (isEmpty(dueWest(location))) {
				return dueWest(location);
			} else if (isEmpty(dueNorth(location))) {
				return dueNorth(location);
			} else if (isEmpty(dueEast(location))) {
				return dueEast(location);
			}
		}
		return null;
	}

	/**
	 * checks due north of current cell
	 * 
	 * @param location
	 *            of current cell
	 * @return location of cell due north
	 */
	@Override
	public Location dueNorth(Location location) {
		Location dueNorth = null;
		
		if (location.getRow() == 0) {
			dueNorth = new Location(this.maxRows - 1, location.getCol());
			return dueNorth;
		}
		
		dueNorth = new Location(location.getRow() - 1, location.getCol());
		return dueNorth;
	}

	/**
	 * checks due south of current cell
	 * 
	 * @param location
	 *            of current cell
	 * @return location of cell due south
	 */
	@Override
	public Location dueSouth(Location location) {
		Location dueSouth = null;
		
		if(location.getRow() == this.maxRows - 1 ) {
			dueSouth = new Location(0, location.getCol());
			return dueSouth;
		}
		
		dueSouth = new Location(location.getRow() + 1, location.getCol());
		return dueSouth;
	}

	/**
	 * checks due west of current cell
	 * 
	 * @param location
	 *            of current cell
	 * @return location of cell due west
	 */
	@Override
	public Location dueWest(Location location) {
		Location dueWest = null;
		
		if (location.getCol() == 0) {
			dueWest = new Location(location.getRow(), this.maxCols - 1);
			return dueWest;
		}
		
		dueWest = new Location(location.getRow(), location.getCol() - 1);
		return dueWest;
	}

	/**
	 * checks due east of current cell
	 * 
	 * @param location
	 *            of current cell
	 * @return location of cell due east
	 */
	@Override
	public Location dueEast(Location location) {
		Location dueEast = null;
		
		if (location.getCol() == this.maxCols - 1) {
			dueEast = new Location(location.getRow(), 0);
			return dueEast;
		}
		
		dueEast = new Location(location.getRow(), location.getCol() + 1);
		return dueEast;
	}

	/**
	 * gets the full map of the ecosystem
	 * 
	 * @return a 2d array representation of the ecosystem
	 */
	@Override
	public Animal[][] getMap() {

		return this.map;
	}

	/**
	 * enabled all living creatures
	 */
	@Override
	public void enableTheLiving() {
		
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] != null) {
					map[i][j].enable();
				}
			}
		}

	}

	/**
	 * buries all dead creatures
	 */
	@Override
	public void buryTheDead() {
		
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] != null && !map[i][j].isAlive()) {
					Location deadAnimal = new Location(i, j);
					remove(deadAnimal);
				}
			}
		}

	}

	/**
	 * adds an animal to the ecosystem
	 * 
	 * @param x
	 *            animal to be added
	 * @param location
	 *            location to be added at
	 */
	@Override
	public void add(Animal x, Location location) {
		
		map[location.getRow()][location.getCol()] = x;

	}

}

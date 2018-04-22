/**
 * 
 */
package edu.ncsu.csc216.simulation.actor;

import java.awt.Color;
import java.util.Random;

import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * Animal Superclass that parents all animal types in the food chain
 * 
 * @author Nikhil Shirsekar
 * 
 * 
 */
public abstract class Animal {

	/** Time since last meal */
	private int timeSinceLastMeal;

	/** time since last time bred */
	private int timeSinceLastBreed;

	/** states if its possible to act in the current step */
	private boolean canActThisStep;

	/** symbol representing animal */
	private char symbol;

	/** shows if an animal is alive or not */
	private boolean alive;

	/** seed value */
	private static int seed = 500;

	/** generates a random integer */
	private static Random randomGenerator = new Random(seed);

	/**
	 * constructor for Animal
	 * 
	 * @param symbol
	 *            symbol of animal
	 */
	public Animal(char symbol) {
		this.symbol = symbol;
		this.alive = true;
		timeSinceLastBreed = 0;
		timeSinceLastMeal = 0;
		this.canActThisStep = false;
	}

	/**
	 * creates a random seed value
	 * 
	 * @param seed
	 *            random int value
	 */
	public static void setRandomSeed(int seed) { // Helpful for Testing
		randomGenerator = new Random(seed);

	}

	/**
	 * gets symbol for specific animal
	 * 
	 * @return symbol for animal
	 */
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * is true when animal is alive
	 * 
	 * @return true when animal is alive
	 */
	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * enabled an animal
	 */
	public void enable() {
		canActThisStep = true;
	}

	/**
	 * disables an animal
	 */
	public void disable() {
		canActThisStep = false;
	}

	/**
	 * kills an animal and removes it from the ecosystem
	 */
	protected void die() {
		this.alive = false;
		disable();
	}

	/**
	 * checks to see if an animal can act
	 * 
	 * @return true if an animal can act
	 */
	protected boolean canAct() {
		if (canActThisStep) {
			return canActThisStep;
		} else {
			return false;
		}
	}

	/**
	 * gets the number of steps since an animal has last bred
	 * 
	 * @return number of steps since an animal has bred
	 */
	protected int getTimeSinceLastBreed() {
		return timeSinceLastBreed;
	}

	/**
	 * gets the amount of time since an animal has last eaten
	 * 
	 * @return number of steps since last meal animal had
	 */
	protected int getTimeSinceLastMeal() {
		return timeSinceLastMeal;
	}

	/**
	 * increase the number of steps since last meal
	 */
	protected void incrementTimeSinceLastMeal() {
		timeSinceLastMeal = timeSinceLastMeal + 1;
	}

	/**
	 * increase number of times since last breed
	 */
	protected void incrementTimeSinceLastBreed() {
		timeSinceLastBreed = timeSinceLastBreed + 1;
	}

	/**
	 * action for animal to breed
	 * 
	 * @param location
	 *            location of animal
	 * @param ecoGrid
	 *            gives functions for grid
	 * @return true if animal can breed
	 */
	protected boolean breed(Location location, EcoGrid ecoGrid) {
		if (!pastBreedTime(timeSinceLastBreed)) {
			return false;
		}
		Location breedLocation = ecoGrid.findFirstEmptyNeighbor(location, 0);

		if (breedLocation == null) {
			return false;

		} else {
			ecoGrid.add(makeNewBaby(), breedLocation);
			this.timeSinceLastBreed = 0;
			return true;
		}
	}

	/**
	 * action for animal to move
	 * 
	 * @param location
	 *            location of animal
	 * @param ecoGrid
	 *            gives functions for grid
	 */
	protected void move(Location location, EcoGrid ecoGrid) {
		Animal movingAnimal = ecoGrid.getItemAt(location);
		Location moveLocation = ecoGrid.findFirstEmptyNeighbor(location, randomGenerator.nextInt(4));

		if (moveLocation != null) {
			ecoGrid.add(movingAnimal, moveLocation);
			ecoGrid.remove(location);
		}

	}

	/**
	 * action for animal to eat
	 * 
	 * @param location
	 *            location of animal
	 * @param ecoGrid
	 *            gives functions for grid
	 * @return true if animal can eat
	 */
	protected boolean eat(Location location, EcoGrid ecoGrid) {
		
		Animal predator = ecoGrid.getItemAt(location);

		// Checks due west
		if (ecoGrid.getItemAt(ecoGrid.dueWest(location)) != null
				&& getFoodChainRank() > ecoGrid.getItemAt(ecoGrid.dueWest(location)).getFoodChainRank()) {

			ecoGrid.remove(ecoGrid.dueWest(location));
			ecoGrid.add(predator, ecoGrid.dueWest(location));
			ecoGrid.remove(location);
			this.timeSinceLastMeal = 0;
			return true;
		}

		// Checks due north
		if (ecoGrid.getItemAt(ecoGrid.dueNorth(location)) != null
				&& getFoodChainRank() > ecoGrid.getItemAt(ecoGrid.dueNorth(location)).getFoodChainRank()) {

			ecoGrid.remove(ecoGrid.dueNorth(location));
			ecoGrid.add(predator, ecoGrid.dueNorth(location));
			ecoGrid.remove(location);
			this.timeSinceLastMeal = 0;
			return true;
		}

		// Checks due east
		if (ecoGrid.getItemAt(ecoGrid.dueEast(location)) != null
				&& getFoodChainRank() > ecoGrid.getItemAt(ecoGrid.dueEast(location)).getFoodChainRank()) {

			ecoGrid.remove(ecoGrid.dueEast(location));
			ecoGrid.add(predator, ecoGrid.dueEast(location));
			ecoGrid.remove(location);
			this.timeSinceLastMeal = 0;
			return true;

		}

		// Checks due south
		if (ecoGrid.getItemAt(ecoGrid.dueSouth(location)) != null
				&& getFoodChainRank() > ecoGrid.getItemAt(ecoGrid.dueSouth(location)).getFoodChainRank()) {

			ecoGrid.remove(ecoGrid.dueSouth(location));
			ecoGrid.add(predator, ecoGrid.dueSouth(location));
			ecoGrid.remove(location);
			this.timeSinceLastMeal = 0;
			return true;
		}

		// If there is nothing to eat in all directions
		return false;

	}

	/**
	 * gets color for animal type
	 * 
	 * @return color that represents animal
	 */
	public abstract Color getColor();

	/**
	 * makes an animal act
	 * 
	 * @param location
	 *            location of animal
	 * @param ecoGrid
	 *            actions to use on grid
	 */
	public abstract void act(Location location, EcoGrid ecoGrid);

	/**
	 * checks if an animal has passed its specified breeding time
	 * 
	 * @param breedTime
	 *            breed time for animal
	 * @return true if it has passed its breeding time
	 */
	protected abstract boolean pastBreedTime(int breedTime);

	/**
	 * creates a new baby of the specified type of animal
	 * 
	 * @return animal of the specified type
	 */
	protected abstract Animal makeNewBaby();

	/**
	 * gets the rank in the food chain of the specified
	 * 
	 * @return rank of animal in food chain
	 */
	protected abstract int getFoodChainRank();

}

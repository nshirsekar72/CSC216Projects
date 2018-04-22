/**
 * 
 */
package edu.ncsu.csc216.simulation.actor;

import java.awt.Color;

import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * Low end animal subclass for animal superclass
 * 
 * @author Nikhil Shirsekar
 *
 */
public class PurePrey extends Animal {

	/** Age of animal low on food chain */
	private int age;

	/**
	 * Constructor for low end animal on food chain
	 * 
	 * @param symbol
	 *            symbol for animal
	 */
	public PurePrey(char symbol) {
		super(symbol);
	}

	/**
	 * gets the color of an animal
	 * 
	 * @return color for animal
	 */
	@Override
	public Color getColor() {
		return Configs.getPreyColor();
	}

	/**
	 * allows animal to act
	 */
	@Override
	public void act(Location location, EcoGrid ecoGrid) {

		if (canAct()) {
			disable();
			if (pastBreedTime(getTimeSinceLastBreed()) && breed(location, ecoGrid)) {
				age++;
			} else {
				incrementTimeSinceLastBreed();
				age++;
				move(location, ecoGrid);

			}

			if (age >= Configs.getPreyStarveTime()) {
				die();

			}

		}

	}

	/**
	 * checks to see if the animal is passed its breed time
	 */
	@Override
	protected boolean pastBreedTime(int breedTime) {
		if (breedTime >= Configs.getPreyBreedTime()) {
			return true;
		}
		return false;

	}

	/**
	 * makes a new baby animal of its type
	 * 
	 * @return a new baby animal of the same type
	 */
	@Override
	protected Animal makeNewBaby() {

		Animal babyPurePrey = new PurePrey(getSymbol());

		babyPurePrey.disable();

		return babyPurePrey;

	}

	/**
	 * gets the rank of the animal on the food chain
	 * 
	 * @return rank of animal
	 */
	@Override
	protected int getFoodChainRank() {
		return Configs.getPreyFoodChainRank();
	}

}

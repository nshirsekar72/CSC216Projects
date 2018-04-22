/**
 * 
 */
package edu.ncsu.csc216.simulation.actor;

import java.awt.Color;

import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * Middle animal subclass for animal superclass
 * 
 * @author Nikhil Shirsekar
 *
 */
public class PredatorPrey extends Animal {

	/**
	 * constructor for a middle range animal
	 * 
	 * @param symbol
	 *            symbol for animal
	 */
	public PredatorPrey(char symbol) {
		super(symbol);
	}

	/**
	 * gets the color of an animal
	 * 
	 * @return color for animal
	 */
	@Override
	public Color getColor() {
		return Configs.getMiddleColor();
	}

	/**
	 * allows animal to act
	 */
	@Override
	public void act(Location location, EcoGrid ecoGrid) {

		if (canAct()) {

			
			if (pastBreedTime(getTimeSinceLastBreed()) && breed(location, ecoGrid)) {
				incrementTimeSinceLastMeal();
				//return;

			} else if (eat(location, ecoGrid)) {
				incrementTimeSinceLastBreed();
				//return;

			} else {
				incrementTimeSinceLastMeal();
				incrementTimeSinceLastBreed();
				move(location, ecoGrid);
			}

			if (getTimeSinceLastMeal() >= Configs.getMiddleStarveTime()) {
				die();
			}
			
			disable();
		}

	}

	/**
	 * checks to see if the animal is passed its breed time
	 */
	@Override
	protected boolean pastBreedTime(int breedTime) {
		if (breedTime >= Configs.getMiddleBreedTime()) {
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
		Animal babyPredatorPrey = new PredatorPrey(getSymbol());

		babyPredatorPrey.disable();

		return babyPredatorPrey;
	}

	/**
	 * gets the rank of the animal on the food chain
	 * 
	 * @return rank of animal
	 */
	@Override
	protected int getFoodChainRank() {
		return Configs.getMiddleFoodChainRank();
	}

}

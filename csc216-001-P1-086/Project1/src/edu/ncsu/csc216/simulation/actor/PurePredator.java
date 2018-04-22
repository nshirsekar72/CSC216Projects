package edu.ncsu.csc216.simulation.actor;

import java.awt.Color;

import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * High rank animal subclass for animal supercalss
 * 
 * @author Nikhil Shirsekar
 *
 */
public class PurePredator extends Animal {

	/**
	 * constructor for the highest ranking animals in the food chain
	 * 
	 * @param symbol
	 *            symbol for animal
	 */
	public PurePredator(char symbol) {
		super(symbol);
	}

	/**
	 * get color for animal
	 * 
	 * @return color
	 */
	@Override
	public Color getColor() {
		return Configs.getPredatorColor();
	}

	/**
	 * lets animal act
	 * 
	 * @param location
	 *            location of animal that will act
	 * @param ecoGrid
	 *            gives functionality of grid
	 */
	@Override
	public void act(Location location, EcoGrid ecoGrid) {

		if (canAct()) {

			disable();
			if (eat(location, ecoGrid)) {
				incrementTimeSinceLastBreed();
				return;

			} else if (pastBreedTime(getTimeSinceLastBreed()) && breed(location, ecoGrid)) {
				incrementTimeSinceLastMeal();
				return;
			} else {
				incrementTimeSinceLastMeal();
				incrementTimeSinceLastBreed();
				move(location, ecoGrid);
			}

			if (getTimeSinceLastMeal() >= Configs.getPredatorStarveTime()) {
				die();

			}
		}

	}

	/**
	 * checks to see if animal can breed
	 * 
	 * @return true if past animals breed time
	 */
	@Override
	protected boolean pastBreedTime(int breedTime) {
		if (breedTime >= Configs.getPredatorBreedTime()) {
			return true;
		}
		return false;

	}

	/**
	 * makes a new baby animal of the same type
	 * 
	 * @return new baby animal of the same type
	 */
	@Override
	protected Animal makeNewBaby() {

		Animal babyPurePredator = new PurePredator(getSymbol());

		babyPurePredator.disable();

		return babyPurePredator;
	}

	/**
	 * gets rank of animal in food chain
	 * 
	 * @return animals food chain rank
	 */
	@Override
	protected int getFoodChainRank() {

		return Configs.getPredatorFoodChainRank();
	}

}

package edu.ncsu.csc216.simulation.actor;

import java.awt.Color;

/**
 * Configuration items for the simulation
 * 
 * @author Nikhil Shirsekar
 *
 */
public class Configs {

	/** Default food chain ranks */
	private static final int[] DEFAULT_FOOD_CHAIN_RANK = { 0, 10, 20 };

	/** Default colors for all animals */
	private static final Color[] DEFAULT_COLORS = { Color.green, Color.orange, Color.red };

	/** Colors of the players */
	private static Color[] playerColors;

	/** Default starve time for each animal */
	private static final int[] DEFAULT_STARVE_TIME = { 10, 6, 5 };

	/** Amount of time it takes to starve for each animal */
	private static int[] starveTime;

	/** Default time between each animals breeding */
	private static final int[] DEFAULT_BREED_TIME = { 1, 7, 15 };

	/** Breed times for each of the animals */
	private static int[] breedTime;

	/**
	 * configures the simulatior with custom values from file
	 * 
	 * @param colors
	 *            colors of animals
	 * @param starveTimes
	 *            starve times of animals
	 * @param breedTimes
	 *            breed times of animals
	 */
	public static void initConfigs(Color[] colors, int[] starveTimes, int[] breedTimes) {

		playerColors = colors;
		starveTime = starveTimes;
		breedTime = breedTimes;
	}

	/**
	 * sets all parameters for animals to default
	 */
	public static void setToDefaults() {
		playerColors = DEFAULT_COLORS;
		starveTime = DEFAULT_STARVE_TIME;
		breedTime = DEFAULT_BREED_TIME;
	}

	/**
	 * gets the color for low animals
	 * 
	 * @return color of animal
	 */
	public static Color getPreyColor() {
		return playerColors[0];
	}

	/**
	 * gets the color for middle animals
	 * 
	 * @return color of animal
	 */
	public static Color getMiddleColor() {
		return playerColors[1];
	}

	/**
	 * gets the color for high animals
	 * 
	 * @return color of animal
	 */
	public static Color getPredatorColor() {
		return playerColors[2];
	}

	/**
	 * gets food chain rank for low animal
	 * 
	 * @return food chain rank value
	 */
	public static int getPreyFoodChainRank() {

		return DEFAULT_FOOD_CHAIN_RANK[0];
	}

	/**
	 * gets food chain rank for middle animal
	 * 
	 * @return food chain rank value
	 */
	public static int getMiddleFoodChainRank() {

		return DEFAULT_FOOD_CHAIN_RANK[1];
	}

	/**
	 * gets food chain rank for high animal
	 * 
	 * @return food chain rank value
	 */
	public static int getPredatorFoodChainRank() {
		// TODO
		return DEFAULT_FOOD_CHAIN_RANK[2];
	}

	/**
	 * gets the starve time for low animals
	 * 
	 * @return starve time for animal
	 */
	public static int getPreyStarveTime() {
		return starveTime[0];
	}

	/**
	 * gets the starve time for middle animals
	 * 
	 * @return starve time for animal
	 */
	public static int getMiddleStarveTime() {
		return starveTime[1];
	}

	/**
	 * gets the starve time for high animals
	 * 
	 * @return starve time for animal
	 */
	public static int getPredatorStarveTime() {
		return starveTime[2];
	}

	/**
	 * gets breedTime for low animal
	 * 
	 * @return breedTime
	 */
	public static int getPreyBreedTime() {
		return breedTime[0];
	}

	/**
	 * gets breedTime for middle animal
	 * 
	 * @return breedTime
	 */
	public static int getMiddleBreedTime() {
		return breedTime[1];
	}

	/**
	 * gets breedTime for high animal
	 * 
	 * @return breedTime
	 */
	public static int getPredatorBreedTime() {
		return breedTime[2];
	}
}

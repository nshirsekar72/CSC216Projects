/**
 * Gives more detail then the location class
 */
package edu.ncsu.csc216.simulation.environment.utils;

import java.awt.Color;

/**
 * Subclass of location, adds color and symbols to the location in a grid
 * @author Nikhil
 *
 */
public class PaintedLocation extends Location {
	
	/**color for specified animal*/
	private Color tint;
	
	/** symbol for animal */
	private char symbol;
	
	/**
	 * constructor for painted location
	 * @param row row number
	 * @param column column number
	 * @param color color of animal
	 * @param symbol symbol for animal
	 */
	public PaintedLocation(int row, int column, Color color, char symbol) {
		super(row, column);
		this.tint = color;
		this.symbol = symbol;
	}
	
	/**
	 * gets color of animal
	 * @return color of animal
	 */
	public Color getColor(){
		return this.tint;
	}
	
	/**
	 * gets symbol of animal
	 * @return symbol of animal
	 */
	public char getSymbol() {
		return this.symbol;
	}

}

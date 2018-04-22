/**
 * tests the rest of painted location
 */
package edu.ncsu.csc216.simulation.environment.utils;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import edu.ncsu.csc216.simulation.environment.utils.PaintedLocation;

/**
 * tests the rest of painted location that could not be tested in the simulator package
 * @author Nikhil
 *
 */
public class PaintedLocationTest {
	
	/**
	 * tests getting color and symbol
	 */
	@Test
	public void testGettingColorAndSymbol() {
		PaintedLocation c = new PaintedLocation(0, 0, Color.white, 'x');
		
		assertEquals(Color.white, c.getColor());
		
		assertEquals('x', c.getSymbol());
	}

}

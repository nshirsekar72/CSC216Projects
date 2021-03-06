/**
 * test class for predator prey, the middle animal
 */
package edu.ncsu.csc216.simulation.actor;
import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.simulation.actor.Animal;
import edu.ncsu.csc216.simulation.actor.Configs;
import edu.ncsu.csc216.simulation.actor.PredatorPrey;
import edu.ncsu.csc216.simulation.actor.PurePrey;
import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.Ecosystem;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * test class for predator prey
 * @author Nikhil
 *
 */
public class PredatorPreyTest {
	
	private EcoGrid ecoGrid;
	private EcoGrid ecoGrid2;

	/**
	 * Sets up the Ecosystem for testing.
	 * 
	 * @throws Exception
	 *             if the Ecosystem can't be made
	 */
	@Before
	public void setUp() throws Exception {
		ecoGrid = new Ecosystem(3, 3);
		ecoGrid2 = new Ecosystem(3, 3);
		PredatorPrey.setRandomSeed(500);
	}
	
	/**
	 * private method that prints a string of ecoGrid
	 * @return s string of ecoGrid
	 */
	private String printX() {
		String s = "";
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				Animal x = ecoGrid.getItemAt(new Location(i, j));
				if (x != null)
					s += x.getSymbol();
				else
					s += "x";
			}
		return s;
	}
	
	/**
	 * private method that prints a string of ecoGrid2
	 * @return s string of ecoGrid2
	 */
	private String printX2() {
		String s = "";
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				Animal x = ecoGrid2.getItemAt(new Location(i, j));
				if (x != null)
					s += x.getSymbol();
				else
					s += "x";
			}
		return s;
	}
	
	/**
	 * tests the predatorPreyConstructor
	 */
	@Test
	public void testPredatorPreyConstructor() {
		Configs.setToDefaults();
		Animal predatorPrey = new PredatorPrey('x');
		assertEquals(Color.orange, predatorPrey.getColor());
	}
	
	/**
	 * tests the act method for predator prey
	 */
	@Test
	public void testPredatorPreyAct() {
		Configs.setToDefaults();
		ecoGrid.add(new PredatorPrey('A'), new Location(0, 1));
		ecoGrid.add(new PredatorPrey('A'), new Location(0, 2));
		ecoGrid.add(new PredatorPrey('A'), new Location(1, 0));
		ecoGrid.add(new PredatorPrey('A'), new Location(1, 1));
		ecoGrid.add(new PredatorPrey('A'), new Location(2, 1));

		// tests that a pure predator will eat an animal lower in food chain
		// rank due east
		ecoGrid.add(new PurePrey('P'), new Location(1, 2));
		Location testLocation1 = new Location(1, 1);
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation1).act(testLocation1, ecoGrid);

		String actual = printX();
		String expected = "xAAAxAxAx";

		assertEquals(expected, actual);

		// Eating an animal due west
		ecoGrid.add(new PurePrey('P'), new Location(2, 0));
		Location testLocation2 = new Location(2, 1);
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation2).act(testLocation2, ecoGrid);

		actual = printX();
		expected = "xAAAxAAxx";

		assertEquals(expected, actual);

		// Eating an animal due north
		ecoGrid.add(new PurePrey('P'), new Location(0, 0));
		Location testLocation3 = new Location(1, 0);
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation3).act(testLocation3, ecoGrid);

		actual = printX();
		expected = "AAAxxAAxx";

		assertEquals(expected, actual);

		// Eating animal due south
		ecoGrid.add(new PurePrey('P'), new Location(1, 2));
		Location testLocation4 = new Location(0, 2);
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation4).act(testLocation4, ecoGrid);

		actual = printX();
		expected = "AAxxxAAxx";

		assertEquals(expected, actual);
		
		
		

		// A predator will move if it cannot eat or breed
		// Using a second ecoGrid for these tests
		Animal x = new PredatorPrey('A');
		ecoGrid2.add(x, new Location(1, 1));
		ecoGrid2.add(new PredatorPrey('A'), new Location(0, 1));
		ecoGrid2.add(new PredatorPrey('A'), new Location(2, 1));
		ecoGrid2.add(new PredatorPrey('A'), new Location(1, 0));
		ecoGrid2.add(new PredatorPrey('A'), new Location(1, 2));
		Location testLocationNull = new Location(1, 0);
		ecoGrid2.enableTheLiving();
		ecoGrid2.getItemAt(testLocationNull).act(testLocationNull, ecoGrid2);

		actual = printX2();
		expected = "xAxxAAAAx";
		
		assertEquals(expected, actual);
		//assertFalse(expected.equals(actual));

		// Testing if a middle animal starves it will die
		ecoGrid2.add(new PredatorPrey('A'), new Location(1, 0));
		for (int i = 0; i < 7; i++) {
			ecoGrid2.enableTheLiving();
			ecoGrid2.getItemAt(new Location(1, 1)).act(new Location(1, 1), ecoGrid2);
		}

		if (ecoGrid.getMap() == null) {
			fail();
		}

		ecoGrid2.buryTheDead();
		
		actual = printX2();
		expected = "xAxAxAAAx";
				
		assertEquals(expected, actual);	
		
	}
	
	/**
	 * tests the breed method for predator prey
	 */
	@Test
	public void testPredatorPreyBreed() {
		Color[] colors = {Color.red, Color.blue, Color.yellow};
		int[] starveTimes = {400, 400, 400};
		int[] breedTimes = {1, 1, 1};
		Configs.initConfigs(colors, starveTimes, breedTimes);
		
		ecoGrid.add(new PredatorPrey('A'), new Location(0, 0));
		ecoGrid.add(new PredatorPrey('A'), new Location(0, 1));
		ecoGrid.add(new PredatorPrey('A'), new Location(0, 2));
		ecoGrid.add(new PredatorPrey('A'), new Location(1, 0));
		ecoGrid.add(new PredatorPrey('A'), new Location(1, 1));
		ecoGrid.add(new PredatorPrey('A'), new Location(2, 0));
		ecoGrid.add(new PredatorPrey('A'), new Location(2, 1));
		Location testLocation = new Location(1, 1);
		
		//Checks that a new baby is made after a predator can breed
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation).act(testLocation, ecoGrid);
		
		String actual = printX();
		String expected = "AAAAxAAAx";
		
		assertEquals(expected,  actual);
		
		Location testLocation2 = new Location(1, 2);
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation2).act(testLocation2, ecoGrid);
		
		actual = printX();
		expected = "AAAAAAAAx";
		
		assertEquals(expected,  actual);

		
	}

}

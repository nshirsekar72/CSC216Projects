/**
 * test class for pure prey
 */
package edu.ncsu.csc216.simulation.actor;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.simulation.actor.Animal;
import edu.ncsu.csc216.simulation.actor.Configs;
import edu.ncsu.csc216.simulation.actor.PurePrey;
import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.Ecosystem;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * test class for pure prey
 * @author Nikhil
 *
 */
public class PurePreyTest {
	
	/** ecosytem used in all test cases*/
	private EcoGrid ecoGrid;

	/**
	 * Sets up the Ecosystem for testing.
	 * 
	 * @throws Exception
	 *             if the Ecosystem can't be made
	 */
	@Before
	public void setUp() throws Exception {
		ecoGrid = new Ecosystem(3, 3);
		Configs.setToDefaults();
	}
	
	/**
	 * tests the pure prey constructor
	 */
	@Test
	public void testPurePreyConstructor() {
		Animal purePrey = new PurePrey('x');
		assertEquals(Color.green, purePrey.getColor());
	}
	
	/**
	 * tests the act method in pure prey
	 */
	@Test
	public void testPurePreyAct() {
		ecoGrid.add(new PurePrey('A'), new Location(0, 1));
		ecoGrid.add(new PurePrey('A'), new Location(0, 2));
		ecoGrid.add(new PurePrey('A'), new Location(1, 0));
		ecoGrid.add(new PurePrey('A'), new Location(1, 1));
		ecoGrid.add(new PurePrey('A'), new Location(2, 1));

		// tests that a pure prey animal will move if it cannot breed
		Location testLocation1 = new Location(1, 1);
		ecoGrid.enableTheLiving();
		ecoGrid.getItemAt(testLocation1).act(testLocation1, ecoGrid);

		String actual = printX();
		String expected = "xAAAxAxAx";

		assertEquals(expected, actual);

		// tests that pure prey animal will breed now that its moved once
		ecoGrid.add(new PurePrey('A'), new Location(1, 1));

		Location testLocation2 = new Location(1, 2);
		ecoGrid.getItemAt(testLocation2).enable();
		ecoGrid.getItemAt(testLocation2).act(testLocation2, ecoGrid);

		actual = printX();
		expected = "xAAAAAxAA";
		
		assertEquals(expected, actual);
//		
//		//Tests that nothing happens if act is called on a null index
//		Location emptyLocation = new Location(0,0);
//		ecoGrid.getItemAt(emptyLocation).act(emptyLocation, ecoGrid);
//		
//		assertEquals(expected, actual);

	}
	
	/**
	 * private method that prints a string ecosystem
	 * @return s string ecosystem
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

}

/**
 * test class for te rest of ecosystem
 */
package edu.ncsu.csc216.simulation.environment;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.simulation.actor.PurePredator;
import edu.ncsu.csc216.simulation.environment.utils.Location;

/**
 * tests the rest of ecosytem that could not be tested in the actor package
 * @author Nikhil
 *
 */
public class EcosystemTest {
	
	/**
	 * tests the remaining case in findFirstEmptyNeighbor
	 */
	@Test
	public void testfindFirstEmptyNeighbor() {
		EcoGrid x = new Ecosystem(3, 3);
		x.add(new PurePredator('A'), new Location(1, 1));
		x.add(new PurePredator('A'), new Location(0, 1));
		x.add(new PurePredator('A'), new Location(2, 1));
		x.add(new PurePredator('A'), new Location(1, 0));
		x.add(new PurePredator('A'), new Location(1, 2));
		Location testLocation = new Location(1, 1);
		
		assertNull(x.findFirstEmptyNeighbor(testLocation, 2));
		
	}

}

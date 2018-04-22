/**
 * test class for automa simulator
 */
package edu.ncsu.csc216.simulation.simulator;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import edu.ncsu.csc216.simulation.actor.Configs;
import edu.ncsu.csc216.simulation.environment.utils.PaintedLocation;
import edu.ncsu.csc216.simulation.simulator.AutomataSimulator;

/**
 * Test class for automata simulator
 * @author Nikhil
 *
 */
public class AutomataSimulatorTest {

	/** Valid init File */
	private final String validTestInitFile = "test-files/eco-system-1.txt";
	/** Valid confFile */
	private final String validConfFile = "test-files/config-1.txt";
	/** Invalid init File (missing Row in Grid) */
	private final String invalidInitFile = "test-files/eco-system-2.txt";
	
	/**
	 * will test the constructor without conf file
	 */
	@Test
	public void testConstructorWithoutConfFile() {
		
		//Tests valid init file
		AutomataSimulator x = new AutomataSimulator(validTestInitFile);
		String[] names = x.getNames();
		
		assertEquals("O: Great Gray Owl", names[0]);
		assertEquals("I: Insect", names[names.length - 1]);
		assertEquals("F: Frog", names[2]);
		assertEquals("M: Mouse", names[1]);
		
		//Test for invalid file (missing row)
		AutomataSimulator i = null;
		try {
		i = new AutomataSimulator(invalidInitFile);
		fail();
		} catch (IllegalArgumentException e) {
			assertNull(i);
		}
		
		//Test for invalid file (extra row)
		AutomataSimulator e = null;
		try {
		e = new AutomataSimulator("test-files/eco-system-3.txt");
		fail();
		} catch (IllegalArgumentException r) {
			assertNull(e);
		}
				
		//Test getView();
		x.getView();
		PaintedLocation[][] p = x.getView();
		assertEquals(Color.black, p[0][0].getColor());
		
		//Test Step 
		x.step();
		
		
		
	}
	
	/**
	 * will test the constructor with conf file
	 */
	@Test
	public void testConstructorWithConfFile() {
		AutomataSimulator r = new AutomataSimulator(validTestInitFile, validConfFile);

		// Check that all names are correct
		String[] names = r.getNames();
		assertEquals("O: Great Gray Owl", names[0]);
		assertEquals("I: Insect", names[names.length - 1]);
		assertEquals("F: Frog", names[2]);
		assertEquals("M: Mouse", names[1]);

		// Check that custom colors are correct
		assertEquals(Color.red, Configs.getPreyColor());
		assertEquals(Color.green, Configs.getMiddleColor());
		assertEquals(Color.blue, Configs.getPredatorColor());

		// check that custom starve times are correct
		assertEquals(8, Configs.getPreyStarveTime());
		assertEquals(7, Configs.getMiddleStarveTime());
		assertEquals(4, Configs.getPredatorStarveTime());

		// Check that custom breed times are correct
		assertEquals(2, Configs.getPreyBreedTime());
		assertEquals(6, Configs.getMiddleBreedTime());
		assertEquals(10, Configs.getPredatorBreedTime());

	}

}

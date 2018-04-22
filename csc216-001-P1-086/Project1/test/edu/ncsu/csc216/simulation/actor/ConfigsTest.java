/**
 * test file for configs
 */
package edu.ncsu.csc216.simulation.actor;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import edu.ncsu.csc216.simulation.actor.Configs;

/**
 * test class for configs
 * @author Nikhil
 *
 */
public class ConfigsTest {
	
	/**
	 * tests usage of initconfigs
	 */
	@Test
	public void testInitConfigs() {
		Color[] c = { Color.cyan, Color.black, Color.red };
		int[] s = { 10, 43, 72 };
		int[] b = { 234, 2456, 435 };

		Configs.initConfigs(c, s, b);

		// Checking colors match up
		assertEquals(Color.cyan, Configs.getPreyColor());
		assertEquals(Color.black, Configs.getMiddleColor());
		assertEquals(Color.red, Configs.getPredatorColor()); 

		// Checking starve times match up
		assertEquals(10, Configs.getPreyStarveTime());
		assertEquals(43, Configs.getMiddleStarveTime());
		assertEquals(72, Configs.getPredatorStarveTime());

		// Checking breed times match up
		assertEquals(234, Configs.getPreyBreedTime());
		assertEquals(2456, Configs.getMiddleBreedTime());
		assertEquals(435, Configs.getPredatorBreedTime());
	}
	
	/**
	 * tests setting configs to defaults
	 */
	@Test
	public void testSetDefaults() {
		Configs.setToDefaults();

		// Checking colors match up
		assertEquals(Color.green, Configs.getPreyColor());
		assertEquals(Color.orange, Configs.getMiddleColor());
		assertEquals(Color.red, Configs.getPredatorColor());

		// Checking starve times match up
		assertEquals(10, Configs.getPreyStarveTime());
		assertEquals(6, Configs.getMiddleStarveTime());
		assertEquals(5, Configs.getPredatorStarveTime());

		// Checking breed times match up
		assertEquals(1, Configs.getPreyBreedTime());
		assertEquals(7, Configs.getMiddleBreedTime());
		assertEquals(15, Configs.getPredatorBreedTime());
	}

}

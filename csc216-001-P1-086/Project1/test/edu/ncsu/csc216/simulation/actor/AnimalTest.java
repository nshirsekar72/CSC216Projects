/**
 * Test class for animal
 */
package edu.ncsu.csc216.simulation.actor;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.simulation.actor.Animal;
import edu.ncsu.csc216.simulation.actor.PredatorPrey;
import edu.ncsu.csc216.simulation.actor.PurePredator;
import edu.ncsu.csc216.simulation.actor.PurePrey;


/**
 * Tests the animal superclass's public concrete methods
 * @author Nikhil
 *
 */
public class AnimalTest {

	/**
	 * tests the animal constructor with each of its subclasses
	 */
	@Test
	public void constructorTests() {
		
		//Constructing a PurePrey Animal
		Animal purePrey = new PurePrey('Z');
		assertEquals('Z', purePrey.getSymbol());
		
		//Constructing a PredatorPrey Animal
		Animal predatorPrey = new PredatorPrey('G');
		assertEquals('G', predatorPrey.getSymbol());
		
		//Constructing a PurePrey Animal
		Animal purePredator = new PurePredator('X');
		assertEquals('X', purePredator.getSymbol());
		
	}
	
	/**
	 * tests the isAlive method
	 */
	@Test
	public void isAliveTest() {
		
		//Testing isAlive when constructing an animal
		Animal a = new PurePredator('X');
		assertTrue(a.isAlive());
	}

}

package edu.ncsu.csc216.simulation.simulator;

import edu.ncsu.csc216.simulation.environment.utils.PaintedLocation;

/**
 * Method that help run the simulation
 * @author Nikhil Shirsekar
 *
 */
public interface SimulatorInterface {
	
	/**
	 * simulates a step in teh ecosystem
	 */
	public void step();
	
	/**
	 * gets the colored grid of animal in the ecosystem
	 * @return colored grid of animals in the ecosystem
	 */
	public PaintedLocation[][] getView();
	
	/**
	 * gets array of animal names
	 * @return 1D array of animal names
	 */
	public String[] getNames();
}

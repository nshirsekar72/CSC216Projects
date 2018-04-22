package edu.ncsu.csc216.simulation.simulator;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.simulation.actor.Animal;
import edu.ncsu.csc216.simulation.actor.Configs;
import edu.ncsu.csc216.simulation.actor.PredatorPrey;
import edu.ncsu.csc216.simulation.actor.PurePredator;
import edu.ncsu.csc216.simulation.actor.PurePrey;
import edu.ncsu.csc216.simulation.environment.EcoGrid;
import edu.ncsu.csc216.simulation.environment.Ecosystem;
import edu.ncsu.csc216.simulation.environment.utils.Location;
import edu.ncsu.csc216.simulation.environment.utils.PaintedLocation;

/**
 * Main class that runs the simulation steps and reads in files
 * 
 * @author Nikhil
 *
 */
public class AutomataSimulator implements SimulatorInterface {

	/** Length of one side of the ecosystem grid */
	private static final int SIZE = 20;

	/** Max threshold amount */
	private static final int THRESHOLD = 2;

	/** error message displayed if there is a size error */
	private static final String SIZE_ERROR_MESSAGE = "Invalid Size";

	/** error message displayed if there is a threshold error */
	private static final String THRESHOLD_ERROR_MESSAGE = "Threshold error";

	/** character for an empty spot in the grid */
	private static final char EMPTY = '.';

	/** Allows for the functionality of ecogrids methods */
	private EcoGrid simpleSystem;

	/** array that holds the names of the animals in the ecosystem */
	private String[] names;

	/** number of names of animals in the ecosystem */
	private int numberOfNames;

	/** Array of the symbols for the animals in the ecosystem */
	private char[] symbol;

	/**
	 * constructor for the simulator with only a initial main file
	 * 
	 * @param init
	 *            main file name
	 */
	public AutomataSimulator(String init) {
		Configs.setToDefaults();
		Scanner fileReader = null;
		Scanner lineReader = null;
		this.simpleSystem = new Ecosystem(SIZE, SIZE);
		
		// Call to private method that reads the init file
		initReader(fileReader, lineReader, init);

	}

	/**
	 * Constructor with main init file along with optional configuration file
	 * 
	 * @param init
	 *            main file used to create the ecosystem
	 * @param conf
	 *            secondary optional file for custom configurations
	 */
	public AutomataSimulator(String init, String conf) {
		Scanner fileReader = null;
		Scanner lineReader = null;
		this.simpleSystem = new Ecosystem(SIZE, SIZE);

		// Call to private method that read the init file
		initReader(fileReader, lineReader, init);

		// This section will read the optional conf file.
		// If there are any errors in this file it will set the configurations
		// to defaults
		Color[] colors = new Color[3];
		int[] starveTimes = new int[3];
		int[] breedTimes = new int[3];
		Scanner confFileReader = null;
		Scanner confLineReader = null;

		try {
			File confFile = new File(conf);
			confFileReader = new Scanner(confFile);

		} catch (FileNotFoundException e) {
			Configs.setToDefaults();

		}

		try {
			// This loop will fill the colors array with the custom hex
			// colors created by user
			confLineReader = new Scanner(confFileReader.nextLine());

			for (int i = 0; i < colors.length; i++) {
				String hexColor = confLineReader.next().trim();
				colors[i] = Color.decode("#" + hexColor);
			}
			confLineReader.close();
			// This loop will fill the starveTimes array with custom values
			confLineReader = new Scanner(confFileReader.nextLine());
			for (int i = 0; i < starveTimes.length; i++) {
				starveTimes[i] = confLineReader.nextInt();
			}

			confLineReader.close();
			// This loop will fill the breedTimes array with custom values
			confLineReader = new Scanner(confFileReader.nextLine());
			for (int i = 0; i < breedTimes.length; i++) {
				breedTimes[i] = confLineReader.nextInt();
			}

			Configs.initConfigs(colors, starveTimes, breedTimes);

			confFileReader.close();
			confLineReader.close();
		} catch (IllegalArgumentException e) {
			Configs.setToDefaults();
		} 

	}

	/**
	 * carries out a single step for the ecosystem
	 */
	@Override
	public void step() {
		simpleSystem.enableTheLiving();

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

				Location animalLocation = new Location(i, j);

				if (!simpleSystem.isEmpty(animalLocation)) {
					simpleSystem.getItemAt(animalLocation).act(animalLocation, simpleSystem);
				}
			}
		}

		simpleSystem.buryTheDead();
	}

	/**
	 * gets the grid that the ecosystem is living on
	 * 
	 * @return full painted grid of the ecosystem
	 */
	@Override
	public PaintedLocation[][] getView() {
		PaintedLocation[][] paintedGrid = new PaintedLocation[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Location locationToBePainted = new Location(i, j);

				// Checks for empty location
				if (simpleSystem.isEmpty(locationToBePainted)) {
					paintedGrid[i][j] = new PaintedLocation(i, j, Color.black, ' ');

					// Checks for prey
				} else if (simpleSystem.getItemAt(locationToBePainted).getSymbol() == symbol[0]) {
					paintedGrid[i][j] = new PaintedLocation(i, j, Configs.getPreyColor(),
							simpleSystem.getItemAt(locationToBePainted).getSymbol());

					// Checks for Predators
				} else if (simpleSystem.getItemAt(locationToBePainted).getSymbol() == symbol[symbol.length - 1]) {
					paintedGrid[i][j] = new PaintedLocation(i, j, Configs.getPredatorColor(),
							symbol[symbol.length - 1]);

					// checks for middle
				} else if (simpleSystem.getItemAt(locationToBePainted).getSymbol() != symbol[0]
						&& simpleSystem.getItemAt(locationToBePainted).getSymbol() != symbol[symbol.length - 1]) {
					paintedGrid[i][j] = new PaintedLocation(i, j, Configs.getMiddleColor(),
							simpleSystem.getItemAt(locationToBePainted).getSymbol());
				}
			}
		}

		return paintedGrid;
	}

	/**
	 * gets the array of names for the animals in the ecosystem
	 * 
	 * @return array of animal names
	 */
	@Override
	public String[] getNames() {
		return names;
	}

	/**
	 * reads the init file for the simulation 
	 * 
	 * @param fileReader
	 *            scanner that loads entire file
	 * @param lineReader
	 *            scanner that is fed each line
	 * @param init
	 *            name of the initialization file
	 */
	private void initReader(Scanner fileReader, Scanner lineReader, String init) {
		try {

			File fileToLoad = new File(init);
			fileReader = new Scanner(fileToLoad);

		} catch (FileNotFoundException e) {

			throw new IllegalArgumentException("Cannot use File");
		}

		try {
			// This will setup the arrays to hold the names and symbols of the
			// animals
			String nextLine = fileReader.nextLine();
			lineReader = new Scanner(nextLine);
			this.numberOfNames = lineReader.nextInt();
			
			if (this.numberOfNames < THRESHOLD) {
				fileReader.close();
				lineReader.close();
				throw new IllegalArgumentException(THRESHOLD_ERROR_MESSAGE);
			}
				
			this.names = new String[numberOfNames];
			this.symbol = new char[numberOfNames];
			String[] initialStorageOfNames = new String[numberOfNames];
			// This for loop should take care of filling out the name and symbol
			// arrays
			for (int i = 0; i < numberOfNames; i++) {
				//fileReader.next(); // Trash
				nextLine = fileReader.nextLine();
				lineReader = new Scanner(nextLine);

				symbol[symbol.length - (i + 1)] = nextLine.trim().charAt(0);
				initialStorageOfNames[i] = nextLine.substring(1, nextLine.length()).trim();

			}
			
			for ( int i = 0; i < numberOfNames; i++) {
				names[i] = symbol[symbol.length - (i + 1)] + ":" + " " + initialStorageOfNames[i];
			}

			// This last section will read in the ecosystem grid from the file
			String gridLine = null;
			char gridSymbol = ' ';

			for (int i = 0; i < SIZE; i++) {
				gridLine = fileReader.nextLine();
				for (int j = 0; j < SIZE; j++) {
					

					if (gridLine.length() > SIZE) {
						fileReader.close();
						lineReader.close();
						throw new IllegalArgumentException(SIZE_ERROR_MESSAGE);
					}
					String symbols = null;
					gridSymbol = gridLine.charAt(j);
					
					for (int k = 0; k < symbol.length; k++) {
						symbols = symbols + symbol[k];
					}
					
					symbols = symbols + '.';
					
					if (!symbols.contains(gridSymbol + "")) {
						fileReader.close();
						lineReader.close();
						throw new IllegalArgumentException("Illegal Character");
					}
					
					if (gridSymbol == ' ') {
						fileReader.close();
						lineReader.close();
						throw new IllegalArgumentException("Invalid Symbol");
					}

					if (gridSymbol == symbol[0]) {
						Animal purePrey = new PurePrey(symbol[0]);
						simpleSystem.add(purePrey, new Location(i, j));

					} else if ( gridSymbol == EMPTY) {
						simpleSystem.add(null, new Location(i, j));

					} else if (gridSymbol == symbol[symbol.length - 1]) {
						Animal purePredator = new PurePredator(symbol[symbol.length - 1]);
						simpleSystem.add(purePredator, new Location(i, j));

					} else if (gridSymbol != symbol[0] && gridSymbol != symbol[symbol.length - 1]
							&& gridSymbol != EMPTY) {
						Animal predatorPrey = new PredatorPrey(gridLine.charAt(j));
						simpleSystem.add(predatorPrey, new Location(i, j));
					}	
					
				}
			}
			
			//check to make sure ecosystem in file is does not have extra rows
			if (fileReader.hasNextLine()) {
				fileReader.close();
				lineReader.close();
				throw new IllegalArgumentException("Grid has more than 20 rows");
			}

		} catch (IllegalArgumentException e) {
			fileReader.close();
			lineReader.close();
			throw new IllegalArgumentException("Invalid File");
		} catch (InputMismatchException e) {
			fileReader.close();
			lineReader.close();
			throw new IllegalArgumentException("Intputs do not match up, Invalid File");
		} catch (NoSuchElementException e) {
			fileReader.close();
			lineReader.close();
			throw new IllegalArgumentException("Not enough grid lines in ecosystem, Invalid File");
		} catch (StringIndexOutOfBoundsException e) {
			fileReader.close();
			lineReader.close();
			throw new IllegalArgumentException("Not enough grid lines in ecosystem, Invalid File");
		}
		fileReader.close();
		lineReader.close();
	}

}

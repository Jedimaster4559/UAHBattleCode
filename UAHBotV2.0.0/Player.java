import bc.*;
import java.util.ArrayList;
import java.lang.Long;
import java.util.Random;

/**
 * Class that contains the main processing for the entire bot.
 * This includes the main method of the bot.
 *
 */
public class Player {
	

	//create all variable that might be used throughout run
	static Random rand;			//Random to be used when needed
	static Direction[] directions;		//Array of all directions (this can be improved with an array without Direction.Center)
	static GameController gc;		//the game controller so it is more available to us
	
	//counters for all units
	static int numFactories;
	static int numWorkers;
	static int numKnights;
	static int numMages;
	static int numRangers;
	static int numHealers;
	static int numRockets;
  
	//Set some strategy goals
	static final int highFactoryGoal = 3;
	static final int lowFactoryGoal = 8;
	static int highKarboniteGoal = 300;
	static int lowKarboniteGoal = 50;
	static boolean initRocketBuilt = false;
	
	static final int stage = 300;
	
	// set variables to be used for karboniteGoal
	
	// this multiplier helps ensure there is additional karbonite to use for unit production in the late game
	static final double kgMultiplier = 1.2;			
	// the literal production cost of karbonite, taken from the battlecode game specs
	static final int rocketCost = (int) bc.bcUnitTypeBlueprintCost(UnitType.Rocket);
        
	//Set some strategy goals
	static int rocketGoal = 1;
	

	//a VecUnit of all of our units (this may no longer be necessary)
	static VecUnit units;						
	static boolean peaceful = false;	//Peaceful toggle for bot (TESTING ONLY)
	
	//List of all of our units
	public static ArrayList<UAHUnit> UAHUnits = new ArrayList<UAHUnit>();	
	//list of all units created this turn
	public static ArrayList<UAHUnit> newUnits = new ArrayList<UAHUnit>();	
	//list of all units that died this turn
	public static ArrayList<UAHUnit> deadUnits = new ArrayList<UAHUnit>();	

	public static ArrayList<KarboniteLocation> karboniteLocations = new ArrayList<KarboniteLocation>();	//List of all KarboniteLocations

	static long startTime = 0;
	static long endTime = 0;

	/**
	 * Main method for the bot that runs the entire bot
	 */
	public static void main(String[] args) {
	
		//Add a Game Controller
		gc = new GameController();
		
		//Seed Randomizer for debugging purposes
		rand = new Random();
		//rand.setSeed(1337);

		//Grab the enemy team locations
		Utilities.findEnemyTeam(gc);

		//Create and Array of all Directions a bot can travel (Maybe change to array without Direction.Center?)
		directions = Direction.values();
		
		//initialize logic handler
		LogicHandler.initialize(gc);

		//get a list of all our units at game start
		units = gc.myUnits();

		//add all initial units to units ArrayList
		for (int i = 0; i < units.size(); i++) {
			UAHUnits.add(new Worker(units.get(i), gc));
		}
		
		
		//loop through all units and process their turn
		while (true){
			//check for dead units and new mars rockets
			
			
			//Mars logic
			if(gc.planet() == Planet.Mars){
				//find rockets when they land and at them to units list
				Utilities.verifyList(gc);
				
				//Process Logic
				LogicHandler.process(gc);
				
				//try to run all units this turn
				try {
					for (UAHUnit unit : UAHUnits) {		//Loop through all of our units
						if (unit.isAlive()) {
							unit.preProcess();	//Preprocess the unit (determines if the bot still exists)
							unit.process();		//Allow the unit to process its turn
						}
						
					}
					
					//process any unit changes from this turn
					UAHUnits.removeAll(deadUnits);
					UAHUnits.addAll(newUnits);
					newUnits.clear();
					deadUnits.clear();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//earth logic
			else{
				
				if (gc.round() >= 749) {
					UAHUnits.clear();
					deadUnits.clear();
					newUnits.clear();
					gc.nextTurn();
					continue;
				}
				
				Utilities.verifyList(gc);
				
				//Process Logic
				LogicHandler.process(gc);

				//try to run all units this turn
				try {
					
					for (UAHUnit unit : UAHUnits) {		//Loop through all of our units
						if (unit.isAlive()) {
							unit.preProcess();	//Preprocess the unit
							unit.process();		//process the unit's actions
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//add all unit changes to main list
				UAHUnits.addAll(newUnits);
				UAHUnits.removeAll(deadUnits);
				
				//clear unit changes
				newUnits.clear();
				deadUnits.clear();
				
			}
			
			//end the turn
			gc.nextTurn();
        }
		
	}

}

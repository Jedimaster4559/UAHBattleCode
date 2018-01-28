import bc.*;
import java.util.ArrayList;
import java.lang.Long;
import java.util.Random;

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
				
				if (gc.round() % 10 == 0) {
					long startTime = System.nanoTime();
				}
				Utilities.verifyList(gc);
				
				//Process Logic
				LogicHandler.process(gc);
				
				if (gc.round() % 10 == 0) {
					long endTime = System.nanoTime();
					
					System.out.println("initial turn processing time:" + (endTime - startTime));
				}
				
				UAHUnits.addAll(newUnits);
				deadUnits.clear();
				newUnits.clear();
				
				//try to run all units this turn
				try {
					if (gc.round() % 10 == 0) {
						startTime = System.nanoTime();
					}
					
					for (UAHUnit unit : UAHUnits) {		//Loop through all of our units
						if (unit.isAlive()) {
							//System.out.println(unit.getUnit().unitType() + " start time left:" + gc.getTimeLeftMs());
							unit.preProcess();	//Preprocess the unit (determines if the bot still exists)
							unit.process();		//Allow the unit to process its turn
							//System.out.println(unit.getUnit().unitType() + "   end time left:" + gc.getTimeLeftMs());
						}
						
					}
					
					if (gc.round() % 10 == 0) {
						endTime = System.nanoTime();
						System.out.println("Unit loop processing time:" + (endTime - startTime));
					}
					
					//process any unit changes from this turn
					UAHUnits.removeAll(deadUnits);
					UAHUnits.addAll(newUnits);
					
					for (UAHUnit unit : newUnits) {
						unit.preProcess();
						unit.process();
					}
					//clear changes to be used next turn
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
				
				if (gc.round() % 10 == 0) {
					long startTime = System.nanoTime();
				}
				
				Utilities.verifyList(gc);
				
				//Process Logic
				LogicHandler.process(gc);
				
				if (gc.round() % 10 == 0) {
					long endTime = System.nanoTime();
					
					System.out.println("initial turn processing time:" + (endTime - startTime));
				}

				//try to run all units this turn
				try {
					
					for (UAHUnit unit : UAHUnits) {		//Loop through all of our units
						if (unit.isAlive()) {
							//System.out.println("Running: " + unit.getUnit().unitType());
							unit.preProcess();	//Preprocess the unit
							unit.process();		//process the unit's actions
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println(UAHUnits.size());
				//System.out.println(newUnits.size());
				//add all unit changes to main list
				UAHUnits.addAll(newUnits);
				UAHUnits.removeAll(deadUnits);
				
				//run any new units
				/*for (UAHUnit unit : newUnits) {
					unit.preProcess();
					unit.process();
				}*/
				
				//clear unit changes
				newUnits.clear();
				deadUnits.clear();
				
			}
			
			//proceed to next turn
			if (gc.round() % 10 == 0) {
				System.out.println(gc.round() + ":" + gc.getTimeLeftMs());
			}
			gc.nextTurn();
        }
		
	}

}

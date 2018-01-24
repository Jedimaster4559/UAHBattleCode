import bc.*;
import java.util.ArrayList;
import java.lang.Long;
import java.util.Random;

public class Player {
	
	//create all variable that might be used throughout run
	static Random rand;
	static Direction[] directions;
	static GameController gc;
	static int numFactories;
	static int numWorkers;
	static int numKnights;
	static int numMages;
	static int numRangers;
	static int numHealers;
	static int numRockets;
	static int factoryGoal = 5;
	static int rocketGoal = 2;
	static VecUnit units;
	static boolean peaceful = false;
	static ArrayList<UAHUnit> UAHUnits = new ArrayList<UAHUnit>();
	static ArrayList<UAHUnit> newUnits = new ArrayList<UAHUnit>();
	static ArrayList<UAHUnit> deadUnits = new ArrayList<UAHUnit>();

		
	public static void main(String[] args) {
	
		//Add a Game Controller
		gc = new GameController();
		
		//Seed Randomizer for debugging purposes
		rand = new Random();
		//rand.setSeed(1337);
		
		//Grab the enemy team locations
		Utilities.findEnemyTeam(gc);
		
		//Create and Array of all Directions a bot can travel
		directions = Direction.values();
		
		//initialize logic handler
		LogicHandler.initialize(gc);

		//get a list of all our units at game start
		units = gc.myUnits();
		
		//add all inital units to units ArrayList
		for (int i = 0; i < units.size(); i++) {
			UAHUnits.add(new Worker(units.get(i), gc));
		}
		
		//loop through all units and process their turn
		while (true){
			if(gc.planet() == Planet.Mars && gc.round() > 700){
				//find rockets when they land and at them to units list
				Utilities.verifyList(gc);
				UAHUnits.addAll(newUnits);
				deadUnits.clear();
				
				//try to run all units this turn
				try {
					for (UAHUnit unit : UAHUnits) {
						if (unit.isAlive()) {
							System.out.println("Running: " + unit.getUnit().unitType());
							unit.preProcess();
							unit.process();
						}
						
					}
					
					//process any unit changes from this turn
					UAHUnits.removeAll(deadUnits);
					UAHUnits.addAll(newUnits);
					
					//clear changes to be used next turn
					newUnits.clear();
					deadUnits.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				//Process Logic
				LogicHandler.process(gc);
				
				//try to run all units this turn
				try {
					for (UAHUnit unit : UAHUnits) {
						if (unit.isAlive()) {
							unit.preProcess();
							unit.process();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//add all unit changes to main list
				UAHUnits.addAll(newUnits);
				UAHUnits.removeAll(deadUnits);
				
				//run any new units
				for (UAHUnit unit : newUnits) {
					unit.process();
				}
				
				//clear unit changes
				newUnits.clear();
				deadUnits.clear();
				
			}
			
			//proceed to next turn
			gc.nextTurn();
        }
		
	}
}

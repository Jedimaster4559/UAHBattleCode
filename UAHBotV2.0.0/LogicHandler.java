import bc.*;
import java.util.*;

class LogicHandler {
	
	//Are we trying to escape earth to mars?
	static boolean escaping;	
	
	//List of all rockets so this info is publicly available
	static ArrayList<UAHUnit> rockets = new ArrayList<UAHUnit>();	
	
	//Total numbers of factories we are willing to build
	static int factoryGoal = 10;	
	
	public static void initialize(GameController gc) {		
		//initialize Pathing
		Path.initializePathing(gc);
		
		//initialize escape mode (starts as no since we can't anyways)
		escaping = false;
		
		//initialize researching
		gc.queueResearch(UnitType.Worker);
		gc.queueResearch(UnitType.Knight);
		gc.queueResearch(UnitType.Rocket);
		gc.queueResearch(UnitType.Worker);
		gc.queueResearch(UnitType.Knight);
		gc.queueResearch(UnitType.Worker);
		gc.queueResearch(UnitType.Knight);
		gc.queueResearch(UnitType.Worker);
		
	}
	
	public static void process(GameController gc) {
		//initialize count of all units
		Utilities.countUnits(gc.myUnits());
		
		//determine whether we are needing to escape or not
		if(!escaping && gc.round() >= 700) {
			startEscaping(gc);
		} else if (escaping) {
			getRocketLocations(gc);
		}
	}
	
	public static void startEscaping(GameController gc) {
		//set escaping to true
		escaping = true;
		
		//set variable with all rocket locations
		getRocketLocations(gc);
	}


	public static void getRockets(GameController gc) {
		//clears the rocket array
		rockets.clear();						
		
		//loop through all units and add all rockets to the arraylist of Rockets
		for(int i = 0; i < Player.UAHUnits.size(); i++) {		
			UAHUnit rocket = Player.UAHUnits.get(i);
			if(rocket.getUnit().unitType() == UnitType.Rocket &&
					rocket.getUnit().location().isOnMap())
			{
				rockets.add(rocket);


			}
		}
	}
	
	public static void calculateKarboniteGoals(GameController gc) {
		if (gc.round() < Player.stage) {
			Player.highKarboniteGoal = 50;
			Player.lowKarboniteGoal = 300;
			
		} else {
			Player.highKarboniteGoal = 200;
			Player.lowKarboniteGoal = (int)(Player.rocketGoal * Player.rocketCost * Player.kgMultiplier);
		}
	}
	
	public static void calculateRocketGoal(GameController gc, int numUnits) {
		getRockets();
		Player.rocketGoal = Math.ceil((double)numUnits/8) - rockets.size();
	}
}

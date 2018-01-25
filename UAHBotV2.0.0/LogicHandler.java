import bc.*;
import java.util.*;

class LogicHandler {
	
	static boolean escaping;					//Are we trying to escape earth to mars?
	static ArrayList<UAHUnit> rockets = new ArrayList<UAHUnit>();	//List of all rockets so this info is publicly available
	static int factoryGoal = 10;					//Total numbers of factories we are willing to build
	
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
	

	public static void getRocketLocations(GameController gc) {
		rockets.clear();						//clears the rocket array (not sure why we need to do this since it should be empty)
		for(int i = 0; i < Player.UAHUnits.size(); i++) {		//loop through all units and add all rockets to the arraylist of Rockets
			UAHUnit rocket = Player.UAHUnits.get(i);
			if(rocket.getUnit().unitType() == UnitType.Rocket &&
					rocket.getUnit().location().isOnMap())
			{
				rockets.add(rocket);


			}
		}
	}
}

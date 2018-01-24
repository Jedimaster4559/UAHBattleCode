import bc.*;
import java.util.*;

class LogicHandler {
	static boolean escaping;
	static ArrayList<UAHUnit> rockets = new ArrayList<UAHUnit>();
	static int factoryGoal = 10;
	
	public static void initialize(GameController gc) {


		//initialize Pathing
		Path.initializePathing(gc);
		
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
		rockets.clear();
		for(int i = 0; i < Player.UAHUnits.size(); i++) {
			UAHUnit rocket = Player.UAHUnits.get(i);
			if(rocket.getUnit().unitType() == UnitType.Rocket &&
					rocket.getUnit().location().isOnMap())
			{
				rockets.add(rocket);


			}
		}
	}
}
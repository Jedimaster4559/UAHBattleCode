import bc.*;
import java.util.*;

class LogicHandler {
	static boolean escaping;
	static Unit[] rockets;
	
	public static void initialize(GameController gc){
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
		
		//initialize production type
		Worker.productionType = UnitType.Factory;
	}
	
	public static void process(GameController gc){
		//initialize count of all units
		Utilities.countUnits(Player.units);
		
		if(gc.round() == 720){
			startEscaping(gc);
		}
		if(gc.round() > 150 && Player.numFactories >= 3){
			Worker.productionType = UnitType.Rocket;
		}
	}
	
	public static void startEscaping(GameController gc){
		//set escaping to true
		escaping = true;
		
		//set variable with all rocket locations
		getRocketLocations(gc);
	}
	
	public static void getRocketLocations(GameController gc){
		rockets = new Unit[Player.numRockets];
		int i = 0;
		for(long j = 0; j < Player.units.size(); j++){
			if(Player.units.get(j).unitType() == UnitType.Rocket){
				rockets[i] = Player.units.get(j);
				i++;
			}
		}
	}
}
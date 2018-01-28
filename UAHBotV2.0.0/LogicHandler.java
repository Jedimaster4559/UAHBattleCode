import bc.*;
import java.util.*;

class LogicHandler {

	static boolean escaping;					//Are we trying to escape earth to mars?
	static ArrayList<UAHUnit> rockets = new ArrayList<UAHUnit>();	//List of all rockets so this info is publicly available
	static int factoryGoal = 10;					//Total numbers of factories we are willing to build
	static ArrayList<KarboniteLocation> usedDeposit = new ArrayList<KarboniteLocation>();
	
	public static void initialize(GameController gc) {		
		//initialize Pathing
		Path.initializePathing(gc);
		
		initializeKarboniteLocations(gc);
		
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
		
		if (gc.planet() == Planet.Earth) {
			//determine whether we are needing to escape or not
			if(!escaping && gc.round() >= 700) {
				startEscaping(gc);
			} else if (escaping) {
				getRockets(gc);
			}
			
			calculateKarboniteGoals(gc);
			calculateRocketGoal(gc, (int)gc.myUnits().size());
		}
		
		if(gc.round() % 50 == 0){
			checkKarbonite(gc);
		}
	}
	
	public static void startEscaping(GameController gc) {
		//set escaping to true
		escaping = true;
		
		//set variable with all rocket locations
		getRockets(gc);
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
		getRockets(gc);
		Player.rocketGoal = (int) Math.ceil((double)numUnits/8) - rockets.size();

	}
	public static void initializeKarboniteLocations(GameController gc) {
		if(gc.planet() == Planet.Earth){
			VecMapLocation allLocations = gc.allLocationsWithin(new MapLocation(Planet.Earth,0,0), 5001);
			for(long i = 0; i < allLocations.size(); i++) {
				if(Path.earth.initialKarboniteAt(allLocations.get(i)) > 0) {
					Player.karboniteLocations.add(new KarboniteLocation(allLocations.get(i), gc));
				}
			}
		} else {
			
		}
	}
	
	public static void checkKarbonite(GameController gc){
		for(KarboniteLocation location:Player.karboniteLocations){
			try{
				long karbonite = gc.karboniteAt(location.getMapLocation());
				if(karbonite <= 0){
					usedDeposit.add(location);
				} else {
					location.setKarbonite(karbonite);
				}
			}
			catch(Exception e){
				
			}
		}
		Player.karboniteLocations.removeAll(usedDeposit);
		usedDeposit.clear();

	}
}

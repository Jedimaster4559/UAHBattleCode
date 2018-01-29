import bc.*;
import java.util.*;
import java.lang.Math;

class LogicHandler {

	static boolean escaping;					//Are we trying to escape earth to mars?
	static ArrayList<UAHUnit> rockets = new ArrayList<UAHUnit>();	//List of all rockets so this info is publicly available
	static int factoryGoal = 10;					//Total numbers of factories we are willing to build
	static ArrayList<KarboniteLocation> usedDeposit = new ArrayList<KarboniteLocation>();
	static int marsLanding = 0;
	static int combatGoal;
	static int lastTurn = 100;
	
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
		gc.queueResearch(UnitType.Knight);
		gc.queueResearch(UnitType.Ranger);
		gc.queueResearch(UnitType.Mage);
		gc.queueResearch(UnitType.Ranger);
		gc.queueResearch(UnitType.Knight);
		gc.queueResearch(UnitType.Mage);
		gc.queueResearch(UnitType.Rocket);
		
		combatGoal = (int)Math.sqrt((double)Path.earthMapHeight * Path.earthMapWidth);
		
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
			
			int combatUnits = Player.numKnights + Player.numRangers + Player.numMages;
			
			if(gc.round() - lastTurn >= 100 && combatUnits >= combatGoal){
				for(UAHUnit unit:Player.UAHUnits){
					UnitType type = unit.getUnit().unitType();
					if(type == UnitType.Knight || type == UnitType.Mage || type == UnitType.Ranger){
						MobileUnit thisUnit = (MobileUnit)unit;
						thisUnit.setEnemy(true);
					}
				}
				lastTurn = (int)gc.round();
			}
			
			calculateKarboniteGoals(gc);
			calculateRocketGoal(gc, (int)gc.myUnits().size());
		} else {
			if(gc.round() == 750){
				marsLanding = (int)gc.currentDurationOfFlight() + 750;
			}
			
			/*if (gc.round() == marsLanding + 5) {
				Player.UAHUnits.clear();
				VecUnit units = gc.myUnits();
				for (long i = 0; i < units.size(); i++) {
					Unit unit = units.get(i);
					switch (unit.unitType()) {	//Go through all unit types and create a new object
						case Worker:		//of the type of unit we plan to unload
							Worker newWorker = new Worker(unit, gc);	
							Player.newUnits.add(newWorker);
							break;
						case Knight:
							Knight newKnight = new Knight(unit, gc);
							Player.newUnits.add(newKnight);
							break;
						case Ranger:
							Ranger newRanger = new Ranger(unit, gc);
							Player.newUnits.add(newRanger);
							break;
						case Mage:
							Mage newMage = new Mage(unit, gc);
							Player.newUnits.add(newMage);
							break;
						case Healer:
							Healer newHealer = new Healer(unit, gc);
							Player.newUnits.add(newHealer);
							break;
						case Rocket:
							Rocket newRocket = new Rocket(unit, gc);
							Player.newUnits.add(newRocket);
							break;
					}
				}
			}*/
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

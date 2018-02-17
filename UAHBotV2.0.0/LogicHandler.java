import bc.*;
import java.util.*;
import java.lang.Math;


/**
 * This is a class that handles all overall logical schemes, such as research,
 * and escaping to mars
 *
 */
class LogicHandler {

	static boolean escaping;					//Are we trying to escape earth to mars?
	static ArrayList<UAHUnit> rockets = new ArrayList<UAHUnit>();	//List of all rockets so this info is publicly available
	static int factoryGoal = 10;					//Total numbers of factories we are willing to build
	static ArrayList<KarboniteLocation> usedDeposit = new ArrayList<KarboniteLocation>();	//Array of Karbonite Locations so that we know where karbonite is
	static int marsLanding = 0;
	static int combatGoal;
	static int lastTurn = 100;
	
	/**
	 * This is a method that runs at the beginning of the first turn. It
	 * does all initial research that must be done in order to survive.
	 * @param gc
	 */
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
		
		//sets the goal of how many units we should produce before sending them all to attack the enemy.
		combatGoal = (int)Math.sqrt((double)Path.earthMapHeight * Path.earthMapWidth);
		
	}
	
	/**
	 * This is processed every turn and determines whether conditions have
	 * changed such that there needs to be a strategic change. Ex: We now
	 * have a lot of units built up so they should go attack the enemy.
	 * 
	 * @param gc
	 */
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
			
			//counts the number of units we have capable of attacking
			int combatUnits = Player.numKnights + Player.numRangers + Player.numMages;
			
			//determines if we should send a large number of bots to attack the enemy.
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
			//calculate the turn that we land on mars
			if(gc.round() == 750){
				marsLanding = (int)gc.currentDurationOfFlight() + 750;
			}
			
		}
		
		if(gc.round() % 50 == 0){
			checkKarbonite(gc);
		}
	}
	
	/**
	 * Changes all important strategy conditions to those to escape from
	 * earth and go to mars
	 * 
	 * @param gc
	 */
	public static void startEscaping(GameController gc) {
		//set escaping to true
		escaping = true;
		
		//set variable with all rocket locations
		getRockets(gc);
	}

	/**
	 * Finds all rockets and puts them in the rockets array.
	 * 
	 * @param gc
	 */
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
	
	/**
	 * Calculates how much Karbonite we need to acheive our goals
	 * 
	 * @param gc
	 */
	public static void calculateKarboniteGoals(GameController gc) {
		if (gc.round() < Player.stage) {
			Player.highKarboniteGoal = 50;
			Player.lowKarboniteGoal = 300;
			
		} else {
			Player.highKarboniteGoal = 200;
			Player.lowKarboniteGoal = (int)(Player.rocketGoal * Player.rocketCost * Player.kgMultiplier);
		}
	}
	
	/**
	 * Calculates the number of rockets we need to successfully escape to mars.
	 * 
	 * @param gc
	 * @param numUnits
	 */
	public static void calculateRocketGoal(GameController gc, int numUnits) {
		getRockets(gc);
		Player.rocketGoal = (int) Math.ceil((double)numUnits/8) - rockets.size();

	}
	
	/**
	 * Finds all of the karbonite locations throughout the map
	 * and adds them to an arraylist
	 * 
	 * @param gc
	 */
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
	
	/**
	 * Attempts to check all the karbonite locations to determine
	 * if they still have karbonite.
	 * 
	 * @param gc
	 */
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

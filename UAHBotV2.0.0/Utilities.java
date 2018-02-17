import bc.*;
import java.util.*;
import java.lang.Long;

/**
 * Class that contains usefull, multipurpose
 * methods that can be used in many different
 * cases.
 *
 */
class Utilities {
	
	static Team enemyTeam;
	static MapLocation[] enemyStartLocations;

	/**
	 * Method that will count all of our units.
	 * 
	 * @param units
	 */
	public static void countUnits(VecUnit units){
		Player.numFactories = 0;
		Player.numWorkers = 0;
		Player.numKnights = 0;
		Player.numMages = 0;
		Player.numRangers = 0;
		Player.numHealers = 0;
		Player.numRockets = 0;
		for(int i = 0; i < units.size(); i++){
			Unit unit = units.get(i);
			switch (unit.unitType()) {
				case Factory:
					Player.numFactories++;
					break;
				case Worker:
					Player.numWorkers++;
					break;
				case Knight:
					Player.numKnights++;
					break;
				case Mage:
					Player.numMages++;
					break;
				case Ranger:
					Player.numRangers++;
					break;
				case Healer:
					Player.numHealers++;
					break;
				case Rocket:
					Player.numRockets++;
					break;
			}
		}
	}
	
	/**
	 * Verifies our arraylist of units to determine if
	 * it happens to be missing any units.
	 * 
	 * @param gc
	 */
	public static void verifyList(GameController gc){
		if (gc.planet() == Planet.Earth) return;
		
		VecUnit units = gc.myUnits();
		boolean found = false;
		if (Player.UAHUnits.size() == units.size()) {
			return;
		}
		
		if (gc.planet() == Planet.Mars && gc.round() > 800) {
			
		}
		
		long foundUnit = 0;
		for(long i = 0; i < units.size(); i++)
		{
			Unit unit = units.get(i);
			found = false;
			for(UAHUnit target:Player.UAHUnits)
			{
				if(unit.id() == target.getUnitId())
				{
					
					found = true;
					foundUnit = i;
					break;
				}
			}
			if(!found && gc.planet() == Planet.Mars && !(unit.location().isInGarrison()))
			{
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
		}
		
	}
  
	/**
	 * Moves the bot in a random direction
	 * 
	 * @param unit
	 * @param gc
	 */
	public static void moveRandomDirection(Unit unit, GameController gc){
		try{
			Direction randomDirection = Path.directions[Player.rand.nextInt(Path.directions.length)];
			if(gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), randomDirection)){
				gc.moveRobot(unit.id(), randomDirection);
			}
		}
		catch(Exception e){
			System.out.println("An error occurred in moveRandomDirection(Unit unit, GameController gc)");
			System.out.println("Unit ID: " + unit.id());
			e.printStackTrace();
		}		
	}
	
	/**
	 * If there is an enemy within attack range, then attacks that bot.
	 * 
	 * @param unit
	 * @param gc
	 */
	public static void senseAndAttackInRange(Unit unit, GameController gc){
		try{
			VecUnit units = gc.senseNearbyUnitsByTeam(
					unit.location().mapLocation(), unit.attackRange(),
					enemyTeam);
			if(units.size() > 0){
				int enemyID = units.get(0).id();
				if(gc.canAttack(unit.id(), enemyID)){
					gc.attack(unit.id(), enemyID);
				}
			}
			
		}
		catch(Exception e){
			System.out.println("An error occurred in senseAndAttackInRange(Unit unit, GameController g)c");
			System.out.println("Unit ID: " + unit.id());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Moves a bot to the nearest enemy that it can see.
	 * 
	 * @param unit
	 * @param gc
	 */
	public static void moveToNearestEnemy(Unit unit, GameController gc) {
		if (!gc.isMoveReady(unit.id())) return;

		try{
			MapLocation currentLocation = unit.location().mapLocation();
			VecUnit enemyUnits = gc.senseNearbyUnitsByTeam(currentLocation, unit.visionRange(), enemyTeam);
			if(enemyUnits.size() > 0){
				long lowest = Long.MAX_VALUE;
				int index = 0;
				for(int i = 0; i < (int)enemyUnits.size(); i++){
					if(enemyUnits.get((int)i).location().mapLocation().distanceSquaredTo(currentLocation) < lowest){
						lowest = enemyUnits.get((int)i).location().mapLocation().distanceSquaredTo(currentLocation);
						index = i;
					}
				}
				MapLocation enemyLocation = enemyUnits.get(index).location().mapLocation();
				if (!gc.isMoveReady(unit.id())) return;
				Path.determinePathing(unit, enemyLocation, gc);
			}
		}
		catch(Exception e){
			System.out.println("An error occurred in moveToNearestEnemy(Unit unit, GameController gc)");
			System.out.println("Unit ID: " + unit.id());
			e.printStackTrace();
		}
				
	}
	
	/**
	 * Moves a bot to the closest rocket.
	 * 
	 * @param unit
	 * @param gc
	 */
	public static void moveTowardNearestRocket(Unit unit, GameController gc){
		if (!gc.isMoveReady(unit.id())) return;
		try {
			MapLocation currentLocation = unit.location().mapLocation();
			long distances[] = new long[LogicHandler.rockets.size()];
			long lowest = Long.MAX_VALUE;
			int closestRocketIndex = Integer.MAX_VALUE;
			for(int i = 0; i < LogicHandler.rockets.size(); i++) {
				UAHUnit rocket = LogicHandler.rockets.get(i);
				if (rocket.getUnit().location().isOnMap()) {
					MapLocation rocketLocation = rocket.getUnit().location().mapLocation();
					long currentDistance = rocketLocation.distanceSquaredTo(currentLocation);
					if(currentDistance < lowest){
						lowest = currentDistance;
						closestRocketIndex = i;
					}
				}
			}
			if (closestRocketIndex < LogicHandler.rockets.size()) {
				UAHUnit dest = LogicHandler.rockets.get(closestRocketIndex);
				if(gc.canLoad(dest.getUnitId(), unit.id())){
					gc.load(dest.getUnitId(), unit.id());
				}
				Path.determinePathing(unit, dest.getUnit().location().mapLocation(), gc);
			}
		}
		catch(Exception e){
			System.out.println("An error occurred in MoveTowardNearestRocket(Unit unit, GameController gc)");
			System.out.println("Unit ID: " + unit.id());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * returns the nearest blueprint to a unit so that we can
	 * send more of our workers to help them build.
	 * 
	 * @param unit
	 * @param gc
	 * @return
	 */
	public static int getNearbyBlueprint(Unit unit, GameController gc){
		VecUnit units = gc.senseNearbyUnits(unit.location().mapLocation(), 4);
		for(long i = 0; i < units.size(); i++){
			if((units.get(i).unitType() == UnitType.Factory || units.get(i).unitType() == UnitType.Rocket) && units.get(i).structureIsBuilt() == 0){
				return (int)units.get(i).id();
			}
		}
		return Integer.MAX_VALUE;
	}
	
	/**
	 * Method to return the color of the enemy team. Allows us to
	 * know who is our friend and who is our enemy since there was
	 * no easy way to do that this year.
	 * 
	 * @param gc
	 */
	public static void findEnemyTeam(GameController gc){
		if(gc.team().equals(Team.Blue)){
			enemyTeam = Team.Red;
		} else {
			enemyTeam = Team.Blue;
		}
	}
	
	/**
	 * A simple binary search algorithm in a very general form.
	 * 
	 * @param obj
	 * @param target
	 * @return
	 */
	public static int getIndex(Object[] obj, Object target){
		int counter = 0;
		for(Object current:obj){
			if(current.equals(target)){
				return counter;
			}
			counter++;
		}
		return -1; //Not quite sure what the best thing would be to put here so we don't throw an error
	}
	
	/**
	 * sets the enemy starting location to the opposite
	 * of our current locations
	 * 
	 * @param gc
	 */
	public static void invertPositions(GameController gc){
		if (gc.planet() != Planet.Earth) return;
        VecUnit allUnits = Path.earth.getInitial_units();
        int counter = 0;
        enemyStartLocations = new MapLocation[(int)(allUnits.size()/2)];
        for(long i = 0; i < allUnits.size(); i++){
            if(allUnits.get(i).team() == enemyTeam){
                enemyStartLocations[counter] = allUnits.get(i).location().mapLocation();
				System.out.println(allUnits.get(i).location().mapLocation());
                counter++;
            }
        }
    }
    
	/**
	 * Moves a bot closer to the enemy starting location
	 * 
	 * @param unit
	 * @param gc
	 */
    public static void moveTowardEnemyStart(Unit unit, GameController gc){
        Path.bugPath(unit, enemyStartLocations[0], gc);
    }
}
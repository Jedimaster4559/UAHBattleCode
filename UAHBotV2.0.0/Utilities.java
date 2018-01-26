import bc.*;
import java.util.*;
import java.lang.Long;

class Utilities {
	
	static Team enemyTeam;
	static MapLocation[] enemyStartLocations;
    
	//Method to count all units Should be run at the beginning of each turn.
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
	
	//for mars, add rockets if they didn't exist in the
	//UAHUnits array before
	public static void verifyList(GameController gc){
		VecUnit units = gc.myUnits();
		boolean found = false;
		if (Player.UAHUnits.size() == units.size()) {
			return;
		}
		for(long i = 0; i < units.size(); i++){
			found = false;
			for(UAHUnit target:Player.UAHUnits){
				if(units.get(i) == target.getUnit()){
					found = true;
					break;
				}
			}
			if(!found){
				Unit unit = units.get(i);
				if(unit.unitType() == UnitType.Rocket){
					Player.newUnits.add(new Rocket(unit,gc));
					
				}
			}
		}
	}
  
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
	
	//Method to attack one enemy within range if possible
	public static void senseAndAttackInRange(Unit unit, GameController gc){
		try{
			VecUnit units = gc.senseNearbyUnitsByTeam(unit.location().mapLocation(), unit.attackRange(), enemyTeam);
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
	
	//Method to move toward the closest enemy
	public static void moveToNearestEnemy(Unit unit, GameController gc) {
		if (unit.movementHeat() > 0) return;

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
				Path.determinePathing(unit, enemyLocation, gc);
			}
		}
		catch(Exception e){
			System.out.println("An error occurred in moveToNearestEnemy(Unit unit, GameController gc)");
			System.out.println("Unit ID: " + unit.id());
			e.printStackTrace();
		}
				
	}
	
	//Method to move toward the closest rocket
	public static void moveTowardNearestRocket(Unit unit, GameController gc){
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
	
	//Returns int id of nearby blueprint
	public static int getNearbyBlueprint(Unit unit, GameController gc){
		VecUnit units = gc.senseNearbyUnits(unit.location().mapLocation(), 4);
		for(long i = 0; i < units.size(); i++){
			if((units.get(i).unitType() == UnitType.Factory || units.get(i).unitType() == UnitType.Rocket) && units.get(i).structureIsBuilt() == 0){
				return (int)units.get(i).id();
			}
		}
		return Integer.MAX_VALUE;
	}
	
	//Method that returns enemy team
	public static void findEnemyTeam(GameController gc){
		if(gc.team().equals(Team.Blue)){
			enemyTeam = Team.Red;
		} else {
			enemyTeam = Team.Blue;
		}
	}
	
	//Method to return the index number of an object in an array
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
	
	public static void invertPositions(Unit unit, GameController gc){
		if (gc.planet() != Planet.Earth) return;
        VecUnit allUnits = Path.earth.getInitial_units();
        int counter = 0;
        enemyStartLocations = new MapLocation[(int)(allUnits.size()/2)];
        for(long i = 0; i < allUnits.size(); i++){
            if(allUnits.get(i).team() == enemyTeam){
                enemyStartLocations[counter] = allUnits.get(i).location().mapLocation();
                counter++;
            }
        }
    }
    
    public static void moveTowardEnemyStart(Unit unit, GameController gc){
        Path.bugPath(unit, enemyStartLocations[0], gc);
    }
}
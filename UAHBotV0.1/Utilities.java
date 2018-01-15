import bc.*;
import java.util.*;
import java.lang.Long;

class Utilities {
	
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
			if(unit.unitType() == UnitType.Factory)
				Player.numFactories++;
			if(unit.unitType() == UnitType.Worker)
				Player.numWorkers++;
			if(unit.unitType() == UnitType.Knight)
				Player.numKnights++;
			if(unit.unitType() == UnitType.Mage)
				Player.numMages++;
			if(unit.unitType() == UnitType.Ranger)
				Player.numRangers++;
			if(unit.unitType() == UnitType.Healer)
				Player.numHealers++;
			if(unit.unitType() == UnitType.Rocket)
				Player.numRockets++;
		}
	}
  
	public static void moveRandomDirection(Unit unit, GameController gc){
		Direction randomDirection = Player.directions[Player.rand.nextInt(Player.directions.length)];
		if(gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), randomDirection)){
			gc.moveRobot(unit.id(), randomDirection);
		}
	}
	
	//Method to attack one enemy within range if possible
	public static void senseAndAttackInRange(Unit unit, GameController gc){
		VecUnit units = gc.senseNearbyUnitsByTeam(unit.location().mapLocation(), unit.attackRange(), enemyTeam(gc));
		int enemyID = units.get(0).id();
		if(gc.canAttack(unit.id(), enemyID)){
			gc.attack(unit.id(), enemyID);
		}
	}
	
	//Method to move toward the closest enemy
	public static void moveToNearestEnemy(Unit unit, GameController gc){
		MapLocation currentLocation = unit.location().mapLocation();
		VecUnit enemyUnits = gc.senseNearbyUnitsByTeam(currentLocation, unit.visionRange(), enemyTeam(gc));
		long[] distances = new long[(int)enemyUnits.size()];
		long lowest = Long.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < (int)enemyUnits.size(); i++){
			if(enemyUnits.get((int)i).location().mapLocation().distanceSquaredTo(currentLocation) < lowest){
				index = i;
			}
		}
		MapLocation enemyLocation = enemyUnits.get(index).location().mapLocation();
		Path.determinePathing(unit, enemyLocation, gc);
	}
	
	//Method that returns enemy team
	public static Team enemyTeam(GameController gc){
		gc.team();
		if(gc.team().equals(Team.Blue)){
			gc.team();
			return Team.Red;
		}
		else {
			gc.team();
			return Team.Blue;
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
		return 0; //Not quite sure what the best thing would be to put here so we don't throw an error
	}
}
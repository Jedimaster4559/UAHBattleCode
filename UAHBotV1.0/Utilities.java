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
		try{
			Direction randomDirection = Player.directions[Player.rand.nextInt(Player.directions.length)];
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
			VecUnit units = gc.senseNearbyUnitsByTeam(unit.location().mapLocation(), unit.attackRange(), enemyTeam(gc));
			if(units.size() > 0){
				System.out.println("Targeting");
				int enemyID = units.get(0).id();
				if(gc.canAttack(unit.id(), enemyID)){
					//System.out.println("Attacking");
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
	public static void moveToNearestEnemy(Unit unit, GameController gc){
		try{
			MapLocation currentLocation = unit.location().mapLocation();
			VecUnit enemyUnits = gc.senseNearbyUnitsByTeam(currentLocation, unit.visionRange(), enemyTeam(gc));
			if(enemyUnits.size() > 0){
				//System.out.println("Finding");
				long lowest = Long.MAX_VALUE;
				int index = 0;
				for(int i = 0; i < (int)enemyUnits.size(); i++){
					if(enemyUnits.get((int)i).location().mapLocation().distanceSquaredTo(currentLocation) < lowest){
						lowest = enemyUnits.get((int)i).location().mapLocation().distanceSquaredTo(currentLocation);
						index = i;
					}
				}
				MapLocation enemyLocation = enemyUnits.get(index).location().mapLocation();
				//System.out.println("Moving");
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
		try{
			MapLocation currentLocation = unit.location().mapLocation();
			long distances[] = new long[LogicHandler.rockets.length];
			long lowest = Long.MAX_VALUE;
			int closestRocketIndex = 0;
			for(int i = 0; i < LogicHandler.rockets.size(); i++) {
				Unit rocket = LogicHandler.rockets[i];
				if(rocket.location().mapLocation().distanceSquaredTo(currentLocation) < lowest){
					lowest = rocket.location().mapLocation().distanceSquaredTo(currentLocation);
					closestRocketIndex = i;
				}
			}
			
			Unit dest = LogicHandler.rockets[closestRocketIndex];
			if(gc.canLoad(dest.id(), unit.id())){
				gc.load(dest.id(), unit.id());
			}
			Path.determinePathing(unit, dest.location().mapLocation(), gc);
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
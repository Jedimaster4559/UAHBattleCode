import bc.*;
import java.util.*;

class Utilities {
	
	//Method to count all units Should be run at the beginning of each turn.
	public static void countUnits(VecUnit units){
		Player.numFactories = 0;
		Player.numWorkers = 0;
		Player.numKnights = 0;
		Player.numMages = 0;
		Player.numRangers = 0;
		Player.numHealers = 0
		Player.numRockets = 0;
		for(int = 0; i < unit.size(); i++){
			if(unit.unitType() == UnitType.Factory)
				numFactories++;
			if(unit.unitType() == UnitType.Worker)
				numWorkers++;
			if(unit.unitType() == UnitType.Knight)
				numKnights++;
			if(unit.unitType() == UnitType.Mage)
				numMages++;
			if(unit.unitType() == UnitType.Ranger)
				numRangers++;
			if(unit.unitType() == UnitType.Healer)
				numHealers++;
			if(unit.unitType() == UnitType.Rocket)
				numRockets++;
		}
	}
  
	public static void moveRandomDirection(Unit unit){
		Direction randomDirection = directions[rand.nextInt(directions.length)];
		if(gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), directions[Player.rand.nextInt(directions.length)])){
			gc.moveRobot(unit.id(), randomDirection);
		}
	}
}
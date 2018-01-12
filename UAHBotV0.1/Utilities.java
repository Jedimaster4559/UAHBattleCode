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
}
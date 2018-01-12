import bc.*;
import java.util.*;

public class Methods{
	public static void countUnits(VecUnit units){
		numFactories = 0;
		  numWorkers = 0;
		  numKnights = 0;
		  numMages = 0;
		  numRangers = 0;
		  numHealers = 0
		  numRockets = 0;
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
	  if(gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), directions[rand.nextInt(directions.length)])){
		  gc.moveRobot(unit.id(), randomDirection);
	  }
  }
	
}

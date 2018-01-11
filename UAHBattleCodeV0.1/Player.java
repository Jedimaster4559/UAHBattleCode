import bc.*;
import java.util.Random;;

public class Player {
	
	static Random rand = new Random();
	static Direction[] directions;
	static GameController gc;
	static int numFactories;
	static int numWorkers;
	static int numKnights;
	static int numMages;
	static int numRangers;
	static int numHealers;
	static int numRockets;
		
  public static void main(String[] args) {
	
	//Add a Game Controller
	gc = new GameController();
	
	//Seed Randomizer for debugging purposes
	rand.setSeed(4559);
	
	//Create and Array of all Directions a bot can travel
	directions = Direction.values();
	
	while (true){
		System.out.println("CurrentRound: " + gc.round());
		
		VecUnit units = gc.myUnits();
		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
			
			
			//This block determines unit type and then executes a method accordingly
			if(unit.unitType() == UnitType.Factory)
				try{
					runFactory(unit);
				}
				catch(Exception e){
					System.out.println("A Factory Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			if(unit.unitType() == UnitType.Worker)
				runWorker(unit);
			if(unit.unitType() == UnitType.Knight)
				runKnight(unit);
			if(unit.unitType() == UnitType.Mage)
				runMage(unit);
			if(unit.unitType() == UnitType.Ranger)
				runRanger(unit);
			if(unit.unitType() == UnitType.Healer)
				runHealer(unit);
			if(unit.unitType() == UnitType.Rocket)
				runRocket(unit);
			
        }
		gc.nextTurn();
	}
	
  }
  
  static void runFactory(Unit unit){
	  
  }
  
  static void runWorker(Unit unit){
	  Direction randomDirection = directions[rand.nextInt(directions.length)];
	  if(gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), directions[rand.nextInt(directions.length)])){
		  gc.moveRobot(unit.id(), randomDirection);
	  }
  }
  
  static void runKnight(Unit unit){
	  
  }
  
  static void runMage(Unit unit){
	  
  }
  
  static void runRanger(Unit unit){
	  
  }
  
  static void runHealer(Unit unit){
	  
  }
  
  static void runRocket(Unit unit){
	  
  }
  
  static void countUnits(VecUnit units){
	  for(int = 0; i < unit.size(); i++){
		  numFactories = 0;
		  numWorkers = 0;
		  numKnights = 0;
		  numMages = 0;
		  numRangers = 0;
		  numHealers = 0
		  numRockets = 0;
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
  
}

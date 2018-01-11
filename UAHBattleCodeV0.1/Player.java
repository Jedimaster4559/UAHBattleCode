import bc.*;
import java.util.Random;;

public class Player {
	
	static Random rand = new Random();
	static Direction[] directions;
	static GameController gc;
		
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
}

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
			//RunFactory
			if(unit.unitType() == UnitType.Factory){
				try{
					runFactory(unit);
				}
				catch(Exception e){
					System.out.println("A Factory Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			//Run Worker
			if(unit.unitType() == UnitType.Worker){
				try{
					runWorker(unit);
				}
				catch(Exception e) {
					System.out.println("A Worker Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			//Run Knight
			if(unit.unitType() == UnitType.Knight){
				try{
					runKnight(unit);
				}
				catch(Exception e) {
					System.out.println("A Knight Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			//Run Mage
			if(unit.unitType() == UnitType.Mage){
				try{
					runMage(unit);
				}
				catch(Exception e) {
					System.out.println("A Mage Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			//Run Ranger
			if(unit.unitType() == UnitType.Ranger){
				try{
					runRanger(unit);
				}
				catch(Exception e) {
					System.out.println("A Ranger Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			//Run Healer
			if(unit.unitType() == UnitType.Healer){
				try{
					runHealer(unit);
				}
				catch(Exception e) {
					System.out.println("A Healer Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			//Run Rocket
			if(unit.unitType() == UnitType.Rocket){
				try{
					runRocket(unit);
				}
				catch(Exception e) {
					System.out.println("A Rocket Error Occurred:\nUnit Id: " + unit.id());
					e.printStackTrace();
				}
			}
			
        }
		gc.nextTurn();
	}
	
  }
  
	static void runFactory(Unit unit){
	  
	}
  
	static void runWorker(Unit unit){
	  
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

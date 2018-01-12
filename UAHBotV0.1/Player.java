import bc.*;
<<<<<<< HEAD
import java.util.ArrayList;
import java.lang.Long;

public class Player {
  public static void main(String[] args) {
	
	//Add a Game Controller
	GameController gc = new GameController();
	
	PlanetMap earthMap = gc.startingMap(Planet.Earth);
	
	Long heightLong = new Long(earthMap.getHeight());
	Long widthLong = new Long(earthMap.getWidth());
	
	int height = heightLong.intValue();
	int width = widthLong.intValue();
	
	ArrayList<ArrayList<Long>> karboniteEarth =
			new ArrayList<ArrayList<Long>>(height);
	
	MapLocation dummyLocation = new MapLocation(Planet.Earth, 0, 0);
	
	//put starting karbonite in 2d arrayList
	for (int i = 0; i < height; i++) {
		dummyLocation.setY(i);
		ArrayList<Long> karboniteCol = new ArrayList<Long>(width);
		for (int j = 0; j < width; j++) {
			dummyLocation.setX(j);
			karboniteCol.add(new Long(earthMap.initialKarboniteAt(dummyLocation)));
		}
		karboniteEarth.add(karboniteCol);
	}
	
	//print starting karbonite
	for (int i = 0; i < karboniteEarth.size(); i++) {
		ArrayList karboniteCol = karboniteEarth.get(i);
		for (int j = 0; j < karboniteCol.size(); j++) {
			System.out.println("starting karbonite at " +
					"(" + i + ", " + j + ") is: " + karboniteCol.get(j));
		}
	}
	
	while (true){
		System.out.println("CurrentRound: " + gc.round());
		
		VecUnit units = gc.myUnits();
		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
			System.out.println("looping through units");
			
			//This block determines unit type and then executes a method accordingly
			if (unit.unitType() == UnitType.Factory) {
				runFactory(unit);
			} else if (unit.unitType() == UnitType.Worker) {
				System.out.println("Found worker!");
				Worker.process(unit, gc);
			} else if (unit.unitType() == UnitType.Knight) {
				runKnight(unit);
			} else if (unit.unitType() == UnitType.Mage) {
				runMage(unit);
			} else if (unit.unitType() == UnitType.Ranger) {
				runRanger(unit);
			} else if (unit.unitType() == UnitType.Healer) {
				runHealer(unit);
			} else if (unit.unitType() == UnitType.Rocket) {
				runRocket(unit);
			}
			
        }
		System.out.println("total karbonite is: " + gc.karbonite());
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
=======
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
		
		//loop through all units and process their turn
		while (true){
			System.out.println("CurrentRound: " + gc.round());
			
			//get all units
			VecUnit units = gc.myUnits();
			
			//initialize count of all units
			Utilities.countUnits(units);
			
			//loop through units
			for (int i = 0; i < units.size(); i++) {
				Unit unit = units.get(i);
				
				runUnitLogic(unit);
				
				
			}
			
			//end turn
			gc.nextTurn();
		}
	
	}
	
	//This method will determine what type of unit each unit is and run it's processing code
	public static void runUnitLogic(Unit unit){
		//This block determines unit type and then executes a method accordingly
		//RunFactory
		if(unit.unitType() == UnitType.Factory){
			try{
				Factory.process(unit,gc);
			}
			catch(Exception e){
				System.out.println("A Factory Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Worker
		else if(unit.unitType() == UnitType.Worker){
			try{
				Worker.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Worker Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Knight
		else if(unit.unitType() == UnitType.Knight){
			try{
				Knight.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Knight Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Mage
		else if(unit.unitType() == UnitType.Mage){
			try{
				Mage.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Mage Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Ranger
		else if(unit.unitType() == UnitType.Ranger){
			try{
				Ranger.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Ranger Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Healer
		else if(unit.unitType() == UnitType.Healer){
			try{
				Healer.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Healer Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Rocket
		else if(unit.unitType() == UnitType.Rocket){
			try{
				Rocket.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Rocket Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}				
	}
  
>>>>>>> master
}

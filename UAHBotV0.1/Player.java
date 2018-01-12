import bc.*;
import java.util.ArrayList;

public class Player {
  public static void main(String[] args) {
	
	//Add a Game Controller
	GameController gc = new GameController();
	
	PlanetMap earthMap = gc.startingMap();
	
	ArrayList<ArrayList> karboniteEarth = new ArrayList<ArrayList>(earthMap.getHeight());
	
	MapLocation dummyLocation = new MapLocation(Planet.Earth, 0, 0);
	
	//put starting karbonite in 2d arrayList
	for (int i = 0; i < earthMap.getHeight(); i++) {
		dummyLocation.setY(i);
		ArrayList<long> karboniteCol = new ArrayList<long>(earthMap.getWidth());
		for (int j = 0; j < earthMap.getWidth(); j++) {
			dummyLocation.setX(j);
			karboniteCol.add(earthMap.initialKarboniteAt(dummyLocation));
		}
		karboniteEarth.add(karboniteCol);
	}
	
	//print starting karbonite
	for (int i = 0; i < karboniteEarth.size()) {
		ArrayList karboniteCol = karboniteEarth.get(i);
		for (int j = 0; j < karboniteCol.size()) {
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
}

import bc.*;
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
	
	ArrayList<ArrayList<long>> karboniteEarth =
			new ArrayList<ArrayList<long>>(height);
	
	MapLocation dummyLocation = new MapLocation(Planet.Earth, 0, 0);
	
	//put starting karbonite in 2d arrayList
	for (int i = 0; i < height; i++) {
		dummyLocation.setY(i);
		ArrayList<long> karboniteCol = new ArrayList<long>(width);
		for (int j = 0; j < width; j++) {
			dummyLocation.setX(j);
			karboniteCol.add(earthMap.initialKarboniteAt(dummyLocation));
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
}

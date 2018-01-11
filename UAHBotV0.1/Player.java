import bc.*;

public class Player {
  public static void main(String[] args) {
	
	//Add a Game Controller
	GameController gc = new GameController();
	
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

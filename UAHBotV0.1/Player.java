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
			
			
			//This block determines unit type and then executes a method accordingly
			if(unit.unitType() == UnitType.Factory)
				runFactory(unit);
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

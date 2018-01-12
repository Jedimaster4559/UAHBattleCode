import bc.*;

class Factory {
		
		public static boolean canProcess(Unit unit){
			if (unit.unitType() == UnitType.Factory){
				return true;
			}
			return false;
		}

		public static void process(Unit unit, GameController gc){
			//Test to make sure the factory exists in a valid location
			if (unit.location().mapLocation().getPlanet() == null) {
				return;
			}
			System.out.println("Valid Location");
			
			//Creates a new unit if the factory isn't producing
			if(!unit.isFactoryProducing()){
				UnitType unitCreateType = decideUnitType();
				if(gc.canProduceRobot(unit.id(), unitCreateType)){
					gc.produceRobot(unit.id(), unitCreateType);
				}
			}
			
			
			
		}
		
		public static UnitType decideUnitType(){
			if(true){
				return UnitType.Worker;
			}
			
		}
}
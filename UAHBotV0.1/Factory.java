import bc.*;

class Factory implements UnitInterface {
		
		public static boolean canProcess(Unit unit){
			if (unit.unitType() == UnitType.Factory){
				return true;
			}
			return false;
		}

		public static void process(Unit unit, GameController gc){
			
			/*
			VecUnitID unitIDs = unit.structureGarrison();
			for(long i = 0; i < unitIDs.size(); i++){
				Unit garrisonedUnit = gc.unit(unitIDs.get(i));
				gc.unload();
				Player.runUnitLogic(garrisonedUnit);
			}
			*/
			
			//Attempts to unload all bots
			if(unit.structureGarrison().size() > 0)
			{
				Direction[] directions = Direction.values();
				for(Direction direction:directions){
					if(gc.canUnload(unit.id(), direction)) {
						gc.unload(unit.id(), direction);
						if(unit.structureGarrison().size() == 0){
							break;
						}
					}
				}
			}
			
			//Creates a new unit if the factory isn't producing
			if(unit.isFactoryProducing() == 0){
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
			return null;
			
		}

}
import bc.*;

class Factory extends Structue{
	
	private boolean built = false;
	
	public Factory(Unit unit, GameController gc) {
		super(unit, gc);
		built = false;
	}

	public void process() {
		
		/*
		VecUnitID unitIDs = unit.structureGarrison();
		for(long i = 0; i < unitIDs.size(); i++){
			Unit garrisonedUnit = gc.unit(unitIDs.get(i));
			gc.unload();
			Player.runUnitLogic(garrinedUnit);
		}
		*/
		
		if (!built) {
			if (unit.structureIsBuilt() == 1) {
				built = true;
			} else {
				return;
			}
		}
		
		//Attempts to unload all bots
		if(unit.structureGarrison().size() > 0)
		{
			Direction[] directions = Direction.values();
			for(Direction direction : directions){
				if(gc.canUnload(unit.id(), direction)) {
					gc.unload(unit.id(), direction);
					if(unit.structureGarrison().size() == 0){
						break;
					}
				}
			}
		}
		
		//Creates a new unit if the factory isn't producing
		if(unit.isFactoryProducing() == 0 && built &&
				Player.numKnights < 40){
			UnitType unitCreateType = Factory.decideUnitType();
			//System.out.println("Creating new unit: " + unitCreateType);
			if(gc.canProduceRobot(unit.id(), unitCreateType)){
				gc.produceRobot(unit.id(), unitCreateType);
			}
		}

	}
	
	public static UnitType decideUnitType(){
		if(Player.numWorkers < 10){
			return UnitType.Worker;
		} else if(Player.numKnights <= Player.numRangers){
			return UnitType.Knight;
		} else{
			return UnitType.Ranger;
		}
	}

}
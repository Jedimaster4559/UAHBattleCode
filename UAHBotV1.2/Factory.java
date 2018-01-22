import bc.*;

class Factory extends Structure {
	
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
					int unloadId = unit.structureGarrison().get(0);
					Unit unloadUnit = gc.unit(unloadId);
					UnitType unloadType = unloadUnit.unitType();
					switch (unloadType) {
						case UnitType.Worker:
							Worker newWorker = new Worker(unloadUnit, gc);
							UAHUnits.add(newWorker);
							break;
						case UnitType.Knight:
							Knight newKnight = new Knight(unloadUnit, gc);
							UAHUnits.add(newKnight);
							break;
						case UnitType.Ranger:
							Ranger newRanger = new Ranger(unloadUnit, gc);
							UAHUnits.add(newRanger);
							break;
						case UnitType.Mage:
							Mage newMage = new Mage(unloadUnit, gc);
							UAHUnits.add(newMage);
							break;
						case UnitType.Healer:
							Healer newHealer = new Healer(unloadUnit, gc);
							UAHUnits.add(newHealer);
							break;
					}
						
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
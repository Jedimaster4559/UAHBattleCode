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
				//System.out.println("Structure built! " + unitId);
			} else {
				return;
			}
		}
		
		//Attempts to unload all bots
		if(unit.structureGarrison().size() > 0)
		{
			Direction[] directions = Direction.values();
			for (Direction direction : directions) {
				if (gc.canUnload(unit.id(), direction)) {
					//System.out.println("Unloading unit" + unitId);
					int unloadId = unit.structureGarrison().get(0);
					Unit unloadUnit = gc.unit(unloadId);
					UnitType unloadType = unloadUnit.unitType();
					switch (unloadType) {
						case Worker:
							Worker newWorker = new Worker(unloadUnit, gc);
							Player.newUnits.add(newWorker);
							break;
						case Knight:
							Knight newKnight = new Knight(unloadUnit, gc);
							Player.newUnits.add(newKnight);
							break;
						case Ranger:
							Ranger newRanger = new Ranger(unloadUnit, gc);
							Player.newUnits.add(newRanger);
							break;
						case Mage:
							Mage newMage = new Mage(unloadUnit, gc);
							Player.newUnits.add(newMage);
							break;
						case Healer:
							Healer newHealer = new Healer(unloadUnit, gc);
							Player.newUnits.add(newHealer);
							break;
					}
						
					gc.unload(unit.id(), direction);
					if(unit.structureGarrison().size() == 0){
						break;
					}
				}
			}
		}
		//System.out.println("Trying production " + unitId);
		//Creates a new unit if the factory isn't producing
		if(unit.isFactoryProducing() == 0) {
			UnitType unitCreateType = decideUnitType();
			//System.out.println("Creating new unit: " + unitCreateType);
			if(gc.canProduceRobot(unit.id(), unitCreateType)){
				gc.produceRobot(unit.id(), unitCreateType);
			}
		}

	}
	
	public UnitType decideUnitType() {
		if (Player.numWorkers < 10) {
			return UnitType.Worker;
		} else if (Player.numKnights <= Player.numRangers) {
			return UnitType.Knight;
		} else {
			return UnitType.Ranger;
		}
	}

}
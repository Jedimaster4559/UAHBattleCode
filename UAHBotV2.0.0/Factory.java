import bc.*;

class Factory extends Structure {
	
	//By default when a factory is created it is still under construction
	private boolean built = false;
	
	
	public Factory(Unit unit, GameController gc) {
		super(unit, gc);
		built = false;
	}

	
	public void process() {
		
		//check to determing if the factory has been built
		if (!built) {
			if (unit.structureIsBuilt() == 1) {
				built = true;	//true if it does
			} else {
				return;		//returns and does not process if the factor has not been built yet (prevents errors)
			}
		}
		
		//Attempts to unload all bots
		if(unit.structureGarrison().size() > 0)
		{
			Direction[] directions = Direction.values();					//get all directions (improve this later)
			for (Direction direction : directions) {					//Loop through all of the directions
				if (gc.canUnload(unit.id(), direction)) {				//Check if the bot can unload in a given dir direction
					int unloadId = unit.structureGarrison().get(0);			
					Unit unloadUnit = gc.unit(unloadId);				//set important variables for unload
					UnitType unloadType = unloadUnit.unitType();
					switch (unloadType) {						//Go through all unit types and create a new object
						case Worker:						//of the type of unit we plan to unload
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
						
					gc.unload(unit.id(), direction);				//unload unit in the given direction
					if(unit.structureGarrison().size() == 0){
						break;							//if the factory happens to be empty now, stop unloading
					}								//if not, keep trying different directions
				}
			}
		}
		
		if(unit.isFactoryProducing() == 0) {				//create new unit if the factory is not already working on one
			UnitType unitCreateType = decideUnitType();		//decide the unit type of said unit
			if(gc.canProduceRobot(unit.id(), unitCreateType)){	
				gc.produceRobot(unit.id(), unitCreateType);	//create the unit if it is possible to do so
			}
		}

	}
	
	public UnitType decideUnitType() {
		if (Player.numWorkers < 10) {				
			return UnitType.Worker;
		} else if (Player.numKnights <= Player.numRangers) {	//Currently returns 10 workers and then
			return UnitType.Knight;				//splits between Knights and Rangers
		} else {
			return UnitType.Ranger;
		}
	}


}

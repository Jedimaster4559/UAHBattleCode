import bc.*;



class Rocket extends Structure {

	public Rocket(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
		
		//abort processing for the turn if we are in space
		//update currentLocation otherwise
		if (unit.location().isOnMap() == 0) {
			return;
		} else {
			currentLocation = unit.location().mapLocation();
		}
		
		//abort processing if on earth and not built yet
		if(!(unit.location().mapLocation().getPlanet() == Planet.Mars)) {
			if (unit.structureIsBuilt() == 0) return;			//Check to see if the structure is actually built
		}
		
		if (unit.location().mapLocation().getPlanet() == Planet.Earth) {	//If we are on earth
			//check if we should launch
			if(		(unit.structureGarrison().size() == 8) ||
					((unit.structureGarrison().size() * 2 + gc.round()) > 745) ||
					(gc.round() >= 748))	
			{
				findLandableSpot(unit, gc);		//try to launch
			}
		
		} else if (unit.structureGarrison().size() > 0) {//if we are on Mars, attempt to unload
		
			Direction[] directions = Direction.values();					//get an array of all directions
			for (Direction direction : directions) {					//loop through all possible directions
			
				if (gc.canUnload(unit.id(), direction)) {				//if we can unload a unit in this direction, do it
					int unloadId = unit.structureGarrison().get(0);			
					Unit unloadUnit = gc.unit(unloadId);				//helpful unload variables
					UnitType unloadType = unloadUnit.unitType();
					switch (unloadType) {						//determine unit type and then add to the new
						case Worker:						//units array list
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
						
					gc.unload(unit.id(), direction);		//unload the given unit in given direction
					if(unit.structureGarrison().size() == 0){	//if the Rocket is now empty, stop trying to unload
						break;


					}
				}
			}
		}
	}
	

	public void findLandableSpot(Unit unit, GameController gc) {
		//variable for this process
		MapLocation randomLocation;
		int randx;
		int randy;
		
		for (int i = 0; i < 100; i++) {							//for loop so we don't spend too much time per turn in this step
			randx = Player.rand.nextInt((int)Path.mars.getWidth()-1);
			randy = Player.rand.nextInt((int)Path.mars.getHeight()-1);		//set the random location
			randomLocation = new MapLocation(Planet.Mars, randx,randy);
			if(gc.canLaunchRocket(unit.id(), randomLocation) && 			
					(Path.mars.isPassableTerrainAt(randomLocation) == 1)){	//if this is a safe location to launch to, then launch
				gc.launchRocket(unit.id(), randomLocation);
				break;
			}
		}
	}

}

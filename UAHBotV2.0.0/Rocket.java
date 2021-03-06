import bc.*;

/**
 * Class that contains all the methods for the Rocket.
 *
 */
class Rocket extends Structure {


	private MapLocation currentLocation;
	private boolean finished = false;

	/**
	 * Constructor for the Rocket class
	 * 
	 * @param unit
	 * @param gc
	 */
	public Rocket(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	/**
	 * Runs a rockets entire turn.
	 * 
	 */
	public void process() {
		//abort processing for the turn if we are in space
		//update currentLocation otherwise
		if (!unit.location().isOnMap() || finished) {
			return;
		} else {
			currentLocation = unit.location().mapLocation();
		}
		
		if (currentLocation.getPlanet() == Planet.Earth) {	//If we are on earth
			//check if we should launch
			
			//Check to see if the structure is actually built
			if (unit.structureIsBuilt() == 0) return;
			
			
			if(		(unit.structureGarrison().size() >= 8) ||
					((unit.structureGarrison().size() * 2 + gc.round()) > 743) ||
					(gc.round() >= 747))	
			{
				findLandableSpot(unit, gc);		//try to launch
			}
		
		//mars
		} else {
			if (unit.structureGarrison().size() > 0) {//if we are on Mars, attempt to unload
				for (Direction direction : Path.directions) {					
					unit = gc.unit(unitId);
					//if we can unload a unit in this direction, do it
					if (gc.canUnload(unit.id(), direction)) {	
						gc.unload(unit.id(), direction);
						
						//helpful unload variables
						Unit unloadUnit = gc.senseUnitAtLocation(currentLocation.add(direction));			
						UnitType unloadType = unloadUnit.unitType();
						switch (unloadType) {		//determine unit type and then add to the new
							case Worker:			//units array list
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
						
						//unload the given unit in given direction
								
						
					}
					if(unit.structureGarrison().size() == 0){	//if the Rocket is now empty, stop trying to unload
						break;
					}
				}
			} else {
				finished = true;
				//Player.deadUnits.add(this);
			}
		}
	}
	
	/**
	 * Finds a spot where it is possible for a rocket to land on mars. Uses
	 * random values to find this location.
	 * 
	 * @param unit
	 * @param gc
	 */
	public void findLandableSpot(Unit unit, GameController gc) {
		//variable for this process
		MapLocation randomLocation;
		int randx;
		int randy;
		
		for (int i = 0; i < 100; i++) {	//for loop so we don't spend too much time per turn in this step
			randx = Player.rand.nextInt((int)Path.mars.getWidth()-1);
			randy = Player.rand.nextInt((int)Path.mars.getHeight()-1);		//set the random location
			randomLocation = new MapLocation(Planet.Mars, randx,randy);
			//if this is a safe location to launch to, then launch
			if(gc.canLaunchRocket(unit.id(), randomLocation)){	
				gc.launchRocket(unit.id(), randomLocation);
				break;
			}
		}
	}

}

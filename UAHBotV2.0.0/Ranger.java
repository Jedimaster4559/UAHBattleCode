import bc.*;

/**
 * Class that contains all methods pertaining specifically
 * to the Ranger.
 * 
 */
class Ranger extends MobileUnit{

	/**
	 * Constructor for the ranger class
	 * 
	 * @param unit
	 * @param gc
	 */
	public Ranger(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	/**
	 * Processing Method for the ranger. Runs an
	 * entire ranger turn.
	 */
	public void process() {
		//if bot is not on map, do not process
		if (!unit.location().isOnMap()) {
			return;
		} else {
			currentLocation = unit.location().mapLocation();
		}
		
		//if we should be escaping to Mars, attempt to 
		if (currentLocation.getPlanet() == Planet.Earth &&
				LogicHandler.escaping && gc.isMoveReady(unitId)) {	
			Utilities.moveTowardNearestRocket(unit, gc);		//do so
		}
		
		if (!Player.peaceful) {						//peaceful toggle for debugging purposes
			if(unit.attackHeat() < 10){
				Utilities.senseAndAttackInRange(unit, gc);	//attack enemy if possible
			}
			if(gc.isMoveReady(unitId)){
				Utilities.moveToNearestEnemy(unit, gc);		//move toward an enemy if possible
			}
		}
		if(gc.isMoveReady(unitId)){
			dest = Path.setDest(unit, enemy, gc);
			Path.determinePathing(unit, dest, gc);		//move a random direction if possible
		}
	}

}

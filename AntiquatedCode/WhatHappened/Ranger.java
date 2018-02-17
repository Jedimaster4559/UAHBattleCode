import bc.*;


class Ranger extends MobileUnit{

	public Ranger(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
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
			Utilities.moveRandomDirection(unit, gc);		//move a random direction if possible
		}
	}

}

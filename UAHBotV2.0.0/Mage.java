import bc.*;


class Mage extends MobileUnit{
	
	public Mage(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
        	//Do not process if we are not on Earth or Mars
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
		
		if (!Player.peaceful) {						//Peaceful catch for debugging purposes
			if(gc.isAttackReady(unitId)){
				Utilities.senseAndAttackInRange(unit, gc);	//attack an enemy if possible
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

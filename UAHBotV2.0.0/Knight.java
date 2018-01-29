import bc.*;

class Knight extends MobileUnit {
	
	public Knight(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
		//check to see if the given unit is on the map and therefore controllable
		if (!unit.location().isOnMap()) {
			return;
		} else {
			currentLocation = unit.location().mapLocation();
		}
		
		//determine if we need to be attempting
		if (currentLocation.getPlanet() == Planet.Earth 
				&& LogicHandler.escaping && gc.isMoveReady(unitId))
		{	
			//System.out.println("escape attempt");
			Utilities.moveTowardNearestRocket(unit, gc);		//to get on a rocket
		}
		
		if (!Player.peaceful) {		//Peaceful catch (for debugging purposes)
			//attack if possible
			if (gc.isAttackReady(unitId)) {				
				Utilities.senseAndAttackInRange(unit, gc);
			}
			//move if possible
			if (gc.isMoveReady(unitId)) {
				Utilities.moveToNearestEnemy(unit, gc);
			}
		}
		if (gc.isMoveReady(unitId)) {
			//If we still can move, attempt to move a random direction
			dest = Path.setDest(unit, enemy, gc);
			Path.determinePathing(unit, dest, gc);		
		}
		
	}
	
}

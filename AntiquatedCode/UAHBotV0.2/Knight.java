import bc.*;

class Knight {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Knight) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		if(!unit.location().isOnMap()){
			return;
		}
		
		MapLocation currentLocation = unit.location().mapLocation();
		if(unit.attackHeat() < 10){
			Utilities.senseAndAttackInRange(unit, gc);
		}
		if(unit.movementHeat() < 10){
			Utilities.moveToNearestEnemy(unit, gc);
		}
		if(unit.movementHeat() < 10){
			Utilities.moveRandomDirection(unit, gc);
		}
		
		
	}
	
}
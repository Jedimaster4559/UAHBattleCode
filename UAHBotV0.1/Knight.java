import bc.*;

class Knight implements UnitInterface {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Knight) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		MapLocation currentLocation = unit.location().mapLocation();
		if(unit.attackHeat() < 10){
			Utilities.senseAndAttackInRange(unit, gc);
		}
		else if(unit.movementHeat() < 10){
			Utilities.moveToNearestEnemy(unit, gc);
		}
		else if(unit.movementHeat() < 10){
			Utilities.moveRandomDirection(unit, gc);
		}
		
		
	}
	
}
import bc.*;

class Ranger extends MobileUnit{

	public static void process(Unit unit, GameController gc) {
		
		if (LogicHandler.escaping) {
			Utilities.moveTowardNearestRocket(unit, gc);
		}
		
		if (!Player.peaceful) {
			if(unit.attackHeat() < 10){
				Utilities.senseAndAttackInRange(unit, gc);
			}
			if(unit.movementHeat() < 10){
				Utilities.moveToNearestEnemy(unit, gc);
			}
		}
		if(unit.movementHeat() < 10){
			Utilities.moveRandomDirection(unit, gc);
		}
	}
}
import bc.*;

class Ranger {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Ranger) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		
	}
	
}
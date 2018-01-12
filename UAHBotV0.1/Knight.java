import bc.*;

class Knight {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Knight) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		
	}
	
}
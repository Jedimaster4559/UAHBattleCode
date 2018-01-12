import bc.*;

class Knight implements UnitInterface {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Knight) {
			return true;
		}
		return false;
	}
	
	public static void process(GameController gc, Unit unit) {
		
	}
	
}
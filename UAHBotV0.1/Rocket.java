import bc.*;

class Rocket {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Rocket) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		
	}
	
}
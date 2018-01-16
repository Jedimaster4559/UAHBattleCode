import bc.*;

class Healer implements UnitInterface {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Healer) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		
	}
	
}
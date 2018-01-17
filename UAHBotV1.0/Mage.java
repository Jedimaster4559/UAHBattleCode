import bc.*;

class Mage {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Mage) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		
	}
	
}
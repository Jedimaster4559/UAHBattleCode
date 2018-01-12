import bc.*;

class Factory implements UnitInterface {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Factory) {
			return true;
		}
		return false;
	}
	
	public static void process(GameController gc, Unit unit) {
		
	}
	
}
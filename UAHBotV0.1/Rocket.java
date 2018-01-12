import bc.*;

class Rocket implements UnitInterface {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Rocket) {
			return true;
		}
		return false;
	}
	
	public static void process(GameController gc, Unit unit) {
		
	}
	
}
import bc.*;

class Worker {
	
	public static boolean canProcess(Unit unit) {
		if (unit.UnitType() == UnitType.Worker) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		if (unit.location.mapLocation.getPlanet() == null) {
			return;
		}
		if (gc.karbonite() < 500 && gc.canHarvest(unit.id(), Direction.Center)) {
			gc.harvest(unit.id(), Direction.Center);
		} else if (gc.canBlueprint(unit.id(), UnitType.Factory, Direction.Center)) {
			gc.blueprint(unit.id(), UnitType.Factory, Direction.Center);
		} else if (gc.canMove(unit.id(), Direction.East)) {
			gc.move(unit.id(), Direction.East);
		}
	}
}	
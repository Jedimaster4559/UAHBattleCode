import bc.*;

class Worker {
	
	public static boolean canProcess(Unit unit) {
		if (unit.unitType() == UnitType.Worker) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		if (unit.location().mapLocation().getPlanet() == null) {
			return;
		}
		System.out.println("valid location");
		if (gc.karbonite() < 500 && gc.canHarvest(unit.id(), Direction.Center)) {
			System.out.println("Harvesting!");
			gc.harvest(unit.id(), Direction.Center);
		} else if (gc.canBlueprint(unit.id(), UnitType.Factory, Direction.Center)) {
			System.out.println("Blueprinting!");
			gc.blueprint(unit.id(), UnitType.Factory, Direction.Center);
		} else if (gc.canMove(unit.id(), Direction.West)) {
			System.out.println("Moving!");
			gc.moveRobot(unit.id(), Direction.West);
		}
	}
}	
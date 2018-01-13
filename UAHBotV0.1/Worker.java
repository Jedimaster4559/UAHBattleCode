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
		
		if (gc.karbonite() < 500 && gc.canHarvest(unit.id(), Direction.Center)) {
			System.out.println("Harvesting!");
			gc.harvest(unit.id(), Direction.Center);
		//} else if (gc.canBuild(unit.id()) {
		//	VecUnit factories = gc.senseUnitAtLocation(
		//			unit.location().mapLocation(), 50, UnitType.Factory);
		//	for (int i = 0; i < factories.size(); i++) {
		//		if (gc.canBuild(unit.id(), factories.get(i))) {
		//			gc.build(unit.id(), factories.get(i));
		//		}
		//	}
		} else if (gc.canBlueprint(unit.id(), UnitType.Factory, Direction.Center)) {
			System.out.println("Blueprinting!");
			gc.blueprint(unit.id(), UnitType.Factory, Direction.Center);
		} else if (gc.canMove(unit.id(), Direction.West)) {
			System.out.println("Moving!");
			Utilities.moveRandomDirection(unit, gc);
		}
	}
}	
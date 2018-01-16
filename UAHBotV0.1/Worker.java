import bc.*;

class Worker {
	static UnitType productionType;
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
                // factory logic
                else if ((Utilities.getNearbyBlueprint(unit, gc)!= null) &&(gc.canBuild(unit.id(),Utilities.getNearbyBlueprint(unit, gc))) && (Player.numFactories < 3)) // build
                {
                    System.out.println("Building");
                    gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));
                }
                else
                {   // blueprint logic
                    for(Direction direction:Path.directions)
                    {
                        if(gc.canBlueprint(unit.id(), productionType.Factory, direction))
                        {
                            System.out.println("Blueprinting");
                            gc.blueprint(unit.id(), productionType.Factory, direction);
                        }
                    }
                }
                
                // harvest logic
                if (gc.canHarvest(unit.id(), Direction.Center))
                {
			System.out.println("Harvesting");
			gc.harvest(unit.id(), Direction.Center);             
                }
                else
                {
                    Utilities.moveRandomDirection(unit, gc);
                }                 
	}      
}	
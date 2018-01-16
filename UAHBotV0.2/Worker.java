import bc.*;
import java.lang.Integer;

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
                else if ((Utilities.getNearbyBlueprint(unit, gc)!= Integer.MAX_VALUE) &&(gc.canBuild(unit.id(),Utilities.getNearbyBlueprint(unit, gc)))) // build
                {
                    System.out.println("Building");
                    gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));
                }
                else
                {   // blueprint logic
                    for(Direction direction:Path.directions)
                    {
                        if(gc.canBlueprint(unit.id(), productionType, direction))
                        {
                            System.out.println("Blueprinting");
                            gc.blueprint(unit.id(), productionType, direction);
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
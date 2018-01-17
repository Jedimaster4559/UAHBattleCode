import bc.*;
import java.lang.Integer;

class Worker {
	static UnitType productionType;
	static boolean isBuilding;
	
	public static boolean canProcess(Unit unit) {
		if (unit.unitType() == UnitType.Worker) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		if (!unit.location().isOnMap()) {
			return;
		}
		
        // factory logic
        if ((Utilities.getNearbyBlueprint(unit, gc)!= Integer.MAX_VALUE) &&
			(gc.canBuild(unit.id(),Utilities.getNearbyBlueprint(unit, gc)))) // build
        {
            //System.out.println("Building");
            gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));
            isBuilding = true;
        }
        else if(Player.numFactories <= 20)
        {   // blueprint logic
			productionType = Unit.Factory;
            for(Direction direction:Path.directions)
            {
                if(gc.canBlueprint(unit.id(), productionType, direction))
                {
                    //System.out.println("Blueprinting");
                    gc.blueprint(unit.id(), productionType, direction);
                    isBuilding = true;
                }
            }
        } else if (gc.round > 500) {
			productionType = Unit.Rocket;
            for(Direction direction:Path.directions)
            {
                if(gc.canBlueprint(unit.id(), productionType, direction))
                {
                    //System.out.println("Blueprinting");
                    gc.blueprint(unit.id(), productionType, direction);
                    isBuilding = true;
                }
            }
        }
		
        // harvest logic
        if (gc.canHarvest(unit.id(), Direction.Center))
        {
        	//System.out.println("Harvesting");
        	gc.harvest(unit.id(), Direction.Center);             
        }
        else if (!isBuilding)
        {
            Utilities.moveRandomDirection(unit, gc);
        }  
        isBuilding = false;
	}      
}	
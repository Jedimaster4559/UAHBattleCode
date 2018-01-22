import bc.*;
import java.lang.Integer;

public class Worker extends MobileUnit {
	
	static UnitType productionType;
	static boolean isBuilding;
	
	public Worker(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public static void process(Unit unit, GameController gc) {
		if (!unit.location().isOnMap()) {
			return;
		}
		
        // factory logic
        if ((Utilities.getNearbyBlueprint(unit, gc)!= Integer.MAX_VALUE) &&
			(gc.canBuild(unit.id(),Utilities.getNearbyBlueprint(unit, gc)))) // build
        {
			decideProductionType();
            //System.out.println("Building");
            gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));
            isBuilding = true;
			return;
        }
        else if(Worker.productionType != null &&
				(((gc.karbonite() + (950 - gc.round())) > 1000) 
				|| gc.round() > 500))
        {   // blueprint logic
			
            for(Direction direction:Path.directions)
            {
                if(gc.canBlueprint(unit.id(), Worker.productionType, direction))
                {
					//System.out.println("Bluprinting: " + Worker.productionType);
                    gc.blueprint(unit.id(), Worker.productionType, direction);
                    isBuilding = true;
					return;
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

		public decideProductionType() {
		if (gc.round() > 500) {
			productionType = UnitType.Rocket;
		}
		else {
			if (Player.numFactories >= factoryGoal) {
				productionType = null;
			}
			else {
				productionType = UnitType.Factory;
			}
		}
	}
}	
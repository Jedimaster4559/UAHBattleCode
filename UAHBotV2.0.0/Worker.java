import bc.*;
import java.lang.Integer;

public class Worker extends MobileUnit {
	
	UnitType productionType;
	boolean isBuilding = false;
	
	public Worker(Unit unit, GameController gc) {
		super(unit, gc);
		productionType = UnitType.Factory;
		isBuilding = false;
	}
	
	public void process() {

		if (!unit.location().isOnMap()) {
			return;
		}
		
        // factory logic

		int nearestBlueprintId = Utilities.getNearbyBlueprint(unit, gc);
        if ((nearestBlueprintId != Integer.MAX_VALUE) &&
			(gc.canBuild(unit.id(), nearestBlueprintId))) // build
        {
            gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));
            isBuilding = true;
			return;
        }
        else if((((gc.karbonite() + (950 - gc.round())) > 1000) 
				|| gc.round() > 500))
        {   // blueprint logic
			decideProductionType();
			if (productionType != null) {
				for(Direction direction:Path.directions)
				{
					if(gc.canBlueprint(unitId, productionType, direction))
					{
						try {
							gc.blueprint(unitId, productionType, direction);
							Unit blueprintUnit = gc.senseUnitAtLocation(currentLocation.add(direction));
							if (blueprintUnit.unitType() == UnitType.Factory) {
								Player.newUnits.add(new Factory(blueprintUnit, gc));
							} else {
								Player.newUnits.add(new Rocket(blueprintUnit, gc));
							}
							isBuilding = true;
						} catch (Exception e) {
							System.out.println("error blueprinting or making factory object");
							e.printStackTrace();
						}
					}
				}
			}

        }
		
        // harvest logic
        if (gc.canHarvest(unit.id(), Direction.Center))
        {

        	gc.harvest(unit.id(), Direction.Center);             
        }
        else if (!isBuilding)
        {
            Utilities.moveRandomDirection(unit, gc);
			unit = gc.unit(unitId);
			currentLocation = unit.location().mapLocation();
        }  
        isBuilding = false;
	}  

	public void decideProductionType() {
		if (gc.round() > 600) {
			productionType = UnitType.Rocket;
		}
		else {
			if (Player.numFactories >= Player.factoryGoal) {
				productionType = null;
			}
			else {
				productionType = UnitType.Factory;
			}
		}
	}

}	
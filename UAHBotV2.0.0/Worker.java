import bc.*;
import java.lang.Integer;

public class Worker extends MobileUnit {
	
	//The current production type (depending on how we are running this, is this still necessary)
	private UnitType productionType;
	
	
	//a statement to prevent the bot from moving away from its current construction project
	boolean isBuilding = false;
	
	public Worker(Unit unit, GameController gc) {
		super(unit, gc);
		productionType = UnitType.Factory;
		isBuilding = false;
	}
	
	public void process() {

		if (!unit.location().isOnMap()) {	//If the worker is not on the map, then do not process
			return;
		}
		
		//determines what the ID number of the nearest blueprint is
		int nearestBlueprintId = Utilities.getNearbyBlueprint(unit, gc);
		// Can we build the nearest blueprint
        if ((nearestBlueprintId != Integer.MAX_VALUE) &&
			(gc.canBuild(unit.id(), nearestBlueprintId))) 
        	{
            	gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));	//build
           		isBuilding = true;	//ensure we don't move this turn
				return;
        	}
        else if((((gc.karbonite() + (950 - gc.round())) > 1000) 
				|| gc.round() > 500))
		{   
			// blueprint logic
			decideProductionType();	//decide the current production type
			if (productionType != null) {
				for(Direction direction:Path.directions) //loop through all directions
				{
					//if we can blueprint, then try to blueprint
					if(gc.canBlueprint(unitId, productionType, direction))	
					{
						try {
							gc.blueprint(unitId, productionType, direction);
							Unit blueprintUnit = gc.senseUnitAtLocation(currentLocation.add(direction));	//gets the blueprint we just created as a unit
							if (blueprintUnit.unitType() == UnitType.Factory) {				
								Player.newUnits.add(new Factory(blueprintUnit, gc));
							} else {	//creates an object of the proper structure type
								Player.newUnits.add(new Rocket(blueprintUnit, gc));
							}
							isBuilding = true;	//ensure we don't move this turn
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
			//if the location we are standing on is harvestable, then harvest it       
			gc.harvest(unit.id(), Direction.Center); 
		}
		else if (!isBuilding)
		{
		    Utilities.moveRandomDirection(unit, gc);//if we are not moving, then blueprint right here
			unit = gc.unit(unitId);	//make sure we update our current position
			currentLocation = unit.location().mapLocation();	
		}  
		isBuilding = false;	//reset this variable at the end of processing
	}  

	public void decideProductionType() {
		if (gc.round() > 600) {		//basically, make sure we aren't planning to escape
			productionType = UnitType.Rocket;
		}
		else {
			//check out team production goals to determine if/what we should build
			if (Player.numFactories >= Player.factoryGoal) {
				productionType = null;
			}
			else {
				productionType = UnitType.Factory;
			}
		}
	}

}	

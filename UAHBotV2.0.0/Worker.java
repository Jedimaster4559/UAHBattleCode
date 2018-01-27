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
        else if(gc.round() < Player.stage) //pre-prep
		{
			
			if (gc.karbonite() < Player.highKarboniteGoal) {
				mine();
			} 
			if (Player.numFactories < Player.highFactoryGoal) {
				boolean worked = blueprintType(UnitType.Factory);
			} 
			if (!Player.initRocketBuilt) {
				boolean worked = blueprintType(UnitType.Rocket);
			}
			if (gc.karbonite() < Player.lowKarboniteGoal) {
				mine();
			}
			if (Player.numFactories < Player.lowFactoryGoal) {
				boolean worked = blueprintType(UnitType.Factory);
			}
			mine();
			/*
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
							//gets the blueprint we just created as a unit
							Unit blueprintUnit = gc.senseUnitAtLocation(currentLocation.add(direction));
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
			*/

		} else {
			if (Player.numRockets < Player.rocketGoal) {
				boolean worked = blueprintType(UnitType.Rocket);
			}
			if (gc.karbonite() < Player.lowKarboniteGoal) {
				mine();
			}
			if (Player.numFactories < Player.highFactoryGoal && !LogicHandler.escaping) {
				boolean worked = blueprintType(UnitType.Factory);
			}
			mine();
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
	
	public boolean blueprintType(UnitType type) {
		for(Direction direction:Path.directions) //loop through all directions
		{
			if(gc.canBlueprint(unitId, type, direction))	
			{
				try {
					gc.blueprint(unitId, type, direction);
					//gets the blueprint we just created as a unit
					Unit blueprintUnit = gc.senseUnitAtLocation(currentLocation.add(direction));
					if (type == UnitType.Factory) {				
						Player.newUnits.add(new Factory(blueprintUnit, gc));
					} else {	//creates an object of the proper structure type
						Player.newUnits.add(new Rocket(blueprintUnit, gc));
					}
					isBuilding = true;	//ensure we don't move this turn
					return true;
				} catch (Exception e) {
					System.out.println("error blueprinting or making factory object");
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public void mine() {
		if (gc.canHarvest(unit.id(), Direction.Center))
		{
			//if the location we are standing on is harvestable, then harvest it       
			gc.harvest(unit.id(), Direction.Center); 
		}
	}

	/*
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
	*/
}	

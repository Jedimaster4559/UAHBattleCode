import bc.*;
import java.lang.Integer;

public class Worker extends MobileUnit {
	
	UnitType productionType;	//The current production type (depending on how we are running this, is this still necessary)
	boolean isBuilding = false;	//a statement to prevent the bot from moving away from its current contruction project
	boolean isHarvesting = false;
	
	public Worker(Unit unit, GameController gc) {
		super(unit, gc);
		productionType = UnitType.Factory;
		isBuilding = false;
		isHarvesting = false;
	}
	
	public void process() {

		if (!unit.location().isOnMap()) {	//If the worker is not on the map, then do not process
			return;
		}
		
		int nearestBlueprintId = Utilities.getNearbyBlueprint(unit, gc); //determines what the ID number of the nearest blueprint is
        if ((nearestBlueprintId != Integer.MAX_VALUE) &&
			(gc.canBuild(unit.id(), nearestBlueprintId))) // Can we build the nearest blueprint
        	{
            	gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));	//build
           		isBuilding = true;						//ensure we don't move this turn
				return;
        	}
        else if((((gc.karbonite() + (950 - gc.round())) > 1000) 
				|| gc.round() > 500))
		{   
			// blueprint logic
			decideProductionType();					//decide the current production type
			if (productionType != null) {
				for(Direction direction:Path.directions)	//loop through all directions
				{
					if(gc.canBlueprint(unitId, productionType, direction))			//if we can blueprint, then try to blueprint
					{
						try {
							gc.blueprint(unitId, productionType, direction);
							Unit blueprintUnit = gc.senseUnitAtLocation(currentLocation.add(direction));	//gets the blueprint we just created as a unit
							if (blueprintUnit.unitType() == UnitType.Factory) {				
								Player.newUnits.add(new Factory(blueprintUnit, gc));
							} else {									//creates an object of the proper structure type
								Player.newUnits.add(new Rocket(blueprintUnit, gc));
							}
							isBuilding = true;								//ensure we don't move this turn
							dest = blueprintUnit.location().mapLocation();
							isHarvesting = false;
						} catch (Exception e) {
							System.out.println("error blueprinting or making factory object");
							e.printStackTrace();
						}
					}
				}
			}

		}
        else if(!isHarvesting){
        	dest = null;
        	isBuilding = false;
        }

		// harvest logic
		if (gc.canHarvest(unitId, Direction.Center))
		{
			gc.harvest(unitId, Direction.Center);      //if the location we are standing on is harvestable, then harvest it
			KarboniteLocation thisDeposit = getKarboniteLocation();
			if(thisDeposit != null){
				thisDeposit.setKarbonite(gc.karboniteAt(currentLocation));
				if(thisDeposit.getKarbonite() <= 0){
					if(thisDeposit.getMapLocation() == currentLocation){
						isHarvesting = false;
						dest = null;
					}
					Player.karboniteLocations.remove(thisDeposit);
				}
			}
		}
		else if (!isBuilding && !isHarvesting)
		{
		    //Utilities.moveRandomDirection(unit, gc);		//if we are not moving, then blueprint right here
			if(unit.movementHeat() < 10){
				if(dest == null){
					setBestKarbonite();
				}
				Path.determinePathing(unit, dest, gc);
				unit = gc.unit(unitId);					//make sure we update our current position
				currentLocation = unit.location().mapLocation();	
			}
			
		}
		
		if (!isBuilding){
			if(unit.movementHeat() < 10){
				if(dest != null){
					Path.determinePathing(unit, dest, gc);
				} else {
					Utilities.moveRandomDirection(unit, gc);
				}
				unit = gc.unit(unitId);
				currentLocation = unit.location().mapLocation();
			}
		}
	}  
	
	public KarboniteLocation getKarboniteLocation(){
		for(KarboniteLocation location:Player.karboniteLocations){
			if(currentLocation.toString().equals(location.getMapLocation().toString())){
				return location;
			}
		}
		return null;
		
	}
	
	public void setBestKarbonite() {
		int index = 0;
		int counter = 0;
		int highest = Integer.MIN_VALUE;
		for(KarboniteLocation location:Player.karboniteLocations){
			if(currentLocation != null && location.getDistance(currentLocation) != 0 && location.karboniteAmount/location.getDistance(currentLocation) > highest){
				index = counter;
			}
			counter++;
		}
		KarboniteLocation bestLocation = Player.karboniteLocations.get(index);
		dest = bestLocation.mapLocation;
		isHarvesting = true;
		System.out.println("Setting Best Karbonite Location");
	}

	public void decideProductionType() {
		if (gc.round() > 600) {					//basically, make sure we aren't planning to escape
			productionType = UnitType.Rocket;
		}
		else {
			if (Player.numFactories >= Player.factoryGoal) {//check out team production goals to determine if/what we should build
				productionType = null;
			}
			else {
				productionType = UnitType.Factory;
			}
		}
	}

}	

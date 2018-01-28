import bc.*;
import java.lang.Integer;

public class Worker extends MobileUnit {
	
	//The current production type (depending on how we are running this, is this still necessary)
	private UnitType productionType;
	
	//a statement to prevent the bot from moving away from its current contruction project
	private boolean isBuilding = false;	
	private boolean isHarvesting = false;

	
	public Worker(Unit unit, GameController gc) {
		super(unit, gc);
		isBuilding = false;
		isHarvesting = false;
	}
	
	public void process() {

		if (!unit.location().isOnMap()) {	//If the worker is not on the map, then do not process
			return;
		}

		currentLocation = unit.location().mapLocation();

		//determines what the ID number of the nearest blueprint is
		int nearestBlueprintId = Utilities.getNearbyBlueprint(unit, gc);
		// Can we build the nearest blueprint
        if ((nearestBlueprintId != Integer.MAX_VALUE) &&
			(gc.canBuild(unit.id(), nearestBlueprintId))) 
        {
			gc.build(unit.id(),Utilities.getNearbyBlueprint(unit, gc));	//build
			isBuilding = true;	//ensure we don't move this turn
			return;

        } else {
			isBuilding = false;
			dest = null;
		}
			
		if(gc.round() < 600) //pre-prep
		{
			
			if (gc.karbonite() < Player.highKarboniteGoal) {
				mine();
			} 
			if (Player.numFactories < Player.highFactoryGoal) {
				boolean worked = blueprintType(UnitType.Factory);
				//if (worked) System.out.println("Built HPfactory");
			} 
			if (!Player.initRocketBuilt) {
				boolean worked = blueprintType(UnitType.Rocket);
				if (worked) {
					Player.initRocketBuilt = true;
					//System.out.println("Built initRocket");

				}
			}
			if (gc.karbonite() < Player.lowKarboniteGoal) {
				mine();
			}
			if (Player.numFactories < Player.lowFactoryGoal) {
				boolean worked = blueprintType(UnitType.Factory);
				//if (worked) System.out.println("Built LPfactory");
			}
			mine();

		//post-prep logic
		} else {
			if (Player.numRockets < Player.rocketGoal) {
				boolean worked = blueprintType(UnitType.Rocket);
				//if (worked) System.out.println("Built escape rocket");
			}
			if (gc.karbonite() < Player.lowKarboniteGoal) {
				mine();
			}
			if (Player.numFactories < Player.highFactoryGoal && !LogicHandler.escaping) {
				boolean worked = blueprintType(UnitType.Factory);
				//if (worked) System.out.println("Built HPfactory escaping");
			}
			mine();
		}
		
		if (!isBuilding){
			if(unit.movementHeat() < 10){
				if(dest != null && gc.karboniteAt(currentLocation) == 0){
					Path.determinePathing(unit, dest, gc);
				} else if (gc.karboniteAt(currentLocation) == 0){
					Utilities.moveRandomDirection(unit, gc);
				}
				unit = gc.unit(unitId);
				currentLocation = unit.location().mapLocation();
			} 	
		}
	}
	
	public boolean blueprintType(UnitType type) {
		for(Direction direction:Path.directions) //loop through all directions
		{
			if(gc.canBlueprint(unitId, type, direction) && 
					gc.karboniteAt(currentLocation.add(direction)) <= 0)	
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
					isBuilding = true;								//ensure we don't move this turn
					dest = blueprintUnit.location().mapLocation();
					isHarvesting = false;
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
			} else {
				isHarvesting = false;
				dest = null;
			}
		}
		
		//Utilities.moveRandomDirection(unit, gc);		//if we are not moving, then blueprint right here
		else if (!isBuilding && !isHarvesting)
		{
			if(unit.movementHeat() < 10){
				if(dest == null){
					setBestKarbonite();
				}
				Path.determinePathing(unit, dest, gc);
				unit = gc.unit(unitId);					//make sure we update our current position
				currentLocation = unit.location().mapLocation();	
			}
			isHarvesting = true;
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
		//System.out.println("Setting Best Karbonite Location");

	}
}	

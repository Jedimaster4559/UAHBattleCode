import bc.*;

class Rocket {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Rocket) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		if(unit.structureGarrison().size() == 8 || ((unit.structureGarrison().size() * 2 + gc.round()) > 745)){
			findLandableSpot(unit, gc);
    }
		//Attempts to unload all bots
		if(unit.rocketIsUsed() == 1) {
			if(unit.structureGarrison().size() > 0)
			{
				Direction[] directions = Direction.values();
				for(Direction direction:directions){
					if(gc.canUnload(unit.id(), direction)) {
						gc.unload(unit.id(), direction);
						if(unit.structureGarrison().size() == 0){
							break;
						}
					}
				}
			}
		}
	}
	
	public static void findLandableSpot(Unit unit, GameController gc) {
		MapLocation randomLocation;
		int randx;
		int randy;
		while(true){
			randx = Player.rand.nextInt((int)Path.mars.getWidth()-1);
			randy = Player.rand.nextInt((int)Path.mars.getHeight()-1);
			randomLocation = new MapLocation(Planet.Mars, randx,randy);
			if(gc.canLaunchRocket(unit.id(), randomLocation) && 
					(Path.mars.isPassableTerrainAt(randomLocation) == 1)){
				System.out.println("Launching rocket!");
				gc.launchRocket(unit.id(), randomLocation);
				break;
			}
		}
	}
}
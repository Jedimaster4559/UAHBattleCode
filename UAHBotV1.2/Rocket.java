import bc.*;

class Rocket extends Structure {

	public Rocket(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
		System.out.println("Rocket exists " + unitId + ":" + gc.round());
		if (unit.location().isOnMap() && unit.location().mapLocation().getPlanet() == Planet.Mars) {
			System.out.println("On mars");
		}
		
		if (unit.structureIsBuilt() == 0) return;
		
		if (unit.rocketIsUsed() == 0) {
			if(unit.structureGarrison().size() == 8 ||
					((unit.structureGarrison().size() * 2 + gc.round()) > 745))
			{
				findLandableSpot(unit, gc);
			}
		
		//Attempts to unload all bots
		} else {
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
	
	public void findLandableSpot(Unit unit, GameController gc) {
		MapLocation randomLocation;
		int randx;
		int randy;
		for (int i = 0; i < 100; i++) {
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
import bc.*;

class Rocket {
	public static boolean canProcess(Unit unit) {
		if(unit.unitType() == UnitType.Rocket) {
			return true;
		}
		return false;
	}
	
	public static void process(Unit unit, GameController gc) {
		if(unit.structureGarrison().size() == 8 || (gc.round() > 740 && unit.structureGarrison().size() > 4) || gc.round() > 745){
			findLandableSpot(unit, gc);
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
			if(gc.canLaunchRocket(unit.id(), randomLocation) && (Path.mars.isPassableTerrainAt(randomLocation) == 1)){
				gc.launchRocket(unit.id(), randomLocation);
			}
		}
	}
	
}
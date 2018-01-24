import bc.*;


class Rocket extends Structure {

	public Rocket(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
		MapLocation currentLocation = unit.location().mapLocation();
		
		if(!(currentLocation.getPlanet() == Planet.Earth || currentLocation.getPlanet() ==Planet.Mars)){
			return;
		}
		
		if(!(unit.location().mapLocation().getPlanet() == Planet.Mars)){
			if (unit.structureIsBuilt() == 0) return;
		}
		
		if (unit.location().mapLocation().getPlanet() == Planet.Earth && unit.rocketIsUsed() == 0) {
			if(unit.structureGarrison().size() == 8 ||
					((unit.structureGarrison().size() * 2 + gc.round()) > 745))
			{
				findLandableSpot(unit, gc);
			}
		
		}
		
		if(currentLocation.getPlanet() == Planet.Mars && unit.structureGarrison().size() > 0)
		{
			Direction[] directions = Direction.values();
			for (Direction direction : directions) {
				if (gc.canUnload(unit.id(), direction)) {
					int unloadId = unit.structureGarrison().get(0);
					Unit unloadUnit = gc.unit(unloadId);
					UnitType unloadType = unloadUnit.unitType();
					switch (unloadType) {
						case Worker:
							Worker newWorker = new Worker(unloadUnit, gc);
							Player.newUnits.add(newWorker);
							break;
						case Knight:
							Knight newKnight = new Knight(unloadUnit, gc);
							Player.newUnits.add(newKnight);
							break;
						case Ranger:
							Ranger newRanger = new Ranger(unloadUnit, gc);
							Player.newUnits.add(newRanger);
							break;
						case Mage:
							Mage newMage = new Mage(unloadUnit, gc);
							Player.newUnits.add(newMage);
							break;
						case Healer:
							Healer newHealer = new Healer(unloadUnit, gc);
							Player.newUnits.add(newHealer);
							break;
					}
						
					gc.unload(unit.id(), direction);
					if(unit.structureGarrison().size() == 0){
						break;


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
				gc.launchRocket(unit.id(), randomLocation);
				break;
			}
		}
	}

}
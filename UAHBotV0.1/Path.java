import bc.*;

class Path {
	static PlanetMap earth;
	static PlanetMap mars;
	static int mapHeight;
	static int mapLength;
	static Direction[] directions;
	static int[] tryRotate;
	
	static void initializePathing(GameController gc){
		earth = gc.startingMap(Planet.Earth);
		mars = gc.startingMap(Planet.Mars);
		directions = new Direction[]{Direction.North, Direction.Northeast, Direction.East, Direction.Southeast, Direction.South, Direction.Southwest, Direction.West, Direction.Northwest};
		tryRotate = new int[]{0, -1, 1, -2, 2};
	}
	
	static void fuzzyPath(Unit unit, MapLocation dest, GameController gc){
		MapLocation start = unit.location().mapLocation();
		Direction toward = start.directionTo(dest);
		for(int tilt:tryRotate){
			Direction currentDirection = rotate(toward, tilt, gc);
			if(gc.canMove(unit.id(), currentDirection)){
				gc.moveRobot(unit.id(), currentDirection);
			}
		}
	}
	
	static Direction rotate(Direction dir, int amount, GameController gc){
		int index = Utilities.getIndex(directions, dir);
		return directions[(index+amount)%8];
	}
	
	
	
}
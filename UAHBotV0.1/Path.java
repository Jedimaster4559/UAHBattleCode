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
	
	//Fuzzy Path attempts to take the shortest path and can get around really
	//simple obstacles. It is really low processing but will occasionally fail
	static void fuzzyPath(Unit unit, MapLocation dest, GameController gc){
		MapLocation start = unit.location().mapLocation();
		Direction toward = start.directionTo(dest);
		for(int tilt:tryRotate){
			Direction currentDirection = rotate(toward, tilt, gc);
			if(gc.canMove(unit.id(), currentDirection)){
				gc.moveRobot(unit.id(), currentDirection);
				break;
			}
		}
	}
	
	//Method to take a direction and rotate it by a certain amount.
	//Positive amounts rotate clockwise and negative rotate counter clockwise
	static Direction rotate(Direction dir, int amount, GameController gc){
		int index = Utilities.getIndex(directions, dir);
		return directions[(index+amount)%8];
	}
	
	//Bug path is a much more accurate method of going to a destination
	//but has the problem that it may not be the most intelligent. In most
	//cases, however, this will be the best method due to a combination of
	//its simplicity, and it's accuracy.
	static void bugPath(Unit unit, MapLocation dest, GameController gc){
		MapLocation start = unit.location().mapLocation();
		Direction toward = start.directionTo(dest);
		if(gc.canMove(unit.id(), toward)){
			gc.moveRobot(unit.id(), toward);
		} else {
			for(int i = 0; i<8; i++){
				toward = rotate(toward, 1, gc);
				if(gc.canMove(unit.id(), toward)){
					gc.moveRobot(unit.id(), toward);
					break;
				}
			}
		}
			
		
	}
	
	
	
}
import bc.*;

/**
 * Please note that this class is very old and
 * is currently being updated with numerous upgrades.
 * These include an A* Algorithm that will be the 
 * main pathing method.
**/

class Path {
	static PlanetMap earth;
	static PlanetMap mars;
	static int earthMapHeight;
	static int earthMapWidth;
	static Direction[] directions;
	static int[] tryRotate;
	static int[][] map;
	static boolean frenzy;
	
	public static void initializePathing(GameController gc){
		
		//Initialize all the variables we may need for successful pathing
		earth = gc.startingMap(Planet.Earth);
		mars = gc.startingMap(Planet.Mars);
		directions = new Direction[]{Direction.North, Direction.Northeast, Direction.East, Direction.Southeast, Direction.South, Direction.Southwest, Direction.West, Direction.Northwest};
		tryRotate = new int[]{0, -1, 1, -2, 2};
		earthMapHeight = (int)earth.getHeight();
		earthMapWidth = (int)earth.getWidth();
		frenzy = false;
		Utilities.invertPositions(gc);
	}
	
	//Fuzzy Path attempts to take the shortest path and can get around really
	//simple obstacles. It is really low processing but will occasionally fail
	public static void fuzzyPath(Unit unit, MapLocation dest, GameController gc){
		if(dest == null){
			return;
		}
		MapLocation start = unit.location().mapLocation();			//sets the current location of the robot
		Direction toward = start.directionTo(dest);					//sets a direction toward the destination
		for(int tilt:tryRotate){									//rotate through directions all directions until broken
			Direction currentDirection = rotate(toward, tilt, gc);	//set the current direction
			if(gc.canMove(unit.id(), currentDirection)){			//see if we can move  there
				gc.moveRobot(unit.id(), currentDirection);			//Move that robot to that location
				break;												//end function
			}
		}
	}
	
	//Method to take a direction and rotate it by a certain amount.
	//Positive amounts rotate clockwise and negative rotate counter clockwise
	public static Direction rotate(Direction dir, int amount, GameController gc){
		int index = Utilities.getIndex(directions, dir);		//set the amount we want to rotate
		return directions[(index+amount+8)%8];					//return that as a direction
	}
	
	//Bug path is a much more accurate method of going to a destination
	//but has the problem that it may not be the most intelligent. In most
	//cases, however, this will be the best method due to a combination of
	//its simplicity, and it's accuracy.
	public static void bugPath(Unit unit, MapLocation dest, GameController gc){
		MapLocation start = unit.location().mapLocation();		//create starting location
		Direction toward = start.directionTo(dest);				//set a direction towards the destination
		
		//determine if we can move in that direction
		if(gc.canMove(unit.id(), toward) && gc.isMoveReady(unit.id())){	
			gc.moveRobot(unit.id(), toward);					//move that direction
		} else {												//if we cant
			for(int i = 0; i<8; i++){							//loop through all of the directions in clockwise order
				toward = rotate(toward, 1, gc);					//set each rotation direction
				//determine if we can move there
				if(gc.canMove(unit.id(), toward) && gc.isMoveReady(unit.id())){				
					gc.moveRobot(unit.id(), toward);			//move the robot to that location
					break;										//end function
				}
			}
		}
	}
	
	//Method that will move a unit from its current point to the frenzy
	//along the shortest path
	public static void frenzyPath(Unit unit, GameController gc){
		//create variables needed for this path
		MapLocation currentLocation = unit.location().mapLocation();
		int unitx = currentLocation.getX();
		int unity = currentLocation.getY();
		int current = map[unity][unitx];
		
		//loop through and search for a point closer to the destination
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j<=1; j++){
				MapLocation dest = new MapLocation(Planet.Earth, i, j);
				if(gc.canMove(unit.id(), currentLocation.directionTo(dest)) && map[j][i] == current - 1){
					gc.moveRobot(unit.id(), currentLocation.directionTo(dest));
					return;
				}
			}
		}
		
		//loop through and search for a point an equal distance from the destination
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j<=1; j++){
				MapLocation dest = new MapLocation(Planet.Earth, i, j);
				if(gc.canMove(unit.id(), currentLocation.directionTo(dest)) && map[j][i] == current && !dest.equals(currentLocation)){
					gc.moveRobot(unit.id(), currentLocation.directionTo(dest));
					return;
				}
			}
		}
	}
	
	
	public static void initializeFrenzyPath(MapLocation dest, GameController gc){
		map = new int[earthMapHeight][earthMapWidth];	//create an empty map
		recursiveFillMap(dest, 1, gc);					//recursively fill above map
		frenzy = true;									//turn on frenzy mode
		
	}
	
	public static void turnOffFrenzy(){
		frenzy = false;
	}
	
	static void recursiveFillMap(MapLocation dest, int i, GameController gc){
		//set x any y variables
		int destx = dest.getX();
		int desty = dest.getY();
		
		//loop through all surrounding points
		for(int x = -1; x<=1; x++){
			for(int y = -1; y<1; y++){
				MapLocation currentLocation = new MapLocation(Planet.Earth,destx+x,desty+y);			//set current grid point
				if(earth.onMap(currentLocation) && earth.isPassableTerrainAt(currentLocation) > 0){		//is actually a good point
					if(i<map[desty+y][destx+x]){														//is not already set at a better number
						map[desty+y][destx+x] = i;														//set to current length
						recursiveFillMap(new MapLocation(Planet.Earth,destx+y,desty+x),i+1,gc);			//iterate off of that point
					}
				}
			}
		}
	}
	
	//Method designed to be used to determine and run the best pathing algorithm
	//Should be used in the case where you
	static void determinePathing(Unit unit, MapLocation dest, GameController gc){
		//UnitType type = unit.unitType();
		if(dest == null || gc.planet() == Planet.Mars){
			Utilities.moveRandomDirection(unit, gc);
		}
		if (gc.isMoveReady(unit.id())) {
			fuzzyPath(unit, dest, gc);
		}
	}
	
	static MapLocation setDest(Unit unit, boolean mode, GameController gc){
		UnitType type = unit.unitType();
		MapLocation dest;
		if(mode){
			dest = Utilities.enemyStartLocations[0];
		} else {
			dest = null;
		}
		return dest;
	}
}

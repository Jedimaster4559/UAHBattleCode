import bc.*;


/*Notes
Ant pathing
flag bad paths asn < 0
if statement only go on paths that have value >= 0
*/

class Path 
{
	List Open = new ArrayList(); //locations we can visit
	List Closed = new ArrayList(); //locations we can't visit
	
	static PlanetMap earth;
	static PlanetMap mars;
	
	static int earthMapHeight;
	static int earthMapWidth;
	static int earthMapSize;
	
	static int marsMapHeight;
	static int marsMapWidth;
	static int marsMapSize;
	
	public static int diagonalWeight = 14;
	public static int vHWeight = 10; 	//Vertical and Horizontal weight
	public int currentLocation;
	
	public bool dropPheromones = false;
	
	public static void initializePathing(GameController gc)
	{				
		//initialize Earth variables
		earth = gc.startingMap(Planet.Earth);
		earthMapHeight = (int)earth.getHeight();
		earthMapWidth = (int)earth.getWidth();
		earthMapSize = (int)earthMapWidth * earthMapHeight;
		
		//intitialize Mars variables
		mars = gc.startingMap(Planet.Mars);
		marsMapHeight = (int)mars.getHeight();
		marsMapWidth = (int)mars.getWidth();
		marsMapSize = (int)marsMapWidth * marsMapHeight;
		
	}
	
	class cell(MapLocation x, MapLocation y)
	{
		//MapLocation x;
		//MapLocation y;
		double f, g, h;
		//bool passable = true;	
	}
	
	public static void aStar(Unit unit, MapLocation dest, GameController gc, bool dropPheromones)
	{
		MapLocation Start = unit.location().mapLocation();
		Open.add(unit.mapLocation());
		
		while(unit.location().mapLocation() != dest)
		{
			if(isPassableTerrainAt(add(North)))
				Open.add(cell(MapLocation.getX(add(North)), MapLocation.getY(add(South))));
			if(isPassableTerrainAt(add(Northeast)))
				Open.add(cell(MapLocation.getX(add(Northeast)), MapLocation.getY(add(South))));
			if(isPassableTerrainAt(add(Northwest)))
				Open.add(add(Northwest));
			if(isPassableTerrainAt(add(West)))
				Open.add(add(West));
			if(isPassableTerrainAt(add(Southeast)))
				Open.add(add(Southeast));
			if(isPassableTerrainAt(add(South)))
				Open.add(add(South));
			if(isPassableTerrainAt(add(Southwest)))
				Open.add(add(Southwest));
			if(isPassableTerrainAt(add(West)))
				Open.add(add(West));
			
		}
	}
	
}

/*
Helpful directories: Direction, GameMap, Location, MapLocation, PlanetMap

The biggest challenge is using everything they gave us to navigate their map. Here is some pseudo code

Initialize 2 lists, open and closed. Add starting node to Open list
LOOP
	Check surrounding nodes to see if they are open. Can use isPassableTerrainAt function.
		if nodes are passable, add to Open List as cell with f, g, and h costs. If not passable, add them to closed
			g is the distance from the current location to the starting location, h is from current location to destination
			f = g+h
			horizontal/vertical movements should be weighted as 10, diagonal as 14
		
		add current node to closed list, move to node in Open list with lowest f cost.
END LOOP
When the loop ends, You should have a path

for ant colony optimization (not yet tested or fully researched) create a 3rd list and somehow add the path taken to it. Then have the path for the other units to follow

RESOURCES:
A* code videos
https://www.youtube.com/watch?v=WxYqSKPU4C4
https://www.youtube.com/watch?v=mZfyt03LDH4
A* with units 
https://www.youtube.com/watch?v=dn1XRIaROM4
A* pseudo and real code with explanations
https://www.geeksforgeeks.org/a-search-algorithm/
Visual interactive representation 
https://qiao.github.io/PathFinding.js/visual/
A* explanation with pseudo code
https://www.youtube.com/watch?v=-L-WgKMFuhE&t=614s
*/
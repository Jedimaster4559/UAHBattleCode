import bc.*;
public class KarboniteLocation {
	MapLocation mapLocation;
	GameController gc;
	long karboniteAmount;
	long lastVisited;
	
	/**
	 * Constructor for karbonite locations. This sets the amount of of karbonite, and when it
	 * was last visited.
	 * 
	 * @param mapLocation
	 * @param gc
	 */
	public KarboniteLocation(MapLocation mapLocation, GameController gc) {
		this.mapLocation = mapLocation;
		this.gc = gc;
		if(gc.planet() == Planet.Earth){
			karboniteAmount = Path.earth.initialKarboniteAt(mapLocation);
		} else {
			karboniteAmount = Path.mars.initialKarboniteAt(mapLocation);
		}
		lastVisited = gc.round();
	}
	
	/**
	 * getter method for the location of the karbonite	
	 * 
	 * @return
	 */
	public MapLocation getMapLocation(){
		return mapLocation;
	}
	
	/**
	 * a method that will calculate the distance between this karbonite
	 * and another location
	 * 
	 * @param currentLocation
	 * @return
	 */
	public long getDistance(MapLocation currentLocation){
		return currentLocation.distanceSquaredTo(mapLocation);
	}
	
	/**
	 * setter method for the amount of karbonite left at this spot
	 * 
	 * @param amount
	 */
	public void setKarbonite(long amount){
		karboniteAmount = amount;
	}
	
	/**
	 * getter method for the amount of karbonite left
	 * at this location
	 * 
	 * @return
	 */
	public long getKarbonite(){
		return karboniteAmount;
	}
	
	/**
	 * setter method for when this location was last visited.
	 */
	public void setLastVisited(){
		lastVisited = gc.round();
	}
	
	/**
	 * getter method for when this location was last visited.
	 * 
	 * @return
	 */
	public long getLastVisited(){
		return lastVisited;
	}
	
	
}

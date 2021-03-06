import bc.*;
public class KarboniteLocation {
	MapLocation mapLocation;
	GameController gc;
	long karboniteAmount;
	long lastVisited;
	
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
	
	public MapLocation getMapLocation(){
		return mapLocation;
	}
	
	public long getDistance(MapLocation currentLocation){
		return currentLocation.distanceSquaredTo(mapLocation);
	}
	
	public void setKarbonite(long amount){
		karboniteAmount = amount;
	}
	
	public long getKarbonite(){
		return karboniteAmount;
	}
	
	public void setLastVisited(){
		lastVisited = gc.round();
	}
	
	public long getLastVisited(){
		return lastVisited;
	}
	
	
}

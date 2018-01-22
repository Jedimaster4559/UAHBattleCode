import bc.*;

abstract class MobileUnit extends UAHUnit {
	
	protected MapLocation currentLocation;
	protected MapLocation dest;
	
	public MobileUnit(Unit unit, GameController gc) {
		super(unit, gc);
		currentLocation = unit.location().mapLocation();
		dest = null;
	}
	
	public MapLocation getCurrentLocation() {
		return currentLocation;
	}
	
	public void setCurrentLocation(MapLocation newLocation) {
		currentLocation = newLocation;
	}
	
	public MapLocation getDest() {
		return dest;
	}
	
	public void setDest(MapLocation newDest) {
		dest = newDest;
	}
	
	public abstract void process();
}
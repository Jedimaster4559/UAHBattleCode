import bc.*;

abstract class MobileUnit extends UAHUnit {
	
	protected MapLocation currentLocation;	//The current location of a mobile unit
	protected MapLocation dest;		//The destination of a mobile unit
	protected boolean enemy;
	
	public MobileUnit(Unit unit, GameController gc) {
		super(unit, gc);
		if (unit.location().isOnMap()) {				//attempts to set the bots starting location
			currentLocation = unit.location().mapLocation();
		} else {
			currentLocation = null;
		}
		dest = null;							//bot has no destination when it is first created
		enemy = false;
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
	
	public boolean getEnemy() {
		return enemy;
	}
	
	public void setEnemy(boolean mode) {
		enemy = mode;
	}
	
	public abstract void process();
}

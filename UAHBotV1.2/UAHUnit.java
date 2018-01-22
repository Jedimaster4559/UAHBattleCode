import bc.*;

public abstract class UAHUnit {
	
	private Unit unit;
	private int unitId;
	private MapLocation currentLocation;
	private MapLocation dest;
	
	public UAHUnit(Unit unit) {
		this.unit = unit;
		unitId = unit.id();
		currentLocation = unit.location().mapLocation();
		dest = null;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public int getUnitId() {
		return unitId;
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
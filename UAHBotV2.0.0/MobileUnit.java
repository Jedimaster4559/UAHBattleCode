import bc.*;

/**
 * abstract class for mobile units.
 * This allows us to extend our standard
 *
 */
abstract class MobileUnit extends UAHUnit {
	
	protected MapLocation currentLocation;	//The current location of a mobile unit
	protected MapLocation dest;		//The destination of a mobile unit
	protected boolean enemy;
	
	/**
	 * Constructor for mobile unit. The big difference from
	 * This and the regular UAHUnit Constructor is that this
	 * allows for establishing a destination, the currentlocation,
	 * and if it should be moving towards the enemy.
	 * 
	 * @param unit
	 * @param gc
	 */
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
	
	/**
	 * getter method for the current location
	 * 
	 * @return current location of the bot
	 */
	public MapLocation getCurrentLocation() {
		return currentLocation;
	}
	
	/**
	 * setter method for the current location
	 * 
	 * @param newLocation
	 */
	public void setCurrentLocation(MapLocation newLocation) {
		currentLocation = newLocation;
	}
	
	/**
	 * getter method for the destination of the bot
	 * 
	 * @return destination of the bot
	 */
	public MapLocation getDest() {
		return dest;
	}
	
	/**
	 * setter method for the destination of the bot
	 * 
	 * @param newDest
	 */
	public void setDest(MapLocation newDest) {
		dest = newDest;
	}
	
	/**
	 * getter method for the enemy pathing status
	 * 
	 * @return boolean pathing setting status
	 */
	public boolean getEnemy() {
		return enemy;
	}
	
	/**
	 * sets the enemy pathing status
	 * 
	 * @param mode
	 */
	public void setEnemy(boolean mode) {
		enemy = mode;
	}
	
	public abstract void process();
}

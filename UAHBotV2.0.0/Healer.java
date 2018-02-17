import bc.*;

/**
 * This is a class to contain all processing specific to the Healer class.
 * Healers should never be created so there is no processing for them, but
 * this class still exists so that an error is not thrown in the case one
 * is made.
 *
 */
class Healer extends MobileUnit {
	
	/**
	 * Constructor for the Healer. Requires the unit and the GameController
	 * 
	 * @param unit
	 * @param gc
	 */
	public Healer(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	/**
	 * Process method for the healer. This is empty since healers should not exist.
	 */
	public void process() {


		
	}
	
}
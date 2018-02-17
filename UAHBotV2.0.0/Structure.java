import bc.*;

/**
 * Class of structure that extends our standard unit class.
 * Right now, it doesn't add anything, however, if a need for
 * it comes up, we will already have things set up properly
 *
 */
abstract class Structure extends UAHUnit {
	
	/**
	 * Constructor for the structure class. Right now,
	 * it just calls the UAHUnit constructor.
	 * 
	 * @param unit
	 * @param gc
	 */
	public Structure(Unit unit, GameController gc) {
		super(unit, gc);
	}
}
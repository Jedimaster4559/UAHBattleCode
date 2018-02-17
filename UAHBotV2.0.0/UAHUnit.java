import bc.*;

/**
 * Basic abstract class for all units.
 * 
 *
 */
public abstract class UAHUnit {
	
	protected GameController gc;
	protected Unit unit;
	protected int unitId;
	protected UnitType UAHUnitType;
	
	/**
	 * Constructor for all units. This stores several pieces
	 * of important information such as unit id, unit, and unit type
	 * 
	 * @param unit
	 * @param gc
	 */
	public UAHUnit(Unit unit, GameController gc) {
		this.unit = unit;
		unitId = unit.id();
		UAHUnitType = unit.unitType();
		this.gc = gc;
	}
	
	/**
	 * getter method that returns the unit
	 * @return unit
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * getter method for unit id
	 * @return
	 */
	public int getUnitId() {
		return unitId;
	}
	
	/**
	 * Method that checks if a unit is alive or not.
	 * This is super inefficient but it works. Basically, if the unit
	 * is dead, it throws an error that we catch and then we delete
	 * the bot.
	 * @return if the bot is alive or not
	 */
	public boolean isAlive() {
		try{					//somewhat inefficient and maybe
			gc.unit(unitId);	//buggy way to detect a dead unit
							//but is perhaps the only way to
		}					//do so due to BC API problems
		catch(Exception e) {
			Player.deadUnits.add(this);
			return false;
		}
		
		return true;
	}
	
	/**
	 * updates the unit so that we have the most accurate info.
	 * 
	 */
	public void preProcess() {
		unit = gc.unit(unitId);
	}
	
	public abstract void process();
}

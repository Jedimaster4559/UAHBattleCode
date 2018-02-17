import bc.*;

public abstract class UAHUnit {
	
	protected GameController gc;
	protected Unit unit;
	protected int unitId;
	protected UnitType UAHUnitType;
	
	public UAHUnit(Unit unit, GameController gc) {
		this.unit = unit;
		unitId = unit.id();
		UAHUnitType = unit.unitType();
		this.gc = gc;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public int getUnitId() {
		return unitId;
	}
	
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
	
	public void preProcess() {
		unit = gc.unit(unitId);
	}
	
	public abstract void process();
}

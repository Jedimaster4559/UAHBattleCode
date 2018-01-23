import bc.*;

public abstract class UAHUnit {
	
	protected GameController gc;
	protected Unit unit;
	protected int unitId;
	
	
	public UAHUnit(Unit unit, GameController gc) {
		this.unit = unit;
		unitId = unit.id();
		this.gc = gc;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public int getUnitId() {
		return unitId;
	}
	
	public void preProcess() {
		unit = gc.unit(unitId);
	}
	
	public abstract void process();
}
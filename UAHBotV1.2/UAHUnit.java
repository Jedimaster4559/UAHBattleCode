import bc.*;

public abstract class UAHUnit {
	
	private GameController gc;
	private Unit unit;
	private int unitId;
	
	
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
	
	public abstract void process();
}
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
		try{
			gc.unit(unitId);
			
		}
		catch(NoSuchUnit e) {
			System.out.println("Unit No Longer Exists");
			System.out.println("Unit Type: " + UAHUnitType);
			System.out.println("Unit ID: " + unitId);
			Player.deadUnits.add(this);
			System.out.println("Removing Unit from Array of Units");
			return false;
		}
		catch(Exception e) {
			System.out.println("isAlive() error occurred");
			System.out.println("Unit ID: " + unit.id());
			e.printStackTrace();
			return true;
		}
		
		return true;
	}
	
	public void preProcess() {
		unit = gc.unit(unitId);
	}
	
	public abstract void process();
}
import bc.*;

class Knight extends MobileUnit {
	
	public Knight(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
		//check to see if the given unit is on the map and therefore controllable
		if (!unit.location().isOnMap()) {
			return;
		}
		
		if (LogicHandler.escaping && unit.movementHeat() < 10) {	//determine if we need to be attempting
			System.out.println("escape attempt");
			Utilities.moveTowardNearestRocket(unit, gc);		//to get on a rocket
		}
		
		if (!Player.peaceful) {						//Peaceful catch (for debugging purposes)
			if (unit.attackHeat() < 10) {				
				Utilities.senseAndAttackInRange(unit, gc);	//If we are capable of attacking, attack
			}
			if (unit.movementHeat() < 10) {
				Utilities.moveToNearestEnemy(unit, gc);		//If we could not attack and are capable of
			}							//moving, move
		}
		if (unit.movementHeat() < 10) {
			Utilities.moveRandomDirection(unit, gc);		//If we still can move, attempt to move a random direction
		}
		
	}
	
}

import bc.*;


class Mage extends MobileUnit{
	
	public Mage(Unit unit, GameController gc) {
		super(unit, gc);
	}
	
	public void process() {
        	//Do not process if we are not on Earth or Mars
		if (!unit.location().isOnMap()) {
			return;
		}
		
		//Attempt to escape/move to the nearest rocket if that is something currently important
		if (LogicHandler.escaping && unit.movementHeat() < 10) {
			Utilities.moveTowardNearestRocket(unit, gc);
		}
		
		if (!Player.peaceful) {						//Peaceful catch for debugging purposes
			if(unit.attackHeat() < 10){
				Utilities.senseAndAttackInRange(unit, gc);	//attack an enemy if possible
			}
			if(unit.movementHeat() < 10){
				Utilities.moveToNearestEnemy(unit, gc);		//move toward an enemy if possible
			}
		}
		if(unit.movementHeat() < 10){				
			Utilities.moveRandomDirection(unit, gc);		//move a random direction if possible
		}
	}	
}

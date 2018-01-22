import bc.*;
import java.util.ArrayList;
import java.lang.Long;
import java.util.Random;

public class Player {
	
	static Random rand;
	static Direction[] directions;
	static GameController gc;
	static int numFactories;
	static int numWorkers;
	static int numKnights;
	static int numMages;
	static int numRangers;
	static int numHealers;
	static int numRockets;
	static int factoryGoal = 5;
	static int rocketGoal = 2;
	static VecUnit units;
	static boolean peaceful = false;
	static ArrayList<UAHUnit> UAHUnits = new ArrayList<UAHUnit>();
		
	public static void main(String[] args) {
	
		//Add a Game Controller
		gc = new GameController();
		
		//Seed Randomizer for debugging purposes
		rand = new Random();
		//rand.setSeed(4559);
		
		if (gc.planet() == Planet.Earth) {
			//System.out.println("find enemy team from earth");
			Utilities.findEnemyTeam(gc);
		}
		
		//Create and Array of all Directions a bot can travel
		directions = Direction.values();
		
		//initialize logic handler
		LogicHandler.initialize(gc);

		units = gc.myUnits();
		
		for (int i = 0; i < units.size(); i++) {
			UAHUnits.add(new Worker(units[i], gc));
		}
		
		//loop through all units and process their turn
		while (true){
			//System.out.println("CurrentRound: " + gc.round());
			
			//get all units
												
			//Process Logic
			
			//loop through units
			for (UAHUnit unit : UAHUnits) {
				unit.process();
			}
			
			gc.nextTurn();
        }
		
	}
	
	
	
	//This method will determine what type of unit each unit is and run it's processing code
	public static void runUnitLogic(Unit unit){
		//This block determines unit type and then executes a method accordingly
		//RunFactory
		/**
		if(unit.unitType() == UnitType.Factory){
			try{
				Factory.process(unit,gc);
			}
			catch(Exception e){
				System.out.println("A Factory Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Worker
		else if(unit.unitType() == UnitType.Worker){
			try{
				Worker.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Worker Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Knight
		else if(unit.unitType() == UnitType.Knight){
			try{
				Knight.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Knight Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Mage
		else if(unit.unitType() == UnitType.Mage){
			try{
				Mage.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Mage Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Ranger
		else if(unit.unitType() == UnitType.Ranger){
			try{
				Ranger.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Ranger Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Healer
		else if(unit.unitType() == UnitType.Healer){
			try{
				Healer.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Healer Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}
		//Run Rocket
		else if(unit.unitType() == UnitType.Rocket){
			try{
				Rocket.process(unit,gc);
			}
			catch(Exception e) {
				System.out.println("A Rocket Error Occurred:\nUnit Id: " + unit.id());
				e.printStackTrace();
			}
		}*/				
	}
}

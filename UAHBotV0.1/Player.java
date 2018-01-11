import bc.*;

public class Player {
  public static void main(String[] args) {
	
	//Add a Game Controller
	GameController gc = new GameController();
	
	while (true){
		System.Out.Println("CurrentRound: " + gc.round());
		
		VecUnit units = gc.myUnits();
		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
        }
		gc.nextTurn();
	}
	
  }
}

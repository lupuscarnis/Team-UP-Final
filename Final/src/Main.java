import boundary.GUIController;
import controllers.GameController;

public class Main {	

	public static void main(String[] args) throws Exception {		
		GameController gc = new GameController();
		
		gc.setChanceCardToDraw(19);
		gc.setFaceValue(2);
		
		gc.play();
	}
}

import boundary.GUIController;
import controllers.GameController;

public class Main {	

	public static void main(String[] args) throws Exception {		
		
		/*
		GameController gc = new GameController();
		gc.play();	
		*/
		
		GUIController.getInstance().updateHouseCount(4, 2);
	}
}

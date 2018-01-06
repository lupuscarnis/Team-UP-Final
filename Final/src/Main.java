import java.io.IOException;

import controllers.GameBoardController;
import controllers.GameController;
import entities.chancecard.ChanceCard;
import entities.field.Field;
import utilities.ChanceLoader;
import utilities.FieldLoader;

public class Main {

	public static void main(String[] args) throws IOException {

		//GameController gc = new GameController();

		//gc.play();
		
		
		ChanceLoader cl = new ChanceLoader();
		
	
		
		for (ChanceCard string : cl.getCards()) {
			System.out.println(string);;
		}
	}
}

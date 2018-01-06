import java.io.IOException;
import java.util.Iterator;

import boundary.GUIController;
import controllers.ChanceCardController;
import controllers.GameBoardController;
import controllers.GameController;
import entities.chancecard.ChanceCard;
import entities.field.Field;
import utilities.ChanceLoader;
import utilities.FieldLoader;

public class Main {

	public static void main(String[] args) throws Exception {
		GameController gc = new GameController();

		gc.play();
	}
}

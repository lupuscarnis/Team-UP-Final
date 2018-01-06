package controllers;



import entities.Player;
import entities.enums.FieldName;
import entities.field.Field;
import utilities.FieldLoader;

public class PlayerController {
	

	public Player[] createNewPlayers(String[] playerNames) throws Exception {

		// TODO: Move to global scope.
		GameBoardController gbc = new GameBoardController(new FieldLoader().getFields());
		
		
		Player[] tmp = new Player[playerNames.length];

		int index = 0;
		for (String name : playerNames) {			
			
			Field start = gbc.getFieldByName(FieldName.Start);
			
			tmp[index] = new Player(name, 30000, start); // HACK: 30000: Where should the money come from?

			index++;
		}

		return tmp;
	}
}

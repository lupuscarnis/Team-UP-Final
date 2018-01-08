package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.UserOption;
import entities.field.Field;
import utilities.MyRandom;

public class GameLogicCtrl {

	private static GameLogicCtrl instance;
	private GUIController gui = GUIController.getInstance();
	private FieldLogicController flc = FieldLogicController.getInstance();

	private GameLogicCtrl() throws IOException {
	}

	public UserOption showUserOptions(Player currentPlayer) throws Exception {

		int index = 0;
		UserOption[] options = new UserOption[10]; // Hack: we don't know the size yet, so 10 is random!

		// if not in jail - you can throw dice
		if (!currentPlayer.isInJail()) {
			options[index] = UserOption.ThrowDice;
			index++;
		}

		options[index] = UserOption.BuyHouse;
		index++;

		options[index] = UserOption.BuyHotel;
		index++;

		options[index] = UserOption.PawnLot;
		index++;

		options[index] = UserOption.EndTurn;
		index++;

		// empty array of nulls
		int elements = 0;
		for (UserOption userOption : options) {

			if (userOption != null)
				elements++;
		}

		// create new array with correct size
		UserOption[] tmp = new UserOption[elements];

		// insert into array
		index = 0;
		for (UserOption option : options) {

			if (option != null) {
				tmp[index] = option;
				index++;
			}
		}

		return gui.showOptions("Vælg:", tmp);
	}

	/**
	 * Added by Frederik on 06-01-2018 23:49:04
	 * 
	 * Rolls dice and moves player
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	// TODO: Make use of cup when throwing dice!
	public void rollAndMove(Player currentPlayer) throws Exception {

		int currentFieldNo = currentPlayer.getCurrentField().getFieldNumber();

		// Throw Die
		int faceValue = MyRandom.randInt(2, 12);

		// get next field
		Field nextField = flc.getNextField(currentFieldNo, faceValue);

		// Update current pos on player object
		currentPlayer.setCurrentField(nextField);

		// update gui
		gui.movePlayer(currentPlayer);
	}

	public static GameLogicCtrl getInstance() throws IOException {
		if (instance == null)
			instance = new GameLogicCtrl();
		return instance;
	}
}

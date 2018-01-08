package controllers;

import boundary.GUIController;
import entities.Player;
import entities.enums.UserOption;

public class GameLogicCtrl {

	private GUIController gui;

	public GameLogicCtrl(GUIController gui) {
		this.gui = gui;
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
}

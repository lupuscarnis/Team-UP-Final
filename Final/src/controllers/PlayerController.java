package controllers;

import entities.Player;
import entities.enums.FieldName;
import entities.field.Field;

public class PlayerController {

	private static PlayerController instance;
	
	private PlayerController() {}
	

	public Player[] createNewPlayers(String[] playerNames) throws Exception {

		// TODO: Move to global scope.
		// instead have a field number to place they are standing in the field array and
		// let the other controller handle
		// what happends there.
		
		Player[] tmp = new Player[playerNames.length];

		int index = 0;
		for (String name : playerNames) {

			Field start = GameBoardController.getInstance().getFieldByName(FieldName.Start);

			tmp[index] = new Player(name, 30000, start); // HACK: 30000: Where should the money come from?

			index++;
		}

		return tmp;
	}

	public void deposit(int value, Player player) {
		if (value <= 0) {
			value = value * (-1);
			player.deposit(value);
		} else {
			player.deposit(value);
		}

	}

	public void withdraw(int value, Player player) {
		if (value >= 0) {
			value = value * (-1);
			player.withdraw(value);
		} else {
			player.withdraw(value);
		}
	}

	public static PlayerController getInstance() {

		if (instance == null)
			instance = new PlayerController();

		return instance;
	}


	 /* 
	 *
	 *public int getAssetValue(Player player) { int combinedValue;
	 *
	 *
	 * Find value of proporties combinedValue = combinedValue; // Number of
	 *hotels combinedValue = combinedValue + (player.getHotelsOwned()*hotelPrice);
	 * 
	 * Number of houses combinedValue =
	 * combinedValue+(player.getHousesOwned()*housePrice);
	 *
	 * Balance in account combinedValue = combinedValue+(player.getBalance());
	 * 
	 * 
	 * return combinedValue;
	 * 
	 *
	 * 
	 * 
	 * }
	 */

}

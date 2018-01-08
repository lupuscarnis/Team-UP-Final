package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.UserOption;
import entities.field.Field;
import utilities.MyRandom;

public class GameLogicCtrl {
	private Player previousPlayer = null; // Who played last turn
	private Player startPlayer = null; // Who starts first
	private static GameLogicCtrl instance;
	private GUIController gui = GUIController.getInstance();
	private FieldLogicController flc = FieldLogicController.getInstance();

	private GameLogicCtrl() throws IOException {
	}

	public static GameLogicCtrl getInstance() throws IOException {
		if (instance == null)
			instance = new GameLogicCtrl();
		return instance;
	}

	public UserOption showUserOptions(Player currentPlayer) throws Exception {

		int index = 0;
		UserOption[] options = new UserOption[10]; // Hack: we don't know the size yet, so 10 is random!

		// if not in jail - you can throw dice
		if (!currentPlayer.isInJail() && !currentPlayer.isDoneThrowing()) {
			options[index] = UserOption.ThrowDice;
			index++;
		}
/*
		options[index] = UserOption.BuyHouse;
		index++;

		options[index] = UserOption.BuyHotel;
		index++;

		options[index] = UserOption.PawnLot;
		index++;*/

		if(currentPlayer.isDoneThrowing()) {
				options[index] = UserOption.EndTurn;
				index++;
		}
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

	/**
	 * Added by Frederik on 23-11-2017 17:34:24
	 * 
	 * Gets the next player for the next turn.
	 * 
	 * @param players
	 * @return
	 * @throws Exception
	 */
	public Player getNextPlayer(Player[] players) throws Exception {

		if (previousPlayer == null) {
			previousPlayer = players[0];

			return players[0];
		}

		int indexMax = players.length - 1;
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];

			if (player.equals(previousPlayer)) {

				if (i < indexMax) {
					previousPlayer = players[i + 1];
					return players[i + 1];
				} else {
					previousPlayer = players[0];
					return players[0];
				}
			}
		}

		throw new Exception("Player was not found!");
	}

	/**
	 * Added by Kasper on 16-01-2017
	 * 
	 * Calculates and returns who starts first.
	 * 
	 * @param players
	 * @return startPlayer
	 * @throws Exception
	 */

	public Player getStartPlayer(Player[] players) throws Exception {

		int numPlayers = players.length;
		int newHighest = 0;

		for (int i = 0; i < numPlayers; i++) {

			int resultRoll = MyRandom.randInt(1, 6);

			if (resultRoll > newHighest) {

				newHighest = resultRoll;

				startPlayer = players[i];
			}

			System.out.println(players[i].getName() + " Rolled " + resultRoll);
		}

		if (startPlayer != null) {

			System.out.println("-- " + startPlayer.getName() + " goes first! --");

			previousPlayer = startPlayer;
			
			return startPlayer;
		}		
		
		throw new Exception("No players were found!");
	}

	public void handleUserChoice(Player currentPlayer, UserOption userChoice) {
		
		
		
	}
}

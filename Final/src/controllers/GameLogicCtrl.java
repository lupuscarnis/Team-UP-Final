package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Cup;
import entities.Die;
import entities.Player;
import entities.enums.FieldName;
import entities.enums.UserOption;
import entities.field.Field;
import utilities.Messager;
import utilities.MyRandom;



/**
 * Responsible for generating user choices,
 * for finding nextPlayer,
 * for moving player,
 * for handling jail.
 * 
 */

public class GameLogicCtrl extends BaseController {

	Cup cup = new Cup();
	private Player previousPlayer = null; // Who played last turn
	private Player startPlayer = null; // Who starts first
	private BusinessLogicController blc = null;
	private FieldLogicController flc = null;

	// default constructor.
	public GameLogicCtrl() throws IOException {

	}
	public void setFlc(FieldLogicController flc) {
		this.flc = flc;
	}

	public void setBlc(BusinessLogicController blc) {
		this.blc = blc;
	}
	/**
	 * Generates and presents the available UserOptions for the player
	 * 
	 * @param currentPlayer
	 * @return Picked UserOption
	 * @throws Exception
	 */
	public UserOption showUserOptions(Player currentPlayer) throws Exception {

		int index = 0;
		UserOption[] options = new UserOption[10]; // Hack: we don't know the size yet, so 10 is random!

		if (blc.canPawn(currentPlayer)) {
			options[index] = UserOption.PawnLot;
			index++;
		}
		if (blc.hasPawn(currentPlayer))
			options[index] = UserOption.Unpawn;
		index++;

		if (currentPlayer.isDoneThrowing()) {
			options[index] = UserOption.EndTurn;
			index++;
		}
		if (currentPlayer.isInJail() == true) {
			options[index] = UserOption.PayToLeaveJail;
			index++;
		}
		if (currentPlayer.isInJail() == true && currentPlayer.getJailCard()) {
			options[index] = UserOption.GetOutOfJailCard;
			index++;
		}
		if (!currentPlayer.isDoneThrowing()) {
			options[index] = UserOption.ThrowDice;
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

		return Messager.presentOptions(tmp, currentPlayer.getName());
	}

	/**
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
		if (previousPlayer.getRollDoubleStreak() > 0) {

			return previousPlayer;
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
	/**
	 * Added by Frederik on 06-01-2018 23:49:04
	 * 
	 * Rolls dice, evaluate jail status and move player
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void rollAndMove(Player currentPlayer) throws Exception {

		int currentFieldNo = currentPlayer.getCurrentField().getFieldNumber();

		// Throw Die
		int faceValue = cup.rollDice();

		// Stores the current diceValue in player.
		// int faceValue = cup.rollDice();
		currentPlayer.setLastRoll(faceValue);

		// if the player rolled double, increase counter by 1, else set it to 0
		if (cup.rolledDouble()) {
			int streak = currentPlayer.getRollDoubleStreak();
			currentPlayer.setRollDoubleStreak(streak + 1);
			currentPlayer.setIsInJail(false);
		}
		if (!cup.rolledDouble()) {
			currentPlayer.setRollDoubleStreak(0);
			if (currentPlayer.isInJail()) {
				currentPlayer.setTurnsJailed(currentPlayer.getTurnsJailed() + 1);
			}
			if (currentPlayer.getTurnsJailed() == 3) {
				payToLeaveJail(currentPlayer);
			}
		}

		gui.showDice(cup.getD1().getValue(), cup.getD2().getValue());

		if (currentPlayer.getRollDoubleStreak() < 3 && !currentPlayer.isInJail()) {
			// Checks if he passes start and gives him money
			checkPassedStart(currentPlayer, faceValue, true);
			// get next field
			Field nextField = flc.getNextField(currentFieldNo, faceValue);

			// Update current pos on player object
			currentPlayer.setCurrentField(nextField);

			// Update gui
			gui.movePlayer(currentPlayer);
		}
		if (currentPlayer.getRollDoubleStreak() == 3) {
			handleGoToJail(currentPlayer);
			Messager.showRollStreakJail(currentPlayer);
		}

	}

	// checks if the Player Move past start this turn and receives 4000
	public void checkPassedStart(Player currentPlayer, int faceValue, boolean canReceive) throws Exception {
		if ((currentPlayer.getCurrentField().getFieldNumber() + faceValue > 40) && (canReceive == true)) {
			currentPlayer.deposit(BusinessLogicController.MONEY_FOR_PASSING_START);
			Messager.showPassedStart(currentPlayer);
			System.out.println("Du fik 4000 over start! hurray!");
		}
	}

	/**
	 * 
	 * @param fromField
	 * @param toField
	 * @return hasPassedStart
	 */
	public boolean checkHavePassedStart(int fromField, int toField) {
		return toField < fromField;
	}

	/**
	 * Method to put players in jail
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void handleGoToJail(Player currentPlayer) throws Exception {

		//to prevent players from continuing their turn when jailed.
		currentPlayer.setRollDoubleStreak(0);
		
		Field jail = gbc.getFieldByName(FieldName.Fængslet);
		int fromField = currentPlayer.getCurrentField().getFieldNumber();

		// put player in jail
		currentPlayer.setIsInJail(true);
		currentPlayer.setCurrentField(jail);
		// update gui
		gui.updatePlayerPosition(currentPlayer.getName(), fromField, jail.getFieldNumber());
	}

	/**
	 * Releases player from jail for fee
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void payToLeaveJail(Player player) throws Exception {
		player.withdraw(1000);
		gui.updateBalance(player);
		player.setIsInJail(false);
	}
}

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

public class GameLogicCtrl {
	private Player previousPlayer = null; // Who played last turn
	private Player startPlayer = null; // Who starts first
	private static GameLogicCtrl instance;
	private GUIController gui = GUIController.getInstance();
	private FieldLogicController flc = FieldLogicController.getInstance();
	Die d1 = new Die(6, 1);
	Die d2 = new Die(6, 1);
	Cup cup = new Cup(0, 0, d1, d2);

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

		// can pawn
		if (BusinessLogicController.getInstance().canPawn(currentPlayer)) {
			options[index] = UserOption.PawnLot;
			index++;
		}
		if(BusinessLogicController.getInstance().canPawn(currentPlayer))
			options[index] = UserOption.Unpawn;
		index++;

		// can sell houses
		if (false) {
			options[index] = UserOption.BuyHouse;
			index++;
		}
		// can sell hotel
		if (false) {
			options[index] = UserOption.BuyHotel;
			index++;
		}

		if (currentPlayer.isDoneThrowing()) {
			options[index] = UserOption.EndTurn;
			index++;
		} 
		if(currentPlayer.isInJail()==true)
		{options[index] = UserOption.PayToLeaveJail;
		index++;
		
		}
		if(currentPlayer.isInJail()==true && currentPlayer.getJailCard()) {
			options[index] = UserOption.GetOutOfJailCard;
			index++;
		}
		if(!currentPlayer.isDoneThrowing()) {
			options[index] = UserOption.ThrowDice;
			index++;
		}

		// if (currentPlayer.isDoneThrowing()) {

		// }

		// if not in jail - you can throw dice
		// TODO: Check for roll streak
		// else if (!currentPlayer.isInJail()) {
		// options[index] = UserOption.ThrowDice;
		// index++;
		// }

		/*
		 * options[index] = UserOption.BuyHouse; index++;
		 * 
		 * options[index] = UserOption.BuyHotel; index++;
		 * 
		 * options[index] = UserOption.PawnLot; index++;
		 */

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
	 * Added by Frederik on 06-01-2018 23:49:04
	 * 
	 * Rolls dice and moves player
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	// TODO: M
	public void rollAndMove(Player currentPlayer) throws Exception {

		int currentFieldNo = currentPlayer.getCurrentField().getFieldNumber();

		// Throw Die
		int faceValue = cup.rollDice();
		
		//if the player rolled double, increase counter by 1, else set it to 0
		if(cup.rolledDouble()) {
			int streak = currentPlayer.getRollDoubleStreak();
			currentPlayer.setRollDoubleStreak(streak+1);
			currentPlayer.setIsInJail(false);
		}
		if(!cup.rolledDouble()) {
			currentPlayer.setRollDoubleStreak(0);
		}
		
		gui.showDice(cup.getD1().getValue(), cup.getD2().getValue());
		
		if(currentPlayer.getRollDoubleStreak()<3 && !currentPlayer.isInJail()) {
		// Checks if he passes start and gives him money
		checkPassedStart(currentPlayer, faceValue, true);
		// get next field
		Field nextField = flc.getNextField(currentFieldNo, faceValue);

		// Update current pos on player object
		currentPlayer.setCurrentField(nextField);

		// Update gui
		gui.movePlayer(currentPlayer);
		}
		if(currentPlayer.getRollDoubleStreak()==3) {
			handleGoToJail(currentPlayer);
			Messager.showRollStreakJail(currentPlayer);
		}
	}

	// checks if the Player Move past start this turn and receives 4000
	private void checkPassedStart(Player currentPlayer, int faceValue, boolean canReceive) throws Exception {
		if ((currentPlayer.getCurrentField().getFieldNumber() + faceValue > 40) && (canReceive == true)) {
			currentPlayer.deposit(4000);
			Messager.showPassedStart(currentPlayer);
			System.out.println("Du fik 4000 over start! hurray!");
		}
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

	public void handleGoToJail(Player currentPlayer) throws IOException, Exception {

		Field jail = GameBoardController.getInstance().getFieldByName(FieldName.Fængslet);
		int fromField = currentPlayer.getCurrentField().getFieldNumber();

		// put player in jail
		currentPlayer.setIsInJail(true);
		currentPlayer.setCurrentField(jail);

		// update gui
		GUIController.getInstance().updatePlayerPosition(currentPlayer.getName(), fromField, jail.getFieldNumber());
	}
}

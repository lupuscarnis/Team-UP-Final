package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.UserOption;
import utilities.MyRandom;

public class GameController {

	// controllers
	private FieldLogicController flc = FieldLogicController.getInstance();
	private PlayerController pc = PlayerController.getInstance();
	private GameLogicCtrl glc = GameLogicCtrl.getInstance();	
	private GUIController gui = GUIController.getInstance();

	//TODO: Remove
	private BusinessLogicController blc = BusinessLogicController.getInstance();

	// CONSTANTS
	private static final int PLAYER_MIN = 3;
	private static final int PLAYER_MAX = 6;

	// Maybe move these to bank

	private static final int MONEY_START_AMOUNT = 30000; // Amount for each player at game start
	private static final int MONEY_START = 4000; // Amount when passing START
	private static final int MONEY_JAIL = 1000; // Amount to pay to leave jail
	private static final int TAX_CASH_AMOUNT = 4000;
	private static final double TAX_PERCENTAGE_AMOUNT = 0.1;

	// ATTRIBUTES
	private Player[] players = null;
	private Player lastPlayer = null; // Who played last turn
	private Player startPlayer = null; // Who starts first
	private Player currentPlayer = null; // The current players round

	// FOR TESTING PURPOSES!

	// Testing Gameover
	public GameController() throws IOException {
	}

	private boolean gameOver(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}

	// Testing player is in jail
	private boolean isJail(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}

	// Testing player gets out of jail
	private boolean getOutJail(Player[] players) {		
		return false;
	}

	public void setupGame() throws Exception {

		// get player names from UI
		String[] playerNames = gui.getNewPlayerNames();

		// create new players via PlayerCtrl.
		players = pc.createNewPlayers(playerNames);

		// now GUI can be setup with players
		gui.setup(players);
	}

	public void play() throws Exception {

		// indicates number of turns in current game
		int turnCounter = 1;

		// indicates if first turn or not
		boolean isFirstTurn = true;

		// setup
		setupGame();

		System.out.println("-------------------------");
		System.out.println("-- The Game has Begun! --");
		System.out.println("-------------------------");

		// start game loop
		while (!gameOver(players)) {

			// Checking which players starts first if turn == 1
			if (isFirstTurn) {

				// indicate that it's not first turn no more.
				isFirstTurn = false;

				// Get first player from highest "roll"
				currentPlayer = getStartPlayer(players);

				// Vis start spil knap
				gui.showPromt("Start spil");

			} else {

				// find next player
				currentPlayer = getNextPlayer(players);
			}

			// present options for user
			UserOption choice = glc.showUserOptions(currentPlayer);

			System.out.println("Choice: " + choice);

			// Starting main round play through
			System.out.println("-- Round: " + turnCounter + " --");

			if (!isJail(players) || isJail(players) && getOutJail(players)) { // Player !isJail or (isJail and pays a

				// fee to get out)
				if (isJail(players) && getOutJail(players))
					System.out.println("-- Player payed 1000kr to get out of jail --");

				// roll and move player
				glc.rollAndMove(currentPlayer);

				// resolve field
				flc.resolveField(currentPlayer);

				// Check if player still has money or should be removed.
				int playerCount = players.length;

				players = blc.evaluatePlayer(currentPlayer, players);

				// No players left = Game over
				if (players.length == 0) {
					System.out.println("Game over!!!!!!!");
					// TODO: Beautify!!!!
					break;
				}

				// TODO: Needs to be handled properly!
				if (players.length < playerCount)
					lastPlayer = players[0];

				Thread.sleep(400);

			} else { // Player isJail, doesn't pay the fee and must therefore wait until next turn

				System.out.println("-- Player must skip this turn --");
			}

			turnCounter++;
		}

		// if is Player inJail?

		// else Throw Die

		// MovePlayer

		// Evaluate Field

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

		if (lastPlayer == null) {
			lastPlayer = players[0];

			return players[0];
		}

		int indexMax = players.length - 1;
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];

			if (player.equals(lastPlayer)) {

				if (i < indexMax) {
					lastPlayer = players[i + 1];
					return players[i + 1];
				} else {
					lastPlayer = players[0];
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

			return startPlayer;
		}

		throw new Exception("No players were found!");
	}

}

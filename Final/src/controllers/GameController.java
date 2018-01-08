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

	// TODO: Remove
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
				currentPlayer = glc.getStartPlayer(players);

				// Vis start spil knap
				gui.showPromt("Start spil");

			} else {

				// find next player
				currentPlayer = glc.getNextPlayer(players);
			}

			boolean diceThrown = false;
			
			while(!diceThrown) {
			// present options for user
			UserOption choice = glc.showUserOptions(currentPlayer);		
			// handles the choice of the user
			diceThrown = handleChoice(choice);
			}
			
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
	}

	private boolean handleChoice(UserOption choice) {
		
		switch(choice) {
		case BuyHotel:
			return false;
		case BuyHouse:
			return false;
		case EndTurn:
			System.out.println("Make a real choice");
			return false;
		case PawnLot:
			return false;
		case ThrowDice:
			return true;
		default:
			return false;
		
		}
		
	}
}

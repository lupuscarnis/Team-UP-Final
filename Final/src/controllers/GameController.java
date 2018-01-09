package controllers;

import java.io.IOException;

import boundary.GUIController;
import entities.Player;
import entities.field.Field;
import utilities.FieldLoader;
import utilities.MyRandom;

public class GameController {

	private FieldLogicController flc = null;
	private GUIController gui = null;
	private PlayerController pc = null;
	private GameBoardController gbc = null;
	// TODO: Move/share.

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
		gui = new GUIController();
		gbc = new GameBoardController(new FieldLoader().getFields());
		flc = new FieldLogicController(gbc, gui);
		pc = new PlayerController();
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
		// TODO Auto-generated method stub
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

		// setup
		setupGame();

		// Start round count
		int turn = 1;

		System.out.println("-------------------------");
		System.out.println("-- The Game has Begun! --");
		System.out.println("-------------------------");

		// start game loop

		while (!gameOver(players)) {

			// Checking which players starts first if turn == 1

			if (turn == 1) {

				// Get first player from highest "roll"
				currentPlayer = getStartPlayer(players);

			} else {

				// find next player
				currentPlayer = getNextPlayer(players);

			}

			// Starting main round play through
			System.out.println("-- Round: " + turn + " --");

			if (!isJail(players) || isJail(players) && getOutJail(players)) { // Player !isJail or (isJail and pays a
																				// fee to get out)

				if (isJail(players) && getOutJail(players))
					System.out.println("-- Player payed 1000kr to get out of jail --");
				//resetter antal rerolls denne tur
				flc.cup1.setRerolls(0);
				// roll and move player
				flc.rollAndMove(currentPlayer);
				//if 3 (or more) rerolls occure, player doesnt resolve field and goes to jail.
				if(flc.cup1.getRerolls()>2){currentPlayer.isInJail(true); break;}
				// resolve field
				flc.resolveField(currentPlayer.getCurrentField());
			if(flc.d1.getValue()==flc.d2.getValue())
			{
				// roll and move player
				flc.rollAndMove(currentPlayer);
				if(flc.cup1.getRerolls()>2){currentPlayer.isInJail(true); break;}
				// resolve field
				flc.resolveField(currentPlayer.getCurrentField());
			}
			if(flc.d1.getValue()==flc.d2.getValue())
			{
				// roll and move player
				flc.rollAndMove(currentPlayer);
				if(flc.cup1.getRerolls()>2){currentPlayer.isInJail(true); break;}
				// resolve field
				flc.resolveField(currentPlayer.getCurrentField());
			}
			if(flc.d1.getValue()==flc.d2.getValue())
			{currentPlayer.isInJail(true);}
			
				// Check if player still has money or should be removed.
				int playerCount = players.length;
				BusinessLogicController blc = new BusinessLogicController(gui, gbc);
				players = blc.evaluatePlayer(currentPlayer, players);
				
				// No players left = Game over
				if(players.length==1)
				{
					System.out.println("Game over!!!!!!!");
					//TODO: Beautify!!!!
					break;
				}
				
				//TODO: Needs to be handled properly!
				if(players.length<playerCount)
					lastPlayer=players[0];				

				Thread.sleep(400);

			} else { // Player isJail, doesn't pay the fee and must therefore wait until next turn

				System.out.println("-- Player must skip this turn --");
			}

			turn++;

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
		} else {

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

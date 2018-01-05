
package entities;

public class Game {

	// CONSTANTS

	private static final int PLAYER_MIN = 3;
	private static final int PLAYER_MAX = 6;
	private static final int DIE_MIN = 1;
	private static final int DIE_MAX = 6;

	// Maybe move these to bank

	private static final int MONEY_START = 4000; // Amount when passing START
	private static final int MONEY_JAIL = 1000; // Amount to pay to leave jail
	private static final int TAX_CASH_AMOUNT = 4000;
	private static final double TAX_PERCENTAGE_AMOUNT = 0.1;

	// ATTRIBUTES
	private Player[] players = null;

	// FOR TESTING PURPOSES!

	private boolean gameOver(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isJail(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * Setup the Game
	 * 
	 * @throws Exception
	 */

	public void setupGame(Player[] players) throws Exception {

		System.out.println("Game: Setup!");

	}

	/**
	 * 
	 * From CDIO3 @ Frederik
	 * 
	 * @throws Exception
	 */

	public void setupGame() throws Exception {
		setupGame(null);
	}

	/**
	 * 
	 * Main Play() loop
	 * 
	 * @throws Exception
	 */

	public void play() throws Exception {


		// Set the game up
		
		this.setupGame();

		// Game loop

		System.out.println("Running main game loop");

		while (!gameOver(players)) {

			// if is Player inJail?

			if (isJail(players)) {

				System.out.println("Player must pay 1000kr to get out of jail");		

			} else {

				// else Throw Die
				
				System.out.println("Player Throws Die");

				// MovePlayer
				
				System.out.println("Player moves to field");

				// Evaluate Field
				
				System.out.println("Player moves to field");

			}


		}

	}


}


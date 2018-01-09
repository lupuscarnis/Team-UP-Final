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
		return true;
	}	

	private boolean getOutJail(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}	
	
	private boolean sellProperty(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}	
	
	private boolean buyProperty(Player[] players) {
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

		int turn = 1;

		// Game loop

		System.out.println("Running main game loop");

		while (!gameOver(players)) { // Checking if the player is gameover


			System.out.println("Round:" + "turn");
			// if is Player inJail?

			if (!isJail(players) || isJail(players) && getOutJail(players)) { // Player !isJail or isJail and pays a fee to get out

				if (isJail(players) && getOutJail(players)) System.out.println("Player payed 1000kr to get out of jail");

				// Throw Die

				System.out.println("Player Throws Die");

				// MovePlayer

				System.out.println("Player moves to field");

				// Evaluate Field

				System.out.println("Player moves to field");

			} else { // Player isJail and must therefore wait until next turn

				System.out.println("Player must skip this turn");	
				
			}

			turn++;

		}

	}

}

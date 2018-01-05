
import controllers.GameController;
import entities.Player;

public class Main {


	// Should be moved to Run.java
	
	public static void main(String[] args) {

		GameController gc = new GameController();

		gc.play();
	}

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
			
			
			// if is Player inJail?
			
			
            // else Throw Die
			
			// MovePlayer
			
            // Evaluate Field

		}


	}
}

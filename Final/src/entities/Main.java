
package entities;

import controllers.GameController;

public class Main {
	
	
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
		 * Main Play() loop
		 * 
		 * @throws Exception
		 */
		
		public void play() throws Exception {
			
			
			
		}
		
		
	}
}

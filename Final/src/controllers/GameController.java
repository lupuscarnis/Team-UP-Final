package controllers;

import java.io.IOException;

import boundary.GUIController;
import entities.Player;
import entities.field.Field;
import utilities.FieldLoader;
import utilities.MyRandom;

public class GameController {

			FieldLoader fl = new FieldLoader();
			GUIController gui = null;
			private PlayerController pc = new PlayerController();
			// TODO: Move/share.
			GameBoardController gbc = null;
					
			
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
			private static final int FIELD_COUNT = 40; // Move to GBC??

			// ATTRIBUTES
			private Player[] players = null;
			private Player lastPlayer = null; // Who played last turn

			
			public GameController() throws IOException {
				gui = new GUIController();
				gbc = new GameBoardController(fl.getFields());
			}	
			
			
			public void setupGame() throws Exception {

				// get player names from UI
				String[] playerNames = gui.getNewPlayerNames();
				
				// create new players via PlayerCtrl.
				players = pc.createNewPlayers(playerNames);
				
				// Lacks a step: Who should start?
				
				// now GUI can be setup with players
				gui.setup(players);				
			}

			/**
			 * 
			 * From CDIO3 @ Frederik
			 * 
			 * @throws Exception
			 */

			

			public void play() throws Exception{

				// setup
				setupGame();
				
				
				// start game loop
				while(true)
				{
					// find next player
					Player currentPlayer = getNextPlayer(players);					
					
					// "roll"
					int faceValue = MyRandom.randInt(2, 12);
					
					// get next field
					int currentFieldNo = currentPlayer.getCurrentField().getFieldNumber();
					Field nextField = this.getNextField(currentFieldNo, faceValue);
					
					Thread.sleep(500);
					// update current pos on player object (should be moved to a controller...)
					currentPlayer.setCurrentField(nextField);
					
					// move 
					gui.movePlayer(currentPlayer, nextField.getFieldNumber());					
				}
				
				
				// if is Player inJail?
				
				
	            // else Throw Die
				
				// MovePlayer
				
	            // Evaluate Field

			}

	
			/**
			 * Added by Frederik on 23-11-2017 17:50:40
			 * 
			 * Calculates and returns next field for player.
			 * 
			 * @param faceValue
			 * @param currentFieldNumber
			 * @return
			 */
			public Field getNextField(int currentFieldNumber, int faceValue) {

				int nextFieldNo = faceValue + currentFieldNumber;

				// Check for valid next field
				if (nextFieldNo > FIELD_COUNT)
					nextFieldNo += -FIELD_COUNT;

				return gbc.getFieldByNumber(nextFieldNo);
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
	
	
}

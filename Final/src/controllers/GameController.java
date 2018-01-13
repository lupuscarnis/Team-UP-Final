package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.FieldName;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.OwnableField;
import utilities.Messager;

public class GameController {

	// controllers
	private BusinessLogicController blc = BusinessLogicController.getInstance();
	private FieldLogicController flc = FieldLogicController.getInstance();
	private GameLogicCtrl glc = GameLogicCtrl.getInstance();
	private GUIController gui = GUIController.getInstance();

	// ATTRIBUTES
	private Player[] players = null;
	private Player currentPlayer = null; // The current players round

	public GameController() throws IOException {
	}

	/**
	 * Indicates whether game is over or not.
	 * 
	 * @param players
	 * @return
	 */
	private boolean gameOver(Player[] players) {
		int playerCount = 0;
		for (Player player : players) {
			if (player.getBalance() > 0) {
				playerCount++;
			}
		}
		int index = 0;
		Player[] playersRemaining = new Player[playerCount];
		for (Player player : players) {
			if (player.getBalance() > 0) {
				playersRemaining[index] = player;
				index++;
			}
		}

		if (playersRemaining.length == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Method sets up game at game start.
	 * 
	 * @throws Exception
	 */
	public void setupGame() throws Exception {

		// get player names from UI
		String[] playerNames = gui.getNewPlayerNames();

		// create new players via PlayerCtrl.
		players = createNewPlayers(playerNames);

		// now GUI can be setup with players
		gui.setup(players);
	}

	/**
	 * Main game loop, that continues until game over.
	 * 
	 * @throws Exception
	 */
	public void play() throws Exception {

		// indicates if first turn or not
		boolean isFirstTurn = true;

		// setup
		setupGame();

		// start game loop
		while (!gameOver(players)) {

			// Checking which players starts first if turn == 1
			if (isFirstTurn) {

				// indicate that it's not first turn no more.
				isFirstTurn = false;

				// Get first player from highest "roll"
				currentPlayer = glc.getStartPlayer(players);

				// stopper currentplayer for at blive til next player.
			} else { // find next player
				do {
					blc.destroyPlayer(currentPlayer);
					currentPlayer = glc.getNextPlayer(players);
				} while (currentPlayer.getBalance() == 0);

			}
			//gui.showPromt("Det er " + currentPlayer.getName() + "s tur!");

			// present options for user
			// End when EndTurn is selected
			UserOption userChoice = null;
			do {
				userChoice = glc.showUserOptions(currentPlayer);

				switch (userChoice) {

				case BuyHotel:
					break;
				case BuyHouse:
					break;
				case PawnLot:

					// show pawnable lots
					OwnableField[] fields = BusinessLogicController.getInstance().getPawnableFields(currentPlayer);

					String[] tmp = new String[fields.length + 1];

					tmp[0] = "- Annuller!";

					int index = 1;
					for (OwnableField string : fields) {
						tmp[index] = string.getTitle();
						index++;
					}

					String result = Messager.getSelectionResult(tmp, currentPlayer.getName());

					// pawn selected lot
					if (!result.equals("- Annuller!"))
						BusinessLogicController.getInstance().pawnLot(result, currentPlayer);

					break;
				case ThrowDice:
					// TODO: Roll streak?
					currentPlayer.setDoneThrowing(true);

					// roll and move player
					glc.rollAndMove(currentPlayer);

					// handle possible field actions
					flc.handleFieldAction(currentPlayer, players);
					break;
				case GetOutOfJailCard:
					currentPlayer.setIsInJail(false);
					currentPlayer.setJailCard(false);
					break;
				case PayToLeaveJail:
					glc.payToLeaveJail(currentPlayer);
					break;
				case EndTurn:
					currentPlayer.setDoneThrowing(false);
					break;
				case Unpawn:

					OwnableField[] pawnedList = BusinessLogicController.getInstance().getPawnedFields(currentPlayer);

					String[] pawnedNameList = new String[pawnedList.length + 1];

					pawnedNameList[0] = "Annuller!";
					int index1 = 1;

					for (OwnableField field : pawnedList) {

						pawnedNameList[index1] = field.getTitle();
						index1++;

					}

					String answer = Messager.getSelectionResult(pawnedNameList, currentPlayer.getName());

					// unpawn selected lot
					if (!answer.equals("Annuller!"))
						BusinessLogicController.getInstance().unpawn(answer, currentPlayer);

					break;

				default:
					throw new Exception("Case not found!");
				}

			} while (userChoice != UserOption.EndTurn);
			if (gameOver(players)) {
				Messager.showMessage("spillet er ovre vinderen er den med penge tilbage :)");
				break;
			}
		}

	}

	/**
	 * Gets player array (the players currently playing the game). 
	 * 
	 * @return
	 */
	public Player[] getPlayers() {
		return players;
	}

	public Player[] createNewPlayers(String[] playerNames) throws Exception {

		// TODO: Move to global scope.
		// instead have a field number to place they are standing in the field array and
		// let the other controller handle
		// what happends there.
		GameBoardController gbc = GameBoardController.getInstance();

		Player[] tmp = new Player[playerNames.length];

		int index = 0;
		for (String name : playerNames) {

			Field start = gbc.getFieldByName(FieldName.Start);

			tmp[index] = new Player(name, 30000, start); // HACK: 30000: Where should the money come from?

			index++;
		}

		return tmp;
	}
}

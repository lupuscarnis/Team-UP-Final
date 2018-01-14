package boundary;

import java.awt.Color;
import java.io.IOException;
import entities.Player;
import entities.enums.UserOption;
import entities.field.*;
import gui_fields.*;
import gui_fields.GUI_Car.Pattern;
import gui_fields.GUI_Car.Type;
import gui_main.GUI;
import utilities.EnumParser;

/**
 * Class is the intermediary between GUI (Boundry) and the rest of the system
 * (Entities). Class is implemented as a Singleton, so .getInstance() is needed instead of new.
 */
public class GUIController {

	private GUI gui = null;
	private GUI_Player[] guiPlayers = null;
	private GUI_Car[] carList = null;
	private static GUIController instance;

	// default constructor
	private GUIController() throws IOException {
		gui = new BoardGenerator().makeBoard();
	}

	/**
	 * Returns instance of class (singleton). 
	 * 
	 * @return
	 * @throws IOException
	 */
	public static GUIController getInstance() throws IOException {
		if (instance == null)
			instance = new GUIController();

		return instance;
	}

	/**
	 * Sets up game with player array from logic.
	 * 
	 * @param players
	 */
	public void setup(Player[] players) {
		setupPlayers(players);
	}

	/**
	 * Gets array of player names from the gui at game start
	 * 
	 * @return
	 */
	public String[] getNewPlayerNames() {

		// For testing - DONT REMOVE JUST COMMENT OUT :)
		if (1 == 1)
			return new String[] { "Huga", "Hanne", "Balder" };

		// get number of players
		String noOfPlayers = gui.getUserSelection("Vælg antal spillere", "3", "4", "5", "6");
		String[] players = new String[Integer.parseInt(noOfPlayers)];

		// get names of players
		String name;
		for (int i = 0; i < players.length; i++) {
			boolean invalidName = false;
			do {
				invalidName = false;
				name = this.gui.getUserString("Navn på spiller " + (i + 1) + "?");
				// checks for duplicate names
				for (int a = i; a > 0; a--) {
					if (name.equals(players[a - 1])) {
						invalidName = true;
					}

					if (name.equals("NoBid")) {
						invalidName = true;
					}
				}
				// checks if name is empty
				if (name.length() < 1) {
					invalidName = true;
				}
			} while (invalidName);
			players[i] = name;
		}
		return players;
	}

	private void initializeCarList() {
		this.carList = new GUI_Car[6];
		this.carList[0] = new GUI_Car(Color.RED, Color.yellow, Type.RACECAR, Pattern.DOTTED);
		this.carList[1] = new GUI_Car(Color.YELLOW, Color.yellow, Type.TRACTOR, Pattern.DOTTED);
		this.carList[2] = new GUI_Car(Color.black, Color.yellow, Type.CAR, Pattern.DOTTED);
		this.carList[3] = new GUI_Car(Color.green, Color.yellow, Type.UFO, Pattern.DOTTED);
		this.carList[4] = new GUI_Car(Color.blue, Color.yellow, Type.RACECAR, Pattern.ZEBRA);
		this.carList[5] = new GUI_Car(Color.cyan, Color.yellow, Type.TRACTOR, Pattern.ZEBRA);
	}

	/**
	 * setup game with player array
	 * 
	 * @param players
	 */
	private void setupPlayers(Player[] players) {
		// init. array of GUI_Players
		this.guiPlayers = new GUI_Player[players.length];

		// init. array of GUI_Cars
		initializeCarList();

		// add players to GUI_Players array
		int index = 0;
		for (Player p : players) {
			// TODO: should use the same constant value for game start amount as argument
			// instead
			// of hardcoding
			this.guiPlayers[index] = new GUI_Player(p.getName(), 30000, this.carList[index]);

			index++;
		}

		// add player and car to board
		for (GUI_Player gPlayer : this.guiPlayers) {

			// add the player to the board/list
			addPlayerToBoard(gPlayer);

			// add car to start field
			addCarToField(1, gPlayer);
		}
	}

	/**
	 * Helper method: Adds a player to the playerlist on the (gui)board
	 * 
	 * @param player
	 */
	private void addPlayerToBoard(GUI_Player player) {
		this.gui.addPlayer(player);
	}

	/**
	 * Helper method: moves a player on the (gui)board
	 * 
	 * @param fromField
	 * @param toField
	 * @param player
	 */
	private void moveCar(int fromField, int toField, GUI_Player player) {
		removeCarFromField(fromField, player);
		addCarToField(toField, player);
	}

	/**
	 * Helper method: Adds (player piece) car to a field on the (gui)board
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void addCarToField(int fieldNo, GUI_Player player) {
		this.gui.getFields()[fieldNo - 1].setCar(player, true);
	}

	/**
	 * Helper method: Removes a (player piece) car from a field on the (gui)board
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void removeCarFromField(int fieldNo, GUI_Player player) {
		this.gui.getFields()[fieldNo - 1].setCar(player, false);
	}

	/**
	 * Helper method: Moves a (player piece) car from one field to another field on
	 * the (gui)board
	 * 
	 * @param playerToMove
	 * @throws Exception
	 */
	public void movePlayer(Player playerToMove) throws Exception {

		int fromField = playerToMove.getPreviousField().getFieldNumber();
		int toField = playerToMove.getCurrentField().getFieldNumber();
		GUI_Player gPlayer = findPlayer(playerToMove.getName());

		removeCarFromField(fromField, gPlayer);
		moveCar(fromField, toField, gPlayer);
	}

	/**
	 * Helper method: Looks up a "GUI"-player from a "Logic"-player name.
	 * 
	 * @param playerNameToFind
	 * @return
	 * @throws Exception
	 */
	private GUI_Player findPlayer(String playerNameToFind) throws Exception {

		for (GUI_Player gPlayer : this.guiPlayers) {

			if (gPlayer.getName().equals(playerNameToFind))
				return gPlayer;
		}

		throw new Exception("No player found!");
	}

	/**
	 * Helper method: Changes sub-text of field to show current owner.
	 * 
	 * @param name
	 * @param fieldId
	 * @throws Exception
	 */
	public void updateLotOwner(String name, int fieldId) throws Exception {

		// Find gPlayer (new owner)
		GUI_Player gP = findPlayer(name);

		// set subtext with owner name
		gui.getFields()[fieldId - 1].setSubText("Ejer: " + name);
	}

	/**
	 * Helper method: Updates the player balance.
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void updateBalance(Player currentPlayer) throws Exception {
		updateBalance(new Player[] { currentPlayer });
	}

	/**
	 * Helper method: Updates the player balance for each player in the array.
	 * 
	 * @param players
	 * @throws Exception
	 */
	public void updateBalance(Player[] players) throws Exception {

		for (Player p : players) {

			// Find gPlayer
			GUI_Player gP = findPlayer(p.getName());

			// set balance
			gP.setBalance(p.getBalance());
		}
	}

	/**
	 * Helper method: Removes player from the board (he has lost).
	 * 
	 * @param playerToRemove
	 * @throws Exception
	 */
	public void removePlayer(Player playerToRemove) throws Exception {

		// Find gPlayer
		GUI_Player gP = findPlayer(playerToRemove.getName());

		String newName = String.format("%s (tabt)", gP.getName());

		GUI_Player[] tmp = new GUI_Player[guiPlayers.length - 1];

		int inner = 0;
		for (GUI_Player item : guiPlayers) {

			if (item != gP) {
				tmp[inner] = item;
				inner++;
			}
		}

		// set name
		gP.setName(newName);

		// set balance
		gP.setBalance(0);

		// remove car
		this.removeCarFromField(playerToRemove.getCurrentField().getFieldNumber(), gP);

		guiPlayers = tmp;
	}

	/**
	 * Helper method: Removes owner name from lot field
	 * 
	 * @param field
	 */
	public void removeLotOwner(OwnableField field) {
		gui.getFields()[field.getFieldNumber() - 1].setSubText("Pris: " + field.getPrice());
	}

	/**
	 * Helper method: Shows message in chance card slot.
	 * 
	 * @param string
	 */
	public void showMessage(String string) {
		gui.displayChanceCard(string);
	}

	/**
	 * Helper method: Shows message to user and awaits "OK" button press.
	 * 
	 * @param string
	 */
	public void showPromt(String string) {
		gui.showMessage(string);
	}

	/**
	 * Helper method: Show buttons to current player for selection.
	 * 
	 * @param label
	 * @param userOptions
	 * @return
	 * @throws Exception
	 */
	public UserOption showOptions(String label, UserOption[] userOptions) throws Exception {

		String[] options = new String[userOptions.length];

		int index = 0;
		for (UserOption option : userOptions) {

			options[index] = EnumParser.fromUserOptionToStr(option);

			index++;
		}
		String result = gui.getUserButtonPressed(label, options);

		return EnumParser.fromStrToUserOption(result);
	}

	/**
	 * Helper method: Shows the dice on the gui with a roll and small delay
	 * 
	 * @param dice1
	 * @param dice2
	 * @throws InterruptedException
	 */
	public void showDice(int dice1, int dice2) throws InterruptedException {
		int minValue = 1, maxValue = 6;
		int rollDecreaser1 = 6;
		int rollDecreaser2 = 6;
		for (int i = 0; i <= 6; i++) {
			rollDecreaser2 -= 1;
			rollDecreaser1 -= 1;
			gui.setDice(((int) (Math.random() * maxValue) + minValue), rollDecreaser1,
					((int) (Math.random() * maxValue) + minValue), rollDecreaser2);
			Thread.sleep(30);
		}
		gui.setDice(dice1, 3, dice2, 3);
	}

	/**
	 * Helper method: Updates the players position on the board
	 * 
	 * @param playerName
	 * @param fromField
	 * @param toField
	 * @throws Exception
	 */
	public void updatePlayerPosition(String playerName, int fromField, int toField) throws Exception {
		moveCar(fromField, toField, findPlayer(playerName));
	}

	/**
	 * Helper method: Creates drop down list for selection and returns choice.
	 * 
	 * @param label
	 * @param strings
	 * @return
	 */
	public String getSelection(String label, String[] strings) {
		return gui.getUserSelection(label, strings);
	}

	/**
	 * Helper method: Sets houses on a lot field
	 * 
	 * @param count
	 * @param fieldNumber
	 */
	public void setHouse(int count, int fieldNumber) {
		GUI_Street housePlacer = (GUI_Street) gui.getFields()[fieldNumber - 1];
		housePlacer.setHouses(count);
	}

	/**
	 * Helper method: Sets hotels on a lot field
	 * 
	 * @param hasHotel
	 * @param fieldNumber
	 */
	public void setHotel(boolean hasHotel, int fieldNumber) {
		GUI_Street housePlacer = (GUI_Street) gui.getFields()[fieldNumber - 1];
		housePlacer.setHotel(hasHotel);
	}

	/**
	 * Helper method: Set sub text on field to indicate that field is pawned.
	 * 
	 * @param fieldNumber
	 */
	public void setPawnStatus(int fieldNumber) {
		gui.getFields()[fieldNumber - 1].setSubText("PANTSAT");
	}

	/**
	 * Helper method: Clears pawn label when field is unpawned.
	 * 
	 * @param fieldNumber
	 * @param ownerName
	 * @throws IOException
	 * @throws Exception
	 */
	public void clearPawnStatus(int fieldNumber, String ownerName) throws IOException, Exception {
		GUIController.getInstance().updateLotOwner(ownerName, fieldNumber);
	}

	/**
	 * Helper method: Gets user bid when bidding on lot field.
	 * 
	 * @return
	 */
	public int getUsersInt() {
		String input = "Giv bud";
		return gui.getUserInteger(input);
	}
}

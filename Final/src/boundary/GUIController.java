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
/**
 * Added by Frederik on 06-01-2018 03:02:05
 *
 */
public class GUIController {

	private GUI gui = null;
	private GUI_Player[] guiPlayers = null;
	private GUI_Car[] carList = null;
	private static GUIController instance;

	private GUIController() throws IOException {
		gui = new BoardGenerator().makeBoard();
	}

	public static GUIController getInstance() throws IOException {
		if (instance == null)
			instance = new GUIController();

		return instance;
	}

	/**
	 * Added by Frederik on 06-01-2018 03:02:01
	 * 
	 * @throws InterruptedException
	 */
	public void setup(Player[] players) {

		setupPlayers(players);
	}

	/**
	 * Added by Frederik on 06-01-2018 03:18:12
	 * 
	 * @return
	 */
	public String[] getNewPlayerNames() {

		// For testing - DONT REMOVE JUST COMMENT OUT :)
		//if (1 == 1)
			//return new String[] { "Huga", "Hanne", "Balder" };

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
			// should use the same constant value for game start amount as argument instead
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
	 * Added by Frederik on 06-01-2018 03:01:41
	 * 
	 * @param fromField
	 * @param toField
	 * @param player
	 */

	private void addPlayerToBoard(GUI_Player player) {
		this.gui.addPlayer(player);
	}

	private void moveCar(int fromField, int toField, GUI_Player player) {
		removeCarFromField(fromField, player);
		addCarToField(toField, player);
	}

	/**
	 * Added by Frederik on 06-01-2018 03:01:46
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void addCarToField(int fieldNo, GUI_Player player) {
		this.gui.getFields()[fieldNo - 1].setCar(player, true);
	}

	/**
	 * Added by Frederik on 06-01-2018 03:01:49
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void removeCarFromField(int fieldNo, GUI_Player player) {
		this.gui.getFields()[fieldNo - 1].setCar(player, false);
	}

	public void movePlayer(Player playerToMove) throws Exception {

		int fromField = playerToMove.getPreviousField().getFieldNumber();
		int toField = playerToMove.getCurrentField().getFieldNumber();
		GUI_Player gPlayer = findPlayer(playerToMove.getName());

		removeCarFromField(fromField, gPlayer);
		moveCar(fromField, toField, gPlayer);
	}

	private GUI_Player findPlayer(String playerNameToFind) throws Exception {

		for (GUI_Player gPlayer : this.guiPlayers) {

			if (gPlayer.getName().equals(playerNameToFind))
				return gPlayer;
		}

		throw new Exception("No player found!");
	}

	/**
	 * Added by Frederik on 06-01-2018 22:51:40
	 * 
	 * Changes sub-text of field to show current owner.
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
	 * Added by Frederik on 09-01-2018 00:49:47
	 * 
	 * Updates the balance of the player
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void updateBalance(Player currentPlayer) throws Exception {
		updateBalance(new Player[] { currentPlayer });
	}
	/**
	 * Added by Frederik on 06-01-2018 23:30:48
	 * 
	 * Updates the balance of the names in the array
	 * 
	 * @param names
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
	 * Added by Frederik on 07-01-2018 00:07:55
	 * 
	 * Remove player from board
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
	 * Added by Frederik on 07-01-2018 01:25:01
	 * 
	 * Removes owner name from lot
	 * 
	 * @param field
	 */
	public void removeLotOwner(OwnableField field) {
		gui.getFields()[field.getFieldNumber() - 1].setSubText("Pris: " + field.getPrice());
	}

	public void showMessage(String string) {
		gui.displayChanceCard(string);
	}

	public void showPromt(String string) {
		gui.showMessage(string);
	}

	/**
	 * Added by Frederik on 08-01-2018 14:18:05
	 * 
	 * Can show buttons and return selection
	 * 
	 * @param label
	 * @param options
	 * @throws Exception
	 */
	public UserOption showOptions(String label, UserOption[] userOptions) throws Exception {

		String[] options = new String[userOptions.length];

		int index = 0;
		for (UserOption option : userOptions) {

			options[index] = parseUserOption(option);

			index++;
		}

		String result = gui.getUserButtonPressed(label, options);

		return parseFromStringToUserOption(result);
	}

	/**
	 * Added by Frederik on 08-01-2018 17:18:14
	 * 
	 * Parses user selection (string) to UserOption
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private UserOption parseFromStringToUserOption(String result) throws Exception {

		if (parseUserOption(UserOption.BuyHotel) == result)
			return UserOption.BuyHotel;
		if (parseUserOption(UserOption.BuyHouse) == result)
			return UserOption.BuyHouse;
		if (parseUserOption(UserOption.EndTurn) == result)
			return UserOption.EndTurn;
		if (parseUserOption(UserOption.PawnLot) == result)
			return UserOption.PawnLot;
		if (parseUserOption(UserOption.ThrowDice) == result)
			return UserOption.ThrowDice;
		if (parseUserOption(UserOption.BuyField) == result)
			return UserOption.BuyField;
		if (parseUserOption(UserOption.NoThanks) == result)
			return UserOption.NoThanks;
		if (parseUserOption(UserOption.PayRent) == result)
			return UserOption.PayRent;
		if (parseUserOption(UserOption.IncomeTaxPay4000) == result)
			return UserOption.IncomeTaxPay4000;
		if (parseUserOption(UserOption.IncomeTaxPayTenPercent) == result)
			return UserOption.IncomeTaxPayTenPercent;
		if(parseUserOption(UserOption.bidOnField) == result)
			return UserOption.bidOnField;
		throw new Exception("Translation not found!");
	}

	/**
	 * Added by Frederik on 08-01-2018 17:06:36
	 * 
	 * Converts from UserOption to text that can be displayed to the user.
	 * 
	 * @param option
	 * @return
	 * @throws Exception
	 */
	public void showDice(int value1, int value2) throws InterruptedException
	{
		int minValue=1,maxValue=6;
		int rollDecreaser1 = 6;
		int rollDecreaser2 = 6;
		for(int i=0; i<=6;i++)
		{
			rollDecreaser2 -= 1;
			rollDecreaser1 -= 1;
		gui.setDice(((int)(Math.random()*maxValue)+minValue), rollDecreaser1, ((int)(Math.random()*maxValue)+minValue), rollDecreaser2);
		Thread.sleep(30);
		}
		gui.setDice(value1, 3, value2, 3);					
	}
	
	
	private String parseUserOption(UserOption option) throws Exception {
		switch (option) {
		case PayRent:
			return "Betal leje";
		case NoThanks:
			return "Nej tak";
		case BuyField:
			return "Køb grund";
		case BuyHotel:
			return "Køb hotel";
		case BuyHouse:
			return "Køb hus";
		case EndTurn:
			return "Afslut tur";
		case PawnLot:
			return "Pantsæt grund";
		case ThrowDice:
			return "Kast terning";
		case IncomeTaxPay4000:
			return "Betal 4000";
		case IncomeTaxPayTenPercent:
			return "Betal 10%";
		default:
			throw new Exception("Case not found!");
		}
	}

	public void updatePlayerPosition(String playerName, int fromField, int toField) throws Exception {		
		moveCar(fromField, toField, findPlayer(playerName));
	}
	
	

	public String getSelection(String label, String[] strings) {
		return gui.getUserSelection(label, strings);		
	}
	/**
	 * @author Nicolai
	 * @param count
	 * @param fieldNumber
	 * sets the number of houses
	 */
	public void setHouse(int count,int fieldNumber )
	{	
		GUI_Street housePlacer = (GUI_Street)gui.getFields()[fieldNumber-1];		
		housePlacer.setHouses(count);
	}
	/**
	 * @author Nicolai
	 * @param hasHotel
	 * @param fieldNumber'
	 * Sets A hotel or takes it off.
	 */
	public void setHotel(boolean hasHotel, int fieldNumber) {
			
		GUI_Street housePlacer = new GUI_Street();
		housePlacer = (GUI_Street)gui.getFields()[fieldNumber-1];  
		housePlacer.setHotel(hasHotel);
			
		}

	public void updatePawnStatus(int fieldNumber) {
		gui.getFields()[fieldNumber-1].setSubText("PANTSAT");	
	}	
}

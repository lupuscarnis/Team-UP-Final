package boundary;

import java.awt.Color;
import java.io.IOException;

import controllers.PlayerController;
import entities.Player;
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
	//private GUI gui = null;
	private GUI_Field[] guiFields = null;
	private GUI_Player[] guiPlayers = null;
	private GUI_Car[] carList = null;	
	
	public GUIController() throws IOException {
		 gui = new BoardGenerator().makeBoard();
	}	
	
	/**
	 * Added by Frederik on 06-01-2018 03:02:01
	 * 
	 * @throws InterruptedException
	 */
	public void setup(Player[] players) {
		
		setupPlayers(players);
	}

	public void setupGUIBoard(Field[] fields) {
	
		this.guiFields = new GUI_Field[40];
		
		initializeFields(fields);
		
		this.gui = new GUI(this.guiFields);
		
	}

	private void initializeFields(Field[] fields) {
		
		int fieldNumber = 0;
		for (Field field : fields) {
			
			switch(field.getFieldType()) {
			case BREWERY:
				GUI_Field tmp = new GUI_Brewery();
				BreweryField brewery = (BreweryField) field;
				tmp.setTitle(field.getTitle());
				tmp.setSubText(brewery.getPrice()+" kr.");
				tmp.setDescription(field.getDesc());
				
				this.guiFields[fieldNumber]=tmp;
				break;
			case CHANCE:
				tmp = new GUI_Chance();
				tmp.setSubText(field.getTitle());
				tmp.setDescription(field.getDesc());

				this.guiFields[fieldNumber]=tmp;
				break;
			case EXTRATAX:
				tmp = new GUI_Tax();
				tmp.setTitle(field.getDesc());
				tmp.setSubText(field.getTitle());
				tmp.setDescription(field.getDesc());

				this.guiFields[fieldNumber]=tmp;
				break;
			case FREEPARKING:
				tmp = new GUI_Refuge();
				tmp.setTitle(field.getTitle());
				tmp.setDescription(field.getDesc());
				tmp.setSubText("Helle");
				tmp.setBackGroundColor(Color.white);

				this.guiFields[fieldNumber]=tmp;
				break;
			case GOTOJAIL:
				tmp = new GUI_Jail();
				tmp.setTitle(field.getTitle());
				tmp.setSubText(field.getTitle());
				tmp.setDescription(field.getDesc());
				
				this.guiFields[fieldNumber]=tmp;
				break;
			case INCOMETAX:
				tmp = new GUI_Tax();
				tmp.setTitle(field.getDesc());
				tmp.setSubText(field.getTitle());
				tmp.setDescription(field.getDesc());

				this.guiFields[fieldNumber]=tmp;
				break;
			case LOT:
				tmp = new GUI_Street();
				LotField lot = (LotField) field;
				tmp.setTitle(field.getTitle());
				tmp.setSubText(lot.getPrice()+" kr.");
				tmp.setDescription(field.getDesc());
				
				Color color= Color.black;
				switch(lot.getColor()) {
				case BLUE:
					color=Color.blue;
					break;
				case GRAY:
					color=Color.gray;
					break;
				case GREEN:
					color=Color.green;
					break;
				case PINK:
					color=Color.pink;
					break;
				case PURPLE:
					color=Color.magenta;
					break;
				case RED:
					color=Color.red;
					break;
				case WHITE:
					color=Color.white;
					break;
				case YELLOW:
					color=Color.yellow;
					break;
				default:
					break;						
				}
				tmp.setBackGroundColor(color);
				
				this.guiFields[fieldNumber]=tmp;
				break;
			case SHIPPING:
				tmp = new GUI_Shipping();
				ShippingField shipping = (ShippingField) field;
				tmp.setTitle(field.getTitle());
				tmp.setSubText(shipping.getPrice()+" kr.");
				tmp.setDescription(field.getDesc());
				
				this.guiFields[fieldNumber]=tmp;
				
				break;
			case START:
				tmp = new GUI_Start();
				tmp.setTitle(field.getTitle());
				tmp.setDescription(field.getDesc());
				tmp.setSubText("");
				tmp.setBackGroundColor(Color.white);

				this.guiFields[fieldNumber]=tmp;
				break;
			case VISITJAIL:
				tmp = new GUI_Jail();
				tmp.setTitle(field.getTitle());
				tmp.setSubText(field.getDesc());
				tmp.setDescription(field.getDesc());
				
				this.guiFields[fieldNumber]=tmp;
				break;
			default:
				break;
			
			}
			fieldNumber++;
		}
		
	}
	
	
	/**
	 * Added by Frederik on 06-01-2018 03:18:12
	 * 
	 * @return
	 */

	public String[] getNewPlayerNames() {
		// get number of players
		String noOfPlayers = gui.getUserSelection("Vælg antal spillere", "3", "4", "5", "6");
		String[] players = new String[Integer.parseInt(noOfPlayers)];

		// get names of players
		String name;
		for (int i = 0; i < players.length; i++) {
			boolean invalidName=false;
			do {
				invalidName=false;
				name = this.gui.getUserString("Navn på spiller " + (i + 1) + "?");
				//checks for duplicate names
				for(int a=i;a>0;a--) {
					if(name.equals(players[a-1])) {
						invalidName=true;
					}
				}
				//checks if name is empty
				if(name.length()<1) {
					invalidName=true;
				}
			}
			while(invalidName);
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
			//should use the same constant value for game start amount as argument instead of hardcoding
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
		gui.getFields()[fieldId-1].setSubText("Ejer: " + name);		
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
			
			gP.setBalance(p.getBalance());			
		}		
	}	
}

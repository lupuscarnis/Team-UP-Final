package controllers;

import java.io.IOException;

import boundary.GUIController;
import entities.Player;
import entities.enums.BreweriesOwned;
import entities.enums.UserOption;
import entities.field.BreweryField;
import entities.field.LotField;
import entities.field.OwnableField;
import utilities.Messager;

/**
 * Added by Frederik on 06-01-2018 20:38:39
 * 
 * Class intended to hold logic reg. business transactions etc.
 *
 */
//
// TODO: Implement class
public class BusinessLogicController {

	private static BusinessLogicController instance;
	private GUIController gui = GUIController.getInstance();
	private GameBoardController gbc = GameBoardController.getInstance();

	private BusinessLogicController() throws IOException {
	}

	// Calculate rent for field
	public int calculateRent(OwnableField field) {
		// TODO: Implement method
		return 0;
	}

	// Pawn lot
	public void pawnLot(OwnableField field) {
		// TODO: Implement method
	}

	// set owner of (ownable)field.
	public void setOwner(OwnableField field, Player owner) {

		// set owner
		field.setOwner(owner);

		// update gui
		gui.removeLotOwner(field);
	}

	/**
	 * Added by Frederik on 06-01-2018 22:06:20
	 * 
	 * Set owner of Get Out Of Jail Card.
	 * 
	 * @param owner
	 * @param hasCard
	 */
	public void setGetOutOfJailCard(Player owner, boolean hasCard) {
		owner.setJailCard(hasCard);
	}

	/**
	 * Added by Frederik on 06-01-2018 23:16:41
	 * 
	 * Handles player wants to buy lot. DOES NOT CHECK FOR SUFFICIENT FUNDS!! MUST
	 * BE DONE BEFORE CALL TO METHOD!
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void buyLot(Player player) throws Exception {

		OwnableField of = (OwnableField) player.getCurrentField();

		// withdraw money
		player.withdraw(of.getPrice());

		// set owner
		of.setOwner(player);

		// update gui
		Messager.showLotBoughtMessage(of);
	}

	/**
	 * Added by Frederik on 06-01-2018 23:19:10
	 * 
	 * Handles the case where the user has landed of owned field and must pay rent.
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void payRent(Player currentPlayer) throws Exception {

		// TODO: MANGLER EN TERNING
		int faceValue = 10; // Random value - must come from dice!
		OwnableField currentField = (OwnableField) currentPlayer.getCurrentField();

		// finds how far player moved, without using the dice.
		/*
		 * 
		 * if (currentPlayer.getCurrentField().getFieldNumber() >=
		 * currentPlayer.getPreviousField().getFieldNumber()) { faceValue =
		 * currentPlayer.getPreviousField().getFieldNumber() -
		 * currentPlayer.getCurrentField().getFieldNumber(); } else { faceValue = 40 -
		 * currentPlayer.getPreviousField().getFieldNumber() +
		 * currentPlayer.getCurrentField().getFieldNumber(); }
		 */
		//
		int rent = currentField.calculateRent(faceValue);
		Player payee = currentField.getOwner();
		Player payer = currentPlayer;

		// tell user he must pay rent
		Messager.showMustPayRent(payee.getName(), rent, currentPlayer.getName());

		// withdraw from payer
		// TODO: What happens if user cant afford?
		payer.withdraw(rent);

		// deposit to payee
		payee.deposit(rent);

		// update balances in gui
		gui.updateBalance(new Player[] { payer, payee });
	}

	/*
	 * OwnableField field = (OwnableField) currentPlayer.getCurrentField(); Player
	 * owner = field.getOwner(); ======= // tell user he must pay rent
	 * Messager.showMustPayRent(payee.getName(), rent); >>>>>>> branch
	 * '2.-iteration' of https://github.com/lupuscarnis/Team-UP-Final.git
	 * 
	 * // withdraw from payer // TODO: What happens if user cant afford?
	 * payer.withdraw(rent);
	 * 
	 * // deposit to payee payee.deposit(rent);
	 * 
	 * // update balances in gui gui.updateBalance(new Player[] { payer, payee }); }
	 * 
	 * /** Added by Kasper on 09-01-2018 23:16:41
	 * 
	 * Handles player wants to build a house. DOES NOT CHECK FOR SUFFICIENT FUNDS!!
	 * MUST BE DONE BEFORE CALL TO METHOD!
	 * 
	 * @param player
	 * 
	 * @throws Exception
	 */

	public int playerNetWorth(Player currentPlayer) throws IOException {
		int netWorth;
		int playerBalance = currentPlayer.getBalance();
		int playerFieldWorth = 0;

		OwnableField[] fieldsOwned = GameBoardController.getInstance().getFieldsByOwner(currentPlayer);
		for (OwnableField field : fieldsOwned) {
			if (field instanceof LotField) {
				LotField lotField = (LotField) field;
				playerFieldWorth += lotField.getPrice()
						+ (lotField.getHouseCount() + 5 * lotField.getHotelCount()) * lotField.getBuildingCost();
			}
		}

		netWorth = playerBalance + playerFieldWorth;
		return netWorth;

	}

	public void buildHouse(Player player) throws Exception {
		LotField lf = (LotField) player.getCurrentField();

		// withdraw money (Price of one house)
		player.withdraw(lf.getBuildingCost());

		// update number of houses on lot

		// update gui
		gui.updateBalance(player);
		// gui.updateLotOwner(player.getName(), of.getFieldNumber());
		gui.showMessage("Du har nu bygget et hotel på grunden: " + lf.getTitle());
	}

	/**
	 * Added by Kasper on 09-01-2018 23:16:41
	 * 
	 * Handles player wants to build a house. DOES NOT CHECK FOR SUFFICIENT FUNDS OR
	 * IF THE LOT HAS 4 HOUSES AS REQUIRED!! MUST BE DONE BEFORE CALL TO METHOD!
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void buildHotel(Player player) throws Exception {
		LotField lf = (LotField) player.getCurrentField();

		// withdraw money (5 times the cost of a house)
		player.withdraw(lf.getPrice() * 5);

		// set owner
		lf.setOwner(player);

		// update gui
		gui.updateBalance(player);
		// gui.updateLotOwner(player.getName(), of.getFieldNumber());
		gui.showMessage("Du har nu bygget et hotel på grunden: " + lf.getTitle());
	}

	/**
	 * Added by Frederik on 07-01-2018 00:05:28
	 * 
	 * Check if player still has money left, else remove player from game
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public Player[] evaluatePlayer(Player currentPlayer, Player[] allPlayers) throws Exception {

		if (currentPlayer.getNetWorth() <= 0) {

			Player[] tmp = new Player[allPlayers.length - 1];

			int inner = 0;
			// traverse player array and insert into new array
			for (int i = 0; i < allPlayers.length; i++) {

				if (allPlayers[i] != currentPlayer) {
					tmp[inner] = allPlayers[i];
					inner++;
				}
			}

			// remove all owned fields
			for (OwnableField ownedField : gbc.getFieldsByOwner(currentPlayer)) {
				setOwner(ownedField, null);
			}

			// update gui and remove player
			gui.removePlayer(currentPlayer);

			return tmp;
		}

		return allPlayers;
	}

	public static BusinessLogicController getInstance() throws IOException {
		if (instance == null)
			instance = new BusinessLogicController();

		return instance;
	}

	/**
	 * Added by Frederik on 09-01-2018 00:17:56
	 * 
	 * Check if user can afford lot
	 * 
	 * @param currentPlayer
	 * @return
	 */
	public boolean userCanAfford(int currentPlayerBalance, OwnableField fieldToBuy) {

		if (currentPlayerBalance >= fieldToBuy.getPrice())
			return true;

		return false;
	}

	public void payIncomeTax(Player currentPlayer, UserOption choice) throws Exception {

		int sumToCollect = 0;

		if (choice == UserOption.IncomeTaxPay4000)
			sumToCollect = 4000;
		else
			sumToCollect = (int) Math.floor(currentPlayer.getBalance() * 0.1);

		currentPlayer.withdraw(sumToCollect);

		Messager.showYouPaidIncomeTax(currentPlayer, sumToCollect);
	}

	/**
	 * Added by Kasper on 09-01-2018 00:17:56
	 * 
	 * Check if user can afford to build a house
	 * 
	 * @param currentPlayer
	 *            LotField
	 * @return
	 */
	public boolean userCanAffordHouse(int currentPlayerBalance, LotField fieldToBuy) {

		if (currentPlayerBalance >= fieldToBuy.getBuildingCost())
			return true;

		return false;
	}

	/**
	 * Added by Kasper on 09-01-2018 00:17:56
	 * 
	 * Check if user can afford to build a hotel (price getBuildingCost * 5)
	 * 
	 * @param currentPlayer
	 *            LotField
	 * @return
	 */
	public boolean userCanAffordHotel(int currentPlayerBalance, LotField fieldToBuy) {

		if (currentPlayerBalance >= fieldToBuy.getBuildingCost() * 5)
			return true;

		return false;
	}

	public OwnableField[] getPawnableFields(Player owner) throws IOException {
		OwnableField[] fieldsOwned = GameBoardController.getInstance().getFieldsByOwner(owner);

		// if no fields are found return empty array.
		if (fieldsOwned.length == 0)
			return new OwnableField[0];

		// har LotFields UDEN grunde
		int index = 0;
		OwnableField[] fields = new OwnableField[40]; // given high number by design, as to avoid out of bounds
														// exception.

		for (OwnableField field : fieldsOwned) {

			// if lot field then make sure no buildings are on it
			if (field instanceof LotField) {
				LotField lf = (LotField) field;

				int buildingCount = lf.getHotelCount() + lf.getHouseCount();

				if (buildingCount == 0 && !lf.isPawned()) {
					fields[index] = field;
					index++;
				}
			}
			// if shipping/brewery then make sure it's now pawned.
			else {
				if (!field.isPawned()) {
					fields[index] = field;
					index++;
				}
			}
		}

		// count no of pawnable fields found
		int count = 0;
		for (OwnableField item : fields) {

			if (item != null)
				count++;
		}

		// copy to array of correct length
		index = 0;
		OwnableField[] tmp = new OwnableField[count];
		for (OwnableField ownableField : fields) {
			if (fields[index] != null) {
				tmp[index] = fields[index];
				index++;
			}
		}

		return tmp;
	}

	/**
	 * Indicates if a player can pawn any lots or not. *
	 * 
	 * Added by Frederik on 10-01-2018 19:19:19
	 * 
	 * @param currentPlayer
	 * @return
	 * @throws IOException
	 */
	public boolean canPawn(Player currentPlayer) throws IOException {
		return this.getPawnableFields(currentPlayer).length > 0;
	}

	public void pawnLot(String result, Player owner) throws Exception {

		boolean found = false;
		OwnableField[] fieldsOwned = GameBoardController.getInstance().getFieldsByOwner(owner);

		for (OwnableField field : fieldsOwned) {

			if (field.getTitle().equals(result)) {

				// indicate field is pawned
				field.setPawned(true);

				// pay player money for pawning
				field.getOwner().deposit(field.getPawnPrice());

				// update gui (is pawned and balance
				GUIController.getInstance().updatePawnStatus(field.getFieldNumber());
				GUIController.getInstance().updateBalance(field.getOwner());

				// confirm in gui
				Messager.showFieldPawned(field.getTitle());

				found = true;
			}
		}

		if (!found)
			throw new Exception("Field never found!");
	}
}

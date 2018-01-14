package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.FieldName;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import utilities.Messager;

/**
 * Class intended to hold logic reg. business transactions etc.
 */
public class BusinessLogicController extends BaseController {

	public static final int MONEY_FOR_PASSING_START = 4000;
	
	public BusinessLogicController() throws IOException {
	}


	/**
	 * Helper method: Looks up a "GUI"-player from a "Logic"-player name.
	 * 
	 * @param field
	 */
	public void pawnLot(OwnableField field) {
		// TODO: Implement method
	}

	/**
	 * Helper method: Looks up a "GUI"-player from a "Logic"-player name.
	 * 
	 * @param playerNameToFind
	 * @return
	 * @throws Exception
	 * Sets the owner  of the field.
	 * 
	 * @param field
	 * @param owner
	 */
	public void setOwner(OwnableField field, Player owner) {

		// set owner
		field.setOwner(owner);

		// update gui
		gui.removeLotOwner(field);
	}

	/** 
	 * Set owner of Get Out Of Jail Card.
	 * 
	 * @param owner
	 * @param hasCard
	 */
	public void setGetOutOfJailCard(Player owner, boolean hasCard) {
		owner.setJailCard(hasCard);
	}

	/**
	 * Handles player wants to buy lot. The player will get field even if it can't afford it.
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
	 * Handles the case where the user has landed on owned field and must pay rent.
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void payRent(Player currentPlayer) throws Exception {

		// Gets the last dice.
		int faceValue = currentPlayer.getLastRoll(); 
		OwnableField currentField = (OwnableField) currentPlayer.getCurrentField();

		int rent = currentField.calculateRent(faceValue);
		Player payee = currentField.getOwner();
		Player payer = currentPlayer;

		// tell user he must pay rent
		Messager.showMustPayRent(payee.getName(), rent, currentPlayer.getName());

		// withdraw from payer
		//nothing happends if payer can't pay
		payer.withdraw(rent);

		// deposit to payee
		payee.deposit(rent);

		// update balances in gui
		gui.updateBalance(new Player[] { payer, payee });
	}

/**
 * Calculates the players Current NetWorth	
 * @param currentPlayer
 * @return int
 * @throws IOException
 */
	public int playerNetWorth(Player currentPlayer) throws IOException {
		int netWorth;
		int playerBalance = currentPlayer.getBalance();
		int playerFieldWorth = 0;

		OwnableField[] fieldsOwned = gbc.getFieldsByOwner(currentPlayer);
		for (OwnableField field : fieldsOwned) {
			if (field instanceof LotField) {
				LotField lotField = (LotField) field;
				playerFieldWorth += (lotField.getPrice()
						+ (lotField.getHouseCount() + 5 * lotField.getHotelCount()) * lotField.getHousePrice()) / 2;
			}
		}

		netWorth = playerBalance + playerFieldWorth;
		return netWorth;
	}

	/**
	 * Builds a house and withdraws the money
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void buildHouse(Player player) throws Exception {
		LotField lf = (LotField) player.getCurrentField();
		// withdraw money (Price of one house)
		player.withdraw(lf.getHousePrice());
		// update number of houses on lot + 1
		lf.setHouseCount(lf.getHouseCount() + 1);
		// update number of houses on lot + 1 (GUI)
		gui.setHouse(lf.getHouseCount(), lf.getFieldNumber());
		// update gui
		gui.updateBalance(player);
		// gui.updateLotOwner(player.getName(), of.getFieldNumber());
		gui.showMessage("Du har nu bygget et hus på grunden: " + lf.getTitle());
	}

	/** 
	 * Handles player wants to build a house. DOES NOT CHECK FOR SUFFICIENT FUNDS OR
	 * IF THE LOT HAS 4 HOUSES AS REQUIRED!! MUST BE DONE BEFORE CALL TO METHOD!
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void buildHotel(Player player) throws Exception {
		LotField lf = (LotField) player.getCurrentField();
		// withdraw money (5 times the cost of a house)
		player.withdraw(lf.getHousePrice() * 5);
		// update number of houses on lot
		lf.setHouseCount(0);
		// update number of hotels on lot
		lf.setHotelCount(1);
		// set owner
		lf.setOwner(player);
		// update gui
		gui.updateBalance(player);
		
		gui.setHouse(0, lf.getFieldNumber());
		
		gui.setHotel(true, lf.getFieldNumber());
		// gui.updateLotOwner(player.getName(), of.getFieldNumber());
		gui.showMessage("Du har nu bygget et hotel på grunden: " + lf.getTitle());
	
		
	}

	/** 
	 * Check if player still has money left, else remove player from game
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public Player[] evaluatePlayer(Player currentPlayer, Player[] allPlayers) throws Exception {

		if (this.playerNetWorth(currentPlayer) <= 0) {

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
	/**
	 *	Pays the IncomeTax
	 * 10% option
	 * determines the value
	 * 
	 * @param currentPlayer
	 * @param choice
	 * @throws Exception
	 */
	public void payIncomeTax(Player currentPlayer, UserOption choice) throws Exception {

		int sumToCollect = 0;

		if (choice == UserOption.IncomeTaxPay4000)
			sumToCollect = 4000;
		else
			sumToCollect = (int) Math.floor(playerNetWorth(currentPlayer) * 0.1);

		currentPlayer.withdraw(sumToCollect);

		Messager.showYouPaidIncomeTax(currentPlayer, sumToCollect);
	}

	/**
	 * 
	 * Check if user can afford lot
	 * 
	 * @param currentPlayer
	 * @return
	 */
	public boolean userCanAffordLot(int currentPlayerBalance, OwnableField fieldToBuy) {

		if (currentPlayerBalance >= fieldToBuy.getPrice())
			return true;

		return false;
	}

	/**
	 * 
	 * Check if user can afford to build a house
	 * 
	 * @param currentPlayer
	 *            LotField
	 * @return
	 */
	public boolean userCanAffordHouse(int currentPlayerBalance, LotField fieldToBuy) {

		if (currentPlayerBalance >= fieldToBuy.getHousePrice())
			return true;

		return false;
	}

	/**
	 * 
	 * Check if user can afford to build a hotel (price getBuildingCost * 5)
	 * 
	 * @param currentPlayer
	 *            LotField
	 * @return
	 */
	public boolean userCanAffordHotel(int currentPlayerBalance, LotField fieldToBuy) {

		if (currentPlayerBalance >= fieldToBuy.getHousePrice() * 5)
			return true;

		return false;
	}
	
	/**
	 * Check if a user can build a house
	 * 
	 * @param currentPlayer
	 *            LotField
	 * @return
	 */
	public boolean playerCanBuildHouse(Player currentPlayer, LotField lf) {
		
		if (lf.getOwner() == currentPlayer && userCanAffordHouse(currentPlayer.getBalance(), lf) && lf.getHouseCount() < 4 && lf.getHotelCount() != 1)
			return true;

		return false;
	}
	
	/**
	 * 
	 * Check if a user can build a hotel
	 * 
	 * @param currentPlayer
	 *            LotField
	 * @return
	 */
	public boolean playerCanBuildHotel(Player currentPlayer, LotField lf) {
		
		if (lf.getOwner() == currentPlayer && userCanAffordHotel(currentPlayer.getBalance(), lf) && lf.getHouseCount() == 4	&& lf.getHotelCount() != 1)
			return true;

		return false;
	}


	/**
	 * Indicates if a user has houses to sell or not.
	 * 
	 * @param currentPlayer
	 * @return false if nothing to sell, true if yes.
	 * @throws Exception
	 */
	public boolean hasHouse(Player currentPlayer) throws Exception {
		// Get the fields owned by our player
		OwnableField[] ownedField = gbc.getFieldsByOwner(currentPlayer);

		// runs all owned fields trough checking if pawned
		for (OwnableField field : ownedField) {

			// if lot field check for buildings
			
			if (field instanceof LotField) {
				LotField lf = (LotField) field;

				// check if there are houses on the lotfield
				if (lf.getHouseCount() >= 1) {
					return true;
				}

			}
		}
		return false;
	}
	
	/**
	 * Indicates if a user has houses to sell or not.
	 * 
	 * @param currentPlayer
	 * @return false if nothing to sell, true if yes.
	 * @throws Exception
	 */
	public boolean hasHotel(Player currentPlayer) throws Exception {
		// Get the fields owned by our player
		OwnableField[] ownedField = gbc.getFieldsByOwner(currentPlayer);

		// runs all owned fields trough checking if pawned
		for (OwnableField field : ownedField) {

			// if lot field check for buildings
			
			if (field instanceof LotField) {
				LotField lf = (LotField) field;

				// check if there are houses on the lotfield
				if (lf.getHotelCount() >= 1) {
					return true;
				}

			}
		}
		return false;
	}
	

	/**
	 * @param result
	 *            The field which houses are to be sold.
	 * @param owner
	 *            Owner of the field.
	 * @throws Exception
	 */
	public void sellHouse(String result, Player owner) throws Exception {
		OwnableField[] fieldsOwned = gbc.getFieldsByOwner(owner);

		for (OwnableField fields : fieldsOwned) {

			if (fields instanceof LotField) {
				LotField lf = (LotField) fields;

				// Determine desposit amount for the seller
				int depositAmount = (lf.getHousePrice() * lf.getHouseCount()) / 2;

				lf.setHouseCount(lf.getHouseCount() - 1);

				owner.deposit(depositAmount);
				gui.updateBalance(owner);

				// update GUI FAILSAFE
				if (lf.getHouseCount() < 0)
				{

					gui.setHouse(0, fields.getFieldNumber());

				} else {
					
					gui.setHouse(lf.getHouseCount(), fields.getFieldNumber());
					
				}
				// confirm in gui
				Messager.showHouseSold(lf);
			}
		}
	}

	/**
	 * @param result
	 *            The field which hotels are to be sold.
	 * @param owner
	 *            Owner of the field.
	 * @throws Exception
	 */
	public void sellHotel(String result, Player owner) throws Exception {
		OwnableField[] fieldsOwned = gbc.getFieldsByOwner(owner);

		for (OwnableField fields : fieldsOwned) {

			if (fields instanceof LotField) {
				LotField lf = (LotField) fields;

				// Determine desposit amount for the seller
				int depositAmount = lf.getHotelPrice() / 2;

				lf.setHotelCount(lf.getHotelCount() - 1);

				owner.deposit(depositAmount);
				gui.updateBalance(owner);

				// update GUI FAILSAFE
				if (lf.getHotelCount() < 0)
				{

					gui.setHotel(false, fields.getFieldNumber());

				} else {
					
					gui.setHotel(false, fields.getFieldNumber());
					
				}
				// confirm in gui
				Messager.showHouseSold(lf);
			}
		}
	}
	/**
	 * @param currentPlayer
	 * @return Returns an List Of Fields that has houses or returns null if no fields
	 *         are pawned
	 * @throws Exception
	 */
	public OwnableField[] getFieldsWithHouses(Player currentPlayer) throws Exception {
		// Get the fields owned by our player
		OwnableField[] ownedFields = gbc.getFieldsByOwner(currentPlayer);
		// Creates temporary storage
		OwnableField[] tmp = new OwnableField[40];
		// Creates Variables
		int index = 0;
		int count = 0;
		int index2 = 0;
		// runs all owned fields trough checking if pawned adding them to tmp
		for (OwnableField field : ownedFields) {

			// if lot field then make sure no buildings are on it
			if (field instanceof LotField) {
				LotField lf = (LotField) field;

				int buildingCount = lf.getHouseCount();

				// count of Pawnedfields
				// uses Count to create a semi Dynamic array

				if (buildingCount >= 1 && !lf.isPawned()) {

					tmp[index] = ownedFields[index];
					index++;
					count++;
				} else {
					index++;
				}
			}

		}
		// Creates a New OwnedFields to the right value of pawned fields while clearing
		// all null posts
		OwnableField[] cleanVersion = new OwnableField[count];
		// clear all null posts

		for (OwnableField housesFields : tmp) {

			if (housesFields != null) {
				cleanVersion[index2] = housesFields;
				index2++;
			}

		}
		return cleanVersion;
	}

	/**
	 * @param currentPlayer
	 * @return Returns an List Of Fields that has hotels or returns null if no fields
	 *         are pawned
	 * @throws Exception
	 */
	public OwnableField[] getFieldsWithHotels(Player currentPlayer) throws Exception {
		// Get the fields owned by our player
		OwnableField[] ownedFields = gbc.getFieldsByOwner(currentPlayer);
		// Creates temporary storage
		OwnableField[] tmp = new OwnableField[40];
		// Creates Variables
		int index = 0;
		int count = 0;
		int index2 = 0;
		// runs all owned fields trough checking if pawned adding them to tmp
		for (OwnableField field : ownedFields) {

			// if lot field then make sure no buildings are on it
			if (field instanceof LotField) {
				LotField lf = (LotField) field;

				int hotelCount = lf.getHotelCount();

				// count of Pawnedfields
				// uses Count to create a semi Dynamic array

				if (hotelCount >= 1 && !lf.isPawned()) {

					tmp[index] = ownedFields[index];
					index++;
					count++;
				} else {
					index++;
				}
			}

		}
		// Creates a New OwnedFields to the right value of pawned fields while clearing
		// all null posts
		OwnableField[] cleanVersion = new OwnableField[count];
		// clear all null posts

		for (OwnableField housesFields : tmp) {

			if (housesFields != null) {
				cleanVersion[index2] = housesFields;
				index2++;
			}

		}
		return cleanVersion;
	}
	/**
	 * Gets a list of all the all the fields player have that a pawnable
	 * @param owner
	 * @return
	 * @throws IOException
	 */
	public OwnableField[] getPawnableFields(Player owner) throws IOException {
		OwnableField[] fieldsOwned = gbc.getFieldsByOwner(owner);

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
	 * @param currentPlayer
	 * @return Returns an List Of Fields thats pawned or returns null if no fields
	 *         are pawned
	 * @throws Exception
	 */
	public OwnableField[] getPawnedFields(Player currentPlayer) throws Exception {
		// Get the fields owned by our player
		OwnableField[] ownedFields = gbc.getFieldsByOwner(currentPlayer);
		// Creates temporary storage
		OwnableField[] tmp = new OwnableField[40];
		// Creates Variables
		int index = 0;
		int count = 0;
		int index2 = 0;
		// runs all owned fields trough checking if pawned adding them to tmp
		for (OwnableField fields : ownedFields) {

			// count of Pawnedfields
			// uses Count to create a semi Dynamic array
			if (fields.isPawned() == true) {

				tmp[index] = ownedFields[index];
				index++;
				count++;
			} else {
				index++;
			}
		}

		// Creates a New OwnedFields to the right value of pawned fields while clearing
		// all null posts
		OwnableField[] cleanVersion = new OwnableField[count];
		// clear all null posts

		for (OwnableField pawnedFields : tmp) {

			if (pawnedFields != null) {
				cleanVersion[index2] = pawnedFields;
				index2++;
			}

		}
		return cleanVersion;
	}

	/**
	 * Indicates if a user has anything to pawn or not.
	 * 
	 * @param currentPlayer
	 * @return false if nothing to pawn, true if yes.
	 * @throws Exception
	 */
	public boolean hasPawn(Player currentPlayer) throws Exception {
		// Get the fields owned by our player
		OwnableField[] ownedFields = gbc.getFieldsByOwner(currentPlayer);

		// runs all owned fields trough checking if pawned
		for (OwnableField fields : ownedFields) {

			// check if there is a Pawnedfields
			if (fields.isPawned() == true) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if a player can pawn any lots or not. *
	 * 
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
		OwnableField[] fieldsOwned = gbc.getFieldsByOwner(owner);

		for (OwnableField field : fieldsOwned) {

			if (field.getTitle().equals(result)) {

				// indicate field is pawned
				field.setPawned(true);

				// pay player money for pawning
				field.getOwner().deposit(field.getPawnPrice());

				// update gui (is pawned and balance
				gui.setPawnStatus(field.getFieldNumber());
				gui.updateBalance(field.getOwner());

				// confirm in gui
				Messager.showFieldPawned(field.getTitle());

				found = true;
			}
		}

		if (!found)
			throw new Exception("Field never found!");
	}
/**
 * Creates a auction And returns the highest bidder.
 * @param field
 * @param players
 * @return
 * @throws Exception
 */
	
	public Player auction(Field field, Player[] players) throws Exception {
		// lige nu laver du en rigtig spiller om til highestbidder, spilleren slettes,
		// der skal nok sendes noget andet videre
		Player highestBidder = new Player("highestBidder", 0);
		Player NoBid = new Player("NoBid", 0);
		int highestBid = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].getBalance() != 0) {
				int newBid = Messager.showAuctionMessage(players[i], field);

				if (newBid > highestBid) {
					if (players[i].getBalance() > newBid) {
						highestBid = newBid;
						highestBidder = players[i];
					} else {
						Messager.showMessage(highestBidder.getName()
								+ " bød mere end han har råd til, og er diskvalificeret fra at byde");
					}
				}
			}
			if (highestBid == 0) {
				highestBidder = NoBid;
			}
		}

		highestBidder.withdraw(highestBid);

		return highestBidder;
	}

	/**
	 * @param result
	 *            The field to be unpawned.
	 * @param owner
	 *            Owner of the field.
	 * @throws Exception
	 */
	public void unpawn(String result, Player owner) throws Exception {
		OwnableField[] fieldsOwned = gbc.getFieldsByOwner(owner);

		for (OwnableField fields : fieldsOwned) {
			if (fields.getTitle().equals(result)) {

				int cost = 0;

				// cost is first set to the pawn price + 100 for each 1000 of the pawn price
				// then if modulus of (pawnprice/10)/100 is different from 0 (if it doesn't
				// divide perfectly with 100) you add 100 to that
				// it is supposed to round UP to nearest 100
				cost = fields.getPawnPrice() + ((fields.getPawnPrice() / 1000) * 100);
				if ((fields.getPawnPrice() / 10) % 100 != 0) {
					cost += 100;
				}

				owner.withdraw(cost);
				gui.updateBalance(owner);

				fields.setPawned(false);
				gui.clearPawnStatus(fields.getFieldNumber(), owner.getName());

				// confirm in gui
				Messager.showFieldunPawned(fields.getTitle());
			}
		}
	}

	/**
	 * semi removes the player from the game. Removes him as owner.
	 * @param deadGuy
	 * @throws Exception
	 */
	public void destroyPlayer(Player deadGuy) throws Exception {
		// hvis implementeret her, vil beskeden gentages en masse gange
		// Messager.showMessage(deadGuy.getName()+" har mistet alle hans penge");
		OwnableField[] OF = gbc.getFieldsByOwner(deadGuy);
		for (OwnableField field : OF) {
			field.setOwner(null);
		}
			
		
			gui.updatePlayerPosition(deadGuy.getName(), deadGuy.getCurrentField().getFieldNumber(), gbc.getFieldByName(FieldName.Fængslet).getFieldNumber());
			deadGuy.setCurrentField(gbc.getFieldByName(FieldName.Fængslet));
			deadGuy.getAccount().setBalance(0);
		
	}
}
package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.FieldName;
import entities.enums.UserOption;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;
import utilities.FieldLoader;

public class FieldLogicController {

	private static FieldLogicController instance;

	private FieldLogicController() throws IOException {
	}

	public void handleFieldAction(Player currentPlayer) throws Exception {

		Field currentField = currentPlayer.getCurrentField();

		switch (currentField.getFieldType()) {

		case BREWERY:
			BreweryField bf = (BreweryField) currentField;

			// If no owner and user has the money he can buy
			if (bf.getOwner() == null && BusinessLogicController.getInstance().userCanAfford(currentPlayer.getBalance(), bf)) {
				// ask if user wants to buy
				GUIController.getInstance().showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				UserOption choice = GUIController.getInstance().showOptions("Vælg:",
						new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// user opted to buy lot
				if (choice == UserOption.BuyLot)
					
					BusinessLogicController.getInstance().buyLot(currentPlayer);		

					BusinessLogicController.getInstance().buyLot(currentPlayer);

			}
			// field has owner and player must pay rent
			// if owner != current player
			else

				BusinessLogicController.getInstance().payRent(currentPlayer);	

			BusinessLogicController.getInstance().payRent(currentPlayer);

			break;
		case CHANCE:
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType() + " draw a card");
			ChanceCardController.getInstance().drawChanceCard();
			ChanceCardController.getInstance().handleDraw(currentPlayer);

			// ingen grund til cast da den bare er en Field type
			break;
		case EXTRATAX:
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType());
			currentPlayer.withdraw(2000);
			break;
		case FREEPARKING:
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType() + " nothing happens");
			break;
		case GOTOJAIL:
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType());

			Field jail = GameBoardController.getInstance().getFieldByName(FieldName.Fængslet);

			currentPlayer.setCurrentField(jail);

			GUIController.getInstance().movePlayer(currentPlayer);

			currentPlayer.isInJail(true);

			break;

		case INCOMETAX: {
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType());


			UserOption userChoice = GUIController.getInstance().showOptions("Hvordan vil du betal din skat: ",
					new UserOption[] { UserOption.IncomeTaxPay4000, UserOption.IncomeTaxPayTenPercent });
			
			switch (userChoice) {

			case IncomeTaxPay4000: {
				currentPlayer.withdraw(4000);

				break;
			}
			case IncomeTaxPayTenPercent: {
				// TODO: Create a player constant that summs up his value

				break;
			}
			}
		}

		case LOT: {
			LotField lf = (LotField) currentField;

			// no owner!
			if (lf.getOwner() == null && BusinessLogicController.getInstance().userCanAfford(currentPlayer.getBalance(), lf)) {

				GUIController.getInstance().showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				UserOption choice = GUIController.getInstance().showOptions("Vælg:",
						new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// user opted to buy lot
				if (choice == UserOption.BuyLot)

					BusinessLogicController.getInstance().buyLot(currentPlayer);	

					BusinessLogicController.getInstance().buyLot(currentPlayer);
				System.out.println(currentPlayer.getName() + " Bought " + currentField.getFieldType() + "/"
						+ currentField.getFieldNumber());

				// Ask player if he/she wants to build a house (checks for owner, playerBalance
				// and number of houses on lot)

			} 
			else if ((lf.getOwner() == currentPlayer) && BusinessLogicController.getInstance().userCanAffordHouse(currentPlayer.getBalance(), lf)
					&& lf.getHouseCount() < 4) {

				GUIController.getInstance().showMessage("you have landed on a  " + currentField.getFieldType()
						+ " which you already own. Do you wish to build a house on it?");
				UserOption choice = GUIController.getInstance().showOptions("Vælg:",
						new UserOption[] { UserOption.BuyHouse, UserOption.NoThanks });

				// user opted to build a house
				if (choice == UserOption.BuyHouse)

					// Update number of houses on the lot
					lf.setHouseCount(lf.getHouseCount() + 1);

				BusinessLogicController.getInstance().buildHouse(currentPlayer);
				System.out.println(currentPlayer.getName() + " bought a house on " + currentField.getFieldType() + "/"
						+ currentField.getFieldNumber() + " the lot now has " + lf.getHouseCount() + " house(s) on it");

				// Ask player if he/she wants to build a hotel (checks for owner, playerBalance
				// and number of houses on lot)

			} else if ((lf.getOwner() == currentPlayer) && BusinessLogicController.getInstance().userCanAffordHotel(currentPlayer.getBalance(), lf)
					&& lf.getHouseCount() == 4 && lf.getHotelCount() != 1) {

				GUIController.getInstance().showMessage("you have landed on a  " + currentField.getFieldType()
						+ " which you already own. The lot already has 4 houses on it. Do you wish to build a hotel?");
				UserOption choice = GUIController.getInstance().showOptions("Vælg:",
						new UserOption[] { UserOption.BuyHotel, UserOption.NoThanks });

				// user opted to build a house
				if (choice == UserOption.BuyHotel)

					// Update number of hotels on the lot
					lf.setHotelCount(lf.getHotelCount() + 1);

				BusinessLogicController.getInstance().buildHotel(currentPlayer);
				System.out.println(currentPlayer.getName() + " bought a hotel on " + currentField.getFieldType() + "/"
						+ currentField.getFieldNumber() + " the lot now has " + lf.getHotelCount() + " hotel on it");


			}
			// pay rent

			else
				BusinessLogicController.getInstance().payRent(currentPlayer);

			break;
		}
		/**
		 * Added by Kasper on 1/9-2018
		 * 
		 * Handles Shipping field (buy/pay rent)
		 */

		case SHIPPING: {
			ShippingField sf = (ShippingField) currentField;
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType() + " do you wish to purchase it?");

			// no owner!
			if (sf.getOwner() == null && BusinessLogicController.getInstance().userCanAfford(currentPlayer.getBalance(), sf)) {

				GUIController.getInstance().showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				UserOption choice = GUIController.getInstance().showOptions("Vælg:",
						new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// user opted to buy lot
				if (choice == UserOption.BuyLot)

					BusinessLogicController.getInstance().buyLot(currentPlayer);	

				BusinessLogicController.getInstance().buyLot(currentPlayer);
				System.out.println(currentPlayer.getName() + " Bought " + currentField.getFieldType() + "/"
						+ currentField.getFieldNumber());

			}
			// pay rent
			else
				BusinessLogicController.getInstance().payRent(currentPlayer);

			break;
		}
		case START:
			GUIController.getInstance().showMessage("you have landed on " + currentField.getFieldType() + " you gain 4000 kr.");
			currentPlayer.deposit(4000);
			// ingen grund til cast da den bare er en Field type
			break;
		case VISITJAIL:
			GUIController.getInstance().showMessage(
					"you have landed on " + currentField.getFieldType() + " you are here on a visit, nothing happens.");
			// ingen grund til cast da den bare er en Field type
			break;
		default:
			throw new Exception("Case not found!");
		}

	}

	/**
	 * Added by Frederik on 23-11-2017 17:50:40
	 * 
	 * Calculates and returns next field for player.
	 * 
	 * @param faceValue
	 * @param currentFieldNumber
	 * @return
	 * @throws IOException 
	 */
	public Field getNextField(int currentFieldNumber, int faceValue) throws IOException {

		int nextFieldNo = faceValue + currentFieldNumber;

		// Check for valid next field
		if (nextFieldNo > GameBoardController.getInstance().FIELD_COUNT)
			nextFieldNo += -GameBoardController.getInstance().FIELD_COUNT;

		return GameBoardController.getInstance().getFieldByNumber(nextFieldNo);
	}

	public static FieldLogicController getInstance() throws IOException {

		if (instance == null)
			instance = new FieldLogicController();

		return instance;

	}
}

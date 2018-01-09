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
	private BusinessLogicController blc = BusinessLogicController.getInstance();
	private ChanceCardController ccc = ChanceCardController.getInstance();
	private controllers.GameBoardController gbc = GameBoardController.getInstance();
	private GUIController gui = GUIController.getInstance();

	private FieldLogicController() throws IOException {
	}

	public void handleFieldAction(Player currentPlayer) throws Exception {

		Field currentField = currentPlayer.getCurrentField();

		switch (currentField.getFieldType()) {

		case BREWERY:
			BreweryField bf = (BreweryField) currentField;

			// If no owner and user has the money he can buy
			if (bf.getOwner() == null && blc.userCanAfford(currentPlayer.getBalance(), bf)) {
				// ask if user wants to buy
				gui.showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				UserOption choice = gui.showOptions("Vælg:",
						new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// user opted to buy lot
				if (choice == UserOption.BuyLot)
					blc.buyLot(currentPlayer);		
			}
			// field has owner and player must pay rent 
			// if owner != current player
			else
				blc.payRent(currentPlayer);	

			break;
		case CHANCE:
			gui.showMessage("you have landed on " + currentField.getFieldType() + " draw a card");
			ccc.drawChanceCard();
			ccc.handleDraw(currentPlayer);

			// ingen grund til cast da den bare er en Field type
			break;
		case EXTRATAX:
			gui.showMessage("you have landed on " + currentField.getFieldType());
			currentPlayer.withdraw(2000);
			break;
		case FREEPARKING:
			gui.showMessage("you have landed on " + currentField.getFieldType() + " nothing happens");
			break;
		case GOTOJAIL:
			gui.showMessage("you have landed on " + currentField.getFieldType());

			Field jail = this.gbc.getFieldByName(FieldName.Fængslet);

			currentPlayer.setCurrentField(jail);

			gui.movePlayer(currentPlayer);

			currentPlayer.isInJail(true);

			break;
		case INCOMETAX:
			gui.showMessage("you have landed on " + currentField.getFieldType());
			currentPlayer.withdraw(4000);
			// at implementere valget der bruger 10% maa vente lidt
			break;

			/**
			 * Added by Kasper on 1/9-2018
			 * 
			 * Handles Lot field (buy/pay rent) 
			 */

		case LOT:
			LotField lf = (LotField) currentField;

			// no owner!
			if (lf.getOwner() == null && blc.userCanAfford(currentPlayer.getBalance(), lf)) {

				gui.showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				UserOption choice = gui.showOptions("Vælg:",
						new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// user opted to buy lot
				if (choice == UserOption.BuyLot)
					blc.buyLot(currentPlayer);	
				System.out.println(currentPlayer.getName() + " Bought "+currentField.getFieldType()+"/"+currentField.getFieldNumber());
				
				
			// Ask player if he/she wants to build a house	(checks for owner, playerBalance and number of houses on lot)
				
			} else if ((lf.getOwner() == currentPlayer) && blc.userCanAffordHouse(currentPlayer.getBalance(), lf) && lf.getHouseCount() < 4) {
				
				gui.showMessage(
						"you have landed on a  " + currentField.getFieldType() + " which you already own. Do you wish to build a house on it?");
				UserOption choice = gui.showOptions("Vælg:",
						new UserOption[] { UserOption.BuyHouse, UserOption.NoThanks });
				
				// user opted to build a house
				if (choice == UserOption.BuyHouse)
					
					//Update number of houses on the lot
					lf.setHouseCount(lf.getHouseCount()+1);
					
					blc.buildHouse(currentPlayer);	
				System.out.println(currentPlayer.getName() + " bought a house on "+currentField.getFieldType()+"/"+currentField.getFieldNumber()+" the lot now has " + lf.getHouseCount() + " house(s) on it");
				
				
			// Ask player if he/she wants to build a hotel	(checks for owner, playerBalance and number of houses on lot)	
				
			} else if ((lf.getOwner() == currentPlayer) && blc.userCanAffordHotel(currentPlayer.getBalance(), lf) && lf.getHouseCount() == 4 && lf.getHotelCount() != 1) {
				
				gui.showMessage(
						"you have landed on a  " + currentField.getFieldType() + " which you already own. The lot already has 4 houses on it. Do you wish to build a hotel?");
				UserOption choice = gui.showOptions("Vælg:",
						new UserOption[] { UserOption.BuyHotel, UserOption.NoThanks });
				
				// user opted to build a house
				if (choice == UserOption.BuyHotel)
					
					//Update number of hotels on the lot
					lf.setHotelCount(lf.getHotelCount()+1);
					
					blc.buildHotel(currentPlayer);	
				System.out.println(currentPlayer.getName() + " bought a hotel on "+currentField.getFieldType()+"/"+currentField.getFieldNumber()+" the lot now has " + lf.getHotelCount() + " hotel on it");
						
			}
			// pay rent

			else
				blc.payRent(currentPlayer);

			break;

			/**
			 * Added by Kasper on 1/9-2018
			 * 
			 * Handles Shipping field (buy/pay rent) 
			 */

		case SHIPPING:

			ShippingField sf = (ShippingField) currentField;

			// no owner!
			if (sf.getOwner() == null && blc.userCanAfford(currentPlayer.getBalance(), sf)) {

				gui.showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				UserOption choice = gui.showOptions("Vælg:",
						new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// user opted to buy lot
				if (choice == UserOption.BuyLot)
					blc.buyLot(currentPlayer);	
				System.out.println(currentPlayer.getName() + " Bought "+currentField.getFieldType()+"/"+currentField.getFieldNumber());

			}
			// pay rent
			else
				blc.payRent(currentPlayer);

			break;

		case START:
			gui.showMessage("you have landed on " + currentField.getFieldType() + " you gain 4000 kr.");
			currentPlayer.deposit(4000);
			// ingen grund til cast da den bare er en Field type
			break;
		case VISITJAIL:
			gui.showMessage(
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
	 */
	public Field getNextField(int currentFieldNumber, int faceValue) {

		int nextFieldNo = faceValue + currentFieldNumber;

		// Check for valid next field
		if (nextFieldNo > gbc.FIELD_COUNT)
			nextFieldNo += -gbc.FIELD_COUNT;

		return gbc.getFieldByNumber(nextFieldNo);
	}

	public static FieldLogicController getInstance() throws IOException {

		if (instance == null)
			instance = new FieldLogicController();

		return instance;

	}
}

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
import utilities.Messager;

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
		UserOption choice = null;
		
		switch (currentField.getFieldType()) {

		case BREWERY:
			BreweryField bf = (BreweryField) currentField;

			// If no owner and user has the money he can buy
			if (bf.getOwner() == null && blc.userCanAfford(currentPlayer.getBalance(), bf)) {
				// ask if user wants to buy
				gui.showMessage(
						"you have landed on a  " + currentField.getFieldType() + " do you wish to purchase it?");
				choice = gui.showOptions("Vælg:",
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
			
			
			
			// pay tax
			//blc.payIncomeTax(currentPlayer, choice);		
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
			
			// Tell user he must pay income tax and get choice (10% or 4000)
			choice = Messager.showMustPayIncomeTax(currentField.getFieldType());
			
			// pay tax
			blc.payIncomeTax(currentPlayer, choice);
			
			//gui.showMessage("you have landed on " + currentField.getFieldType());
			//currentPlayer.withdraw(4000);
			// at implementere valget der bruger 10% maa vente lidt
			break;

		case LOT:
			LotField lf = (LotField) currentField;

			// no owner!
			if (lf.getOwner() == null) {
				// 1. TODO: ask if player wants to buy

				// 2. if yes - set owner
				//blc.buyLot(currentPlayer);
			}
			// pay rent
			else
				blc.payRent(currentPlayer);

			break;
		case SHIPPING:

			ShippingField sf = (ShippingField) currentField;

			Messager.showWantToBuyMessage(currentField.getFieldType());

			// if not owned player can buy
			if (sf.getOwner() == null) {
				choice = Messager
						.presentOptions(new UserOption[] { UserOption.BuyLot, UserOption.NoThanks });

				// TODO: Check for enough money!
				if (choice == UserOption.BuyLot)
					blc.buyLot(currentPlayer);
			}
			// owned = pay rent if player != owner
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

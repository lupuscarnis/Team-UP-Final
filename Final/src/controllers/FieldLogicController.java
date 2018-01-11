package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.OwnableField;
import utilities.Messager;

public class FieldLogicController {

	private static FieldLogicController instance;
	private BusinessLogicController blc = BusinessLogicController.getInstance();
	private ChanceCardController ccc = ChanceCardController.getInstance();
	private controllers.GameBoardController gbc = GameBoardController.getInstance();
	private GUIController gui = GUIController.getInstance();

	private FieldLogicController() throws IOException {
	}

	public void handleFieldAction(Player currentPlayer, Player[] allPlayers) throws Exception {

		Field currentField = currentPlayer.getCurrentField();
		UserOption choice = null;

		switch (currentField.getFieldType()) {

		case LOT:
		case SHIPPING:
		case BREWERY:
			OwnableField of = (OwnableField) currentField;

			// no owner!
			if (of.getOwner() == null) {

				// TODO: What if player don't have the money?
				choice = Messager.showWantToBuyMessage(of.getTitle(), currentPlayer.getName());

				// user opted to buy field
				if (choice == UserOption.BuyField)
					{blc.buyLot(currentPlayer);}
				
		else if(choice == UserOption.NoThanks)
				{Player HighestBidder = blc.auction(currentPlayer.getCurrentField(), null);
				blc.buyLot(HighestBidder);
				
				}
				
			} 
			else if (of.getOwner() == currentPlayer) {
				
				//Player wants to buy a house
				choice = Messager.showWantToBuildHouseMessage(of.getTitle(), currentPlayer.getName());
				
				// user opted to build a house
				if (choice == UserOption.BuyHouse)
					blc.buildHouse(currentPlayer);
				

			}
			// pay rent
			else {
				if (!of.isPawned())
					blc.payRent(currentPlayer);
				else
					gui.showMessage("Du skal ikke betale leje da feltet er pantsat!");
			}
			break;
		case VISITJAIL:
			gui.showMessage(
					"you have landed on " + currentField.getFieldType() + " you are here on a visit, nothing happens.");
			// ingen grund til cast da den bare er en Field type
			break;
		case CHANCE:
			ccc.handleDraw(currentPlayer, allPlayers);			
			break;
		case EXTRATAX:
			break;
		case START:
		case FREEPARKING:

			// landed on START
			if (currentField.getFieldType() == FieldType.START) {

				// TODO: Move out to BLC and pay START money even if you dont land on start!!
				gui.showMessage("you have landed on " + currentField.getFieldType() + " you gain 4000 kr.");
				currentPlayer.deposit(4000);
			}
			// landed on FREE PARKTIN
			else {
				gui.showMessage("you have landed on " + currentField.getFieldType() + " nothing happens");
			}
			break;
		case GOTOJAIL:
			// Show message to player
			Messager.showMustGoToJail(currentPlayer);

			// handle logic reg. going to jail
			GameLogicCtrl.getInstance().handleGoToJail(currentPlayer);
			break;
		case INCOMETAX:
			// Tell user he must pay income tax and get choice (10% or 4000)
			choice = Messager.showMustPayIncomeTax(currentField.getFieldType(), currentPlayer.getName());

			// pay tax
			blc.payIncomeTax(currentPlayer, choice);
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

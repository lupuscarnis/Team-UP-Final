package controllers;

import java.io.IOException;
import entities.Player;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import utilities.Messager;

public class FieldLogicController extends BaseController {

	private BusinessLogicController blc = null;
	private ChanceCardController ccc = null;
	private GameLogicCtrl glc = null;

 
	/**
	 * default constructor.
	 * 
	 * 
	 */
	public FieldLogicController() throws IOException {
	}
	/**
	 * sets a bussiness logic Controller
	 * 
	 * @param BussinessLogicController
	 */
	public void setBlc(BusinessLogicController blc) {
		this.blc = blc;
	}
	/**
	 * Sets a ChanceCardController
	 * 
	 * @param ChanceCardController
	 */
	public void setCcc(ChanceCardController ccc) {
		this.ccc = ccc;
	}
	/**
	 * sets a GameLogicCtrl
	 * 
	 * @param GameLogicCtrl

	 */
	public void setGlc(GameLogicCtrl glc) {
		this.glc = glc;
	}
	/**
	 * finds the field type, and takes the appropriate action(s) for any field the player lands on
	 * 
	 * @param Player
	 * @param Player[]
	 * @throws Exception
	 */
	public void handleFieldAction(Player currentPlayer, Player[] allPlayers) throws Exception {

		Field currentField = currentPlayer.getCurrentField();
		UserOption choice = null;

		switch (currentField.getFieldType()) {

		case LOT:
			
			LotField lf = (LotField) currentField;

			// no owner! Player can buy
			if (lf.getOwner() == null) {

				// Check if player can afford to buy the lot
				if (blc.userCanAffordLot(currentPlayer.getBalance(), lf)) {

					choice = Messager.showWantToBuyMessage(lf.getTitle(), currentPlayer.getName());

					// user opted to buy field
					if (choice == UserOption.BuyField) {
						blc.buyLot(currentPlayer);
						
						// update gui
						Messager.showLotBoughtMessage((OwnableField) currentPlayer.getCurrentField());
					}

					// Check if player was presented with a choice or no choice was given (a little
					// redundant)
					else if (choice == UserOption.NoThanks || choice == null) {
						Player highestBidder = blc.auction(currentPlayer.getCurrentField(), allPlayers);

						if (highestBidder.getName() == "NoBid") {
							Messager.showMessage("ingen gad at købe " + currentField.getTitle());
						}

						else {
							OwnableField of = (OwnableField) currentField;
							of.setOwner(highestBidder);
							Messager.showLotBoughtMessage(of);
							// at opdatere GUI'en crasher programmet, saa lige nu bruger jeg console til at
							// teste det virker.
							System.out.println(highestBidder.getBalance());

						}
					}
				}
			}
			
			// Player owns this lot, has balance and lot has < 4 houses = Player can build a
			// house
			
			else if (blc.playerCanBuildHouse(currentPlayer, lf)) {
			
				// Moved the logic to BLC (playerCanBuildHouse)
				
//			else if (lf.getOwner() == currentPlayer && currentField.getFieldType() == FieldType.LOT
//					&& blc.userCanAffordHouse(currentPlayer.getBalance(), lf) && lf.getHouseCount() < 4) {

				// Player wants to buy a house
				choice = Messager.showWantToBuildHouseMessage(lf.getTitle(), currentPlayer.getName());

				// user opted to build a house
				if (choice == UserOption.BuyHouse) {
					blc.buildHouse(currentPlayer);
				}

			}
			
			// Player owns this lot, has balance and has 4 houses on it and no hotel already
			// = Player can build a hotel
			
			else if (blc.playerCanBuildHotel(currentPlayer, lf)) {
			
				// Moved the logic to BLC (playerCanBuildHotel)
				
//			else if (lf.getOwner() == currentPlayer && currentField.getFieldType() == FieldType.LOT
//					&& blc.userCanAffordHotel(currentPlayer.getBalance(), lf) && lf.getHouseCount() == 4
//					&& lf.getHotelCount() != 1) {

				// Player wants to buy a house
				choice = Messager.showWantToBuildHotelMessage(lf.getTitle(), currentPlayer.getName());

				// user opted to build a house
				if (choice == UserOption.BuyHotel) {
					blc.buildHotel(currentPlayer);
				}

			}
			// pay rent
			// pay rent
			else if (!lf.isPawned() | !lf.getOwner().isInJail()) {
				blc.payRent(currentPlayer);
			} else if (lf.getOwner().isInJail()) {
				gui.showMessage("Du skal ikke betale leje da feltets ejer er fængslet!");
			} else {
				gui.showMessage("Du skal ikke betale leje da feltet er pantsat!");
			}
			break;

		case SHIPPING:
		case BREWERY:
			OwnableField ofSB = (OwnableField) currentField;
			// no owner! Player can buy
			if (ofSB.getOwner() == null) {

				// Check if player can afford to buy the lot
				if (blc.userCanAffordLot(currentPlayer.getBalance(), ofSB)) {

					choice = Messager.showWantToBuyMessage(ofSB.getTitle(), currentPlayer.getName());

					// user opted to buy field
					if (choice == UserOption.BuyField) {
						blc.buyLot(currentPlayer);
					}
// if the player can't afford a lot, the lot goes on auction
				}
				if (blc.userCanAffordLot(currentPlayer.getBalance(), ofSB) == false) {
					Player highestBidder = blc.auction(currentPlayer.getCurrentField(), allPlayers);

					if (highestBidder.getName() == "NoBid") {
						Messager.showMessage("ingen gad at købe " + currentField.getTitle());
					}

					else {
						OwnableField of = (OwnableField) currentField;
						of.setOwner(highestBidder);
						Messager.showLotBoughtMessage(of);
					

					}
				}
			}
			// pay rent
			else if (!ofSB.isPawned() | !ofSB.getOwner().isInJail()) {
				blc.payRent(currentPlayer);
			} else if (ofSB.getOwner().isInJail()) {
				gui.showMessage("Du skal ikke betale leje da feltets ejer er fængslet!");
			} else {
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
				gui.showMessage("Du har landet på " + currentField.getFieldType() + " modtag 4000 kr.");
			}
			// landed on FREE PARKTIN
			else {
				gui.showMessage("du har landet på " + currentField.getFieldType() + " der sker intet");
			}
			break;
		case GOTOJAIL:
			// Show message to player
			Messager.showMustGoToJail(currentPlayer);

			// handle logic reg. going to jail
			glc.handleGoToJail(currentPlayer);
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
}
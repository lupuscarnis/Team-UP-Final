package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.LotField;
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
//			OwnableField of = (OwnableField) currentField;
			LotField lf = (LotField) currentField;
			
			// no owner! Player can buy
			if (lf.getOwner() == null) {

				// Check if player can afford to buy the lot
				if (blc.userCanAfford(currentPlayer.getBalance(), lf)) {

					choice = Messager.showWantToBuyMessage(lf.getTitle(), currentPlayer.getName());

					// user opted to buy field
					if (choice == UserOption.BuyField)
					{blc.buyLot(currentPlayer);}

					// Check if player was presented with a choice or no choice was given (a little redundant)
					else if(choice == UserOption.NoThanks || choice == null)
					{
						Player highestBidder = blc.auction(currentPlayer.getCurrentField(), allPlayers);


						if(highestBidder.getName() =="NoBid" ){Messager.showMessage("ingen gad at købe " +currentField);}

						else{
							OwnableField of = (OwnableField) currentField;
							of.setOwner(highestBidder);
							Messager.showLotBoughtMessage(of);
							//at opdatere GUI'en crasher programmet, saa lige nu bruger jeg console til at teste det virker.
							System.out.println(highestBidder.getBalance());
							
						}

					} 

				// If the player cannot afford to buy, still give the other players the possibility to buy it on auction 
//				} else {
//
//					Player highestBidder = blc.auction(currentPlayer.getCurrentField(), allPlayers);
//
//
//					if(highestBidder==null ){Messager.showMessage("ingen gad at købte " +currentPlayer.getCurrentField());}
//
//					else{
//						blc.buyLot(highestBidder);
//					}
//
//			
					}
			} 
			// Player owns this lot, has balance and lot has < 4 houses = Player can build a house
			else if (lf.getOwner() == currentPlayer && currentField.getFieldType() == FieldType.LOT && blc.userCanAffordHouse(currentPlayer.getBalance(), lf) && lf.getHouseCount() < 4 ) {
				
				//Player wants to buy a house
				choice = Messager.showWantToBuildHouseMessage(lf.getTitle(), currentPlayer.getName());

				// user opted to build a house
				if (choice == UserOption.BuyHouse)
					{blc.buildHouse(currentPlayer);}

			}
			// Player owns this lot, has balance and has 4 houses on it and no hotel already = Player can build a hotel
			else if (lf.getOwner() == currentPlayer  && currentField.getFieldType() == FieldType.LOT && blc.userCanAffordHotel(currentPlayer.getBalance(), lf) && lf.getHouseCount() == 4 && lf.getHotelCount() != 1) {
				
				//Player wants to buy a house
				choice = Messager.showWantToBuildHotelMessage(lf.getTitle(), currentPlayer.getName());

				// user opted to build a house
				if (choice == UserOption.BuyHotel)
					{blc.buildHotel(currentPlayer);}

			}
			// pay rent
			// pay rent
			else if(!lf.isPawned() | !lf.getOwner().isInJail()) {
					blc.payRent(currentPlayer);
				}
				else if(lf.getOwner().isInJail()){
					gui.showMessage("Du skal ikke betale leje da feltets ejer er fængslet!");
				}
					else {
						gui.showMessage("Du skal ikke betale leje da feltet er pantsat!");
					}
			break;
			
		case SHIPPING:
		case BREWERY:
			OwnableField ofSB = (OwnableField) currentField;
			// no owner! Player can buy
			if (ofSB.getOwner() == null) {
				
				// Check if player can afford to buy the lot
				if (blc.userCanAfford(currentPlayer.getBalance(), ofSB)) {
				
					choice = Messager.showWantToBuyMessage(ofSB.getTitle(), currentPlayer.getName());

					// user opted to buy field
					if (choice == UserOption.BuyField)
					{blc.buyLot(currentPlayer);}
					
				}

			}
			// pay rent
			else if(!ofSB.isPawned() | !ofSB.getOwner().isInJail()) {
					blc.payRent(currentPlayer);
				}
				else if(ofSB.getOwner().isInJail()){
					gui.showMessage("Du skal ikke betale leje da feltets ejer er fængslet!");
				}
					else {
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

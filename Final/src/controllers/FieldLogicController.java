package controllers;

import java.io.IOException;
import boundary.GUIController;

import entities.Cup;
import entities.Die;

import entities.Player;
import entities.enums.FieldName;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import entities.field.ShippingField;

import gui_main.GUI;


import utilities.FieldLoader;

import utilities.MyRandom;


import utilities.Messager;


public class FieldLogicController {


	FieldLoader Fl = new FieldLoader();
	ChanceCardController ccc1 = new ChanceCardController();
	private controllers.GameBoardController gbc1;
	private boundary.GUIController guic;
	private GUI gui1 = null;
	Player currentPlayer;
	Die d1 = new Die(1,6);
	Die d2 = new Die(1,6);
	Cup cup1 = new Cup(0, 0, d1, d2);
	
//	Field[] Fieldlist = Field[40];
	
	


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

		case LOT:
		case SHIPPING:
		case BREWERY:
			OwnableField of = (OwnableField) currentField;

			// no owner!
			if (of.getOwner() == null) {

				// TODO: What if player don't have the money?
				choice = Messager.showWantToBuyMessage(of.getTitle());

				// user opted to buy field
				if (choice == UserOption.BuyField)
					blc.buyLot(currentPlayer);
			}
			// pay rent
			else
				blc.payRent(currentPlayer);

			break;
		case VISITJAIL:
			gui.showMessage(
					"you have landed on " + currentField.getFieldType() + " you are here on a visit, nothing happens.");
			// ingen grund til cast da den bare er en Field type
			break;
		case CHANCE:
			gui.showMessage("you have landed on " + currentField.getFieldType() + " draw a card");
			ccc.drawChanceCard();
			ccc.handleDraw(currentPlayer);
			break;
		case EXTRATAX:
			break;
		case START:
		case FREEPARKING:

			// landed on START
			if (currentField.getFieldType() == FieldType.START) {
				
				//TODO: Move out to BLC and pay START money even if you dont land on start!!
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
			choice = Messager.showMustPayIncomeTax(currentField.getFieldType());

			// pay tax
			blc.payIncomeTax(currentPlayer, choice);
			break;

		default:
			throw new Exception("Case not found!");
		}


	}	


	

	/**
	 * Added by Frederik on 06-01-2018 23:49:04 
	 * 
	 * Rolls dice and moves player
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	//TODO: Make use of cup when throwing dice!
	public void rollAndMove(Player currentPlayer) throws Exception {
		
		int currentFieldNo = currentPlayer.getCurrentField().getFieldNumber();
		
		// Throw Die
		int faceValue = cup1.rollDice();

		// get next field		
		Field nextField = this.getNextField(currentFieldNo, faceValue);
		
		// Update current pos on player object 
		currentPlayer.setCurrentField(nextField);
	//if(currentPlayer.getPreviousField().getFieldNumber()>currentPlayer.getCurrentField().getFieldNumber())
		//{currentPlayer.deposit(4000);}
		
		// update gui
		guic.movePlayer(currentPlayer);		

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

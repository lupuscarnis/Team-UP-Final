package controllers;



import java.io.IOException;

import boundary.GUIController;

import entities.Player;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import entities.field.ShippingField;
import utilities.ChanceLoader;
import utilities.FieldLoader;
import utilities.MyRandom;


public class FieldLogicController {

	FieldLoader Fl = new FieldLoader();
	ChanceCardController ccc = null;
	private controllers.GameBoardController gbc;
	private GUIController gui = null;
	Player currentPlayer;
	
	public FieldLogicController(GameBoardController gbc, GUIController gui) throws IOException {		
		this.gbc = gbc;
		this.gui = gui;
		this.ccc=new ChanceCardController(new ChanceLoader().getCards());
	}

	
		public void resolveField(Player currentPlayer) throws Exception{
		
		Field currentField = currentPlayer.getCurrentField();		
		
		switch (currentField.getFieldType()) {

		case BREWERY:

			BreweryField bf = (BreweryField)currentField;
			gui.showMessage("you have landed on a  "+currentField.getFieldType()+" do you wish to purchase it?");

			 bf = (BreweryField) currentField;

			break;
		case CHANCE:
			gui.showMessage("you have landed on "+currentField.getFieldType()+" draw a card");
			ccc.drawChanceCard();
			ccc.handleDraw(currentPlayer);
			
			// ingen grund til cast da den bare er en Field type
			break;
		case EXTRATAX:
			gui.showMessage("you have landed on "+currentField.getFieldType());
			currentPlayer.withdraw(2000);
			// ingen grund til cast da den bare er en Field type
			break;
		case FREEPARKING:
			gui.showMessage("you have landed on "+currentField.getFieldType()+" nothing happens");
			// ingen grund til cast da den bare er en Field type
			break;
		case GOTOJAIL:
			gui.showMessage("you have landed on "+currentField.getFieldType());
			currentPlayer.setCurrentField(currentField);
			gui.movePlayer(currentPlayer);
			currentPlayer.isInJail(true);
			// er saa vidt jeg forstaar ikke muligt at implementere pt.
			// ingen grund til cast da den bare er en Field type
			break;
		case INCOMETAX:
			gui.showMessage("you have landed on "+currentField.getFieldType());
			currentPlayer.withdraw(4000);
			// at implementere valget der bruger 10% maa vente lidt
			// ingen grund til cast da den bare er en Field type
			break;	

		case LOT:			
			LotField lf = (LotField) currentField;
			BusinessLogicController blc = new BusinessLogicController(gui,gbc);
			gui.showMessage("you have landed on "+currentField.getFieldType()+" do you wish to purchase it?");
			// no owner!
			if (lf.getOwner() == null) {
				// 1. TODO: ask if player wants to buy

				// 2. if yes - set owner
				blc.buyLot(currentPlayer);
			}
			// pay rent
			else
				blc.payRent(currentPlayer);


			break;
		case SHIPPING:

			ShippingField sf = (ShippingField)currentField;
			gui.showMessage("you have landed on "+currentField.getFieldType()+" do you wish to purchase it?");

			sf = (ShippingField) currentField;


			break;
		case START:
			gui.showMessage("you have landed on "+currentField.getFieldType()+" you gain 4000 kr.");
			currentPlayer.deposit(4000);
			// ingen grund til cast da den bare er en Field type
			break;
		case VISITJAIL:
			gui.showMessage("you have landed on "+currentField.getFieldType() +" you are here on a visit, nothing happens.");
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
}

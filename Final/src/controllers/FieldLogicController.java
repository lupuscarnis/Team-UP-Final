package controllers;

import boundary.GUIController;
import entities.Player;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;
import utilities.MyRandom;

public class FieldLogicController {

	private controllers.GameBoardController gbc = null;
	private GUIController gui = null;

	public FieldLogicController(GameBoardController gbc, GUIController gui) {
		this.gbc = gbc;
		this.gui = gui;
	}

	public void resolveField(Player currentPlayer) throws Exception {
		
		Field currentField = currentPlayer.getCurrentField();		
		
		switch (currentField.getFieldType()) {
		case BREWERY:
			BreweryField bf = (BreweryField) currentField;
			break;
		case CHANCE:
			// ingen grund til cast da den bare er en Field type
			break;
		case EXTRATAX:
			// ingen grund til cast da den bare er en Field type
			break;
		case FREEPARKING:
			// ingen grund til cast da den bare er en Field type
			break;
		case GOTOJAIL:
			// ingen grund til cast da den bare er en Field type
			break;
		case INCOMETAX:
			// ingen grund til cast da den bare er en Field type
			break;
		case LOT:			
			LotField lf = (LotField) currentField;
			BusinessLogicController blc = new BusinessLogicController(gui,gbc);

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
			ShippingField sf = (ShippingField) currentField;

			break;
		case START:
			// ingen grund til cast da den bare er en Field type
			break;
		case VISITJAIL:
			// ingen grund til cast da den bare er en Field type
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
		int faceValue = MyRandom.randInt(2, 12);

		// get next field		
		Field nextField = this.getNextField(currentFieldNo, faceValue);
		
		// Update current pos on player object 
		currentPlayer.setCurrentField(nextField);
		
		// update gui
		gui.movePlayer(currentPlayer);		
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

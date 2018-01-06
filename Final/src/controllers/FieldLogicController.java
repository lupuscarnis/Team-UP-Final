package controllers;

import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;

public class FieldLogicController {
	private controllers.ChanceCardController ccc;
	private controllers.GameBoardController gbc;
	
	public entities.Player currentPlayer;
	// kan ikke finde en "currentplayer" i Game endnu, taenker at jeg kommer til at bruge den
	//Game.getCurrentPlayer()
	
	public FieldLogicController(GameBoardController gbc) {
		this.gbc=gbc;
	}
	
	
	public void resolveField(Field field) throws Exception
	{
		switch(field.getFieldType())
		{
		case BREWERY:
			BreweryField bf = (BreweryField)field;
			
			break;
		case CHANCE:
			ccc.drawChanceCard();
			ccc.handleDraw();
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
			LotField lf = (LotField)field;
			
			break;
		case SHIPPING:
			ShippingField sf = (ShippingField)field;
			
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
}

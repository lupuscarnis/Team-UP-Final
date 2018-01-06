package controllers;

import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;
import gui_main.GUI;

public class FieldLogicController {
	private controllers.ChanceCardController ccc;
	private controllers.GameBoardController gbc;
	private boundary.GUIController guic;
	private GUI gui = null;
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
			gui.showMessage("you have landed on a  "+field.getFieldType()+"do you wish to purchase it?");
			break;
		case CHANCE:
			ccc.drawChanceCard();
			ccc.handleDraw(currentPlayer);
			gui.showMessage("you have landed on "+field.getFieldType()+"");
			// ingen grund til cast da den bare er en Field type
			break;
		case EXTRATAX:
			gui.showMessage("you have landed on "+field.getFieldType());
			// ingen grund til cast da den bare er en Field type
			break;
		case FREEPARKING:
			gui.showMessage("you have landed on "+field.getFieldType());
			// ingen grund til cast da den bare er en Field type
			break;
		case GOTOJAIL:
			gui.showMessage("you have landed on "+field.getFieldType());
			// ingen grund til cast da den bare er en Field type
			break;
		case INCOMETAX:
			gui.showMessage("you have landed on "+field.getFieldType());
			// ingen grund til cast da den bare er en Field type
			break;
		case LOT:
			LotField lf = (LotField)field;
			
			gui.showMessage("you have landed on "+field.getFieldType());
			break;
		case SHIPPING:
			ShippingField sf = (ShippingField)field;
			
			gui.showMessage("you have landed on "+field.getFieldType());
			break;
		case START:
			gui.showMessage("you have landed on "+field.getFieldType());
			// ingen grund til cast da den bare er en Field type
			break;
		case VISITJAIL:
			gui.showMessage("you have landed on "+field.getFieldType());
			// ingen grund til cast da den bare er en Field type
			break;
		default:			
			throw new Exception("Case not found!");
		}
	}	
}

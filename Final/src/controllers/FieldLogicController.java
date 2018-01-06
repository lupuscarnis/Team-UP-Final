package controllers;

import entities.Player;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;
import gui_main.GUI;
import utilities.FieldLoader;

public class FieldLogicController {
	FieldLoader Fl = new FieldLoader();
	ChanceCardController ccc = new ChanceCardController(null);
	private controllers.GameBoardController gbc;
	private boundary.GUIController guic;
	private GUI gui = null;
	Player currentPlayer;
	Field[] fieldlist = new Field[40];
	Field[] Fieldlist = Field[40];
	
	
	for (int i = 0; i < fieldlist.length; i++) 
	{
		Field tmp = gbc.getFieldByNumber(i + 1);

		String title = tmp.getTitle();
		String text = tmp.getDesc();
}


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
			gui.showMessage("you have landed on a  "+field.getFieldType()+" do you wish to purchase it?");
			break;
		case CHANCE:
			ccc.drawChanceCard();
			ccc.handleDraw(currentPlayer);
			gui.showMessage("you have landed on "+field.getFieldType()+" draw a card");
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

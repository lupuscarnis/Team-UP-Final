package controllers;

import entities.Player;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import entities.field.ShippingField;
import gui_main.GUI;
import utilities.FieldLoader;
import boundary.GUIController;

public class FieldLogicController {
	FieldLoader Fl = new FieldLoader();
	ChanceCardController ccc = new ChanceCardController(null);
	private controllers.GameBoardController gbc;
	private boundary.GUIController guic;
	private GUI gui = null;
	Player currentPlayer;
	
//	Field[] Fieldlist = Field[40];
	
	



	// kan ikke finde en "currentplayer" i Game endnu, taenker at jeg kommer til at bruge den. 
	//Game.getCurrentPlayer()
	
	public FieldLogicController(GameBoardController gbc) {
		this.gbc=gbc;
	}
	
//	public int getPrice(int fieldnumber){
//	int price = 0;
//	OwnableField theField = OwnableField(null, fieldnumber, null) ;
//		return price;
//		det her gik ikke, har brug for metode der giver mig prisen, med indputtet fieldnumber. kig p[ getfieldbynumber fra bpundary
//	}
	
	
	public void resolveField(Field field) throws Exception
	{
		{
		
//		Field[] fieldlist = new Field[40];
//		for (int i = 0; i < fieldlist.length; i++) 
//		{
//			Field tmp = gbc.getFieldByNumber(i + 1);
//
//			String title = tmp.getTitle();
//			String text = tmp.getDesc();
		}
		
		switch(field.getFieldType())
		{
		case BREWERY:
			BreweryField bf = (BreweryField)field;
			gui.showMessage("you have landed on a  "+field.getFieldType()+" do you wish to purchase it?");
			break;
		case CHANCE:
			gui.showMessage("you have landed on "+field.getFieldType()+" draw a card");
			ccc.drawChanceCard();
			ccc.handleDraw(currentPlayer);
			
			// ingen grund til cast da den bare er en Field type
			break;
		case EXTRATAX:
			gui.showMessage("you have landed on "+field.getFieldType());
			currentPlayer.withdraw(2000);
			// ingen grund til cast da den bare er en Field type
			break;
		case FREEPARKING:
			gui.showMessage("you have landed on "+field.getFieldType()+" nothing happens");
			// ingen grund til cast da den bare er en Field type
			break;
		case GOTOJAIL:
			gui.showMessage("you have landed on "+field.getFieldType());
			guic.movePlayer(currentPlayer, 10);
			currentPlayer.isInJail(true);
			// er saa vidt jeg forstaar ikke muligt at implementere pt.
			// ingen grund til cast da den bare er en Field type
			break;
		case INCOMETAX:
			gui.showMessage("you have landed on "+field.getFieldType());
			currentPlayer.withdraw(4000);
			// at implementere valget der bruger 10% maa vente lidt
			// ingen grund til cast da den bare er en Field type
			break;
		case LOT:
			LotField lf = (LotField)field;
		//currentPlayer.getCurrentField().getPrice();
			gui.showMessage("you have landed on "+field.getFieldType()+" do you wish to purchase it?");
			break;
		case SHIPPING:
			ShippingField sf = (ShippingField)field;
			gui.showMessage("you have landed on "+field.getFieldType()+" do you wish to purchase it?");
			break;
		case START:
			gui.showMessage("you have landed on "+field.getFieldType()+" you gain 4000 kr.");
			currentPlayer.deposit(4000);
			// ingen grund til cast da den bare er en Field type
			break;
		case VISITJAIL:
			gui.showMessage("you have landed on "+field.getFieldType() +" you are here on a visit, nothing happens.");
			// ingen grund til cast da den bare er en Field type
			break;
		default:			
			throw new Exception("Case not found!");
		}
	}	

}

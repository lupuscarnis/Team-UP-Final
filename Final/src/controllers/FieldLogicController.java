package controllers;



import boundary.GUIController;
import entities.Cup;
import entities.Die;
import entities.Player;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;
import gui_main.GUI;
import utilities.FieldLoader;
import utilities.MyRandom;


public class FieldLogicController {

	FieldLoader Fl = new FieldLoader();
	ChanceCardController ccc = new ChanceCardController(null);
	private controllers.GameBoardController gbc;
	private boundary.GUIController guic;
	private GUI gui = null;
	Player currentPlayer;
	Die d1 = new Die(1,6);
	Die d2 = new Die(1,6);
	Cup cup1 = new Cup(0, 0, d1, d2);
	
//	Field[] Fieldlist = Field[40];
	
	



	// kan ikke finde en "currentplayer" i Game endnu, taenker at jeg kommer til at bruge den. 
	//Game.getCurrentPlayer()
	
	public FieldLogicController(GameBoardController gbc) {
		
		this.gbc=gbc;
	}

	
	
	public FieldLogicController(GameBoardController gbc, GUIController guic) {
		this.gbc = gbc;
		this.guic = guic;

	}

	
//	public int getPrice(int fieldnumber){
//	int price = 0;
//	OwnableField theField = OwnableField(null, fieldnumber, null) ;
//		return price;
//		det her gik ikke, har brug for metode der giver mig prisen, med indputtet fieldnumber. kig p[ getfieldbynumber fra bpundary
//	}
	
	
	//
	//{
		//{
		
//		Field[] fieldlist = new Field[40];
//		for (int i = 0; i < fieldlist.length; i++) 
//		{
//			Field tmp = gbc.getFieldByNumber(i + 1);
//
//			String title = tmp.getTitle();
//			String text = tmp.getDesc();
	//	}
		
//		switch(field.getFieldType())
//		{

	//public void resolveField(Player currentPlayer) throws Exception {
		
		public void resolveField(Field field) throws Exception{
		
		Field currentField = currentPlayer.getCurrentField();		
		
		switch (currentField.getFieldType()) {

		case BREWERY:

			BreweryField bf = (BreweryField)field;
			gui.showMessage("you have landed on a  "+field.getFieldType()+" do you wish to purchase it?");

			 bf = (BreweryField) currentField;

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
			currentPlayer.setCurrentField(field);
			guic.movePlayer(currentPlayer);
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
			LotField lf = (LotField) currentField;
			BusinessLogicController blc = new BusinessLogicController(guic,gbc);
			gui.showMessage("you have landed on "+field.getFieldType()+" do you wish to purchase it?");
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

			ShippingField sf = (ShippingField)field;
			gui.showMessage("you have landed on "+field.getFieldType()+" do you wish to purchase it?");

			sf = (ShippingField) currentField;


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
//		if(currentPlayer.getPreviousField().getFieldNumber()>currentPlayer.getPreviousField().getFieldNumber())
//		{currentPlayer.deposit(4000);}
		
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
}

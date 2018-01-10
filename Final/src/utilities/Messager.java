package utilities;

import java.io.IOException;

import boundary.GUIController;
import controllers.ChanceCardController;
import entities.Player;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.OwnableField;

// TODO: GUI skal ikke kaldes via GUIController.getInstance()
// TODO: All text must be in EITHER danish or english!!
public class Messager {

	//private static 
	public static UserOption presentOptions(UserOption[] options, String playerName) throws Exception {
		
		
		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		return GUIController.getInstance().showOptions(CHOOSE, options);
	}

	
	public static UserOption showWantToBuyMessage(String title, String playerName) throws Exception {
		GUIController.getInstance().showMessage("you have landed on \"" + title + "\" do you wish to purchase it?");
		
		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		return GUIController.getInstance().showOptions(CHOOSE, new UserOption[] { UserOption.BuyField, UserOption.NoThanks });
	}

	public static void showLotBoughtMessage(OwnableField of) throws IOException, Exception {
		Player player = of.getOwner();

		GUIController.getInstance().updateBalance(player);
		GUIController.getInstance().updateLotOwner(player.getName(), of.getFieldNumber());
		GUIController.getInstance().showMessage("Du har nu købt grunden: " + of.getTitle());
	}

	public static void showMustPayRent(String name, int rent, String playerName) throws Exception {

		// show message
		String txt = String.format("Du er landet på et felt ejet af %s, og bliver nødt til at betale leje på kr. %s!",
				name, rent);
		GUIController.getInstance().showMessage(txt);

		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		// Show pay button
		GUIController.getInstance().showOptions(CHOOSE, new UserOption[] { UserOption.PayRent });
	}

	public static UserOption showMustPayIncomeTax(FieldType fieldType, String playerName) throws IOException, Exception {

		// show message
		GUIController.getInstance().showMessage("you have landed on " + fieldType);

		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		// get choice from player
		return GUIController.getInstance().showOptions(CHOOSE,
				new UserOption[] { UserOption.IncomeTaxPayTenPercent, UserOption.IncomeTaxPay4000 });
	}

	public static void showYouPaidIncomeTax(Player payer, int sumToCollect) throws Exception {

		GUIController.getInstance().showMessage(String.format("Du har nu betalt %s i indkomstskat.", sumToCollect));
		
		
		GUIController.getInstance().updateBalance(payer);
	}


	public static void showMustGoToJail(Player currentPlayer) throws Exception {
		
		Field field = currentPlayer.getCurrentField();
		
		GUIController.getInstance().showMessage("you have landed on " + field.getTitle());
		
		GUIController.getInstance().showPromt("Du flyttes i fængsel!");
		
		GUIController.getInstance().movePlayer(currentPlayer);
	}
	public static void  showPassedStart(Player currentPlayer) throws Exception
	{
		GUIController.getInstance().updateBalance(currentPlayer);		
		GUIController.getInstance().showMessage("Du har fået 4000 over start, congratulations");
	
	}
	
	public static void showMoveChanceCard(Player currentPlayer, Field newField) throws Exception{
		GUIController.getInstance().updatePlayerPosition(currentPlayer.getName(), currentPlayer.getPreviousField().getFieldNumber(), currentPlayer.getCurrentField().getFieldNumber());
		GUIController.getInstance().showMessage(currentPlayer+" er blevet flyttet til "+currentPlayer.getCurrentField());
	}
	public static void showPayChanceCard(Player currentPlayer, int moneyLost) throws Exception{
		GUIController.getInstance().updateBalance(currentPlayer);
		GUIController.getInstance().showMessage(currentPlayer+" har måttet betale "+moneyLost);
	}
	public static void showReceiveChanceCard(Player currentPlayer, int moneyReceived) throws Exception{
		GUIController.getInstance().updateBalance(currentPlayer);
		GUIController.getInstance().showMessage(currentPlayer+" har fået "+moneyReceived);
	}	
	public static void showMessage(String tekst) throws IOException{
		GUIController.getInstance().showMessage(tekst);
	}
}


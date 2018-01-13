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

	public static UserOption showWantToBuildHouseMessage(String title, String playerName) throws Exception {
		GUIController.getInstance().showMessage("you have landed on \"" + title + "\", which you already own. Do you wish to build a house here?");
		
		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		return GUIController.getInstance().showOptions(CHOOSE, new UserOption[] { UserOption.BuyHouse, UserOption.NoThanks });
	}

	public static UserOption showWantToBuildHotelMessage(String title, String playerName) throws Exception {
		GUIController.getInstance().showMessage("you have landed on \"" + title + "\", which you already own. Do you wish to build a hotel here?");
		
		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		return GUIController.getInstance().showOptions(CHOOSE, new UserOption[] { UserOption.BuyHotel, UserOption.NoThanks });
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

		//TODO: Skal laves om!!
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

	public static void showRollStreakJail(Player currentPlayer) throws Exception {
		
		GUIController.getInstance().showMessage("Du har slået to ens tre gange i træk og må derfor i fængsel.");
		
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


	public static String getSelectionResult(String[] tmp, String playerName) throws IOException {

		String CHOOSE = String.format("(%s): Du kan vælge flg.:", playerName);;
		
		return GUIController.getInstance().getSelection(CHOOSE, tmp);	
	}


	public static void showFieldPawned(String fieldName) throws IOException {
		GUIController.getInstance().showMessage(String.format("Du har nu pantsat \"%s\"", fieldName));
	}	
	public static int showAuctionMessage(Player player, Field field) throws Exception{
		//Player highestBidder = null;
		GUIController.getInstance().showMessage(player.getName()+ " kan nu byde på grunden "+field);
		//return highestBidder;
		String bf = "byd på ejendommen";
		String nt = "Nej tak";
		int bid;
		// det er muligvis et problem at der ligger saa meget logik i Messager, men det er i haab om at 
		// Bussinesslogiccontroller ikke direkte interegerer med Gui'en
		//UserOption[] options ={GUIController.getInstance().parseFromStringToUserOption(bf), GUIController.getInstance().parseFromStringToUserOption(nt)};
	//UserOption choice = presentOptions(options, player.getName());
	
	
	UserOption choice = GUIController.getInstance().showOptions("kid",new UserOption[] {UserOption.bidOnField,UserOption.NoThanks});
	
	
	if (choice==GUIController.getInstance().parseFromStringToUserOption(bf)){
		showMessage(player.getName()+ " skal indtaste sit bud");
		bid = GUIController.getInstance().getUsersInt();
		
	}
	else{showMessage("du har valgt ikke at deltage i auktionen");
	bid = 0;
	}
	return bid;
	}
	
}


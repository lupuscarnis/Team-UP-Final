package utilities;

import java.io.IOException;

import boundary.GUIController;
import entities.Player;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.OwnableField;

// TODO: GUI skal ikke kaldes via GUIController.getInstance()
// TODO: All text must be in EITHER danish or english!!
public class Messager {

	private final static String CHOOSE = "Vælg:";

	public static UserOption presentOptions(UserOption[] options) throws Exception {
		return GUIController.getInstance().showOptions(CHOOSE, options);
	}

	
	public static UserOption showWantToBuyMessage(String title) throws Exception {
		GUIController.getInstance().showMessage("you have landed on \"" + title + "\" do you wish to purchase it?");
		
		return GUIController.getInstance().showOptions(CHOOSE, new UserOption[] { UserOption.BuyField, UserOption.NoThanks });
	}

	public static void showLotBoughtMessage(OwnableField of) throws IOException, Exception {
		Player player = of.getOwner();

		GUIController.getInstance().updateBalance(player);
		GUIController.getInstance().updateLotOwner(player.getName(), of.getFieldNumber());
		GUIController.getInstance().showMessage("Du har nu købt grunden: " + of.getTitle());
	}

	public static void showMustPayRent(String name, int rent) throws Exception {

		// show message
		String txt = String.format("Du er landet på et felt ejet af %s, og bliver nødt til at betale leje på kr. %s!",
				name, rent);
		GUIController.getInstance().showMessage(txt);

		// Show pay button
		GUIController.getInstance().showOptions(CHOOSE, new UserOption[] { UserOption.PayRent });
	}

	public static UserOption showMustPayIncomeTax(FieldType fieldType) throws IOException, Exception {

		// show message
		GUIController.getInstance().showMessage("you have landed on " + fieldType);

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
}

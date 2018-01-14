package utilities;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.FieldType;
import entities.enums.UserOption;
import entities.field.Field;
import entities.field.OwnableField;

/**
 * Intent of this class was to "streamline" the way the user is informed, so
 * that it happend as uniformly as possible. However, since we can't resolve the
 * controller-tangle issue, we have dropped moving this to the controller
 * package.
 */
public class Messager {

	private static String getChooseText(String name) {
		return String.format("(%s): Du kan vælge flg.:", name);
	}

	public static UserOption presentOptions(UserOption[] options, String playerName) throws Exception {
		return GUIController.getInstance().showOptions(getChooseText(playerName), options);
	}

	public static UserOption showWantToBuyMessage(String title, String playerName) throws Exception {
		GUIController.getInstance().showMessage("Du er landet på feltet \"" + title + "\" vil du købe dette felt?");
		return GUIController.getInstance().showOptions(getChooseText(playerName),
				new UserOption[] { UserOption.BuyField, UserOption.NoThanks });
	}

	public static UserOption showWantToBuildHouseMessage(String title, String playerName) throws Exception {
		GUIController.getInstance().showMessage(
				"Du er landet på feltet \"" + title + "\", som du allerede ejer. Vil du bygge et hus her?");

		return GUIController.getInstance().showOptions(getChooseText(playerName),
				new UserOption[] { UserOption.BuyHouse, UserOption.NoThanks });
	}

	public static UserOption showWantToBuildHotelMessage(String title, String playerName) throws Exception {
		GUIController.getInstance().showMessage(
				"Du er landet på feltet \"" + title + "\", som du allerede ejer. Vil du bygge et hotel her?");

		return GUIController.getInstance().showOptions(getChooseText(playerName),
				new UserOption[] { UserOption.BuyHotel, UserOption.NoThanks });
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

		// Show pay button
		GUIController.getInstance().showOptions(getChooseText(playerName), new UserOption[] { UserOption.PayRent });
	}

	public static UserOption showMustPayIncomeTax(FieldType fieldType, String playerName)
			throws IOException, Exception {

		// show message
		GUIController.getInstance().showMessage("Du er landet på " + fieldType);

		// get choice from player
		return GUIController.getInstance().showOptions(getChooseText(playerName),
				new UserOption[] { UserOption.IncomeTaxPayTenPercent, UserOption.IncomeTaxPay4000 });
	}

	public static void showYouPaidIncomeTax(Player payer, int sumToCollect) throws Exception {

		GUIController.getInstance().showMessage(String.format("Du har nu betalt %s i indkomstskat.", sumToCollect));

		GUIController.getInstance().updateBalance(payer);
	}

	public static void showMustGoToJail(Player currentPlayer) throws Exception {

		Field field = currentPlayer.getCurrentField();

		GUIController.getInstance().showMessage("Du er landet på feltet " + field.getTitle());

		GUIController.getInstance().showPromt("Du flyttes i fængsel!");

		GUIController.getInstance().movePlayer(currentPlayer);
	}

	public static void showRollStreakJail(Player currentPlayer) throws Exception {

		GUIController.getInstance().showMessage("Du har slået to ens tre gange i træk og må derfor i fængsel.");

		GUIController.getInstance().showPromt("Du flyttes i fængsel!");

		GUIController.getInstance().movePlayer(currentPlayer);
	}

	public static void showPassedStart(Player currentPlayer) throws Exception {
		GUIController.getInstance().updateBalance(currentPlayer);
		GUIController.getInstance().showMessage("Du har fået 4000 over start, congratulations");

	}

	public static void showMoveChanceCard(Player currentPlayer, Field newField) throws Exception {
		GUIController.getInstance().updatePlayerPosition(currentPlayer.getName(),
				currentPlayer.getPreviousField().getFieldNumber(), currentPlayer.getCurrentField().getFieldNumber());
		GUIController.getInstance()
				.showMessage(currentPlayer + " er blevet flyttet til " + currentPlayer.getCurrentField());
	}

	public static void showPayChanceCard(Player currentPlayer, int moneyLost) throws Exception {
		GUIController.getInstance().updateBalance(currentPlayer);
		GUIController.getInstance().showMessage(currentPlayer + " har måttet betale " + moneyLost);
	}

	public static void showReceiveChanceCard(Player currentPlayer, int moneyReceived) throws Exception {
		GUIController.getInstance().updateBalance(currentPlayer);
		GUIController.getInstance().showMessage(currentPlayer + " har fået " + moneyReceived);
	}

	public static void showMessage(String tekst) throws IOException {
		GUIController.getInstance().showMessage(tekst);
	}

	public static String getSelectionResult(String[] tmp, String playerName) throws IOException {
		return GUIController.getInstance().getSelection(getChooseText(playerName), tmp);
	}

	public static void showFieldPawned(String fieldName) throws IOException {
		GUIController.getInstance().showMessage(String.format("Du har nu pantsat \"%s\"", fieldName));
	}

	public static void showFieldunPawned(String fieldName) throws IOException {
		GUIController.getInstance().showMessage(String.format("Du har nu upantsat \"%s\"", fieldName));
	}

	public static int showAuctionMessage(Player player, Field field) throws Exception {

		GUIController.getInstance().showMessage(player.getName() + " kan nu byde på grunden " + field);

		String bf = "Byd på ejendommen";
		int bid;

		UserOption choice = GUIController.getInstance().showOptions("kid",
				new UserOption[] { UserOption.bidOnField, UserOption.NoThanks });

		if (choice == EnumParser.fromStrToUserOption(bf)) {
			showMessage(player.getName() + " skal indtaste sit bud");
			bid = GUIController.getInstance().getUsersInt();
		} else {
			showMessage("du har valgt ikke at deltage i auktionen");
			bid = 0;
		}
		return bid;
	}
}

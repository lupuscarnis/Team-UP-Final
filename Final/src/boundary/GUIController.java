package boundary;

import controllers.PlayerController;
import entities.Player;
import gui_fields.GUI_Player;
import gui_main.GUI;

/**
 * Added by Frederik on 06-01-2018 03:02:05
 *
 */
public class GUIController {

	private GUI g = new GUI();
	private GUI_Player[] gPlayers = null;

	/**
	 * Added by Frederik on 06-01-2018 03:18:12
	 * 
	 * @return
	 */
	// TODO: Tjek for:
	// - empty string som navn
	// - samme navn
	// - samme farve på bil
	public String[] getNewPlayerNames() {
		// get number of players
		String noOfPlayers = g.getUserSelection("Vælg antal spillere", "3", "4", "5", "6");
		String[] players = new String[Integer.parseInt(noOfPlayers)];

		// get names of players
		for (int i = 0; i < players.length; i++) {
			players[i] = g.getUserString("Navn på spiller " + (i + 1) + "?");
		}
		return players;
	}

	/**
	 * Added by Frederik on 06-01-2018 03:02:01
	 * 
	 * @throws InterruptedException
	 */
	public void setup(Player[] players) {
		// init. array of GUI_Players
		gPlayers = new GUI_Player[players.length];

		// add players to GUI_Players array
		int index = 0;
		for (Player p : players) {

			gPlayers[index] = new GUI_Player(p.getName(), p.getBalance());

			index++;
		}

		// add player and car to board
		for (GUI_Player gPlayer : this.gPlayers) {

			// add the player to the board/list
			addPlayerToBoard(gPlayer);

			// add car to start field
			addCarToField(1, gPlayer);
		}
	}

	/**
	 * Added by Frederik on 06-01-2018 03:01:41
	 * 
	 * @param fromField
	 * @param toField
	 * @param player
	 */

	private void addPlayerToBoard(GUI_Player player) {
		g.addPlayer(player);
	}

	private void moveCar(int fromField, int toField, GUI_Player player) {
		removeCarFromField(fromField, player);
		addCarToField(toField, player);
	}

	/**
	 * Added by Frederik on 06-01-2018 03:01:46
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void addCarToField(int fieldNo, GUI_Player player) {
		g.getFields()[fieldNo - 1].setCar(player, true);
	}

	/**
	 * Added by Frederik on 06-01-2018 03:01:49
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void removeCarFromField(int fieldNo, GUI_Player player) {
		g.getFields()[fieldNo - 1].setCar(player, false);
	}

	public void movePlayer(Player playerToMove, int toField) throws Exception {

		int fromField = playerToMove.getPreviousField().getFieldNumber();
		GUI_Player gPlayer = findPlayer(playerToMove.getName());

		removeCarFromField(fromField, gPlayer);
		moveCar(fromField, toField, gPlayer);
	}

	private GUI_Player findPlayer(String playerNameToFind) throws Exception {

		for (GUI_Player gPlayer : gPlayers) {

			if (gPlayer.getName().equals(playerNameToFind))
				return gPlayer;
		}

		throw new Exception("No player found!");
	}
}

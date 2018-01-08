package controllers;

import java.io.IOException;


import boundary.GUIController;
import entities.Player;
import entities.enums.BreweriesOwned;
import entities.enums.LotRentTier;
import entities.enums.UserOption;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;

/**
 * Added by Frederik on 06-01-2018 20:38:39
 * 
 * Class intended to hold logic reg. business transactions etc.
 *
 */
// TODO: Implement class
public class BusinessLogicController {

	private static BusinessLogicController instance;
	private GUIController gui = GUIController.getInstance();
	private GameBoardController gbc = GameBoardController.getInstance();

	private BusinessLogicController() throws IOException {		
	}

	// Calculate rent for field
	public int calculateRent(OwnableField field) {
		// TODO: Implement method
		return 0;
	}

	// Pawn lot
	public void pawnLot(OwnableField field) {
		// TODO: Implement method
	}

	// set owner of (ownable)field.
	public void setOwner(OwnableField field, Player owner) {

		// set owner
		field.setOwner(owner);

		// update gui
		gui.removeLotOwner(field);
	}

	/**
	 * Added by Frederik on 06-01-2018 22:06:20
	 * 
	 * Set owner of Get Out Of Jail Card.
	 * 
	 * @param owner
	 * @param hasCard
	 */
	public void setGetOutOfJailCard(Player owner, boolean hasCard) {
		owner.setJailCard(hasCard);
	}

	/**
	 * Added by Frederik on 06-01-2018 23:16:41
	 * 
	 * Handles player wants to buy lot. DOES NOT CHECK FOR SUFFICIENT FUNDS!!
	 * MUST BE DONE BEFORE CALL TO METHOD!
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void buyLot(Player player) throws Exception {
		OwnableField of = (OwnableField) player.getCurrentField();		

		// withdraw money
		player.withdraw(of.getPrice());

		// set owner
		of.setOwner(player);

		// update gui
		gui.updateBalance(player);
		gui.updateLotOwner(player.getName(), of.getFieldNumber());
		gui.showMessage("Du har nu købt grunden: " + of.getTitle());
	}

	/**
	 * Added by Frederik on 06-01-2018 23:19:10
	 * 
	 * Handles the case where the user has landed of owned field and must pay rent.
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public void payRent(Player currentPlayer) throws Exception {

		OwnableField currentField = (OwnableField) currentPlayer.getCurrentField();
		Player owner = currentField.getOwner();

		switch(currentField.getFieldType())
		{
		case BREWERY:
			
			String txt= String.format("Du er landet på et felt ejet af %s, og bliver nødt til at betale leje!", owner.getName());			
			gui.showMessage(txt);
			
			//TODO: MANGLER EN TERNING OG HVOR SKAL MODIFIER KOMME FRA?
			int terning = 6; // RANDOM TAL!!!
			BreweryField bf = (BreweryField) currentField;
			int rent = bf.getModifierFor(BreweriesOwned.ONE)*terning;
			
			gui.showOptions("Vælg", new UserOption[] {UserOption.PayRent});
			
			currentPlayer.withdraw(rent);			
			break;
		
		case LOT:
			break;
		case SHIPPING:
			break;
		
		default:
			throw new Exception("Case not found!");		
		}
		
/*
		OwnableField field = (OwnableField) currentPlayer.getCurrentField();
		Player owner = field.getOwner();

		// TODO: Calc rent properly
		int rentToPay = field.getRentFor(LotRentTier.TwoHouses);

		// withdraw from current player.
		currentPlayer.withdraw(rentToPay);

		// give to owner
		owner.deposit(rentToPay);*/

		// update balances in gui
		gui.updateBalance(new Player[] { currentPlayer, owner });
	}

	/**
	 * Added by Frederik on 07-01-2018 00:05:28
	 * 
	 * Check if player still has money left, else remove player from game
	 * 
	 * @param currentPlayer
	 * @throws Exception
	 */
	public Player[] evaluatePlayer(Player currentPlayer, Player[] allPlayers) throws Exception {

		if (currentPlayer.getNetWorth() <= 0) {

			Player[] tmp = new Player[allPlayers.length - 1];

			int inner = 0;
			// traverse player array and insert into new array
			for (int i = 0; i < allPlayers.length; i++) {

				if (allPlayers[i] != currentPlayer) {
					tmp[inner] = allPlayers[i];
					inner++;
				}
			}

			// remove all owned fields
			for (OwnableField ownedField : gbc.getFieldsByOwner(currentPlayer)) {
				setOwner(ownedField, null);
			}

			// update gui and remove player
			gui.removePlayer(currentPlayer);

			return tmp;
		}

		return allPlayers;
	}

	public static BusinessLogicController getInstance() throws IOException {
		if (instance == null)
			instance = new BusinessLogicController();

		return instance;
	}

	
	/**
	 * Added by Frederik on 09-01-2018 00:17:56 
	 * 
	 * Check if user can afford lot
	 * 
	 * @param currentPlayer
	 * @return
	 */
	public boolean userCanAfford(int currentPlayerBalance, OwnableField fieldToBuy) {
		
		if(currentPlayerBalance>=fieldToBuy.getPrice())
			return true;		
		
		return false;
	}
}

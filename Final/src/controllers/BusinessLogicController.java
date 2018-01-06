package controllers;

import boundary.GUIController;
import entities.Player;
import entities.enums.LotRentTier;
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

	private GUIController gui;

	public BusinessLogicController(GUIController gui) {
		this.gui =gui;
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
		// TODO: Implement
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
	 * Handles player wants to buy lot
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void buyLot(Player player) throws Exception {		
		LotField lf = (LotField) player.getCurrentField();
		
		//TODO: check if player has money
		
		// withdraw money
		player.withdraw(lf.getPrice());
		
		// set owner
		lf.setOwner(player);	
		
		// update gui
		gui.updateLotOwner(player.getName(), lf.getFieldNumber());
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

		//TODO: Must implent switch for all ownable field types, b/c diff. ways of paying rent.
		
		LotField field = (LotField) currentPlayer.getCurrentField();
		Player owner = field.getOwner();
		
		// TODO: Calc rent properly
		int rentToPay = field.getRentFor(LotRentTier.TwoHouses);
		
		// withdraw from current player.
		currentPlayer.withdraw(rentToPay);
		
		// give to owner
		owner.deposit(rentToPay);
		
		// update balances in gui
		gui.updateBalance(new Player[] {currentPlayer, owner});
	}
}

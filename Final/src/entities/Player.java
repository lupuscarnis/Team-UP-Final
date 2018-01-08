package entities;

import entities.field.Field;

public class Player {
	private Account account = null;
	private String name = "";
	private Field currentField = null; // Current field player is on.
	private Field previousField = null; // Previous field player stood on.
	private boolean isInJail;
	private boolean hasJailCard = false;

	public Player(String name, int startAmount) {
		this(name, startAmount, null);
	}

	public Player(String name, int startAmount, Field currentField) {
		this.account = new Account(startAmount);
		this.name = name;
		this.currentField = currentField;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @param newField
	 *            Sets the players current field
	 */
	public void setCurrentField(Field newField) {

		// Indicates if user was on a field earlier or not.
		if (this.currentField != null)
			this.previousField = this.currentField;

		this.currentField = newField;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @return returns the field the player is standing on.
	 */
	public Field getCurrentField() {
		return this.currentField;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @param Navnet
	 *            p� spiller
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @return returns player names.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @return Returns The players account balance.
	 */
	public int getBalance() {

		return this.account.getBalance();
	}

	/**
	 * @author Nicolai
	 * @version 1.0
	 * @param Amount
	 * @return Deposit Money.
	 */
	public void deposit(int deposit) {
		this.account.deposit(deposit);

	}

	/**
	 * @author Nicolai
	 * @version 1.0
	 * @param Amount
	 * @return Returns if the withdraw was successful or not.
	 */
	public boolean withdraw(int amount) {
		boolean temp = account.withdraw(amount);
		return temp;
	}

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @param balance
	 *            Sets the current balance to the amount given.
	 */
	public void setBalance(int balance) {
		this.account.setBalance(balance);
	}

	public String toString() {
		return "Navn: " + "Balance: " + account.getBalance() + "Står på: " + currentField;
	}

	public Account getAccount() {
		return this.account;
	}

	public Field getPreviousField() {
		return this.previousField;
	}

	public void isInJail(boolean isInJail) {
		this.isInJail = isInJail;
	}

	public boolean isInJail() {

		return this.isInJail;
	}

	// Opgøre sin formue så vi kan læse denne: "Deres kontante penge + skøder +
	// bygninger ikke overstiger kr. 15000."
	// og måske også Win condition?
	public int getNetWorth() {
		return getBalance();
	}

	// Count no. of houses owned.
	public int getHousesOwned() {
		return 0;
	}

	// Count no. of hotels owned.
	public int getHotelsOwned() {
		return 0;
	}

	/**
	 * Added by Frederik on 06-01-2018 21:55:29
	 * 
	 * Indicates whether player has GetOutOfJailForFreeCard or not.
	 * 
	 * @param hasCard
	 */
	public void setJailCard(boolean hasCard) {
		this.hasJailCard = hasCard;
	}
	/**
	 * Added by Frederik on 06-01-2018 22:02:44 
	 * 
	 * Returns GetOutOfJailCard status
	 * 
	 * @return
	 */
	public boolean getJailCard() {
		return this.hasJailCard;
	}
}

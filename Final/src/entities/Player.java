package entities;

import entities.field.Field;

/**
 * Player class that holds information regarding the player and his account.
 *
 */
public class Player {
	private Account account = null;
	private String name = "";
	private Field currentField = null; // Current field player is on.
	private Field previousField = null; // Previous field player stood on.
	private int rollDoubleStreak = 0; // How many times in a row the player has rolled a double.
	private int turnsJailed = 0; // How many turns have the player been jailed.
	private boolean isInJail = false;
	private int lastRoll = 0;
	private boolean hasJailCard = false;
	private boolean doneThrowing = false; // Indicates that user is done throwing this turn
	private boolean hasRolled = false; // Indicates whether user has rolled or not.

	public Player(String name, int startAmount) {
		this(name, startAmount, null);
	}

	public Player(String name, int startAmount, Field currentField) {
		this.account = new Account(startAmount);
		this.name = name;
		this.currentField = currentField;
	}

	/**
	 * Sets current field (the field the player is on) for player
	 * 
	 * @param newField
	 */
	public void setCurrentField(Field newField) {

		// Indicates if user was on a field earlier or not.
		if (this.currentField != null)
			this.previousField = this.currentField;

		this.currentField = newField;
	}

	/**
	 * Returns the field that the player is standing on.
	 * 
	 * @return
	 */
	public Field getCurrentField() {
		return this.currentField;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getBalance() {
		return this.account.getBalance();
	}

	/**
	 * Deposits money in the players account
	 * 
	 * @param deposit
	 */
	public void deposit(int deposit) {
		this.account.deposit(deposit);
	}

	/**
	 * Withdraws money from player account
	 * 
	 * @param amount
	 * @return
	 */
	public boolean withdraw(int amount) {
		return this.account.withdraw(amount);
	}

	/**
	 * Sets the current balance to the amount given.
	 * 
	 * @param balance
	 */
	public void setBalance(int balance) {
		this.account.setBalance(balance);
	}

	public String toString() {
		return "Navn: " + "Balance: " + this.account.getBalance() + "Står på: " + this.currentField;
	}

	/**
	 * Returns the players account
	 * 
	 * @return
	 */
	public Account getAccount() {
		return this.account;
	}

	/**
	 * Gets the field that the player was previously standing on.
	 * 
	 * @return
	 */
	public Field getPreviousField() {
		return this.previousField;
	}

	public void setIsInJail(boolean isInJail) {
		if (!isInJail) {
			this.turnsJailed = 0;
		}
		this.isInJail = isInJail;
	}

	/**
	 * Indicates if player is in jail or not.
	 * 
	 * @return
	 */
	public boolean isInJail() {

		return this.isInJail;
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
	 * Indicates if player has "Get Out Of Jail For Free Card" or not.
	 * 
	 * @return
	 */
	public boolean getJailCard() {
		return this.hasJailCard;
	}

	/**
	 * Indicates if player can roll dice or not.
	 * 
	 * @return
	 */
	public boolean isDoneThrowing() {
		return this.doneThrowing;
	}

	public void setDoneThrowing(boolean doneThrowing) {
		this.doneThrowing = doneThrowing;
	}

	/**
	 * Indicates how many times the player has rolled the same in current turn
	 * 
	 * @return
	 */
	public int getRollDoubleStreak() {
		return this.rollDoubleStreak;
	}

	/**
	 * Sets rollDoubleStreak to parameter value
	 * 
	 * @param newStreak
	 */
	public void setRollDoubleStreak(int newStreak) {
		this.rollDoubleStreak = newStreak;
	}

	/**
	 * Shows number of turns player has been in jail
	 * 
	 * @return
	 */
	public int getTurnsJailed() {
		return this.turnsJailed;
	}

	/**
	 * Set total number of turns in jail.
	 * 
	 * @param turnsJailed
	 */
	public void setTurnsJailed(int turnsJailed) {
		this.turnsJailed = turnsJailed;
	}

	/**
	 * Set what the player rolled last.
	 * 
	 * @param value
	 */
	public void setLastRoll(int value) {
		this.lastRoll = value;
	}

	public int getLastRoll() {
		return this.lastRoll;
	}

	public int getFieldNumber() {
		return getCurrentField().getFieldNumber();
	}
}

package entities;

import entities.field.Field;

public class Player {
	private Account _account = null;
	private String _name = "";
	private Field _currentField = null; // Current field player is on.
	private Field _previousField = null; // Previous field player stood on.
	private boolean _isInJail;
	
	

	public Player(String name, int startAmount, Field currentField) {
		_account = new Account(startAmount);
		_name=name;
		this._currentField=currentField;
		
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @param newField
	 *            Sets the players current field
	 */
	public void setCurrentField(Field newField) {

		// Indicates if user was on a field earlier or not.
		if (_currentField != null)
			_previousField = _currentField;

		_currentField = newField;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @return returns the field the player is standing on.
	 */
	public Field getCurrentField() {
		return _currentField;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @param Navnet
	 *            på spiller
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * @version 1.0
	 * @author Fredrik Charles
	 * @return returns player names.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @return Returns The players account balance.
	 */
	public int getBalance() {
		
		return _account.getBalance();
	}

	/**
	 * @author Nicolai
	 * @version 1.0
	 * @param Amount
	 * @return Deposit Money.
	 */
	public void deposit(int deposit) {
		_account.deposit(deposit);

	}

	/**
	 * @author Nicolai
	 * @version 1.0
	 * @param Amount
	 * @return Returns if the withdraw was successful or not.
	 */
	public boolean withdraw(int amount) {
		boolean temp = _account.withdraw(amount);
		return temp;
	}

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @param balance
	 *            Sets the current balance to the amount given.
	 */
	public void setBalance(int balance) {
		_account.setBalance(balance);
	}

	public String toString() {
		return "Navn: " + "Balance: " + _account.getBalance() + "Står på: " + _currentField;
	}

	public Account getAccount() {
		return this._account;
	}

	public Field getPreviousField() {
		return _previousField;
	}

	public void isInJail(boolean isInJail) {
		_isInJail = isInJail;
	}

	public boolean isInJail() {

		return _isInJail;
	}
	

	
	//Opgøre sin formue så vi kan løse denne: "Deres kontante penge + skøder + bygninger ikke overstiger kr. 15000."
		// og måske også Win condition?
		public int getNetWorth() {return 0;}
		
		// Count no. of houses owned.
		public int getHousesOwned() {return 0;}
		
		// Count no. of hotels owned.
		public int getHotelsOwned() {return 0;}
}

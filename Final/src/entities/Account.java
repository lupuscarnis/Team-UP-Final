package entities;

/**
 * @version 1.0
 * @author Nicolai Barnett
 */
public class Account {
	private int _balance;

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @param balance
	 *            Makes a player Balance.
	 */

	public Account(int balance) {
		_balance = balance;
	}

	/**
	 * @author Nicolai
	 * 
	 * @version 1.0
	 * @param Deposit
	 */
	public boolean deposit(int deposit) {
		if (deposit <= 0) {
			return false;
		} else {
			_balance = _balance + deposit;
			return true;
		}
	}

	/**
	 * @author Nicolai
	 * @version 1.1
	 * @param Amount
	 * @return Returns if the withdraw was successful or not.
	 */
	public boolean withdraw(int amount) {
		if (amount >= _balance) {
			setBalance(0);
			return false;
		} else {
			_balance = _balance - amount;
			return true;
		}

	}

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @param balance
	 *            Sets the player balance
	 */
	public void setBalance(int balance) {
		_balance = balance;
	}

	/**
	 * @version 1.0
	 * @author Nicolai Barnett
	 * @return _balance Gets The current player balance if non had been entered
	 *         previously it returns 20
	 */
	public int getBalance() {
		return _balance;
	}

	public String toString() {
		return "Balance: " + _balance;
	}
}

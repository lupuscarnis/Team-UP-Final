package entities;


public class Account {
	private int _balance;


	public Account(int balance) {
		_balance = balance;
	}

/**
 * 
 * @param deposit
 * @return transactionComplete
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
 * 
 * @param amount
 * @return transactionComplete
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
	 * Sets the player balance
	 * 
	 * @version 1.0
	 * @param balance
	 *            
	 */
	public void setBalance(int balance) {
		_balance = balance;
	}

	/**
	 * @version 1.0
	 * @return _balance
	 */
	public int getBalance() {
		return _balance;
	}

	public String toString() {
		return "Balance: " + _balance;
	}
}

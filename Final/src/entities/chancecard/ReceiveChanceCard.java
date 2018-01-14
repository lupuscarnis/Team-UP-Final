package entities.chancecard;

/**
 * Chance card sub class that represents cards where player gets to receive
 * money.
 *
 */
public class ReceiveChanceCard extends ChanceCard {

	private int amount;

	public ReceiveChanceCard(int id, String text, int receivableAmount) {
		super(id, text);

		this.amount = receivableAmount;
	}

	/**
	 * Gets amount player will receive.
	 * 
	 * @return
	 */
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String toString() {
		return super.toString() + String.format("\nAmount: %s\n", getAmount());
	}
}

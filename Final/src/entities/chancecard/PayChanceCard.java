package entities.chancecard;

/**
 * Chance card sub class that represents cards where player has to pay.
 *
 */
public class PayChanceCard extends ChanceCard {

	private int amount;

	public PayChanceCard(int id, String text, int payableAmount) {
		super(id, text);

		this.amount = payableAmount;
	}

	/**
	 * Get amount player has to pay 
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

package entities.chancecard;

public class ReceiveChanceCard extends ChanceCard {

	private int amount;

	public ReceiveChanceCard(int id, String text, int receivableAmount) {
		super(id, text);
		
		this.amount = receivableAmount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String toString()
	{
		return super.toString() + String.format("\nAmount: %s\n", getAmount());	
	}
}

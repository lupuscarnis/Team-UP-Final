package entities.chancecard;

/**
 * Class representing a chance card.
 *
 */
public abstract class ChanceCard {

	private int id;
	private String text;

	public ChanceCard(int id, String text) {
		this.setId(id);
		this.setText(text);
	}

	/**
	 * Gets id of chance card.
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the text on the chance card.
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return String.format("Id: %s\n Text: %s", getId(), getText());
	}
}

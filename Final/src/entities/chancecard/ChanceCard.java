package entities.chancecard;

public class ChanceCard {

	private int id;
	private String text;

	public ChanceCard(int id, String text) {
		this.setId(id);
		this.setText(text);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

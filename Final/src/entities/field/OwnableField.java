package entities.field;

import java.io.IOException;
import entities.Player;
import entities.enums.FieldType;

/**
 * Abstract class the represents the base class for all ownable fields.
 *
 */
public abstract class OwnableField extends Field {

	private int price;
	private int pawnPrice;
	private Player owner = null;
	private boolean isPawned = false; // Is field pawned or not.

	public OwnableField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice) {
		super(fieldType, fieldNo, text1);

		this.price = price;
		this.pawnPrice = pawnPrice;
	}

	/**
	 * Method that must be implemented by all sub classes.
	 * 
	 * @param dieFaceValue
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public abstract int calculateRent(int dieFaceValue) throws IOException, Exception;

	public int getPawnPrice() {
		return this.pawnPrice;
	}

	public void setPawnPrice(int pawnPrice) {
		this.pawnPrice = pawnPrice;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * overrid af javas egen toString metode, bruges til at faa en string med denne felttypes informationer.
	 * 
	 * @return
	 */
	public String toString() {
		return super.toString() + String.format("Pawnprice: %s\n" + "Price: %s\n", getPawnPrice(), getPrice());
	}

	/**
	 * Indicates if field is pawned or not.
	 * 
	 * @return
	 */
	public boolean isPawned() {
		return this.isPawned;
	}

	public void setPawned(boolean isPawned) {
		this.isPawned = isPawned;
	}

}

package entities.field;

import entities.Player;
import entities.enums.FieldType;
/**
 * Added by Frederik on 05-01-2018 22:48:23 
 *
 */
public abstract class OwnableField extends Field {

	private int price;
	private int pawnPrice;
	private Player owner = null;
	private boolean pawned = false;

	public OwnableField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice) {
		super(fieldType, fieldNo, text1);

		this.price = price;
		this.pawnPrice = pawnPrice;
	}

	public int getPawnPrice() {
		return pawnPrice;
	}

	public void setPawnPrice(int pawnPrice) {
		this.pawnPrice = pawnPrice;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public void setPawned(Boolean pawn) {
		this.pawned = pawn;
	}
	
	public boolean getPawned() {
		return this.pawned;
	}

	public String toString() {
		return super.toString() + String.format("Pawnprice: %s\n" + "Price: %s\n", getPawnPrice(), getPrice());
	}
	
	
}

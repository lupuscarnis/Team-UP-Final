package entities.field;

import java.io.IOException;

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

	public OwnableField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice) {
		super(fieldType, fieldNo, text1);

		this.price = price;
		this.pawnPrice = pawnPrice;
	}
	
	/**
	 * Added by Frederik on 09-01-2018 09:51:33 
	 * 
	 * Method that can calc. own rent.
	 * 
	 * @return
	 * @throws IOException 
	 */
	//TODO: Think about what to b/c not all methods needs a die
	public abstract int calculateRent(int dieFaceValue) throws IOException;

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

	public String toString() {
		return super.toString() + String.format("Pawnprice: %s\n" + "Price: %s\n", getPawnPrice(), getPrice());
	}
	
	
}

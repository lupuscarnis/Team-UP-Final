package entities.field;

import java.io.IOException;

import entities.enums.FieldType;
import entities.enums.ShippingOwned;

public class ShippingField extends OwnableField {

	private int[] rentList;
	private String text2;

	public ShippingField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice, String text2,
			int[] shipRent) {
		super(fieldType, fieldNo, text1, price, pawnPrice);

		this.rentList = shipRent;
		this.text2 = text2;
	}

	public int getRentFor(ShippingOwned qtyOwned) {
		return rentList[qtyOwned.ordinal()];
	}

	public String toString() {

		return super.toString() + String.format(
				"Text2: %s\n" + "Leje for 1 rederi: %s\n" + "Leje for 2 rederi: %s\n" + "Leje for 3 rederi: %s\n"
						+ "Leje for 4 rederi: %s\n",
				getDesc(), getRentFor(ShippingOwned.One), getRentFor(ShippingOwned.Two),
				getRentFor(ShippingOwned.Three), getRentFor(ShippingOwned.Four));
	}

	public String getDesc() {
		return text2;
	}

	public void setDesc(String text2) {
		this.text2 = text2;
	}

	
	@Override
	public int calculateRent(int dieFaceValue) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}

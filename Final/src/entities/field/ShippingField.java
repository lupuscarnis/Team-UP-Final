package entities.field;

import controllers.GameBoardController;
import entities.enums.FieldType;
import entities.enums.ShippingOwned;

/**
 * Sub class of OwnableField that represents a ShippingField.
 *
 */
public class ShippingField extends OwnableField {

	private int[] rentList;
	private String text2;

	public ShippingField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice, String text2,
			int[] shipRent) {
		super(fieldType, fieldNo, text1, price, pawnPrice);

		this.rentList = shipRent;
		this.text2 = text2;
	}

	/**
	 * Gets rent modifier based on shipping fields owned.  
	 * 
	 * @param qtyOwned
	 * @return
	 */
	public int getRentFor(ShippingOwned qtyOwned) {
		return this.rentList[qtyOwned.ordinal()];
	}

	public String toString() {

		return super.toString() + String.format(
				"Text2: %s\n" + "Leje for 1 rederi: %s\n" + "Leje for 2 rederi: %s\n" + "Leje for 3 rederi: %s\n"
						+ "Leje for 4 rederi: %s\n",
				getDesc(), getRentFor(ShippingOwned.One), getRentFor(ShippingOwned.Two),
				getRentFor(ShippingOwned.Three), getRentFor(ShippingOwned.Four));
	}

	public String getDesc() {
		return this.text2;
	}

	public void setDesc(String text2) {
		this.text2 = text2;
	}

	/**
	 * Calcs rent for this field
	 */
	@Override
	public int calculateRent(int dieFaceValue) throws Exception {

		int fieldsOwned = GameBoardController.getInstance().getShippingFieldsOwned(this.getOwner());

		ShippingOwned owned = null;

		switch (fieldsOwned) {
		case 1:
			owned = ShippingOwned.One;
			break;
		case 2:
			owned = ShippingOwned.Two;
			break;
		case 3:
			owned = ShippingOwned.Three;
			break;
		case 4:
			owned = ShippingOwned.Four;
			break;
		default:
			throw new Exception("Case not found!");
		}

		return this.getRentFor(owned);
	}
}

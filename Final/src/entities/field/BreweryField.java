package entities.field;

import java.io.IOException;
import controllers.GameBoardController;
import entities.enums.BreweriesOwned;
import entities.enums.FieldType;

/**
 * Sub class of OwnableField that represents a BreweryField. Uses Diemodifiers to determine price of rent
 *
 */
public class BreweryField extends OwnableField {

	private int[] dieModifiers;

	public BreweryField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice, int[] dieModifiers) {
		super(fieldType, fieldNo, text1, price, pawnPrice);

		this.dieModifiers = dieModifiers;
	}

	/**
	 * Shows the die modifier (eg. is it 100/200 times dice value).
	 * 
	 * @param breweriesOwned
	 * @return
	 */
	public int getModifierFor(BreweriesOwned breweriesOwned) {
		return this.dieModifiers[breweriesOwned.ordinal()];
	}

	public String toString() {
		return super.toString() + String.format("Leje for 1: %s\n" + "Leje for 2: %s\n",
				getModifierFor(BreweriesOwned.ONE), getModifierFor(BreweriesOwned.TWO));
	}

	@Override
	/**
	 * Calculates rent for this field. Overrides the calculateRent method of OwnableField.
	 */
	public int calculateRent(int dieFaceValue) throws IOException {

		int fieldsOwned = GameBoardController.getInstance().countBreweriesOwned(this.getOwner());
		int modifier = this.getModifierFor((fieldsOwned == 1 ? BreweriesOwned.ONE : BreweriesOwned.TWO));

		return modifier * dieFaceValue;
	}
}

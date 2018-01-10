package entities.field;

import java.io.IOException;
import controllers.GameBoardController;
import entities.enums.BreweriesOwned;
import entities.enums.FieldType;

public class BreweryField extends OwnableField {

	private int[] dieModifiers;

	public BreweryField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice, int[] dieModifiers) {
		super(fieldType, fieldNo, text1, price, pawnPrice);

		this.dieModifiers = dieModifiers;
	}

	public int getModifierFor(BreweriesOwned breweriesOwned) {
		return this.dieModifiers[breweriesOwned.ordinal()];
	}

	public String toString() {
		return super.toString() + String.format("Leje for 1: %s\n" + "Leje for 2: %s\n",
				getModifierFor(BreweriesOwned.ONE), getModifierFor(BreweriesOwned.TWO));
	}

	@Override
	public int calculateRent(int dieFaceValue) throws IOException {

		int fieldsOwned = GameBoardController.getInstance().countBreweriesOwned(this.getOwner());
		int modifier = this.getModifierFor((fieldsOwned == 1 ? BreweriesOwned.ONE : BreweriesOwned.TWO));
		
		return modifier*dieFaceValue;
	}
}

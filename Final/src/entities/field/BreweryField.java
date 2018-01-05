package entities.field;

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
	
	public String toString()
	{
		return super.toString() + String.format("Leje for 1: %s\n"
				+ "Leje for 2: %s\n", 
				getModifierFor(BreweriesOwned.ONE),
				getModifierFor(BreweriesOwned.TWO));
	}
}

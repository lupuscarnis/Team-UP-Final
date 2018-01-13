package controllers;

import java.io.IOException;
import entities.Player;
import entities.enums.FieldName;
import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import entities.field.ShippingField;
import utilities.FieldLoader;

public class GameBoardController {

	public static final int FIELD_COUNT = 40;

	private static GameBoardController instance;

	private Field[] fieldArray = null;

	private GameBoardController() throws IOException {
		this.fieldArray = new FieldLoader().getFields();
	}

	public static GameBoardController getInstance() throws IOException {
		if (instance == null)
			instance = new GameBoardController();

		return instance;
	}

	// Find n�rmeste rederi (fra nuv�r. pos)
	public ShippingField getNearestShipping(int currentPosition) throws Exception {

		int start = currentPosition;
		start = start >= 36 ? 0 : start;
		
		Field[] fields = this.getFields();

		for (int i = start; start < fields.length; i++) {
			if (fields[i].getFieldType() == FieldType.SHIPPING)
				return (ShippingField) fields[i];
		}
		
		throw new Exception("Shipping field not found!");
	};

	// returns all shipping fields
	private ShippingField[] getAllShippingFields() {

		ShippingField[] tmp = new ShippingField[4]; // Only 4 shipping fields
		int index = 0;
		for (OwnableField field : tmp) {

			if (field.getFieldType() == FieldType.SHIPPING) {
				tmp[index] = (ShippingField) field;
				index++;
			}
		}

		return tmp;
	}

	// TODO: Skal hedder "getFieldByTitle"
	public Field getFieldByName(FieldName fieldToFind) throws Exception {

		switch (fieldToFind) {
		case Start:
			return getFieldByNumber(1);
		case Fængslet:
			return getFieldByNumber(11);
		case FrederiksbergAlle:
			return getFieldByNumber(12);
		case Grønningen:
			return getFieldByNumber(25);
		case Rådhuspladsen:
			return getFieldByNumber(40);
		default:
			throw new Exception("Feltet blev ikke fundet!");
		}
	}

	// Finde "Ryk tre felter tilbage" (fra nuv�r. pos)
	public Field getFieldRelativeToPos(int currentPos, int numberOffields) {
		// TODO Auto-generated method stub
		return null;
	}

	public Field getFieldByNumber(int fieldNo) {
		return fieldArray[fieldNo - 1];
	}

	public Field[] getFields() {
		return fieldArray;
	}

	/**
	 * Added by Frederik on 07-01-2018 00:48:33
	 * 
	 * Gets all fields by current owner
	 * 
	 * NB: It's not optimized, but it can wait for ArrayList :)
	 * 
	 * @param currentPlayer
	 * @return
	 */
	public OwnableField[] getFieldsByOwner(Player currentOwner) {

		int count = 0;
		for (Field field : fieldArray) {

			if (field instanceof OwnableField) {
				OwnableField of = (OwnableField) field;

				if (of.getOwner() == currentOwner)
					count++;
			}
		}

		if (count > 0) {
			OwnableField[] tmp = new OwnableField[count];

			int index = 0;
			for (Field field : fieldArray) {

				if (field instanceof OwnableField) {
					OwnableField of = (OwnableField) field;

					if (of.getOwner() == currentOwner) {
						tmp[index] = of;
						index++;
					}
				}
			}
			return tmp;
		}

		return new OwnableField[0];
	}

	public LotField[] getLotFieldsByOwner(Player currentOwner) {

		int count = 0;
		for (Field field : fieldArray) {

			if (field instanceof LotField) {
				LotField of = (LotField) field;

				if (of.getOwner() == currentOwner)
					count++;
			}
		}

		if (count > 0) {
			LotField[] tmp = new LotField[count];

			int index = 0;
			for (Field field : fieldArray) {

				if (field instanceof LotField) {
					LotField of = (LotField) field;

					if (of.getOwner() == currentOwner) {
						tmp[index] = of;
						index++;
					}
				}
			}
			return tmp;
		}

		return new LotField[0];
	}

	/**
	 * Added by Frederik on 07-01-2018 01:11:42
	 * 
	 * I know it's not optimized, but since we can't use ArrayList...
	 * 
	 * @return All ownable fields
	 */
	public OwnableField[] getAllOwnableFields() {
		int count = 0;
		for (Field field : fieldArray) {

			if (field instanceof OwnableField) {
				OwnableField of = (OwnableField) field;

				count++;
			}
		}

		OwnableField[] tmp = new OwnableField[count];
		int index = 0;
		for (Field field : fieldArray) {

			if (field instanceof OwnableField) {
				OwnableField of = (OwnableField) field;

				tmp[index] = of;
				index++;
			}
		}
		return tmp;
	}

	// Returns all lot fields
	public LotField[] getAllLotFields() {

		LotField[] tmp = new LotField[22];

		int index = 0;
		for (Field field : this.getAllOwnableFields()) {

			if (field instanceof LotField) {
				tmp[index] = (LotField) field;
				index++;
			}
		}

		return tmp;
	}

	// Count breweries owned by player
	public int countBreweriesOwned(Player owner) {

		int count = 0;
		for (OwnableField field : this.getAllOwnableFields()) {

			if (FieldType.BREWERY == field.getFieldType()) {

				// check if it's owned
				if (field.getOwner() != null && field.getOwner() == owner)
					count++;
			}
		}

		return count;
	}

	// Count shipping owned by player
	public int countShippingOwned(Player owner) {

		int count = 0;
		for (OwnableField field : this.getAllOwnableFields()) {

			if (FieldType.SHIPPING == field.getFieldType()) {

				// check if it's owned
				if (field.getOwner() != null && field.getOwner() == owner)
					count++;
			}
		}

		return count;
	}

	// count lot fields owned in same color
	public int countLotsOwnedByColor(LotColor lotColor, Player owner) {

		int count = 0;
		for (LotField field : this.getAllLotFields()) {

			// check that lot field is corret color and has a owner
			if (field.getColor() == lotColor && field.getOwner() != null) {

				// check that owner == player
				if (field.getOwner() == owner)
					count++;
			}
		}

		return count;
	}

	/**
	 * Added by Frederik on 09-01-2018 14:48:35
	 * 
	 * Counts total lots in color group: eg. all lots with the (LotColor)color blue.
	 * 
	 * @param blue
	 * @return
	 */
	public int countLotsInColorGroup(LotColor lotColor) {

		int count = 0;
		for (LotField field : this.getAllLotFields()) {

			// check that lot field is correct color
			if (field.getColor() == lotColor)
				count++;
		}
		return count;
	}

	// Return number of shipping fields owned by player
	public int getShippingFieldsOwned(Player owner) {

		int count = 0;
		for (Field field : this.getFieldsByOwner(owner)) {

			if (field.getFieldType() == FieldType.SHIPPING)
				count++;
		}
		return count;
	}
}

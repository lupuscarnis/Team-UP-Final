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

/**
 * Controller in charge of managing the gameboard, and passing on information about it to other classes
 * 
 */
public class GameBoardController {

	// fields
	private static GameBoardController instance = null;
	public static final int FIELD_COUNT = 40;
	private Field[] fieldArray = null;
	/**
	 * a contructor that uses the fieldLoader to create an array
	 * @throws IOException
	 * 
	 */
	private GameBoardController() throws IOException {
		this.fieldArray = new FieldLoader().getFields();
	}
	/**
	 * Helper method: Looks up a "GUI"-player from a "Logic"-player name.
	 * @return instance
	 * @throws Exception
	 */
	public static GameBoardController getInstance() throws IOException {
		if (instance == null)
			instance = new GameBoardController();

		return instance;
	}

	/**
	 * The methods purpose is to find the nearest shipping type field, for a certain player. Used for a chanceCard
	 * 
	 * @param currentPosition
	 * @throws Exception
	 */
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

	/**
	 * finds every field with the shipping type, from an array, usually all the fields in the game.
	 * 

	 * @return ShippingField[]
	 */
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

	/**
	 * finds a field, based on the name if the field, using field enums
	 * 
	 * @param fieldToFind
	 * @throws Exception
	 */
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

	/**
	 * finds a field, based on it's number
	 * 
	 * @param fieldNo
	 */
	public Field getFieldByNumber(int fieldNo) {
		return fieldArray[fieldNo - 1];
	}

	/**
	 * returns a fieldArray, that is created in this class
	 * @return fieldArray
	 */
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

	/**
	 * getLotFieldsByOwner is used to get an array with all the fields owned by a player, has multiple applications
	 * 
	 * @param currentOwner
	 * 
	 */
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
	 * is used to get an array with all OwnableFields on the gameBoard
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

	/**
	 * a specialized getOwnableFields that only returns Lots, not breweries and shipping types
	 * 
	 * @return all LotFields
	 */
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
	/**
	 * gets the number of breweries owned by a player, used for calculating rent on this type of field
	 * 
	 * @param Owner
	 * @return int
	 */
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

	/**
	 * gets the number of shipping type fields owned by a player, used for calculating rent on this type of field
	 * 
	 * @param owner
	 * @return int
	 */
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

	/**
	 * a method used to find out if you can build on a field. It shows how many fields of the same color are owned by a player
	 * 
	 * @param owner
	 * @param lotColor
	 * @return int
	 */
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
	 * @return int
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

	/**
	 * helping method, used to get the number of shipping fields owned by a player, used for calculating rent
	 * 
	 * @param owner
	 * @return int
	 */
	public int getShippingFieldsOwned(Player owner) {

		int count = 0;
		for (Field field : this.getFieldsByOwner(owner)) {

			if (field.getFieldType() == FieldType.SHIPPING)
				count++;
		}
		return count;
	}
}

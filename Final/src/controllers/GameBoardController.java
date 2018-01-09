package controllers;

import java.io.IOException;

import entities.Player;
import entities.enums.FieldName;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import utilities.FieldLoader;

public class GameBoardController {

	public static final int FIELD_COUNT = 40;

	private static GameBoardController instance;

	private Field[] fieldArray = null;

	private GameBoardController() throws IOException {
		this.fieldArray = new FieldLoader().getFields();
	}

	// Finde n�rmeste f�rge (fra nuv�r. pos)
	// Find n�rmeste rederi (fra nuv�r. pos)
	public Field getNearestShipping(int currentPosition) {
		// TODO Auto-generated method stub
		return null;
	};

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

	public static GameBoardController getInstance() throws IOException {
		if (instance == null)
			instance = new GameBoardController();

		return instance;
	}
}

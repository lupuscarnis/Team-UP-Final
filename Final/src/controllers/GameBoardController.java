package controllers;

import entities.enums.FieldName;
import entities.field.Field;

public class GameBoardController {

	public static final int FIELD_COUNT = 40;
	
	private Field[] fieldArray = null;

	public GameBoardController(Field[] fields) {
		this.fieldArray = fields;
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
}

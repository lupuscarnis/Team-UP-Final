package entities.field;

import entities.enums.FieldType;

public class Field {

	private int fieldNumber;
	private String text1;
	private FieldType fieldType;

	public Field(int fieldNo, String text1, FieldType fieldType) {
		this.fieldNumber = fieldNo;
		this.text1 = text1;
		this.fieldType = fieldType;
	}

	public FieldType getFieldType() {
		return this.fieldType;
	}

	public int getFieldNumber() {
		return this.fieldNumber;
	}

	public String getText1() {
		return text1;
	}

	public String toString() {
		return String.format("Feltnr: %s\n" + "Text1: %s\\n" + "" + "", getFieldNumber(), getText1());
	}

}

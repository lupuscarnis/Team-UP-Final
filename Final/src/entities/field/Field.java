package entities.field;

import entities.enums.FieldType;

public class Field {

	private int fieldNumber;
	private FieldType fieldType;
	private String text1;
	private String text2;

	public Field(FieldType fieldType, int fieldNo, String text1) {
		this(fieldType, fieldNo, text1, "");
	}

	public Field(FieldType fieldType, int fieldNo, String text1, String text2) {
		this.fieldNumber = fieldNo;
		this.text1 = text1;
		this.setText2(text2);
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
		return String.format("Type: %s\n" + "Feltnr: %s\n" + "Text1: %s\n" + "Text2: %s\n", getFieldType(),
				getFieldNumber(), getText1(), getText2());
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}
}

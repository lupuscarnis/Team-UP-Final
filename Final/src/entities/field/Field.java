package entities.field;

import entities.enums.FieldType;

/**
 * Represents all other types of fields that are not represented by a sub class. Main purpose is to be extended from, by other field types.
 *
 */
public class Field {

	private int fieldNumber;
	private FieldType fieldType;
	private String title;
	private String desc;

	public Field(FieldType fieldType, int fieldNo, String title) {
		this(fieldType, fieldNo, title, "");
	}

	public Field(FieldType fieldType, int fieldNo, String title, String desc) {
		this.fieldNumber = fieldNo;
		this.title = title;
		this.desc = desc;
		this.fieldType = fieldType;
	}

	/**
	 * Gets the type of field.
	 * 
	 * @return
	 */
	public FieldType getFieldType() {
		return this.fieldType;
	}

	public int getFieldNumber() {
		return this.fieldNumber;
	}

	public String getTitle() {
		return title;
	}
	/**
	 * returns a long string with all the information of the field. Overrider javas toString metode.
	 * 
	 * @return String
	 */
	public String toString() {
		return String.format("Type: %s\n" + "Feltnr: %s\n" + "Text1: %s\n" + "Text2: %s\n", getFieldType(),
				getFieldNumber(), getTitle(), getDesc());
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.ShippingField;

/**
 * Added by Frederik on 24-11-2017 20:06:51
 *
 */
public class FieldLoader {

	private InputStream getInputStream(String filename) {
		return getClass().getResourceAsStream(filename);
	}

	public Field[] getFields() throws IOException {
		Field[] fields = new Field[40];
		InputStream in = getInputStream("/fieldsdata.txt");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

			String currentLine = br.readLine();
			while (currentLine != null) {

				// Check that line is not placeholder: eg: [SHIPPING] or
				// [Type/Number/Text1/....]
				if (!currentLine.substring(0, 1).equals("[")) {

					// get line as string array
					String[] arr = currentLine.split(";");

					// get type, number and text1
					FieldType fieldType = FieldType.valueOf(arr[0]);
					int fieldNo = Integer.parseInt(arr[1]);
					String text1 = arr[2];

					// create new Field
					Field field = null;

					// set attribs. according to FieldType
					switch (fieldType) {
					
					case START:
					case INCOMETAX:
					case EXTRATAX:
					case GOTOJAIL:
					case VISITJAIL:
					case FREEPARKING:
						field = new Field(fieldType, fieldNo, text1, arr[3]);
						break;
						
					case CHANCE:					
						field = new Field(fieldType, fieldNo, text1);
						break;

					case BREWERY:
						field = new BreweryField(fieldType, fieldNo, text1, Integer.parseInt(arr[3]),
								Integer.parseInt(arr[4]),
								new int[] { Integer.parseInt(arr[5]), Integer.parseInt(arr[6]) });
						break;
					case LOT:
						int price = Integer.parseInt(arr[3]);
						int pawnPrice = Integer.parseInt(arr[4]);
						LotColor lotColor = LotColor.valueOf(arr[5]);
						int[] rent = getRent(arr[8], arr[9], arr[10], arr[11], arr[12], arr[13]);
						int housePrice = Integer.parseInt(arr[7]);
						int hotelPrice = Integer.parseInt(arr[8]);

						LotField tmp = new LotField(fieldType, fieldNo, text1, price, pawnPrice);
						tmp.setColor(lotColor);
						tmp.setHousePrice(housePrice);
						tmp.setHotelPrice(hotelPrice);
						tmp.setRent(rent);
						field = tmp;

						break;
					case SHIPPING:
						int[] shipRent = getShippingRent(new String[] { arr[6], arr[7], arr[8], arr[9] });
						int shipPrice = Integer.parseInt(arr[3]);
						int shipPawnPrice = Integer.parseInt(arr[4]);
						String text2 = arr[5];

						field = new ShippingField(fieldType, fieldNo, text1, shipPrice, shipPawnPrice, text2, shipRent);
						break;
					}
					fields[fieldNo - 1] = field;
				}
				currentLine = br.readLine();
			}
		}

		return fields;
	}

	private int[] getShippingRent(String[] rents) {

		int[] tmp = new int[4];

		int index = 0;
		for (String item : rents) {
			tmp[index] = Integer.parseInt(item);
			index++;
		}

		return tmp;
	}

	private int[] getRent(String lotRent, String oneHouseRent, String twoHouseRent, String threeHouseRent,
			String fourHouseRent, String hotelRent) {

		int[] tmp = new int[6];

		int index = 0;
		for (String item : new String[] { lotRent, oneHouseRent, twoHouseRent, threeHouseRent, fourHouseRent,
				hotelRent }) {

			tmp[index] = Integer.parseInt(item);

			index++;
		}

		return tmp;
	}
}

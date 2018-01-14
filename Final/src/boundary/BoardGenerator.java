package boundary;

import java.awt.Color;
import java.io.IOException;
import controllers.GameBoardController;
import entities.enums.BreweriesOwned;
import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.enums.LotRentTier;
import entities.enums.ShippingOwned;
import entities.field.BreweryField;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import entities.field.ShippingField;
import gui_fields.GUI_Brewery;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Refuge;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Tax;
import gui_main.GUI;
import utilities.FieldLoader;

/**
 * This class was intended as a "Controller" for board generation of the board and fields.
 * As it would fill too much in the GUICtrl, it became a seperate class.
 */
public class BoardGenerator {
/**
 * Creates the full GameBoard 
 * @return
 * @throws IOException
 */
	public GUI makeBoard() throws IOException {

		FieldLoader fl = new FieldLoader();
		GUI_Field[] fields = new GUI_Field[40];

		for (int i = 0; i < fields.length; i++) {

			Field tmp = GameBoardController.getInstance().getFieldByNumber(i + 1);

			String title = formatText(tmp.getTitle(), 8);
			String sub = "Sub";// tmp.getText2();
			String desc = tmp.getDesc();
			Color fgColor = Color.BLACK;
			Color bgColor = null;

			switch (tmp.getFieldType()) {
			/*
			 * case BREWERY: break; case CHANCE: break; case EXTRATAX: break; case
			 * FREEPARKING: break; case GOTOJAIL: break; case INCOMETAX: break;
			 */

			case START:
				
				desc = tmp.getDesc(); 
				sub = "Modtag 4000";
				bgColor = Color.red;

				fields[i] = new GUI_Start(title, sub, desc, bgColor, fgColor);
				break;

			case BREWERY:
				GUI_Brewery breweryField = new GUI_Brewery();
				breweryField.setTitle(title);
				breweryField.setBackGroundColor(Color.black);
				breweryField.setForeGroundColor(Color.WHITE);
				breweryField.setSubText("Pris: " + ((OwnableField) tmp).getPrice());
				breweryField.setDescription(getBreweryDesc((BreweryField) tmp));

				fields[i] = breweryField;
				break;

			case FREEPARKING:
				GUI_Refuge refugeField = new GUI_Refuge();
				refugeField.setTitle(title);
				refugeField.setSubText("Helle");
				refugeField.setBackGroundColor(Color.white);
				refugeField.setDescription(desc);

				fields[i] = refugeField;
				break;

			case VISITJAIL:
			case GOTOJAIL:

				GUI_Jail jailField = new GUI_Jail();
				jailField.setTitle(title);
				jailField.setSubText(title);
				jailField.setDescription(desc);

				fields[i] = jailField;

				break;

			case CHANCE:
				GUI_Chance chanceField = new GUI_Chance();
				chanceField.setDescription(title);
				fields[i] = chanceField;
				break;

			case INCOMETAX:
			case EXTRATAX:
				// check whether it's a income or extra tax field,
				// and insert subtext accordingly.
				sub = tmp.getFieldType() == FieldType.INCOMETAX ? "10% el. 4000" : "Betal 2000";

				GUI_Tax taxField = new GUI_Tax();
				taxField.setTitle(title);
				taxField.setDescription(desc);
				taxField.setBackGroundColor(Color.CYAN);
				taxField.setSubText(sub);

				fields[i] = taxField;
				break;

			case LOT:
				desc = getLotDesc(tmp); // TODO: How do you center the text?
				sub = "Pris: " + ((OwnableField) tmp).getPrice();
				fgColor = getFgColor(((LotField) tmp).getColor()); // TODO: Rename method name: getLotFgColor
				bgColor = getBgColor(((LotField) tmp).getColor()); // TODO: Rename method name: getLotBgColor

				fields[i] = new GUI_Street(title, sub, desc, "0", bgColor, fgColor);
				break;
			case SHIPPING:
				GUI_Shipping shipField = new GUI_Shipping();
				shipField.setTitle(title);
				shipField.setSubText("Pris: " + ((OwnableField) tmp).getPrice());
				shipField.setDescription(getShippingDesc((ShippingField) tmp));

				fields[i] = shipField;
				break;
			default:

				fields[i] = new GUI_Start(title, sub, desc, Color.WHITE, Color.BLACK);
				break;
			}

		}

		return new GUI(fields);

	}
/**
 *  Gets The background Color of the current lot 
 * @param lotColor
 * @return Color
 */
	public Color getBgColor(LotColor lotColor) {

		switch (lotColor) {
		case BLUE:
			return Color.decode("#4B9BE0");

		case GRAY:
			return Color.decode("#999999");

		case GREEN:
			return Color.decode("#66CC00");

		case PINK:
			return Color.decode("#FF8777");

		case PURPLE:
			return Color.decode("#963C96");

		case RED:
			return Color.decode("#FE0000");

		case WHITE:
			return Color.decode("#FFFFFF");

		case YELLOW:
			return Color.decode("#FFFF33");

		default:
			return null;
		}
	}
/**
 * This static method gets the brewery description
 * 
 * @param field
 * @return String
 */
	private static String getBreweryDesc(BreweryField field) {

		return String.format(
				"<div>%s</div>" + "<br>" + "<div>Leje:</div>" + "<br>" + "<div>1 bryggeri: %s x terninger</div>"
						+ "<br>" + "<div>2 bryggerier: %s x terninger</div>" + "<br>",
				field.getTitle(), field.getModifierFor(BreweriesOwned.ONE), field.getModifierFor(BreweriesOwned.TWO));
	}
/**
 * This static method gets The shipping desc
 * @param field
 * @return
 */
	
	private static String getShippingDesc(ShippingField field) {

		String desc = field.getTitle();

		// if desc then insert in title like: DSB (Halskov/Knudshoved)
		if (field.getDesc().length() > 0)
			desc = field.getTitle() + " (" + field.getDesc() + ")";

		return String.format(
				"<div>%s</div>" + "<br />" + "<div>Leje: </div> " + "Hvis 1 rederi ejes: %s" + "<br />"
						+ "Hvis 2 rederier ejes: %s" + "<br />" + "Hvis 3 rederier ejes: %s" + "<br />"
						+ "Hvis 4 rederier ejes: %s" + "<br />",
				desc, field.getRentFor(ShippingOwned.One), field.getRentFor(ShippingOwned.Two),
				field.getRentFor(ShippingOwned.Three), field.getRentFor(ShippingOwned.Four));
	}
/**
 * This static method gets the lot descriptions
 * @param field
 * @return
 */
	private static String getLotDesc(Field field) {

		LotField lf = (LotField) field;

		return String.format("<html><div>%s</div>" + "<div>Pris pr. bygning: %s</div>"

				+ "<div>Leje:</div>" + "<div>Grund: %s</div>" + "<div>m. 1 hus: %s</div>" + "<div>m. 2 hus: %s</div>"
				+ "<div>m. 3 hus: %s</div>" + "<div>m. 4 hus: %s</div>" + "<div>m. hotel: %s</div>" + "</html>",
				lf.getTitle(), lf.getBuildingCost(), lf.getRentFor(LotRentTier.Lot),
				lf.getRentFor(LotRentTier.OneHouse), lf.getRentFor(LotRentTier.TwoHouses),
				lf.getRentFor(LotRentTier.ThreeHouses), lf.getRentFor(LotRentTier.FourHouses),
				lf.getRentFor(LotRentTier.Hotel));
	}
/**
 * static method that is used to get the color of the foreground 
 * @param color
 * @return
 */
	private static Color getFgColor(LotColor color) {
		if (color == LotColor.PURPLE)
			return Color.white;

		return Color.BLACK;
	}
/**
 * Format the text. Is used to get the text in the right format for the reader.
 * @param text
 * @param i
 * @return
 */
	private static String formatText(String text, int i) {

		return String.format("<html><span style='font-size: %spx;'>%s</span></html>", i, text);
	}
}

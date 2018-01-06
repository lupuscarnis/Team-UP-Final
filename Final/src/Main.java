import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import boundary.GUIController;
import controllers.ChanceCardController;
import controllers.GameBoardController;
import controllers.GameController;
import entities.chancecard.ChanceCard;
import entities.enums.FieldName;
import entities.enums.LotColor;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_main.GUI;
import utilities.ChanceLoader;
import utilities.FieldLoader;

public class Main {

	public static Color getBgColor(LotColor lotColor) {

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

	public static void main(String[] args) throws Exception {

		FieldLoader fl = new FieldLoader();
		GameBoardController gbc = new GameBoardController(fl.getFields());

		GUI_Field[] fields = new GUI_Field[40];

		for (int i = 0; i < fields.length; i++) {

			Field tmp = gbc.getFieldByNumber(i + 1);

			String title = title = formatText(tmp.getTitle(), 8);
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
				//TODO: How do you make title larger?
				desc = tmp.getDesc(); // TODO: How do you center the text?
				sub = "Modtag 4000";
				bgColor = Color.red;

				fields[i] = new GUI_Start(title, sub, desc, bgColor, fgColor);
				break;
			case LOT:
				desc = formatText(tmp.getTitle(), 10); // TODO: How do you center the text?
				sub = "Pris: " + ((OwnableField) tmp).getPrice();
				fgColor = getFgColor(((LotField) tmp).getColor());
				bgColor = getBgColor(((LotField) tmp).getColor());

				fields[i] = new GUI_Street(title, sub, desc, "0", bgColor, fgColor);
				break;
			/*
			 * case SHIPPING: break; case START: break; case VISITJAIL: break;
			 */
			default:

				fields[i] = new GUI_Start(title, sub, desc, Color.WHITE, Color.BLACK);
				break;
			}

		}

		GUI g = new GUI(fields);

		g.addPlayer(new GUI_Player("Hansi"));

		/*
		 * GameController gc = new GameController();
		 * 
		 * gc.play();
		 */
	}

	private static Color getFgColor(LotColor color) {
		if (color == LotColor.PURPLE)
			return Color.white;

		return Color.BLACK;
	}

	private static String formatText(String text, int i) {

		return String.format("<html><span style='font-size: %spx;'>%s</span></html>", i, text);
	}
}

import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import boundary.GUIController;
import controllers.ChanceCardController;
import controllers.GameBoardController;
import controllers.GameController;
import entities.chancecard.ChanceCard;
import entities.enums.FieldName;
import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.field.Field;
import entities.field.LotField;
import entities.field.OwnableField;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Tax;
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
				// TODO: How do you make title larger?
				desc = tmp.getDesc(); // TODO: How do you center the text?
				sub = "Modtag 4000";
				bgColor = Color.red;

				fields[i] = new GUI_Start(title, sub, desc, bgColor, fgColor);
				break;

			case CHANCE:
				GUI_Chance chanceField = new GUI_Chance();
				chanceField.setDescription(title);
				fields[i] = chanceField;
				break;
				
			case INCOMETAX:
			case EXTRATAX:				
				// check wether it's a income or extra tax field,
				// and insert subtext accordingly.
				sub = tmp.getFieldType()==FieldType.INCOMETAX?"10% el. 4000":"Betal 2000";
				
				GUI_Tax taxField = new GUI_Tax();
				taxField.setTitle(title);
				taxField.setDescription(desc);
				taxField.setBackGroundColor(Color.CYAN);
				taxField.setSubText(sub);
				
				fields[i] = taxField;
				break;

			case LOT://TODO: Change it!
				desc = formatText(tmp.getTitle(), 10); // TODO: How do you center the text?
				sub = "Pris: " + ((OwnableField) tmp).getPrice();
				fgColor = getFgColor(((LotField) tmp).getColor()); // TODO: Rename method name: getLotFgColor
				bgColor = getBgColor(((LotField) tmp).getColor()); // TODO: Rename method name: getLotBgColor

				fields[i] = new GUI_Street(title, sub, desc, "0", bgColor, fgColor);
				break;
			/*
			 * case SHIPPING: break; case START: break; case VISITJAIL: break;
			 */
				
			case SHIPPING:				
				GUI_Shipping shipField = new GUI_Shipping();
				shipField.setTitle(title);
				shipField.setSubText("Pris: " + ((OwnableField)tmp).getPrice());
				shipField.setDescription(desc.length()==0?tmp.getTitle():desc); // If no desc. insert title
				
				fields[i]=shipField;
				break;
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

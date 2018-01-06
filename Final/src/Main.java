import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import boundary.GUIController;
import controllers.ChanceCardController;
import controllers.GameBoardController;
import controllers.GameController;
import entities.chancecard.ChanceCard;
import entities.enums.BreweriesOwned;
import entities.enums.FieldName;
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
import gui_fields.GUI_Player;
import gui_fields.GUI_Refuge;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Tax;
import gui_main.GUI;
import utilities.ChanceLoader;
import utilities.FieldLoader;

public class Main {	

	public static void main(String[] args) throws Exception {		
		GameController gc = new GameController();
		gc.play();		
	}
}

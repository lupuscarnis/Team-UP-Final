package controllers;

import java.io.IOException;
import boundary.GUIController;

/**
 * Class is base controller for all classes in "controllers" package EXCEPT GameBoardController as it's
 * strictly used as lookup and therefor not using the other controllers.
 */
public abstract class BaseController {

	protected GUIController gui = null;
	protected GameBoardController gbc = null;
	
	public BaseController() throws IOException {
		this.gui = GUIController.getInstance(); // singleton
		this.gbc = GameBoardController.getInstance(); // singleton		
	}
}

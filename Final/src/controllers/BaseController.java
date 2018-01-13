package controllers;

import java.io.IOException;
import boundary.GUIController;

/** 
 * Class is base controller for all classes in "controllers" package
 */
public abstract class BaseController {

	protected GUIController gui = null;
	
	public BaseController() throws IOException {
		gui = GUIController.getInstance();
	}
}

package controllers;

import boundary.GUIController;
import entities.Player;

public class GameLogicCtrl {

	private GUIController gui;

	public GameLogicCtrl(GUIController gui) {
		this.gui=gui;
	}

	
	public void showUserOptions(Player currentPlayer) {
		
		// if in jail bla bla bla
		gui.showOptions("Du har flg. valg:", new String[] {"Kast terning"});
		
		
	}

}

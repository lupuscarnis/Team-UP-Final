package controllers;

import java.io.IOException;

import entities.Player;
import entities.enums.FieldName;
import entities.field.Field;
import entities.field.OwnableField;
import utilities.FieldLoader;

public class PlayerController {
	
	

	public  Player[] createNewPlayers(String[] playerNames) throws Exception {

		// TODO: Move to global scope. 
		// instead have a field number to place they are standing in the field array and
		// let the other controller handle
		// what happends there.
		GameBoardController gbc = new GameBoardController(new FieldLoader().getFields());

		Player[] tmp = new Player[playerNames.length];

		int index = 0;
		for (String name : playerNames) {

			Field start = gbc.getFieldByName(FieldName.Start);

			tmp[index] = new Player(name, 30000, start); // HACK: 30000: Where should the money come from?

			index++;
		}

		return tmp;
	}

	public void deposit(int value, Player player) {
		if (value <= 0) {
			value = value * (-1);
			player.deposit(value);
		} else {
			player.deposit(value);
		}

	}

	public void withdraw(int value, Player player) {
		if (value >= 0) {
			value = value * (-1);
			player.withdraw(value);
		} else {
			player.withdraw(value);
		}
	}
	
	
	 
	 // moves the player to the new location if it hits start it will go back around
	/*
	public void movePlayer(Player player, int diceSum) throws IOException
	{
		GameBoardController gbc = new GameBoardController(new FieldLoader().getFields());
		if(player.getCurrentField().getFieldNumber() + diceSum >= 39 )
		{
			
			Field fb = new OwnableField();
			int remainder  = (player.getCurrentField().getFieldNumber()%39
			Field temp = gbc.getFieldByNumber(remainder);
			player.setCurrentField(temp);
		}
		else
		{
			
			
			int  newFieldValue = player.getCurrentField().getFieldNumber() + diceSum;
			Field temp = gbc.getFieldByNumber(newFieldValue);
			player.setCurrentField(temp);
			
			
		}
		
		
	}

	
	
	
	
	
	
	public int getAssetValue(Player player)
	{
		int combinedValue;
		
		
		// Find value of proporties 
		combinedValue = combinedValue;
		// Number of hotels
		combinedValue = combinedValue + (player.getHotelsOwned()*hotelPrice);
		
		// Number of houses 
		combinedValue = combinedValue+(player.getHousesOwned()*housePrice);
		
		// Balance in account
		combinedValue = combinedValue+(player.getBalance());
	
		
		return combinedValue;
		
		
		
	}
	*/
	
}

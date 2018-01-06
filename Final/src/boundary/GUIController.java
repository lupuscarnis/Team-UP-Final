package boundary;

import gui_fields.GUI_Player;
import gui_main.GUI;

/**
 * Added by Frederik on 06-01-2018 03:02:05 
 *
 */
public class GUIController {
	
	
	GUI g = new GUI();
	
	
	
	/**
	 * Added by Frederik on 06-01-2018 03:02:01 
	 * 
	 * @throws InterruptedException
	 */
	public void setup() throws InterruptedException
	{
		GUI_Player p1 = new GUI_Player("Hanne", 5000);
		GUI_Player p2 = new GUI_Player("Jøden", 5000);
		
		
		g.addPlayer(p1);
		g.addPlayer(p2);
		
		addCarToField(1, p1);
		addCarToField(1, p2);
		Thread.sleep(1500);
		moveCar(1, 5, p1);
		
		Thread.sleep(1500);
		moveCar(5, 10, p1);
		
		Thread.sleep(1500);
		moveCar(10, 15, p1);
		
		Thread.sleep(1500);
		moveCar(15, 25, p1);
		
		Thread.sleep(1500);
		moveCar(25, 32, p1);
		
		Thread.sleep(1500);
		moveCar(32, 40, p1);		
	}
	
	/**
	 * Added by Frederik on 06-01-2018 03:01:41 
	 * 
	 * @param fromField
	 * @param toField
	 * @param player
	 */
	private void moveCar(int fromField, int toField, GUI_Player player) {		
		removeCarFromField(fromField, player);
		addCarToField(toField, player);
	}
	/**
	 * Added by Frederik on 06-01-2018 03:01:46 
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void addCarToField(int fieldNo, GUI_Player player)
	{
		g.getFields()[fieldNo-1].setCar(player, true);
	}
	/**
	 * Added by Frederik on 06-01-2018 03:01:49 
	 * 
	 * @param fieldNo
	 * @param player
	 */
	private void removeCarFromField(int fieldNo, GUI_Player player)
	{
		g.getFields()[fieldNo-1].setCar(player, false);
	}
}

package utilities;

import java.util.Random;

/**
 * Class that simulates random number generation between min and max. 
 *
 */
public class MyRandom {

	private static Random rand = new Random();
	/**
	 * Returns new int between min and max. 
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randInt(int min, int max) {

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}
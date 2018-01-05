package utilities;

import java.util.Random;

public class MyRandom {

	private static Random _rand = new Random();

	
	/**
	 * @param min
	 *            Lowest possible value
	 * @param max
	 *            Highest possible value
	 * @return Value within the boundaries of min and max
	 */

	public static int randInt(int min, int max) {

		int randomNum = _rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}
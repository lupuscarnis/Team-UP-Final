package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import entities.chancecard.ChanceCard;
import entities.chancecard.GetOutJailForFreeChanceCard;
import entities.chancecard.MoveChanceCard;
import entities.chancecard.PayChanceCard;
import entities.chancecard.ReceiveChanceCard;

/**
 * This class handles loading of chance card data from "chancedata.txt"
 *
 */
public class ChanceLoader {

	private InputStream getInputStream(String filename) {
		return getClass().getResourceAsStream(filename);
	}

	/**
	 * Primary method that loads and inserts chance card data into a array of chance
	 * cards.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ChanceCard[] getCards() throws Exception {
		ChanceCard[] cards = new ChanceCard[33];
		InputStream in = getInputStream("/chancedata.txt");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

			String currentLine = br.readLine();
			while (currentLine != null) {

				// Check that line is not placeholder: eg: [xxx]
				if (!currentLine.substring(0, 1).equals("[")) {

					// get line as string array
					String[] arr = currentLine.split(";");

					// get type, number and text1
					String cardType = arr[0];
					int id = Integer.parseInt(arr[1]);
					String text = arr[2];

					switch (cardType) {
					case "MOVE":
						cards[id - 1] = new MoveChanceCard(id, text);
						break;
					case "PAY":
						int payAmount = (arr.length > 3) ? Integer.parseInt(arr[3]) : 0;
						cards[id - 1] = new PayChanceCard(id, text, payAmount);
						break;
					case "RECEIVE":
						int receiveAmount = (arr.length > 3) ? Integer.parseInt(arr[3]) : 0;
						cards[id - 1] = new ReceiveChanceCard(id, text, receiveAmount);
						break;
					case "FREEJAIL":
						cards[id - 1] = new GetOutJailForFreeChanceCard(id, text);
						break;
					default:
						throw new Exception("Card type not found!");
					}
				}
				currentLine = br.readLine();
			}
		}

		return cards;
	}
}

package controllers;

import entities.chancecard.ChanceCard;
import utilities.MyRandom;

public class ChanceCardController {
	
	private ChanceCard[] cardArray;

	public ChanceCardController(ChanceCard[] cards) {
		this.cardArray = cards;
	}	
	
	public ChanceCard drawChanceCard() {
		int minIndex = 0;
		int maxIndex = cardArray.length - 1;
		int nextCard = MyRandom.randInt(minIndex, maxIndex);

		return cardArray[nextCard];
	}
	
	
}

package controllers;

import entities.Player;
import entities.chancecard.ChanceCard;
import entities.chancecard.GetOutJailForFreeChanceCard;
import entities.chancecard.MoveChanceCard;
import entities.chancecard.PayChanceCard;
import entities.chancecard.ReceiveChanceCard;
import utilities.MyRandom;

public class ChanceCardController {

	private ChanceCard[] cardArray;

	public ChanceCardController(ChanceCard[] cards) {
		this.cardArray = cards;
	}

	private ChanceCard drawChanceCard() {
		int minIndex = 0;
		int maxIndex = cardArray.length - 1;
		int nextCard = MyRandom.randInt(minIndex, maxIndex);

		return cardArray[nextCard];
	}

	public void handleDraw(Player player) throws Exception {
		// Draw card
		ChanceCard card = this.drawChanceCard();

		// Draw: Get out of jail for free
		if (card instanceof GetOutJailForFreeChanceCard) {
			System.out.println("Get out card trukket");
		}
		// Draw: Move
		else if (card instanceof MoveChanceCard) {
			System.out.println("Move card trukket");

		}
		// Draw: Pay
		else if (card instanceof PayChanceCard) {
			System.out.println("Pay card trukket");
		}
		// Draw: Receive
		else if (card instanceof ReceiveChanceCard) {
			System.out.println("Receive card trukket");
		}
	}
}

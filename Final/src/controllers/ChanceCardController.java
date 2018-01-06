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

	public ChanceCard drawChanceCard() {
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

			// 31;I anledning af kongens f�dselsdag ben�des de herved for f�ngsel. Dette
			// kort kan opbevares, indtil de f�r brug for det, eller de kan s�lge det.
			// 32;I anledning af kongens f�dselsdag ben�des de herved for f�ngsel. Dette
			// kort kan opbevares, indtil de f�r brug for det, eller de kan s�lge det.
			switch (card.getId()) {

			case 31:
			case 32:
				break;

			default:
				throw new Exception("Case not found!");
			}

			System.out.println("Get out card trukket");
		}
		// Draw: Move
		else if (card instanceof MoveChanceCard) {

			switch (card.getId()) {
			// 1;Ryk brikken frem til det n�rmeste rederi og betal to gange den leje han
			// ellers er berettiget til. Hvis selskabet ikke ejes af nogen, kan de k�be det.
			// 2;Ryk brikken frem til det n�rmeste rederi og betal to gange den leje han
			// ellers er berettiget til. Hvis selskabet ikke ejes af nogen, kan de k�be det.
			case 1:
			case 2:
				break;

			// Tag ind p� r�dhuspladsen
			case 3:
				break;

			// G� i f�ngsel, ryk direkte til f�ngslet. Selv om de passerer start,
			// indkasserer de ikke kr. 4000.
			case 4:
			case 5:
				break;

			// Ryk tre felter tilbage.
			case 12:
				break;

			// Ryk frem til Gr�nningen. Hvis De passerer start, indkasser da kr. 4000.
			case 19:
				break;

			// Ryk frem til start.
			case 20:
				break;

			// Ryk frem til Frederiksberg All�. Hvis de passerer start, indkasser kr. 4000.
			case 29:
				break;

			// Tag med den n�rmeste f�rge - ryk brikken frem, og hvis du passerer start
			// indkasser da kr. 4000.
			case 30:
				break;

			default:
				throw new Exception("Case not found!");
			}
			System.out.println("Move card trukket");
		}
		// Draw: Pay
		else if (card instanceof PayChanceCard) {

			switch (card.getId()) {

			// Oliepriserne er steget, og de skal betale kr 500 pr. hus og kr 2000 pr.
			// hotel.
			case 6:
				break;

			// 13;Ejendomsskatterne er steget, ekstraudgifterne er kr. 800 pr. hus og kr.
			// 2300 pr. hotel.;
			case 13:
				break;

			/*
			 * 10;De har m�ttet vedtage en parkeringsb�de. Betal kr. 200 i b�de.;200 15;De
			 * har modtaget Deres tandl�geregning. Betal kr. 2000.;2000 21;Betal kr. 3000
			 * for reparation af Deres vogn.;3000 22;Betal kr. 3000 for reparation af Deres
			 * vogn.;3000 23;Betal Deres bilforsikring kr. 1000.;1000 28;De har v�ret en tur
			 * i udlandet og har haft for mange cigaretter med hjem. Betal kr. 200;200 33;De
			 * har k�rt frem for fuld stop. Betal kr. 1000.;1000
			 */
			case 10:
			case 15:
			case 21:
			case 22:
			case 23:
			case 28:
			case 33:
				PayChanceCard pc = (PayChanceCard) card;
				pc.getAmount();
				break;

			default:
				throw new Exception("Case not found!");
			}

			System.out.println("Pay card trukket");
		}
		// Draw: Receive
		else if (card instanceof ReceiveChanceCard) {

			switch (card.getId()) {
			/*
			 * 7;Deres premieobligation er kommet ud. De modtager kr 1000 af banken.;1000
			 * 8;Deres premieobligation er kommet ud. De modtager kr 1000 af banken.;1000
			 * 9;Grundet dyrtiden har de f�et en gageforh�jelse. Modtag kr. 1000.;1000
			 * 11;kommunen har eftergivet et kvartals skat. H�v i banken kr. 3000.;3000
			 * 14;De havde en r�kke med elleve rigtige i tipning. Modtag kr. 1000.;1000
			 * 16;De modtager Deres aktieudbytte. Modtag kr. 1000 af banken. ;1000 17;Modtag
			 * udbytte af Deres aktier kr. 1000.;1000 18;Modtag udbytte af Deres aktier kr.
			 * 1000.;1000 24;V�rdien af egen avl fra nyttehaven udg�r kr. 200, som De
			 * modtager af banken.;200 27;De har vundet i Klasselotteriet Modtag kr. 500;500
			 */
			case 7:
			case 8:
			case 9:
			case 11:
			case 14:
			case 16:
			case 17:
			case 18:
			case 24:
			case 27:
				ReceiveChanceCard rc = (ReceiveChanceCard) card;
				rc.getAmount();
				// er de ting der kommer nu en lav prioritet
				break;
			// 25;De modtager Matador-legatet for v�rdig tr�ngende, stort kr. 40000. Ved
			// v�rdig tr�ngende forst�s, at deres formue, d.v.s. Deres kontante penge +
			// sk�der + bygninger ikke overstiger kr. 15000.;
			case 25:
				break;
			// 26;Det er deres f�dselsdag. Modtag af hver medspiller kr. 200.;
			case 26:
				break;
			default:
				throw new Exception("Case not found!");
			}
			System.out.println("Receive card trukket");
		}
	}
}

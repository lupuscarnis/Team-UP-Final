package controllers;

import java.io.IOException;

import entities.Player;
import entities.chancecard.ChanceCard;
import entities.chancecard.GetOutJailForFreeChanceCard;
import entities.chancecard.MoveChanceCard;
import entities.chancecard.PayChanceCard;
import entities.chancecard.ReceiveChanceCard;
import entities.enums.FieldName;
import entities.field.Field;
import entities.field.LotField;
import utilities.ChanceLoader;
import utilities.Messager;
import utilities.MyRandom;

/**
 * 
 * Handles when players land on chance card field (draw card, evaluate card)
 *
 */
public class ChanceCardController extends BaseController {

	private ChanceCard[] cardArray = null; // holds all cards from chancedata.txt
	private FieldLogicController flc;
	private GameLogicCtrl glc;
	private BusinessLogicController blc;

	public ChanceCardController() throws IOException {
		this.cardArray = new ChanceLoader().getCards();
	}

	public void setFlc(FieldLogicController flc) {
		this.flc = flc;
	}

	public void setGlc(GameLogicCtrl glc) {
		this.glc = glc;
	}
/**
 * 
 * @return drawn ChanceCard
 */
	private ChanceCard drawNextCard() {
		int minIndex = 0;
		int maxIndex = cardArray.length - 1;
		int nextCard = MyRandom.randInt(minIndex, maxIndex);

		return cardArray[nextCard];
	}
/**
 * 
 * Draws chance chance and performs action of drawn chance card
 * 
 * @param player
 * @param allPlayers
 * @throws Exception
 */
	public void handleDraw(Player player, Player[] allPlayers) throws Exception {
		// Draw card
		ChanceCard card = drawNextCard();

		// Draw: Get out of jail for free
		if (card instanceof GetOutJailForFreeChanceCard) {

			// 31+32; I anledning af kongens fødselsdag benådes de herved for fængsel. Dette
			// kort kan opbevares, indtil de får brug for det, eller de kan sælge det.
			switch (card.getId()) {

			case 31:
			case 32:
				// update logic
				player.setJailCard(true);

				// update gui
				gui.showMessage(card.getText());
				break;

			default:
				throw new Exception("Case not found!");
			}
		}
		// Draw: Move
		else if (card instanceof MoveChanceCard) {

			Field toField = null;
			Field fromField = player.getCurrentField();

			switch (card.getId()) {

			// 1+2;Ryk brikken frem til det nærmeste rederi og betal to gange den leje han
			// ellers er berettiget til. Hvis selskabet ikke ejes af nogen, kan de købe det.
			case 1:
			case 2:
				// find nearest shipping
				toField = gbc.getNearestShipping(player.getCurrentField().getFieldNumber());

				// update gui
				gui.showMessage(card.getText());
				gui.showPromt("");
				gui.updatePlayerPosition(player.getName(), player.getCurrentField().getFieldNumber(),
						toField.getFieldNumber());

				// move player
				player.setCurrentField(toField);

				// evalute landed on field
				flc.handleFieldAction(player, allPlayers);
				break;

			// 3:Tag ind på rådhuspladsen
			case 3: {
				// find field
				toField = gbc.getFieldByName(FieldName.Rådhuspladsen);

				// opdate gui
				gui.showMessage(card.getText());
				gui.showPromt("");
				gui.updatePlayerPosition(player.getName(), player.getCurrentField().getFieldNumber(),
						toField.getFieldNumber());

				// update logic
				player.setCurrentField(toField);

				// eval landed on field
				flc.handleFieldAction(player, allPlayers);
				break;
			}

			// Gå i fængsel, ryk direkte til fængslet. Selvom de passerer start,
			// indkasserer de ikke kr. 4000.
			case 4:
			case 5: {
				// find field
				toField = gbc.getFieldByName(FieldName.Fængslet);

				// opdate gui
				gui.showMessage(card.getText());
				gui.showPromt("");
				gui.updatePlayerPosition(player.getName(), player.getCurrentField().getFieldNumber(),
						toField.getFieldNumber());

				// handle logic
				glc.handleGoToJail(player);
				break;
			}

			// Ryk tre felter tilbage.
			case 12: {

				if (player.getCurrentField().getFieldNumber() == 3)
					toField = gbc.getFieldByNumber(40);
				// all other fields
				else
					// find field
					toField = gbc.getFieldByNumber(player.getCurrentField().getFieldNumber() - 3);

				// opdate gui
				gui.updatePlayerPosition(player.getName(), fromField.getFieldNumber(), toField.getFieldNumber());

				// update logic
				player.setCurrentField(toField);

				// handle new field
				flc.handleFieldAction(player, allPlayers);
				break;
			}

			// Ryk frem til Grønningen. Hvis De passerer start, indkasser da kr. 4000.
			case 19:

				// find field
				toField = gbc.getFieldByName(FieldName.Grønningen);

				// get money for passing start
				if (glc.checkHavePassedStart(player.getCurrentField().getFieldNumber(), toField.getFieldNumber()))
					player.deposit(BusinessLogicController.MONEY_FOR_PASSING_START);

				gui.showMessage(card.getText());
				gui.showPromt("");
				gui.updatePlayerPosition(player.getName(), fromField.getFieldNumber(), toField.getFieldNumber());

				// update gui
				gui.updateBalance(player);
				gui.updatePlayerPosition(player.getName(), fromField.getFieldNumber(), toField.getFieldNumber());

				// update logic
				player.setCurrentField(toField);

				// resolve new field
				flc.handleFieldAction(player, allPlayers);
				break;

			// Ryk frem til start.
			case 20: {

				toField = gbc.getFieldByName(FieldName.Start);

				gui.showMessage(card.getText());
				gui.showPromt("");
				gui.updatePlayerPosition(player.getName(), fromField.getFieldNumber(), toField.getFieldNumber());

				player.setCurrentField(toField);
				break;
			}

			// Ryk frem til Frederiksberg Alle. Hvis de passerer start, indkasser kr. 4000.
			case 29:
				// find fields
				toField = gbc.getFieldByName(FieldName.FrederiksbergAlle);

				// update player field
				player.setCurrentField(toField);

				// update gui
				gui.updatePlayerPosition(player.getName(), fromField.getFieldNumber(), toField.getFieldNumber());

				// update gui
				gui.showMessage(card.getText());
				gui.showPromt(String.format("(%s):", player.getName()));
				break;

			// Tag med den nærmeste færge - ryk brikken frem, og hvis du passerer start
			// indkasser da kr. 4000.
			case 30:
				// find fields
				toField = gbc.getNearestShipping(player.getFieldNumber());

				// inform/update player
				gui.showMessage(card.getText());
				gui.showPromt("");
				gui.updatePlayerPosition(player.getName(), fromField.getFieldNumber(), toField.getFieldNumber());

				// update logic
				player.setCurrentField(toField);

				// handle new field
				flc.handleFieldAction(player, allPlayers);
				break;

			default:
				throw new Exception("Case not found!");
			}
		}
		// Draw: Pay
		else if (card instanceof PayChanceCard) {

			switch (card.getId()) {
			// 6: Oliepriserne er steget, og de skal betale kr 500 pr. hus og kr 2000 pr.
			// hotel.
			// 13;Ejendomsskatterne er steget, ekstraudgifterne er kr. 800 pr. hus og kr.
			// 2300 pr. hotel.;
			case 6:
			case 13:
				int amountPrHotel = (card.getId() == 6 ? 2000 : 2300); // If card 6 then 2000 else 2300
				int amountPrHouse = (card.getId() == 6 ? 500 : 800); // if card 6 then 500 else 800

				// show card to player
				gui.showMessage(card.getText());
				gui.showPromt("");

				// find all fields belonging til player
				LotField[] fields = gbc.getLotFieldsByOwner(player);

				// count houses and hotel on field
				int houseCount = 0;
				int hotelCount = 0;
				for (LotField lotField : fields) {
					houseCount += lotField.getHouseCount();
					hotelCount += lotField.getHotelCount();
				}

				// calc sum to pay
				int paySum = (houseCount * amountPrHouse) + (hotelCount * amountPrHotel);

				System.out.println(paySum);

				// update logic
				player.withdraw(paySum);

				// update gui
				gui.updateBalance(player);
				break;

			/*
			 * 10;De har måttet vedtage en parkeringsbøde. Betal kr. 200 i bøde. 15;De har
			 * modtaget Deres tandlægeregning. Betal kr. 2000. 21;Betal kr. 3000 for
			 * reparation af Deres vogn. 22;Betal kr. 3000 for reparation af Deres vogn.
			 * 23;Betal Deres bilforsikring kr. 1000. 28;De har været en tur i udlandet og
			 * har haft for mange cigaretter med hjem. Betal kr. 200 33;De har kørt frem for
			 * fuld stop. Betal kr. 1000.
			 */
			case 10:
			case 15:
			case 21:
			case 22:
			case 23:
			case 28:
			case 33:
				PayChanceCard pc = (PayChanceCard) card;

				// inform/update player
				gui.showMessage(card.getText());
				gui.showPromt("");

				// update logic
				player.withdraw(pc.getAmount());

				// update (gui) balance
				gui.updateBalance(player);
				break;

			default:
				throw new Exception("Case not found!");
			}
		}
		// Draw: Receive
		else if (card instanceof ReceiveChanceCard) {

			ReceiveChanceCard rc = (ReceiveChanceCard) card;

			switch (card.getId()) {
			/*
			 * 7;Deres premieobligation er kommet ud. De modtager kr 1000 af banken. 8;Deres
			 * premieobligation er kommet ud. De modtager kr 1000 af banken. 9;Grundet
			 * dyrtiden har de fået en gageforhøjelse. Modtag kr. 11;kommunen har eftergivet
			 * et kvartals skat. Hæv i banken kr. 14;De havde en række med elleve rigtige i
			 * tipning. Modtag kr. 16;De modtager Deres aktieudbytte. Modtag kr. 1000 af
			 * banken. 17;Modtag udbytte af Deres aktier kr. 1000. 18;Modtag udbytte af
			 * Deres aktier kr. 1000. 24;Værdien af egen avl fra nyttehaven udgør kr. 200,
			 * som De modtager af banken. 27;De har vundet i Klasselotteriet Modtag kr.
			 * 500;500
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
				// inform/update player
				gui.showMessage(card.getText());
				gui.showPromt("");

				// update logic
				player.deposit(rc.getAmount());

				// update (gui) balance
				gui.updateBalance(player);

				break;
			// 25;De modtager Matador-legatet for værdig trængende, stort kr. 40000. Ved
			// værdig trængende forstås, at deres formue, d.v.s. Deres kontante penge +
			// skøder + bygninger ikke overstiger kr. 15000.;
			case 25:
				if (blc.playerNetWorth(player) <= 15000) {
					player.deposit(40000); 
					Messager.showReceiveChanceCard(player, 40000);
				} else {
					Messager.showMessage("du er aaaaalt for rig til at gælde som værdigt trængende. Du får intet");
				}
				break;

			// 26;Det er deres fødselsdag. Modtag af hver medspiller kr. 200.;
			case 26:

				// inform/update player
				gui.showMessage(card.getText());
				gui.showPromt("");

				// update logic
				for (Player p : allPlayers) {

					int amount = 200;

					// withdraw money
					p.withdraw(amount);

					// deposit money
					player.deposit(amount);
				}

				// update gui
				gui.updateBalance(allPlayers);

				break;
			default:
				throw new Exception("Case not found!");
			}
		}
	}
	
	/**
	 * method used for creating BussinessLogicController
	 * @param blc
	 */
	public void setBlc(BusinessLogicController blc) {
		this.blc = blc;
	}
}

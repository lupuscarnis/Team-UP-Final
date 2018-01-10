package controllers;

import java.io.IOException;

import boundary.GUIController;
import entities.Player;
import entities.chancecard.ChanceCard;
import entities.chancecard.GetOutJailForFreeChanceCard;
import entities.chancecard.MoveChanceCard;
import entities.chancecard.PayChanceCard;
import entities.chancecard.ReceiveChanceCard;
import entities.enums.FieldName;
import entities.field.Field;
import utilities.ChanceLoader;
import utilities.Messager;
import utilities.MyRandom;

public class ChanceCardController {

	private static ChanceCardController instance;
	private ChanceCard[] cardArray;
	
	private ChanceCardController() throws IOException {
		this.cardArray = new ChanceLoader().getCards();
	}

	public ChanceCard drawChanceCard() {
		int minIndex = 0;
		int maxIndex = cardArray.length - 1;
		int nextCard = MyRandom.randInt(minIndex, maxIndex);

		return cardArray[28];
	}

	public void handleDraw(Player player) throws Exception {
		// Draw card
		ChanceCard card = this.drawChanceCard();

		// Draw: Get out of jail for free
		if (card instanceof GetOutJailForFreeChanceCard) {

			// 31;I anledning af kongens fødselsdag benådes de herved for fængsel. Dette
			// kort kan opbevares, indtil de får brug for det, eller de kan sælge det.
			// 32;I anledning af kongens fødselsdag benådes de herved for fængsel. Dette
			// kort kan opbevares, indtil de får brug for det, eller de kan sælge det.

			// 31;I anledning af kongens fødselsdag benådes de herved for føngsel. Dette
			// kort kan opbevares, indtil de før brug for det, eller de kan sælge det.
			// 32;I anledning af kongens fødselsdag benådes de herved for føngsel. Dette
			// kort kan opbevares, indtil de før brug for det, eller de kan sælge det.

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

			Field moveTofield = null;

			switch (card.getId()) {
			// 1;Ryk brikken frem til det nærmeste rederi og betal to gange den leje han
			// ellers er berettiget til. Hvis selskabet ikke ejes af nogen, kan de købe det.
			// 2;Ryk brikken frem til det nærmeste rederi og betal to gange den leje han
			// ellers er berettiget til. Hvis selskabet ikke ejes af nogen, kan de købe det.
			case 1:
			case 2:
				break;

			// Tag ind på rådhuspladsen
	
			case 3:
			{player.setCurrentField(GameBoardController.getInstance().getFieldByNumber(40));
			Messager.showMoveChanceCard(player, player.getCurrentField());
			FieldLogicController.getInstance().handleFieldAction(player);
			
			break;}

			// Gå i fængsel, ryk direkte til fængslet. Selv om de passerer start,
			// indkasserer de ikke kr. 4000.
			case 4:
			case 5:
			{player.setCurrentField(GameBoardController.getInstance().getFieldByNumber(11));
			Messager.showMoveChanceCard(player, player.getCurrentField());
			// vi maa lige finde ud af det med faengslet, nu er der to muligheder for hvordan han rigtigt "is in jail"
			FieldLogicController.getInstance().handleFieldAction(player);
			player.isInJail(true);
			break;}
				

			// Ryk tre felter tilbage.

			case 12:{
			if(player.getCurrentField().getFieldNumber()==3){player.setCurrentField(GameBoardController.getInstance().getFieldByNumber(40));
			Messager.showMoveChanceCard(player, player.getCurrentField());}
			
			else
				//(player.getCurrentField().getFieldNumber()==1){player.setCurrentField(gbc.getFieldByNumber(38));}
			{player.setCurrentField(GameBoardController.getInstance().getFieldByNumber( player.getCurrentField().getFieldNumber()-3));
			Messager.showMoveChanceCard(player, player.getCurrentField());
			
			}
			FieldLogicController.getInstance().handleFieldAction(player);
				break;}

			// Ryk frem til Grønningen. Hvis De passerer start, indkasser da kr. 4000.
			case 19:
				// {player
				break;
				
					/*
					player.setCurrentField(gbc.getFieldByNumber(25));
				Messager.showMoveChanceCard(player, player.getCurrentField());
				if(player.getCurrentField().getFieldNumber()<player.getPreviousField().getFieldNumber())
				{player.deposit(4000);
				Messager.showPassedStart(player);}
				flc.handleFieldAction(player);
				break;
				
				*/
			
			// Ryk frem til start.
			case 20:
			{player.setCurrentField(GameBoardController.getInstance().getFieldByNumber(1));
			Messager.showMoveChanceCard(player, player.getCurrentField());
				break;}

			// Ryk frem til Frederiksberg Allê. Hvis de passerer start, indkasser kr. 4000.
			// Ryk frem til Frederiksberg Alle. Hvis de passerer start, indkasser kr. 4000.
			case 29:
				// find fields
				moveTofield = GameBoardController.getInstance().getFieldByName(FieldName.FrederiksbergAlle);
				Field fromField = player.getCurrentField();
				
				
				
				GUIController.getInstance().showMessage(card.getText());
				
				GUIController.getInstance().showPromt(String.format("(%s):", player.getName()));
				
				
				
				

				// update player field
				player.setCurrentField(moveTofield);

				// update gui
				GUIController.getInstance().updatePlayerPosition(player.getName(), fromField.getFieldNumber(),
						moveTofield.getFieldNumber());
				break;

			// Tag med den nærmeste færge - ryk brikken frem, og hvis du passerer start
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
			 * <<<<<<< HEAD 10;De har måttet vedtage en parkeringsbøde. Betal kr. 200 i
			 * bøde.;200 15;De ======= 10;De har måttet vedtage en parkeringsbåde. Betal kr.
			 * 200 i både.;200 15;De >>>>>>> branch 'develop' of
			 * https://github.com/lupuscarnis/Team-UP-Final.git har modtaget Deres
			 * tandlægeregning. Betal kr. 2000.;2000 21;Betal kr. 3000 for reparation af
			 * Deres vogn.;3000 22;Betal kr. 3000 for reparation af Deres vogn.;3000
			 * 23;Betal Deres bilforsikring kr. 1000.;1000 28;De har været en tur i udlandet
			 * og har haft for mange cigaretter med hjem. Betal kr. 200;200 33;De har kørt
			 * frem for fuld stop. Betal kr. 1000.;1000
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
				player.withdraw(pc.getAmount());
				Messager.showPayChanceCard(player, pc.getAmount());
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
			 * 9;Grundet dyrtiden har de fået en gageforhøjelse. Modtag kr. 1000.;1000
			 * 11;kommunen har eftergivet et kvartals skat. Hæv i banken kr. 3000.;3000
			 * 14;De havde en række med elleve rigtige i tipning. Modtag kr. 1000.;1000
			 * 16;De modtager Deres aktieudbytte. Modtag kr. 1000 af banken. ;1000 17;Modtag
			 * udbytte af Deres aktier kr. 1000.;1000 18;Modtag udbytte af Deres aktier kr.
			 * 1000.;1000 24;Værdien af egen avl fra nyttehaven udgør kr. 200, som De
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
				player.deposit(rc.getAmount());
				Messager.showReceiveChanceCard(player, rc.getAmount());
				
				// er de ting der kommer nu en lav prioritet
				break;
			// 25;De modtager Matador-legatet for værdig trængende, stort kr. 40000. Ved
			// værdig trængende forstås, at deres formue, d.v.s. Deres kontante penge +
			// skøder + bygninger ikke overstiger kr. 15000.;
			case 25:
				break;
			// 26;Det er deres fødselsdag. Modtag af hver medspiller kr. 200.;
			case 26:
				break;
			default:
				throw new Exception("Case not found!");
			}
			System.out.println("Receive card trukket");
		}
	}

	public static ChanceCardController getInstance() throws IOException {

		if (instance == null)
			instance = new ChanceCardController();

		return instance;
	}
}

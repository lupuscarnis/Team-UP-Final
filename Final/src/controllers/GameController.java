package controllers;

import java.io.IOException;
import boundary.GUIController;
import entities.Player;
import entities.enums.UserOption;
import entities.field.OwnableField;
import utilities.Messager;

//TODO: MANGLER:
// - Chancekort
// - Slå dig ud af fængslet
// - bygninger på grunde
// - pantsætning
// - Ret stavning i Chancedata.txt
// - Get net worth
// - Player lose
// - Player won

public class GameController {

	// controllers
	private BusinessLogicController blc = BusinessLogicController.getInstance();
	private FieldLogicController flc = FieldLogicController.getInstance();
	private PlayerController pc = PlayerController.getInstance();
	private GameLogicCtrl glc = GameLogicCtrl.getInstance();
	private GUIController gui = GUIController.getInstance();

	// CONSTANTS
	private static final int PLAYER_MIN = 3;
	private static final int PLAYER_MAX = 6;

	// Maybe move these to bank
	private static final int MONEY_START_AMOUNT = 30000; // Amount for each player at game start
	private static final int MONEY_START = 4000; // Amount when passing START
	private static final int MONEY_JAIL = 1000; // Amount to pay to leave jail
	private static final int TAX_CASH_AMOUNT = 4000;
	private static final double TAX_PERCENTAGE_AMOUNT = 0.1;

	// ATTRIBUTES
	private Player[] players = null;
	private Player lastPlayer = null; // Who played last turn
	private Player currentPlayer = null; // The current players round
	//TODO: REMOVE
	public static int diceFaceValue =-1; // STRICTLY FOR TESTING
	public static int setChanceCardNumberToDraw=-1; // STRICTLY FOR TESTING

	// FOR TESTING PURPOSES!

	// Testing Gameover
	public GameController() throws IOException {
	}

	private boolean gameOver(Player[] players) {
		int playerCount = 0;
		for (Player player : players) {
			if(player.getBalance()>0) {
				playerCount++;
			}
		}
		int index = 0;
		Player[] playersRemaining = new Player[playerCount];
		for (Player player : players) {
			if(player.getBalance()>0) {
				playersRemaining[index] = player;
				index++;
			}
		}
		
		if(playersRemaining.length==1) {
			return true;
		}
		return false;
	}

	// Testing player is in jail
	private boolean isJail(Player[] players) {
		// TODO Auto-generated method stub
		return false;
	}

	// Testing player gets out of jail
	private boolean getOutJail(Player[] players) {
		return false;
	}

	public void setupGame() throws Exception {

		// get player names from UI
		String[] playerNames = gui.getNewPlayerNames();

		// create new players via PlayerCtrl.
		players = pc.createNewPlayers(playerNames);

		// now GUI can be setup with players
		gui.setup(players);
	}

	public void play() throws Exception {

		// indicates number of turns in current game
		int turnCounter = 0;
		// indicates number of rerolls made by player
		int rerolls = 0;

		// indicates if first turn or not
		boolean isFirstTurn = true;

		// setup
		setupGame();

		System.out.println("-------------------------");
		System.out.println("-- The Game has Begun! --");
		System.out.println("-------------------------");

		// start game loop
		while (!gameOver(players)) {

			// increment turn counter
			turnCounter++;

			// Checking which players starts first if turn == 1
			if (isFirstTurn) {

				// indicate that it's not first turn no more.
				isFirstTurn = false;

				// Get first player from highest "roll"
				currentPlayer = glc.getStartPlayer(players);

				// Vis start spil knap

				// gui.showPromt("Start spil");

				// gui.showPromt("Start spil");
				/*
				 * if(glc.d1.getValue()==glc.d2.getValue()){ currentPlayer = currentPlayer;
				 * rerolls++;
				 */

				// stopper currentplayer for at blive til next player.
			} else { // find next player
				do{
					currentPlayer = glc.getNextPlayer(players);
				} while(currentPlayer.getBalance()==0);
			}

			/*
			 * } rerolls = 0; }
			 */
			gui.showPromt("Det er " + currentPlayer.getName() + "s tur!");

			// present options for user
			// End when EndTurn is selected
			UserOption userChoice = null;
			do {
				userChoice = glc.showUserOptions(currentPlayer);

				switch (userChoice) {

				case BuyHotel:
					break;
				case BuyHouse:
					break;
				case PawnLot:

					// show pawnable lots
					OwnableField[] fields = BusinessLogicController.getInstance().getPawnableFields(currentPlayer);
					
					String[] tmp = new String[fields.length + 1];

					tmp[0] = "- Annuller!";

					int index = 1;
					for (OwnableField string : fields) {
						tmp[index] = string.getTitle();
						index++;
					}

					String result = Messager.getSelectionResult(tmp, currentPlayer.getName());

					// pawn selected lot
					if (!result.equals("- Annuller!"))
						BusinessLogicController.getInstance().pawnLot(result, currentPlayer);

					break;
				case ThrowDice:
					// TODO: Roll streak?
					currentPlayer.setDoneThrowing(true);

					// roll and move player
					glc.rollAndMove(currentPlayer);

					// handle possible field actions
					flc.handleFieldAction(currentPlayer, players);
					break;
				case GetOutOfJailCard:
					currentPlayer.setIsInJail(false);
					currentPlayer.setJailCard(false);
					break;
				case PayToLeaveJail:
					glc.payToLeaveJail(currentPlayer);
					break;				
				case EndTurn:
					currentPlayer.setDoneThrowing(false);
					break;
				case Unpawn:
				
					OwnableField[] pawnedList = BusinessLogicController.getInstance().getPawnedFields(currentPlayer);
					
					
					String[] pawnedNameList = new String[pawnedList.length + 1];
					
					pawnedNameList[0] = "Annuller!";
					int index1 = 1;
					
					for(OwnableField field : pawnedList)
					{
						
						pawnedNameList[index1] = field.getTitle();
						index1++;
						
						
					}
					
					String answer = Messager.getSelectionResult(pawnedNameList, currentPlayer.getName());

					// unpawn selected lot
					if (!answer.equals("Annuller!"))
						BusinessLogicController.getInstance().unpawn(answer, currentPlayer);
					
					break;
				
				
				default:
					throw new Exception("Case not found!");
				}

			} while (userChoice != UserOption.EndTurn);
		}
	}

	
	/**
	 * FOR TESTING: You can "hard code" face value of dice
	 * 
	 * Added by Frederik on 12-01-2018 13:52:27 
	 * 
	 * @param value
	 */
	public void setFaceValue(int value)
	{
		GameController.diceFaceValue = value;
	}
	
	/**
	 * FOR TESTING: You can "hard code" which chance card to draw EVERY time.
	 * 
	 * Added by Frederik on 12-01-2018 13:52:27 
	 * 
	 * @param value
	 */
	public void setChanceCardToDraw(int cardNo)
	{
		GameController.setChanceCardNumberToDraw = cardNo;
	}
	
	public Player[] getPlayers() {
		return players;
	}
}

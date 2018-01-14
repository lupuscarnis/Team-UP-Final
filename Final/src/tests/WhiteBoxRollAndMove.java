package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.GameLogicCtrl;
import entities.Cup;
import entities.Player;

public class WhiteBoxRollAndMove {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void WBTC1() throws Exception {
	
	Player testPlayer = new Player("Player", 0);
	boolean[] rolledDouble = {false,false,false,false,true};
	boolean[] isJailed = {false,true,true,true,true};
	int[] turnsJailed = {2,0,1,2,0};
	
	
	
	Cup cup = new Cup();
	
		for(int i=0;i<5;i++) {
		System.out.println("Dataset "+(i+1));
			testPlayer.setIsInJail(isJailed[i]);
			System.out.print("{"+isJailed[i]+",");
			cup.setRolledDouble(rolledDouble[i]);
			System.out.print(rolledDouble[i]+",");
			testPlayer.setTurnsJailed(turnsJailed[i]);
			System.out.println(turnsJailed[i]+"}");
			
			System.out.println("");
			
			System.out.println("Actual output");
			System.out.print("{");
			
			boolean payToLeave=false;
				if (cup.getRolledDouble()) {
					int streak = testPlayer.getRollDoubleStreak();
					testPlayer.setRollDoubleStreak(streak + 1);
					testPlayer.setIsInJail(false);
				}
				if (!cup.getRolledDouble()) {
					testPlayer.setRollDoubleStreak(0);
					if (testPlayer.isInJail()) {
						testPlayer.setTurnsJailed(testPlayer.getTurnsJailed() + 1);
					}
					if (testPlayer.getTurnsJailed() == 3) {
						payToLeave = true;
						//payToLeaveJail()
					}
				}
			System.out.print(cup.getRolledDouble()+",");
			System.out.print(testPlayer.getRollDoubleStreak()+",");
			System.out.print(testPlayer.isInJail()+",");
			System.out.print(testPlayer.getTurnsJailed()+",");
			System.out.print(payToLeave);
			System.out.println("}");
			System.out.println("");
			System.out.println("");
		}
	}

}

package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.BusinessLogicController;
import entities.Player;

public class BusinessLogicCtrlTestsOLD {

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
	public void testCalculateRent() {
		
		
		// test shipping
		gbc.
		
		
		
		
		
		
	}

	@Test
	public void testPawnLot() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOwner() {
		fail("Not yet implemented");
	}

	/**
	 * Added by Frederik on 06-01-2018 22:05:52 
	 * Test that card state can be set on player.
	 */
	@Test
	public void testSetGetOutOfJailCard() {

		Player p = new Player("Hansi", 0);

		// check that player does not have card
		assertFalse(p.getJailCard());
		
		// change card state to true
		p.setJailCard(true);
		
		// check that player does have card
		assertTrue(p.getJailCard());
		
		// change card state to false
		p.setJailCard(false);
				
		// check that player does not have card
		assertFalse(p.getJailCard());
	}
	
	@Test
	public void testAuction() {
		// giver foerst mening hvis der e ren metode til at kunne trykke eller skrive input i gui, saa auktion test vil halvt vaere en gui test
		
		fail("Not yet implemented");
	}
}

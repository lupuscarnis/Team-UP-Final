package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import controllers.BusinessLogicController;
import controllers.GameBoardController;
import entities.Player;
import entities.enums.UserOption;
import entities.field.LotField;
import entities.field.OwnableField;

public class BusinessLogicControllerTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	Player p = new Player("Gurli", 520);
	private GameBoardController gbc;
	private BusinessLogicController blc;

	@Before
	public void setUp() throws Exception {
		gbc = GameBoardController.getInstance();
		blc = new BusinessLogicController();
	}

	@After
	public void tearDown() throws Exception {
		gbc.destroy();
	}

	/**
	 * Tests that you can set owner state on field.
	 */
	@Test
	public void testSetOwner() {

		for (OwnableField field : gbc.getAllOwnableFields()) {

			// assert not owned
			assertTrue(field.getOwner() == null);

			// change owner state
			blc.setOwner(field, p);
		}
	}

	/**
	 * Tests that player can get GOOJCFF (GetOutOfJail.....)
	 */
	@Test
	public void testSetGetOutOfJailCard() {

		// assert player dont have card
		assertFalse(p.getJailCard());

		// set state
		blc.setGetOutOfJailCard(p, true);

		// assert player has card
		assertTrue(p.getJailCard());
	}

	/**
	 * Tests that system can set owner on selected field.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuyLot() throws Exception {

		for (OwnableField field : gbc.getAllOwnableFields()) {

			
			p.setBalance(10000);
			



			// field not owned
			assertTrue(field.getOwner() == null);

			p.setCurrentField(field);

			// buy lot
			blc.buyLot(p);

			// field owned
			assertTrue(field.getOwner() == p);
			
			assertTrue(10000-field.getPrice()== p.getBalance());
		}
	}

	@Test
	public void testPayRent() throws Exception {
		// TODO: To be implemented
	}

	/**
	 * Calc. the players networth (money + lots + houses)
	 * 
	 * @throws IOException
	 *
	 */
	@Test
	public void testPlayerNetWorth() throws IOException {

		// assert initial
		assertEquals(p.getBalance(), 520);

		// set owner of 1 lot
		OwnableField field = (OwnableField) gbc.getFieldByNumber(2);
		blc.setOwner(field, p);
		assertEquals(p.getBalance() + 600, blc.playerNetWorth(p));

		// set owner of 2 lots
		field = (OwnableField) gbc.getFieldByNumber(4);
		blc.setOwner(field, p);
		assertEquals(p.getBalance() + 1200, blc.playerNetWorth(p));

		// set owner of 2 lots and 4 houses and 1 hotel
		LotField lf = (LotField) gbc.getFieldByNumber(4);
		lf.setHotelCount(1);
		lf.setHouseCount(4);

		assertEquals(p.getBalance() + 1200 + 2500 + 2000, blc.playerNetWorth(p));
	}

	/**
	 * Test that player can build house on field.
	 * 
	 * @throws Exception
	 */
	public void testBuildHouse() throws Exception {
		// TODO: To be implemented
	}

	public void testBuildHotel() {
		// TODO: To be implemented
	}

	public void testEvaluatePlayer() {
		// TODO: To be implemented
	}

	public void testPayIncomeTax() throws Exception {
		// test the 10% option
		assertEquals(520, p.getBalance());		
		blc.payIncomeTax(p,UserOption.IncomeTaxPayTenPercent);		
		assertEquals(520-52, p.getBalance());
		
		// pay 4000 option
		p.setBalance(5000);
		assertEquals(5000, p.getBalance());		
		blc.payIncomeTax(p,UserOption.IncomeTaxPay4000);		
		assertEquals(1000, p.getBalance());		
	}

	public void testUserCanAffordLot() {

	}

	public void testUserCanAffordHouse() {

	}

	public void testUserCanAffordHotel() {

	}

	public void testPlayerCanBuildHouse() {

	}

	public void testPlayerCanBuildHotel() {

	}

	public void testHasHouse() {

	}

	public void testHasHotel() {

	}

	public void testSellHouse() {

	}

	public void testSellHotel() {

	}

	public void testGetFieldsWithHouses() {

	}

	public void testGetFieldsWithHotels() {

	}

	public void testGetPawnableFields() {

	}

	public void testGetPawnedFields() {

	}

	public void testHasPawn() {

	}

	public void testCanPawn() {

	}

	public void testPawnLotStringPlayer() {

	}

	public void testAuction() {

	}

	public void testUnpawn() {

	}

	public void testDestroyPlayer() {

	}

}

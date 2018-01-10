package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import controllers.GameBoardController;
import entities.Player;
import entities.field.OwnableField;

public class GameBoardCtrlTests {

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
	public void testGetNearestShipping() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFieldByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFieldRelativeToPos() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFieldByNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFields() {
		fail("Not yet implemented");
	}

	/**
	 * Added by Frederik on 07-01-2018 00:53:09
	 * 
	 * Check that correct no. fields are returned for owner.
	 * 
	 * @throws IOException
	 *
	 */
	@Test
	public void testGetFieldsByOwner() throws IOException {

		GameBoardController gbc = GameBoardController.getInstance();
		Player p = new Player("Henny", 0);

		// 0
		assertTrue(gbc.getFieldsByOwner(p).length == 0);

		int count = 1;
		for (int fieldNo : new int[] { 2, 4, 6, 7, 9, 10, 12, 13, 14, 15, 16, 17, 19, 20, 22, 24, 25, 26, 27, 28, 29,
				30, 32, 33, 35, 36, 38, 40 }) {

			OwnableField of = (OwnableField) gbc.getFieldByNumber(fieldNo);

			of.setOwner(p);
			assertTrue(gbc.getFieldsByOwner(p).length == count);

			count++;
		}

		count--;

		for (int fieldNo : new int[] { 2, 4, 6, 7, 9, 10, 12, 13, 14, 15, 16, 17, 19, 20, 22, 24, 25, 26, 27, 28, 29,
				30, 32, 33, 35, 36, 38, 40 }) {

			OwnableField of = (OwnableField) gbc.getFieldByNumber(fieldNo);

			assertTrue(gbc.getFieldsByOwner(p).length == count);

			of.setOwner(null);

			count--;
		}
	}

	@Test
	public void countNumberOwnedByType() {
		
		
		
		
		
		
	}
}

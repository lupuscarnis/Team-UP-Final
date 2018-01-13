package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.GameBoardController;
import entities.Player;
import entities.enums.FieldName;
import entities.field.OwnableField;

public class GameBoardControllerTests {

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
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNearestShipping() throws IOException, Exception {
		int[] arrayPlayerPosition = new int[40];
		for(int i=0;i<40;i++) {
			arrayPlayerPosition[i] = i+1;
		}
		for(int currentPosition : arrayPlayerPosition) {
		
			if(currentPosition<6 | currentPosition>=36) {
				assertTrue(GameBoardController.getInstance().getNearestShipping(currentPosition).getFieldNumber()==6);
			}
			if(currentPosition>=6 && currentPosition <16) {
				assertTrue(GameBoardController.getInstance().getNearestShipping(currentPosition).getFieldNumber()==16);
			}
			if(currentPosition>=16 && currentPosition <26) {
				assertTrue(GameBoardController.getInstance().getNearestShipping(currentPosition).getFieldNumber()==26);
			}
			if(currentPosition>=26 && currentPosition <36) {
				assertTrue(GameBoardController.getInstance().getNearestShipping(currentPosition).getFieldNumber()==36);
			}
		}
	}

	@Test
	public void testGetFieldByName() throws IOException, Exception {
		FieldName[] fieldNameEnumArray = {FieldName.FrederiksbergAlle, FieldName.Fængslet, FieldName.Grønningen, FieldName.Rådhuspladsen, FieldName.Start};
		String[] fieldTitleArray = {"Frederiksberg Allé", "Fængsel", "Grønningen", "Rådhuspladsen", "Start"};
				
		for(int i=0; i<fieldNameEnumArray.length; i++) {
		assertTrue(GameBoardController.getInstance().getFieldByName(fieldNameEnumArray[i]).getTitle().equals(fieldTitleArray[i]));
		}
	}

	@Test
	public void testGetFieldRelativeToPos() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testGetFieldByNumber() throws IOException {
//		int[] fieldNumbers = new int [40];
//		for (int i=0; i<40; i++) {
//			fieldNumbers[i] = i;
//		}
//		
//		for(int i=0; i<40; i++) {
//		assertTrue(i==GameBoardController.getInstance().getFieldByNumber(i).getFieldNumber());
//		}
//	}

	@Test
	public void testGetFields() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testGetFieldsByOwner() throws IOException {
//		Player[] players = new Player[2];
//		Player[0] = new Player("Bølle", 100);
//		
//		OwnableField[] fieldArray = GameBoardController.getInstance().getAllOwnableFields();
//		for(OwnableField field : fieldArray) {
//			field.setOwner(player);
//		}
//		
//	}

	@Test
	public void testGetLotFieldsByOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllOwnableFields() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllLotFields() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountBreweriesOwned() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountShippingOwned() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountLotsOwnedByColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountLotsInColorGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShippingFieldsOwned() {
		fail("Not yet implemented");
	}

}

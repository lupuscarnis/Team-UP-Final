package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.BusinessLogicController;
import controllers.GameBoardController;

public class BusinessLogicControllerTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	private GameBoardController gbc;

	@Before
	public void setUp() throws Exception {
		gbc = GameBoardController.getInstance();
		blc= new BusinessLogicController();
	}

	@After
	public void tearDown() throws Exception {
		gbc.destroy();
	}

	
	public void testBusinessLogicController() {
		 
	}

	
	public void testPawnLotOwnableField() {
		 
	}

	
	public void testSetOwner() {
		 
	}

	
	public void testSetGetOutOfJailCard() {
		 
	}

	
	public void testBuyLot() {
		 
	}

	
	public void testPayRent() {
		 
	}

	
	public void testPlayerNetWorth() {
		 
	}

	
	public void testBuildHouse() {
		 
	}

	
	public void testBuildHotel() {
		 
	}

	
	public void testEvaluatePlayer() {
		 
	}

	
	public void testPayIncomeTax() {
		 
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

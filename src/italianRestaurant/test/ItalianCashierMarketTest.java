package italianRestaurant.test;

import application.Phonebook;
import italianRestaurant.*;
import italianRestaurant.ItalianCashierRole.BillState;
import italianRestaurant.test.mock.*;
import market.test.mock.*;
import junit.framework.TestCase;

/**
 * 
 * This class is a JUnit test class to unit test the ItalianRestaurantCashierRole's
 *  interactions with the new Market in SimCity201.
 *
 */

public class ItalianCashierMarketTest extends TestCase{
	//these are instantiated for each test separately via the setUp() method.
	ItalianCashierRole cashier;
	MockSalesPerson mockSalesPerson;
	MockItalianCook mockCook;
	
	public void setUp() throws Exception{
		super.setUp();
		cashier = new ItalianCashierRole("italianRestaurantCashier", Phonebook.getPhonebook().getItalianRestaurant());
		mockCook = new MockItalianCook("Mock Cook");
		mockSalesPerson = new MockSalesPerson("mocksalesperson");
	}
	
	public void testOneNormalMarketScenario() {
		//Checking pre-conditions
		assertEquals("ItalianRestaurantCashier should have 0 bills in it. It doesn't.", cashier.BillFoods.size(), 0);
		
		cashier.msgPleasePayForItems("Steak", "17", 271.83, mockSalesPerson);
		
		//step 1
		assertEquals("ItalianRestaurantCashier should have 1 bill in it. It doesn't.", cashier.BillFoods.size(), 1);
				
		assertTrue("CashierBill should have a state == pending. It doesn't.",
				cashier.BillFoods.get(0).s == BillState.pending);
		//step 2
		//NOTE: I called the scheduler in the assertTrue statement below (to succintly check the return value at the same time)
		
		assertTrue("ItalianRestaurantCashier's scheduler should have returned true (needs to react to customer's HereIsMoney), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		assertTrue("CashierBill should have a state == done. It doesn't.",
				cashier.BillOrders.get(0).s == BillState.done);
		
		assertFalse("ItalianRestaurantCashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
	}	
}

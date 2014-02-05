package italianRestaurant.test;

import italianRestaurant.ItalianCashierRole;
import italianRestaurant.ItalianCashierRole.BillState;
import italianRestaurant.ItalianRestaurant;
import italianRestaurant.test.mock.MockItalianCustomer;
import italianRestaurant.test.mock.MockItalianMarket;
import italianRestaurant.test.mock.MockItalianWaiter;
import junit.framework.TestCase;

/**
 * 
 * This class is a JUnit test class to unit test the ItalianRestaurantCashierRole's basic interaction
 * with waiters, customers, and the host.
 * It is provided as an example to students in CS201 for their unit testing lab.
 *
 */
public class CashierTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	ItalianCashierRole cashier;
	MockItalianWaiter waiter;
	MockItalianCustomer customer;
	MockItalianMarket market1;
	MockItalianMarket market2;
	ItalianRestaurant restaurant;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		cashier = new ItalianCashierRole("italianRestaurantCashier", restaurant);		
		customer = new MockItalianCustomer("mockcustomer");		
		waiter = new MockItalianWaiter("mockwaiter");
		market1 = new MockItalianMarket("Market1");
		market2 = new MockItalianMarket("Market2");
	}	
	/**
	 * This tests the italianRestaurantCashier under very simple terms: one customer is ready to pay the exact bill.
	 */
	public void testOneNormalCustomerScenario()
	{
		//setUp() runs first before this test!
		
		customer.cashier = cashier;//You can do almost anything in a unit test.			
		
		//check preconditions
		assertEquals("ItalianRestaurantCashier should have 0 bills in it. It doesn't.",cashier.BillOrders.size(), 0); 
				
		
		//step 1 of the test
		cashier.msgComputeBill(waiter, customer, "Steak");//send the message from a waiter
		
		//check postconditions for step 1 and preconditions for step 2
		
		assertEquals("ItalianRestaurantCashier should have 1 bill in it. It doesn't.", cashier.BillOrders.size(), 1);
		
		assertFalse("ItalianRestaurantCashier's scheduler should have returned false (no actions to do on a billorder from a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals(
				"ItalianRestaurantMockWaiter should have an empty event log after the ItalianRestaurantCashier's scheduler is called for the first time. Instead, the ItalianRestaurantMockWaiter's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals(
				"ItalianRestaurantMockCustomer should have an empty event log after the ItalianRestaurantCashier's scheduler is called for the first time. Instead, the ItalianRestaurantMockCustomer's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		//step 2 of the test
		
		cashier.msgHereIsMoney(customer, 20.00);
		
		//check postconditions for step 2 / preconditions for step 3
		
		
		assertTrue("CashierBill should have a state == pending. It doesn't.",
				cashier.BillOrders.get(0).s == BillState.pending);
		
		assertTrue("CashierBill should contain the right customer in it. It doesn't.", 
					cashier.BillOrders.get(0).c == customer);
		
		
		//step 3
		//NOTE: I called the scheduler in the assertTrue statement below (to succintly check the return value at the same time)
		
		assertTrue("ItalianRestaurantCashier's scheduler should have returned true (needs to react to customer's HereIsMoney), but didn't.", 
					cashier.pickAndExecuteAnAction());
		
		//check postconditions for step 3 / preconditions for step 4
		assertTrue("ItalianRestaurantMockCustomer should have logged an event for receiving \"HereIsYourTotal\" with the correct balance, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsYourTotal from italianRestaurantCashier. Total = 7.98"));
		
		assertTrue("CashierBill should contain changeDue == 0.0. It contains something else instead: $" 
				+ cashier.BillOrders.get(0).change, cashier.BillOrders.get(0).change == 0);
		
		
		
		//step 4
		
		assertTrue("ItalianRestaurantCashier's scheduler should have returned true (needs to react to customer's ReadyToPay), but didn't.", 
					cashier.pickAndExecuteAnAction());
		
		//check postconditions for step 4
		assertTrue("ItalianRestaurantMockCustomer should have logged an event for receiving \"HereIsYourChange\" with the correct change, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsYourChange from italianRestaurantCashier. Change = 0.0"));
	
		
		assertTrue("CashierBill should have a state == done. It doesn't.",
				cashier.BillOrders.get(0).s == BillState.done);
		
		assertFalse("ItalianRestaurantCashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
	
	}//end one normal customer scenario
	
	public void testOneNormalMarketScenario()
	{
		market1.cashier = cashier;
		
		//check preconditions
		assertEquals("ItalianRestaurantCashier should have 0 bill in it. It doesn't.", cashier.BillFoods.size(), 0);
		
		cashier.msgRestockingBill(market1, "Steak", 271.83);
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
	
	public void testTwoMarketsScenario()
	{
		market1.cashier = cashier;
		
		//check preconditions
		
		cashier.msgRestockingBill(market1, "Steak", 239.85);
		cashier.msgRestockingBill(market2, "Steak", 47.97);
		
		//step 1
				assertEquals("ItalianRestaurantCashier should have 2 bill in it. It doesn't.", cashier.BillFoods.size(), 2);
				
				assertTrue("CashierBill should have a state == pending. It doesn't.",
						cashier.BillFoods.get(0).s == BillState.pending);
				
				//step 2
				//NOTE: I called the scheduler in the assertTrue statement below (to succintly check the return value at the same time)
				
				assertTrue("ItalianRestaurantCashier's scheduler should have returned true (needs to react to customer's HereIsMoney), but didn't.", 
							cashier.pickAndExecuteAnAction());
				
				assertTrue("CashierBill should have a state == done. It doesn't.",
						cashier.BillOrders.get(0).s == BillState.done);
				
				assertTrue("ItalianRestaurantCashier's scheduler should have returned true (needs to react to customer's HereIsMoney), but didn't.", 
						cashier.pickAndExecuteAnAction());
		
		//step 3
		assertEquals("ItalianRestaurantCashier should have 1 bill in it. It doesn't.", cashier.BillFoods.size(), 1);
		
		assertTrue("CashierBill should have a state == pending. It doesn't.",
				cashier.BillFoods.get(0).s == BillState.pending);
		
		//step 4
		//NOTE: I called the scheduler in the assertTrue statement below (to succintly check the return value at the same time)
		
		assertTrue("ItalianRestaurantCashier's scheduler should have returned true (needs to react to customer's HereIsMoney), but didn't.", 
					cashier.pickAndExecuteAnAction());
		
		assertTrue("CashierBill should have a state == done. It doesn't.",
				cashier.BillOrders.get(0).s == BillState.done);
		
		assertFalse("ItalianRestaurantCashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
	}
	
}

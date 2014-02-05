//package seafoodRestaurant.test;
//
//import junit.framework.TestCase;
////import restaurant.CashierAgent;
////import restaurant.Check.CheckState;
////import restaurant.test.mock.MockCustomer;
////import restaurant.test.mock.MockMarket;
////import restaurant.test.mock.MockWaiter;
//import seafoodRestaurant.SeafoodRestaurantCashierRole;
//import seafoodRestaurant.SeafoodRestaurantCheck.CheckState;
//import seafoodRestaurant.test.mock.SeafoodRestaurantMockCustomer;
//import seafoodRestaurant.test.mock.SeafoodRestaurantMockWaiter;
//
//public class SeafoodRestaurantV22BTests extends TestCase
//{
//	//these are instantiated for each test separately via the setUp() method.
//	SeafoodRestaurantCashierRole americanRestaurantCashier;
////	SeafoodRestaurantMockMarket market1;
////	SeafoodRestaurantMockMarket market2;
//    SeafoodRestaurantMockWaiter waiter;
//    SeafoodRestaurantMockCustomer customer;
//
//    /**
//     * This method is run before each test. You can use it to instantiate the class variables
//     * for your agent and mocks, etc.
//     */
//	public void setUp() throws Exception
//	{
//		super.setUp();
//		americanRestaurantCashier = new SeafoodRestaurantCashierRole("americanRestaurantCashier");    
////		market1 = new SeafoodRestaurantMockMarket("market1");
////		market2 = new SeafoodRestaurantMockMarket("market2");
//        customer = new SeafoodRestaurantMockCustomer("mockcustomer");                
//        waiter = new SeafoodRestaurantMockWaiter("mockwaiter");
//	}
//	
//    /**
//     * This tests the americanRestaurantCashier under very simple terms: one market is ready to be paid.
//     */
//	public void testNormativeScenarioOne()
//	{
//		assertEquals("The AmericanRestaurantCashier should have enough $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		//americanRestaurantCashier.MarketCost("Lobster Tail and Roll", 20.00, market1);
//		
//		assertEquals("The AmericanRestaurantCashier should still $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("The AmericanRestaurantCashier should have one market bill at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 1);
//		//assertEquals("The AmericanRestaurantCashier should have the one market bill from the Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).market, market1);
//		assertEquals("The AmericanRestaurantCashier should have the one market bill for Lobster Tail and Roll. It doesn't.", americanRestaurantCashier.MarketBills.get(0).foodItem, "Lobster Tail and Roll");
//		assertEquals("The AmericanRestaurantCashier should have the one market bill of $20.00. It doesn't.", americanRestaurantCashier.MarketBills.get(0).finalTotal, 20.00);
//
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		assertEquals("The AmericanRestaurantCashier should have $15 in profits. It doesn't.", americanRestaurantCashier.profits, 15.00);
//		assertEquals("The AmericanRestaurantCashier should have $20 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 20.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		//assertEquals("MockMarket should have logged one event. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 1, market1.log.size());		
//	}
//
//	 /**
//     * This tests the americanRestaurantCashier under simple terms: two markets are ready to be paid.
//     */
//	public void testNormativeScenarioTwo()
//	{
//		assertEquals("The AmericanRestaurantCashier should have enough $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		//assertEquals("MockMarket1 should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		//assertEquals("MockMarket2 should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market2.log.toString(), 0, market2.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		//americanRestaurantCashier.MarketCost("Lobster Tail and Roll", 20.00, market1);
//		
//		assertEquals("The AmericanRestaurantCashier should still $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("The AmericanRestaurantCashier should have one market bill at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 1);
//		//assertEquals("The AmericanRestaurantCashier should have the one market bill from the first Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).market, market1);
//		assertEquals("The AmericanRestaurantCashier should have the one market bill for Lobster Tail and Roll from the first Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).foodItem, "Lobster Tail and Roll");
//		assertEquals("The AmericanRestaurantCashier should have the one market bill of $20.00 from the first Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).finalTotal, 20.00);
//
//		//americanRestaurantCashier.MarketCost("Bourbon-Glazed Salmon", 12.00, market2);
//		
//		assertEquals("The AmericanRestaurantCashier should still $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("The AmericanRestaurantCashier should have two market bills at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 2);
//		//assertEquals("The AmericanRestaurantCashier should have the one market bill from the second Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(1).market, market2);
//		assertEquals("The AmericanRestaurantCashier should have the one market bill for Bourbon-Glazed Salmon from the second Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(1).foodItem, "Bourbon-Glazed Salmon");
//		assertEquals("The AmericanRestaurantCashier should have the one market bill of $12.00 from the second Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(1).finalTotal, 12.00);		
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should have one market bills at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 1);
//		//assertEquals("The other market bill should be from the second Mock Market. It isn't.", americanRestaurantCashier.MarketBills.get(0).market, market2);
//		assertEquals("The AmericanRestaurantCashier should have enough $15 in profits. It doesn't.", americanRestaurantCashier.profits, 15.00);
//		assertEquals("The AmericanRestaurantCashier should have enough $20 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 20.00);
//		//assertEquals("MockMarket1 should have logged one event. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 1, market1.log.size());
//		//assertEquals("MockMarket2 should have logged no events. Instead, the MockMarket's event log reads: " 
//		//		+ market2.log.toString(), 0, market2.log.size());
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		assertEquals("The AmericanRestaurantCashier should have enough $3 in profits. It doesn't.", americanRestaurantCashier.profits, 3.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have enough $32 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 32.00);
//		//assertEquals("MockMarket2 should have logged one event. Instead, the MockMarket's event log reads: " 
//		//		+ market2.log.toString(), 1, market2.log.size());		
//	}
//
//	 /**
//     * This tests the americanRestaurantCashier under simple terms: one customer is ready to pay.
//     */
//	public void testScenarioThree()
//	{
//		customer.cashier = americanRestaurantCashier;
//		waiter.cashier = americanRestaurantCashier;
//		
//		assertEquals("The AmericanRestaurantCashier should have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no checks at the moment. It does.", americanRestaurantCashier.AllChecks.size(), 0);
//		
//        americanRestaurantCashier.GiveMeCheck("7.98", customer, waiter);
//        
//		assertEquals("The AmericanRestaurantCashier should have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		//assertEquals("The Check should be in the 'Created' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Created);
//		assertEquals("The Check should be for 7.98. It isn't.", americanRestaurantCashier.AllChecks.get(0).cost, 7.98);
//		assertEquals("The Check should be for the Mock AmericanRestaurantCustomer. It isn't.", americanRestaurantCashier.AllChecks.get(0).c, customer);
//		assertEquals("The Check should be for the Mock AmericanRestaurantWaiter. It isn't.", americanRestaurantCashier.AllChecks.get(0).w, waiter);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());		
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		//assertEquals("The Check should be in the 'Pending' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Pending);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an event logged. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 1, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());	
//		
//		customer.cashier.HereIsPayment(americanRestaurantCashier.AllChecks.get(0), 7.98);
//
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());	
//
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//        assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + americanRestaurantCashier.AllChecks.get(0).s,
//                americanRestaurantCashier.AllChecks.get(0).s == CheckState.PaidOff);
//		assertEquals("The AmericanRestaurantCashier should have $42.98 in profits. It doesn't.", americanRestaurantCashier.profits, 42.98);
//		assertEquals("The AmericanRestaurantCashier should have $42.98 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 42.98);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have logged two events. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 2, customer.log.size());	      
//	}
//	
//	 /**
//     * This tests the americanRestaurantCashier under simple terms: one customer is ready to pay, but doesn't have enough money.
//     */
//	public void testScenarioFour()
//	{
//		customer.cashier = americanRestaurantCashier;
//		waiter.cashier = americanRestaurantCashier;
//		
//		assertEquals("The AmericanRestaurantCashier should have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no checks at the moment. It does.", americanRestaurantCashier.AllChecks.size(), 0);
//		
//        americanRestaurantCashier.GiveMeCheck("7.98", customer, waiter);
//        
//		assertEquals("The AmericanRestaurantCashier should have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'Created' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Created);
//		assertEquals("The Check should be for 7.98. It isn't.", americanRestaurantCashier.AllChecks.get(0).cost, 7.98);
//		assertEquals("The Check should be for the Mock AmericanRestaurantCustomer. It isn't.", americanRestaurantCashier.AllChecks.get(0).c, customer);
//		assertEquals("The Check should be for the Mock AmericanRestaurantWaiter. It isn't.", americanRestaurantCashier.AllChecks.get(0).w, waiter);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());		
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'Pending' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Pending);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an event logged. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 1, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());	
//		
//		customer.cashier.HereIsPayment(americanRestaurantCashier.AllChecks.get(0), 0.0);
//
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());	
//
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());	
//
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//        assertTrue("CashierBill should contain a bill with state == NotPaidOff. It doesn't." + americanRestaurantCashier.AllChecks.get(0).s,
//                americanRestaurantCashier.AllChecks.get(0).s == CheckState.NotPaidOff);
//		assertEquals("The AmericanRestaurantCashier should have $27.02 in profits. It doesn't.", americanRestaurantCashier.profits, 27.02);
//		assertEquals("The AmericanRestaurantCashier should have $35.00 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $7.98 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 7.98);
//		assertEquals("AmericanRestaurantCustomer should still have logged two events. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 2, customer.log.size());	      
//	}
//	
//	 /**
//     * This tests the americanRestaurantCashier under simple terms: one customer is ready to pay and one market is ready to get paid. The market will prompt the americanRestaurantCashier while the customer is there.
//     */
//	public void testScenarioFive()
//	{
//		customer.cashier = americanRestaurantCashier;
//		waiter.cashier = americanRestaurantCashier;
//		
//		assertEquals("The AmericanRestaurantCashier should have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		assertEquals("The AmericanRestaurantCashier should have no checks at the moment. It does.", americanRestaurantCashier.AllChecks.size(), 0);
//		
//        americanRestaurantCashier.GiveMeCheck("7.98", customer, waiter);
//        
//		assertEquals("The AmericanRestaurantCashier should have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'Created' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Created);
//		assertEquals("The Check should be for 7.98. It isn't.", americanRestaurantCashier.AllChecks.get(0).cost, 7.98);
//		assertEquals("The Check should be for the Mock AmericanRestaurantCustomer. It isn't.", americanRestaurantCashier.AllChecks.get(0).c, customer);
//		assertEquals("The Check should be for the Mock AmericanRestaurantWaiter. It isn't.", americanRestaurantCashier.AllChecks.get(0).w, waiter);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'Pending' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Pending);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an event logged. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 1, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		customer.cashier.HereIsPayment(americanRestaurantCashier.AllChecks.get(0), 7.98);
//
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		//americanRestaurantCashier.MarketCost("Lobster Tail and Roll", 20.00, market1);
//		
//		assertEquals("The AmericanRestaurantCashier should have one market bill at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 1);
//		//assertEquals("The AmericanRestaurantCashier should have the one market bill from the Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).market, market1);
//		assertEquals("The AmericanRestaurantCashier should have the one market bill for Lobster Tail and Roll. It doesn't.", americanRestaurantCashier.MarketBills.get(0).foodItem, "Lobster Tail and Roll");
//		assertEquals("The AmericanRestaurantCashier should have the one market bill of $20.00. It doesn't.", americanRestaurantCashier.MarketBills.get(0).finalTotal, 20.00);
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should still be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		assertEquals("The AmericanRestaurantCashier should have $15 in profits. It doesn't.", americanRestaurantCashier.profits, 15.00);
//		assertEquals("The AmericanRestaurantCashier should have $20 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 20.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		//assertEquals("MockMarket should have logged one event. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 1, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should still be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//        assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + americanRestaurantCashier.AllChecks.get(0).s,
//                americanRestaurantCashier.AllChecks.get(0).s == CheckState.PaidOff);
//		assertEquals("The AmericanRestaurantCashier should have $22.98 in profits. It doesn't.", americanRestaurantCashier.profits, 22.98);
//		assertEquals("The AmericanRestaurantCashier should have $42.98 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 42.98);
//		assertEquals("The AmericanRestaurantCashier should still have $20 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 20.00);
//		assertEquals("AmericanRestaurantCustomer should still have logged two events. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 2, customer.log.size());	
//	}
//	
//	 /**
//     * This tests the americanRestaurantCashier under simple terms: one customer is ready to pay and one market is ready to get paid.
//     * The market will prompt the americanRestaurantCashier while the customer is there.
//     * The americanRestaurantCashier doesn't have enough to pay the market until the customer pays.
//     */
//	public void testScenarioSix()
//	{
//		customer.cashier = americanRestaurantCashier;
//		waiter.cashier = americanRestaurantCashier;
//		
//		assertEquals("The AmericanRestaurantCashier should have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		assertEquals("The AmericanRestaurantCashier should have no checks at the moment. It does.", americanRestaurantCashier.AllChecks.size(), 0);
//		
//        americanRestaurantCashier.GiveMeCheck("7.98", customer, waiter);
//        
//		assertEquals("The AmericanRestaurantCashier should have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'Created' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Created);
//		assertEquals("The Check should be for 7.98. It isn't.", americanRestaurantCashier.AllChecks.get(0).cost, 7.98);
//		assertEquals("The Check should be for the Mock AmericanRestaurantCustomer. It isn't.", americanRestaurantCashier.AllChecks.get(0).c, customer);
//		assertEquals("The Check should be for the Mock AmericanRestaurantWaiter. It isn't.", americanRestaurantCashier.AllChecks.get(0).w, waiter);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 0, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'Pending' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.Pending);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantWaiter should still have an event logged. Instead, the AmericanRestaurantWaiter's event log reads: " 
//				+ waiter.log.toString(), 1, waiter.log.size());
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		customer.cashier.HereIsPayment(americanRestaurantCashier.AllChecks.get(0), 7.98);
//
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		
//		//americanRestaurantCashier.MarketCost("Lobster Tail and Roll", 40.00, market1);
//		
//		assertEquals("The AmericanRestaurantCashier should have one market bill at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 1);
//		//assertEquals("The AmericanRestaurantCashier should have the one market bill from the Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).market, market1);
//		assertEquals("The AmericanRestaurantCashier should have the one market bill for Lobster Tail and Roll. It doesn't.", americanRestaurantCashier.MarketBills.get(0).foodItem, "Lobster Tail and Roll");
//		assertEquals("The AmericanRestaurantCashier should have the one market bill of $40.00. It doesn't.", americanRestaurantCashier.MarketBills.get(0).finalTotal, 40.00);
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should still be in the 'CustomerHere' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.CustomerHere);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in profits. It doesn't.", americanRestaurantCashier.profits, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $35 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 35.00);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 0, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The AmericanRestaurantCashier should have one market bill at the moment. It doesn't.", americanRestaurantCashier.MarketBills.size(), 1);
//		//assertEquals("The AmericanRestaurantCashier should have the one market bill from the Mock Market. It doesn't.", americanRestaurantCashier.MarketBills.get(0).market, market1);
//		assertEquals("The AmericanRestaurantCashier should have the one market bill for Lobster Tail and Roll. It doesn't.", americanRestaurantCashier.MarketBills.get(0).foodItem, "Lobster Tail and Roll");
//		assertEquals("The AmericanRestaurantCashier should have the one market bill of $40.00. It doesn't.", americanRestaurantCashier.MarketBills.get(0).finalTotal, 40.00);
//		assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + americanRestaurantCashier.AllChecks.get(0).s,
//                americanRestaurantCashier.AllChecks.get(0).s == CheckState.PaidOff);
//		assertEquals("The AmericanRestaurantCashier should have $42.98 in profits. It doesn't.", americanRestaurantCashier.profits, 42.98);
//		assertEquals("The AmericanRestaurantCashier should have $42.98 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 42.98);
//		assertEquals("The AmericanRestaurantCashier should still have $0 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 0.0);
//		assertEquals("AmericanRestaurantCustomer should still have logged two events. Instead, the AmericanRestaurantCustomer's event log reads: " 
//				+ customer.log.toString(), 2, customer.log.size());
//		//assertEquals("MockMarket should have an empty event log before AmericanRestaurantCashier's scheduler is called. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 0, market1.log.size());
//		
//		assertTrue("AmericanRestaurantCashier's PickAndExecuteAction should have returned true. It didn't.", americanRestaurantCashier.pickAndExecuteAnAction());
//		
//		assertEquals("The AmericanRestaurantCashier should have no market bills at the moment. It does.", americanRestaurantCashier.MarketBills.size(), 0);
//		assertEquals("The AmericanRestaurantCashier should have $2.98 in profits. It doesn't.", americanRestaurantCashier.profits, 2.98);
//		assertEquals("The AmericanRestaurantCashier should have $40 in accumulated Costs. It doesn't.", americanRestaurantCashier.accumulatedCosts, 40.00);
//		assertEquals("The AmericanRestaurantCashier should still have $42.98 in accumulated Revenue. It doesn't.", americanRestaurantCashier.accumulatedRevenue, 42.98);
//		//assertEquals("MockMarket should have logged one event. Instead, the MockMarket's event log reads: " 
//		//		+ market1.log.toString(), 1, market1.log.size());
//		assertEquals("The AmericanRestaurantCashier should still have one check at the moment. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//		assertEquals("The Check should still be in the 'PaidOff' state. It isn't." + americanRestaurantCashier.AllChecks.get(0).s, americanRestaurantCashier.AllChecks.get(0).s, CheckState.PaidOff);		
//		
//	}
//}

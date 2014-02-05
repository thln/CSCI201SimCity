//package seafoodRestaurant.test;
//
////import restaurant.CashierAgent;
////import restaurant.Check;
////import restaurant.Check.CheckState;
////import restaurant.test.mock.EventLog;
////import restaurant.CashierAgent.cashierBillState;
////import restaurant.WaiterAgent.Bill;
////import restaurant.test.mock.MockCustomer;
////import restaurant.test.mock.MockWaiter;
//import seafoodRestaurant.SeafoodRestaurantCashierRole;
//import seafoodRestaurant.SeafoodRestaurantCheck;
//import seafoodRestaurant.SeafoodRestaurantCheck.CheckState;
//import seafoodRestaurant.test.mock.SeafoodRestaurantMockCustomer;
//import seafoodRestaurant.test.mock.SeafoodRestaurantMockWaiter;
//import junit.framework.*;
//
//public class SeafoodRestaurantCashierTest extends TestCase
//{
//    //these are instantiated for each test separately via the setUp() method.
//    SeafoodRestaurantCashierRole americanRestaurantCashier;
//    SeafoodRestaurantMockWaiter waiter;
//    SeafoodRestaurantMockCustomer customer;
//    
//    
//    
//    /**
//     * This method is run before each test. You can use it to instantiate the class variables
//     * for your agent and mocks, etc.
//     */
//    public void setUp() throws Exception
//    {
//            super.setUp();                
//            americanRestaurantCashier = new SeafoodRestaurantCashierRole("americanRestaurantCashier");                
//            customer = new SeafoodRestaurantMockCustomer("mockcustomer");                
//            waiter = new SeafoodRestaurantMockWaiter("mockwaiter");
//    }        
//    /**
//     * This tests the americanRestaurantCashier under very simple terms: one customer is ready to pay the exact bill.
//     */
//    public void testOneNormalCustomerScenario()
//    {
//            //setUp() runs first before this test!
//            
//            customer.cashier = americanRestaurantCashier;//You can do almost anything in a unit test.                        
//            SeafoodRestaurantCheck mockCheck = new SeafoodRestaurantCheck("Lobster Tail and Roll", customer, waiter);
//            //check preconditions
//            assertEquals("AmericanRestaurantCashier should have 0 bills in it. It doesn't.",americanRestaurantCashier.AllChecks.size(), 0);                
//
//            americanRestaurantCashier.GiveMeCheck("7.98", customer, waiter);
//            
//            //check postconditions for step 1 and preconditions for step 2
//            assertEquals("AmericanRestaurantMockWaiter should have an empty event log before the AmericanRestaurantCashier's scheduler is called. Instead, the AmericanRestaurantMockWaiter's event log reads: "
//                                            + waiter.log.toString(), 0, waiter.log.size());
//            
//            assertEquals("AmericanRestaurantCashier should have 1 bill in it. It doesn't.", americanRestaurantCashier.AllChecks.size(), 1);
//             
//            assertEquals(
//                            "AmericanRestaurantMockWaiter should have an empty event log after the AmericanRestaurantCashier's scheduler is called for the first time. Instead, the AmericanRestaurantMockWaiter's event log reads: "
//                                            + waiter.log.toString(), 0, waiter.log.size());
//            
//            assertEquals(
//                            "AmericanRestaurantMockCustomer should have an empty event log after the AmericanRestaurantCashier's scheduler is called for the first time. Instead, the AmericanRestaurantMockCustomer's event log reads: "
//                                            + waiter.log.toString(), 0, waiter.log.size());
//            
//            assertTrue("CashierBill should contain a bill of price = $7.98. It contains something else instead: $" 
//                            + americanRestaurantCashier.AllChecks.get(0).cost, americanRestaurantCashier.AllChecks.get(0).cost == 7.98);
//            
//            assertTrue("CashierBill should contain a bill with the right customer in it. It doesn't.", 
//                                    americanRestaurantCashier.AllChecks.get(0).c == customer);
//            
//            americanRestaurantCashier.HereIsPayment(americanRestaurantCashier.AllChecks.get(0), 7.98);
//            americanRestaurantCashier.pickAndExecuteAnAction();
//            
//            assertTrue("CashierBill should contain a bill with state == PaidOff. It doesn't." + americanRestaurantCashier.AllChecks.get(0).s,
//                            americanRestaurantCashier.AllChecks.get(0).s == CheckState.PaidOff);
//    
//    }//end one normal customer scenario
//    
//   
//}

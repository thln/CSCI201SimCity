package chineseRestaurant.test;

//import bank.BankGuardRole;
import chineseRestaurant.ChineseRestaurantAltWaiterRole;
import chineseRestaurant.ChineseRestaurantCookRole;
import chineseRestaurant.ChineseRestaurantRevolvingStand;
import chineseRestaurant.ChineseRestaurantMyCustomer.customerState;
import chineseRestaurant.test.mock.ChineseRestaurantMockCustomer;
import junit.framework.TestCase;
import application.Phonebook;
//import person.Role.RoleState;
import person.Worker;
//import restaurant.RevolvingStand;
/**
 * Alternative AmericanRestaurantWaiter Test
 * @author Tam Henry Le Nguyen
 *
 *The point of this test is to see if the waiter can use the revolving stand or not.
 */

public class ChineseRestaurantAlternativeWaiterTest extends TestCase
{
	Worker person1;
	Worker person2;
	//Worker person3;
	ChineseRestaurantAltWaiterRole altWaiter;
	ChineseRestaurantCookRole cook;
    ChineseRestaurantMockCustomer customer;
    ChineseRestaurantMockCustomer customer2;
    // RevolvingStand rs; // = Phonebook.getPhonebook().getRestaurant().getRevolvingStand();
    //MockCook cook;

    /**
     * This method is run before each test. You can use it to instantiate the class variables
     * for your agent and mocks, etc.
     */
	public void setUp() throws Exception
	{
		super.setUp();
		//Phonebook();
        customer = new ChineseRestaurantMockCustomer("mockcustomer");
        customer2 = new ChineseRestaurantMockCustomer("mockcustomer2");
        person1 = new Worker("testAlternativeWaiter", 200, "alternative waiter", "restaurant", 800, 1200, 1500);
        person2 = new Worker("testCook", 200, "cook", "restaurant", 800, 1200, 1500);
		altWaiter = new ChineseRestaurantAltWaiterRole ( person1, person1.getName(), "Alternative Waiter", Phonebook.getPhonebook().getChineseRestaurant());
		cook = new ChineseRestaurantCookRole(person2, person2.getName(), "cook", Phonebook.getPhonebook().getChineseRestaurant());
	//	rs = Phonebook.getPhonebook().getRestaurant().theRevolvingStand;
		//cook = new MockCook("mockcook");
	}
	
    /**
     * This tests the alternative waiter under the following terms: 
     * if the waiter has access to the revolving stand
     * if the chef has access to the revolving stand
     * what happens if the chef checks an empty revolving stand
     * if the waiter can pull an order on the revolving stand
     * if the chef can take the order from the revolving stand
     * if the revolving stand is empty again
     */
	public void testNormativeScenarioOne()
	{
		try 
		{
			setUp();
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
		assertEquals("Person 1's job Title should be Alternative AmericanRestaurantWaiter. It isn't.", "alternative waiter", person1.getJob().title);
		assertEquals("Person 2's job Title should be Cook. It isn't.","cook", person2.getJob().title);
		assertEquals("Person 1's job location should be restaurant. It isn't.", "restaurant", person1.getJob().jobPlace);
		assertEquals("Person 2's job location should be restaurant. It isn't.", "restaurant", person2.getJob().jobPlace);
		
		//Should not be in worker role yet
		assertEquals("Person 1's worker Role should not be active. It is.", null, person1.getWorkerRole());
		assertEquals("Person 2's worker Role should not be active. It is.", null, person2.getWorkerRole());
		
		assertTrue("There should be no waiters in the host's list of waiters. There are.",Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.waiters.isEmpty());
		assertEquals("There should be no cook in the restaurant. There is.",Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.getPerson(), null);
		assertFalse("AltWaiter's PickAndExecuteAction should have returned false. It didn't.", altWaiter.pickAndExecuteAnAction());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		
		altWaiter.test = true;
		
		assertTrue("The waiter's list of customers should be empty. It isn't.", altWaiter.ChineseRestaurantMyCustomers.isEmpty());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());
		
		altWaiter.msgPleaseSeatTestCustomer(customer);

		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.ChineseRestaurantMyCustomers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Waiting. It isn't.", customerState.Waiting, altWaiter.ChineseRestaurantMyCustomers.get(0).state );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());

		
		altWaiter.msgHeresMyOrder(customer, "steak");
		
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Ordered. It isn't.", customerState.Ordered, altWaiter.ChineseRestaurantMyCustomers.get(0).state );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());

		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		assertTrue("Alternative AmericanRestaurantWaiter's PickAndExecuteAction should have returned true. It didn't.", altWaiter.pickAndExecuteAnAction());
		
		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.ChineseRestaurantMyCustomers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().getSize());

		assertTrue("Cook's PickAndExecuteAction should have returned true. It didn't.", cook.pickAndExecuteAnAction());

		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.ChineseRestaurantMyCustomers.size());
		assertEquals("The cook's list of orders should be 1. It isn't.", 1, cook.myOrders.size());
		assertEquals("The customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());
		assertEquals("The cook's order should be the same as the waiter's order.", "steak", cook.myOrders.get(0).choice);
	}
	
	/**
	 * This tests the alternative waiter under the following terms:
	 * if there are two orders placed on the revolving stand the chef will take
	 * the orders in the correct sort and remove them accordingly 
	 */
	public void testNormativeScenarioTwo()
	{
		try 
		{
			setUp();
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
		assertEquals("Person 1's job Title should be Alternative AmericanRestaurantWaiter. It isn't.", "alternative waiter", person1.getJob().title);
		assertEquals("Person 2's job Title should be Cook. It isn't.","cook", person2.getJob().title);
		assertEquals("Person 1's job location should be restaurant. It isn't.", "restaurant", person1.getJob().jobPlace);
		assertEquals("Person 2's job location should be restaurant. It isn't.", "restaurant", person2.getJob().jobPlace);
		
		//Should not be in worker role yet
		assertEquals("Person 1's worker Role should not be active. It is.", null, person1.getWorkerRole());
		assertEquals("Person 2's worker Role should not be active. It is.", null, person2.getWorkerRole());
		
		assertTrue("There should be no waiters in the host's list of waiters. There are.",Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.waiters.isEmpty());
		assertEquals("There should be no cook in the restaurant. There is.",Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.getPerson(), null);
		assertFalse("AltWaiter's PickAndExecuteAction should have returned false. It didn't.", altWaiter.pickAndExecuteAnAction());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		
		altWaiter.test = true;
		
		assertTrue("The waiter's list of customers should be empty. It isn't.", altWaiter.ChineseRestaurantMyCustomers.isEmpty());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());
		
		altWaiter.msgPleaseSeatTestCustomer(customer);
		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.ChineseRestaurantMyCustomers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Waiting. It isn't.", customerState.Waiting, altWaiter.ChineseRestaurantMyCustomers.get(0).state );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());

		
		altWaiter.msgHeresMyOrder(customer, "steak");
		
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Ordered. It isn't.", customerState.Ordered, altWaiter.ChineseRestaurantMyCustomers.get(0).state );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());

		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		assertTrue("Alternative AmericanRestaurantWaiter's PickAndExecuteAction should have returned true. It didn't.", altWaiter.pickAndExecuteAnAction());
		
		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.ChineseRestaurantMyCustomers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().getSize());

		altWaiter.msgPleaseSeatTestCustomer(customer2);
		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.ChineseRestaurantMyCustomers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertEquals("The customer's state should be Waiting. It isn't.", customerState.Waiting, altWaiter.ChineseRestaurantMyCustomers.get(1).state );
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().getSize());
		
		altWaiter.msgHeresMyOrder(customer2, "chicken");
		
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The customer's state should be Ordered. It isn't.", customerState.Ordered, altWaiter.ChineseRestaurantMyCustomers.get(1).state );
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().getSize());
		
		assertTrue("Alternative AmericanRestaurantWaiter's PickAndExecuteAction should have returned true. It didn't.", altWaiter.pickAndExecuteAnAction());
		
		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.ChineseRestaurantMyCustomers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.myOrders.isEmpty());
		assertEquals("The first customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertEquals("The second customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(1).state);
		assertEquals("The revolving stand's list of orders should be 2. It isn't.", 2, Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().getSize());
		
		assertTrue("Cook's PickAndExecuteAction should have returned true. It didn't.", cook.pickAndExecuteAnAction());

		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.ChineseRestaurantMyCustomers.size());
		assertEquals("The cook's list of orders should be 1. It isn't.", 1, cook.myOrders.size());
		assertEquals("The first customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertEquals("The second customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(1).state);
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().getSize());
		assertEquals("The cook's first order should be the same as the waiter's order.", "steak", cook.myOrders.get(0).choice);

		assertTrue("Cook's PickAndExecuteAction should have returned true. It didn't.", cook.pickAndExecuteAnAction());

		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.ChineseRestaurantMyCustomers.size());
		assertEquals("The cook's list of orders should be 2. It isn't.", 2, cook.myOrders.size());
		assertEquals("The first customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(0).state);
		assertEquals("The second customer's state should be Waiting for order. It isn't.", customerState.WaitingForFood, altWaiter.ChineseRestaurantMyCustomers.get(1).state);
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty());
		assertEquals("The cook's first order should be the same as the waiter's order.", "steak", cook.myOrders.get(0).choice);
		assertEquals("The cook's second order should be the same as the waiter's order.", "chicken", cook.myOrders.get(1).choice);

	}
}

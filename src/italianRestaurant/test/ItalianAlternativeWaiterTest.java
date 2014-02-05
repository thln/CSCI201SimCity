package italianRestaurant.test;

import italianRestaurant.*;
import italianRestaurant.ItalianMyCustomer.MyCustState;
import italianRestaurant.test.mock.*;
import junit.framework.TestCase;
import application.Phonebook;
//import person.Role.RoleState;
import person.Worker;
//import restaurant.RevolvingStand;
/**
 * Alternative ItalianWaiter Test
 * @author Carmen Tan
 *
 *The point of this test is to see if the waiter can use the revolving stand or not.
 */

public class ItalianAlternativeWaiterTest extends TestCase
{
	Worker person1;
	Worker person2;
	//Worker person3;
	ItalianAltWaiterRole altWaiter;
	ItalianCookRole cook;
    MockItalianCustomer customer;
    MockItalianCustomer customer2;
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
        customer = new MockItalianCustomer("mockcustomer");
        customer2 = new MockItalianCustomer("mockcustomer2");
        person1 = new Worker("testAlternativeWaiter", 200, "alternative waiter", "restaurant", 800, 1200, 1500);
        person2 = new Worker("testCook", 200, "cook", "restaurant", 800, 1200, 1500);
		altWaiter = new ItalianAltWaiterRole ( person1, person1.getName(), "Alternative Waiter"/*, Phonebook.getPhonebook().getItalianRestaurant()*/);
		cook = new ItalianCookRole(person2, person2.getName(), "cook", Phonebook.getPhonebook().getItalianRestaurant());
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
		
		assertTrue("There should be no waiters in the host's list of waiters. There are.",Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waiters.isEmpty());
		assertEquals("There should be no cook in the restaurant. There is.",Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCookRole.getPerson(), null);
		assertFalse("AltWaiter's PickAndExecuteAction should have returned false. It didn't.", altWaiter.pickAndExecuteAnAction());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		
		altWaiter.test = true;
		
		assertTrue("The waiter's list of customers should be empty. It isn't.", altWaiter.Customers.isEmpty());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());
		
		altWaiter.msgPleaseSitCustomer(customer, 0, 0);

		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.Customers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be waiting. It isn't.", MyCustState.waiting, altWaiter.Customers.get(0).s );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());

		
		altWaiter.msgHereIsOrder(customer, "steak");
		
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be Ordered. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());

		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		assertTrue("Alternative AmericanRestaurantWaiter's PickAndExecuteAction should have returned true. It didn't.", altWaiter.pickAndExecuteAnAction());
		
		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.Customers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().getSize());

		assertTrue("Cook's PickAndExecuteAction should have returned true. It didn't.", cook.pickAndExecuteAnAction());

		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.Customers.size());
		assertEquals("The cook's list of orders should be 1. It isn't.", 1, cook.Orders.size());
		assertEquals("The customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());
		assertEquals("The cook's order should be the same as the waiter's order.", "steak", cook.Orders.get(0).choice);
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
		
		assertTrue("There should be no waiters in the host's list of waiters. There are.",Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waiters.isEmpty());
		assertEquals("There should be no cook in the restaurant. There is.",Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCookRole.getPerson(), null);
		assertFalse("AltWaiter's PickAndExecuteAction should have returned false. It didn't.", altWaiter.pickAndExecuteAnAction());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		
		altWaiter.test = true;
		
		assertTrue("The waiter's list of customers should be empty. It isn't.", altWaiter.Customers.isEmpty());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());
		
		altWaiter.msgPleaseSitCustomer(customer, 0, 0);
		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.Customers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be waiting. It isn't.", MyCustState.waiting, altWaiter.Customers.get(0).s );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());
		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());

		
		altWaiter.msgHereIsOrder(customer, "steak");
		
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be Ordered. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s );
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());

		assertFalse("Cook's PickAndExecuteAction should have returned false. It didn't.", cook.pickAndExecuteAnAction());
		assertTrue("Alternative AmericanRestaurantWaiter's PickAndExecuteAction should have returned true. It didn't.", altWaiter.pickAndExecuteAnAction());
		
		assertEquals("The waiter's list of customers should be 1. It isn't.", 1, altWaiter.Customers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().getSize());

		altWaiter.msgPleaseSitCustomer(customer2, 1, 1);
		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.Customers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertEquals("The customer's state should be waiting. It isn't.", MyCustState.waiting, altWaiter.Customers.get(1).s );
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().getSize());
		
		altWaiter.msgHereIsOrder(customer2, "chicken");
		
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The customer's state should be Ordered. It isn't.", MyCustState.ordered, altWaiter.Customers.get(1).s );
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().getSize());
		
		assertTrue("Alternative AmericanRestaurantWaiter's PickAndExecuteAction should have returned true. It didn't.", altWaiter.pickAndExecuteAnAction());
		
		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.Customers.size());
		assertTrue("The cook's list of orders should be empty. It isn't.", cook.Orders.isEmpty());
		assertEquals("The first customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertEquals("The second customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(1).s);
		assertEquals("The revolving stand's list of orders should be 2. It isn't.", 2, Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().getSize());
		
		assertTrue("Cook's PickAndExecuteAction should have returned true. It didn't.", cook.pickAndExecuteAnAction());

		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.Customers.size());
		assertEquals("The cook's list of orders should be 1. It isn't.", 1, cook.Orders.size());
		assertEquals("The first customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertEquals("The second customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(1).s);
		assertEquals("The revolving stand's list of orders should be 1. It isn't.", 1, Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().getSize());
		assertEquals("The cook's first order should be the same as the waiter's order.", "steak", cook.Orders.get(0).food.type);

		assertTrue("Cook's PickAndExecuteAction should have returned true. It didn't.", cook.pickAndExecuteAnAction());

		assertEquals("The waiter's list of customers should be 2. It isn't.", 2, altWaiter.Customers.size());
		assertEquals("The cook's list of orders should be 2. It isn't.", 2, cook.Orders.size());
		assertEquals("The first customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(0).s);
		assertEquals("The second customer's state should be waiting for order. It isn't.", MyCustState.ordered, altWaiter.Customers.get(1).s);
		assertTrue("The revolving stand's list of orders should be empty. It isn't.", Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty());
		assertEquals("The cook's first order should be the same as the waiter's order.", "steak", cook.Orders.get(0).food.type);
		assertEquals("The cook's second order should be the same as the waiter's order.", "chicken", cook.Orders.get(1).food.type);

	}
}

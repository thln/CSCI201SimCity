package bank.test;

import person.Worker;
import bank.BankGuardRole;
import bank.mock.BankCustomerMock;
import bank.mock.BankTellerMock;
import junit.framework.TestCase;

public class GuardTest extends TestCase
{

	BankGuardRole guard;
	BankCustomerMock customer;
	BankTellerMock teller;
	Worker person;

	public void setUp() throws Exception {
		super.setUp();
		person = new Worker("Josh", 200, "bankGuard", "bank", 800, 1200, 1500);
		guard = new BankGuardRole (person.getName(), person, "bankGuard");
		customer = new BankCustomerMock ("mockcustomer");
		teller = new BankTellerMock ("bankteller");		
		guard.test = true;
	}

	public void testOneNormalTellerArrivesCustomerArrives() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}
		//check preconditions
		assertEquals("Guard should have 0 customers in list. It doesn't.", guard.getCustomers().size(), 0);
		assertEquals("Guard should have 0 tellers in list. It doesn't.", guard.getTellers().size(), 0); 

		//Step 1: Teller comes to work, add teller to the bank guard's list
		guard.msgTellerCameToWork (teller); 

		//Step 1 post-conditions
		assertEquals("Guard should have 1 teller in list. It doesn't.", guard.getTellers().size(), 1);

		//Step 1a, call scheduler

		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());

		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);

		//Step 2: AmericanRestaurantCustomer arrives at bank
		guard.msgArrivedAtBank(customer);

		//Step 2 post-conditions
		assertEquals("Guard should have 1 customer in list. It doesn't.", guard.getCustomers().size(), 1);

		//Step 3: Scheduler runs...should execute method "assignToTeller"
		assertFalse("Guard's scheduler should have returned false, but didn't.", 
				guard.pickAndExecuteAnAction());
	
		//Step 3 post-conditions
		assertTrue("MockCustomer should have logged an event for being assigned to teller, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), 
				customer.log.containsString("Assigned to bank teller " + teller.getName()));

		assertTrue("MockTeller shoudl have the state 'busy'",
				guard.getTellers().get(0).state == BankGuardRole.TellerState.busy);

		assertEquals("Guard should have 0 customers in list. It doesn't.", guard.getCustomers().size(), 0);

		assertFalse("Guard's scheduler should have returned false (no teller available), but didn't.", 
				guard.pickAndExecuteAnAction());

		//Step 4: customer leaves bank

		guard.msgCustomerLeavingBank(teller) ;

		//Step 4 post-conditions

		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);

		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());
		
		guard.msgTellerLeavingWork(teller);
	}



	public void testTwoOneTellerTwoCustomers() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		//Set person to work as bankguard and set role active
		//person.setWorkerRole(guard);
		//person.getWorkerRole().setRoleActive();


		//this tests a normal interaction, then a second customer comes in the middle of this interaction
		BankCustomerMock customer2 = new BankCustomerMock ("mockcustomer");

		//check preconditions
		assertEquals("Guard should have 0 customers in list. It doesn't.", guard.getCustomers().size(), 0);
		assertEquals("Guard should have 0 tellers in list. It doesn't.", guard.getTellers().size(), 0); 

		//Step 1: Teller comes to work, add teller to the bank guard's list
		guard.msgTellerCameToWork (teller); 


		//Step 1 post-conditions
		assertEquals("Guard should have 1 teller in list. It doesn't.", guard.getTellers().size(), 1); 	

		//Step 1a

		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());

		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);

		//Step 2: AmericanRestaurantCustomer 1 arrives at bank
		guard.msgArrivedAtBank(customer);

		//Step 2 post-conditions
		assertEquals("Guard should have 1 customer in list. It doesn't.", guard.getCustomers().size(), 1);

		//Step 3: Scheduler runs...should execute method "assignToTeller"
		assertFalse("Guard's scheduler should have returned false, but didn't.", 
				guard.pickAndExecuteAnAction());

		//Step 3 post-conditions
		assertTrue("MockCustomer should have logged an event for being assigned to teller, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), 
				customer.log.containsString("Assigned to bank teller " + teller.getName()));

		assertTrue("MockTeller shoudl have the state 'busy' but instead his state is " + guard.getTellers().get(0).state,
				guard.getTellers().get(0).state == BankGuardRole.TellerState.busy);

		assertEquals("Guard should have 0 customers in list. It doesn't.", guard.getCustomers().size(), 0);

		assertFalse("Guard's scheduler should have returned false, but didn't.", 
				guard.pickAndExecuteAnAction());

		//Step 4: second customer arrives at bank

		guard.msgArrivedAtBank(customer2);

		//Step 4 post-conditions
		assertEquals("Guard should have 1 customer in list. It doesn't.", guard.getCustomers().size(), 1);

		//Step 5: Scheduler runs...should execute method "assignToTeller"
		guard.pickAndExecuteAnAction();

		//Step 5 post-conditions, second customer should have to wait
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for no teller available, but his last event logged reads instead: " 
				+ customer2.log.getLastLoggedEvent().toString(), customer2.log.containsString("No teller available, must wait"));

		assertFalse("Guard's scheduler should have returned false, but didn't.", 
				guard.pickAndExecuteAnAction());

		//Step 6: customer 1 leaves bank

		guard.msgCustomerLeavingBank(teller) ;

		//Step 6 post-conditions

		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);

		assertFalse("Guard's scheduler should have returned false, but didn't.", 
				guard.pickAndExecuteAnAction());

		//step 7: assigning customer 2 to the teller
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for being assigned to teller, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), 
				customer.log.containsString("Assigned to bank teller " + teller.getName()));

		assertFalse("Guards's scheduler should have returned false, but didn't.", 
				guard.pickAndExecuteAnAction());
		
		guard.msgTellerLeavingWork(teller);
	}



	public void testThreeCustomerArrivesBeforeWorkingTeller() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		//check preconditions
		assertEquals("Guard should have 0 customers in list. It doesn't.", guard.getCustomers().size(), 0);
		assertEquals("Guard should have 0 tellers in list. It doesn't.", guard.getTellers().size(), 0); 


		//Step 1: AmericanRestaurantCustomer arrives at bank
		guard.msgArrivedAtBank(customer);

		//Step 1 post-conditions
		assertEquals("Guard should have 1 customer in list. It doesn't.", guard.getCustomers().size(), 1);

		//Step 2: Scheduler runs...should execute method "assignToTeller"
		assertFalse("Guard's scheduler should have returned false (no teller available), but didn't.", 
				guard.pickAndExecuteAnAction());

		//Step 2 post-conditions
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for no teller available, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("No teller available, must wait"));

		//Step 3: Teller arrives at work
		guard.msgTellerCameToWork (teller); 
		
		//Step 3 post-conditions
		
		assertEquals("Guard should have 1 teller in list. It doesn't.", guard.getTellers().size(), 1); 	
		
		//Step 4: run scheduler and execute method "assignToTeller"
		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());
		
		//Step 4 post-conditions
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for being assigned to teller, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), 
				customer.log.containsString("Assigned to bank teller " + teller.getName()));

		assertTrue("MockTeller shoudl have the state 'busy'",
				guard.getTellers().get(0).state == BankGuardRole.TellerState.busy);

	
		//Step 5: customer leaves bank

		guard.msgCustomerLeavingBank(teller) ;

		//Step 5 post-conditions

		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);

		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());
		
		guard.msgTellerLeavingWork(teller);
	}
	
	//TODO THIS METHOD
	public void testFourTwoCustomersArriveBeforeWorkingTeller() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		//check preconditions
		assertEquals("Guard should have 0 customers in list. It doesn't.", guard.getCustomers().size(), 0);
		assertEquals("Guard should have 0 tellers in list. It doesn't.", guard.getTellers().size(), 0); 


		//Step 1: AmericanRestaurantCustomer arrives at bank
		guard.msgArrivedAtBank(customer);

		//Step 1 post-conditions
		assertEquals("Guard should have 1 customer in list. It doesn't.", guard.getCustomers().size(), 1);

		//Step 2: Scheduler runs...should execute method "assignToTeller"
		assertFalse("Guard's scheduler should have returned false (no teller available), but didn't.", 
				guard.pickAndExecuteAnAction());

		//Step 2 post-conditions
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for no teller available, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("No teller available, must wait"));

		//Step 3: Teller arrives at work
		guard.msgTellerCameToWork (teller); 
		
		//Step 3 post-conditions
		
		assertEquals("Guard should have 1 teller in list. It doesn't.", guard.getTellers().size(), 1); 	
		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);
		
		//Step 4: run scheduler and execute method "assignToTeller"
		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());
		
		//Step 4 post-conditions
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for being assigned to teller, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), 
				customer.log.containsString("Assigned to bank teller " + teller.getName()));

		assertTrue("MockTeller shoudl have the state 'busy'",
				guard.getTellers().get(0).state == BankGuardRole.TellerState.busy);

	
		//Step 5: customer leaves bank

		guard.msgCustomerLeavingBank(teller) ;

		//Step 5 post-conditions

		assertTrue("1st MockTeller should have the state 'available' ", 
				guard.getTellers().get(0).state == BankGuardRole.TellerState.available);

		assertFalse("Guard's scheduler should have returned false (no new customers)  but didn't.", 
				guard.pickAndExecuteAnAction());
		
		guard.msgTellerLeavingWork(teller);
	}
}

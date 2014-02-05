package market.test;

import chineseRestaurant.ChineseRestaurant;
import chineseRestaurant.test.mock.ChineseRestaurantMockCook;
import application.Phonebook;
import junit.framework.TestCase;
import market.Market;
import market.MarketOrder.orderState;
import market.MarketOrder;
import market.MarketRunnerRole;
import market.SalesPersonRole;
import market.MarketCustomerRole.MarketCustomerState;
import market.interfaces.UPSman;
import market.test.mock.MockMarketCustomer;
import market.test.mock.MockMarketRunner;
import market.test.mock.MockSalesPerson;
import market.test.mock.MockUPSman;
import person.Worker;

public class MarketRunnerTest extends TestCase {

	Market market;
	ChineseRestaurant chineseRestaurant;
	Worker worker;
	MarketRunnerRole marketRunner;
	MockSalesPerson salesPerson;
	MockMarketCustomer marketCustomer;
	MockUPSman UPSman;
	
	public void setUp() throws Exception {
		super.setUp();
		market = Phonebook.getPhonebook().getEastMarket();
		chineseRestaurant = Phonebook.getPhonebook().getChineseRestaurant();
		salesPerson = (MockSalesPerson) Phonebook.getPhonebook().getEastMarket().getSalesPerson(true);
		marketCustomer = new MockMarketCustomer("MockMarketCustomer");
		UPSman = (MockUPSman) Phonebook.getPhonebook().getEastMarket().getUPSman(true);
		
		worker = new Worker("Worker", 50, "Market Runner", "Market", 8, 12, 24);
		marketRunner = new MarketRunnerRole(worker, "Market Runner", "Market Runner", market);
		marketRunner.test = true;
	}


	public void testOneMarketCustomerOrderNormativeScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Checking pre-conditions
		assertEquals("Market Runner shouldn't have any orders in it", marketRunner.orders.size(), 0);
		
		assertEquals("Market Runner should have an empty event log before the Market Runner's msgHeresAnOrder is called. Instead, the Market Runner's event log reads: "
				+ marketRunner.log.toString(), 0, marketRunner.log.size());
		
		//Step 1
		marketRunner.msgHeresAnOrder(new MarketOrder(marketCustomer, "Ice Cream", 1)); 
		
		//Checking post conditions for step 1
		assertEquals("Market Runner should have 1 order in it", marketRunner.orders.size(), 1);
		
		assertEquals("Market runner should have 1 event in the log before the MarkerRunner's msgHeresAnOrder is called. Instead, the Market Runners's event log reads: "
				+ marketRunner.log.toString(), 1, marketRunner.log.size());
		
		
		//Step 2
		assertTrue("MarketRunner's scheduler should have returned true (has an action to do on an Order from a Sales Person), but didn't.", marketRunner.pickAndExecuteAnAction());
		
		//Checking post condition for step 2
		assertEquals("Mock Sales Person should have 1 event in the log after Mock Sales Person scheduler is called. Instead, the Mock Sales Person's event log reads: "
				+ salesPerson.log.toString(), 1, salesPerson.log.size());
		
		assertEquals("Market inventory for Ice Cream should have decreased by 1", market.inventory.get("Ice Cream").amount, 999);
		
		assertEquals("Market Runner should have no orders in orders", marketRunner.orders.size(), 0);
		
		assertFalse("MarketRunner's scheduler should have returned false because it has no action to do, but didn't.", marketRunner.pickAndExecuteAnAction());

	} //testOneMarketCustomerOrderNormativeScenerio
	
	public void testTwoBusinessOrderNormativeScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Checking pre-conditions
				assertEquals("Market Runner shouldn't have any orders in it", marketRunner.orders.size(), 0);
				
				assertEquals("Market Runner should have an empty event log before the Market Runner's msgHeresAnOrder is called. Instead, the Market Runner's event log reads: "
						+ marketRunner.log.toString(), 0, marketRunner.log.size());
				
				//Step 1
				marketRunner.msgHeresAnOrder(new MarketOrder(chineseRestaurant, "Steak", 1)); 
				
				//Checking post conditions for step 1
				assertEquals("Market Runner should have 1 order in it", marketRunner.orders.size(), 1);
				
				assertEquals("Market runner should have 1 event in the log before the MarkerRunner's msgHeresAnOrder is called. Instead, the Market Runners's event log reads: "
						+ marketRunner.log.toString(), 1, marketRunner.log.size());
				
				
				//Step 2
				assertTrue("MarketRunner's scheduler should have returned true (has an action to do on an Order from a Sales Person), but didn't.", marketRunner.pickAndExecuteAnAction());
				
				//Checking post condition for step 2
				assertEquals("Mock UPSman should have 1 event in the log after Mock UPSman scheduler is called. Instead, the Mock UPSman's event log reads: "
						+ UPSman.log.toString(), 1, UPSman.log.size());
				
				assertEquals("Market inventory for Ice Cream should have decreased by 1", market.inventory.get("Steak").amount, 999);
				
				assertEquals("Market Runner should have no orders in orders", marketRunner.orders.size(), 0);
				
				assertFalse("MarketRunner's scheduler should have returned false because it has no action to do, but didn't.", marketRunner.pickAndExecuteAnAction());
		
	} //testTwoBusinessOrderNormativeScenerio
	
	public void testThreeMarketCustomerAndBusinessOrderScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testFourBusinessAndMarketCustomerOrderScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

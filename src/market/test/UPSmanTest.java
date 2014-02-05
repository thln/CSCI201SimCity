package market.test;

import chineseRestaurant.ChineseRestaurant;
import chineseRestaurant.test.mock.ChineseRestaurantMockCook;
import application.Phonebook;
import application.Restaurant;
import junit.framework.TestCase;
import market.Market;
import market.MarketOrder.orderState;
import market.MarketOrder;
import market.MarketRunnerRole;
import market.SalesPersonRole;
import market.MarketCustomerRole.MarketCustomerState;
import market.UPSmanRole;
import market.interfaces.UPSman;
import market.test.mock.MockMarketCustomer;
import market.test.mock.MockMarketRunner;
import market.test.mock.MockSalesPerson;
import market.test.mock.MockUPSman;
import person.Worker;

public class UPSmanTest extends TestCase {

	Market market;
	ChineseRestaurant chineseRestaurant;
	Worker worker;
	MockSalesPerson salesPerson;
	MockMarketRunner marketRunner;
	ChineseRestaurantMockCook cook;

	UPSmanRole UPSman;

	public void setUp() throws Exception {
		super.setUp();
		market = Phonebook.getPhonebook().getEastMarket();
		chineseRestaurant = Phonebook.getPhonebook().getChineseRestaurant();
		salesPerson = (MockSalesPerson) Phonebook.getPhonebook().getEastMarket().getSalesPerson(true);
		marketRunner = (MockMarketRunner) Phonebook.getPhonebook().getEastMarket().getMarketRunner(true);
		cook = (ChineseRestaurantMockCook) Phonebook.getPhonebook().getChineseRestaurant().getCook(true);

		worker = new Worker("Worker", 50, "UPSman", "Market", 8, 12, 24);
		UPSman = new UPSmanRole(worker, "UPSman", "UPSman", market);
		UPSman.test = true;
	}


	public void testOneBusinessOrderNormativeScenerio() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Checking pre-conditions
		assertEquals("UPSman shouldn't have any orders in it", UPSman.orders.size(), 0);

		assertEquals("UPSman should have an empty event log before the UPSman's msgDeliverOrder is called. Instead, the UPSman's event log reads: "
				+ UPSman.log.toString(), 0, UPSman.log.size());

		//Step 1
		UPSman.msgDeliverOrder(new MarketOrder(chineseRestaurant, "Steak", 1));

		//Checking post conditions for step 1
		assertEquals("UPSman should have 1 order in it", UPSman.orders.size(), 1);

		assertEquals("UPSman should have 1 event in the log before the UPSman's msgDeliver is called. Instead, the UPSman's event log reads: "
				+ UPSman.log.toString(), 1, UPSman.log.size());


		//Step 2
		assertTrue("UPSman's scheduler should have returned true (has an action to do on an Order from a Market Runner), but didn't.", UPSman.pickAndExecuteAnAction());

		//Checking post condition for step 2
		assertEquals("Mock Cook should have 1 event in the log after Mock Cook scheduler is called. Instead, the Mock UPSman's event log reads: "
				+ cook.log.toString(), 1, cook.log.size());
		
		assertEquals("Mock Sales Person should have 1 event in the log after Mock Cook scheduler is called. Instead, the Mock UPSman's event log reads: "
				+ salesPerson.log.toString(), 1, salesPerson.log.size());

		assertEquals("UPSman should have no orders in orders", UPSman.orders.size(), 0);

		assertFalse("UPSman's scheduler should have returned false because it has no action to do, but didn't.", UPSman.pickAndExecuteAnAction());

	} //testOneBusinessOrderNormativeScenerio
}

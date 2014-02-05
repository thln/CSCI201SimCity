package market.test.mock;

import market.MarketOrder;
import market.interfaces.MarketCustomer;
import market.interfaces.SalesPerson;
//import market.interfaces.SeafoodRestaurant;
//import seafoodRestaurant.SeafoodRestaurant;
import testing.EventLog;
import testing.LoggedEvent;
import testing.Mock;
import chineseRestaurant.ChineseRestaurant;

public class MockSalesPerson extends Mock implements SalesPerson {

	public EventLog log = new EventLog();
	
	public MockSalesPerson(String name) {
		super(name);
	}

	//Messages
	public void msgIWantProducts(MarketCustomer customer, String item, int numWanted) {
	}

	public void msgIWantProducts(ChineseRestaurant chineseRestaurant, String item, int numWanted) {
	}

	public void msgOrderFulfilled(MarketOrder o) {
		log.add(new LoggedEvent("Recieved msgOrderFulfilled"));
	}

	public void msgOrderDelivered(MarketOrder o) {
		log.add(new LoggedEvent("Recieved msgOrderDelivered"));
	}

	public void msgPayment(MarketCustomer customer, double payment) {
		log.add(new LoggedEvent("Recieved msgPayment"));
	}

	public void msgPayment(ChineseRestaurant restaurant, double payment) {
	}
	
	//Actions
	public void findItems(MarketOrder o) {
	}

	public void giveCustomerItems(MarketOrder o) {

	}

	public void askForPayment(MarketOrder o){

	}

//	@Override
//	public void msgPayment(SeafoodRestaurant seafoodRestaurant, double payment) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void msgPayment(SeafoodRestaurant seafoodRestaurant, double payment) {
//		// TODO Auto-generated method stub
//		
//	}

}

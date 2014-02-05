package chineseRestaurant.test.mock;

import chineseRestaurant.interfaces.ChineseRestaurantCashier;
import chineseRestaurant.interfaces.ChineseRestaurantCustomer;
import chineseRestaurant.interfaces.ChineseRestaurantWaiter;
import market.interfaces.SalesPerson;
import testing.EventLog;
import testing.LoggedEvent;
import testing.Mock;

public class ChineseRestaurantMockCashier extends Mock implements ChineseRestaurantCashier {

	/**
	 * This is a Mock AmericanRestaurantCashier to serve for testing purposes. 
	 * @author Kristi Hupka
	 *
	 */
	
	public EventLog log = new EventLog();

	public ChineseRestaurantMockCashier(String name) {
		super(name);
	}

	/**
	 * Messages
	 */
	public void msgComputeBill(String choice, int tableNumber, ChineseRestaurantWaiter chineseRestaurantWaiter) {

	}

	public void msgPayment(String choice, double amount, ChineseRestaurantCustomer customer) {

	}

	public void msgPleasePayForItems(String choice, int amount, double bill, SalesPerson market) {
		log.add(new LoggedEvent("Recieved msgPleasePayForItems"));
	}
}

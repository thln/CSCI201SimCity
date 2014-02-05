package chineseRestaurant.test.mock;

import chineseRestaurant.ChineseRestaurantCashierRole;
import chineseRestaurant.interfaces.ChineseRestaurantMarket;
import testing.LoggedEvent;
import testing.Mock;

/**
 * A sample AmericanRestaurantMockCustomer built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Kristi Hupka
 *
 */

public class ChineseRestaurantMockMarket extends Mock implements ChineseRestaurantMarket {

	public ChineseRestaurantCashierRole chineseRestaurantCashierRole;

	public ChineseRestaurantMockMarket(String name) {
		super(name);
	}

	public void msgOutofItems(String choice, int orderAmount) {
		log.add(new LoggedEvent("Received msgOutOfItems from cook. Choice: " + choice + "Order Amount: " + orderAmount));
	}

	public void msgPayment(double payment, ChineseRestaurantCashierRole cashier) {
		log.add(new LoggedEvent("Received msgPayment from americanRestaurantCashier. Payment: " + payment));
	}
}
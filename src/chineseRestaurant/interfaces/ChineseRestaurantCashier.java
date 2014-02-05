package chineseRestaurant.interfaces;

import market.interfaces.SalesPerson;

public interface ChineseRestaurantCashier {
	public String getName();

	/**
	 * Messages
	 */
	public void msgComputeBill(String choice, int tableNumber, ChineseRestaurantWaiter chineseRestaurantWaiter);

	public void msgPayment(String choice, double amount, ChineseRestaurantCustomer customer);
	
	public void msgPleasePayForItems(String choice, int amount, double bill, SalesPerson market);
}

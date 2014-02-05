package chineseRestaurant.interfaces;

import chineseRestaurant.ChineseRestaurantCashierRole;

public interface ChineseRestaurantMarket {
	
	public String getName();
	
	/**
	 * Messages
	 */
	public void msgOutofItems(String choice, int orderAmount);
	
	public void msgPayment(double payment, ChineseRestaurantCashierRole chineseRestaurantCashierRole);
}

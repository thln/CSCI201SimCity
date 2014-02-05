package italianRestaurant.interfaces;

import market.interfaces.SalesPerson;

/**
 * A sample AmericanRestaurantCashier interface built to unit test a AmericanRestaurantCashierRole.
 *
 *
 */
public interface ItalianCashier {
	
	 //Sent by the waiter prompting the americanRestaurantCashier to compute the bill
	
	public abstract void msgComputeBill(ItalianWaiter w, ItalianCustomer c, String choice);
	
	public abstract void msgHereIsMoney(ItalianCustomer c, Double payment);
	
	public abstract void msgRestockingBill(ItalianMarket market, String foodname, Double billtotal);
	
	public abstract void msgPleasePayForItems(String foodname, String orderAmt, Double billtotal, SalesPerson sp);

}
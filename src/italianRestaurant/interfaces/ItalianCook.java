package italianRestaurant.interfaces;



/**
 * A sample AmericanRestaurantCashier interface built to unit test a AmericanRestaurantCashierRole.
 *
 *
 */
public interface ItalianCook {
	
	 //Sent by the waiter prompting the americanRestaurantCashier to compute the bill
	
	//message from waiter to market to fulfill inventory
	public abstract void msgHereIsCustOrder(ItalianWaiter w, String choice, int table);
	
	public abstract void msgOrderDone(ItalianMarket market, String foodname, int foodamt);
	
	public abstract void msgOutofChoice(ItalianMarket market, String foodname);
	
	public abstract void setAllowedOrder(boolean allowed);
}
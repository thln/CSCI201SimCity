package italianRestaurant.interfaces;


/**
 * A sample AmericanRestaurantWaiter interface built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Carmen Tan
 *
 */
public interface ItalianWaiter {

	public abstract void msgHereIsOrder(ItalianCustomer c, String choice);
	public abstract void msgHereIsCheck(ItalianCustomer c, Double total);
	public abstract void msgOrderDone(String choice, int table);
	public abstract void msgFoodOut(String choice, int table);
	
	
}
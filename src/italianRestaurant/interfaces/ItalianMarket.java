package italianRestaurant.interfaces;

/**
 * A sample Market interface built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Carmen Tan
 */
public interface ItalianMarket {
	
	 //Sent by the waiter prompting the americanRestaurantCashier to compute the bill
	
	//message from waiter to market to fulfill inventory
	public abstract void msgOrderforMarket(ItalianCook c, String choice, int amount);
	
	public abstract void msgHereIsPayment(ItalianCashier cashier, Double payment);
	
	public abstract void msgCashierStillOwes(ItalianCashier cashier, Double leftover);

}
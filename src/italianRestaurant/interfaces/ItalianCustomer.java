package italianRestaurant.interfaces;

import italianRestaurant.ItalianCustomerRole.AgentEvent;

/**
 * A sample AmericanRestaurantCustomer interface built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Carmen Tan
 *
 */
public interface ItalianCustomer {
	
	public void msgComeIn();
	
	public abstract void msgSitAtTable(int table);
	
	public abstract void msgdoneOrdering();
	
	public abstract void msgdoneWaitingForFood();
	
	public abstract void msgFoodOutReorder(String OutChoice);
	/**
	 * @param total The cost according to the americanRestaurantCashier
	 *
	 * Sent by the americanRestaurantCashier prompting the customer's money after the customer has approached the americanRestaurantCashier.
	 */
	public abstract void msgHereIsBill(Double total);

	/**
	 * @param total change (if any) due to the customer
	 *
	 * Sent by the americanRestaurantCashier to end the transaction between him and the customer. total will be >= 0 .
	 */
	public abstract void msgChangeAndReceipt(String receipt, Double change);


	/**
	 * @param remaining_cost how much money is owed
	 * Sent by the americanRestaurantCashier if the customer does not pay enough for the bill (in lieu of sending {@link #HereIsYourChange(double)}
	 */
	public abstract void YouOweUs(Double remaining_cost);

}
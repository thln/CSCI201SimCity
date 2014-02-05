package italianRestaurant;

import italianRestaurant.interfaces.*;

public class ItalianRestaurantOrder {

	/*Order class stores the different customer orders
	 * the waiter gives to the cook, and its state
	 * of cooking on the cook's "grill".
	 */
	public enum OrderState {pending, cooking, done, finished};
	public ItalianWaiter w;
	public ItalianFood food;
	public String choice;
	public int table;
	public OrderState s;
	
	ItalianRestaurantOrder(ItalianWaiter waiter, String f, int t)  {
		w = waiter;
		choice = f;
		table = t;
	}
		
	ItalianRestaurantOrder(ItalianWaiter waiter, ItalianFood f, int t) {
		w = waiter;
		food = f;
		table = t;
		s = OrderState.pending;
	}
		
	public String toString() {
		return "Order " + food;
	}
}

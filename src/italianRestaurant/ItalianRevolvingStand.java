package italianRestaurant;

import java.util.*;

public class ItalianRevolvingStand {

	List<ItalianRestaurantOrder> AllOrders = Collections.synchronizedList(new ArrayList<ItalianRestaurantOrder>());

	public void newOrder(ItalianRestaurantOrder o) {
		synchronized(AllOrders)
		{
			AllOrders.add(o);
		}
	}

	public ItalianRestaurantOrder takeOrder() {
		synchronized(AllOrders) {
			ItalianRestaurantOrder o = AllOrders.get(0);
			AllOrders.remove(0);
			return o;
		}
	}

	public boolean isStandEmpty() {
		synchronized(AllOrders)
		{
			return AllOrders.isEmpty();
		}
	}
	
	public int getSize() {
		return AllOrders.size();
	}
}

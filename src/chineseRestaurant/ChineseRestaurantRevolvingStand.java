package chineseRestaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChineseRestaurantRevolvingStand 
{

	List<ChineseRestaurantOrder> AllOrders = Collections.synchronizedList(new ArrayList<ChineseRestaurantOrder>());

	public void newOrder(ChineseRestaurantOrder o) 
	{
		synchronized(AllOrders)
		{
			AllOrders.add(o);
		}
	}

	public ChineseRestaurantOrder takeOrder() 
	{
		synchronized(AllOrders)
		{
			ChineseRestaurantOrder o = AllOrders.get(0);
			AllOrders.remove(0);
			return o;
		}
	}

	public boolean isStandEmpty() 
	{
		synchronized(AllOrders)
		{
			return AllOrders.isEmpty();
		}
	}
	
	public int getSize()
	{
		return AllOrders.size();
	}
}

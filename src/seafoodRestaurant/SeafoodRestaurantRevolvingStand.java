//package seafoodRestaurant;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import seafoodRestaurant.interfaces.SeafoodRestaurantWaiter;
//
//public class SeafoodRestaurantRevolvingStand 
//{
//	List<SeafoodRestaurantOrder> AllOrders = Collections.synchronizedList(new ArrayList<SeafoodRestaurantOrder>());
//
//	public void newOrder(String order, int table, SeafoodRestaurantWaiter wait) 
//	{
//		synchronized(AllOrders)
//		{
//			AllOrders.add(new SeafoodRestaurantOrder(order, table, wait));
//		}
//	}
//
//	public SeafoodRestaurantOrder takeOrder() 
//	{
//		synchronized(AllOrders)
//		{
//			SeafoodRestaurantOrder o = AllOrders.get(0);
//			AllOrders.remove(0);
//			return o;
//		}
//	}
//
//	public boolean isStandEmpty() 
//	{
//		synchronized(AllOrders)
//		{
//			return AllOrders.isEmpty();
//		}
//	}
//	
//	public int getSize()
//	{
//		return AllOrders.size();
//	}
//
//}

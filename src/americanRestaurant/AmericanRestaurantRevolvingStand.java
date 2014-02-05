//package americanRestaurant;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import americanRestaurant.AmericanRestaurantCookRole.Order;
//
//public class AmericanRestaurantRevolvingStand {
//
//	List<Order> AllOrders;
//
//	AmericanRestaurantRevolvingStand(){
//		AllOrders = Collections.synchronizedList(new ArrayList<Order>());
//	}
//	
//	public void newOrder(Order o) 
//	{
//		synchronized(AllOrders)
//		{
//			AllOrders.add(o);
//		}
//	}
//
//	public Order takeOrder() 
//	{
//		synchronized(AllOrders)
//		{
//			Order o = AllOrders.get(0);
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
//}

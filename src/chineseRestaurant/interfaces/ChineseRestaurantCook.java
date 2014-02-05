package chineseRestaurant.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import chineseRestaurant.ChineseRestaurantOrder;
import chineseRestaurant.ChineseRestaurantWaiterRole;
import chineseRestaurant.ChineseRestaurantCookRole.Stock;
import market.Market;
import person.Worker;

public interface ChineseRestaurantCook 
{
	public abstract void msgHeresAnOrder(int table, String choice, ChineseRestaurantWaiterRole chineseRestaurantWaiterRole);

	public abstract void msgOrderDone(ChineseRestaurantOrder chineseRestaurantOrder);

	public abstract void msgCantFulfill(String choice, int amount, int orderedAmount);

	public abstract void msgOrderFulfillment(String choice, int amount, int orderedAmount);

	public abstract void msgAtDestination();
}

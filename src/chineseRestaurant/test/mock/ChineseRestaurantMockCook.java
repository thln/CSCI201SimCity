package chineseRestaurant.test.mock;

import chineseRestaurant.ChineseRestaurantOrder;
import chineseRestaurant.ChineseRestaurantWaiterRole;
import chineseRestaurant.interfaces.ChineseRestaurantCook;
import market.Market;
import testing.EventLog;
import testing.LoggedEvent;
import testing.Mock;

	/**
	 * This is a mock cook to serve for testing purposes. 
	 * @author Tam Henry Le Nguyen
	 *
	 */

public class ChineseRestaurantMockCook extends Mock implements ChineseRestaurantCook {
	
	public EventLog log = new EventLog();
	
	public ChineseRestaurantMockCook(String name)
	{
		super(name);
	}
	
	public void msgHeresAnOrder(int table, String choice, ChineseRestaurantWaiterRole chineseRestaurantWaiterRole)
	{
		
	}

	public void msgOrderDone(ChineseRestaurantOrder chineseRestaurantOrder)
	{
		
	}

	public void msgAtDestination()
	{
		
	}

	@Override
	public void msgCantFulfill(String choice, int amount, int orderedAmount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgOrderFulfillment(String choice, int amount, int orderedAmount) {
		log.add(new LoggedEvent("Recieved msgOrderFulfillment"));
	}
}

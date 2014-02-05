package chineseRestaurant.interfaces;

import chineseRestaurant.ChineseRestaurantMenu;
import chineseRestaurant.interfaces.ChineseRestaurantHost;
import chineseRestaurant.interfaces.ChineseRestaurantWaiter;

/**
 * A sample AmericanRestaurantCustomer interface built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Kristi Hupka
 *
 */
public interface ChineseRestaurantCustomer {

	public abstract void setHost(ChineseRestaurantHost chineseRestaurantHost);

	public abstract String getCustomerName();
	
	public abstract String getName();



	/**
	 * Messages
	 */
	public abstract void gotHungry(int xHome, int yHome);

	public abstract void msgTablesAreFull();
	
	public abstract void msgPleaseFollowMe(int tableNumber, ChineseRestaurantMenu chineseRestaurantMenu, ChineseRestaurantWaiter chineseRestaurantWaiter);

	public abstract void msgAnimationFinishedGoToSeat();

	public abstract void msgWhatWouldYouLike();

	public abstract void msgPleaseReorder(ChineseRestaurantMenu newMenu);

	public abstract void msgHeresYourOrder(String choice);

	public abstract void msgHeresYourCheck(double check);
	
	public abstract void msgAnimationFinishedGoToCashier();

	public abstract void msgHeresYourChange(double change);
	
	public abstract void msgGoToJail();

	public abstract void msgAnimationFinishedLeaveRestaurant();

	public abstract void msgComeIn();
}
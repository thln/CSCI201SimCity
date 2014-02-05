package chineseRestaurant.test.mock;


import chineseRestaurant.ChineseRestaurantMenu;
import chineseRestaurant.interfaces.ChineseRestaurantCashier;
import chineseRestaurant.interfaces.ChineseRestaurantHost;
import chineseRestaurant.interfaces.ChineseRestaurantCustomer;
import chineseRestaurant.interfaces.ChineseRestaurantWaiter;
import testing.LoggedEvent;
import testing.Mock;

/**
 * A sample AmericanRestaurantMockCustomer built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Kristi Hupka
 *
 */
public class ChineseRestaurantMockCustomer extends Mock implements ChineseRestaurantCustomer {

	public ChineseRestaurantCashier chineseRestaurantCashier;

	/**
	 * Reference to the AmericanRestaurantCashier under test that can be set by the unit test.
	 */
	//public AmericanRestaurantCashier americanRestaurantCashier;

	public ChineseRestaurantMockCustomer(String name) {
		super(name);
	}

	@Override
	public void setHost(ChineseRestaurantHost chineseRestaurantHost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCustomerName() {
		// TODO Auto-generated method stub
		return "AmericanRestaurantMockCustomer";
	}

	@Override
	public void gotHungry(int xHome, int yHome) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgTablesAreFull() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAnimationFinishedGoToSeat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWhatWouldYouLike() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPleaseReorder(ChineseRestaurantMenu newMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHeresYourOrder(String choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHeresYourCheck(double check) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAnimationFinishedGoToCashier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHeresYourChange(double change) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgHeresYourChange from americanRestaurantCashier. Change: " + change));	
	}
	
	@Override
	public void msgGoToJail() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received msgGoToJail from americanRestaurantCashier."));	
	}

	@Override
	public void msgAnimationFinishedLeaveRestaurant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPleaseFollowMe(int tableNumber, ChineseRestaurantMenu chineseRestaurantMenu, ChineseRestaurantWaiter chineseRestaurantWaiter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgComeIn() {
		// TODO Auto-generated method stub
		
	}
}


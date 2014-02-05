package chineseRestaurant.test.mock;

import chineseRestaurant.interfaces.ChineseRestaurantCashier;
import chineseRestaurant.interfaces.ChineseRestaurantCook;
import chineseRestaurant.interfaces.ChineseRestaurantHost;
import chineseRestaurant.interfaces.ChineseRestaurantCustomer;
import chineseRestaurant.interfaces.ChineseRestaurantWaiter;
import testing.LoggedEvent;
import testing.Mock;

/**
 * A sample AmericanRestaurantMockWaiter built to unit test a AmericanRestaurantCashierRole.
 *
 * @author Kristi Hupka
 *
 */

public class ChineseRestaurantMockWaiter extends Mock implements ChineseRestaurantWaiter {


	public ChineseRestaurantMockWaiter(String name) {
		super(name);
	}

	public void msgAtDestination() {
		// TODO Auto-generated method stub
		
	}

	
	public void msgIsInLobby() {
		// TODO Auto-generated method stub
		
	}

	
	public void msgReadyToOrder(ChineseRestaurantCustomer customer) {
		// TODO Auto-generated method stub
		
	}

	
	public void msgHeresMyOrder(ChineseRestaurantCustomer customer, String choice) {
		// TODO Auto-generated method stub
		
	}

	
	public void msgOrderIsNotAvailable(String choice, int tableNumber) {
		// TODO Auto-generated method stub
		
	}


	public void msgOrderIsReady(int table, String choice) {
		// TODO Auto-generated method stub
		
	}


	public void msgIWantMyCheck(ChineseRestaurantCustomer cust) {
		// TODO Auto-generated method stub
		
	}

	
	public void msgLeavingTable(ChineseRestaurantCustomer cust) {
		// TODO Auto-generated method stub
		
	}


	public void msgWantToGoOnBreak() {
		// TODO Auto-generated method stub
		
	}


	public void msgPermissionToBreak(boolean reply) {
		// TODO Auto-generated method stub
		
	}


	public void msgGoOffBreak() {
		// TODO Auto-generated method stub
		
	}

	
	public void msgHereIsCheck(int tableNumber, double checkAmount) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Received HereIsCheck from americanRestaurantCashier. Total = " + checkAmount));
	}


	public void setHost(ChineseRestaurantHost chineseRestaurantHost) {
		// TODO Auto-generated method stub
		
	}

	public void setCook(ChineseRestaurantCook chineseRestaurantCook) {
		// TODO Auto-generated method stub
		
	}

	public void setCashier(ChineseRestaurantCashier chineseRestaurantCashier) {
		// TODO Auto-generated method stub
		
	}

	public boolean isOnBreak() {
		// TODO Auto-generated method stub
		return false;
	}


	public void msgPleaseSeatCustomer(int tableNumber, ChineseRestaurantCustomer customer, int xHome, int yHome) {
		// TODO Auto-generated method stub
		
	}

}

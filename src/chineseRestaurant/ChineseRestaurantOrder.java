package chineseRestaurant;

import chineseRestaurant.interfaces.ChineseRestaurantCustomer;

public class ChineseRestaurantOrder {

	ChineseRestaurantWaiterRole chineseRestaurantWaiterRole;
	public ChineseRestaurantCustomer customer;

	int tableNumber;

	public String choice;

	enum orderStatus {Open, Cooking, Done}
	orderStatus status = orderStatus.Open;

	ChineseRestaurantOrder(int tableNumber, String choice, ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) {
		this.tableNumber = tableNumber;
		this.choice = choice;
		this.chineseRestaurantWaiterRole = chineseRestaurantWaiterRole;
	}

	ChineseRestaurantOrder(int tableNumber, String choice, ChineseRestaurantCustomer customer) {
		this.tableNumber = tableNumber;
		this.choice = choice;
		this.customer = customer;
	}

	//utilities
	public void setCooking() {
		status = orderStatus.Cooking;
	}

	public void setDone() {
		status = orderStatus.Done;
	}

	public boolean isOpen() {
		if (status == orderStatus.Open)
			return true;
		else
			return false;
	}


	public boolean isDone() {
		if (status == orderStatus.Done)
			return true;
		else
			return false;
	}
}
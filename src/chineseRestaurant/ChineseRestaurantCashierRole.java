package chineseRestaurant;


import java.util.*;

import chineseRestaurant.interfaces.ChineseRestaurantCashier;
import chineseRestaurant.interfaces.ChineseRestaurantCustomer;
import chineseRestaurant.interfaces.ChineseRestaurantWaiter;
import application.Phonebook;
import market.interfaces.SalesPerson;
import person.Person;
import person.Role;
import person.Worker;


/**
 * Restaurant AmericanRestaurantCashier Role
 */

public class ChineseRestaurantCashierRole extends Role implements ChineseRestaurantCashier {

	public ChineseRestaurant chineseRestaurant;


	//Keeps a list of checks
	public List<Check> Checks = Collections.synchronizedList(new ArrayList<Check>());
	public List<Payment> Payments = Collections.synchronizedList(new ArrayList<Payment>());
	public List<Order> OrdersToPay = Collections.synchronizedList(new ArrayList<Order>());

	//Map of food prices
	private Map<String, Double> foodPrices = new HashMap<String, Double>(); {
		foodPrices.put("Chicken", 10.99);
		foodPrices.put("Steak", 15.99);
		foodPrices.put("Pizza", 8.99);
		foodPrices.put("Salad", 5.99);
	}

	public ChineseRestaurantCashierRole(Person p1, String pName, String rName, ChineseRestaurant chineseRestaurant) {
		super(p1, pName, rName);
		this.chineseRestaurant = chineseRestaurant;
	}

	public ChineseRestaurantCashierRole(String roleName, ChineseRestaurant chineseRestaurant) {
		super(roleName);
		this.chineseRestaurant = chineseRestaurant;
	}

	public String getName() {
		return person.getName();
	}



	/**
	 * Messages
	 */
	public void msgComputeBill(String choice, int tableNumber, ChineseRestaurantWaiter chineseRestaurantWaiter) {
		synchronized(Checks) {
			print("Calculating bill for table " + tableNumber);
			//log.add(new LoggedEvent("Calculating bill for table"));
			Checks.add(new Check(choice, tableNumber, chineseRestaurantWaiter));
			if (person != null){
				stateChanged();
			}
		}
	}

	public void msgPayment(String choice, double amount, ChineseRestaurantCustomer customer) {
		synchronized(Payments)
		{
			print("Received payment from " + customer.getCustomerName());
			//log.add(new LoggedEvent("Received payment from " + customer.getCustomerName()));
			Payments.add(new Payment(choice, amount, customer));
			stateChanged();
		}
	}

	public void msgPleasePayForItems(String choice, int amount, double bill, SalesPerson market) {
		synchronized(OrdersToPay)
		{
			OrdersToPay.add(new Order(choice, amount, bill, market));
			//log.add(new LoggedEvent("Received msgOrderFulfilled from " + market.getName()));
			stateChanged();
		}
	}


	/**
	 * Scheduler
	 */
	public boolean pickAndExecuteAnAction() 
	{
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		synchronized(Checks)
		{
			if (!Checks.isEmpty()) 
			{
				ComputeBill();
				return true;
			}
		}

		synchronized(Payments)
		{
			if (!Payments.isEmpty()) 
			{
				GiveChange();
				return true;
			}
		}

		synchronized(OrdersToPay)
		{
			if (!OrdersToPay.isEmpty()) 
			{
				PayMarket();
				return true;
			}
		}

		if (leaveRole) {
			chineseRestaurant.goingOffWork(person);
			leaveRole = false;
			return true;
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}



	/**
	 * Actions
	 */

	public void ComputeBill() {
		double checkAmount = foodPrices.get(Checks.get(0).choice);
		Checks.get(0).waiterRole.msgHereIsCheck(Checks.get(0).tableNumber, checkAmount);
		Checks.remove(0);
	}

	private void GiveChange() {
		if (Payments.get(0).payment < foodPrices.get(Payments.get(0).choice)) 
		{
			Payments.get(0).customer.msgGoToJail();
			Payments.remove(0);
			return;
		}

		double change;
		change = Payments.get(0).payment - foodPrices.get(Payments.get(0).choice);
		change = Math.round(change * 100.0) / 100.0;
		Payments.get(0).customer.msgHeresYourChange(change);
		print("Gave change to customer " + Payments.get(0).customer.getName());
		Payments.remove(0);
	}

	private void PayMarket() {
		if (OrdersToPay.get(0).bill == OrdersToPay.get(0).amountOrdered * foodPrices.get(OrdersToPay.get(0).choice)) {
			OrdersToPay.get(0).setBill(Math.round(OrdersToPay.get(0).bill * 100.0) / 100.0);
			print("Giving Market " + OrdersToPay.get(0).market + " for " + OrdersToPay.get(0).amountOrdered + " " + OrdersToPay.get(0).choice + "(s) x $" + foodPrices.get(OrdersToPay.get(0).choice) + " = $" + OrdersToPay.get(0).bill);
			OrdersToPay.get(0).market.msgPayment(chineseRestaurant, OrdersToPay.get(0).bill);
			//change above if implementing multiple markets
			OrdersToPay.remove(0);
		}
		else {
			//Dispute bill?
		}
	}



	//Check Class
	public class Check {
		String choice;
		int tableNumber;
		ChineseRestaurantWaiter waiterRole;

		Check(String choice, int tableNumber, ChineseRestaurantWaiter chineseRestaurantWaiter) {
			this.choice = choice;
			this.tableNumber = tableNumber;
			this.waiterRole = chineseRestaurantWaiter;
		}
	}

	//Payment Class
	public class Payment {
		public String choice;
		public double payment;
		public ChineseRestaurantCustomer customer;

		Payment(String choice, double payment, ChineseRestaurantCustomer customer) {
			this.choice = choice;
			this.payment = payment;
			this.customer = customer;
		}
	}

	//Order Class
	public class Order {
		public String choice;
		int amountOrdered;
		double bill;
		SalesPerson market; //The market

		Order(String choice, int amountOrdered, double bill, SalesPerson market) {
			this.choice = choice;
			this.amountOrdered = amountOrdered;
			this.bill = bill;
			this.market = market;
		}

		public void setBill(double newBill) {
			bill = newBill;
		}
	}
}
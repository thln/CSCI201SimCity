package chineseRestaurant;

//import agent.Agent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import chineseRestaurant.interfaces.ChineseRestaurantCustomer;
import chineseRestaurant.interfaces.ChineseRestaurantWaiter;
import chineseRestaurant.ChineseRestaurantMyCustomer.customerState;
import person.Person;
import person.Role;
import person.Worker;
import application.Phonebook;
import application.gui.animation.agentGui.RestaurantWaiterGui;

/**
 * Restaurant AmericanRestaurantWaiter Role
 */

public class ChineseRestaurantWaiterRole extends Role implements ChineseRestaurantWaiter {

	//Keeps  a list of customers
	public List<ChineseRestaurantMyCustomer> ChineseRestaurantMyCustomers = Collections.synchronizedList(new ArrayList<ChineseRestaurantMyCustomer>());
	public List<ChineseRestaurantOrder> readyOrders = Collections.synchronizedList(new ArrayList<ChineseRestaurantOrder>());

	protected String name;
	protected String RoleName = "AmericanRestaurantWaiter";
	//RestaurantWaiterGui waiterGui = (RestaurantWaiterGui) gui;
	RestaurantWaiterGui waiterGui;

	public ChineseRestaurantMenu chineseRestaurantMenu = new ChineseRestaurantMenu();

	public ChineseRestaurant chineseRestaurant;

	Timer breakTimer = new Timer();

	protected boolean isInLobby = true;
	protected enum breakStatus{working, askedToGoOnBreak, waitingForReply, receivedReply, onBreak, goOffBreak};
	breakStatus state = breakStatus.working;

	protected boolean PermissionToBreak = false;

	public ChineseRestaurantWaiterRole(Person p1, String pName, String rName, ChineseRestaurant restaurant) {
		super(p1, pName, rName);
		chineseRestaurant = restaurant;
	}

	public String getMaitreDName() {
		return name;
	}

	/**
	 * Messages
	 */
	public void msgPleaseSeatCustomer(int tableNumber, ChineseRestaurantCustomerRole customer, int xHome, int yHome) {
		ChineseRestaurantMyCustomers.add(new ChineseRestaurantMyCustomer(customer, tableNumber, xHome, yHome));
		print(customer.getCustomerName() + " was added to myCustomers list");
		stateChanged();
	}

	public void msgPleaseSeatTestCustomer(ChineseRestaurantCustomer customerTest)
	{
		if(test)
		{
			ChineseRestaurantMyCustomers.add(new ChineseRestaurantMyCustomer(customerTest));
			print("Test AmericanRestaurantCustomer was added.");
			stateChanged();
		}
	}

	public void msgAtDestination() {//from animation
		atDestination.release();// = true;
		stateChanged();
	}

	public void msgIsInLobby() {
		isInLobby = true;
		if (person != null){
			stateChanged();
		}
	}

	public void msgReadyToOrder(ChineseRestaurantCustomerRole customer) {
		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) 
		{
			if (myCust.customer == customer) 
			{
				myCust.setReadyToOrder();
				stateChanged();
			}
		}
	}

	public void msgHeresMyOrder(ChineseRestaurantCustomer customer, String choice) {
		//		if(test)
		//		{
		//			for (myCustomer myCust : myCustomers) 
		//			{
		//				if (myCust.customer == customer) 
		//				{
		//					myCust.setChoice(choice);
		//					myCust.setOrdered();
		//					stateChanged();
		//				}
		//			}
		//		}

		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) 
		{
			if (myCust.customer == customer || myCust.testCustomer == customer) 
			{
				myCust.setChoice(choice);
				myCust.setOrdered();
				stateChanged();
			}
		}
	}

	public void msgOrderIsNotAvailable(String choice, int tableNumber) {
		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) {
			if (myCust.tableNumber == tableNumber) {
				myCust.setReorder();
				stateChanged();
			}
		}
	}

	public void msgOrderIsReady(int table, String choice) {
		ChineseRestaurantOrder chineseRestaurantOrder = null;
		for (ChineseRestaurantMyCustomer MC: ChineseRestaurantMyCustomers) {
			if (MC.tableNumber == table)
				chineseRestaurantOrder = new ChineseRestaurantOrder(table, choice, MC.customer);
		}

		readyOrders.add(chineseRestaurantOrder);
		stateChanged();
	}

	public void msgIWantMyCheck(ChineseRestaurantCustomerRole cust) {
		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) {
			if (myCust.customer == cust) {
				myCust.setWantCheck();
				stateChanged();
			}
		}
	}

	public void msgLeavingTable(ChineseRestaurantCustomerRole cust)  {
		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) 
		{
			if (myCust.customer == cust) 
			{
				myCust.setFinished();
				stateChanged();
			}
		}
	}

	public void msgWantToGoOnBreak() {
		state = breakStatus.askedToGoOnBreak;
		print("Got message to go on break");
		stateChanged();
	}

	public void msgPermissionToBreak(boolean reply) {
		PermissionToBreak = reply;
		print("Recieved reply and it is: " + reply);
		state = breakStatus.receivedReply;
		stateChanged();
	}

	public void msgGoOffBreak() {
		state = breakStatus.goOffBreak;
		print("Got message to go off break");
		stateChanged();
	}

	public void msgHereIsCheck(int tableNumber, double checkAmount) {
		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) {
			if (myCust.tableNumber == tableNumber) {
				myCust.CheckAmount = checkAmount;
				print("Recieved " + myCust.customer.getCustomerName() + "'s check of $" + myCust.CheckAmount);
				stateChanged();
			}
		}
	}


	/**
	 * Scheduler
	 */
	public boolean pickAndExecuteAnAction() {

		//if an order is ready, deliver it
		if (leaveRole) {
			AskHostToLeave();
		}

		if (state == breakStatus.askedToGoOnBreak) {
			print("Scheduled to ask for break");
			askToGoOnBreak();
			return true;
		}

		if ((PermissionToBreak == false) && (state == breakStatus.receivedReply)) 
		{
			breakDenied();
			return true;
		}

		if ((ChineseRestaurantMyCustomers.isEmpty()) && (PermissionToBreak == true) && (state == breakStatus.receivedReply)) 
		{
			goOnBreak();
			return true;
		}

		if (state == breakStatus.goOffBreak) 
		{
			goOffBreak();
			return true;
		}

		if (leaveRole) {
			chineseRestaurant.goingOffWork(person);
			leaveRole = false;
			return true;
		}

		synchronized(readyOrders) 
		{
			while (!readyOrders.isEmpty()) 
			{
				deliverOrder();
				return true;
			}
		}

		synchronized(ChineseRestaurantMyCustomers){
			for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) 
			{
				if (myCust.isFinished()) 
				{
					clearTable(myCust);
					return true;
				}

				//if a customer is waiting
				if (myCust.isWaiting()) 
				{
					if (isInLobby == true) 
					{	
						seatCustomer(myCust);
						return true;
					}
					return true;
				} //return true to the abstract agent to re-invoke the scheduler.

				if (myCust.isReadyToOrder()) 
				{
					takeOrder(myCust);
					return true;
				}

				if (myCust.isOrdered()) 
				{
					placeOrder(myCust);
					return true;
				}

				if (myCust.isReorder()) 
				{
					retakeOrder(myCust);
					return true;
				}

				if (myCust.isWantCheck()) 
				{
					giveCheck(myCust);
					return true;
				}
			}
		}


		//		catch (ConcurrentModificationException e) 
		//		{
		//			e.printStackTrace();
		//			stateChanged();
		//			return false;
		//		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}



	/**
	 * Actions
	 */
	protected void seatCustomer(ChineseRestaurantMyCustomer MC) {
		waiterGui.DoPickUpCustomer(MC.xHome, MC.yHome);
		try {
			atDestination.acquire();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();

		}
		MC.customer.msgPleaseFollowMe(MC.tableNumber, chineseRestaurantMenu, this);
		isInLobby = false;
		DoSeatCustomer(MC.customer, MC.tableNumber);

		try {
			atDestination.acquire();
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoLeaveCustomer();
		print("Finished seating " + MC.customer.getCustomerName());
		MC.setSeated();
	}

	// The animation DoXYZ() routines
	protected void DoSeatCustomer(ChineseRestaurantCustomerRole customer, int table) {
		isInLobby = false;
		print("Seating " + customer + " at " + table);
		waiterGui.DoBringToTable(customer, table);
	}

	protected void takeOrder(ChineseRestaurantMyCustomer MC) {
		isInLobby = false;
		print(MC.customer.getCustomerName() + " wants to order");

		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) 
		{
			if (myCust.customer == MC.customer)
			{
				myCust.setRequestedToOrder();
			}
		}

		waiterGui.DoTakeOrder(MC.tableNumber);
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoLeaveCustomer();

		MC.customer.msgWhatWouldYouLike();
	}

	protected void placeOrder(ChineseRestaurantMyCustomer MC) {
		isInLobby = false;
		print("Placing " + MC.customer.getCustomerName() + "'s order");

		for (ChineseRestaurantMyCustomer myCust : ChineseRestaurantMyCustomers) {
			if (myCust.customer == MC.customer) {
				myCust.setWaitingForFood();
			}
		}

		waiterGui.DoGoToKitchen();
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoLeaveCustomer();

		chineseRestaurant.chineseRestaurantCookRole.msgHeresAnOrder(MC.tableNumber, MC.choice, this);

	}

	protected void retakeOrder(ChineseRestaurantMyCustomer MC) {
		isInLobby = false;

		waiterGui.DoTakeOrder(MC.tableNumber);
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoLeaveCustomer();
		MC.setSeated();

		print("Asking " + MC.customer.getCustomerName() + " for a re-order");
		MC.customer.msgPleaseReorder(new ChineseRestaurantMenu().remove(MC.choice));

	}

	protected void deliverOrder() {
		isInLobby = false;
		waiterGui.DoGoToPlatingArea();

		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoDeliverOrder(readyOrders.get(0).tableNumber, readyOrders.get(0).choice);
		print("waiter called msgGotOrder");
		chineseRestaurant.cookGui.msgGotOrder(readyOrders.get(0).choice);
		try {
			atDestination.acquire();
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoLeaveCustomer();
		readyOrders.get(0).customer.msgHeresYourOrder(readyOrders.get(0).choice);
		chineseRestaurant.chineseRestaurantCashierRole.msgComputeBill(readyOrders.get(0).choice, readyOrders.get(0).tableNumber, this);

		//Changing customer state to "Got Food"
		for (ChineseRestaurantMyCustomer MC : ChineseRestaurantMyCustomers) {
			if (MC.customer.equals(readyOrders.get(0).customer))
				MC.setGotFood();	
		}

		readyOrders.remove(0);
	}

	protected void giveCheck(ChineseRestaurantMyCustomer MC) {
		isInLobby = false;

		waiterGui.DoTakeOrder(MC.tableNumber); //Is going to table
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
		waiterGui.DoLeaveCustomer();

		MC.customer.msgHeresYourCheck(MC.CheckAmount);
		MC.state = customerState.GaveCheck;
	}

	protected void clearTable(ChineseRestaurantMyCustomer MC) {
		print(MC.customer.getCustomerName() + " is leaving " + MC.tableNumber);
		chineseRestaurant.chineseRestaurantHostRole.msgLeavingTable(MC.customer, this);
		chineseRestaurant.removeCustomer(MC.customer);

		ChineseRestaurantMyCustomers.remove(MC);
	}

	protected void askToGoOnBreak() 
	{
		print("Asking host for break");
		chineseRestaurant.chineseRestaurantHostRole.msgMayIGoOnBreak(this);
		state = breakStatus.waitingForReply;
	}

	protected void goOnBreak() 
	{
		isInLobby = false;
		state = breakStatus.onBreak;
		PermissionToBreak = false;
		//	waiterGui.DoGoOnBreak();
		stateChanged();
	}

	protected void goOffBreak() {
		isInLobby = false;
		//		waiterGui.DoLeaveCustomer();
		chineseRestaurant.chineseRestaurantHostRole.msgOffBreak(this);
		state = breakStatus.working;
		//		waiterGui.denyBreak();
		stateChanged();
	}

	protected void breakDenied() {
		state = breakStatus.working;
		//	waiterGui.denyBreak();
		stateChanged();
	}

	protected void AskHostToLeave()	{
		if(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole != null)
			Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.msgIAmLeavingSoon(this);

		if(ChineseRestaurantMyCustomers.isEmpty())
		{
			chineseRestaurant.chineseRestaurantHostRole.msgIAmLeavingWork(this);
			((Worker) person).roleFinishedWork();
			leaveRole = false;
		}
	}

	/**
	 * Utilities
	 */

	public boolean isOnBreak() {
		if (state == breakStatus.onBreak)
			return true;
		return false;
	}

	public void setGui(RestaurantWaiterGui waiter) {
		waiterGui = waiter;
	}

	public RestaurantWaiterGui getGui() {
		return waiterGui;
	}
}


